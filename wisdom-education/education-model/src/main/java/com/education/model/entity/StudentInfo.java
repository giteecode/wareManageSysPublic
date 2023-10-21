package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.utils.RegexUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@TableName("student_info")
public class StudentInfo extends BaseEntity<StudentInfo> {


	/**
	 * 姓名
	 */
	@NotBlank(message = "请输入姓名")
	@Length(max = 10)
	private String name;

	/**
	 * 年龄
	 */
	private Integer age;


	@NotNull(message = "请选择性别")
	private Integer sex;

	private String address;

	@NotBlank(message = "请输入手机号")
	@Pattern(regexp = RegexUtils.MOBILE_REGEX, message = "非法手机号")
	@Length(max = 11)
	private String mobile;

	private String password;

	private String encrypt;

	@TableField("head_img")
	private String headImg;

	@TableField("grade_info_id")
	private Integer gradeInfoId;

	@TableField("login_name")
	private String loginName;

	@TableField("disabled_flag")
	private boolean disabledFlag;

	@TableField("login_ip")
	private String loginIp;

	@TableField("login_count")
	private Integer loginCount;

	@TableField("last_login_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;

	/**
	 * 生日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

		public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public boolean isDisabledFlag() {
		return disabledFlag;
	}

	public void setDisabledFlag(boolean disabledFlag) {
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}