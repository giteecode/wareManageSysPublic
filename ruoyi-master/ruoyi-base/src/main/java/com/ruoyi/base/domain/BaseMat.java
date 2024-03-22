package com.ruoyi.base.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料主数据对象 base_mat
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Data
public class BaseMat extends BaseEntity {

    /**
     * 主键
     */
    private Long matId;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String matCode;

    /**
     * 物料描述
     */
    @Excel(name = "物料描述")
    private String matName;

    /**
     * 财务编码
     */
    @Excel(name = "财务编码")
    private String fdCode;

    /**
     * 图号
     */
    @Excel(name = "图号")
    private String figNum;

    /**
     * 物料组
     */
    @Excel(name = "物料组")
    private String matGroup;

    private String matGroupName;

    /**
     * 分类
     */
    @Excel(name = "分类")
    private String matClass;

    private String matClassName;

    /**
     * 基本单位
     */
    @Excel(name = "基本单位")
    private String unitCode;

    /**
     * 毛重
     */
    @Excel(name = "毛重")
    private BigDecimal grossWeight;

    /**
     * 安全库存
     */
    @Excel(name = "安全库存")
    private BigDecimal safetyStock;

    /**
     * 标准价
     */
    @Excel(name = "标准价")
    private BigDecimal standardPrice;

    /**
     * 删除标识
     */
    private String delFlag;

}
