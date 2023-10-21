package com.education.model.response;

import com.education.model.dto.QuestionInfoAnswer;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 15:44
 */
public class QuestionGroupItemResponse {

    private String questionTypeName;
    private List<QuestionInfoAnswer> questionInfoAnswerList;

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public List<QuestionInfoAnswer> getQuestionInfoAnswerList() {
        return questionInfoAnswerList;
    }

    public void setQuestionInfoAnswerList(List<QuestionInfoAnswer> questionInfoAnswerList) {
        this.questionInfoAnswerList = questionInfoAnswerList;
    }
}
