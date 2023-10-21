package com.education.model.dto;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/6/27 15:45
 */
public class QuestionOption {

    /**
     * 试题选项标签
     */
    private String label;

    /**
     * 选项内容
     */
    private String optionName;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
