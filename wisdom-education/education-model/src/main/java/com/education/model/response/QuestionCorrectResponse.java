package com.education.model.response;

import com.education.model.entity.StudentInfo;

import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/4/3 9:27
 */
public class QuestionCorrectResponse {

    /**
     * 学员得分
     */
    private Integer studentMark;

    /**
     * 排行榜
     */
    private Set<StudentInfo> studentInfoSet;

    /**
     * 考试耗时
     */
    private String examTime;

    /**
     * 考试id
     */
    private Integer examInfoId;

    /**
     * 移动端跳转页面视图类型
     */
    private Integer goViewType;

    public Integer getGoViewType() {
        return goViewType;
    }

    public void setGoViewType(Integer goViewType) {
        this.goViewType = goViewType;
    }

    public void setExamInfoId(Integer examInfoId) {
        this.examInfoId = examInfoId;
    }

    public Integer getExamInfoId() {
        return examInfoId;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamTime() {
        return examTime;
    }

    public Integer getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(Integer studentMark) {
        this.studentMark = studentMark;
    }

    public Set<StudentInfo> getStudentInfoSet() {
        return studentInfoSet;
    }

    public void setStudentInfoSet(Set<StudentInfo> studentInfoSet) {
        this.studentInfoSet = studentInfoSet;
    }
}
