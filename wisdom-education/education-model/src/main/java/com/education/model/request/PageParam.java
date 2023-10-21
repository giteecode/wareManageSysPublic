package com.education.model.request;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 19:39
 */
public class PageParam {

    private static final int NO_PAGE = 1;

    private Integer pageNumber = NO_PAGE;
    private Integer pageSize = Integer.MAX_VALUE;

    public PageParam() {

    }

    public PageParam(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
