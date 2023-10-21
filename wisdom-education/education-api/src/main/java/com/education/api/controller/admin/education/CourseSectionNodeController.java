package com.education.api.controller.admin.education;

import com.education.business.service.education.CourseSectionNodeService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.entity.CourseSectionNode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程章节课时管理接口
 * @author zjt
 * @create_at 2022年3月28日 0028 10:03
 * @since 1.0.5
 */
@RestController
@RequestMapping("/course")
public class CourseSectionNodeController extends BaseController {

    @Resource
    private CourseSectionNodeService courseSectionNodeService;

    /**
     * 添加或修改章节课时
     * @param courseSectionNode
     * @return
     */
    @PostMapping("/section/node")
    public Result saveOrUpdateCourseSectionNode(@RequestBody @Validated CourseSectionNode courseSectionNode) {
        return Result.success(courseSectionNodeService.saveOrUpdateCourseSectionNode(courseSectionNode));
    }

    /**
     * 更新章节课时视频信息
     * @param courseSectionNode
     * @return
     */
    @PutMapping("/section/node/video")
    public Result updateSectionNodeVideoInfo(@RequestBody CourseSectionNode courseSectionNode) {
        return Result.success(courseSectionNodeService.saveOrUpdateCourseSectionNode(courseSectionNode));
    }

    /**
     * 删除章节课程
     * @param courseId
     * @param sectionNodeId
     * @return
     */
    @DeleteMapping("/{courseId}/section/node/{sectionNodeId}")
    public Result deleteCourseSectionNode(@PathVariable Integer courseId, @PathVariable Integer sectionNodeId) {
        courseSectionNodeService.delete(courseId, sectionNodeId);
        return Result.success(ResultCode.SUCCESS, "删除成功");
    }
}
