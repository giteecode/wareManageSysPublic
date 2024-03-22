package com.ruoyi.base.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.base.domain.BaseMat;
import org.apache.ibatis.annotations.Param;

/**
 * 物料主数据Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-23
 */
public interface BaseMatMapper {

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
    public List<BaseMat> selectBomList(@Param("matCode") String matCode, @Param("matName") String matName);

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
     * 删除物料主数据
     *
     * @param matId 物料主数据主键
     * @return 结果
     */
    public int deleteBaseMatByMatId(Long matId);

    /**
     * 批量删除物料主数据
     *
     * @param matIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseMatByMatIds(Long[] matIds);
}
