package com.education.model.request;

import com.education.model.entity.QuestionInfo;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 15:25
 */
public class QuestionInfoQuery extends QuestionInfo {

    private List<Integer> questionIds;

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }
}
