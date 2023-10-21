package com.education.business.parser;
import com.education.common.enums.QuestionTypeEnum;

/**
 * 试题解析管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:52
 */
public class QuestionImportParserManager {

    private QuestionImportParser excelQuestionParser;

    private static final QuestionImportParserManager me = new QuestionImportParserManager();

    public static QuestionImportParserManager build() {
        return me;
    }

    public void setExcelQuestionParser(QuestionImportParser excelQuestionParser) {
        this.excelQuestionParser = excelQuestionParser;
    }

    public QuestionImportParser getExcelQuestionParser() {
        return excelQuestionParser;
    }

    private QuestionImportParserManager() {

    }

    public QuestionImportParser createExcelQuestionParser(Integer questionType) {
        if (this.getExcelQuestionParser() != null) {
            return getExcelQuestionParser();
        }

        if (questionType == QuestionTypeEnum.SINGLE_QUESTION.getValue()
           || questionType == QuestionTypeEnum.MULTIPLE_QUESTION.getValue()) {
            return new SelectQuestionParser();
        } else if (questionType == QuestionTypeEnum.JUDGMENT_QUESTION.getValue()) {
            return new JudgmentQuestionParser();
        } else if (questionType == QuestionTypeEnum.FILL_QUESTION.getValue()) {
            return new ExerciseSubjectiveQuestionParser();
        }
        return new DefaultQuestionParser();
    }
}
