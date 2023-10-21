package com.education.business.service.education;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.ExamQuestionAnswerMapper;
import com.education.business.service.BaseService;
import com.education.common.enums.CorrectStatusEnum;
import com.education.common.model.PageInfo;
import com.education.model.dto.ExamQuestionAnswerDto;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:21
 */
@Service
public class ExamQuestionAnswerService extends BaseService<ExamQuestionAnswerMapper, ExamQuestionAnswer> {

    /**
     * 获取考试试题及学员试题答案
     * @return
     */
    public List<QuestionInfoAnswer> getQuestionAnswerByExamInfoId(Integer studentId, Integer examInfoId) {
        return baseMapper.selectQuestionAnswerList(studentId, examInfoId);
    }

    /**
     * 删除学员考试未批改的答题记录
     * @param studentId
     * @param examInfoId
     * @return
     */
    public boolean deleteByExamInfoId(Integer studentId, Integer examInfoId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(ExamQuestionAnswer.class)
                .eq(ExamQuestionAnswer::getStudentId, studentId)
                .eq(ExamQuestionAnswer::getCorrectStatus, CorrectStatusEnum.CORRECT_RUNNING.getValue())
                .eq(ExamQuestionAnswer::getExamInfoId, examInfoId);
        return super.remove(queryWrapper);
    }

    public ExamQuestionAnswer selectByQuestionInfoId(Integer questionInfoId) {
        LambdaQueryWrapper queryWrapper = Wrappers.<ExamQuestionAnswer>lambdaQuery()
                .eq(ExamQuestionAnswer::getQuestionInfoId, questionInfoId)
                .last(" limit 1");
        return super.getOne(queryWrapper);
    }

    public PageInfo listPageByQuestionId(Integer questionId, PageParam pageParam) {
        Page<ExamQuestionAnswerDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        Page<ExamQuestionAnswerDto> result = baseMapper.listPageByQuestionId(page, questionId);
        if (CollUtil.isNotEmpty(result.getRecords())) {
            result.getRecords().forEach(item -> {
                CorrectStatusEnum correctStatusEnum = CorrectStatusEnum.getByCode(item.getCorrectStatus());
                item.setCorrectStatusEnum(correctStatusEnum);
            });
        }
        return selectPage(result);
    }
}
