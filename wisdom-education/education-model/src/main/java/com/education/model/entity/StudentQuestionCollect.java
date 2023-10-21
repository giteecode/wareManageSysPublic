package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 试题收藏表
 * @author zjt
 * @create_at 2022年1月17日 0017 13:56
 * @since 1.0.5
 */
@TableName("student_question_collect")
public class StudentQuestionCollect extends BaseEntity<StudentQuestionCollect> {

    /**
     * 学员id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 试题id
     */
    @TableField("question_info_id")
    private Integer questionInfoId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getQuestionInfoId() {
        return questionInfoId;
    }

    public void setQuestionInfoId(Integer questionInfoId) {
        this.questionInfoId = questionInfoId;
    }
}
