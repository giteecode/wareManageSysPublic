package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.education.business.mapper.education.CourseSectionMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.CacheKey;
import com.education.common.utils.DateUtils;
import com.education.model.dto.CourseSectionDto;
import com.education.model.entity.CourseSection;
import com.education.model.entity.CourseSectionNode;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 课程章
 * @author zengjintao
 * @create_at 2021/10/6 10:01
 * @since version 1.0.3
 */
@Service
public class CourseSectionService extends BaseService<CourseSectionMapper, CourseSection> {

    @Resource
    private CourseInfoService courseInfoService;

    @Override
    @Transactional
    @CacheEvict(cacheNames = {CacheKey.COURSE_SECTION, CacheKey.COURSE_INFO}, key = "#courseSection.courseId")
    public boolean saveOrUpdate(CourseSection courseSection) {
        if (courseSection.getId() == null) {
            courseInfoService.increaseSectionNumber(courseSection.getCourseId());
        }
        return super.saveOrUpdate(courseSection);
    }

    @Cacheable(cacheNames = CacheKey.COURSE_SECTION + "#3600", key = "#courseId")
    public List<CourseSectionDto> selectListByCourseId(Integer courseId) {
        List<CourseSectionDto> list = baseMapper.selectListByCourseId(courseId);
        if (CollUtil.isNotEmpty(list)) {
            this.setVideoInfo(list);
        }
        return list;
    }

    private void setVideoInfo(List<CourseSectionDto> list) {
        list.forEach(item -> {
            Integer courseId = item.getCourseId();
            Integer id = item.getId();
            List<CourseSectionNode> sectionNodeList = item.getCourseSectionNodeList();
            sectionNodeList.forEach(sectionNode -> {
                sectionNode.setCourseId(courseId);
                sectionNode.setCourseSectionId(id);
                Long duration = sectionNode.getDuration();
                sectionNode.setDuration(duration);
                if (duration != null && duration != 0) {
                    sectionNode.setDurationStr(DateUtils.secondToHourMinute(duration, StrUtil.COLON));
                }
            });

            // 章节按id 从小到大排序
            item.setCourseSectionNodeList(sectionNodeList.stream()
                    .sorted(Comparator.comparing(CourseSectionNode::getId))
                    .collect(Collectors.toList()));
        });
    }

    @CacheEvict(cacheNames = {CacheKey.COURSE_SECTION, CacheKey.COURSE_INFO}, key = "#courseId")
    public void deleteByCourseId(Integer courseId) {
        LambdaUpdateWrapper<CourseSection> lambdaUpdateWrapper = Wrappers.lambdaUpdate(CourseSection.class)
                .eq(CourseSection::getCourseId, courseId);
        baseMapper.delete(lambdaUpdateWrapper);
    }

    @CacheEvict(cacheNames = {CacheKey.COURSE_SECTION, CacheKey.COURSE_INFO}, key = "#courseId")
    public void delete(Integer courseId, Integer sectionId) {
        courseInfoService.checkCourse(courseId);
        super.removeById(sectionId);
        courseInfoService.decreaseSectionNumber(courseId);
    }
}
