package com.education.model.dto;

import com.education.model.entity.QuestionInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 15:34
 */
public class QuestionInfoAnswer extends QuestionInfo {

    /**
     * 学员答案
     */
    private String studentAnswer;

    /**
     * 得分
     */
    private Integer studentMark;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 试题类型
     */
    private String questionTypeName;

    /**
     * 试题分数
     */
    private Integer questionMark;


    /**
     * 批改状态
     * @see com.education.common.enums.CorrectStatusEnum
     */
    private Integer correctStatus;

    /**
     * 教师评语
     */
    private String comment;


    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Integer getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(Integer studentMark) {
        this.studentMark = studentMark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public Integer getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(Integer questionMark) {
        this.questionMark = questionMark;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
