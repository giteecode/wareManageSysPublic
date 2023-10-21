package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.entity.VideoWatchProgress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author zjt
 * @create_at 2022年1月13日 0013 10:29
 * @since 1.0.5
 */
public interface VideoWatchProgressMapper extends BaseMapper<VideoWatchProgress> {

    /**
     * 更新视频观看时长
     * @param videoWatchProgress
     */
    boolean updateWatchTime(VideoWatchProgress videoWatchProgress);
}
