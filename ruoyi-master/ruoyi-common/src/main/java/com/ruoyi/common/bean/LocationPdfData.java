package com.ruoyi.common.bean;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 货位对象 base_location
 *
 * @author ruoyi
 * @date 2022-07-24
 */
@Data
public class LocationPdfData {

    /**
     * 货位编码
     */
    private String locationCode;

    /**
     * 货位名称
     */
    private String locationName;

    /**
     * 仓库编码
     */
    private String warehouseCode;

}
