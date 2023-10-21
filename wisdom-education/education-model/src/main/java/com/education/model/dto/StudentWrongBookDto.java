package com.education.model.dto;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/4 18:40
 */
public class StudentWrongBookDto {

    private Integer questionInfoId;
    private String questionTypeName;
    private Integer questionType;
    private String content;
    private String options;
    private String analysis;
    private String subjectName;
    private Integer subjectId;

    /**
     * 得分
     */
    private Integer mark;

    /**
     * 试题分数
     */
    private Integer questionMark;

    /**
     * 正确答案
     */
    private String answer;

    /**
     * 学员答案
     */
    private String studentAnswer;

    /**
     * 业务类型对应的错题数量
     */
    private Integer bizQuestionNumber;

    /**
     * 业务类型对应的具体名称（包括试卷、课后训练等）
     */
    private String bizName;

    /**
     * 业务id
     */
    private Integer bizId;

    public Integer getBizQuestionNumber() {
        return bizQuestionNumber;
    }

    public void setBizQuestionNumber(Integer bizQuestionNumber) {
        this.bizQuestionNumber = bizQuestionNumber;
    }

    public Integer getQuestionInfoId() {
        return questionInfoId;
    }


    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public void setQuestionInfoId(Integer questionInfoId) {
        this.questionInfoId = questionInfoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(Integer questionMark) {
        this.questionMark = questionMark;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }
}
