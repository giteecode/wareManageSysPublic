package com.ruoyi.base.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料BOM对象 base_mat_bom
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Data
public class BaseMatBom extends BaseEntity {

    /**
     * 主键
     */
    private Long bomId;

    /**
     * 父物料编码
     */
    @Excel(name = "父物料编码")
    private String fatherMatCode;

    private String fatherMatName;

    /**
     * 父项数量
     */
    @Excel(name = "父项数量")
    private BigDecimal fatherMatNum;

    /**
     * 子项行项目号
     */
    @Excel(name = "子项行项目号")
    private Long childNo;

    /**
     * 子项物料编码
     */
    @Excel(name = "子项物料编码")
    private String childMatCode;

    private String childMatName;

    /**
     * 子项数量
     */
    @Excel(name = "子项数量")
    private BigDecimal childMatNum;

    /**
     * 是否虚拟键
     */
    @Excel(name = "是否虚拟键")
    private String isFictitious;

    private String figNum;

}
