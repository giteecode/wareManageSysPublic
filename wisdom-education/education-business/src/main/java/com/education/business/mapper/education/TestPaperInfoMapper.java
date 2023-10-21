package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.TestPaperInfo;
import com.education.model.request.TestPaperQuestionRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:27
 */
public interface TestPaperInfoMapper extends BaseMapper<TestPaperInfo> {

    /**
     * 试卷分列表
     * @param page
     * @param testPaperInfo
     * @return
     */
    Page<TestPaperInfoDto> selectPageList(Page<TestPaperInfoDto> page, TestPaperInfo testPaperInfo);

    /**
     * 试卷试题分页列表
     * @param page
     * @param testPaperQuestionRequest
     * @return
     */
    Page<TestPaperQuestionDto> selectPaperQuestionList(Page<TestPaperQuestionDto> page, TestPaperQuestionRequest testPaperQuestionRequest);


    /**
     * 试卷试题列表
     * @param testPaperQuestionRequest
     * @return
     */
    List<TestPaperQuestionDto> selectPaperQuestionList(@Param("testPaperQuestionRequest") TestPaperQuestionRequest testPaperQuestionRequest);

    /**
     * 更新试卷考试人数
     * @param id
     * @param examNumber
     * @return
     */
    @Update("update test_paper_info set exam_number = exam_number + #{examNumber} where id = #{id}")
    boolean updateTestPaperExamNumber(@Param("id") Integer id, @Param("examNumber") Integer examNumber);

    /**
     * 更新试卷试题数量
     * @param testPaperInfoId
     * @param questionNumber
     */
    @Update("update test_paper_info set question_number = question_number + #{questionNumber} where id = #{id}")
    void updateQuestionNumber(@Param("id") Integer testPaperInfoId, @Param("questionNumber") Integer questionNumber);

    /**
     * 更新试卷总分
     * @param id
     * @param addMark
     * @param subtractMark
     */
    @Update("update test_paper_info set mark = mark + #{addMark}, mark = mark - #{subtractMark} where id = #{id}")
    void updateMark(@Param("id") Integer id, @Param("addMark") Integer addMark, @Param("subtractMark") Integer subtractMark);

    /**
     * 更新试卷分数及试题数量
     * @param id
     * @param mark
     * @param questionNumber
     */
    @Update("update test_paper_info set mark = mark - #{mark}, question_number = question_number - #{questionNumber} where id = #{id}")
    void updateMarkAndQuestionNumber(@Param("id") Integer id, @Param("mark") Integer mark, @Param("questionNumber") Integer questionNumber);


    /**
     * 试卷试题分析
     * @param id
     * @return
     */
    Page<QuestionInfoDto> getQuestionAnalysis(Page<QuestionInfoDto> page, @Param("id") Integer id);
}
