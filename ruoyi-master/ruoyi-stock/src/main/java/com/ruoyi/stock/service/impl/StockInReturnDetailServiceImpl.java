package com.ruoyi.stock.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockInReturnDetailMapper;
import com.ruoyi.stock.domain.StockInReturnDetail;
import com.ruoyi.stock.service.IStockInReturnDetailService;

/**
 * 入库单退货详情Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockInReturnDetailServiceImpl implements IStockInReturnDetailService {
    @Autowired
    private StockInReturnDetailMapper stockInReturnDetailMapper;

    /**
     * 查询入库单退货详情
     *
     * @param detailId 入库单退货详情主键
     * @return 入库单退货详情
     */
    @Override
    public StockInReturnDetail selectStockInReturnDetailByDetailId(Long detailId) {
        return stockInReturnDetailMapper.selectStockInReturnDetailByDetailId(detailId);
    }

    /**
     * 查询入库单退货详情列表
     *
     * @param stockInReturnDetail 入库单退货详情
     * @return 入库单退货详情
     */
    @Override
    public List<StockInReturnDetail> selectStockInReturnDetailList(StockInReturnDetail stockInReturnDetail) {
        return stockInReturnDetailMapper.selectStockInReturnDetailList(stockInReturnDetail);
    }

    /**
     * 查询入库单退货详情列表
     */
    @Override
    public List<StockInReturnDetail> selectStockInReturnDetailListByReturnNo(String returnNo){
        return stockInReturnDetailMapper.selectStockInReturnDetailListByReturnNo(returnNo);
    }

    /**
     * 新增入库单退货详情
     *
     * @param stockInReturnDetail 入库单退货详情
     * @return 结果
     */
    @Override
    public int insertStockInReturnDetail(StockInReturnDetail stockInReturnDetail) {
        stockInReturnDetail.setCreateTime(DateUtils.getNowDate());
        return stockInReturnDetailMapper.insertStockInReturnDetail(stockInReturnDetail);
    }

    /**
     * 修改入库单退货详情
     *
     * @param stockInReturnDetail 入库单退货详情
     * @return 结果
     */
    @Override
    public int updateStockInReturnDetail(StockInReturnDetail stockInReturnDetail) {
        stockInReturnDetail.setUpdateTime(DateUtils.getNowDate());
        return stockInReturnDetailMapper.updateStockInReturnDetail(stockInReturnDetail);
    }

    /**
     * 批量删除入库单退货详情
     *
     * @param detailIds 需要删除的入库单退货详情主键
     * @return 结果
     */
    @Override
    public int deleteStockInReturnDetailByDetailIds(Long[] detailIds) {
        return stockInReturnDetailMapper.deleteStockInReturnDetailByDetailIds(detailIds);
    }

    /**
     * 删除入库单退货详情信息
     *
     * @param detailId 入库单退货详情主键
     * @return 结果
     */
    @Override
    public int deleteStockInReturnDetailByDetailId(Long detailId) {
        return stockInReturnDetailMapper.deleteStockInReturnDetailByDetailId(detailId);
    }
}
