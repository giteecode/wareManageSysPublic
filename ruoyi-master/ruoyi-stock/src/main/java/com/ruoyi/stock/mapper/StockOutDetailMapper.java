package com.ruoyi.stock.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.stock.domain.StockOutDetail;
import com.ruoyi.stock.domain.stats.StockOutStats;
import org.apache.ibatis.annotations.Param;

/**
 * 出库单详情Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockOutDetailMapper {
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
     * 新增出库单详情（批量）
     */
    public int insertStockOutDetailList(@Param("list") List<StockOutDetail> list);

    /**
     * 修改出库单详情
     *
     * @param stockOutDetail 出库单详情
     * @return 结果
     */
    public int updateStockOutDetail(StockOutDetail stockOutDetail);

    /**
     * 修改出库单已领数量
     */
    public int updateReceivedQuantity(@Param("orderNo") String orderNo, @Param("matCode") String matCode, @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime,
        @Param("receivedQuantity") BigDecimal receivedQuantity);

    /**
     * 删除出库单详情
     *
     * @param detailId 出库单详情主键
     * @return 结果
     */
    public int deleteStockOutDetailByDetailId(Long detailId);

    /**
     * 批量删除出库单详情
     *
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockOutDetailByDetailIds(Long[] detailIds);

    /**
     * 删除出库单详情
     */
    public int deleteStockOutDetailByOrderId(Long orderId);

    /**
     * 出库统计
     */
    public List<StockOutStats> selectStockOutStatsList(@Param("matCode") String matCode, @Param("matName") String matName);

}
