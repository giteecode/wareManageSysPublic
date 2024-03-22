package com.ruoyi.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.bean.typeEnum.InOrderCheckStatusEnum;
import com.ruoyi.common.bean.typeEnum.InOrderTypeEnum;
import com.ruoyi.common.bean.typeEnum.OrderStatusEnum;
import com.ruoyi.common.bean.typeEnum.StockRecordTypeEnum;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.Arith;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OrderNoUtil;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.stock.domain.*;
import com.ruoyi.stock.domain.stats.StockInStats;
import com.ruoyi.stock.mapper.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.service.IStockInOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入库单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockInOrderServiceImpl implements IStockInOrderService {
    @Autowired
    private StockInOrderMapper stockInOrderMapper;
    @Autowired
    private StockInDetailMapper stockInDetailMapper;
    @Autowired
    private StockMatLabelMapper stockMatLabelMapper;
    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Autowired
    private StockRecordMapper stockRecordMapper;

    /**
     * 查询入库单数量
     */
    @Override
    public Map<String, Long> selectStockInOrderTotal(Date selectDate){
        return stockInOrderMapper.selectStockInOrderTotal(selectDate);
    }

    /**
     * 查询入库单
     *
     * @param orderId 入库单主键
     * @return 入库单
     */
    @Override
    public StockInOrder selectStockInOrderByOrderId(Long orderId) {
        return stockInOrderMapper.selectStockInOrderByOrderId(orderId);
    }

    /**
     * 查询入库单
     *
     * @param orderNo
     * @return 入库单
     */
    public StockInOrder selectStockInOrderByOrderNo(String orderNo){
        return stockInOrderMapper.selectStockInOrderByOrderNo(orderNo);
    }

    /**
     * 查询入库单列表
     *
     * @param stockInOrder 入库单
     * @return 入库单
     */
    @Override
    public List<StockInOrder> selectStockInOrderList(StockInOrder stockInOrder) {
        return stockInOrderMapper.selectStockInOrderList(stockInOrder);
    }

    /**
     * 新增入库单
     */
    @Override
    @Transactional
    public int insertStockInOrder(String username, StockInOrder stockInOrder) {
        //入库单信息
        String orderType = stockInOrder.getOrderType();
        String orderNo = OrderNoUtil.getInOrderNo(orderType);
        Date nowDate = DateUtils.getNowDate();
        stockInOrder.setOrderNo(orderNo);
        stockInOrder.setOrderStatus(OrderStatusEnum.CREATED.getValue());
        stockInOrder.setCheckStatus(InOrderCheckStatusEnum.UN_CHECKOUT.getValue());
        stockInOrder.setCreateBy(username);
        stockInOrder.setCreateTime(nowDate);
        //入库单详情
        List<StockInDetail> detailList = stockInOrder.getDetailList();
        if(CollectionUtils.isNotEmpty(detailList)){
            int i = 1;
            StockMatLabel label = null;
            for(StockInDetail detail : detailList){
                detail.setOrderNo(orderNo);
                detail.setLineNo(i);
                detail.setQualifiedQuantity(detail.getQuantity());
                detail.setCreateBy(username);
                detail.setCreateTime(nowDate);
                //修改物料标签-订单号
                label = new StockMatLabel();
                label.setLabelId(detail.getLabelId());
                label.setOrderNo(orderNo);
                label.setOrderType(orderType);
                stockMatLabelMapper.updateStockMatLabel(label);
                i++;
            }
        }
        //新增入库单详情
        stockInDetailMapper.insertStockInDetailList(detailList);
        //新增入库单
        return stockInOrderMapper.insertStockInOrder(stockInOrder);
    }

    /**
     * 修改入库单
     *
     * @param stockInOrder 入库单
     * @return 结果
     */
    @Override
    public int updateStockInOrder(StockInOrder stockInOrder) {
        stockInOrder.setUpdateTime(DateUtils.getNowDate());
        return stockInOrderMapper.updateStockInOrder(stockInOrder);
    }

    /**
     * 批量删除入库单
     *
     * @param orderIds 需要删除的入库单主键
     * @return 结果
     */
    @Override
    public int deleteStockInOrderByOrderIds(Long[] orderIds) {
        if(ArrayUtils.isNotEmpty(orderIds)){
            for(Long orderId : orderIds){
                stockInDetailMapper.deleteStockInDetailByOrderId(orderId);
            }
        }
        return stockInOrderMapper.deleteStockInOrderByOrderIds(orderIds);
    }

    /**
     * 删除入库单信息
     *
     * @param orderId 入库单主键
     * @return 结果
     */
    @Override
    public int deleteStockInOrderByOrderId(Long orderId) {
        return stockInOrderMapper.deleteStockInOrderByOrderId(orderId);
    }

    /**
     * 入库单质检
     */
    @Override
    @Transactional
    public int inOrderCheck(String username, List<StockInDetail> detailList){
        Date nowDate = DateUtils.getNowDate();
        for(StockInDetail detail : detailList){
            //修改入库单详情合格数量
            detail.setUpdateBy(username);
            detail.setUpdateTime(nowDate);
            stockInDetailMapper.updateStockInDetail(detail);
        }
        //修改入库单状态
        String orderNo = detailList.get(0).getOrderNo();
        StockInOrder order = new StockInOrder();
        order.setOrderNo(orderNo);
        order.setCheckBy(username);
        order.setCheckStatus(InOrderCheckStatusEnum.CHECKOUT.getValue());
        order.setUpdateBy(username);
        order.setUpdateTime(nowDate);
        return stockInOrderMapper.updateStockInOrderByOrderNo(order);
    }

    /**
     * 扫码提交入库单-入库
     */
    @Override
    @Transactional
    public AjaxResult submitStockIn(String username, StockInOrder inOrder){
        List<StockInDetail> detailList = inOrder.getDetailList();
        if(CollectionUtils.isEmpty(detailList)){
            return AjaxResult.error("系统繁忙，请稍后再试！");
        }
        String recordType = StockRecordTypeEnum.getStockInRecordType(inOrder.getOrderType());
        StockInfo info = null;
        StockRecord record = null;
        Date nowDate = DateUtils.getNowDate();
        for(StockInDetail detail : detailList){
            BigDecimal qualifiedQuantity = detail.getQualifiedQuantity();
            String warehouseCode = detail.getWarehouseCode();
            String locationCode = detail.getLocationCode();
            //修改物料标签
            stockMatLabelMapper.updateUsableQuantity(detail.getLabelId(), username, nowDate, warehouseCode, locationCode, qualifiedQuantity);
            //修改入库单详情信息
            detail.setStockInQuantity(qualifiedQuantity);
            detail.setUpdateBy(username);
            detail.setUpdateTime(nowDate);
            stockInDetailMapper.updateStockInDetail(detail);
            //修改库存信息
            info = new StockInfo();
            info.setWarehouseCode(warehouseCode);
            info.setLocationCode(locationCode);
            info.setMatCode(detail.getMatCode());
            info.setBatch(detail.getBatch());
            info.setSupplierCode(detail.getSupplierCode());
            List<StockInfo> infoList = stockInfoMapper.selectStockInfoList(info);
            if(CollectionUtils.isNotEmpty(infoList)){
                info = infoList.get(0);
                info.setQuantity(qualifiedQuantity.add(info.getQuantity()));
                info.setUpdateBy(username);
                info.setUpdateTime(nowDate);
                stockInfoMapper.updateStockInfo(info);
            }else{
                BeanUtils.copyBeanProp(info, detail);
                info.setQuantity(qualifiedQuantity);
                info.setCreateBy(username);
                info.setCreateTime(nowDate);
                stockInfoMapper.insertStockInfo(info);
            }
            //新增库存操作流水信息
            record = new StockRecord();
            BeanUtils.copyBeanProp(record, detail);
            record.setRecordType(recordType);
            record.setQuantity(qualifiedQuantity);
            record.setOrderNo(inOrder.getOrderNo());
            record.setCreateBy(username);
            record.setCreateTime(nowDate);
            stockRecordMapper.insertStockRecord(record);
        }
        //修改入库单信息
        inOrder.setOrderStatus(OrderStatusEnum.ENTERED.getValue());
        inOrder.setUpdateBy(username);
        inOrder.setUpdateTime(nowDate);
        stockInOrderMapper.updateStockInOrder(inOrder);
        return AjaxResult.success("提交成功");
    }

    /**
     * 入库统计
     */
    @Override
    public List<StockInStats> selectStockInStatsList(String matCode, String matName){
        return stockInDetailMapper.selectStockInStatsList(matCode, matName);
    }

}
