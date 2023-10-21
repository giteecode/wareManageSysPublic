package com.education.api.controller.admin.education;

import com.education.business.service.education.CourseSectionService;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.entity.CourseSection;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 课程章节管理接口
 * @author zjt
 * @create_at 2022年3月28日 0028 09:59
 * @since 1.0.5
 */
@RestController
public class CourseSectionController {

    @Resource
    private CourseSectionService courseSectionService;

    /**
     * 添加章节
     * @param courseSection
     * @return
     */
    @PostMapping("/course/section")
    public Result saveOrUpdateCourseSection(@RequestBody @Validated CourseSection courseSection) {
        return Result.success(courseSectionService.saveOrUpdate(courseSection));
    }

    /**
     * 删除章
     * @param courseId
     * @param sectionId
     * @return
     */
    @DeleteMapping("/course/{courseId}/section/{sectionId}")
    public Result deleteCourseSection(@PathVariable Integer courseId, @PathVariable Integer sectionId) {
        courseSectionService.delete(courseId, sectionId);
        return Result.success(ResultCode.SUCCESS, "删除成功");
    }
}
