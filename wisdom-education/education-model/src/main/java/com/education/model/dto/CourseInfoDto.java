package com.education.model.dto;

import com.education.model.entity.CourseInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 15:01
 */
public class CourseInfoDto extends CourseInfo {

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 年级名称
     */
    private String gradeInfoName;

    /**
     * 学员id
     */
    private Integer studentId;

    /**
     * 是否批改
     * @see com.education.common.enums.BooleanEnum
     */
    private Integer collectFlag;

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getGradeInfoName() {
        return gradeInfoName;
    }

    public void setGradeInfoName(String gradeInfoName) {
        this.gradeInfoName = gradeInfoName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
