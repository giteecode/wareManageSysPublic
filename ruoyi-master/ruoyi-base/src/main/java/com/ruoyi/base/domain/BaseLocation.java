package com.ruoyi.base.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 货位对象 base_location
 *
 * @author ruoyi
 * @date 2022-07-24
 */
@Data
public class BaseLocation extends BaseEntity {

    /**
     * 主键
     */
    private Long locationId;

    /**
     * 仓库
     */
    @Excel(name = "仓库")
    private String warehouseCode;

    //仓库名
    private String warehouseName;

    /**
     * 货位编码
     */
    @Excel(name = "货位编码")
    private String locationCode;

    /**
     * 货位名称
     */
    @Excel(name = "货位名称")
    private String locationName;

    /**
     * 货位类型
     */
    private String locationType;

    /**
     * 删除标识
     */
    private String delFlag;

}
