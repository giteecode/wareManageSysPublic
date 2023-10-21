package com.education.api.controller.student;

import com.education.business.service.education.GradeInfoService;
import com.education.business.service.education.SubjectInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 9:45
 */
@RestController("student-gradeInfo")
@RequestMapping("/gradeInfo")
public class GradeInfoController extends BaseController {

    @Resource
    private GradeInfoService gradeInfoService;

    /**
     * 根据年级获取科目列表
     * @param id
     * @return
     */
    @GetMapping("/{id}/subject")
    public Result selectSubjectListById(@PathVariable Integer id) {
        return Result.success(gradeInfoService.selectSubjectListById(id));
    }
}
