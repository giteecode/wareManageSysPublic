package com.ruoyi.base.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库对象 base_warehouse
 *
 * @author ruoyi
 * @date 2022-07-24
 */
@Data
public class BaseWarehouse extends BaseEntity {

    /**
     * 主键
     */
    private Long warehouseId;

    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /**
     * 删除标识
     */
    private String delFlag;

}
