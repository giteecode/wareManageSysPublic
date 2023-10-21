package com.education.business.service.education;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.TestPaperQuestionInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.entity.TestPaperQuestionInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 13:26
 */
@Service
public class TestPaperQuestionInfoService extends BaseService<TestPaperQuestionInfoMapper, TestPaperQuestionInfo> {

    @Resource
    private ExamQuestionAnswerService examQuestionAnswerService;

    public boolean deleteByTestPaperInfoId(Integer testPaperInfoId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(TestPaperQuestionInfo.class)
                .eq(TestPaperQuestionInfo::getTestPaperInfoId, testPaperInfoId);
        return super.remove(queryWrapper);
    }


    /**
     * 校验试卷是否关联了试题
     * @param testPaperInfoId
     * @return
     */
    public boolean hasTestPaperInfoQuestion(Integer testPaperInfoId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(TestPaperQuestionInfo.class)
                .eq(TestPaperQuestionInfo::getTestPaperInfoId, testPaperInfoId)
                .select(TestPaperQuestionInfo::getId).last(" limit 1");
        TestPaperQuestionInfo testPaperQuestionInfo = super.getOne(queryWrapper);
        return ObjectUtils.isNotEmpty(testPaperQuestionInfo);
    }

    public boolean verificationQuestionIsUsed(Integer questionInfoId) {
        LambdaQueryWrapper queryWrapper = Wrappers.<TestPaperQuestionInfo>lambdaQuery()
                .eq(TestPaperQuestionInfo::getQuestionInfoId, questionInfoId)
                .last(" limit 1");
        TestPaperQuestionInfo testPaperQuestionInfo = super.getOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(testPaperQuestionInfo)) {
            return false;
        }

        ExamQuestionAnswer studentQuestionAnswer = examQuestionAnswerService.selectByQuestionInfoId(questionInfoId);
        return ObjectUtils.isEmpty(studentQuestionAnswer);
    }
}
