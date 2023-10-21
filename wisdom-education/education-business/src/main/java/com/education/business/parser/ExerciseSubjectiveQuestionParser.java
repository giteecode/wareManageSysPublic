package com.education.business.parser;

import cn.hutool.core.util.StrUtil;

/**
 * excel 填空题解析器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:44
 */
public class ExerciseSubjectiveQuestionParser extends DefaultQuestionParser {

    @Override
    public String parseAnswerText(String answer) {
        if (StrUtil.isBlank(answer)) {
            return null;
        }
        String[] answerArray = super.parserToken(answer);
        StringBuilder answerContent = new StringBuilder();
        for (int i = 0; i < answerArray.length; i++) {
            answerContent.append("(")
                    .append(i + 1)
                    .append(")、")
                    .append(answerArray[i])
                    .append(" ");
        }
        return answerContent.toString();
    }
}
