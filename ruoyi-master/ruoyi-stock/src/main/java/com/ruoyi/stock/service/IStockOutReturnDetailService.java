package com.ruoyi.stock.service;

import java.util.List;

import com.ruoyi.stock.domain.StockOutReturnDetail;

/**
 * 出库退货详情Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockOutReturnDetailService {
    /**
     * 查询出库退货详情
     *
     * @param detailId 出库退货详情主键
     * @return 出库退货详情
     */
    public StockOutReturnDetail selectStockOutReturnDetailByDetailId(Long detailId);

    /**
     * 查询出库退货详情列表
     *
     * @param stockOutReturnDetail 出库退货详情
     * @return 出库退货详情集合
     */
    public List<StockOutReturnDetail> selectStockOutReturnDetailList(StockOutReturnDetail stockOutReturnDetail);

    /**
     * 查询出库退货详情列表
     */
    public List<StockOutReturnDetail> selectStockOutReturnDetailListByReturnNo(String returnNo);

    /**
     * 新增出库退货详情
     *
     * @param stockOutReturnDetail 出库退货详情
     * @return 结果
     */
    public int insertStockOutReturnDetail(StockOutReturnDetail stockOutReturnDetail);

    /**
     * 修改出库退货详情
     *
     * @param stockOutReturnDetail 出库退货详情
     * @return 结果
     */
    public int updateStockOutReturnDetail(StockOutReturnDetail stockOutReturnDetail);

    /**
     * 批量删除出库退货详情
     *
     * @param detailIds 需要删除的出库退货详情主键集合
     * @return 结果
     */
    public int deleteStockOutReturnDetailByDetailIds(Long[] detailIds);

    /**
     * 删除出库退货详情信息
     *
     * @param detailId 出库退货详情主键
     * @return 结果
     */
    public int deleteStockOutReturnDetailByDetailId(Long detailId);
}
