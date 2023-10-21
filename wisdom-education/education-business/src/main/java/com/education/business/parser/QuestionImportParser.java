package com.education.business.parser;

/**
 * excel 试题解析器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:38
 */
public interface QuestionImportParser {

    /**
     * 解析答案
     * @param answer
     * @return
     */
    default String parseAnswerText(String answer){
        return answer;
    }

    /**
     * 解析选项
     * @param option
     * @return
     */
    default String parseOptionText(String option) {
        return option;
    }
}
