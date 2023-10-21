package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.annotation.Unique;
import com.education.common.enums.BooleanEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 系统管理员表
 */
@TableName("system_admin")
public class SystemAdmin extends BaseEntity<SystemAdmin> {

	/**
	 * 账户名
	 */
	@TableField("login_name")
	@Unique("login_name")
	private String loginName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 密码加密hash
	 */
	private String encrypt;

	/**
	 * 是否禁用
	 */
	@TableField("disabled_flag")
	private Integer disabledFlag;

	/**
	 * 登录ip
	 */
	private String loginIp;

	/**
	 * 登录次数
	 */
	@TableField("login_count")
	private Integer loginCount;

	/**
	 * 真实姓名
	 */
	private String name;

	/**
	 * 最后登录时间
	 */
	@TableField("last_login_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;

	/**
	 * 创建类型
	 */
	@TableField("create_type")
	@Deprecated
	private Integer createType;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 是否超级管理员
	 */
	@TableField("super_flag")
	private Integer superFlag;

	/**
	 * ip 真实地址
	 */
	@TableField("ip_address")
	private String ipAddress;


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public Integer getDisabledFlag() {
		return disabledFlag;
	}

	public void setDisabledFlag(Integer disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSuperFlag() {
		return superFlag;
	}

	public void setSuperFlag(Integer superFlag) {
		this.superFlag = superFlag;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isSuper() {
		return this.superFlag == BooleanEnum.YES.getCode();
	}

	public boolean isDisabled() {
		return this.disabledFlag == BooleanEnum.YES.getCode();
	}
}