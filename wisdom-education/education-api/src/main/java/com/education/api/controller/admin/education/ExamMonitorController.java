package com.education.api.controller.admin.education;

import cn.hutool.core.collection.CollUtil;
import com.education.business.service.education.ExamMonitorService;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.dto.ExamMonitor;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 考试监控接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/29 15:17
 */
@RestController
@RequestMapping("/examMonitor")
public class ExamMonitorController extends BaseController {

    @Resource
    private ExamMonitorService examMonitorService;

    /**
     * 获取试卷正在考试学员
     * @param testPaperId
     * @return
     */
    @GetMapping("/paper/{testPaperId}")
    public Result<PageInfo<ExamMonitor>> selectByTestPaperId(@PathVariable Integer testPaperId, PageParam pageParam) {
        List<ExamMonitor> studentInfoList = examMonitorService.getExamMonitorByTestPaperId(testPaperId);
        PageInfo pageInfo = new PageInfo<>();
        pageInfo.setDataList(CollUtil.page(pageParam.getPageNumber() - 1, pageParam.getPageSize(), studentInfoList));
        pageInfo.setTotal(studentInfoList.size());
        return Result.success(pageInfo);
    }

    /**
     * 强制交卷
     * @param examMonitor
     * @return
     */
    @PostMapping("/paper/force")
    public Result forceCommitPaper(@RequestBody ExamMonitor examMonitor) {
        examMonitorService.forceCommitPaper(examMonitor);
        return Result.success();
    }
}
