package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.entity.QuestionInfo;
import com.education.model.entity.StudentQuestionCollect;
import org.apache.ibatis.annotations.Param;

/**
 * @author zjt
 * @create_at 2022年1月17日 0017 13:59
 * @since 1.0.5
 */
public interface StudentQuestionCollectMapper extends BaseMapper<StudentQuestionCollect> {

    /**
     * 学员收藏试题列表
     * @param page
     * @param studentId
     * @return
     */
    Page<QuestionInfo> selectPageList(Page<QuestionInfo> page, @Param("studentId") Integer studentId);
}
