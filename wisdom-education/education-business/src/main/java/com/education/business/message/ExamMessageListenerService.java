package com.education.business.message;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.education.ExamInfoService;
import com.education.business.service.education.ExamQuestionAnswerService;
import com.education.business.service.education.StudentWrongBookService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.business.service.system.SystemMessageLogService;
import com.education.common.constants.SystemConstants;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.MessageLog;
import com.education.model.entity.ExamQuestionAnswer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/4/5 19:21
 */
@Service
public class ExamMessageListenerService {

    @Resource
    private StudentWrongBookService studentWrongBookService;
    @Resource
    private SystemMessageLogService systemMessageLogService;
    @Resource
    private ExamQuestionAnswerService studentQuestionAnswerService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private TestPaperInfoService testPaperInfoService;

    /**
     * 注意  @Transactional 不能和 @RabbitListener 一起使用
     * 因此将业务方法单独抽出来
     * @param examMessage
     * @param messageId
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void doExamCommitMessageBiz(ExamMessage examMessage, String messageId) {
        // 保存考试记录
        ExamInfo examInfo = examMessage.getExamInfo();
        examInfoService.save(examInfo);
        // 批量保存学员错题
        if (CollectionUtil.isNotEmpty(examMessage.getStudentWrongBookList())) {
            studentWrongBookService.saveBatch(examMessage.getStudentWrongBookList());
        }
        // 保存学员答题记录
        List<ExamQuestionAnswer> studentQuestionAnswerList = examMessage.getStudentQuestionAnswerList();

        String testPaperName = testPaperInfoService.getNameById(examInfo.getTestPaperInfoId());
        studentQuestionAnswerList.stream().forEach(item -> {
            item.setExamInfoId(examInfo.getId());
            item.setTestPaperId(item.getTestPaperId());
            item.setTestPaperName(testPaperName);
        });

        studentQuestionAnswerService.saveBatch(studentQuestionAnswerList);
        // 更新试卷参考人数
        testPaperInfoService.updateExamNumber(examInfo.getTestPaperInfoId());
        LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                .set(MessageLog::getStatus, SystemConstants.CONSUME_SUCCESS)
                .eq(MessageLog::getCorrelationDataId, messageId);
        systemMessageLogService.update(null, updateWrapper);
    }
}
