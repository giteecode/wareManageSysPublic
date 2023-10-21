package com.education.business.service.system;


import com.education.business.mapper.system.SystemLogMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.SystemLog;
import org.springframework.stereotype.Service;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 14:04
 */
@Service
public class SystemLogService extends BaseService<SystemLogMapper, SystemLog> {

    public void removeAll() {
        baseMapper.removeAll();
    }
}
