package com.ruoyi.base.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.BaseMatMapper;
import com.ruoyi.base.domain.BaseMat;
import com.ruoyi.base.service.IBaseMatService;

import javax.validation.Validator;

/**
 * 物料主数据Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-23
 */
@Service
public class BaseMatServiceImpl implements IBaseMatService {
    private static final Logger log = LoggerFactory.getLogger(BaseMatServiceImpl.class);

    @Autowired
    private BaseMatMapper baseMatMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询物料数
     */
    @Override
    public int selectBaseMatTotal(){
        return baseMatMapper.selectBaseMatTotal();
    }

    /**
     * 查询物料主数据
     *
     * @param matId 物料主数据主键
     * @return 物料主数据
     */
    @Override
    public BaseMat selectBaseMatByMatId(Long matId) {
        return baseMatMapper.selectBaseMatByMatId(matId);
    }

    /**
     * 查询bom
     */
    public List<BaseMat> selectBomList(String matCode, String matName){
        return baseMatMapper.selectBomList(matCode, matName);
    }

    /**
     * 查询物料主数据
     *
     * @param matCode
     * @return 物料主数据
     */
    @Override
    public BaseMat selectBaseMatByMatCode(String matCode){
        return baseMatMapper.selectBaseMatByMatCode(matCode);
    }

    /**
     * 查询物料主数据列表
     *
     * @param baseMat 物料主数据
     * @return 物料主数据
     */
    @Override
    public List<BaseMat> selectBaseMatList(BaseMat baseMat) {
        return baseMatMapper.selectBaseMatList(baseMat);
    }

    /**
     * 新增物料主数据
     *
     * @param baseMat 物料主数据
     * @return 结果
     */
    @Override
    public int insertBaseMat(BaseMat baseMat) {
        baseMat.setCreateTime(DateUtils.getNowDate());
        return baseMatMapper.insertBaseMat(baseMat);
    }

    /**
     * 修改物料主数据
     *
     * @param baseMat 物料主数据
     * @return 结果
     */
    @Override
    public int updateBaseMat(BaseMat baseMat) {
        baseMat.setUpdateTime(DateUtils.getNowDate());
        return baseMatMapper.updateBaseMat(baseMat);
    }

    /**
     * 批量删除物料主数据
     *
     * @param matIds 需要删除的物料主数据主键
     * @return 结果
     */
    @Override
    public int deleteBaseMatByMatIds(Long[] matIds) {
        return baseMatMapper.deleteBaseMatByMatIds(matIds);
    }

    /**
     * 删除物料主数据信息
     *
     * @param matId 物料主数据主键
     * @return 结果
     */
    @Override
    public int deleteBaseMatByMatId(Long matId) {
        return baseMatMapper.deleteBaseMatByMatId(matId);
    }

    /**
     * 导入物料数据
     *
     * @param matList         物料数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importMat(List<BaseMat> matList, Boolean isUpdateSupport, String operName){
        if (StringUtils.isNull(matList) || matList.size() == 0) {
            throw new ServiceException("导入物料数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (BaseMat mat : matList) {
            try {
                // 验证物料数据是否存在
                BaseMat m = baseMatMapper.selectBaseMatByMatCode(mat.getMatCode());
                if (StringUtils.isNull(m)) {
                    BeanValidators.validateWithException(validator, mat);
                    mat.setCreateBy(operName);
                    this.insertBaseMat(mat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、物料 " + mat.getMatCode() + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, mat);
                    mat.setMatId(m.getMatId());
                    mat.setUpdateBy(operName);
                    this.updateBaseMat(mat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、物料  " + mat.getMatCode() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、物料 " + mat.getMatCode() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、物料 " + mat.getMatCode() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
