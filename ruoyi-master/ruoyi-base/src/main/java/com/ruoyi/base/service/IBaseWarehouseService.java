package com.ruoyi.base.service;

import java.util.List;

import com.ruoyi.base.domain.BaseWarehouse;

/**
 * 仓库Service接口
 *
 * @author ruoyi
 * @date 2022-07-24
 */
public interface IBaseWarehouseService {
    /**
     * 查询仓库
     *
     * @param warehouseId 仓库主键
     * @return 仓库
     */
    public BaseWarehouse selectBaseWarehouseByWarehouseId(Long warehouseId);

    /**
     * 查询仓库
     *
     * @param warehouseCode
     * @return 仓库
     */
    public String selectBaseWarehouseNameByWarehouseCode(String warehouseCode);

    /**
     * 查询仓库列表
     *
     * @param baseWarehouse 仓库
     * @return 仓库集合
     */
    public List<BaseWarehouse> selectBaseWarehouseList(BaseWarehouse baseWarehouse);

    /**
     * 新增仓库
     *
     * @param baseWarehouse 仓库
     * @return 结果
     */
    public int insertBaseWarehouse(BaseWarehouse baseWarehouse);

    /**
     * 修改仓库
     *
     * @param baseWarehouse 仓库
     * @return 结果
     */
    public int updateBaseWarehouse(BaseWarehouse baseWarehouse);

    /**
     * 批量删除仓库
     *
     * @param warehouseIds 需要删除的仓库主键集合
     * @return 结果
     */
    public int deleteBaseWarehouseByWarehouseIds(Long[] warehouseIds);

    /**
     * 删除仓库信息
     *
     * @param warehouseId 仓库主键
     * @return 结果
     */
    public int deleteBaseWarehouseByWarehouseId(Long warehouseId);
}
