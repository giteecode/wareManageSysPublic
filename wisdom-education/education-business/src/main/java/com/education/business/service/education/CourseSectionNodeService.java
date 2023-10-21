package com.education.business.service.education;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.CourseSectionNodeMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.CacheKey;
import com.education.model.entity.CourseSectionNode;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * 课程章节课时
 * @author zengjintao
 * @create_at 2021/10/6 11:00
 * @since version 1.0.3
 */
@Service
public class CourseSectionNodeService extends BaseService<CourseSectionNodeMapper, CourseSectionNode> {

    @Resource
    private CourseInfoService courseInfoService;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames ={CacheKey.COURSE_SECTION, CacheKey.COURSE_INFO}, key = "#courseSectionNode.courseId")
    public CourseSectionNode saveOrUpdateCourseSectionNode(CourseSectionNode courseSectionNode) {
        if (courseSectionNode.getId() == null) {
            courseInfoService.increaseSectionNodeNumber(courseSectionNode.getCourseId());
        }
        super.saveOrUpdate(courseSectionNode);
        return courseSectionNode;
    }

    @CacheEvict(cacheNames = {CacheKey.COURSE_SECTION, CacheKey.COURSE_INFO}, key = "#courseId")
    public void deleteByCourseId(Integer courseId) {
        LambdaUpdateWrapper<CourseSectionNode> lambdaUpdateWrapper = Wrappers.lambdaUpdate(CourseSectionNode.class)
                .eq(CourseSectionNode::getCourseId, courseId);
        baseMapper.delete(lambdaUpdateWrapper);
    }

    @CacheEvict(cacheNames = {CacheKey.COURSE_SECTION, CacheKey.COURSE_INFO}, key = "#courseId")
    public void delete(Integer courseId, Integer sectionNodeId) {
        courseInfoService.checkCourse(courseId);
        super.removeById(sectionNodeId);
        courseInfoService.decreaseSectionNodeNumber(courseId);
    }

    public Long getVideoTimeById(Integer id) {
        CourseSectionNode courseSectionNode = super.getById(id);
        return courseSectionNode.getDuration();
    }
}
