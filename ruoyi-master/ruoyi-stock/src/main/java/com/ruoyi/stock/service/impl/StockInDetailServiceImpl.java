package com.ruoyi.stock.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockInDetailMapper;
import com.ruoyi.stock.domain.StockInDetail;
import com.ruoyi.stock.service.IStockInDetailService;

/**
 * 入库单详情Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockInDetailServiceImpl implements IStockInDetailService {
    @Autowired
    private StockInDetailMapper stockInDetailMapper;

    /**
     * 查询入库单详情
     *
     * @param detailId 入库单详情主键
     * @return 入库单详情
     */
    @Override
    public StockInDetail selectStockInDetailByDetailId(Long detailId) {
        return stockInDetailMapper.selectStockInDetailByDetailId(detailId);
    }

    /**
     * 查询入库单详情列表
     *
     * @param orderNo
     * @return 入库单详情集合
     */
    public List<StockInDetail> selectStockInDetailListByOrderNo(String orderNo){
        return stockInDetailMapper.selectStockInDetailListByOrderNo(orderNo);
    }

    /**
     * 查询入库单详情列表
     *
     * @param stockInDetail 入库单详情
     * @return 入库单详情
     */
    @Override
    public List<StockInDetail> selectStockInDetailList(StockInDetail stockInDetail) {
        return stockInDetailMapper.selectStockInDetailList(stockInDetail);
    }

    /**
     * 新增入库单详情
     *
     * @param stockInDetail 入库单详情
     * @return 结果
     */
    @Override
    public int insertStockInDetail(StockInDetail stockInDetail) {
        stockInDetail.setCreateTime(DateUtils.getNowDate());
        return stockInDetailMapper.insertStockInDetail(stockInDetail);
    }

    /**
     * 修改入库单详情
     *
     * @param stockInDetail 入库单详情
     * @return 结果
     */
    @Override
    public int updateStockInDetail(StockInDetail stockInDetail) {
        stockInDetail.setUpdateTime(DateUtils.getNowDate());
        return stockInDetailMapper.updateStockInDetail(stockInDetail);
    }

    /**
     * 批量删除入库单详情
     *
     * @param detailIds 需要删除的入库单详情主键
     * @return 结果
     */
    @Override
    public int deleteStockInDetailByDetailIds(Long[] detailIds) {
        return stockInDetailMapper.deleteStockInDetailByDetailIds(detailIds);
    }

    /**
     * 删除入库单详情信息
     *
     * @param detailId 入库单详情主键
     * @return 结果
     */
    @Override
    public int deleteStockInDetailByDetailId(Long detailId) {
        return stockInDetailMapper.deleteStockInDetailByDetailId(detailId);
    }
}
