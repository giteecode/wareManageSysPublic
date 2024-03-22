package com.ruoyi.base.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车间对象 base_workshop
 *
 * @author ruoyi
 * @date 2022-07-28
 */
@Data
public class BaseWorkshop extends BaseEntity {

    /**
     * 主键
     */
    private Long workshopId;

    /**
     * 车间编码
     */
    @Excel(name = "车间编码")
    private String workshopCode;

    /**
     * 车间名称
     */
    @Excel(name = "车间名称")
    private String workshopName;

    /**
     * 删除标识
     */
    private String delFlag;

}
