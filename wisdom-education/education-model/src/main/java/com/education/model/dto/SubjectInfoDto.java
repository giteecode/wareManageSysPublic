package com.education.model.dto;

import com.education.model.entity.SubjectInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 16:29
 */
public class SubjectInfoDto extends SubjectInfo {

    private String gradeName;


    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeName() {
        return gradeName;
    }
}
