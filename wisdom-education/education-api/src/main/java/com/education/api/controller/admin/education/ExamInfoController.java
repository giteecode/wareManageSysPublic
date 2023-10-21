package com.education.api.controller.admin.education;

import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.ExamInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.entity.TestPaperInfo;
import com.education.model.request.PageParam;
import com.education.model.request.StudentQuestionRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 考试管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 19:55
 */
@RestController("/system")
@RequestMapping("/exam")
public class ExamInfoController extends BaseController {

    @Resource
    private ExamInfoService examInfoService;

    @GetMapping
    @RequiresPermissions("system:exam:list")
    public Result list(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        return Result.success(examInfoService.selectExamInfoList(pageParam, studentExamInfoDto));
    }

    /**
     * 获取学员试卷试题列表
     * @param studentId
     * @param examInfoId
     * @return
     */
    @GetMapping("/{studentId}/{examInfoId}/question")
    public Result getExamQuestionList(@PathVariable Integer studentId, @PathVariable Integer examInfoId) {
        return Result.success(examInfoService.selectExamQuestionAnswer(studentId, examInfoId));
    }

    /**
     * 批改学员试卷
     * @param studentQuestionRequest
     * @return
     */
    @RequiresPermissions("system:exam:correct")
    @PostMapping("/correct")
    public Result correctExamQuestion(@RequestBody StudentQuestionRequest studentQuestionRequest) {
        examInfoService.correctStudentExam(studentQuestionRequest);
        return Result.success(ResultCode.SUCCESS, "批改成功");
    }

    /**
     * 考试列表
     * @param testPaperInfo
     * @return
     */
    @RequiresPermissions("system:exam:examReportList")
    @GetMapping("/analyse")
    public Result getExamAnalyseListPage(PageParam pageParam, TestPaperInfo testPaperInfo) {
        return Result.success(examInfoService.getExamAnalyseListPage(pageParam, testPaperInfo));
    }

    /**
     * 考试成绩分析
     * @param testPaperInfoId
     * @return
     */
    @GetMapping("/analyse/{testPaperInfoId}")
    public Result getExamAnalyseByPaperId(@PathVariable Integer testPaperInfoId) {
        return Result.success(examInfoService.getExamAnalyseByPaperId(testPaperInfoId));
    }

    /**
     * 获取考试排名列表
     * @param testPaperInfoId
     * @return
     */
    @GetMapping("/{testPaperInfoId}/ranking")
    public Result getExamRankingList(PageParam pageParam, @PathVariable Integer testPaperInfoId) {
        return Result.success(examInfoService.getExamRankingList(pageParam, testPaperInfoId));
    }

    /**
     * 获取考试试卷试题分析
     * @param pageParam
     * @param testPaperInfoId
     * @return
     */
    @GetMapping("/{testPaperInfoId}/question/analysis")
    public Result getExamQuestionAnalysis(PageParam pageParam, @PathVariable Integer testPaperInfoId) {
        return Result.success(examInfoService.getExamQuestionAnalysis(pageParam, testPaperInfoId));
    }
}
