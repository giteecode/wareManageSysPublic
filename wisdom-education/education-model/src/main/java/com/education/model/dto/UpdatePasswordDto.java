package com.education.model.dto;

import org.hibernate.validator.constraints.Length;


public class UpdatePasswordDto {

    /**
     * 原密码
     */
    private String password;

    @Length(max = 20)
    private String confirmPassword;

    @Length(max = 20)
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
