package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 课程学习进度表
 * @author zjt
 * @create_at 2022年1月13日 0013 10:17
 * @since 1.0.5
 */
@TableName("course_study_progress")
public class CourseStudyProgress extends BaseEntity<CourseStudyProgress> {

    /**
     * 学员id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 课程id
     */
    @TableField("course_id")
    private Integer courseId;

    /**
     * 观看时长
     */
    @TableField("watch_time")
    private Long watchTime;


    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Long getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(Long watchTime) {
        this.watchTime = watchTime;
    }
}
