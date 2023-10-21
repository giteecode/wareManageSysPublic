package com.education.common.enums;

public enum QuestionTypeEnum {
    SINGLE_QUESTION(1, "单选题"), MULTIPLE_QUESTION(2, "多选题"),
    FILL_QUESTION(3, "填空题"), SYNTHESIS_QUESTION(4, "综合题"),
    INDEFINITE_ITEM_QUESTION(5, "不定项题"),
    CALCULATION_QUESTION(7, "计算题"),
    JUDGMENT_QUESTION(6, "判断题");
    private Integer value;
    private String name;
    QuestionTypeEnum(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getName(Integer type) {
        QuestionTypeEnum questionTypes[] = values();
        for (QuestionTypeEnum questionType : questionTypes) {
            if (questionType.getValue() == type) {
                return questionType.getName();
            }
        }
        return null;
    }

    /***
     * 是否为客观题
     * @param questionType
     * @return
     */
    public static boolean isObjectiveQuestion(Integer questionType) {
        if (SINGLE_QUESTION.getValue().equals(questionType)
                || MULTIPLE_QUESTION.getValue().equals(questionType)
                || JUDGMENT_QUESTION.getValue().equals(questionType)) {
            return true;
        }
        return false;
    }
}