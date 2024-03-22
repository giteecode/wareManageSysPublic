package com.ruoyi.base.mapper;

import java.util.List;

import com.ruoyi.base.domain.BaseLocation;

/**
 * 货位Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-24
 */
public interface BaseLocationMapper {
    /**
     * 查询货位
     *
     * @param locationId 货位主键
     * @return 货位
     */
    public BaseLocation selectBaseLocationByLocationId(Long locationId);

    /**
     * 查询货位列表
     *
     * @param baseLocation 货位
     * @return 货位集合
     */
    public List<BaseLocation> selectBaseLocationList(BaseLocation baseLocation);

    /**
     * 新增货位
     *
     * @param baseLocation 货位
     * @return 结果
     */
    public int insertBaseLocation(BaseLocation baseLocation);

    /**
     * 修改货位
     *
     * @param baseLocation 货位
     * @return 结果
     */
    public int updateBaseLocation(BaseLocation baseLocation);

    /**
     * 删除货位
     *
     * @param locationId 货位主键
     * @return 结果
     */
    public int deleteBaseLocationByLocationId(Long locationId);

    /**
     * 批量删除货位
     *
     * @param locationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseLocationByLocationIds(Long[] locationIds);
}
