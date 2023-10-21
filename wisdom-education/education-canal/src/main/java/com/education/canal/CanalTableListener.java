package com.education.canal;



/**
 * canal table 数据监听器
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/8/21 20:18
 */
public interface CanalTableListener<T> {

    /**
     * insert 数据监听
     * @param tableData
     */
    default void onInsert(T tableData) {}

    /**
     * delete 数据监听
     * @param tableData
     */
    default void onDelete(T tableData) {}

    /**
     * update 数据监听
     * @param newTableData
     * @param oldTableData
     */
    default void onUpdate(T newTableData, T oldTableData) {}

    /**
     * 获取表名称
     * @return
     */
    String getTableName();
}
