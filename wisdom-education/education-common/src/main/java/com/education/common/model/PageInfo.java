package com.education.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/15 14:24
 */
public class PageInfo<T> implements Serializable {

    public PageInfo(List<T> dataList, int totalPage, long total) {
        this.dataList = dataList;
        this.total = total;
        this.totalPage = totalPage;
    }

    public PageInfo() {

    }

    private long total = 0;
    private int totalPage; // 总页数
    private List<T> dataList;

    public long getTotal() {
        return total;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
