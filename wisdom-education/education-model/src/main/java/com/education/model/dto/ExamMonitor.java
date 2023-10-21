package com.education.model.dto;

import java.io.Serializable;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/29 16:26
 */
public class ExamMonitor implements Serializable {

    /**
     * 已答题
     */
    private int answerQuestionCount;

    /**
     * 试题数量
     */
    private int questionCount;
    /**
     * 学员id
     */
    private Integer studentId;

    /**
     * 答题设备
     */
    private String device;

    /**
     * 考试开始时间
     */
    private String startExamTime;

    /**
     * 试卷id
     */
    private Integer testPaperInfoId;

    /**
     * 学员姓名
     */
    private String name;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 答题进度
     */
    private String rateStr;

    /**
     * ip
     */
    private String ip;

    /**
     * ip地址
     */
    private String ipAddress;

    public int getAnswerQuestionCount() {
        return answerQuestionCount;
    }

    public String getRateStr() {
        return answerQuestionCount + "/" + questionCount;
    }

    public void setAnswerQuestionCount(int answerQuestionCount) {
        this.answerQuestionCount = answerQuestionCount;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getStartExamTime() {
        return startExamTime;
    }

    public Integer getTestPaperInfoId() {
        return testPaperInfoId;
    }

    public void setTestPaperInfoId(Integer testPaperInfoId) {
        this.testPaperInfoId = testPaperInfoId;
    }

    public void setStartExamTime(String startExamTime) {
        this.startExamTime = startExamTime;
    }

    public void setRateStr(String rateStr) {
        this.rateStr = rateStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
