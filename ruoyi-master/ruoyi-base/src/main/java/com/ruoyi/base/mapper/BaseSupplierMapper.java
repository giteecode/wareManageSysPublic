package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.BaseSupplier;

/**
 * 供应商Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
public interface BaseSupplierMapper 
{
    /**
     * 查询供应商
     * 
     * @param supplierId 供应商主键
     * @return 供应商
     */
    public BaseSupplier selectBaseSupplierBySupplierId(Long supplierId);

    /**
     * 查询供应商列表
     * 
     * @param baseSupplier 供应商
     * @return 供应商集合
     */
    public List<BaseSupplier> selectBaseSupplierList(BaseSupplier baseSupplier);

    /**
     * 新增供应商
     * 
     * @param baseSupplier 供应商
     * @return 结果
     */
    public int insertBaseSupplier(BaseSupplier baseSupplier);

    /**
     * 修改供应商
     * 
     * @param baseSupplier 供应商
     * @return 结果
     */
    public int updateBaseSupplier(BaseSupplier baseSupplier);

    /**
     * 删除供应商
     * 
     * @param supplierId 供应商主键
     * @return 结果
     */
    public int deleteBaseSupplierBySupplierId(Long supplierId);

    /**
     * 批量删除供应商
     * 
     * @param supplierIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseSupplierBySupplierIds(Long[] supplierIds);
}
