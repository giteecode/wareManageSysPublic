package com.education.business.service.system;


import com.education.business.mapper.system.SystemDictMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.SystemDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 21:16
 */
@Service
public class SystemDictService extends BaseService<SystemDictMapper, SystemDict> {

    @Autowired
    private SystemDictValueService systemDictValueService;

    @Transactional
    public void deleteById(Integer id) {
        super.removeById(id);
        // 删除字典类型值
        systemDictValueService.deleteByDictId(id);
    }

    /*public static final String GRADE_TYPE = "grade_type";
    public static final String QUESTION_TYPE = "question_type";
    @Autowired
    private SystemDictValueService systemDictValueService;

    public Result saveOrUpdate(ModelBeanMap params) {
        boolean updateFlag = false;
        if (ObjectUtils.isNotEmpty(params.get("id"))) {
            updateFlag = true;
        }
        return super.saveOrUpdate(updateFlag, params);
    }


    @Transactional
    public Result deleteDictById(ModelBeanMap dictBeanMap) {
        try {
            super.deleteById(dictBeanMap.getInt("id"));
            systemDictValueService.deleteByDictId(dictBeanMap.getInt("id"));
            return Result.success(ResultCode.SUCCESS, "删除成功");
        } catch (Exception e) {
            logger.error("删除字典数据失败", e);
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "删除字典数据失败"));
        }
    }*/
}
