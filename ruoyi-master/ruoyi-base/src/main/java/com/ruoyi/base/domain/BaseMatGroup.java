package com.ruoyi.base.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料组对象 base_mat_group
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Data
public class BaseMatGroup extends BaseEntity {

    /**
     * 主键
     */
    private Long groupId;

    /**
     * 物料组编码
     */
    @Excel(name = "物料组编码")
    private String groupCode;

    /**
     * 物料组名称
     */
    @Excel(name = "物料组名称")
    private String groupName;

    /**
     * 删除标识
     */
    private String delFlag;

}
