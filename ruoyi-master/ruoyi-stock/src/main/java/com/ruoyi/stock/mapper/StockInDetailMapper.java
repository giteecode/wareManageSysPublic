package com.ruoyi.stock.mapper;

import java.util.List;

import com.ruoyi.stock.domain.StockInDetail;
import com.ruoyi.stock.domain.stats.StockInStats;
import org.apache.ibatis.annotations.Param;

/**
 * 入库单详情Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockInDetailMapper {
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
     * 新增入库单详情（批量）
     *
     * @param list 入库单详情list
     * @return 结果
     */
    public int insertStockInDetailList(@Param("list") List<StockInDetail> list);

    /**
     * 修改入库单详情
     *
     * @param stockInDetail 入库单详情
     * @return 结果
     */
    public int updateStockInDetail(StockInDetail stockInDetail);

    /**
     * 删除入库单详情
     *
     * @param detailId 入库单详情主键
     * @return 结果
     */
    public int deleteStockInDetailByDetailId(Long detailId);

    /**
     * 批量删除入库单详情
     *
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockInDetailByDetailIds(Long[] detailIds);

    /**
     * 删除入库单详情
     */
    public int deleteStockInDetailByOrderId(Long orderId);

    /**
     * 入库统计
     */
    public List<StockInStats> selectStockInStatsList(@Param("matCode") String matCode, @Param("matName") String matName);

}
