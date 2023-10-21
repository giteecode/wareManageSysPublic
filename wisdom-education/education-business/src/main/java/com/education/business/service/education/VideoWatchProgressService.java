package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.VideoWatchProgressMapper;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.common.enums.BooleanEnum;
import com.education.model.entity.CourseStudyProgress;
import com.education.model.entity.VideoWatchProgress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 课时视频观看进度
 * @author zjt
 * @create_at 2022年1月13日 0013 10:25
 * @since 1.0.5
 */
@Service
public class VideoWatchProgressService extends BaseService<VideoWatchProgressMapper, VideoWatchProgress> {

    @Resource
    private CourseStudyProgressService courseStudyProgressService;
    @Resource
    private CourseInfoService courseInfoService;
    @Resource
    private CourseSectionNodeService courseSectionNodeService;


    /**
     * 保存课时视频观看时长
     * @param videoWatchProgress
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(VideoWatchProgress videoWatchProgress) {
        Integer studentId = UserSessionContext.getStudentId();
        CourseStudyProgress courseStudyProgress = new CourseStudyProgress();
        courseStudyProgress.setCourseId(videoWatchProgress.getCourseId());
        courseStudyProgress.setStudentId(studentId);
        courseStudyProgress.setWatchTime(videoWatchProgress.getWatchTime());
        Integer sectionNodeId = videoWatchProgress.getSectionNodeId();
        LambdaQueryWrapper<VideoWatchProgress> lambdaQueryWrapper = Wrappers.lambdaQuery(VideoWatchProgress.class)
                .eq(VideoWatchProgress::getStudentId, studentId)
                .eq(VideoWatchProgress::getSectionNodeId, sectionNodeId);
        // 查询是否存在观看历史
        VideoWatchProgress dbVideoWatchProgress = super.selectFirst(lambdaQueryWrapper);
        Date now = new Date();

        long watchTime = videoWatchProgress.getWatchTime();
        Long duration = courseSectionNodeService.getVideoTimeById(sectionNodeId);
        Integer watchFlag = BooleanEnum.NO.getCode();
        // 观看时长大于或等于视频时长，将本节视频设置为已学完状态
        if (watchTime >= duration) {
            watchFlag = BooleanEnum.YES.getCode();
        }
        videoWatchProgress.setWatchEnd(watchFlag);

        if (dbVideoWatchProgress == null) {
            Integer courseId = courseStudyProgress.getCourseId();
            boolean flag = courseStudyProgressService.queryHasStudyCourse(courseId);
            // 课程学习人数加1
            if (!flag) {
                taskManager.pushTask(() -> {
                    courseInfoService.increaseStudyNumber(courseId);
                });
            }
            // 保存课程课时学习进度和课程学习进度
            videoWatchProgress.setCreateDate(now);
            videoWatchProgress.setStudentId(studentId);
            super.save(videoWatchProgress);
            courseStudyProgress.setCreateDate(now);
            return courseStudyProgressService.save(courseStudyProgress);
        }
        // 如果之前已学完课程，在次重新观看不在保存观看记录
        Integer flag = dbVideoWatchProgress.getWatchEnd();
        if (BooleanEnum.YES.getCode().equals(flag)) {
            return false;
        }

        long dbWatchTime = dbVideoWatchProgress.getWatchTime();
        // 已观看时间小于上一次记录的时间表示学员重复观看 直接return
        if (watchTime <= dbWatchTime) {
            return true;
        }

        videoWatchProgress.setUpdateDate(now);
        videoWatchProgress.setStudentId(studentId);
        // 更新课程课时学习进度
        baseMapper.updateWatchTime(videoWatchProgress);
        // 更新课程学习进度
        courseStudyProgress.setUpdateDate(now);
        courseStudyProgressService.updateStudyProgress(courseStudyProgress);
        return true;
    }

    public VideoWatchProgress queryBySectionNodeId(Integer sectionNodeId) {
        LambdaQueryWrapper<VideoWatchProgress> lambdaQueryWrapper = Wrappers.lambdaQuery(VideoWatchProgress.class)
                .eq(VideoWatchProgress::getSectionNodeId, sectionNodeId);
        return selectFirst(lambdaQueryWrapper);
    }
}
