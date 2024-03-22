package com.ruoyi.stock.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockMatLabelMapper;
import com.ruoyi.stock.domain.StockMatLabel;
import com.ruoyi.stock.service.IStockMatLabelService;

/**
 * 物料标签Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockMatLabelServiceImpl implements IStockMatLabelService {
    @Autowired
    private StockMatLabelMapper stockMatLabelMapper;

    /**
     * 查询物料标签
     *
     * @param labelId 物料标签主键
     * @return 物料标签
     */
    @Override
    public StockMatLabel selectStockMatLabelByLabelId(Long labelId) {
        return stockMatLabelMapper.selectStockMatLabelByLabelId(labelId);
    }

    /**
     * 查询物料标签列表
     *
     * @param stockMatLabel 物料标签
     * @return 物料标签
     */
    @Override
    public List<StockMatLabel> selectStockMatLabelList(StockMatLabel stockMatLabel) {
        return stockMatLabelMapper.selectStockMatLabelList(stockMatLabel);
    }

    /**
     * 查询物料标签列表（弹窗）
     *
     * @param stockMatLabel 物料标签
     * @return 物料标签
     */
    @Override
    public List<StockMatLabel> selectStockMatLabelListDialog(StockMatLabel stockMatLabel) {
        return stockMatLabelMapper.selectStockMatLabelListDialog(stockMatLabel);
    }

    /**
     * 新增物料标签
     *
     * @param stockMatLabel 物料标签
     * @return 结果
     */
    @Override
    public int insertStockMatLabel(StockMatLabel stockMatLabel) {
        stockMatLabel.setCreateTime(DateUtils.getNowDate());
        return stockMatLabelMapper.insertStockMatLabel(stockMatLabel);
    }

    /**
     * 修改物料标签
     *
     * @param stockMatLabel 物料标签
     * @return 结果
     */
    @Override
    public int updateStockMatLabel(StockMatLabel stockMatLabel) {
        stockMatLabel.setUpdateTime(DateUtils.getNowDate());
        return stockMatLabelMapper.updateStockMatLabel(stockMatLabel);
    }

    /**
     * 批量删除物料标签
     *
     * @param labelIds 需要删除的物料标签主键
     * @return 结果
     */
    @Override
    public int deleteStockMatLabelByLabelIds(Long[] labelIds) {
        return stockMatLabelMapper.deleteStockMatLabelByLabelIds(labelIds);
    }

    /**
     * 删除物料标签信息
     *
     * @param labelId 物料标签主键
     * @return 结果
     */
    @Override
    public int deleteStockMatLabelByLabelId(Long labelId) {
        return stockMatLabelMapper.deleteStockMatLabelByLabelId(labelId);
    }
}
