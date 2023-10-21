package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 试卷设置表实体类
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/30 21:16
 */
@TableName("test_paper_info_setting")
public class TestPaperInfoSetting extends BaseEntity<TestPaperInfoSetting> {

    /**
     * 试卷id
     */
    @TableField("test_paper_info_id")
    @NotNull
    private Integer testPaperInfoId;

    /**
     * 提交后是否立即出分
     * 1. 批改后出分
     * 2. 立即显示分数
     */
    @TableField("commit_after_type")
    private Integer commitAfterType;


    /**
     * 是否显示学员成绩排名
     */
    @TableField("show_student_sort")
    private Integer showStudentSort;

    /**
     * 考试开始时间
     */
    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    /**
     * 考试结束时间
     */
    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;

    /**
     * 1.指定时间段 2. 永久有效
     */
    @TableField("exam_type")
    private Integer examType;

    /**
     * 参考次数
     */
    @TableField("reference_number")
    private Integer referenceNumber;

    /**
     * 答卷次数类型(1. 1次 2.不限次数 3. 自定义)
     */
    @TableField("reference_type")
    private Integer referenceType;

    public Integer getTestPaperInfoId() {
        return testPaperInfoId;
    }

    public void setTestPaperInfoId(Integer testPaperInfoId) {
        this.testPaperInfoId = testPaperInfoId;
    }

    public Integer getCommitAfterType() {
        return commitAfterType;
    }

    public void setCommitAfterType(Integer commitAfterType) {
        this.commitAfterType = commitAfterType;
    }

    public Integer getShowStudentSort() {
        return showStudentSort;
    }

    public void setShowStudentSort(Integer showStudentSort) {
        this.showStudentSort = showStudentSort;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Integer referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }
}
