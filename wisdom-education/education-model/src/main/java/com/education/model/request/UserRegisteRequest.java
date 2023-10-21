package com.education.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 19:49
 */
public class UserRegisteRequest {

    @NotBlank(message = "请输入用户名")
    @Length(max = 10)
    private String userName;
    @NotBlank(message = "请输入密码")
    @Length(max = 20)
    private String password;
    @NotBlank(message = "请输入手机号")
    @Length(max = 11)
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
