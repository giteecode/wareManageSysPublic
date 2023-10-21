package com.education.model.dto;

import com.education.model.entity.LanguagePointsInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 18:32
 */
public class LanguagePointsInfoDto extends LanguagePointsInfo {

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 年级名称
     */
    private String gradeInfoName;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGradeInfoName() {
        return gradeInfoName;
    }

    public void setGradeInfoName(String gradeInfoName) {
        this.gradeInfoName = gradeInfoName;
    }
}
