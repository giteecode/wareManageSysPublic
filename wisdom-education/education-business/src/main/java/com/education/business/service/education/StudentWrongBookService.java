package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.StudentWrongBookMapper;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.common.enums.QuestionTypeEnum;
import com.education.common.enums.WrongBooBizType;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.dto.StudentWrongBookDto;
import com.education.model.entity.ExamWrongBook;
import com.education.model.entity.SubjectInfo;
import com.education.model.request.PageParam;
import com.education.model.request.WrongBookQuery;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 错题本管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:24
 */
@Service
public class StudentWrongBookService extends BaseService<StudentWrongBookMapper, ExamWrongBook> {

    /**
     * 错题本列表
     * @param pageParam
     * @param wrongBookQuery
     * @return
     */
    public PageInfo<QuestionInfoAnswer> selectPageList(PageParam pageParam, WrongBookQuery wrongBookQuery) {
        Page<QuestionInfoAnswer> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        PageInfo<QuestionInfoAnswer> pageInfo = selectPage(baseMapper.selectPageList(page, wrongBookQuery));
        if (ObjectUtils.isNotEmpty(pageInfo.getDataList())) {
            pageInfo.getDataList().forEach(item -> {
                item.setQuestionTypeName(QuestionTypeEnum.getName(item.getQuestionType()));
            });
        }
        return pageInfo;
    }

    /**
     * 获取错题本科目列表
     * @return
     */
    public List<SubjectInfo> getSubjectList(Integer type) {
        if (WrongBooBizType.PAPER_QUESTION.getValue().equals(type)) {
            return baseMapper.getTestPaperSubjectList(UserSessionContext.getStudentId());
        }
        throw new BusinessException("非法请求!");
    }

    /**
     * 获取错题数量
     * @return
     */
    public Integer questionNumber() {
        LambdaQueryWrapper<ExamWrongBook> queryWrapper = Wrappers.<ExamWrongBook>lambdaQuery()
                .eq(ExamWrongBook::getStudentId, UserSessionContext.getStudentId());
        return count(queryWrapper);
    }

    /**
     * 获取错题本科目试卷
     * @param subjectId
     * @param pageParam
     * @return
     */
    public PageInfo<StudentWrongBookDto> getTestPaperList(Integer subjectId, PageParam pageParam) {
        Page<QuestionInfoAnswer> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.getTestPaperListBySubjectId(page, UserSessionContext.getStudentId(), subjectId));
    }
}
