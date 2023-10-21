package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.validate.Save;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zengjintao
 * @create_at 2021/10/6 9:55
 * @since version 1.0.3
 */
@TableName("course_section_node")
public class CourseSectionNode extends BaseEntity<CourseSectionNode> {

    /**
     * 课程id
     */
    @TableField("course_id")
    @NotNull(message = "请选择课程", groups = {Save.class})
    private Integer courseId;

    /**
     * 课时标题
     */
    @NotBlank(message = "请添加课时标题", groups = {Save.class})
    private String title;

    /**
     * 课时所属章id
     */
    @TableField("course_section_id")
    @NotNull(message = "请选择课时章节")
    private Integer courseSectionId;

    /**
     * 课时附件
     */
    private String enclosure;


    /**
     * 是否免费
     */
    @TableField("free_flag")
    private Integer freeFlag;

    /**
     * 可是简介
     */
    private String synopsis;

    /**
     * 视频时长中文字符串
     */
    @TableField(exist = false)
    private String durationStr;

    /**
     * 视频时长
     */
    private Long duration;

    /**
     * 视频分辨率宽
     */
    private Integer width;

    /**
     * 视频分辨率高
     */
    private Integer height;

    /**
     * 视频大小
     */
    private Long size;

    @TableField("video_url")
    private String videoUrl;

    @TableField("video_name")
    private String videoName;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(Integer courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
