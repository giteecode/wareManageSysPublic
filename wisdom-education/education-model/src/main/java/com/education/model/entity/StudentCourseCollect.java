package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;

/**
 * @author zengjintao
 * @create_at 2021/12/26 11:03
 * @since version 1.0.4
 */
@TableName("student_course_collect")
public class StudentCourseCollect extends BaseEntity<StudentCourseCollect> {

    @TableField("student_id")
    private Integer studentId;

    @TableField("course_id")
    @NotNull
    private Integer courseId;

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
}
