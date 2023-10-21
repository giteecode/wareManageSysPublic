package com.education.model.request;

import com.education.model.entity.QuestionInfo;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 11:38
 */
public class QuestionInfoRequest extends QuestionInfo {

    private List<Options> optionList;


    public void setOptionList(List<Options> optionList) {
        this.optionList = optionList;
    }

    public List<Options> getOptionList() {
        return optionList;
    }

    private class Options {

        private String lable;
        private String value;

        public String getLable() {
            return lable;
        }

        public void setLable(String lable) {
            this.lable = lable;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
