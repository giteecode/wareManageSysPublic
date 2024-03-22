package com.ruoyi.base.mapper;

import java.util.List;

import com.ruoyi.base.domain.BaseMat;
import com.ruoyi.base.domain.BaseMatBom;

/**
 * 物料BOMMapper接口
 *
 * @author ruoyi
 * @date 2022-07-23
 */
public interface BaseMatBomMapper {
    /**
     * 查询物料BOM
     *
     * @param bomId 物料BOM主键
     * @return 物料BOM
     */
    public BaseMatBom selectBaseMatBomByBomId(Long bomId);

    /**
     * 查询物料BOM列表
     */
    public List<BaseMatBom> selectBaseMatBomByFatherMatCode(String fatherMatCode);

    /**
     * 查询物料BOM列表
     *
     * @param baseMatBom 物料BOM
     * @return 物料BOM集合
     */
    public List<BaseMatBom> selectBaseMatBomList(BaseMatBom baseMatBom);

    /**
     * 新增物料BOM
     *
     * @param baseMatBom 物料BOM
     * @return 结果
     */
    public int insertBaseMatBom(BaseMatBom baseMatBom);

    /**
     * 修改物料BOM
     *
     * @param baseMatBom 物料BOM
     * @return 结果
     */
    public int updateBaseMatBom(BaseMatBom baseMatBom);

    /**
     * 删除物料BOM
     *
     * @param bomId 物料BOM主键
     * @return 结果
     */
    public int deleteBaseMatBomByBomId(Long bomId);

    /**
     * 批量删除物料BOM
     *
     * @param bomIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseMatBomByBomIds(Long[] bomIds);

    /**
     * 批量删除物料BOM
     *
     * @param matCodes
     * @return 结果
     */
    public int deleteBaseMatBomByMatCodes(String[] matCodes);

    /**
     * 按父物料删除物料BOM信息
     *
     * @param fatherMatCode
     * @return 结果
     */
    public int deleteBaseMatBomByFMatCode(String fatherMatCode);

}
