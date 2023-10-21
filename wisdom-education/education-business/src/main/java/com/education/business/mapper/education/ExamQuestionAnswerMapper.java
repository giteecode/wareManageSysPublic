package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.ExamQuestionAnswerDto;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.entity.ExamQuestionAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:21
 */
public interface ExamQuestionAnswerMapper extends BaseMapper<ExamQuestionAnswer> {

    /**
     * 获取学员考试答题记录
     * @param studentId
     * @param examInfoId
     * @return
     */
    List<QuestionInfoAnswer> selectQuestionAnswerList(@Param("studentId") Integer studentId,
                                                      @Param("examInfoId") Integer examInfoId);


    /**
     * 试题作答记录列表
     * @param page
     * @param questionId
     * @return
     */
    Page<ExamQuestionAnswerDto> listPageByQuestionId(Page<ExamQuestionAnswerDto> page, Integer questionId);
}
