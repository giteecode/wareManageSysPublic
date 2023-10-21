package com.education.model.dto;

import com.education.common.enums.CorrectStatusEnum;
import com.education.model.entity.ExamQuestionAnswer;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/30 16:31
 */
public class ExamQuestionAnswerDto extends ExamQuestionAnswer {

    /**
     * 学员姓名
     */
    private String studentName;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 试题类型
     */
    private Integer questionType;

    /**
     * 试题批改状态
     */
    private CorrectStatusEnum correctStatusEnum;



    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public CorrectStatusEnum getCorrectStatusEnum() {
        return correctStatusEnum;
    }

    public void setCorrectStatusEnum(CorrectStatusEnum correctStatusEnum) {
        this.correctStatusEnum = correctStatusEnum;
    }
}
