package com.education.business.service.education;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.SubjectInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.model.dto.SubjectInfoDto;
import com.education.model.entity.CourseInfo;
import com.education.model.entity.QuestionInfo;
import com.education.model.entity.SubjectInfo;
import com.education.model.entity.TestPaperInfo;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 13:53
 */
@Service
public class SubjectInfoService extends BaseService<SubjectInfoMapper, SubjectInfo> {

    @Resource
    private QuestionInfoService questionInfoService;
    @Resource
    private CourseInfoService courseInfoService;
    @Resource
    private TestPaperInfoService testPaperInfoService;

    public PageInfo<SubjectInfoDto> selectPageList(PageParam pageParam, SubjectInfo subjectInfo) {
        Page<SubjectInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, subjectInfo));
    }

    @Override
    public boolean saveOrUpdate(SubjectInfo subjectInfo) {
        Integer id = subjectInfo.getId();
        if (subjectInfo.getId() != null) {
            this.checkSubject(id);
        }
        return super.saveOrUpdate(subjectInfo);
    }

    public void deleteById(Integer id) {
        this.checkSubject(id);
        super.removeById(id);
    }

    private void checkSubject(Integer subjectId) {
        CourseInfo courseInfo = courseInfoService.selectFirst(Wrappers.<CourseInfo>lambdaQuery()
                .select(CourseInfo::getId).eq(CourseInfo::getSubjectId, subjectId));
        if (courseInfo != null) {
            throw new BusinessException("该科目已有课程使用");
        }

        QuestionInfo questionInfo = questionInfoService.selectFirst(Wrappers.<QuestionInfo>lambdaQuery()
                .select(QuestionInfo::getId).eq(QuestionInfo::getSubjectId, subjectId));
        if (questionInfo != null) {
            throw new BusinessException("该科目已有试题使用");
        }

        TestPaperInfo testPaperInfo = testPaperInfoService.selectFirst(Wrappers.<TestPaperInfo>lambdaQuery()
                .select(TestPaperInfo::getId).eq(TestPaperInfo::getSubjectId, subjectId));
        if (testPaperInfo != null) {
            throw new BusinessException("该科目已有试卷使用");
        }
    }
}
