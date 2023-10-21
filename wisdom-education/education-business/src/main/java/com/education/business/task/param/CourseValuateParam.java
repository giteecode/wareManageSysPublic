package com.education.business.task.param;

import java.math.BigDecimal;

/**
 * @author zjt
 * @create_at 2022年11月24日 0024 14:45
 * @since 2.0.1
 */
public class CourseValuateParam extends TaskParam {

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 评价分数
     */
    private BigDecimal valuateMark;

    public CourseValuateParam(String queueName) {
        super(queueName);
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getValuateMark() {
        return valuateMark;
    }

    public void setValuateMark(BigDecimal valuateMark) {
        this.valuateMark = valuateMark;
    }
}
