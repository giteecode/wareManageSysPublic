package com.education.api.controller.admin.education;

import cn.hutool.core.collection.CollUtil;
import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.parser.ExcelQuestionImportResult;
import com.education.business.parser.QuestionImportResult;
import com.education.business.parser.TxtQuestionImportResult;
import com.education.business.service.education.ExamQuestionAnswerService;
import com.education.business.service.education.QuestionInfoService;
import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsType;
import com.education.common.annotation.ParamsValidate;
import com.education.common.base.BaseController;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.entity.QuestionInfo;
import com.education.model.request.PageParam;
import com.education.model.request.QuestionInfoQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 试题管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 10:45
 */
@RestController
@RequestMapping("/question")
public class QuestionInfoController extends BaseController {

    @Resource
    private QuestionInfoService questionInfoService;
    @Resource
    private ExamQuestionAnswerService examQuestionAnswerService;

    private final Logger logger = LoggerFactory.getLogger(QuestionInfoController.class);

    /**
     * 试题列表
     * @param pageParam
     * @param questionInfoQuery
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:question:list")
    public Result list(PageParam pageParam, QuestionInfoQuery questionInfoQuery) {
        return Result.success(questionInfoService.selectPageList(pageParam, questionInfoQuery));
    }

    /**
     * 添加或修改试题
     * @param questionInfoDto
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:question:save", "system:question:update"}, logical = Logical.OR)
    @ParamsValidate(params = {
        @Param(name = "schoolType", message = "请选择所属阶段"),
        @Param(name = "gradeInfoId", message = "请选择所属年级"),
        @Param(name = "subjectId", message = "请选择所属科目"),
        @Param(name = "questionType", message = "请选择试题类型"),
        @Param(name = "content", message = "请输入试题内容"),
        @Param(name = "answer", message = "请输入试题答案")
    }, paramsType = ParamsType.JSON_DATA)
    public Result saveOrUpdate(@RequestBody QuestionInfoDto questionInfoDto) {
        return Result.success(questionInfoService.saveOrUpdateQuestionInfo(questionInfoDto));
    }

    /**
     * 试题详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        return Result.success(questionInfoService.selectById(id));
    }

    /**
     * 试题详情
     * @param id
     * @return
     */
    @GetMapping("/{id}/analysis")
    public Result questionAnalysis(@PathVariable Integer id) {
        return Result.success(questionInfoService.questionAnalysis(id));
    }

    /**
     * 试题作答记录列表
     * @return
     */
    @GetMapping("/{id}/answer")
    public Result questionAnswerListPage(@PathVariable Integer id, PageParam pageParam) {
        return Result.success(examQuestionAnswerService.listPageByQuestionId(id, pageParam));
    }

    /**
     * 根据id 删除试题
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:question:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        return Result.success(questionInfoService.deleteById(id));
    }

    /**
     * 导入试题
     * @param gradeInfoId
     * @param subjectId
     * @param file
     * @return
     */
    @PostMapping("/import")
    @ParamsValidate(params = {
        @Param(name = "schoolType", message = "请选择导入所属阶段"),
        @Param(name = "gradeInfoId", message = "请选择导入所属年级"),
        @Param(name = "subjectId", message = "请选择导入所属科目")
    })
    public Result importQuestion(
                                 HttpServletResponse response,
                                 @RequestParam Integer schoolType,
                                 @RequestParam Integer gradeInfoId,
                                 @RequestParam Integer subjectId,
                                 @RequestParam MultipartFile file) {
        String contentType = file.getContentType();
        if (!excelTypes.contains(contentType) && !textTypes.contains(contentType)) {
            return Result.fail(ResultCode.FAIL, "只能导入excel或者txt类型文件");
        }
        try {
            QuestionImportResult questionImportResult;
            if (excelTypes.contains(contentType)) {
                questionImportResult = new ExcelQuestionImportResult(file, response);
            } else {
                questionImportResult = new TxtQuestionImportResult(file);
            }

            questionImportResult.readTemplate();

            if (!questionImportResult.isHasData()) {
                return Result.success(ResultCode.FAIL, questionImportResult.getErrorMsg());
            }

            List<QuestionInfo> questionInfoList = questionImportResult.getSuccessImportQuestionList();
            int successCount = 0;
            if (CollUtil.isNotEmpty(questionInfoList)) {
                successCount = questionInfoService.importQuestion(schoolType, gradeInfoId,
                        subjectId, questionInfoList);
            }
            int failCount = 0;
            List<QuestionInfo> failQuestionList = questionImportResult.getFailImportQuestionList();

            if (failQuestionList != null) {
                failCount = failQuestionList.size();
            }

            // excel 数据校验失败
            String msg = successCount + "道试题导入成功";
            if (ObjectUtils.isNotEmpty(questionImportResult.getErrorMsg())) {
                if (failCount > 0) {
                    msg += "," + failCount + "道试题导入失败，分别为:" + questionImportResult.getErrorMsg();
                }
                return Result.success(ResultCode.EXCEL_VERIFICATION_FAIL,
                       msg, questionImportResult.getErrorFileUrl());
            }
            return Result.success(ResultCode.SUCCESS, msg);
        } catch (Exception e) {
            logger.error("数据导入失败", e);
        }
        return Result.fail(ResultCode.FAIL, "数据导入失败");
    }

    /**
     * 获取每种试题类型数量
     * @return
     */
    @GetMapping("/type/group")
    public Result getNumberByQuestionTypeGroup(Integer gradeInfoId, Integer subjectId) {
        return Result.success(questionInfoService.getNumberByQuestionTypeGroup(gradeInfoId, subjectId));
    }
}
