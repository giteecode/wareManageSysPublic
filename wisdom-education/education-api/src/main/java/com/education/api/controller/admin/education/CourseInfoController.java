package com.education.api.controller.admin.education;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.CourseInfoService;
import com.education.business.service.education.CourseSectionService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 15:03
 */
@RestController
@RequestMapping("/course")
public class CourseInfoController extends BaseController {

    @Resource
    private CourseInfoService courseInfoService;
    @Resource
    private CourseSectionService courseSectionService;


    /**
     * 课程列表
     * @param pageParam
     * @param courseInfo
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:course:list")
    public Result list(PageParam pageParam, CourseInfo courseInfo) {
        return Result.success(courseInfoService.selectPageList(pageParam, courseInfo));
    }

    /**
     * 添加或修改课程
     * @param courseInfo
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:course:save", "system:course:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody @Validated CourseInfo courseInfo) {
        courseInfoService.saveOrUpdateCourse(courseInfo);
        return Result.success();
    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:course:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        courseInfoService.deleteById(id);
        return Result.success();
    }


    /**
     * 课程章节列表
     * @param courseId
     * @return
     */
    @GetMapping("/{courseId}/section")
    public Result selectSectionByCourseId(@PathVariable Integer courseId) {
        return Result.success(courseSectionService.selectListByCourseId(courseId));
    }
}
