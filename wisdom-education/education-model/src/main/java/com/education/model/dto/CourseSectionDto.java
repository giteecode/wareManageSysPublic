package com.education.model.dto;

import com.education.model.entity.CourseSection;
import com.education.model.entity.CourseSectionNode;

import java.util.List;

/**
 * @author zengjintao
 * @create_at 2021/10/6 11:02
 * @since version 1.0.3
 */
public class CourseSectionDto extends CourseSection {

    /**
     * 章节课时列表
     */
    private List<CourseSectionNode> courseSectionNodeList;


    public List<CourseSectionNode> getCourseSectionNodeList() {
        return courseSectionNodeList;
    }

    public void setCourseSectionNodeList(List<CourseSectionNode> courseSectionNodeList) {
        this.courseSectionNodeList = courseSectionNodeList;
    }
}
