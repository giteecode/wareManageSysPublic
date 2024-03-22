package com.ruoyi.stock.service;

import java.util.List;

import com.ruoyi.stock.domain.StockInReturnDetail;

/**
 * 入库单退货详情Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockInReturnDetailService {
    /**
     * 查询入库单退货详情
     *
     * @param detailId 入库单退货详情主键
     * @return 入库单退货详情
     */
    public StockInReturnDetail selectStockInReturnDetailByDetailId(Long detailId);

    /**
     * 查询入库单退货详情列表
     *
     * @param stockInReturnDetail 入库单退货详情
     * @return 入库单退货详情集合
     */
    public List<StockInReturnDetail> selectStockInReturnDetailList(StockInReturnDetail stockInReturnDetail);

    /**
     * 查询入库单退货详情列表
     */
    public List<StockInReturnDetail> selectStockInReturnDetailListByReturnNo(String returnNo);

    /**
     * 新增入库单退货详情
     *
     * @param stockInReturnDetail 入库单退货详情
     * @return 结果
     */
    public int insertStockInReturnDetail(StockInReturnDetail stockInReturnDetail);

    /**
     * 修改入库单退货详情
     *
     * @param stockInReturnDetail 入库单退货详情
     * @return 结果
     */
    public int updateStockInReturnDetail(StockInReturnDetail stockInReturnDetail);

    /**
     * 批量删除入库单退货详情
     *
     * @param detailIds 需要删除的入库单退货详情主键集合
     * @return 结果
     */
    public int deleteStockInReturnDetailByDetailIds(Long[] detailIds);

    /**
     * 删除入库单退货详情信息
     *
     * @param detailId 入库单退货详情主键
     * @return 结果
     */
    public int deleteStockInReturnDetailByDetailId(Long detailId);
}
