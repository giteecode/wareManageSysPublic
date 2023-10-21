package com.education.model.dto;

import com.education.model.entity.CourseStudyProgress;

/**
 * @author zjt
 * @create_at 2022年7月13日 0013 14:25
 * @since 1.0.6
 */
public class CourseStudyProgressDto extends CourseStudyProgress {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 学习进度百分比
     */
    private String studyRate;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudyRate() {
        return studyRate;
    }

    public void setStudyRate(String studyRate) {
        this.studyRate = studyRate;
    }
}
