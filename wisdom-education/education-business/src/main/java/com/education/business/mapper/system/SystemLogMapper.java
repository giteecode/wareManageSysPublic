package com.education.business.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.entity.SystemLog;
import org.apache.ibatis.annotations.Delete;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 14:05
 */
public interface SystemLogMapper extends BaseMapper<SystemLog> {

    @Delete("delete from system_log")
    void removeAll();
}
