package com.ruoyi.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.bean.typeEnum.AllotProgressEnum;
import com.ruoyi.common.bean.typeEnum.OrderStatusEnum;
import com.ruoyi.common.bean.typeEnum.StockRecordTypeEnum;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OrderNoUtil;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.stock.domain.StockAllotDetail;
import com.ruoyi.stock.domain.StockInfo;
import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.mapper.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.domain.StockAllotOrder;
import com.ruoyi.stock.service.IStockAllotOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调拨单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-08-05
 */
@Service
public class StockAllotOrderServiceImpl implements IStockAllotOrderService {
    @Autowired
    private StockAllotOrderMapper stockAllotOrderMapper;
    @Autowired
    private StockAllotDetailMapper stockAllotDetailMapper;
    @Autowired
    private StockMatLabelMapper stockMatLabelMapper;
    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Autowired
    private StockRecordMapper stockRecordMapper;

    /**
     * 查询调拨单
     *
     * @param allotId 调拨单主键
     * @return 调拨单
     */
    @Override
    public StockAllotOrder selectStockAllotOrderByAllotId(Long allotId) {
        return stockAllotOrderMapper.selectStockAllotOrderByAllotId(allotId);
    }

    /**
     * 查询调拨单
     */
    @Override
    public StockAllotOrder selectStockAllotOrderByAllotNo(String allotNo){
        return stockAllotOrderMapper.selectStockAllotOrderByAllotNo(allotNo);
    }

    /**
     * 查询调拨单列表
     *
     * @param stockAllotOrder 调拨单
     * @return 调拨单
     */
    @Override
    public List<StockAllotOrder> selectStockAllotOrderList(StockAllotOrder stockAllotOrder) {
        return stockAllotOrderMapper.selectStockAllotOrderList(stockAllotOrder);
    }

    /**
     * 新增调拨单
     *
     * @param stockAllotOrder 调拨单
     * @return 结果
     */
    @Override
    public int insertStockAllotOrder(String username, StockAllotOrder stockAllotOrder) {
        stockAllotOrder.setAllotNo(OrderNoUtil.generateUniqueKey(OrderNoUtil.ALLOT_PREFIX));
        stockAllotOrder.setAllotStatus(OrderStatusEnum.CREATED.getValue());
        stockAllotOrder.setAllotProgress(AllotProgressEnum.CREATED.getValue());
        stockAllotOrder.setCreateBy(username);
        stockAllotOrder.setCreateTime(DateUtils.getNowDate());
        return stockAllotOrderMapper.insertStockAllotOrder(stockAllotOrder);
    }

    /**
     * 修改调拨单
     *
     * @param stockAllotOrder 调拨单
     * @return 结果
     */
    @Override
    public int updateStockAllotOrder(StockAllotOrder stockAllotOrder) {
        stockAllotOrder.setUpdateTime(DateUtils.getNowDate());
        return stockAllotOrderMapper.updateStockAllotOrder(stockAllotOrder);
    }

    /**
     * 批量删除调拨单
     *
     * @param allotIds 需要删除的调拨单主键
     * @return 结果
     */
    @Override
    public int deleteStockAllotOrderByAllotIds(Long[] allotIds) {
        return stockAllotOrderMapper.deleteStockAllotOrderByAllotIds(allotIds);
    }

    /**
     * 删除调拨单信息
     *
     * @param allotId 调拨单主键
     * @return 结果
     */
    @Override
    public int deleteStockAllotOrderByAllotId(Long allotId) {
        return stockAllotOrderMapper.deleteStockAllotOrderByAllotId(allotId);
    }

    /**
     * 扫码提交调拨单-拣货
     */
    @Override
    @Transactional
    public AjaxResult submitAllotPicking(String username, StockAllotOrder stockAllotOrder){
        List<StockAllotDetail> detailList = stockAllotOrder.getDetailList();
        if(CollectionUtils.isEmpty(detailList)){
            return AjaxResult.error("系统繁忙，请稍后再试！");
        }
        Date nowDate = DateUtils.getNowDate();
        String allotNo = stockAllotOrder.getAllotNo();
        String srcWarehouseCode = stockAllotOrder.getSrcWarehouseCode();
        StockRecord record = null;
        int i = 1;
        for(StockAllotDetail detail : detailList){
            String srcLocationCode = detail.getSrcLocationCode();
            //新增调拨详情信息
            detail.setAllotNo(allotNo);
            detail.setSrcWarehouseCode(srcWarehouseCode);
            detail.setDestWarehouseCode(stockAllotOrder.getDestWarehouseCode());
            detail.setLineNo(i);
            detail.setCreateBy(username);
            detail.setCreateTime(nowDate);
            stockAllotDetailMapper.insertStockAllotDetail(detail);
            //修改库存信息
            stockInfoMapper.updateQuantity(srcWarehouseCode, srcLocationCode, detail.getBatch(), detail.getSupplierCode(),
                detail.getMatCode(), detail.getQuantity());
            //新增库存操作信息
            record = new StockRecord();
            BeanUtils.copyBeanProp(record, detail);
            record.setWarehouseCode(srcWarehouseCode);
            record.setLocationCode(srcLocationCode);
            record.setRecordType(StockRecordTypeEnum.ALLOT_OUT.getValue());
            record.setOrderNo(allotNo);
            record.setCreateBy(username);
            record.setCreateTime(nowDate);
            stockRecordMapper.insertStockRecord(record);
            //修改标签信息
            stockMatLabelMapper.updateAllotOut(detail.getLabelId(), username, nowDate);
            i++;
        }
        //修改调拨单
        stockAllotOrder.setAllotProgress(AllotProgressEnum.PICKING.getValue());
        stockAllotOrder.setUpdateBy(username);
        stockAllotOrder.setUpdateTime(nowDate);
        stockAllotOrderMapper.updateStockAllotOrder(stockAllotOrder);
        return AjaxResult.success("提交成功");
    }

    /**
     * 扫码提交调拨单-接收
     */
    @Override
    @Transactional
    public AjaxResult submitAllotReceive(String username, StockAllotOrder stockAllotOrder){
        List<StockAllotDetail> detailList = stockAllotOrder.getDetailList();
        if(CollectionUtils.isEmpty(detailList)){
            return AjaxResult.error("系统繁忙，请稍后再试！");
        }
        Date nowDate = DateUtils.getNowDate();
        StockInfo info = null;
        StockRecord record = null;
        for(StockAllotDetail detail : detailList){
            String destWarehouseCode = detail.getDestWarehouseCode();
            String destLocationCode = detail.getDestLocationCode();
            BigDecimal signQuantity = detail.getSignQuantity();
            //修改调拨单详情
            stockAllotDetailMapper.updateStockAllotReceive(detail.getDetailId(), destLocationCode, signQuantity, username, nowDate);
            //修改物料标签
            stockMatLabelMapper.updateAllotIn(detail.getLabelId(), destWarehouseCode, destLocationCode, username, nowDate);
            //修改库存信息
            info = new StockInfo();
            info.setWarehouseCode(destWarehouseCode);
            info.setLocationCode(destLocationCode);
            info.setMatCode(detail.getMatCode());
            info.setBatch(detail.getBatch());
            info.setSupplierCode(detail.getSupplierCode());
            List<StockInfo> infoList = stockInfoMapper.selectStockInfoList(info);
            if(CollectionUtils.isNotEmpty(infoList)){
                info = infoList.get(0);
                info.setQuantity(signQuantity.add(info.getQuantity()));
                info.setUpdateBy(username);
                info.setUpdateTime(nowDate);
                stockInfoMapper.updateStockInfo(info);
            }else{
                BeanUtils.copyBeanProp(info, detail);
                info.setQuantity(signQuantity);
                info.setCreateBy(username);
                info.setCreateTime(nowDate);
                stockInfoMapper.insertStockInfo(info);
            }
            //新增存库操作信息
            record = new StockRecord();
            BeanUtils.copyBeanProp(record, detail);
            record.setWarehouseCode(destWarehouseCode);
            record.setLocationCode(destLocationCode);
            record.setRecordType(StockRecordTypeEnum.ALLOT_IN.getValue());
            record.setQuantity(signQuantity);
            record.setOrderNo(stockAllotOrder.getAllotNo());
            record.setCreateBy(username);
            record.setCreateTime(nowDate);
            stockRecordMapper.insertStockRecord(record);
        }
        //修改调拨单
        stockAllotOrder.setAllotProgress(AllotProgressEnum.RECEIVE.getValue());
        stockAllotOrder.setUpdateBy(username);
        stockAllotOrder.setUpdateTime(nowDate);
        stockAllotOrderMapper.updateStockAllotOrder(stockAllotOrder);
        return AjaxResult.success("提交成功");
    }

}
