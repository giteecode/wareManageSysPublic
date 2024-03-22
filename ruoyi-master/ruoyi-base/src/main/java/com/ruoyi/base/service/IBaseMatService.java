package com.ruoyi.base.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.base.domain.BaseMat;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 物料主数据Service接口
 *
 * @author ruoyi
 * @date 2022-07-23
 */
public interface IBaseMatService {

    /**
     * 查询物料数
     */
    public int selectBaseMatTotal();

    /**
     * 查询物料主数据
     *
     * @param matId 物料主数据主键
     * @return 物料主数据
     */
    public BaseMat selectBaseMatByMatId(Long matId);

    /**
     * 查询bom
     */
    public List<BaseMat> selectBomList(String matCode, String matName);

    /**
     * 查询物料主数据
     *
     * @param matCode
     * @return 物料主数据
     */
    public BaseMat selectBaseMatByMatCode(String matCode);

    /**
     * 查询物料主数据列表
     *
     * @param baseMat 物料主数据
     * @return 物料主数据集合
     */
    public List<BaseMat> selectBaseMatList(BaseMat baseMat);

    /**
     * 新增物料主数据
     *
     * @param baseMat 物料主数据
     * @return 结果
     */
    public int insertBaseMat(BaseMat baseMat);

    /**
     * 修改物料主数据
     *
     * @param baseMat 物料主数据
     * @return 结果
     */
    public int updateBaseMat(BaseMat baseMat);

    /**
     * 批量删除物料主数据
     *
     * @param matIds 需要删除的物料主数据主键集合
     * @return 结果
     */
    public int deleteBaseMatByMatIds(Long[] matIds);

    /**
     * 删除物料主数据信息
     *
     * @param matId 物料主数据主键
     * @return 结果
     */
    public int deleteBaseMatByMatId(Long matId);

    /**
     * 导入物料数据
     *
     * @param matList         物料数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importMat(List<BaseMat> matList, Boolean isUpdateSupport, String operName);

}
