package com.ruoyi.stock.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.stock.domain.StockMatLabel;
import org.apache.ibatis.annotations.Param;

/**
 * 物料标签Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockMatLabelMapper {
    /**
     * 查询物料标签
     *
     * @param labelId 物料标签主键
     * @return 物料标签
     */
    public StockMatLabel selectStockMatLabelByLabelId(Long labelId);

    /**
     * 查询物料标签列表
     *
     * @param stockMatLabel 物料标签
     * @return 物料标签集合
     */
    public List<StockMatLabel> selectStockMatLabelList(StockMatLabel stockMatLabel);

    /**
     * 查询物料标签列表（弹窗）
     *
     * @param stockMatLabel 物料标签
     * @return 物料标签集合
     */
    public List<StockMatLabel> selectStockMatLabelListDialog(StockMatLabel stockMatLabel);

    /**
     * 新增物料标签
     *
     * @param stockMatLabel 物料标签
     * @return 结果
     */
    public int insertStockMatLabel(StockMatLabel stockMatLabel);

    /**
     * 修改物料标签
     *
     * @param stockMatLabel 物料标签
     * @return 结果
     */
    public int updateStockMatLabel(StockMatLabel stockMatLabel);

    /**
     * 修改物料标签合格数
     */
    public int updateUsableQuantity(@Param("labelId") Long labelId, @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime,
        @Param("warehouseCode") String warehouseCode, @Param("locationCode") String locationCode, @Param("usableQuantity") BigDecimal usableQuantity);

    /**
     * 修改物料标签已领取数量
     */
    public int updateReceivedQuantity(@Param("labelId") Long labelId, @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime,
        @Param("receivedQuantity") BigDecimal receivedQuantity);

    /**
     * 修改物料标签-上架
     */
    public int updatePutOn(@Param("labelId") Long labelId, @Param("warehouseCode") String warehouseCode, @Param("locationCode") String locationCode,
        @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);

    /**
     * 修改物料标签-下架
     */
    public int updatePutOff(@Param("labelId") Long labelId, @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);

    /**
     * 修改物料标签-调拨入库
     */
    public int updateAllotIn(@Param("labelId") Long labelId, @Param("warehouseCode") String warehouseCode, @Param("locationCode") String locationCode,
        @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);

    /**
     * 修改物料标签-调拨出库
     */
    public int updateAllotOut(@Param("labelId") Long labelId, @Param("updateBy") String updateBy, @Param("updateTime") Date updateTime);

    /**
     * 删除物料标签
     *
     * @param labelId 物料标签主键
     * @return 结果
     */
    public int deleteStockMatLabelByLabelId(Long labelId);

    /**
     * 批量删除物料标签
     *
     * @param labelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockMatLabelByLabelIds(Long[] labelIds);
}
