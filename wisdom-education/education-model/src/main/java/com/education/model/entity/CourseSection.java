package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author zengjintao
 * @create_at 2021/10/6 9:55
 * @since version 1.0.3
 */
@TableName("course_section")
public class CourseSection extends BaseEntity<CourseSection> {

    @TableField("course_id")
    @NotNull(message = "请选择章节所属课程")
    private Integer courseId;

    @NotBlank(message = "章节名称不能为空")
    private String title;

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
}
