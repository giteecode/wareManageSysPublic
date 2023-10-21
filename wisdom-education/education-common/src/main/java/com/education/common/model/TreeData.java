package com.education.common.model;


import java.io.Serializable;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/14 14:42
 */
public abstract class TreeData<T extends TreeData<T>> implements Serializable {

    private Integer id;
    private Integer parentId;
    private String label;
    private List<T> children;
    //private boolean hasChildren = false;



    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
