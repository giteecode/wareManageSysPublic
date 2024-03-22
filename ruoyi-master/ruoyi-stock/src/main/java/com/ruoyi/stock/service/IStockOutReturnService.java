package com.ruoyi.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.domain.StockOutReturn;

/**
 * 出库单退货Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockOutReturnService {

    /**
     * 查询出库单退货数量
     */
    public Map<String, Long> selectStockOutReturnTotal(Date selectDate);

    /**
     * 查询出库单退货
     *
     * @param returnId 出库单退货主键
     * @return 出库单退货
     */
    public StockOutReturn selectStockOutReturnByReturnId(Long returnId);

    /**
     * 查询出库单退货
     */
    public StockOutReturn selectStockOutReturnByReturnNo(String returnNo);

    /**
     * 查询出库单退货列表
     *
     * @param stockOutReturn 出库单退货
     * @return 出库单退货集合
     */
    public List<StockOutReturn> selectStockOutReturnList(StockOutReturn stockOutReturn);

    /**
     * 新增出库单退货
     *
     * @param stockOutReturn 出库单退货
     * @return 结果
     */
    public int insertStockOutReturn(String username, StockOutReturn stockOutReturn);

    /**
     * 修改出库单退货
     *
     * @param stockOutReturn 出库单退货
     * @return 结果
     */
    public int updateStockOutReturn(StockOutReturn stockOutReturn);

    /**
     * 批量删除出库单退货
     *
     * @param returnIds 需要删除的出库单退货主键集合
     * @return 结果
     */
    public int deleteStockOutReturnByReturnIds(Long[] returnIds);

    /**
     * 删除出库单退货信息
     *
     * @param returnId 出库单退货主键
     * @return 结果
     */
    public int deleteStockOutReturnByReturnId(Long returnId);

    /**
     * 扫码提交出库单退货-退货
     */
    public AjaxResult submitOutReturn(String username, StockOutReturn stockOutReturn);

}
