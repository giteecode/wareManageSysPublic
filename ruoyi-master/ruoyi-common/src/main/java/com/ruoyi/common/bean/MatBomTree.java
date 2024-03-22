package com.ruoyi.common.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MatBomTree {

    //父物料编码
    private String fatherMatCode;
    //父项数量
    private BigDecimal fatherMatNum;
    //子项行项目号
    private Long childNo;
    //子项物料编码
    private String childMatCode;
    //子项数量
    private BigDecimal childMatNum;
    //是否虚拟键
    private String isFictitious;
    //子项
    private List<MatBomTree> children;

}
