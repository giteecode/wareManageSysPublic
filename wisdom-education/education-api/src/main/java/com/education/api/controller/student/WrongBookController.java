package com.education.api.controller.student;

import com.education.business.service.education.StudentWrongBookService;
import com.education.business.session.UserSessionContext;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.request.PageParam;
import com.education.model.request.WrongBookQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 错题本管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/4 17:56
 */
@RestController
@RequestMapping("/wrongBook")
public class WrongBookController extends BaseController {

    @Resource
    private StudentWrongBookService studentWrongBookService;

    /**
     * 错题本列表
     * @param pageParam
     * @param wrongBookQuery
     * @return
     */
    @GetMapping
    public Result<PageInfo<QuestionInfoAnswer>> list(PageParam pageParam, WrongBookQuery wrongBookQuery) {
        wrongBookQuery.setStudentId(UserSessionContext.getStudentId());
        return Result.success(studentWrongBookService.selectPageList(pageParam, wrongBookQuery));
    }

    /**
     * 获取考试记录科目
     * @return
     */
    @GetMapping("/subject")
    public Result getSubjectList(Integer type) {
        return Result.success(studentWrongBookService.getSubjectList(type));
    }

    /**
     * 获取错题数量
     * @return
     */
    @GetMapping("/questionNumber")
    public Result questionNumber() {
        return Result.success(studentWrongBookService.questionNumber());
    }

    /**
     * 获取错题本科目试卷
     * @return
     */
    @GetMapping("/subject/{subjectId}/paper")
    public Result getTestPaperList(@PathVariable Integer subjectId, PageParam pageParam) {
        return Result.success(studentWrongBookService.getTestPaperList(subjectId, pageParam));
    }
}
