package com.education.model.dto;

import com.education.model.entity.StudentInfo;
import org.hibernate.validator.constraints.Length;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 18:25
 */
public class StudentInfoDto extends StudentInfo {

    private String gradeName;
    @Length(max = 20)
    private String confirmPassword;
    @Length(max = 20)
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeName() {
        return gradeName;
    }
}
