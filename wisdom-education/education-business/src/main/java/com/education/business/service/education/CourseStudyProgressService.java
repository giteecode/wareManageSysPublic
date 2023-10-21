package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.CourseStudyProgressMapper;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.model.entity.CourseStudyProgress;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;

/**
 * 课程学习进度
 * @author zjt
 * @create_at 2022年1月13日 0013 10:27
 * @since 1.0.5
 */
@Service
public class CourseStudyProgressService extends BaseService<CourseStudyProgressMapper, CourseStudyProgress> {

    /**
     * 更新学习进度
     * @param courseStudyProgress
     */
    public void updateStudyProgress(CourseStudyProgress courseStudyProgress) {
        baseMapper.updateStudyProgress(courseStudyProgress);
    }

    /**
     * 查询学员是否学员过该门课程
     * @param courseId
     */
    public boolean queryHasStudyCourse(Integer courseId) {
        LambdaQueryWrapper<CourseStudyProgress> lambdaQueryWrapper = Wrappers.<CourseStudyProgress>lambdaQuery()
                .eq(CourseStudyProgress::getCourseId, courseId)
                .eq(CourseStudyProgress::getStudentId, UserSessionContext.getStudentId());
        return super.selectFirst(lambdaQueryWrapper) != null;
    }

    public Object listPage(PageParam pageParam) {
        return null;
    }
}
