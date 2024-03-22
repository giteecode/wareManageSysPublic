package com.ruoyi.base.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商对象 base_supplier
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Data
public class BaseSupplier extends BaseEntity {

    /**
     * 主键
     */
    private Long supplierId;

    /**
     * 供应商编码
     */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /**
     * 供应商简称
     */
    @Excel(name = "供应商名称")
    private String supplierName;

    /**
     * 供货名称
     */
    @Excel(name = "供货名称")
    private String supplyType;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contact;

    /**
     * 税号
     */
    @Excel(name = "税号")
    private String taxNumber;

    /**
     * 开户行
     */
    @Excel(name = "开户行")
    private String depositBank;

    /**
     * 账号
     */
    @Excel(name = "账号")
    private String bankAccount;

    /**
     * 是否合格供应商
     */
    @Excel(name = "是否合格供应商")
    private String isQualified;

    /**
     * 城市
     */
    @Excel(name = "城市")
    private String city;

    /**
     * 邮政编码
     */
    @Excel(name = "邮政编码")
    private String postalCode;

    /**
     * 删除标识
     */
    private String delFlag;

}
