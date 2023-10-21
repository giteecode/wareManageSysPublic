package com.education.model.dto;

import com.education.model.entity.StudentCourseCollect;

/**
 * @author zengjintao
 * @create_at 2021/12/26 11:09
 * @since version 1.0.4
 */
public class StudentCourseCollectDto extends StudentCourseCollect {

    /**
     * 批改状态
     * @see com.education.common.enums.BooleanEnum
     */
    private Integer collectFlag;

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }
}
