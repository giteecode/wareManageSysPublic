package com.education.business.service.system;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.system.SystemRoleMapper;
import com.education.business.service.BaseService;
import com.education.common.model.PageInfo;
import com.education.common.utils.ResultCode;
import com.education.model.dto.RoleMenuDto;
import com.education.model.entity.SystemRole;
import com.education.model.entity.SystemRoleMenu;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 角色service
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 11:25
 */
@Service
public class SystemRoleService extends BaseService<SystemRoleMapper, SystemRole> {

    @Resource
    private SystemRoleMenuService systemRoleMenuService;
    @Resource
    private SystemAdminRoleService systemAdminRoleService;

    public PageInfo<SystemRole> listPage(PageParam pageParam, SystemRole systemRole) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(SystemRole.class)
                .like(ObjectUtils.isNotEmpty(systemRole.getName()), SystemRole::getName, systemRole.getName());
        return selectPage(pageParam, queryWrapper);
    }

    /**
     * 根据管理员id 获取角色列表
     * @param adminId
     * @return
     */
    public List<SystemRole> findRoleListByAdminId(Integer adminId) {
        return baseMapper.findRoleListByAdminId(adminId);
    }

    /**
     * 保存角色权限
     * @param roleMenuDto
     */
    @Transactional
    public void saveRolePermission(RoleMenuDto roleMenuDto) {
        Integer roleId = roleMenuDto.getRoleId();
        systemRoleMenuService.deleteByRoleId(roleId); //先删除角色原有的权限
        List<Integer> menuIds = roleMenuDto.getMenuIds();
        if (ObjectUtils.isNotEmpty(menuIds)) {
            List<SystemRoleMenu> systemRoleMenuList = new ArrayList<>();
            menuIds.forEach(menuId -> {
                SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setMenuId(menuId);
                systemRoleMenu.setRoleId(roleId);
                systemRoleMenuList.add(systemRoleMenu);
            });
            systemRoleMenuService.saveBatch(systemRoleMenuList);
        }
    }

    @Transactional
    public ResultCode deleteById(Integer roleId) {
        if (systemAdminRoleService.checkRoleIsUse(roleId)) {
            return new ResultCode(ResultCode.FAIL, "角色已被使用,无法删除");
        }
        this.removeById(roleId); // 删除角色
        systemRoleMenuService.deleteByRoleId(roleId); // 删除角色权限
        return new ResultCode(ResultCode.SUCCESS, "删除成功");
    }
}
