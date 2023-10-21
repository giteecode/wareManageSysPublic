package com.education.model.request;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:47
 */
public class QuestionAnswer {

    private Integer questionInfoId;
    private Integer questionMark; // 试题分数
    private Integer studentMark; // 学员试题得分
  //  private String answer; // 试题答案
    private String studentAnswer; // 学员答案
    private Integer questionType; // 试题类型
    private String comment; // 评语
    private Integer correctStatus; // 批改状态
    private boolean errorQuestionFlag; // 是否加入错题本

    public Integer getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(Integer studentMark) {
        this.studentMark = studentMark;
    }

    public boolean isErrorQuestionFlag() {
        return errorQuestionFlag;
    }

    public void setErrorQuestionFlag(boolean errorQuestionFlag) {
        this.errorQuestionFlag = errorQuestionFlag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public Integer getQuestionInfoId() {
        return questionInfoId;
    }

    public void setQuestionInfoId(Integer questionInfoId) {
        this.questionInfoId = questionInfoId;
    }

    public Integer getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(Integer questionMark) {
        this.questionMark = questionMark;
    }

 /*   public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }*/

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}
