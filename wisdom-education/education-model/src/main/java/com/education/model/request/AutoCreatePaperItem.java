package com.education.model.request;


/**
 * @author zjt
 * @create_at 2022年4月2日 0002 15:51
 * @since 1.6.11
 */
public class AutoCreatePaperItem {

    /**
     * 试题类型
     */
    private Integer questionType;

    /**
     * 试题类型对应的试题数量
     */
    private Integer questionNumber;

    /**
     * 试题分数
     */
    private Integer questionMark;

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(Integer questionMark) {
        this.questionMark = questionMark;
    }
}
