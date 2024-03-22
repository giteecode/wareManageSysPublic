package com.ruoyi.stock.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.bean.request.MatLabelRequestData;
import com.ruoyi.common.bean.request.PutOffRequestBody;
import com.ruoyi.common.bean.request.PutOnRequestBody;
import com.ruoyi.common.bean.typeEnum.StockRecordTypeEnum;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.mapper.StockMatLabelMapper;
import com.ruoyi.stock.mapper.StockRecordMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockInfoMapper;
import com.ruoyi.stock.domain.StockInfo;
import com.ruoyi.stock.service.IStockInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockInfoServiceImpl implements IStockInfoService {
    @Autowired
    private StockInfoMapper stockInfoMapper;
    @Autowired
    private StockMatLabelMapper stockMatLabelMapper;
    @Autowired
    private StockRecordMapper stockRecordMapper;

    /**
     * 查询库存信息
     *
     * @param infoId 库存信息主键
     * @return 库存信息
     */
    @Override
    public StockInfo selectStockInfoByInfoId(Long infoId) {
        return stockInfoMapper.selectStockInfoByInfoId(infoId);
    }

    /**
     * 查询库存信息列表
     *
     * @param stockInfo 库存信息
     * @return 库存信息
     */
    @Override
    public List<StockInfo> selectStockInfoList(StockInfo stockInfo) {
        return stockInfoMapper.selectStockInfoList(stockInfo);
    }

    /**
     * 查询库存汇总列表
     */
    @Override
    public List<StockInfo> selectStockInfoStatsList(StockInfo stockInfo){
        return stockInfoMapper.selectStockInfoStatsList(stockInfo);
    }

    /**
     * 查询推荐货位
     */
    @Override
    public String selectRecommendLocation(String matCode, String warehouseCode){
        return stockInfoMapper.selectRecommendLocation(matCode, warehouseCode);
    }

    /**
     * 新增库存信息
     *
     * @param stockInfo 库存信息
     * @return 结果
     */
    @Override
    public int insertStockInfo(StockInfo stockInfo) {
        stockInfo.setCreateTime(DateUtils.getNowDate());
        return stockInfoMapper.insertStockInfo(stockInfo);
    }

    /**
     * 修改库存信息
     *
     * @param stockInfo 库存信息
     * @return 结果
     */
    @Override
    public int updateStockInfo(StockInfo stockInfo) {
        stockInfo.setUpdateTime(DateUtils.getNowDate());
        return stockInfoMapper.updateStockInfo(stockInfo);
    }

    /**
     * 批量删除库存信息
     *
     * @param infoIds 需要删除的库存信息主键
     * @return 结果
     */
    @Override
    public int deleteStockInfoByInfoIds(Long[] infoIds) {
        return stockInfoMapper.deleteStockInfoByInfoIds(infoIds);
    }

    /**
     * 删除库存信息信息
     *
     * @param infoId 库存信息主键
     * @return 结果
     */
    @Override
    public int deleteStockInfoByInfoId(Long infoId) {
        return stockInfoMapper.deleteStockInfoByInfoId(infoId);
    }

    /**
     * 扫码提交上架
     */
    @Override
    @Transactional
    public AjaxResult submitPutOn(String username, PutOnRequestBody putOnRequestBody){
        String locationCode = putOnRequestBody.getLocationCode();
        String warehouseCode = putOnRequestBody.getWarehouseCode();
        List<MatLabelRequestData> matLabelList = putOnRequestBody.getMatLabelList();
        if(StringUtils.isEmpty(locationCode) || CollectionUtils.isEmpty(matLabelList)){
            return AjaxResult.error("系统繁忙，请稍后再试！");
        }
        Date nowDate = DateUtils.getNowDate();
        StockRecord record = null;
        for(MatLabelRequestData label : matLabelList){
            label.setWarehouseCode(warehouseCode);
            label.setLocationCode(locationCode);
            BigDecimal quantity = label.getUsableQuantity().subtract(label.getReceivedQuantity());
            //修改库存信息
            stockInfoMapper.updateQuantity(warehouseCode, locationCode, label.getBatch(), label.getSupplierCode(),
                    label.getMatCode(), quantity.multiply(new BigDecimal("-1")));
            //新增库存操作信息
            record = new StockRecord();
            BeanUtils.copyBeanProp(record, label);
            record.setRecordType(StockRecordTypeEnum.UPPER.getValue());
            record.setQuantity(quantity);
            record.setCreateBy(username);
            record.setCreateTime(nowDate);
            stockRecordMapper.insertStockRecord(record);
            //修改物料标签
            stockMatLabelMapper.updatePutOn(label.getLabelId(), warehouseCode, locationCode, username, nowDate);
        }
        return AjaxResult.success("提交成功");
    }

    /**
     * 扫码提交下架
     */
    @Override
    @Transactional
    public AjaxResult submitPutOff(String username, PutOffRequestBody putOffRequestBody){
        String locationCode = putOffRequestBody.getLocationCode();
        List<MatLabelRequestData> matLabelList = putOffRequestBody.getMatLabelList();
        if(StringUtils.isEmpty(locationCode) || CollectionUtils.isEmpty(matLabelList)){
            return AjaxResult.error("系统繁忙，请稍后再试！");
        }
        Date nowDate = DateUtils.getNowDate();
        StockRecord record = null;
        for(MatLabelRequestData label : matLabelList){
            //修改库存信息
            BigDecimal quantity = label.getUsableQuantity().subtract(label.getReceivedQuantity());
            stockInfoMapper.updateQuantity(label.getWarehouseCode(), locationCode, label.getBatch(), label.getSupplierCode(),
                    label.getMatCode(), quantity);
            //新增库存操作信息
            record = new StockRecord();
            BeanUtils.copyBeanProp(record, label);
            record.setRecordType(StockRecordTypeEnum.LOWER.getValue());
            record.setQuantity(quantity);
            record.setCreateBy(username);
            record.setCreateTime(nowDate);
            stockRecordMapper.insertStockRecord(record);
            //修改物料标签
            stockMatLabelMapper.updatePutOff(label.getLabelId(), username, nowDate);
        }
        return AjaxResult.success("提交成功");
    }

}
