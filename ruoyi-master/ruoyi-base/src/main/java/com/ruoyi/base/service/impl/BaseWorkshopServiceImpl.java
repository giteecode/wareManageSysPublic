package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BaseWorkshopMapper;
import com.ruoyi.base.domain.BaseWorkshop;
import com.ruoyi.base.service.IBaseWorkshopService;

/**
 * 车间Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-28
 */
@Service
public class BaseWorkshopServiceImpl implements IBaseWorkshopService {
    @Autowired
    private BaseWorkshopMapper baseWorkshopMapper;

    /**
     * 查询车间
     *
     * @param workshopId 车间主键
     * @return 车间
     */
    @Override
    public BaseWorkshop selectBaseWorkshopByWorkshopId(Long workshopId) {
        return baseWorkshopMapper.selectBaseWorkshopByWorkshopId(workshopId);
    }

    /**
     * 查询车间
     *
     * @param workshopCode
     * @return 车间
     */
    @Override
    public String selectBaseWorkshopByWorkshopCode(String workshopCode){
        return baseWorkshopMapper.selectBaseWorkshopByWorkshopCode(workshopCode);
    }

    /**
     * 查询车间列表
     *
     * @param baseWorkshop 车间
     * @return 车间
     */
    @Override
    public List<BaseWorkshop> selectBaseWorkshopList(BaseWorkshop baseWorkshop) {
        return baseWorkshopMapper.selectBaseWorkshopList(baseWorkshop);
    }

    /**
     * 新增车间
     *
     * @param baseWorkshop 车间
     * @return 结果
     */
    @Override
    public int insertBaseWorkshop(BaseWorkshop baseWorkshop) {
        baseWorkshop.setCreateTime(DateUtils.getNowDate());
        return baseWorkshopMapper.insertBaseWorkshop(baseWorkshop);
    }

    /**
     * 修改车间
     *
     * @param baseWorkshop 车间
     * @return 结果
     */
    @Override
    public int updateBaseWorkshop(BaseWorkshop baseWorkshop) {
        baseWorkshop.setUpdateTime(DateUtils.getNowDate());
        return baseWorkshopMapper.updateBaseWorkshop(baseWorkshop);
    }

    /**
     * 批量删除车间
     *
     * @param workshopIds 需要删除的车间主键
     * @return 结果
     */
    @Override
    public int deleteBaseWorkshopByWorkshopIds(Long[] workshopIds) {
        return baseWorkshopMapper.deleteBaseWorkshopByWorkshopIds(workshopIds);
    }

    /**
     * 删除车间信息
     *
     * @param workshopId 车间主键
     * @return 结果
     */
    @Override
    public int deleteBaseWorkshopByWorkshopId(Long workshopId) {
        return baseWorkshopMapper.deleteBaseWorkshopByWorkshopId(workshopId);
    }
}
