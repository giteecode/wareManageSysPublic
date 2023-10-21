package com.education.api.controller.student;

import com.education.business.service.education.CourseStudyProgressService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 课程学习进度接口
 * @author zjt
 * @create_at 2022年1月13日 0013 10:26
 * @since 1.0.5
 */
@RestController
@RequestMapping("/courseStudy")
public class CourseStudyProgressController extends BaseController {

    @Resource
    private CourseStudyProgressService courseStudyProgressService;

    /**
     * 课程学习列表
     * @param pageParam
     * @return
     */
    @GetMapping
    public Result listPage(PageParam pageParam) {
        return Result.success(courseStudyProgressService.listPage(pageParam));
    }


}
