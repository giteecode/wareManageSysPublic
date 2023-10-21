package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;

/**
 * 课程课时视频观看进度表
 * @author zjt
 * @create_at 2022年1月13日 0013 10:20
 * @since 1.0.5
 */
@TableName("video_watch_progress")
public class VideoWatchProgress extends BaseEntity<VideoWatchProgress> {

    /**
     * 学员id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 课程id
     */
    @TableField("course_id")
    @NotNull
    private Integer courseId;

    /**
     * 观看时长
     */
    @TableField("watch_time")
    @NotNull
    private Long watchTime;

    /**
     * 课时id
     */
    @NotNull
    private Integer sectionNodeId;

    /**
     * 是否已观看完本节课程视频
     */
    private Integer watchEnd;

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

    public Integer getSectionNodeId() {
        return sectionNodeId;
    }

    public void setSectionNodeId(Integer sectionNodeId) {
        this.sectionNodeId = sectionNodeId;
    }

    public Integer getWatchEnd() {
        return watchEnd;
    }

    public void setWatchEnd(Integer watchEnd) {
        this.watchEnd = watchEnd;
    }
}
