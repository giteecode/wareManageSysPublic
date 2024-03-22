package com.ruoyi.base.service;

import java.util.List;

import com.ruoyi.base.domain.BaseMatClass;

/**
 * 物料分类Service接口
 *
 * @author ruoyi
 * @date 2022-07-23
 */
public interface IBaseMatClassService {
    /**
     * 查询物料分类
     *
     * @param classId 物料分类主键
     * @return 物料分类
     */
    public BaseMatClass selectBaseMatClassByClassId(Long classId);

    /**
     * 查询物料分类
     *
     * @param classCode
     * @return 物料分类
     */
    public String selectBaseMatClassNameByClassCode(String classCode);

    /**
     * 查询物料分类列表
     *
     * @param baseMatClass 物料分类
     * @return 物料分类集合
     */
    public List<BaseMatClass> selectBaseMatClassList(BaseMatClass baseMatClass);

    /**
     * 新增物料分类
     *
     * @param baseMatClass 物料分类
     * @return 结果
     */
    public int insertBaseMatClass(BaseMatClass baseMatClass);

    /**
     * 修改物料分类
     *
     * @param baseMatClass 物料分类
     * @return 结果
     */
    public int updateBaseMatClass(BaseMatClass baseMatClass);

    /**
     * 批量删除物料分类
     *
     * @param classIds 需要删除的物料分类主键集合
     * @return 结果
     */
    public int deleteBaseMatClassByClassIds(Long[] classIds);

    /**
     * 删除物料分类信息
     *
     * @param classId 物料分类主键
     * @return 结果
     */
    public int deleteBaseMatClassByClassId(Long classId);
}
