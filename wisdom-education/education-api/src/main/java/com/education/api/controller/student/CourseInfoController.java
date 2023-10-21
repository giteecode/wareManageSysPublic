package com.education.api.controller.student;

import com.education.business.service.education.CourseInfoService;
import com.education.business.service.education.CourseSectionService;
import com.education.business.session.UserSessionContext;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 课程管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 18:42
 */
@RestController("student-courseInfo")
@RequestMapping("/courseInfo")
public class CourseInfoController extends BaseController {

    @Resource
    private CourseInfoService courseInfoService;
    @Resource
    private CourseSectionService courseSectionService;

    /**
     * 获取首页推荐课程
     * @return
     */
    @GetMapping("/recommend")
    public Result recommendList() {
        return Result.success(courseInfoService.getRecommendList());
    }

    /**
     * 课程列表
     * @param pageParam
     * @param courseInfo
     * @return
     */
    @GetMapping
    public Result selectPageList(PageParam pageParam, CourseInfo courseInfo) {
        courseInfo.setGradeInfoId(UserSessionContext.getStudentUserSession().getGradeInfoId());
        return Result.success(courseInfoService.selectPageList(pageParam, courseInfo));
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

    /**
     * 获取课程详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(courseInfoService.getById(id));
    }
}
