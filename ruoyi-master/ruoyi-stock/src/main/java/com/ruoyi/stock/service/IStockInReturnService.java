package com.ruoyi.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.bean.request.InReturnRequestBody;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.domain.StockInReturn;

/**
 * 入库单退货Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockInReturnService {

    /**
     * 查询入库单退货数量
     */
    public Map<String, Long> selectStockInReturnTotal(Date selectDate);

    /**
     * 查询入库单退货
     *
     * @param returnId 入库单退货主键
     * @return 入库单退货
     */
    public StockInReturn selectStockInReturnByReturnId(Long returnId);

    /**
     * 查询入库单退货
     */
    public StockInReturn selectStockInReturnByReturnNo(String returnNo);

    /**
     * 查询入库单退货列表
     *
     * @param stockInReturn 入库单退货
     * @return 入库单退货集合
     */
    public List<StockInReturn> selectStockInReturnList(StockInReturn stockInReturn);

    /**
     * 新增入库单退货
     *
     * @param stockInReturn 入库单退货
     * @return 结果
     */
    public int insertStockInReturn(String username, StockInReturn stockInReturn);

    /**
     * 修改入库单退货
     *
     * @param stockInReturn 入库单退货
     * @return 结果
     */
    public int updateStockInReturn(StockInReturn stockInReturn);

    /**
     * 批量删除入库单退货
     *
     * @param returnIds 需要删除的入库单退货主键集合
     * @return 结果
     */
    public int deleteStockInReturnByReturnIds(Long[] returnIds);

    /**
     * 删除入库单退货信息
     *
     * @param returnId 入库单退货主键
     * @return 结果
     */
    public int deleteStockInReturnByReturnId(Long returnId);

    /**
     * 扫码提交入库单退货-退货
     */
    public AjaxResult submitInReturn(String username, InReturnRequestBody inReturnRequestBody);

}
