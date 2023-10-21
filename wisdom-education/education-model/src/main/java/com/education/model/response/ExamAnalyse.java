package com.education.model.response;

import com.education.common.utils.DateUtils;
import com.education.common.utils.NumberUtils;

/**
 * 考试分析
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/5 16:41
 */
public class ExamAnalyse {

    /**
     * 试卷名称
     */
    private String testPaperInfoName;
    /**
     * 考试科目
     */
    private String subjectName;

    /**
     * 考试用时
     */
    private Integer examTime;

    /**
     * 参考人数
     */
    private Integer examNumber;

    /**
     * 总分
     */
    private Integer examMark;

    /**
     * 平均分
     */
    private double avgExamMark;

    /**
     * 及格分数
     */
    private double passExamMark;

    /**
     * 优秀分数线
     */
    private double niceExamMark;

    /**
     * 及格人数
     */
    private Integer passExamNumber;

    /**
     * 优秀人数
     */
    private Integer niceExamNumber;

    /**
     * 考试最低分
     */
    private Integer minExamMark;

    /**
     * 考试最高分
     */
    private Integer maxExamMark;

    /**
     * 及格率
     */
    private Float passRate;

    private String passRateStr;

    /**
     * 平均用时
     */
    private Long avgExamTime;

    /**
     * 平均用时
     */
    private String avgExamTimeStr;

    public void setAvgExamTime(long avgExamTime) {
        this.avgExamTime = avgExamTime;
    }

    public String getAvgExamTimeStr() {
        if (this.avgExamTime != null)
           return DateUtils.secondToHourMinute(this.avgExamTime);
        return this.avgExamTimeStr;
    }

    public void setAvgExamTimeStr(String avgExamTimeStr) {
        this.avgExamTimeStr = avgExamTimeStr;
    }

    public String getTestPaperInfoName() {
        return testPaperInfoName;
    }

    public void setTestPaperInfoName(String testPaperInfoName) {
        this.testPaperInfoName = testPaperInfoName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getAvgExamMark() {
        return avgExamMark;
    }

    public void setAvgExamMark(double avgExamMark) {
        this.avgExamMark = avgExamMark;
    }

    public double getPassExamMark() {
        return passExamMark;
    }

    public void setPassExamMark(double passExamMark) {
        this.passExamMark = passExamMark;
    }

    public double getNiceExamMark() {
        return niceExamMark;
    }

    public void setNiceExamMark(double niceExamMark) {
        this.niceExamMark = niceExamMark;
    }

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    public Integer getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(Integer examNumber) {
        this.examNumber = examNumber;
    }

    public Integer getExamMark() {
        return examMark;
    }

    public void setExamMark(Integer examMark) {
        this.examMark = examMark;
    }



    public void setNiceExamMark(Integer niceExamMark) {
        this.niceExamMark = niceExamMark;
    }

    public Integer getPassExamNumber() {
        return passExamNumber;
    }

    public void setPassExamNumber(Integer passExamNumber) {
        this.passExamNumber = passExamNumber;
    }

    public Integer getNiceExamNumber() {
        return niceExamNumber;
    }

    public void setNiceExamNumber(Integer niceExamNumber) {
        this.niceExamNumber = niceExamNumber;
    }

    public Integer getMinExamMark() {
        return minExamMark;
    }

    public String getPassRateStr() {
        return passRateStr;
    }

    public void setPassRateStr(String passRateStr) {
        this.passRateStr = passRateStr;
    }

    public void setMinExamMark(Integer minExamMark) {
        this.minExamMark = minExamMark;
    }

    public Integer getMaxExamMark() {
        return maxExamMark;
    }

    public void setMaxExamMark(Integer maxExamMark) {
        this.maxExamMark = maxExamMark;
    }

    public Float getPassRate() {
        return passRate;
    }

    public void setPassRate(float passRate) {
        this.passRate = passRate;
    }

    public Long getAvgExamTime() {
        return avgExamTime;
    }
}
