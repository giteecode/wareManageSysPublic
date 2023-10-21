package com.education.api.controller.student;

import com.education.business.service.education.StudentCourseCollectService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.StudentCourseCollectDto;
import com.education.model.entity.StudentCourseCollect;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程收藏
 * @author zengjintao
 * @create_at 2021/12/26 11:01
 * @since version 1.0.3
 */
@RestController
@RequestMapping("/course/collect")
public class StudentCourseCollectController extends BaseController {

    @Resource
    private StudentCourseCollectService studentCourseCollectService;

    /**
     * 课程收藏或取消收藏
     * @param studentCourseCollectDto
     * @return
     */
    @PostMapping
    public Result collect(@RequestBody StudentCourseCollectDto studentCourseCollectDto) {
        studentCourseCollectService.collect(studentCourseCollectDto);
        return Result.success();
    }

}
