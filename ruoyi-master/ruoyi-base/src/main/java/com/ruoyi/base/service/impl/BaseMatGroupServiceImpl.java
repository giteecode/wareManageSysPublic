package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BaseMatGroupMapper;
import com.ruoyi.base.domain.BaseMatGroup;
import com.ruoyi.base.service.IBaseMatGroupService;

/**
 * 物料组Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Service
public class BaseMatGroupServiceImpl implements IBaseMatGroupService {
    @Autowired
    private BaseMatGroupMapper baseMatGroupMapper;

    /**
     * 查询物料组
     *
     * @param groupId 物料组主键
     * @return 物料组
     */
    @Override
    public BaseMatGroup selectBaseMatGroupByGroupId(Long groupId) {
        return baseMatGroupMapper.selectBaseMatGroupByGroupId(groupId);
    }

    /**
     * 查询物料组
     *
     * @param groupCode
     * @return 物料组
     */
    @Override
    public String selectBaseMatGroupNameByGroupCode(String groupCode){
        return baseMatGroupMapper.selectBaseMatGroupNameByGroupCode(groupCode);
    }

    /**
     * 查询物料组列表
     *
     * @param baseMatGroup 物料组
     * @return 物料组
     */
    @Override
    public List<BaseMatGroup> selectBaseMatGroupList(BaseMatGroup baseMatGroup) {
        return baseMatGroupMapper.selectBaseMatGroupList(baseMatGroup);
    }

    /**
     * 新增物料组
     *
     * @param baseMatGroup 物料组
     * @return 结果
     */
    @Override
    public int insertBaseMatGroup(BaseMatGroup baseMatGroup) {
        baseMatGroup.setCreateTime(DateUtils.getNowDate());
        return baseMatGroupMapper.insertBaseMatGroup(baseMatGroup);
    }

    /**
     * 修改物料组
     *
     * @param baseMatGroup 物料组
     * @return 结果
     */
    @Override
    public int updateBaseMatGroup(BaseMatGroup baseMatGroup) {
        baseMatGroup.setUpdateTime(DateUtils.getNowDate());
        return baseMatGroupMapper.updateBaseMatGroup(baseMatGroup);
    }

    /**
     * 批量删除物料组
     *
     * @param groupIds 需要删除的物料组主键
     * @return 结果
     */
    @Override
    public int deleteBaseMatGroupByGroupIds(Long[] groupIds) {
        return baseMatGroupMapper.deleteBaseMatGroupByGroupIds(groupIds);
    }

    /**
     * 删除物料组信息
     *
     * @param groupId 物料组主键
     * @return 结果
     */
    @Override
    public int deleteBaseMatGroupByGroupId(Long groupId) {
        return baseMatGroupMapper.deleteBaseMatGroupByGroupId(groupId);
    }
}
