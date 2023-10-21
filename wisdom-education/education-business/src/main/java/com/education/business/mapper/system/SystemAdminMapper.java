package com.education.business.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.dto.AdminRoleDto;
import com.education.model.entity.SystemAdmin;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 12:09
 */
public interface SystemAdminMapper extends BaseMapper<SystemAdmin> {

    AdminRoleDto selectById(Integer adminId);
}
