package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("language_points_info")
public class LanguagePointsInfo extends BaseEntity<LanguagePointsInfo> {

	private String name;
	@TableField("grade_info_id")
	private Integer gradeInfoId;
	@TableField("subject_id")
	private Integer subjectId;
	@TableField("parent_id")
	private Integer parentId;
	private Integer sort;
	@TableField("has_children")
	private Boolean hasChildren;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
}