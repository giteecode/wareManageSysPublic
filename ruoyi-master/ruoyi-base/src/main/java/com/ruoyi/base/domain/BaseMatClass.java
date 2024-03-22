package com.ruoyi.base.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料分类对象 base_mat_class
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Data
public class BaseMatClass extends BaseEntity {

    /**
     * 主键
     */
    private Long classId;

    /**
     * 分类编码
     */
    @Excel(name = "分类编码")
    private String classCode;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String className;

    /**
     * 删除标识
     */
    private String delFlag;

}
