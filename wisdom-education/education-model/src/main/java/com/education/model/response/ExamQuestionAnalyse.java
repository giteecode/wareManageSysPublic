package com.education.model.response;

import com.education.common.enums.QuestionTypeEnum;
import java.math.BigDecimal;

/**
 * 考试试题分析
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/5 16:41
 */
public class ExamQuestionAnalyse {

    /**
     * 题干
     */
    private String content;

    /**
     * 试题类型
     */
    private Integer questionType;

    /**
     * 试题类型枚举
     */
    private QuestionTypeEnum questionTypeEnum;

    /**
     * 答对次数
     */
    private String rightAnswerNum;

    /**
     * 答错次数
     */
    private Integer errorAnswer;

    /**
     * 总答题数
     */
    private Integer answerNum;

    /**
     * 其它（针对主观题）
     */
    private Integer otherAnswerNum;

    /**
     * 正确率
     */
    private BigDecimal rightRate;

    /**
     * 错误率
     */
    private BigDecimal errorRate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRightAnswerNum() {
        return rightAnswerNum;
    }

    public void setRightAnswerNum(String rightAnswerNum) {
        this.rightAnswerNum = rightAnswerNum;
    }

    public Integer getErrorAnswer() {
        return errorAnswer;
    }

    public void setErrorAnswer(Integer errorAnswer) {
        this.errorAnswer = errorAnswer;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public Integer getOtherAnswerNum() {
        return otherAnswerNum;
    }

    public void setOtherAnswerNum(Integer otherAnswerNum) {
        this.otherAnswerNum = otherAnswerNum;
    }

    public BigDecimal getRightRate() {
        return rightRate;
    }

    public void setRightRate(BigDecimal rightRate) {
        this.rightRate = rightRate;
    }

    public BigDecimal getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(BigDecimal errorRate) {
        this.errorRate = errorRate;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public QuestionTypeEnum getQuestionTypeEnum() {
        return questionTypeEnum;
    }

    public void setQuestionTypeEnum(QuestionTypeEnum questionTypeEnum) {
        this.questionTypeEnum = questionTypeEnum;
    }
}
