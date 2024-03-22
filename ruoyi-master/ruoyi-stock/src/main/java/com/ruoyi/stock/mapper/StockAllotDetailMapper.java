package com.ruoyi.stock.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.stock.domain.StockAllotDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 调拨单详情Mapper接口
 *
 * @author ruoyi
 * @date 2022-08-05
 */
public interface StockAllotDetailMapper {
    /**
     * 查询调拨单详情
     *
     * @param detailId 调拨单详情主键
     * @return 调拨单详情
     */
    public StockAllotDetail selectStockAllotDetailByDetailId(Long detailId);

    /**
     * 查询调拨单详情列表
     *
     * @param stockAllotDetail 调拨单详情
     * @return 调拨单详情集合
     */
    public List<StockAllotDetail> selectStockAllotDetailList(StockAllotDetail stockAllotDetail);

    /**
     * 查询调拨单详情列表
     */
    public List<StockAllotDetail> selectStockAllotDetailListByAllotNo(String allotNo);

    /**
     * 新增调拨单详情
     *
     * @param stockAllotDetail 调拨单详情
     * @return 结果
     */
    public int insertStockAllotDetail(StockAllotDetail stockAllotDetail);

    /**
     * 修改调拨单详情
     *
     * @param stockAllotDetail 调拨单详情
     * @return 结果
     */
    public int updateStockAllotDetail(StockAllotDetail stockAllotDetail);

    /**
     * 修改调拨单详情
     */
    public int updateStockAllotReceive(@Param("detailId") Long detailId, @Param("destLocationCode") String destLocationCode, @Param("signQuantity") BigDecimal signQuantity,
        @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);

    /**
     * 删除调拨单详情
     *
     * @param detailId 调拨单详情主键
     * @return 结果
     */
    public int deleteStockAllotDetailByDetailId(Long detailId);

    /**
     * 批量删除调拨单详情
     *
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockAllotDetailByDetailIds(Long[] detailIds);
}
