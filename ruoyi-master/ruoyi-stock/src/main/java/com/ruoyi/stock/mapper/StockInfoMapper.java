package com.ruoyi.stock.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.stock.domain.StockInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 库存信息Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockInfoMapper {
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
    public String selectRecommendLocation(@Param("matCode") String matCode, @Param("warehouseCode") String warehouseCode);

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
     * 修改库存信息
     */
    public int updateQuantity(@Param("warehouseCode") String warehouseCode, @Param("locationCode") String locationCode, @Param("batch") String batch,
        @Param("supplierCode") String supplierCode, @Param("matCode") String matCode, @Param("quantity") BigDecimal quantity);

    /**
     * 删除库存信息
     *
     * @param infoId 库存信息主键
     * @return 结果
     */
    public int deleteStockInfoByInfoId(Long infoId);

    /**
     * 批量删除库存信息
     *
     * @param infoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockInfoByInfoIds(Long[] infoIds);
}
