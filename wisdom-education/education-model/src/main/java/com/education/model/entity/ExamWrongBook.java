package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 学员错题本实体类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:24
 */
@TableName("exam_wrong_book")
public class ExamWrongBook extends BaseEntity<ExamWrongBook> {

    /**
     * 学员id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 试题id
     */
    @TableField("question_info_id")
    private Integer questionInfoId;

    /**
     * 试题分数
     */
    @TableField("question_mark")
    private Integer questionMark;

    /**
     * 学员答案
     */
    @TableField("student_answer")
    private String studentAnswer;

    /**
     * 批改状态 0 错误 1 正确 2 待批改 3.已批改（针对主观题）
     * @see com.education.common.enums.CorrectStatusEnum
     */
    @TableField("correct_status")
    private Integer correctStatus;


    /**
     * 考试记录id
     */
    private Integer examInfoId;

    public ExamWrongBook() {

    }

    public Integer getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(Integer questionMark) {
        this.questionMark = questionMark;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public ExamWrongBook(Integer studentId, Integer questionInfoId, Integer questionMark) {
        this.studentId = studentId;
        this.questionInfoId = questionInfoId;
        this.questionMark = questionMark;
        this.createDate = new Date();
    }

    public Integer getQuestionInfoId() {
        return questionInfoId;
    }

    public void setQuestionInfoId(Integer questionInfoId) {
        this.questionInfoId = questionInfoId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getExamInfoId() {
        return examInfoId;
    }

    public void setExamInfoId(Integer examInfoId) {
        this.examInfoId = examInfoId;
    }
}
