package com.education.api.controller.admin.education;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.SubjectInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.entity.SubjectInfo;
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
 * 科目管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 13:51
 */
@RestController
@RequestMapping("/subject")
public class SubjectInfoController extends BaseController {

    @Resource
    private SubjectInfoService subjectInfoService;

    /**
     * 科目列表
     * @param pageParam
     * @param subjectInfo
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:subject:list")
    public Result list(PageParam pageParam, SubjectInfo subjectInfo) {
        return Result.success(subjectInfoService.selectPageList(pageParam, subjectInfo));
    }

    /**
     * 添加或修改科目
     * @param subjectInfo
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:subject:save", "system:subject:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody @Validated SubjectInfo subjectInfo) {
        subjectInfoService.saveOrUpdate(subjectInfo);
        return Result.success();
    }

    @DeleteMapping("{id}")
    @RequiresPermissions("system:subject:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        subjectInfoService.deleteById(id);
        return Result.success(ResultCode.SUCCESS, "删除成功");
    }
}
