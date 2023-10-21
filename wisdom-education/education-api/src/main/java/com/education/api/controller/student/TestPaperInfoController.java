package com.education.api.controller.student;

import cn.hutool.core.collection.CollUtil;
import com.education.business.service.education.ExamInfoService;
import com.education.business.service.education.ExamMonitorService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.business.service.education.TestPaperInfoSettingService;
import com.education.business.session.UserSessionContext;
import com.education.common.annotation.FormLimit;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.ExamMonitor;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.request.PageParam;
import com.education.model.request.StudentQuestionRequest;
import com.education.model.response.StudentExamRate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学生端试卷管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 10:26
 */
@RestController("student-testPaperInfo")
@RequestMapping("/testPaperInfo")
public class TestPaperInfoController extends BaseController {

    @Resource
    private TestPaperInfoService testPaperInfoService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private ExamMonitorService examMonitorService;
    @Resource
    private TestPaperInfoSettingService testPaperInfoSettingService;

    /**
     * 试卷列表
     * @param pageParam
     * @param testPaperInfoDto
     * @return
     */
    @GetMapping
    public Result list(PageParam pageParam, TestPaperInfoDto testPaperInfoDto) {
        testPaperInfoDto.setPublishFlag(true);
        return Result.success(testPaperInfoService.selectPageList(pageParam, testPaperInfoDto));
    }

    /**
     * 获取试卷试题
     * @param id
     * @return sync = true 目前貌似只针对本地缓存有效 （该方式主要用户高并发下的同步操作，从而减少对数据库的请求）
     */
    @GetMapping("/{id}/question")
    public Result selectPaperQuestionById(@PathVariable Integer id) {
        List<TestPaperQuestionDto> questionDtoList = testPaperInfoService.selectPaperQuestionListByCache(id);
        // 不返回answer 字段值
        if (CollUtil.isNotEmpty(questionDtoList)) {
            questionDtoList.forEach(item -> item.setAnswer(null));
        }
        return Result.success(questionDtoList);
    }

    /**
     * 试卷详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(testPaperInfoService.getDetail(id));
    }

    /**
     * 提交试卷试题
     * @param studentQuestionRequest
     * @return
     */
    @PostMapping("/commit")
    @FormLimit(message = "请勿重复提交考试")
    public Result commitPaper(@RequestBody StudentQuestionRequest studentQuestionRequest) {
        return Result.success(ResultCode.SUCCESS,
                "提交成功",
                examInfoService.commitTestPaperInfoQuestion(studentQuestionRequest));
    }

    /**
     * 更新学员考试答题进度
     * @param studentExamRate
     * @return
     */
    @PostMapping("/examRate")
    @FormLimit
    public Result updateStudentExamRate(@RequestBody StudentExamRate studentExamRate) {
        ExamMonitor examMonitor = examMonitorService.getExamMonitorStudent(studentExamRate.getTestPaperInfoId(),
                UserSessionContext.getStudentId());
        examMonitor.setAnswerQuestionCount(studentExamRate.getAnswerQuestionCount());
        examMonitor.setTestPaperInfoId(studentExamRate.getTestPaperInfoId());
        examMonitorService.addStudentToExamMonitor(examMonitor);
        return Result.success();
    }

    /**
     * 获取试卷设置
     * @param testPaperId
     * @return
     */
    @GetMapping("/testPaperId/setting")
    public Result getPaperSetting(Integer testPaperId) {
        return Result.success(testPaperInfoSettingService.selectByTestPaperInfoId(testPaperId));
    }

    /**
     * 清除试卷考试缓存
     * @param id
     * @return
     */
    @DeleteMapping("/examTime/countDown/{id}")
    public Result deleteById(@PathVariable Integer id) {
        testPaperInfoService.deleteTestCacheByPaperId(id);
        return Result.success();
    }
}
