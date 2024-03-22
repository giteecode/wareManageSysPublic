package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BaseLocationMapper;
import com.ruoyi.base.domain.BaseLocation;
import com.ruoyi.base.service.IBaseLocationService;

/**
 * 货位Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-24
 */
@Service
public class BaseLocationServiceImpl implements IBaseLocationService 
{
    @Autowired
    private BaseLocationMapper baseLocationMapper;

    /**
     * 查询货位
     * 
     * @param locationId 货位主键
     * @return 货位
     */
    @Override
    public BaseLocation selectBaseLocationByLocationId(Long locationId)
    {
        return baseLocationMapper.selectBaseLocationByLocationId(locationId);
    }

    /**
     * 查询货位列表
     * 
     * @param baseLocation 货位
     * @return 货位
     */
    @Override
    public List<BaseLocation> selectBaseLocationList(BaseLocation baseLocation)
    {
        return baseLocationMapper.selectBaseLocationList(baseLocation);
    }

    /**
     * 新增货位
     * 
     * @param baseLocation 货位
     * @return 结果
     */
    @Override
    public int insertBaseLocation(BaseLocation baseLocation)
    {
        baseLocation.setCreateTime(DateUtils.getNowDate());
        return baseLocationMapper.insertBaseLocation(baseLocation);
    }

    /**
     * 修改货位
     * 
     * @param baseLocation 货位
     * @return 结果
     */
    @Override
    public int updateBaseLocation(BaseLocation baseLocation)
    {
        baseLocation.setUpdateTime(DateUtils.getNowDate());
        return baseLocationMapper.updateBaseLocation(baseLocation);
    }

    /**
     * 批量删除货位
     * 
     * @param locationIds 需要删除的货位主键
     * @return 结果
     */
    @Override
    public int deleteBaseLocationByLocationIds(Long[] locationIds)
    {
        return baseLocationMapper.deleteBaseLocationByLocationIds(locationIds);
    }

    /**
     * 删除货位信息
     * 
     * @param locationId 货位主键
     * @return 结果
     */
    @Override
    public int deleteBaseLocationByLocationId(Long locationId)
    {
        return baseLocationMapper.deleteBaseLocationByLocationId(locationId);
    }
}
