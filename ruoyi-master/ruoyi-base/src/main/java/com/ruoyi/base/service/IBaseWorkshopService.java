package com.ruoyi.base.service;

import java.util.List;

import com.ruoyi.base.domain.BaseWorkshop;

/**
 * 车间Service接口
 *
 * @author ruoyi
 * @date 2022-07-28
 */
public interface IBaseWorkshopService {
    /**
     * 查询车间
     *
     * @param workshopId 车间主键
     * @return 车间
     */
    public BaseWorkshop selectBaseWorkshopByWorkshopId(Long workshopId);

    /**
     * 查询车间
     *
     * @param workshopCode
     * @return 车间
     */
    public String selectBaseWorkshopByWorkshopCode(String workshopCode);

    /**
     * 查询车间列表
     *
     * @param baseWorkshop 车间
     * @return 车间集合
     */
    public List<BaseWorkshop> selectBaseWorkshopList(BaseWorkshop baseWorkshop);

    /**
     * 新增车间
     *
     * @param baseWorkshop 车间
     * @return 结果
     */
    public int insertBaseWorkshop(BaseWorkshop baseWorkshop);

    /**
     * 修改车间
     *
     * @param baseWorkshop 车间
     * @return 结果
     */
    public int updateBaseWorkshop(BaseWorkshop baseWorkshop);

    /**
     * 批量删除车间
     *
     * @param workshopIds 需要删除的车间主键集合
     * @return 结果
     */
    public int deleteBaseWorkshopByWorkshopIds(Long[] workshopIds);

    /**
     * 删除车间信息
     *
     * @param workshopId 车间主键
     * @return 结果
     */
    public int deleteBaseWorkshopByWorkshopId(Long workshopId);
}
