package com.education.business.service.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.auth.AuthUtil;
import com.education.auth.session.UserSession;
import com.education.business.mapper.system.SystemAdminMapper;
import com.education.business.service.BaseService;
import com.education.business.session.AdminUserSession;
import com.education.business.session.UserSessionContext;
import com.education.common.enums.LoginEnum;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.PasswordUtil;
import com.education.common.utils.ResultCode;
import com.education.model.dto.AdminRoleDto;
import com.education.model.dto.MenuTree;
import com.education.model.entity.*;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 11:25
 */
@Service
public class SystemAdminService extends BaseService<SystemAdminMapper, SystemAdmin> {

    @Resource
    private SystemMenuService systemMenuService;
    @Resource
    private SystemRoleService systemRoleService;
    @Resource
    private SystemAdminRoleService systemAdminRoleService;

    public PageInfo<SystemAdmin> listPage(PageParam pageParam, SystemAdmin systemAdmin) {
        Page<SystemAdmin> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        LambdaQueryWrapper queryWrapper = Wrappers.<SystemAdmin>lambdaQuery()
                .like(ObjectUtils.isNotEmpty(systemAdmin.getLoginName()),
                        SystemAdmin::getLoginName, systemAdmin.getLoginName());
        return selectPage(super.page(page, queryWrapper));
    }

    public AdminRoleDto selectById(Integer id) {
        return baseMapper.selectById(id);
    }

    /**
     * 加载用户菜单及权限标识
     * @param userSession
     */
    public void loadUserMenuAndPermission(AdminUserSession userSession) {
        List<SystemMenu> menuList;
        if (userSession.isSuperAdmin()) {
            menuList = systemMenuService.list(Wrappers.lambdaQuery(SystemMenu.class)
                    .orderByAsc(SystemMenu::getSort));
        } else {
            Integer adminId = userSession.getId();
            List<SystemRole> systemRoleList = systemRoleService.findRoleListByAdminId(adminId);
            userSession.setRoleList(systemRoleList);
            List<Integer> roleIds = systemRoleList.stream()
                    .map(SystemRole::getId)
                    .collect(Collectors.toList());
            menuList = systemMenuService.getMenuListByRoles(roleIds);
        }
        if (ObjectUtils.isNotEmpty(menuList)) {
            Set<String> permissionList = menuList.stream()
                    .filter(systemMenu -> ObjectUtils.isNotEmpty(systemMenu.getPermission()))
                    .map(SystemMenu::getPermission)
                    .collect(Collectors.toSet());
            List<MenuTree> menuTreeList = systemMenuService.getTreeMenuList(menuList);
            userSession.setMenuTreeList(menuTreeList);
            userSession.addPermission(permissionList);
        }
    }

    @Transactional
    public void saveOrUpdate(AdminRoleDto adminRoleDto) {

        if (ObjectUtils.isEmpty(adminRoleDto.getId())) {
            String password = adminRoleDto.getPassword();
            if (ObjectUtils.isEmpty(password)) {
                throw new BusinessException(new ResultCode(ResultCode.FAIL, "请输入密码"));
            }
            String confirmPassword = adminRoleDto.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                throw new BusinessException(new ResultCode(ResultCode.FAIL, "密码与确认密码不一致"));
            }
            String encrypt = PasswordUtil.createEncrypt();
            adminRoleDto.setEncrypt(encrypt);
            password = PasswordUtil.createPassword(encrypt, password);
            adminRoleDto.setPassword(password);
        }

        super.saveOrUpdate(adminRoleDto);
        Set<Integer> roleIds = adminRoleDto.getRoleIds();
        // 保存管理员角色信息
        if (ObjectUtils.isNotEmpty(roleIds)) {
            List<SystemAdminRole> adminRoleList = new ArrayList<>();
            roleIds.forEach(roleId -> {
                SystemAdminRole systemAdminRole = new SystemAdminRole();
                systemAdminRole.setAdminId(adminRoleDto.getId());
                systemAdminRole.setRoleId(roleId);
                adminRoleList.add(systemAdminRole);
            });

            systemAdminRoleService.deleteByAdminId(adminRoleDto.getId());
            systemAdminRoleService.saveBatch(adminRoleList);
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        SystemAdmin systemAdmin = super.getById(id);
        if (systemAdmin.isSuper()) {
            throw new BusinessException("不允许删除超级管理员");
        }
        super.removeById(id);
        systemAdminRoleService.deleteByAdminId(id);
    }

    /**
     * 修改密码
     * @param adminRoleDto
     */
    public void updatePassword(AdminRoleDto adminRoleDto) {
        Assert.notBlank(adminRoleDto.getNewPassword(), () -> new BusinessException("新密码不能为空"));
        Assert.notBlank(adminRoleDto.getConfirmPassword(), () -> new BusinessException("确认密码不能为空"));
        String newPassword = adminRoleDto.getNewPassword();
        String confirmPassword = adminRoleDto.getConfirmPassword();
        SystemAdmin systemAdmin = super.getById(adminRoleDto.getId());
        String encrypt = systemAdmin.getEncrypt();
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "密码与确认密码不一致"));
        }
        String md5PassWord = PasswordUtil.createPassword(encrypt, newPassword);// 对新密码进行加密
        super.update(Wrappers.lambdaUpdate(SystemAdmin.class)
                .set(SystemAdmin::getPassword, md5PassWord)
                .eq(SystemAdmin::getId, adminRoleDto.getId()));
    }


    /**
     * 管理员重置密码
     * @param adminRoleDto
     */
    public void resettingPassword(AdminRoleDto adminRoleDto) {
        Assert.notNull(adminRoleDto.getPassword(), () -> new BusinessException("原始密码不能为空"));
        Assert.notNull(adminRoleDto.getNewPassword(), () -> new BusinessException("新密码不能为空"));
        Assert.notNull(adminRoleDto.getConfirmPassword(), () -> new BusinessException("确认密码不能为空"));
        if (!adminRoleDto.getNewPassword().equals(adminRoleDto.getConfirmPassword())) {
            throw new BusinessException("密码与确认密码不一致");
        }
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(SystemAdmin.class)
                .select(SystemAdmin::getPassword, SystemAdmin::getId, SystemAdmin::getEncrypt)
                .eq(SystemAdmin::getId, UserSessionContext.getAdminUserId());
        SystemAdmin dbSystemAdmin = super.getOne(queryWrapper);
        String md5Password = PasswordUtil.createPassword(dbSystemAdmin.getEncrypt(), adminRoleDto.getPassword());
        if (!dbSystemAdmin.getPassword().equals(md5Password)) {
            throw new BusinessException("原始密码错误");
        }
        String newMd5Password = PasswordUtil.createPassword(dbSystemAdmin.getEncrypt(), adminRoleDto.getNewPassword());
        dbSystemAdmin.setPassword(newMd5Password);
        LambdaUpdateWrapper lambdaUpdateWrapper = Wrappers.lambdaUpdate(SystemAdmin.class)
                .set(SystemAdmin::getPassword, newMd5Password)
                .set(SystemAdmin::getUpdateDate, new Date())
                .eq(SystemAdmin::getId, dbSystemAdmin.getId());
        super.update(lambdaUpdateWrapper);
    }
}

