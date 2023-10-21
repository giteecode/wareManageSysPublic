package com.education.model.request;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 15:14
 */
public class TestPaperQuestionRequest {
    private Integer testPaperInfoId;
    private Integer questionType;
    private String content;
    private List<Integer> questionInfoIds;

    private boolean addExamMonitor = true; // 是否加入考试监考中心

    public void setAddExamMonitor(boolean addExamMonitor) {
        this.addExamMonitor = addExamMonitor;
    }

    public boolean isAddExamMonitor() {
        return addExamMonitor;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getQuestionInfoIds() {
        return questionInfoIds;
    }

    public void setQuestionInfoIds(List<Integer> questionInfoIds) {
        this.questionInfoIds = questionInfoIds;
    }

    public Integer getTestPaperInfoId() {
        return testPaperInfoId;
    }

    public void setTestPaperInfoId(Integer testPaperInfoId) {
        this.testPaperInfoId = testPaperInfoId;
    }
}
