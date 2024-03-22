package com.ruoyi.stock.service;

import java.util.List;

import com.ruoyi.stock.domain.StockAllotDetail;

/**
 * 调拨单详情Service接口
 *
 * @author ruoyi
 * @date 2022-08-05
 */
public interface IStockAllotDetailService {
    /**
     * 查询调拨单详情
     *
     * @param detailId 调拨单详情主键
     * @return 调拨单详情
     */
    public StockAllotDetail selectStockAllotDetailByDetailId(Long detailId);

    /**
     * 查询调拨单详情列表
     *
     * @param stockAllotDetail 调拨单详情
     * @return 调拨单详情集合
     */
    public List<StockAllotDetail> selectStockAllotDetailList(StockAllotDetail stockAllotDetail);

    /**
     * 查询调拨单详情列表
     */
    public List<StockAllotDetail> selectStockAllotDetailListByAllotNo(String allotNo);

    /**
     * 新增调拨单详情
     *
     * @param stockAllotDetail 调拨单详情
     * @return 结果
     */
    public int insertStockAllotDetail(StockAllotDetail stockAllotDetail);

    /**
     * 修改调拨单详情
     *
     * @param stockAllotDetail 调拨单详情
     * @return 结果
     */
    public int updateStockAllotDetail(StockAllotDetail stockAllotDetail);

    /**
     * 批量删除调拨单详情
     *
     * @param detailIds 需要删除的调拨单详情主键集合
     * @return 结果
     */
    public int deleteStockAllotDetailByDetailIds(Long[] detailIds);

    /**
     * 删除调拨单详情信息
     *
     * @param detailId 调拨单详情主键
     * @return 结果
     */
    public int deleteStockAllotDetailByDetailId(Long detailId);
}
