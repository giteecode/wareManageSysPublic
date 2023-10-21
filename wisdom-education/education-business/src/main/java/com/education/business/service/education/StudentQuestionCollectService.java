package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.StudentQuestionCollectMapper;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.common.model.PageInfo;
import com.education.model.entity.StudentQuestionCollect;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;

/**
 * 学员试题收藏
 * @author zjt
 * @create_at 2022年1月17日 0017 13:59
 * @since 1.0.5
 */
@Service
public class StudentQuestionCollectService extends BaseService<StudentQuestionCollectMapper, StudentQuestionCollect> {

    public PageInfo<StudentQuestionCollect> selectPage(PageParam pageParam) {
        Page page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
       return selectPage(baseMapper.selectPageList(page, UserSessionContext.getStudentId()));
    }

    public void deleteByQuestionId(Integer questionInfoId) {
        LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(StudentQuestionCollect.class)
                .eq(StudentQuestionCollect::getStudentId, UserSessionContext.getStudentId())
                .eq(StudentQuestionCollect::getQuestionInfoId, questionInfoId);
        super.remove(updateWrapper);
    }
}
