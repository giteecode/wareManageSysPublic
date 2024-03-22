package com.ruoyi.stock.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.stock.domain.StockInReturn;
import org.apache.ibatis.annotations.Param;

/**
 * 入库单退货Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockInReturnMapper {

    /**
     * 查询入库单退货数量
     */
    public Map<String, Long> selectStockInReturnTotal(@Param("selectDate") Date selectDate);

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
    public int insertStockInReturn(StockInReturn stockInReturn);

    /**
     * 修改入库单退货
     *
     * @param stockInReturn 入库单退货
     * @return 结果
     */
    public int updateStockInReturn(StockInReturn stockInReturn);

    /**
     * 删除入库单退货
     *
     * @param returnId 入库单退货主键
     * @return 结果
     */
    public int deleteStockInReturnByReturnId(Long returnId);

    /**
     * 批量删除入库单退货
     *
     * @param returnIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockInReturnByReturnIds(Long[] returnIds);
}
