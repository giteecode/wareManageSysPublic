package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.entity.CourseStudyProgress;

/**
 * @author zjt
 * @create_at 2022年1月13日 0013 10:28
 * @since 1.0.5
 */
public interface CourseStudyProgressMapper extends BaseMapper<CourseStudyProgress> {

    /**
     * 更新学习进度
     * @param courseStudyProgress
     */
    void updateStudyProgress(CourseStudyProgress courseStudyProgress);

    void listPage();
}
