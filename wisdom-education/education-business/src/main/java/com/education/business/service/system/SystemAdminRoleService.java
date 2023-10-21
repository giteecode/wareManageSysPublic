package com.education.business.service.system;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.system.SystemAdminRoleMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.SystemAdminRole;
import org.springframework.stereotype.Service;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 14:44
 */
@Service
public class SystemAdminRoleService extends BaseService<SystemAdminRoleMapper, SystemAdminRole> {

    public int deleteByAdminId(Integer adminId) {
        return baseMapper.delete(new QueryWrapper<SystemAdminRole>().eq("admin_id", adminId));
    }

    public boolean checkRoleIsUse(Integer roleId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(SystemAdminRole.class)
                .eq(SystemAdminRole::getRoleId, roleId)
                .select(SystemAdminRole::getId).last(" limit 1");
        return super.getOne(queryWrapper) == null ? false : true;
    }
}
