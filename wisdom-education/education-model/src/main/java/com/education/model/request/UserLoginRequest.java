package com.education.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 19:49
 */
public class UserLoginRequest {

    @NotBlank(message = "请输入用户名", groups = {AdminLogin.class, StudentLogin.class})
    @Length(max = 10)
    private String userName;
    @NotBlank(message = "请输入密码", groups = {AdminLogin.class, StudentLogin.class})
    @Length(max = 20)
    private String password;
    @NotBlank(message = "非法请求", groups = {AdminLogin.class})
    @Length(max = 20)
    private String key;
    @NotBlank(message = "请输入验证码", groups = {AdminLogin.class})
    @Length(max = 6)
    private String code;

    /**
     * 登录设备类型
     */
    private String deviceType;

    /**
     * 是否自动登录
     */
    private boolean checked = false;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }


    public interface AdminLogin {

    }

    public interface StudentLogin {

    }
}
