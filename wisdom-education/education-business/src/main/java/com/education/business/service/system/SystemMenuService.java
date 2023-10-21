package com.education.business.service.system;

import com.education.business.mapper.system.SystemMenuMapper;
import com.education.business.service.BaseService;
import com.education.common.exception.BusinessException;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.common.utils.TreeUtils;
import com.education.model.dto.MenuTree;
import com.education.model.entity.SystemMenu;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单管理service
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 15:38
 */
@Service
public class SystemMenuService extends BaseService<SystemMenuMapper, SystemMenu> {

    /**
     * 获取角色菜单列表
     * @param roleIds
     * @return
     */
    public List<SystemMenu> getMenuListByRoles(List<Integer> roleIds) {
        if (ObjectUtils.isNotEmpty(roleIds)) {
            return baseMapper.getMenuListByRoles(roleIds);
        }
        return null;
    }

    /**
     * 菜单转换为tree 菜单
     * @param menuList
     * @return
     */
    public List<MenuTree> getTreeMenuList(List<SystemMenu> menuList) {
        List<MenuTree> menuTreeList = new ArrayList<>();
        menuList.forEach(menu -> {
            MenuTree menuTree = new MenuTree();
            menuTree.setId(menu.getId());
            menuTree.setParentId(menu.getParentId());
            menuTree.setLabel(menu.getName());
            menuTree.setIcon(menu.getIcon());
            menuTree.setUrl(menu.getUrl());
            menuTreeList.add(menuTree);
        });
        return TreeUtils.buildTreeData(menuTreeList);
    }

    /**
     * 获取角色tree 菜单
     * @param roleId
     * @return
     */
    public List<MenuTree> getTreeMenuByRoleId(Integer roleId) {
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        List<SystemMenu> systemMenuList = this.getMenuListByRoles(roleIds);
        return getTreeMenuList(systemMenuList);
    }

    /**
     * 树菜单列表
     * @return
     */
    public List<MenuTree> selectMenuTreeList() {
        return TreeUtils.buildTreeData(baseMapper.getTreeMenuList());
    }

    /**
     * 获取tree 中的最后子节点集合
     * @param menuTree
     * @param menuIds
     * @return
     */
    public List<Integer> findLastTreeIds(MenuTree menuTree, List<Integer> menuIds) {
        List<MenuTree> children = menuTree.getChildren();
        if (ObjectUtils.isNotEmpty(children)) {
            children.forEach(childrenMenu -> {
                findLastTreeIds(childrenMenu, menuIds);
            });
        } else {
            menuIds.add(menuTree.getId());
        }
        return menuIds;
    }


    /**
     * 获取角色选中的终极子节点集合
     * @param roleId
     * @return
     */
    public List<Integer> selectTreeCheckedMenuId(Integer roleId) {
        List<MenuTree> menuTreeList = getTreeMenuByRoleId(roleId);
        List<Integer> menuIds = new ArrayList<>();
        menuTreeList.forEach(menu -> {
            findLastTreeIds(menu, menuIds);
        });
        return menuIds;
    }

    /**
     * 树菜单详情
     * @param id
     * @return
     */
    public MenuTree selectById(Integer id) {
        MenuTree menuTree = baseMapper.selectMenuTreeById(id);
        List<MenuTree> menuTreeList = baseMapper.getTreeMenuList();
        Integer parentId = menuTree.getParentId();
        List<MenuTree> parentMenuList = TreeUtils.getParentList(menuTreeList, parentId);

        List<Integer> parentIds = parentMenuList.stream()
                .sorted(Comparator.comparing(MenuTree::getParentId))
                .map(MenuTree::getId)
                .collect(Collectors.toList());
        menuTree.setParentIds(parentIds);
        return menuTree;
    }

    public void deleteById(Integer id) {
        SystemMenu systemMenu = super.getById(id);
        if (ResultCode.SUCCESS.equals(systemMenu.getCreateType())) {
            throw new BusinessException("您不能删除系统内置菜单");
        }
        super.removeById(id);
    }
}
