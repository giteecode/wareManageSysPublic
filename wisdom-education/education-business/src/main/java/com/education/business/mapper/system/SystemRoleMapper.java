package com.education.business.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.entity.SystemRole;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 13:52
 */
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    /**
     * 获取管理员角色列表
     * @param adminId
     * @return
     */
    List<SystemRole> findRoleListByAdminId(Integer adminId);
}
