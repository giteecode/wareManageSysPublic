package com.education.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.annotation.Unique;

import javax.validation.constraints.NotBlank;

@TableName("system_role")
public class SystemRole extends BaseEntity<SystemRole> {

	@Unique
	@NotBlank(message = "请输入角色名称")
	private String name;
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}