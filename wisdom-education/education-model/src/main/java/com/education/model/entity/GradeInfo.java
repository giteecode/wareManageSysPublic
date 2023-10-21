package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.annotation.Unique;

/**
 * 年级信息表
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 13:32
 */
@TableName("grade_info")
public class GradeInfo extends BaseEntity<GradeInfo> {

    /**
     * 年级名称
     */
    @Unique
    private String name;

    /**
     * 所属阶段
     */
    @Unique("school_type")
    private Integer schoolType;

    public Integer getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Integer schoolType) {
        this.schoolType = schoolType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
