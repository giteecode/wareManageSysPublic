package com.education.api.controller.student;

import com.education.business.service.education.StudentQuestionCollectService;
import com.education.business.session.UserSessionContext;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.StudentQuestionCollect;
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
 * 试题收藏接口
 * @author zjt
 * @create_at 2022年1月17日 0017 13:54
 * @since 1.0.5
 */
@RestController
@RequestMapping("/question/collect")
public class StudentQuestionCollectController extends BaseController {

    @Resource
    private StudentQuestionCollectService studentQuestionCollectService;

    /**
     * 学员试题收藏列表
     * @param pageParam
     * @return
     */
    @GetMapping
    public Result listPage(PageParam pageParam) {
        return Result.success(studentQuestionCollectService.selectPage(pageParam));
    }

    /**
     * 添加试题收藏
     * @param studentQuestionCollect
     * @return
     */
    @PostMapping
    public Result save(@RequestBody StudentQuestionCollect studentQuestionCollect) {
        studentQuestionCollect.setStudentId(UserSessionContext.getStudentId());
        studentQuestionCollectService.save(studentQuestionCollect);
        return Result.success();
    }

    /**
     * 删除试题收藏
     * @param questionId
     * @return
     */
    @DeleteMapping("/{questionId}/remove")
    public Result deleteByQuestionId(@PathVariable Integer questionId) {
        studentQuestionCollectService.deleteByQuestionId(questionId);
        return Result.success();
    }
}
