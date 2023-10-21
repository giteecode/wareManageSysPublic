package com.education.business.service.education;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.CourseInfoMapper;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.common.constants.CacheKey;
import com.education.common.enums.BooleanEnum;
import com.education.common.enums.CourseStatusEnum;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.model.dto.CourseInfoDto;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 18:44
 */
@Service
public class CourseInfoService extends BaseService<CourseInfoMapper, CourseInfo> {

    @Resource
    private CourseSectionService courseSectionService;
    @Resource
    private CourseSectionNodeService courseSectionNodeService;

    public PageInfo<CourseInfoDto> selectPageList(PageParam pageParam, CourseInfo courseInfo) {
        Page<CourseInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        Page<CourseInfoDto> resultPage = baseMapper.selectPageList(page, courseInfo);
        List<CourseInfoDto> list = resultPage.getRecords();
        list.forEach(item -> {
            Integer studentId = item.getStudentId();
            if (studentId == null) {
                item.setCollectFlag(BooleanEnum.NO.getCode());
            } else {
                item.setCollectFlag(BooleanEnum.YES.getCode());
            }
        });
        return selectPage(resultPage);
    }

    /**
     * 判断学员是否收藏课程
     * @param list
     */
    private void setIsCollect(List<CourseInfoDto> list) {
        list.forEach(item -> {
            Integer studentId = item.getStudentId();
            if (studentId == null) {
                item.setCollectFlag(BooleanEnum.NO.getCode());
            } else {
                item.setCollectFlag(BooleanEnum.YES.getCode());
            }
        });
    }


    /**
     * 添加或修改课程
     * @param courseInfo
     */
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheKey.COURSE_INFO, key = "#courseInfo.id", condition = "#courseInfo.id != null"),
            @CacheEvict(cacheNames = CacheKey.RECOMMEND_COURSE, key = "'index'", condition = "#courseInfo.status > 0")
        }
    )
    public void saveOrUpdateCourse(CourseInfo courseInfo) {
        Integer courseId = courseInfo.getId();
        if (courseId != null) {
            CourseInfo course = super.getById(courseId);
            if (!CourseStatusEnum.DRAUGHT.getValue().equals(course.getStatus())) {
                if (!courseInfo.getSchoolType().equals(course.getSchoolType())) {
                    throw new BusinessException("已上架课程禁止修改所属阶段");
                }

                if (!courseInfo.getGradeInfoId().equals(course.getGradeInfoId())) {
                    throw new BusinessException("已上架课程禁止修改所属年级");
                }

                if (!courseInfo.getSubjectId().equals(course.getSubjectId())) {
                    throw new BusinessException("已上架课程禁止修改所属科目");
                }
            }
        }
        if (CourseStatusEnum.GROUNDING.getValue().equals(courseInfo.getStatus())) {
            courseInfo.setPushTime(new Date());
        }
        super.saveOrUpdate(courseInfo);
    }

    /**
     * 根据id 删除课程
     * @param courseId
     */
    @Transactional
    public void deleteById(Integer courseId) {
        CourseInfo course = super.getById(courseId);
        if (!CourseStatusEnum.DRAUGHT.getValue().equals(course.getStatus())) {
            throw new BusinessException("非草稿状态课程无法删除");
        }
        super.removeById(courseId);
        courseSectionService.deleteByCourseId(courseId);
        courseSectionNodeService.deleteByCourseId(courseId);
    }

    /**
     * 课程学习人数加1
     * @param id
     */
    public void increaseStudyNumber(Integer id) {
        baseMapper.increaseStudyNumber(id);
    }

    public void increaseSectionNumber(Integer id) {
        baseMapper.increaseSectionNumber(id);
    }

    public void decreaseSectionNumber(Integer id) {
        baseMapper.decreaseSectionNumber(id);
    }

    public void increaseSectionNodeNumber(Integer id) {
        baseMapper.increaseSectionNodeNumber(id);
    }

    public void decreaseSectionNodeNumber(Integer id) {
        baseMapper.decreaseSectionNodeNumber(id);
    }

    /**
     * 评论数量加1
     * @param id
     */
    public void increaseCommentNumber(Integer id) {
        baseMapper.increaseCommentNumber(id);
    }

    @CacheEvict(cacheNames = CacheKey.COURSE_INFO, key = "#courseId")
    public void updateCommentNumberAndValuateMark(Integer courseId, BigDecimal valuateMarkSum) {
        // 评论数量加1
        this.increaseCommentNumber(courseId);
        // 重新计算课程分数
        CourseInfo courseInfo = super.getById(courseId);
        Integer commentNumber = courseInfo.getCommentNumber();
        BigDecimal valuateMark = valuateMarkSum.divide(new BigDecimal(commentNumber)).setScale(0, RoundingMode.HALF_UP);
        courseInfo.setValuateMark(valuateMark);
        super.update(Wrappers.lambdaUpdate(CourseInfo.class)
                .set(CourseInfo::getValuateMark, valuateMark)
                .eq(CourseInfo::getId, courseId));
    }

    public void checkCourse(Integer courseId) {
        CourseInfo courseInfo = super.selectFirst(Wrappers.<CourseInfo>lambdaQuery().eq(CourseInfo::getId, courseId));
        if (courseInfo == null) {
            throw new BusinessException("课程不存在!");
        }
        if (CourseStatusEnum.GROUNDING.getValue().equals(courseInfo.getStatus())) {
            throw new BusinessException("课程已上架，无法删除");
        }
    }


    @Override
    @Cacheable(cacheNames = CacheKey.COURSE_INFO, key = "#id", unless = "#result == null")
    public CourseInfo getById(Serializable id) {
        return super.getById(id);
    }

    @Cacheable(cacheNames = CacheKey.RECOMMEND_COURSE, key = "'index'", unless = "#result == null")
    public List<CourseInfoDto> getRecommendList() {
        List<CourseInfoDto> courseInfoList = baseMapper.getRecommendList(UserSessionContext.getStudentUserSession().getGradeInfoId());
        setIsCollect(courseInfoList);
        return courseInfoList;
    }
}
