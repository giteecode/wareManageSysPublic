package com.education.business.parser;

import com.education.common.utils.ObjectUtils;
import com.jfinal.json.Jackson;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:48
 */
public abstract class AbstractExcelQuestionParser implements QuestionImportParser {

    protected final Jackson jackson = new Jackson();

    protected String openToken = "${";
    protected String endToken = "}";

    public void setOpenToken(String openToken) {
        this.openToken = openToken;
    }

    public void setEndToken(String endToken) {
        this.endToken = endToken;
    }

    public String getOpenToken() {
        return openToken;
    }

   protected String[] parserToken(String text) {
       String[] answerArray = null;
        if (text.startsWith(openToken) && text.endsWith(endToken)) {
            answerArray = ObjectUtils.spilt(text);
            for (int i = 0; i < answerArray.length; i++) {
                String item = answerArray[i].trim();
                int length = item.length();
                answerArray[i] = item.substring(openToken.length(), length - endToken.length());
            }
        }
        return answerArray;
    }
}
