package com.education.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.annotation.Unique;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 科目信息表
 */
@TableName("subject_info")
public class SubjectInfo extends BaseEntity<SubjectInfo> {

	@Unique
	@NotBlank(message = "请输入科目名称")
	@Length(min = 1, max = 20)
	private String name;
	@TableField("school_type")
	@NotNull(message = "请选择科目阶段")
	private Integer schoolType;

	@Unique("grade_info_id")
	@TableField("grade_info_id")
	@NotNull(message = "请选择年级")
	private Integer gradeInfoId;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}
}