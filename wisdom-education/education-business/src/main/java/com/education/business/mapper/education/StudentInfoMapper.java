package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.StudentInfoDto;
import com.education.model.entity.StudentInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 18:19
 */
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

    /**
     * 学员列表
     * @param page
     * @param studentInfo
     * @return
     */
    Page<StudentInfoDto> selectPageList(Page<StudentInfoDto> page, StudentInfo studentInfo);
}
