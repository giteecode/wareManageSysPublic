package com.education.business.parser;

import cn.afterturn.easypoi.util.PoiValidationUtil;
import com.education.common.enums.QuestionTypeEnum;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.TxtQuestionInfo;
import com.education.model.entity.QuestionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析txt 试题 模板导入
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/16 14:43
 */
public class TxtQuestionImportResult extends QuestionImportResult {

    private static final String QUESTION_CONTENT = "[题干]";
    private static final String QUESTION_TYPE_VALUE = "[类型]";
    private static final String QUESTION_ANSWER = "[答案]";
    private static final String QUESTION_ANALYSIS = "[解析]";
    private static final String QUESTION_OPTIONS = "[选项]";
    private static final int TITLE_LENGTH = "[题干]".length();

    private final Logger logger = LoggerFactory.getLogger(TxtQuestionImportResult.class);

    public TxtQuestionImportResult(InputStream inputStream) {
        super(inputStream);
    }

    public TxtQuestionImportResult(MultipartFile file) throws IOException {
        super(file);
    }

    @Override
    public void readTemplate() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(super.getInputStream()));
        String lineContent = null;
        List<QuestionInfo> questionInfoList = new ArrayList();
        List<QuestionInfo> failImportQuestionList = new ArrayList<>();
        List<TxtQuestionInfo> errorTxtQuestionList = new ArrayList<>();
        try {
            QuestionInfo questionInfo = null;
            Integer questionType;

            boolean hasData = false; // txt 文档是否有内容

            int readType = 0;
            int questionInfoIndex = 0; // 记录上道试题的索引下标
            while ((lineContent = reader.readLine()) != null) {
                if (ObjectUtils.isEmpty(lineContent)) {
                    continue;
                }

                if (!hasData) {
                    hasData = true;
                }

                String tokenStart = lineContent.substring(0, TITLE_LENGTH);
                String content = lineContent.substring(TITLE_LENGTH, lineContent.length());

                // 解析题干
                if (tokenStart.startsWith(QUESTION_CONTENT)) {
                    questionInfo = new QuestionInfo();
                    questionInfo.setContent(content);
                    readType = 1;
                    // 解析试题类型
                } else if (tokenStart.startsWith(QUESTION_TYPE_VALUE)) {
                    for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
                        if (item.getName().equals(content)) {
                            questionType = item.getValue();
                            questionInfo.setQuestionType(questionType);
                            questionInfo.setQuestionTypeName(item.getName());
                            break;
                        }
                    }
                    readType = 2;
                } else if (tokenStart.startsWith(QUESTION_ANSWER)) {  // 解析试题答案
                    questionInfo.setAnswer(content);
                    readType = 3;
                } else if (tokenStart.startsWith(QUESTION_OPTIONS)) { // 解析试题选项
                    String options;
                    if (ObjectUtils.isEmpty(content)) {
                        options = null; // 将options 设置为null, 防止选项为空导致插入数据库失败，因为mysql json 类型不支持字符串""
                    } else {
                        options = content;
                    }
                    questionInfo.setOptions(options);
                    readType = 4;
                } else if (tokenStart.startsWith(QUESTION_ANALYSIS)) {
                    questionInfo.setAnalysis(content);
                    readType = 5;
                    String errorMsg = verificationContent(questionInfo);
                    if (ObjectUtils.isEmpty(errorMsg)) {
                        questionInfoList.add(questionInfo);
                    }
                    else {
                        TxtQuestionInfo txtQuestionInfo = new TxtQuestionInfo();
                        txtQuestionInfo.setContent(questionInfo.getContent());
                        txtQuestionInfo.setAnalysis(questionInfo.getAnalysis());
                        txtQuestionInfo.setErrorMsg(errorMsg);
                        txtQuestionInfo.setAnswer(questionInfo.getAnswer());
                        txtQuestionInfo.setQuestionTypeName(questionInfo.getQuestionTypeName());
                        errorTxtQuestionList.add(txtQuestionInfo);
                        failImportQuestionList.add(questionInfo);
                    }
                } else { // 出现内容换行情况
                    switch (readType) { // 判断上一行内容的类型，然后进行内容拼接
                        case 1:
                            questionInfo.setContent(questionInfo.getContent() + content);
                            break;
                        case 3:
                            questionInfo.setAnswer(questionInfo.getAnswer() + content);
                            break;
                        case 4:
                            questionInfo.setOptions(questionInfo.getOptions() + content);
                            break;
                        case 5:
                            questionInfoList.remove(questionInfoIndex);
                            questionInfo.setAnalysis(questionInfo.getAnalysis() + content);
                            questionInfoList.add(questionInfo);
                    }
                }
            }

            if (!hasData) {
                setErrorMsg("txt 文件内容为空，请先添加试题");
                setHasData(false);
            } else {
                super.setSuccessImportQuestionList(questionInfoList);
                super.setFailImportQuestionList(failImportQuestionList);
                if (failImportQuestionList.size() > 0) {
                    this.createErrorTxtFile(errorTxtQuestionList);
                }
            }
        } catch (Exception e) {
            logger.error("试题导入异常, 请检查txt 内容数据是否有换行", e);
        }
    }

    private String verificationContent(QuestionInfo questionInfo) {
        return PoiValidationUtil.validation(questionInfo, null);
    }

    /**
     * 创建错误文件提示
     * @param failImportQuestionList
     */
    private String createErrorTxtFile(List<TxtQuestionInfo> failImportQuestionList) {
       // StringBuilder txtContent = new StringBuilder();
        return null;
    }
}
