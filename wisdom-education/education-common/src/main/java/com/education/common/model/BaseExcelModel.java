package com.education.common.model;

import cn.afterturn.easypoi.handler.inter.IExcelDataModel;

import java.io.Serializable;

/**
 * excel 实体类父类
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/11/7 20:17
 */
public abstract class BaseExcelModel implements IExcelDataModel, Serializable {

    private int rowNum;

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {
       this.rowNum = rowNum;
    }
}
