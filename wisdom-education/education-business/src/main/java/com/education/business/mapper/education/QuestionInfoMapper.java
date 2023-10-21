package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.entity.QuestionInfo;
import com.education.model.request.QuestionInfoQuery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 11:02
 */
public interface QuestionInfoMapper extends BaseMapper<QuestionInfo> {

    /**
     * 试题分页列表
     * @param page
     * @param questionInfo
     * @return
     */
    Page<QuestionInfoDto> selectPageList(Page<QuestionInfoDto> page, QuestionInfoQuery questionInfo);


    QuestionInfoDto selectById(Integer id);

    /**
     * 获取试题数量按试题类型分组
     * @return
     */
    List<QuestionInfoDto> getNumberByQuestionTypeGroup(@Param("gradeInfoId") Integer gradeInfoId, @Param("subjectId") Integer subjectId);
}
