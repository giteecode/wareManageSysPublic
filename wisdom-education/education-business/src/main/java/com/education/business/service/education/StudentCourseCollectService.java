package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.StudentCourseCollectMapper;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.common.enums.BooleanEnum;
import com.education.model.dto.StudentCourseCollectDto;
import com.education.model.entity.StudentCourseCollect;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author zengjintao
 * @create_at 2021/12/26 11:02
 * @since version 1.0.4
 */
@Service
public class StudentCourseCollectService extends BaseService<StudentCourseCollectMapper, StudentCourseCollect> {

    public void collect(StudentCourseCollectDto studentCourseCollectDto) {
        Integer studentId = UserSessionContext.getStudentId();
        if (BooleanEnum.YES.getCode().equals(studentCourseCollectDto.getCollectFlag())) {
            studentCourseCollectDto.setStudentId(studentId);
            studentCourseCollectDto.setCreateDate(new Date());
            super.save(studentCourseCollectDto);
        } else {
            LambdaQueryWrapper<StudentCourseCollect> queryWrapper = Wrappers.lambdaQuery(StudentCourseCollect.class)
                    .eq(StudentCourseCollect::getCourseId, studentCourseCollectDto.getCourseId())
                    .eq(StudentCourseCollect::getStudentId, studentId);
            super.remove(queryWrapper);
        }
    }
}
