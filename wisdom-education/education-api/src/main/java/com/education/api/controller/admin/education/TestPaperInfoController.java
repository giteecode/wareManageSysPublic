package com.education.api.controller.admin.education;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.TestPaperInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.TestPaperInfo;
import com.education.model.entity.TestPaperQuestionInfo;
import com.education.model.request.AutoCreatePaperRequest;
import com.education.model.request.PageParam;
import com.education.model.request.TestPaperQuestionRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 试卷管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:21
 */
@RestController
@RequestMapping("/testPaperInfo")
public class TestPaperInfoController extends BaseController {

    @Resource
    private TestPaperInfoService testPaperInfoService;

    /**
     * 试卷列表
     * @param pageParam
     * @param testPaperInfoDto
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:testPaperInfo:list")
    public Result list(PageParam pageParam, TestPaperInfoDto testPaperInfoDto) {
        return Result.success(testPaperInfoService.selectPageList(pageParam, testPaperInfoDto));
    }

    /**
     * 添加或修改试卷
     * @param testPaperInfo
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:testPaperInfo:save", "system:testPaperInfo:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody TestPaperInfo testPaperInfo) {
        testPaperInfoService.saveOrUpdate(testPaperInfo);
        return Result.success();
    }

    /**
     * 保存试卷试题
     * @param testPaperQuestionInfoList
     * @return
     */
    @PostMapping("/question")
    public Result saveTestPaperInfoQuestion(@RequestBody List<TestPaperQuestionInfo> testPaperQuestionInfoList) {
        testPaperInfoService.saveTestPaperInfoQuestion(testPaperQuestionInfoList);
        return Result.success();
    }

    /**
     * 获取试卷试题列表
     * @param testPaperQuestionRequest
     * @return
     */
    @GetMapping("/question")
    @RequiresPermissions("system:testPaperInfo:relevanceQuestion")
    public Result selectPaperQuestionList(PageParam pageParam, TestPaperQuestionRequest testPaperQuestionRequest) {
        return Result.success(testPaperInfoService.selectPaperQuestionList(pageParam, testPaperQuestionRequest));
    }

    /**
     * 移除试卷试题
     * @param testPaperQuestionInfo
     * @return
     */
    @DeleteMapping("/question")
    public Result removePaperQuestion(@RequestBody TestPaperQuestionInfo testPaperQuestionInfo) {
        return Result.success(testPaperInfoService.removePaperQuestion(testPaperQuestionInfo));
    }

    /**
     * 修改试卷试题分数或者排序
     * @param testPaperQuestionDto
     * @return
     */
    @PutMapping("/markOrSort")
    public Result updatePaperQuestionMarkOrSort(@RequestBody TestPaperQuestionDto testPaperQuestionDto) {
        testPaperInfoService.updatePaperQuestionMarkOrSort(testPaperQuestionDto);
        return Result.success();
    }



    /**
     * 发布试卷
     * @param testPaperInfoId
     * @return
     */
    @PutMapping("/publish/{testPaperInfoId}")
    @RequiresPermissions("system:testPaperInfo:publish")
    public Result publishTestPaperInfo(@PathVariable Integer testPaperInfoId) {
        return Result.success(testPaperInfoService.publishTestPaperInfo(testPaperInfoId));
    }

    /**
     * 撤销试卷
     * @param testPaperInfoId
     * @return
     */
    @PutMapping("cancel/{testPaperInfoId}")
    @RequiresPermissions("system:testPaperInfo:cancel")
    public Result cancelTestPaperInfo(@PathVariable Integer testPaperInfoId) {
        return Result.success(testPaperInfoService.cancelTestPaperInfo(testPaperInfoId));
    }

    /**
     * 删除试卷
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:testPaperInfo:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        return Result.success(testPaperInfoService.deleteById(id));
    }

    /**
     * 试卷打印
     * @param testPaperInfoId
     * @return
     */
    @GetMapping("print/{testPaperInfoId}")
    public Result printPaperInfo(@PathVariable Integer testPaperInfoId) {
        return Result.success(testPaperInfoService.printPaperInfo(testPaperInfoId));
    }

    /**
     * 智能组卷接口
     * @param autoCreatePaperRequest
     * @return
     */
    @PostMapping("/autoCreate")
    public Result autoCreate(@RequestBody @Validated AutoCreatePaperRequest autoCreatePaperRequest) {
        testPaperInfoService.autoCreate(autoCreatePaperRequest);
        return Result.success();
    }

    /**
     * 试卷试题作答分析
     * @param id
     * @return
     */
    @GetMapping("/{id}/question/analysis")
    public Result questionAnalysis(@PathVariable Integer id, PageParam pageParam) {
        return Result.success(testPaperInfoService.questionAnalysis(id, pageParam));
    }
}
