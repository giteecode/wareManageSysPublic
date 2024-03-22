package com.ruoyi.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物料标签对象 stock_mat_label
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class MatLabelPdfData implements Serializable {

    /**
     * 主键
     */
    private Long labelId;

    /**
     * 标签类型
     */
    private String labelType;

    /**
     * 物料编码
     */
    private String matCode;

    /**
     * 物料描述
     */
    private String matName;

    /**
     * 财务编码
     */
    private String fdCode;

    /**
     * 图号
     */
    private String figNum;

    /**
     * 物料组
     */
    private String matGroup;

    private String matGroupName;

    /**
     * 分类
     */
    private String matClass;

    private String matClassName;

    /**
     * 基本单位
     */
    private String unitCode;

    private String unitName;

    /**
     * 批次
     */
    private String batch;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 生产时间
     */
    private Date prodTime;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 入库单号
     */
    private String orderNo;

    /**
     * 入库单类型
     */
    private String orderType;

    private  String orderTypeLabel;

}
