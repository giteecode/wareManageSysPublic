package com.education.business.task;

import com.education.business.service.education.CourseInfoService;
import com.education.business.task.param.CourseValuateParam;
import com.education.common.annotation.EventQueue;
import com.education.common.constants.LocalQueueConstants;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 课程评价提交异步更新课程评价数量、评价分数
 * @author zengjintao
 * @create_at 2021/10/17 10:35
 * @since version 1.0.3
 */
@Component
@EventQueue(name = LocalQueueConstants.COURSE_VALUATE)
public class CourseValuateMessageListener implements TaskListener<CourseValuateParam> {

    @Resource
    private CourseInfoService courseInfoService;

    @Override
    public void onMessage(CourseValuateParam taskParam) {
        Integer courseId = taskParam.getCourseId();
        BigDecimal valuateMark = taskParam.getValuateMark();
        courseInfoService.updateCommentNumberAndValuateMark(courseId, valuateMark);
    }
}
