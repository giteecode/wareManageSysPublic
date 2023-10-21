package com.education.business.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.entity.SystemDictValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemDictValueMapper extends BaseMapper<SystemDictValue> {

    /**
     * 根据类型获取字典值
     * @param dictType
     * @return
     */
    List<SystemDictValue> selectByDictType(@Param("dictType") String dictType);
}
