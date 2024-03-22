package com.ruoyi.stock.service;

import java.util.List;

import com.ruoyi.common.bean.request.PutOffRequestBody;
import com.ruoyi.common.bean.request.PutOnRequestBody;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.domain.StockInfo;

/**
 * 库存信息Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockInfoService {
    /**
     * 查询库存信息
     *
     * @param infoId 库存信息主键
     * @return 库存信息
     */
    public StockInfo selectStockInfoByInfoId(Long infoId);

    /**
     * 查询库存信息列表
     *
     * @param stockInfo 库存信息
     * @return 库存信息集合
     */
    public List<StockInfo> selectStockInfoList(StockInfo stockInfo);

    /**
     * 查询库存汇总列表
     */
    public List<StockInfo> selectStockInfoStatsList(StockInfo stockInfo);

    /**
     * 查询推荐货位
     */
    public String selectRecommendLocation(String matCode, String warehouseCode);

    /**
     * 新增库存信息
     *
     * @param stockInfo 库存信息
     * @return 结果
     */
    public int insertStockInfo(StockInfo stockInfo);

    /**
     * 修改库存信息
     *
     * @param stockInfo 库存信息
     * @return 结果
     */
    public int updateStockInfo(StockInfo stockInfo);

    /**
     * 批量删除库存信息
     *
     * @param infoIds 需要删除的库存信息主键集合
     * @return 结果
     */
    public int deleteStockInfoByInfoIds(Long[] infoIds);

    /**
     * 删除库存信息信息
     *
     * @param infoId 库存信息主键
     * @return 结果
     */
    public int deleteStockInfoByInfoId(Long infoId);

    /**
     * 扫码提交上架
     */
    public AjaxResult submitPutOn(String username, PutOnRequestBody putOnRequestBody);

    /**
     * 扫码提交下架
     */
    public AjaxResult submitPutOff(String username, PutOffRequestBody putOffRequestBody);

}
