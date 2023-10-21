package com.education.api.controller.admin.system;

import com.education.business.service.system.SystemQuartzJobService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.SystemQuartzJob;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 定时任务管理
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController extends BaseController {

    @Resource
    private SystemQuartzJobService systemQuartzJobService;

    /**
     * 添加定时任务
     * @param systemQuartzJob
     * @return
     */
    @PostMapping("save")
    public Result saveQuartz(@RequestBody SystemQuartzJob systemQuartzJob) {
        systemQuartzJobService.saveQuartzJob(systemQuartzJob);
        return Result.success();
    }
}
