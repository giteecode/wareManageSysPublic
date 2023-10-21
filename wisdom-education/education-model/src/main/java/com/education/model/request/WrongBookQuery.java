package com.education.model.request;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/5 14:41
 */
public class WrongBookQuery {

    private Integer studentId;
    private Integer subjectId;
    private Integer questionType;

    /**
     * 错题业务id
     */
    private Integer bizId;

    /**
     * 错题业务类型
     */
    private Integer bizType;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }
}
