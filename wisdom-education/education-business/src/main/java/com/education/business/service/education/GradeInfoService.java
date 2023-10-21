package com.education.business.service.education;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.GradeInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.exception.BusinessException;
import com.education.model.entity.GradeInfo;
import com.education.model.entity.QuestionInfo;
import com.education.model.entity.StudentInfo;
import com.education.model.entity.SubjectInfo;
import com.education.model.entity.TestPaperInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 13:31
 */
@Service
public class GradeInfoService extends BaseService<GradeInfoMapper, GradeInfo> {

    @Resource
    private SubjectInfoService subjectInfoService;
    @Resource
    private StudentInfoService studentInfoService;
    @Resource
    private QuestionInfoService questionInfoService;
    @Resource
    private TestPaperInfoService testPaperInfoService;

    public List<SubjectInfo> selectSubjectListById(@PathVariable Integer id) {
        LambdaQueryWrapper<SubjectInfo> queryWrapper = Wrappers.<SubjectInfo>lambdaQuery()
                .eq(SubjectInfo::getGradeInfoId, id);
        return subjectInfoService.list(queryWrapper);
    }

    public void deleteById(Integer id) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(StudentInfo.class)
                .select(StudentInfo::getId)
                .eq(StudentInfo::getGradeInfoId, id);
        StudentInfo studentInfo = studentInfoService.selectFirst(queryWrapper);
        Assert.isNull(studentInfo, () -> new BusinessException("年级下存在学员，无法删除"));

        LambdaQueryWrapper subjectLambdaQueryWrapper = Wrappers.lambdaQuery(SubjectInfo.class)
                .select(SubjectInfo::getId)
                .eq(SubjectInfo::getGradeInfoId, id);

        SubjectInfo subjectInfo = subjectInfoService.selectFirst(subjectLambdaQueryWrapper);
        Assert.isNull(subjectInfo, () -> new BusinessException("年级下存在科目，无法删除"));

        LambdaQueryWrapper questionLambdaQueryWrapper = Wrappers.lambdaQuery(QuestionInfo.class)
                .eq(QuestionInfo::getGradeInfoId, id);
        QuestionInfo questionInfo = questionInfoService.selectFirst(questionLambdaQueryWrapper);
        Assert.isNull(questionInfo, () -> new BusinessException("年级下存在试题，无法删除"));

        LambdaQueryWrapper testPaperLambdaQueryWrapper = Wrappers.lambdaQuery(TestPaperInfo.class)
                .eq(TestPaperInfo::getGradeInfoId, id);
        TestPaperInfo testPaperInfo = testPaperInfoService.selectFirst(testPaperLambdaQueryWrapper);
        Assert.isNull(testPaperInfo, () -> new BusinessException("年级下存在试卷，无法删除"));
        super.removeById(id);
    }
}
