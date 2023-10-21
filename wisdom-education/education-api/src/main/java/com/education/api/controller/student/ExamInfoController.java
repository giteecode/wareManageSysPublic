package com.education.api.controller.student;

import com.education.business.service.education.ExamInfoService;
import com.education.business.service.education.ExamMonitorService;
import com.education.business.session.UserSessionContext;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.ExamMonitor;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 学生端考试记录管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:53
 */
@RequestMapping("/exam")
@RestController("student-exam")
public class ExamInfoController extends BaseController {

    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private ExamMonitorService examMonitorService;

    /**
     * 获取考试记录科目
     * @return
     */
    @GetMapping("/subject")
    public Result getSubjectList() {
        return Result.success(examInfoService.getSubjectList());
    }

    /**
     * 考试记录列表
     * @param pageParam
     * @param studentExamInfoDto
     * @return
     */
    @GetMapping
    public Result selectExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        return Result.success(examInfoService.selectStudentExamInfoList(pageParam, studentExamInfoDto));
    }


    /**
     * 获取考试试题及答案
     * @param examInfoId
     * @return
     */
    @GetMapping("/{id}/questionAnswer")
    public Result selectExamQuestionAnswer(@PathVariable("id") Integer examInfoId) {
        Integer studentId = UserSessionContext.getStudentId();
        return Result.success(examInfoService.selectExamQuestionAnswer(studentId, examInfoId));
    }

    /**
     * 获取考试结果
     * @param examInfoId
     * @return
     */
    @GetMapping("/{id}")
    public Result<StudentExamInfoDto> selectExamInfo(@PathVariable("id") Integer examInfoId) {
        return Result.success(examInfoService.getExamInfoById(examInfoId));
    }

    /**
     * 添加监控中心接口
     * @param examMonitor
     * @return
     */
    @PostMapping("/monitor")
    public Result addToExamExamMonitor(@RequestBody ExamMonitor examMonitor) {
        examMonitorService.addStudentToExamMonitor(examMonitor);
        return Result.success();
    }

    /**
     * 更新考试进度
     * @param examMonitor
     * @return
     */
    @PostMapping("/monitor/progress")
    public Result updateProgress(@RequestBody ExamMonitor examMonitor) {
        examMonitorService.updateProgress(examMonitor);
        return Result.success();
    }

    /**
     * 开始考试
     * @param testPaperInfoId
     * @return
     */
    @GetMapping("/start")
    public Result startExam(Integer testPaperInfoId) {
        examInfoService.startExam(testPaperInfoId);
        return Result.success();
    }
}
