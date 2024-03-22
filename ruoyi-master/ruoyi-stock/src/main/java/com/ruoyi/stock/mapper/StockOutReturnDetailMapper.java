package com.ruoyi.stock.mapper;

import java.util.List;

import com.ruoyi.stock.domain.StockOutReturnDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 出库退货详情Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockOutReturnDetailMapper {
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
     * 新增出库退货详情（批量）
     */
    public int insertStockOutReturnDetailList(@Param("list") List<StockOutReturnDetail> list);

    /**
     * 修改出库退货详情
     *
     * @param stockOutReturnDetail 出库退货详情
     * @return 结果
     */
    public int updateStockOutReturnDetail(StockOutReturnDetail stockOutReturnDetail);

    /**
     * 删除出库退货详情
     *
     * @param detailId 出库退货详情主键
     * @return 结果
     */
    public int deleteStockOutReturnDetailByDetailId(Long detailId);

    /**
     * 批量删除出库退货详情
     *
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockOutReturnDetailByDetailIds(Long[] detailIds);

    /**
     * 删除出库退货详情
     */
    public int deleteStockOutReturnDetailByReturnId(Long returnId);

}
