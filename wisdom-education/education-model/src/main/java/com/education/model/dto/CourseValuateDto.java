package com.education.model.dto;

import com.education.model.entity.CourseValuate;

/**
 * @author zengjintao
 * @create_at 2021/10/17 9:49
 * @since version 1.0.3
 */
public class CourseValuateDto extends CourseValuate {

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * 头像地址
     */
    private String headImg;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
