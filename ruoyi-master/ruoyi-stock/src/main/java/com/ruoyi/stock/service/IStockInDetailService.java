package com.ruoyi.stock.service;

import java.util.List;

import com.ruoyi.stock.domain.StockInDetail;

/**
 * 入库单详情Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockInDetailService {
    /**
     * 查询入库单详情
     *
     * @param detailId 入库单详情主键
     * @return 入库单详情
     */
    public StockInDetail selectStockInDetailByDetailId(Long detailId);

    /**
     * 查询入库单详情列表
     *
     * @param orderNo
     * @return 入库单详情集合
     */
    public List<StockInDetail> selectStockInDetailListByOrderNo(String orderNo);

    /**
     * 查询入库单详情列表
     *
     * @param stockInDetail 入库单详情
     * @return 入库单详情集合
     */
    public List<StockInDetail> selectStockInDetailList(StockInDetail stockInDetail);

    /**
     * 新增入库单详情
     *
     * @param stockInDetail 入库单详情
     * @return 结果
     */
    public int insertStockInDetail(StockInDetail stockInDetail);

    /**
     * 修改入库单详情
     *
     * @param stockInDetail 入库单详情
     * @return 结果
     */
    public int updateStockInDetail(StockInDetail stockInDetail);

    /**
     * 批量删除入库单详情
     *
     * @param detailIds 需要删除的入库单详情主键集合
     * @return 结果
     */
    public int deleteStockInDetailByDetailIds(Long[] detailIds);

    /**
     * 删除入库单详情信息
     *
     * @param detailId 入库单详情主键
     * @return 结果
     */
    public int deleteStockInDetailByDetailId(Long detailId);
}
