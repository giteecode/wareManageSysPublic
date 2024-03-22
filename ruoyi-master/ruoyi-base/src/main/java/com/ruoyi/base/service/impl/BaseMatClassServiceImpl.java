package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BaseMatClassMapper;
import com.ruoyi.base.domain.BaseMatClass;
import com.ruoyi.base.service.IBaseMatClassService;

/**
 * 物料分类Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Service
public class BaseMatClassServiceImpl implements IBaseMatClassService {
    @Autowired
    private BaseMatClassMapper baseMatClassMapper;

    /**
     * 查询物料分类
     *
     * @param classId 物料分类主键
     * @return 物料分类
     */
    @Override
    public BaseMatClass selectBaseMatClassByClassId(Long classId) {
        return baseMatClassMapper.selectBaseMatClassByClassId(classId);
    }

    /**
     * 查询物料分类
     *
     * @param classCode
     * @return 物料分类
     */
    @Override
    public String selectBaseMatClassNameByClassCode(String classCode){
        return baseMatClassMapper.selectBaseMatClassNameByClassCode(classCode);
    }

    /**
     * 查询物料分类列表
     *
     * @param baseMatClass 物料分类
     * @return 物料分类
     */
    @Override
    public List<BaseMatClass> selectBaseMatClassList(BaseMatClass baseMatClass) {
        return baseMatClassMapper.selectBaseMatClassList(baseMatClass);
    }

    /**
     * 新增物料分类
     *
     * @param baseMatClass 物料分类
     * @return 结果
     */
    @Override
    public int insertBaseMatClass(BaseMatClass baseMatClass) {
        baseMatClass.setCreateTime(DateUtils.getNowDate());
        return baseMatClassMapper.insertBaseMatClass(baseMatClass);
    }

    /**
     * 修改物料分类
     *
     * @param baseMatClass 物料分类
     * @return 结果
     */
    @Override
    public int updateBaseMatClass(BaseMatClass baseMatClass) {
        baseMatClass.setUpdateTime(DateUtils.getNowDate());
        return baseMatClassMapper.updateBaseMatClass(baseMatClass);
    }

    /**
     * 批量删除物料分类
     *
     * @param classIds 需要删除的物料分类主键
     * @return 结果
     */
    @Override
    public int deleteBaseMatClassByClassIds(Long[] classIds) {
        return baseMatClassMapper.deleteBaseMatClassByClassIds(classIds);
    }

    /**
     * 删除物料分类信息
     *
     * @param classId 物料分类主键
     * @return 结果
     */
    @Override
    public int deleteBaseMatClassByClassId(Long classId) {
        return baseMatClassMapper.deleteBaseMatClassByClassId(classId);
    }
}
