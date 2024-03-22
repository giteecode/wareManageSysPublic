package com.ruoyi.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.bean.typeEnum.OrderStatusEnum;
import com.ruoyi.common.bean.typeEnum.OutOrderReturnTypeEnum;
import com.ruoyi.common.bean.typeEnum.StockRecordTypeEnum;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OrderNoUtil;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.stock.domain.StockInReturnDetail;
import com.ruoyi.stock.domain.StockOutReturnDetail;
import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.mapper.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.domain.StockOutReturn;
import com.ruoyi.stock.service.IStockOutReturnService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出库单退货Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockOutReturnServiceImpl implements IStockOutReturnService {
    @Autowired
    private StockOutReturnMapper stockOutReturnMapper;
    @Autowired
    private StockOutReturnDetailMapper stockOutReturnDetailMapper;
    @Autowired
    private StockMatLabelMapper stockMatLabelMapper;
    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Autowired
    private StockRecordMapper stockRecordMapper;

    /**
     * 查询出库单退货数量
     */
    @Override
    public Map<String, Long> selectStockOutReturnTotal(Date selectDate){
        return stockOutReturnMapper.selectStockOutReturnTotal(selectDate);
    }

    /**
     * 查询出库单退货
     *
     * @param returnId 出库单退货主键
     * @return 出库单退货
     */
    @Override
    public StockOutReturn selectStockOutReturnByReturnId(Long returnId) {
        return stockOutReturnMapper.selectStockOutReturnByReturnId(returnId);
    }

    /**
     * 查询出库单退货
     */
    @Override
    public StockOutReturn selectStockOutReturnByReturnNo(String returnNo){
        return stockOutReturnMapper.selectStockOutReturnByReturnNo(returnNo);
    }

    /**
     * 查询出库单退货列表
     *
     * @param stockOutReturn 出库单退货
     * @return 出库单退货
     */
    @Override
    public List<StockOutReturn> selectStockOutReturnList(StockOutReturn stockOutReturn) {
        return stockOutReturnMapper.selectStockOutReturnList(stockOutReturn);
    }

    /**
     * 新增出库单退货
     *
     * @param outReturn 出库单退货
     * @return 结果
     */
    @Override
    public int insertStockOutReturn(String username, StockOutReturn outReturn) {
        Date nowDate = DateUtils.getNowDate();
        String returnNo = OrderNoUtil.getOutOrderReturnNo(outReturn.getReturnType());
        //出库退货单
        outReturn.setReturnNo(returnNo);
        outReturn.setReturnStatus(OrderStatusEnum.CREATED.getValue());
        outReturn.setCreateBy(username);
        outReturn.setCreateTime(nowDate);
        //出库退货详情
        List<StockOutReturnDetail> detailList = outReturn.getDetailList();
        int i = 1;
        Iterator<StockOutReturnDetail> iterator = detailList.iterator();
        StockOutReturnDetail detail = null;
        while (iterator.hasNext()) {
            detail = iterator.next();
            if (detail.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                iterator.remove();
                continue;
            }
            detail.setReturnNo(returnNo);
            detail.setLineNo(i);
            detail.setCreateBy(username);
            detail.setCreateTime(nowDate);
            i++;
        }
        stockOutReturnDetailMapper.insertStockOutReturnDetailList(detailList);
        return stockOutReturnMapper.insertStockOutReturn(outReturn);
    }

    /**
     * 修改出库单退货
     *
     * @param stockOutReturn 出库单退货
     * @return 结果
     */
    @Override
    public int updateStockOutReturn(StockOutReturn stockOutReturn) {
        stockOutReturn.setUpdateTime(DateUtils.getNowDate());
        return stockOutReturnMapper.updateStockOutReturn(stockOutReturn);
    }

    /**
     * 批量删除出库单退货
     *
     * @param returnIds 需要删除的出库单退货主键
     * @return 结果
     */
    @Override
    public int deleteStockOutReturnByReturnIds(Long[] returnIds) {
        if(ArrayUtils.isNotEmpty(returnIds)){
            for(Long returnId : returnIds){
                stockOutReturnDetailMapper.deleteStockOutReturnDetailByReturnId(returnId);
            }
        }
        return stockOutReturnMapper.deleteStockOutReturnByReturnIds(returnIds);
    }

    /**
     * 删除出库单退货信息
     *
     * @param returnId 出库单退货主键
     * @return 结果
     */
    @Override
    public int deleteStockOutReturnByReturnId(Long returnId) {
        return stockOutReturnMapper.deleteStockOutReturnByReturnId(returnId);
    }

    /**
     * 扫码提交出库单退货-退货
     */
    @Override
    @Transactional
    public AjaxResult submitOutReturn(String username, StockOutReturn stockOutReturn){
        Date nowDate = DateUtils.getNowDate();
        List<StockOutReturnDetail> detailList = stockOutReturn.getDetailList();
        if(CollectionUtils.isEmpty(detailList)){
            return AjaxResult.error("系统繁忙，请稍后再试！");
        }
        String warehouseCode = stockOutReturn.getWarehouseCode();
        String workshopCode = stockOutReturn.getWorkshopCode();
        StockRecord record = null;
        for(StockOutReturnDetail detail : detailList){
            Long labelId = detail.getLabelId();
            BigDecimal returnQuantity = detail.getReturnQuantity();
            //修改退货单详情
            detail.setUpdateBy(username);
            detail.setUpdateTime(nowDate);
            stockOutReturnDetailMapper.updateStockOutReturnDetail(detail);
            //修改物料标签
            stockMatLabelMapper.updateReceivedQuantity(labelId, username, nowDate, returnQuantity.multiply(new BigDecimal("-1")));
            //修改库存信息
            stockInfoMapper.updateQuantity(warehouseCode, detail.getLocationCode(), detail.getBatch(),
                    detail.getSupplierCode(), detail.getMatCode(), detail.getReturnQuantity().multiply(new BigDecimal("-1")));
            //新增库存操作信息
            record = new StockRecord();
            BeanUtils.copyBeanProp(record, detail);
            record.setOrderNo(stockOutReturn.getOrderNo());
            record.setRecordType(StockRecordTypeEnum.getStockOutReturnRecordType(stockOutReturn.getReturnType()));
            record.setCreateBy(username);
            record.setCreateTime(nowDate);
            stockRecordMapper.insertStockRecord(record);
        }
        //修改退货单
        stockOutReturn.setReturnStatus(OrderStatusEnum.RETURNED.getValue());
        stockOutReturn.setReturnNo(stockOutReturn.getReturnNo());
        stockOutReturn.setUpdateBy(username);
        stockOutReturn.setUpdateTime(nowDate);
        stockOutReturnMapper.updateStockOutReturn(stockOutReturn);
        return AjaxResult.success("提交成功");
    }

}
