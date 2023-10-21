package com.education.model.request;


import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:15
 */
public class StudentQuestionRequest {

    private Integer testPaperInfoId;
    private Integer examInfoId;
    private long examTime;
    private boolean teacherCorrectFlag; // 是否教师批改
    private List<QuestionAnswer> questionAnswerList;
    private Integer studentId;

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getTestPaperInfoId() {
        return testPaperInfoId;
    }

    public void setTestPaperInfoId(Integer testPaperInfoId) {
        this.testPaperInfoId = testPaperInfoId;
    }

    public void setExamTime(long examTime) {
        this.examTime = examTime;
    }

    public boolean isTeacherCorrectFlag() {
        return teacherCorrectFlag;
    }

    public void setTeacherCorrectFlag(boolean teacherCorrectFlag) {
        this.teacherCorrectFlag = teacherCorrectFlag;
    }

    public Integer getExamInfoId() {
        return examInfoId;
    }

    public void setExamInfoId(Integer examInfoId) {
        this.examInfoId = examInfoId;
    }

    public long getExamTime() {
        return examTime;
    }

    public void setQuestionAnswerList(List<QuestionAnswer> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }

    public List<QuestionAnswer> getQuestionAnswerList() {
        return questionAnswerList;
    }
}
