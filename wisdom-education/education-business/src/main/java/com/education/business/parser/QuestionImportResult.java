package com.education.business.parser;

import com.education.model.entity.QuestionInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/16 15:03
 */
public abstract class QuestionImportResult {

    private final InputStream inputStream;
    private MultipartFile file;
    private HttpServletResponse response;

    private String errorFileUrl; // 错误提示文件路径url
    private boolean hasData = true; // 文件是否有内容
    private List<QuestionInfo> successImportQuestionList; // 导入成功试题
    private List<QuestionInfo> failImportQuestionList; // 导入失败试题
    private String errorMsg; // 错误提示信息

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public QuestionImportResult(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public QuestionImportResult(MultipartFile file) throws IOException {
        this.file = file;
        this.inputStream = file.getInputStream();
    }

    public QuestionImportResult(MultipartFile file, HttpServletResponse response) throws IOException {
        this.file = file;
        this.inputStream = file.getInputStream();
        this.response = response;
    }

    public void setErrorFileUrl(String errorFileUrl) {
        this.errorFileUrl = errorFileUrl;
    }

    public String getErrorFileUrl() {
        return errorFileUrl;
    }

    public List<QuestionInfo> getSuccessImportQuestionList() {
        return successImportQuestionList;
    }

    public void setSuccessImportQuestionList(List<QuestionInfo> successImportQuestionList) {
        this.successImportQuestionList = successImportQuestionList;
    }

    public List<QuestionInfo> getFailImportQuestionList() {
        return failImportQuestionList;
    }

    public void setFailImportQuestionList(List<QuestionInfo> failImportQuestionList) {
        this.failImportQuestionList = failImportQuestionList;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public abstract void readTemplate();
}
