package com.education.model.dto;

import com.education.model.entity.ExamInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 13:28
 */
public class StudentExamInfoDto extends ExamInfo {

    private Integer subjectId;
    private Integer gradeInfoId;
    private String subjectName;
    private String testPaperInfoName;
    private Integer questionNumber;
    private String startTime;
    private String studentName;
    private String gradeName;
    private String endTime;
    private Integer testPaperExamTime; // 试卷考试时间

    private Integer testPaperInfoMark;

    public Integer getGradeInfoId() {
        return gradeInfoId;
    }

    public void setGradeInfoId(Integer gradeInfoId) {
        this.gradeInfoId = gradeInfoId;
    }

    public void setTestPaperInfoMark(Integer testPaperInfoMark) {
        this.testPaperInfoMark = testPaperInfoMark;
    }

    public Integer getTestPaperInfoMark() {
        return testPaperInfoMark;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setTestPaperExamTime(Integer testPaperExamTime) {
        this.testPaperExamTime = testPaperExamTime;
    }

    public Integer getTestPaperExamTime() {
        return testPaperExamTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTestPaperInfoName() {
        return testPaperInfoName;
    }

    public void setTestPaperInfoName(String testPaperInfoName) {
        this.testPaperInfoName = testPaperInfoName;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
