package com.education.api.controller.student;

import com.education.business.service.education.VideoWatchProgressService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.VideoWatchProgress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程章节视频学习进度接口
 * @author zjt
 * @create_at 2022年1月17日 0017 14:47
 * @since 1.0.5
 */
@RestController
@RequestMapping("/section/video")
public class VideoWatchProgressController extends BaseController {

    @Resource
    private VideoWatchProgressService videoWatchProgressService;

    /**
     * 保存章节视频进度
     * @param videoWatchProgress
     * @return
     */
    @PostMapping
    public Result save(@RequestBody VideoWatchProgress videoWatchProgress) {
        videoWatchProgressService.save(videoWatchProgress);
        return Result.success();
    }

    /**
     * 获取课时学习进度
     * @param sectionNodeId
     * @return
     */
    @GetMapping("/sectionNode/{sectionNodeId}")
    public Result queryBySectionNodeId(@PathVariable Integer sectionNodeId) {
        return Result.success(videoWatchProgressService.queryBySectionNodeId(sectionNodeId));
    }
}
