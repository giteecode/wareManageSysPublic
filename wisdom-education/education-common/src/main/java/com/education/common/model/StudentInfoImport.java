package com.education.common.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;


/**
 * 学员信息实体类
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/20 21:22
 */
public class StudentInfoImport extends BaseExcelModel {

    @Excel(name = "学生姓名")
    @NotNull(message = "学生姓名不能为空")
    private String name;

    @Excel(name = "学员照片", type = 2)
    private String headImage;

    @Excel(name = "手机号")
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @Excel(name = "年龄")
    private int age;

    @Excel(name = "性别")
    @NotNull(message = "性别不能为空")
    private String sex;

    @Excel(name = "家庭住址")
    @NotNull(message = "家庭住址不能为空")
    private String address;

    @Excel(name = "就读年级")
    @NotNull(message = "就读年级不能为空")
    private String gradeName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
