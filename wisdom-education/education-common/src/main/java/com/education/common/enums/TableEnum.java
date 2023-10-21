package com.education.common.enums;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/2 13:42
 */
public enum TableEnum {

    QUESTION("question_info", "试题表"),
    COURSE_SECTION_NODE("course_section_node", "课程章节点表")
    ;

    private String name;
    private String desc;

    TableEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
