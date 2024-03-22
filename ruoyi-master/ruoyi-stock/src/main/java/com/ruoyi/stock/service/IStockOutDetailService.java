package com.ruoyi.stock.service;

import java.util.List;

import com.ruoyi.stock.domain.StockOutDetail;

/**
 * 出库单详情Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockOutDetailService {

    /**
     * 查询出库单详情
     *
     * @param detailId 出库单详情主键
     * @return 出库单详情
     */
    public StockOutDetail selectStockOutDetailByDetailId(Long detailId);

    /**
     * 查询出库单详情列表
     *
     * @param stockOutDetail 出库单详情
     * @return 出库单详情集合
     */
    public List<StockOutDetail> selectStockOutDetailList(StockOutDetail stockOutDetail);

    /**
     * 查询出库单列表
     */
    public List<StockOutDetail> selectStockOutDetailListByOrderNo(String orderNo);

    /**
     * 新增出库单详情
     *
     * @param stockOutDetail 出库单详情
     * @return 结果
     */
    public int insertStockOutDetail(StockOutDetail stockOutDetail);

    /**
     * 修改出库单详情
     *
     * @param stockOutDetail 出库单详情
     * @return 结果
     */
    public int updateStockOutDetail(StockOutDetail stockOutDetail);

    /**
     * 批量删除出库单详情
     *
     * @param detailIds 需要删除的出库单详情主键集合
     * @return 结果
     */
    public int deleteStockOutDetailByDetailIds(Long[] detailIds);

    /**
     * 删除出库单详情信息
     *
     * @param detailId 出库单详情主键
     * @return 结果
     */
    public int deleteStockOutDetailByDetailId(Long detailId);
}
