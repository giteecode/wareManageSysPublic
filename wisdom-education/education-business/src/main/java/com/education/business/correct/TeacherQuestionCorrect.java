package com.education.business.correct;

import com.education.common.enums.BooleanEnum;
import com.education.common.enums.CorrectStatusEnum;
import com.education.common.enums.CorrectTypeEnum;
import com.education.common.enums.QuestionTypeEnum;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.Date;
import java.util.Map;

/**
 * 试题管理员批改 (处理已作答主观题)
 * 教师后台批改试题暂时不考虑并发问题
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 21:00
 */
public class TeacherQuestionCorrect extends QuestionCorrect {

    private int teacherMark = 0;


    public TeacherQuestionCorrect(StudentQuestionRequest studentQuestionRequest, ExamInfo examInfo, Map<Integer, String> questionAnswerInfo) {
        super(studentQuestionRequest, examInfo, questionAnswerInfo);
    }

    @Override
    public ExamInfo correctStudentQuestion() {
        questionAnswerList.forEach(questionAnswerItem -> {
            if (!QuestionTypeEnum.isObjectiveQuestion(questionAnswerItem.getQuestionType()) &&
                    ObjectUtils.isNotEmpty(questionAnswerItem.getStudentAnswer())) {
                ExamQuestionAnswer studentQuestionAnswer = createStudentQuestionAnswer(questionAnswerItem);
                studentQuestionAnswer.setMark(questionAnswerItem.getStudentMark());
                studentQuestionAnswer.setStudentAnswer(questionAnswerItem.getStudentAnswer());
                teacherMark += questionAnswerItem.getStudentMark();
                // 后台指定加入学员错题本
                if (questionAnswerItem.isErrorQuestionFlag()) {
                    this.addErrorNumber();
                    this.newStudentWrongBook(questionAnswerItem);
                    studentQuestionAnswer.setCorrectStatus(CorrectStatusEnum.ERROR.getValue());
                } else {
                    studentQuestionAnswer.setCorrectStatus(CorrectStatusEnum.CORRECTED.getValue());
                }
                super.addObjectiveQuestionAnswerList(studentQuestionAnswer);
            }
        });
        return updateExamInfo();
    }




    public ExamInfo updateExamInfo() {
        this.examInfo.setWaitCorrectNumber(0); // 更新待批改试题数量为0
        this.examInfo.setTeacherMark(teacherMark);
        this.examInfo.setCorrectFlag(BooleanEnum.YES.getCode()); // 设置为已批改
        this.examInfo.setUpdateDate(new Date());
        this.examInfo.setErrorNumber(this.examInfo.getErrorNumber() + this.getErrorQuestionNumber());
        if (this.getQuestionNumber() == this.correctQuestionNumber) {
            this.examInfo.setMark(teacherMark);
            this.examInfo.setCorrectType(CorrectTypeEnum.TEACHER.getValue());
        } else {
            this.examInfo.setMark(examInfo.getSystemMark() + teacherMark);
            this.examInfo.setCorrectType(CorrectTypeEnum.SYSTEM_AND_TEACHER.getValue());
        }
        return this.examInfo;
    }
}
