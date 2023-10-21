package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.dto.StudentWrongBookDto;
import com.education.model.entity.ExamWrongBook;
import com.education.model.entity.SubjectInfo;
import com.education.model.request.WrongBookQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:26
 */
public interface StudentWrongBookMapper extends BaseMapper<ExamWrongBook> {

    /**
     * 学员错题本列表
     * @param page
     * @param wrongBookQuery
     * @return
     */
    Page<QuestionInfoAnswer> selectPageList(Page<QuestionInfoAnswer> page, WrongBookQuery wrongBookQuery);

    /**
     * 获取试卷错题本科目列表
     * @return
     */
    List<SubjectInfo> getTestPaperSubjectList(@Param("studentId") Integer studentId);

    /**
     * 根据科目id获取错题本试卷列表
     * @param page
     * @param subjectId
     * @return
     */
    Page<StudentWrongBookDto> getTestPaperListBySubjectId(Page<QuestionInfoAnswer> page, @Param("studentId") Integer studentId, @Param("subjectId") Integer subjectId);
}
