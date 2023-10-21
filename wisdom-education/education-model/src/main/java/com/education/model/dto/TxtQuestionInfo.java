package com.education.model.dto;

import com.education.model.entity.QuestionInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/19 21:03
 */
public class TxtQuestionInfo extends QuestionInfo {

    private String errorMsg;

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
