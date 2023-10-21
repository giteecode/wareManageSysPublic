package com.education.model.dto;

import java.util.List;

/**
 * canal队列数据实体类
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/2 11:58
 */
public class CanalQueueData {

    /**
     * canal 表新数据
     */
    private String data;

    /**
     * canal 旧数据
     */
    private String old;

    /**
     * 监听类型
     */
    private String type;

    /**
     * 表名称
     */
    private String table;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
