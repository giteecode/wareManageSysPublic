package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 课程评价实体类
 * @author zengjintao
 * @create_at 2021/10/17 9:29
 * @since version 1.0.3
 */
@TableName("course_valuate")
public class CourseValuate extends BaseEntity<CourseValuate> {

    /**
     * 课程id
     */
    @NotNull(message = "课程id不能为空")
    @TableField("course_id")
    private Integer courseId;

    /**
     * 学员id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 评价内容
     */
    @NotBlank(message = "请输入评价内容")
    private String content;

    /**
     * 类型
     * @see com.education.common.enums.ValuateTypeEnum
     */
    @TableField("valuate_type")
    private Integer valuateType;

    /**
     * 评价分数
     */
    @NotNull(message = "请评价课程分数")
    @TableField("valuate_mark")
    private Integer valuateMark;


    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getValuateType() {
        return valuateType;
    }

    public void setValuateType(Integer valuateType) {
        this.valuateType = valuateType;
    }

    public void setValuateMark(Integer valuateMark) {
        this.valuateMark = valuateMark;
    }

    public Integer getValuateMark() {
        return valuateMark;
    }
}
