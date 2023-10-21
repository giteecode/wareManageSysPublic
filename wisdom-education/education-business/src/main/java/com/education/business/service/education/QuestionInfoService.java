package com.education.business.service.education;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.QuestionInfoMapper;
import com.education.business.parser.QuestionImportParser;
import com.education.business.parser.QuestionImportParserManager;
import com.education.business.service.BaseService;
import com.education.common.enums.CorrectStatusEnum;
import com.education.common.enums.QuestionTypeEnum;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.entity.QuestionInfo;
import com.education.model.entity.QuestionLanguagePointsInfo;
import com.education.model.request.PageParam;
import com.education.model.request.QuestionInfoQuery;
import com.education.model.response.QuestionGroupItemResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试题管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 11:02
 */
@Service
public class QuestionInfoService extends BaseService<QuestionInfoMapper, QuestionInfo> {

    @Resource
    private QuestionLanguagePointsInfoService questionLanguagePointsInfoService;
    @Resource
    private TestPaperQuestionInfoService testPaperQuestionInfoService;
    @Resource
    private ExamQuestionAnswerService examQuestionAnswerService;

    /**
     * 试题分页列表
     * @param pageParam
     * @param questionInfoQuery
     * @return
     */
    public PageInfo<QuestionInfoDto> selectPageList(PageParam pageParam, QuestionInfoQuery questionInfoQuery) {
        Page<QuestionInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, questionInfoQuery));
    }

    /**
     * 添加或修改试题
     * @param questionInfoDto
     * @return
     */
    @Transactional
    public ResultCode saveOrUpdateQuestionInfo(QuestionInfoDto questionInfoDto) {
        List<Integer> languagePointsInfoIdList = questionInfoDto.getLanguagePointsInfoId();
        Integer questionInfoId = questionInfoDto.getId();
        if (ObjectUtils.isNotEmpty(questionInfoId)) {
            ExamQuestionAnswer studentQuestionAnswer = examQuestionAnswerService.selectByQuestionInfoId(questionInfoId);
            Assert.isNull(studentQuestionAnswer, () -> new BusinessException("试题已被使用，禁止修改"));
            // 删除试题知识点关联
            LambdaQueryWrapper queryWrapper = Wrappers.<QuestionLanguagePointsInfo>lambdaQuery()
                    .eq(QuestionLanguagePointsInfo::getQuestionInfoId, questionInfoDto.getId());
            questionLanguagePointsInfoService.remove(queryWrapper);
        }

        super.saveOrUpdate(questionInfoDto);

        // 保存试题知识点关联信息
        List<QuestionLanguagePointsInfo> questionLanguagePointsInfoList = new ArrayList<>();
        languagePointsInfoIdList.forEach(languagePointsInfoId -> {
            QuestionLanguagePointsInfo questionLanguagePointsInfo = new QuestionLanguagePointsInfo();
            questionLanguagePointsInfo.setLanguagePointsInfoId(languagePointsInfoId);
            questionLanguagePointsInfo.setQuestionInfoId(questionInfoDto.getId());
            questionLanguagePointsInfo.setCreateDate(new Date());
            questionLanguagePointsInfoList.add(questionLanguagePointsInfo);
        });
        questionLanguagePointsInfoService.saveBatch(questionLanguagePointsInfoList);
        return new ResultCode(ResultCode.SUCCESS, "操作成功");
    }


    public QuestionInfoDto selectById(Integer id) {
        QuestionInfoDto questionInfoDto = baseMapper.selectById(id);
        questionInfoDto.setQuestionTypeName(QuestionTypeEnum.getName(questionInfoDto.getQuestionType()));
        return questionInfoDto;
    }


    /**
     * 对用户作答试题分组
     * @param questionInfoAnswerList
     * @return
     */
    public List<QuestionGroupItemResponse> groupQuestion(List<QuestionInfoAnswer> questionInfoAnswerList) {
        List<QuestionGroupItemResponse> list = new ArrayList<>();
        for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
            int value = item.getValue();
            List<QuestionInfoAnswer> questionList = questionInfoAnswerList.stream()
                    .filter(questionInfoAnswer -> value == questionInfoAnswer.getQuestionType().intValue())
                    .collect(Collectors.toList());
            if (ObjectUtils.isNotEmpty(questionList)) {
                QuestionGroupItemResponse examQuestionItemResponse = new QuestionGroupItemResponse();
                examQuestionItemResponse.setQuestionTypeName(item.getName());
                examQuestionItemResponse.setQuestionInfoAnswerList(questionList);
                questionList.forEach(questionItem -> {
                    questionItem.setQuestionTypeName(item.getName());
                });
                list.add(examQuestionItemResponse);
            }
        }
        return list;
    }

    public ResultCode deleteById(Integer questionInfoId) {
        if (testPaperQuestionInfoService.verificationQuestionIsUsed(questionInfoId)) {
            super.removeById(questionInfoId);
            return new ResultCode(ResultCode.SUCCESS, "删除成功");
        }
        return new ResultCode(ResultCode.FAIL, "试题已被使用，无法删除");

    }

    public int importQuestion(Integer schoolType, Integer gradeInfoId, Integer subjectId, List<QuestionInfo> questionInfoList) {
        questionInfoList.forEach(questionInfo -> {
            // 出现空白行跳过
            if (questionInfo.excelDataIsAllNull()) {
                return;
            }

            QuestionTypeEnum questionTypeEnum = Arrays.stream(QuestionTypeEnum.values())
                    .filter(value -> value.getName().equals(questionInfo.getQuestionTypeName()))
                    .findAny().orElse(null);

            if (questionTypeEnum == null) {
                return;
            }
            questionInfo.setQuestionType(questionTypeEnum.getValue());
            QuestionImportParser excelQuestionParser = QuestionImportParserManager.build()
                    .createExcelQuestionParser(questionInfo.getQuestionType());
            questionInfo.setAnswer(excelQuestionParser.parseAnswerText(questionInfo.getAnswer()));
            questionInfo.setOptions(excelQuestionParser.parseOptionText(questionInfo.getOptions()));
            questionInfo.setGradeInfoId(gradeInfoId);
            questionInfo.setSchoolType(schoolType);
            questionInfo.setCreateDate(new Date());
            questionInfo.setSubjectId(subjectId);
        });
        super.saveBatch(questionInfoList);
        return questionInfoList.size();
    }


    public List<QuestionInfo> selectByTypes(List<Integer> typeList, Integer gradeInfoId, Integer subjectId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(QuestionInfo.class)
                .select(QuestionInfo::getId, QuestionInfo::getQuestionType)
                .in(QuestionInfo::getQuestionType, typeList)
                .eq(QuestionInfo::getGradeInfoId, gradeInfoId)
                .eq(QuestionInfo::getSubjectId, subjectId);
        return super.list(queryWrapper);
    }

    public List<QuestionInfoDto> getNumberByQuestionTypeGroup(Integer gradeInfoId, Integer subjectId) {
        List<QuestionInfoDto> questionTypeGroup = baseMapper.getNumberByQuestionTypeGroup(gradeInfoId, subjectId);
        questionTypeGroup.forEach(item -> {
            Integer questionTypeCode = item.getQuestionType();
            String name = QuestionTypeEnum.getName(questionTypeCode);
            item.setQuestionTypeName(name);
        });
        if (CollUtil.isEmpty(questionTypeGroup)) {
            throw new BusinessException("该科目暂未上传试题");
        }
        return questionTypeGroup;
    }

    /**
     * 获取试题数据分析
     * @param id
     * @return
     */
    public QuestionInfoDto questionAnalysis(Integer id) {
        QuestionInfoDto questionInfoDto = this.selectById(id);
        Assert.notNull(questionInfoDto, () -> new BusinessException("试题不存在或已被删除"));
        // 获取试题答题次数统计
        int rightNum = examQuestionAnswerService.count(Wrappers.<ExamQuestionAnswer>lambdaQuery()
                .eq(ExamQuestionAnswer::getCorrectStatus, CorrectStatusEnum.RIGHT.getValue())
                .eq(ExamQuestionAnswer::getQuestionInfoId, id));
        questionInfoDto.setRightAnswerNum(rightNum);
        int errorNum = examQuestionAnswerService.count(Wrappers.<ExamQuestionAnswer>lambdaQuery()
                .eq(ExamQuestionAnswer::getCorrectStatus, CorrectStatusEnum.ERROR.getValue())
                .eq(ExamQuestionAnswer::getQuestionInfoId, id)
                        .isNotNull(ExamQuestionAnswer::getStudentAnswer));
        questionInfoDto.setErrorAnswerNum(errorNum);
        int noAnswerNum = examQuestionAnswerService.count(Wrappers.<ExamQuestionAnswer>lambdaQuery()
                .eq(ExamQuestionAnswer::getCorrectStatus, CorrectStatusEnum.ERROR.getValue())
                        .isNull(ExamQuestionAnswer::getStudentAnswer)
                .eq(ExamQuestionAnswer::getQuestionInfoId, id));
        questionInfoDto.setNoAnswerNum(noAnswerNum);
        int totalAnswerNum = examQuestionAnswerService.count(Wrappers.<ExamQuestionAnswer>lambdaQuery()
                .eq(ExamQuestionAnswer::getQuestionInfoId, id));
        questionInfoDto.setTotalAnswerNum(totalAnswerNum);
        return questionInfoDto;
    }
}
