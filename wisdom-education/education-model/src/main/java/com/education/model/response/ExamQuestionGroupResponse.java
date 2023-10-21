package com.education.model.response;

import com.education.model.dto.StudentExamInfoDto;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/5 11:26
 */
public class ExamQuestionGroupResponse extends QuestionGroupResponse {

    private StudentExamInfoDto studentExamInfoDto;

    public StudentExamInfoDto getStudentExamInfoDto() {
        return studentExamInfoDto;
    }

    public void setStudentExamInfoDto(StudentExamInfoDto studentExamInfoDto) {
        this.studentExamInfoDto = studentExamInfoDto;
    }
}
