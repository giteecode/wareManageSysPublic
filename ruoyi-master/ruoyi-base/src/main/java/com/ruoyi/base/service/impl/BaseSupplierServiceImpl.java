package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BaseSupplierMapper;
import com.ruoyi.base.domain.BaseSupplier;
import com.ruoyi.base.service.IBaseSupplierService;

/**
 * 供应商Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
@Service
public class BaseSupplierServiceImpl implements IBaseSupplierService 
{
    @Autowired
    private BaseSupplierMapper baseSupplierMapper;

    /**
     * 查询供应商
     * 
     * @param supplierId 供应商主键
     * @return 供应商
     */
    @Override
    public BaseSupplier selectBaseSupplierBySupplierId(Long supplierId)
    {
        return baseSupplierMapper.selectBaseSupplierBySupplierId(supplierId);
    }

    /**
     * 查询供应商列表
     * 
     * @param baseSupplier 供应商
     * @return 供应商
     */
    @Override
    public List<BaseSupplier> selectBaseSupplierList(BaseSupplier baseSupplier)
    {
        return baseSupplierMapper.selectBaseSupplierList(baseSupplier);
    }

    /**
     * 新增供应商
     * 
     * @param baseSupplier 供应商
     * @return 结果
     */
    @Override
    public int insertBaseSupplier(BaseSupplier baseSupplier)
    {
        baseSupplier.setCreateTime(DateUtils.getNowDate());
        return baseSupplierMapper.insertBaseSupplier(baseSupplier);
    }

    /**
     * 修改供应商
     * 
     * @param baseSupplier 供应商
     * @return 结果
     */
    @Override
    public int updateBaseSupplier(BaseSupplier baseSupplier)
    {
        baseSupplier.setUpdateTime(DateUtils.getNowDate());
        return baseSupplierMapper.updateBaseSupplier(baseSupplier);
    }

    /**
     * 批量删除供应商
     * 
     * @param supplierIds 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteBaseSupplierBySupplierIds(Long[] supplierIds)
    {
        return baseSupplierMapper.deleteBaseSupplierBySupplierIds(supplierIds);
    }

    /**
     * 删除供应商信息
     * 
     * @param supplierId 供应商主键
     * @return 结果
     */
    @Override
    public int deleteBaseSupplierBySupplierId(Long supplierId)
    {
        return baseSupplierMapper.deleteBaseSupplierBySupplierId(supplierId);
    }
}
