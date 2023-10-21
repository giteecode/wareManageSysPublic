package com.education.model.dto;

import com.education.common.model.TreeData;

import java.util.List;

/**
 * 菜单tree
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/14 14:57
 */
public class MenuTree extends TreeData<MenuTree> {

    private String parentName;
    private String name;
    private String url;
    private String permission;
    private String icon;
    private Integer type;
    private List<Integer> parentIds; // 父级菜单集合

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return parentName;
    }

    public List<Integer> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Integer> parentIds) {
        this.parentIds = parentIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
