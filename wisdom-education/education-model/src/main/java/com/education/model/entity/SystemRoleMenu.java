package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("system_role_menu")
public class SystemRoleMenu extends BaseEntity<SystemRoleMenu> {

	@TableField("menu_id")
	private int menuId;
	@TableField("role_id")
	private int roleId;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}