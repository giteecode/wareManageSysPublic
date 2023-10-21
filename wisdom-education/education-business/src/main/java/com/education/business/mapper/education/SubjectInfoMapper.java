package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.SubjectInfoDto;
import com.education.model.entity.SubjectInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 13:52
 */
public interface SubjectInfoMapper extends BaseMapper<SubjectInfo> {

    /**
     * 科目列表
     * @param page
     * @param subjectInfo
     * @return
     */
    Page<SubjectInfoDto> selectPageList(Page<SubjectInfoDto> page, SubjectInfo subjectInfo);
}
