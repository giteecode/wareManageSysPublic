package com.education.business.parser;

import java.io.InputStream;

/**
 * word 模板试题导入
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/16 15:33
 */
public class WordQuestionImportResult extends QuestionImportResult {

    public WordQuestionImportResult(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public void readTemplate() {
        throw new RuntimeException("Word Template Import is Not Finish");
    }
}
