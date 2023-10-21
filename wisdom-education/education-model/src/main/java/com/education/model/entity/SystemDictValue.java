package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.annotation.Unique;

@TableName("system_dict_value")
public class SystemDictValue extends BaseEntity<SystemDictValue> {

	@TableField("system_dict_id")
	@Unique("system_dict_id")
	private Integer systemDictId;

	private String value;

	@Unique
	private Integer code;

	@TableField("parent_id")
	private Integer parentId;

	private Integer sort;

	public Integer getSystemDictId() {
		return systemDictId;
	}

	public void setSystemDictId(Integer systemDictId) {
		this.systemDictId = systemDictId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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
}