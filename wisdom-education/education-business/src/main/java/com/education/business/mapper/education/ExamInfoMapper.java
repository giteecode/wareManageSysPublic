package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.ExamCount;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.SubjectInfo;
import com.education.model.entity.TestPaperInfo;
import com.education.model.response.ExamAnalyse;
import com.education.model.response.ExamRanking;
import com.education.model.response.TestPaperInfoReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:11
 */
public interface ExamInfoMapper extends BaseMapper<ExamInfo> {

    /**
     * 学员考试记录列表
     * @param page
     * @param studentExamInfoDto
     * @return
     */
    Page<StudentExamInfoDto> selectStudentExamList(Page<StudentExamInfoDto> page, StudentExamInfoDto studentExamInfoDto);


    /**
     * 考试列表
     * @param page
     * @param studentExamInfoDto
     * @return
     */
    Page<StudentExamInfoDto> selectExamList(Page<StudentExamInfoDto> page, StudentExamInfoDto studentExamInfoDto);


    StudentExamInfoDto selectById(@Param("id") Integer id);

    /**
     * 考试统计
     * @param page
     * @param testPaperInfo
     * @return
     */
    Page<TestPaperInfoReport> selectExamReportList(Page<TestPaperInfoReport> page, TestPaperInfo testPaperInfo);

    /**
     * 考试成绩列表
     * @param testPaperInfoId
     * @return
     */
    Page<ExamRanking> selectExamListByTestPaperInfoId(Page<ExamRanking> page, @Param("testPaperInfoId") Integer testPaperInfoId);


    /**
     * 考试详细分析
     * @param params
     * @return
     */
    ExamAnalyse selectExamInfoDetail(Map params);

    /**
     * 按试卷段进行数据统计
     * @param startTime
     * @param endTime
     * @return
     */
    List<ExamCount> countByDateTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 获取考试记录科目
     * @return
     */
    List<SubjectInfo> getSubjectList();
}
