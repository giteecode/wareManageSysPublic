package com.ruoyi.stock.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockOutReturnDetailMapper;
import com.ruoyi.stock.domain.StockOutReturnDetail;
import com.ruoyi.stock.service.IStockOutReturnDetailService;

/**
 * 出库退货详情Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockOutReturnDetailServiceImpl implements IStockOutReturnDetailService {
    @Autowired
    private StockOutReturnDetailMapper stockOutReturnDetailMapper;

    /**
     * 查询出库退货详情
     *
     * @param detailId 出库退货详情主键
     * @return 出库退货详情
     */
    @Override
    public StockOutReturnDetail selectStockOutReturnDetailByDetailId(Long detailId) {
        return stockOutReturnDetailMapper.selectStockOutReturnDetailByDetailId(detailId);
    }

    /**
     * 查询出库退货详情列表
     *
     * @param stockOutReturnDetail 出库退货详情
     * @return 出库退货详情
     */
    @Override
    public List<StockOutReturnDetail> selectStockOutReturnDetailList(StockOutReturnDetail stockOutReturnDetail) {
        return stockOutReturnDetailMapper.selectStockOutReturnDetailList(stockOutReturnDetail);
    }

    /**
     * 查询出库退货详情列表
     */
    @Override
    public List<StockOutReturnDetail> selectStockOutReturnDetailListByReturnNo(String returnNo){
        return stockOutReturnDetailMapper.selectStockOutReturnDetailListByReturnNo(returnNo);
    }

    /**
     * 新增出库退货详情
     *
     * @param stockOutReturnDetail 出库退货详情
     * @return 结果
     */
    @Override
    public int insertStockOutReturnDetail(StockOutReturnDetail stockOutReturnDetail) {
        stockOutReturnDetail.setCreateTime(DateUtils.getNowDate());
        return stockOutReturnDetailMapper.insertStockOutReturnDetail(stockOutReturnDetail);
    }

    /**
     * 修改出库退货详情
     *
     * @param stockOutReturnDetail 出库退货详情
     * @return 结果
     */
    @Override
    public int updateStockOutReturnDetail(StockOutReturnDetail stockOutReturnDetail) {
        stockOutReturnDetail.setUpdateTime(DateUtils.getNowDate());
        return stockOutReturnDetailMapper.updateStockOutReturnDetail(stockOutReturnDetail);
    }

    /**
     * 批量删除出库退货详情
     *
     * @param detailIds 需要删除的出库退货详情主键
     * @return 结果
     */
    @Override
    public int deleteStockOutReturnDetailByDetailIds(Long[] detailIds) {
        return stockOutReturnDetailMapper.deleteStockOutReturnDetailByDetailIds(detailIds);
    }

    /**
     * 删除出库退货详情信息
     *
     * @param detailId 出库退货详情主键
     * @return 结果
     */
    @Override
    public int deleteStockOutReturnDetailByDetailId(Long detailId) {
        return stockOutReturnDetailMapper.deleteStockOutReturnDetailByDetailId(detailId);
    }
}
