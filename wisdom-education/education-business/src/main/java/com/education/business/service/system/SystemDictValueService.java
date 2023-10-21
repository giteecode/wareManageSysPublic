package com.education.business.service.system;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.system.SystemDictValueMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.SystemDictValue;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class SystemDictValueService extends BaseService<SystemDictValueMapper, SystemDictValue> {

    public void deleteByDictId(Integer dictId) {
        // 获取删除字典值id
        LambdaQueryWrapper queryWrapper = Wrappers.<SystemDictValue>lambdaQuery()
                .eq(SystemDictValue::getSystemDictId, dictId);
        baseMapper.delete(queryWrapper);
    }

    public List<SystemDictValue> selectByDictType(String dictType) {
        return baseMapper.selectByDictType(dictType);
    }
}
