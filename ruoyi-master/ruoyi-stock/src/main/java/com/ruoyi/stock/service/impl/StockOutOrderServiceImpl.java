package com.ruoyi.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.bean.request.StockOutRequestBody;
import com.ruoyi.common.bean.typeEnum.OrderStatusEnum;
import com.ruoyi.common.bean.typeEnum.StockRecordTypeEnum;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OrderNoUtil;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.stock.domain.StockMatLabel;
import com.ruoyi.stock.domain.StockOutDetail;
import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.domain.stats.StockOutStats;
import com.ruoyi.stock.mapper.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.domain.StockOutOrder;
import com.ruoyi.stock.service.IStockOutOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出库单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockOutOrderServiceImpl implements IStockOutOrderService {
    @Autowired
    private StockOutOrderMapper stockOutOrderMapper;
    @Autowired
    private StockOutDetailMapper stockOutDetailMapper;
    @Autowired
    private StockMatLabelMapper stockMatLabelMapper;
    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Autowired
    private StockRecordMapper stockRecordMapper;

    /**
     * 查询出库单数量
     */
    @Override
    public Map<String, Long> selectStockOutOrderTotal(Date selectDate){
        return stockOutOrderMapper.selectStockOutOrderTotal(selectDate);
    }

    /**
     * 查询出库单
     *
     * @param orderId 出库单主键
     * @return 出库单
     */
    @Override
    public StockOutOrder selectStockOutOrderByOrderId(Long orderId) {
        return stockOutOrderMapper.selectStockOutOrderByOrderId(orderId);
    }

    /**
     * 查询出库单
     *
     * @param orderNo
     * @return 出库单
     */
    @Override
    public StockOutOrder selectStockOutOrderByOrderNo(String orderNo){
        return stockOutOrderMapper.selectStockOutOrderByOrderNo(orderNo);
    }

    /**
     * 查询出库单列表
     *
     * @param stockOutOrder 出库单
     * @return 出库单
     */
    @Override
    public List<StockOutOrder> selectStockOutOrderList(StockOutOrder stockOutOrder) {
        return stockOutOrderMapper.selectStockOutOrderList(stockOutOrder);
    }

    /**
     * 新增出库单
     *
     * @param stockOutOrder 出库单
     * @return 结果
     */
    @Override
    public int insertStockOutOrder(String username, StockOutOrder stockOutOrder) {
        //出库单
        String orderNo = OrderNoUtil.getOutOrderNo(stockOutOrder.getOrderType());
        Date nowDate = DateUtils.getNowDate();
        stockOutOrder.setOrderNo(orderNo);
        stockOutOrder.setOrderStatus(OrderStatusEnum.CREATED.getValue());
        stockOutOrder.setCreateBy(username);
        stockOutOrder.setCreateTime(nowDate);
        //出库单详情
        List<StockOutDetail> detailList = stockOutOrder.getDetailList();
        if(CollectionUtils.isNotEmpty(detailList)){
            int i = 1;
            for(StockOutDetail detail : detailList){
                detail.setLineNo(i);
                detail.setWarehouseCode(stockOutOrder.getWarehouseCode());
                detail.setWorkshopCode(stockOutOrder.getWorkshopCode());
                detail.setProdOrderNo(stockOutOrder.getProdOrderNo());
                detail.setOrderNo(orderNo);
                detail.setCreateBy(username);
                detail.setCreateTime(nowDate);
                i++;
            }
        }
        stockOutDetailMapper.insertStockOutDetailList(detailList);
        return stockOutOrderMapper.insertStockOutOrder(stockOutOrder);
    }

    /**
     * 修改出库单
     *
     * @param stockOutOrder 出库单
     * @return 结果
     */
    @Override
    public int updateStockOutOrder(StockOutOrder stockOutOrder) {
        stockOutOrder.setUpdateTime(DateUtils.getNowDate());
        return stockOutOrderMapper.updateStockOutOrder(stockOutOrder);
    }

    /**
     * 批量删除出库单
     *
     * @param orderIds 需要删除的出库单主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteStockOutOrderByOrderIds(Long[] orderIds) {
        if(ArrayUtils.isNotEmpty(orderIds)){
            for(Long orderId : orderIds){
                stockOutDetailMapper.deleteStockOutDetailByOrderId(orderId);
            }
        }
        return stockOutOrderMapper.deleteStockOutOrderByOrderIds(orderIds);
    }

    /**
     * 删除出库单信息
     *
     * @param orderId 出库单主键
     * @return 结果
     */
    @Override
    public int deleteStockOutOrderByOrderId(Long orderId) {
        return stockOutOrderMapper.deleteStockOutOrderByOrderId(orderId);
    }

    /**
     * 扫码提交出库单-出库
     */
    @Override
    @Transactional
    public AjaxResult submitStockOut(String username, StockOutRequestBody stockOutRequestBody){
        String orderNo = stockOutRequestBody.getOrderNo();
        Map<Long, BigDecimal> receivedMap = stockOutRequestBody.getReceivedMap();
        StockOutOrder stockOutOrder = stockOutOrderMapper.selectStockOutOrderByOrderNo(orderNo);
        if(stockOutOrder == null || MapUtils.isEmpty(receivedMap)){
            return AjaxResult.error("系统繁忙，请稍后再试！");
        }
        Date nowDate = DateUtils.getNowDate();
        StockMatLabel matLabel = null;
        StockRecord record = null;
        for(Map.Entry<Long, BigDecimal> entry : receivedMap.entrySet()){
            Long labelId = entry.getKey();
            BigDecimal labelQuantity = entry.getValue();
            matLabel = stockMatLabelMapper.selectStockMatLabelByLabelId(labelId);
            if(matLabel == null ||
                    matLabel.getUsableQuantity().subtract(matLabel.getReceivedQuantity()).compareTo(labelQuantity) == -1){
                return AjaxResult.error("系统繁忙，请稍后再试！");
            }
            //更新标签
            stockMatLabelMapper.updateReceivedQuantity(labelId, username, nowDate, labelQuantity);
            //更新出库单详情
            stockOutDetailMapper.updateReceivedQuantity(orderNo, matLabel.getMatCode(), username, nowDate, labelQuantity);
            //更新库存信息
            stockInfoMapper.updateQuantity(matLabel.getWarehouseCode(), matLabel.getLocationCode(), matLabel.getBatch(),
                    matLabel.getSupplierCode(), matLabel.getMatCode(), labelQuantity);
            //新增库存操作流水信息
            record = new StockRecord();
            BeanUtils.copyBeanProp(record, matLabel);
            record.setRecordType(StockRecordTypeEnum.getStockOutRecordType(stockOutOrder.getOrderType()));
            record.setQuantity(labelQuantity);
            record.setOrderNo(stockOutOrder.getOrderNo());
            record.setWorkshopCode(stockOutOrder.getWorkshopCode());
            record.setCreateBy(username);
            record.setCreateTime(nowDate);
            stockRecordMapper.insertStockRecord(record);
        }
        //更新出库单
        stockOutOrder.setOrderStatus(OrderStatusEnum.RECEIVED.getValue());
        stockOutOrder.setUpdateBy(username);
        stockOutOrder.setUpdateTime(nowDate);
        stockOutOrderMapper.updateStockOutOrder(stockOutOrder);
        return AjaxResult.success("提交成功");
    }

    /**
     * 出库统计
     */
    @Override
    public List<StockOutStats> selectStockOutStatsList(String matCode, String matName){
        return stockOutDetailMapper.selectStockOutStatsList(matCode, matName);
    }

}
