package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.dto.CourseSectionDto;
import com.education.model.entity.CourseSection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zengjintao
 * @create_at 2021/10/6 10:01
 * @since version 1.0.3
 */
public interface CourseSectionMapper extends BaseMapper<CourseSection> {

    /**
     * 获取课程章节列表
     * @param courseId
     * @return
     */
    List<CourseSectionDto> selectListByCourseId(@Param("courseId") Integer courseId);


}
