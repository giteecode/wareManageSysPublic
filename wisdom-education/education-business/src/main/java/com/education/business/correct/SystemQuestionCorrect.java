package com.education.business.correct;

import cn.hutool.core.util.StrUtil;
import com.education.common.enums.BooleanEnum;
import com.education.common.enums.CorrectStatusEnum;
import com.education.common.enums.CorrectTypeEnum;
import com.education.common.enums.QuestionTypeEnum;
import com.education.common.utils.DateUtils;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.Date;
import java.util.Map;

/**
 * 试题系统自动批改 (处理客观题及未作答主观题, 然后保存学员本次考试答题记录)
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 20:54
 */
public class SystemQuestionCorrect extends QuestionCorrect {

    private int systemMark = 0;// 系统判分

    public SystemQuestionCorrect(StudentQuestionRequest studentQuestionRequest,
                                 Map<Integer, String> questionAnswerInfo) {
        super(studentQuestionRequest, null, questionAnswerInfo);
    }



    /**
     * 批改客观题及答案为空的主观题
     * @param
     */
    @Override
    public ExamInfo correctStudentQuestion() {
        for (QuestionAnswer questionAnswerItem : questionAnswerList) {
            ExamQuestionAnswer studentQuestionAnswer = new ExamQuestionAnswer();
            studentQuestionAnswer.setQuestionInfoId(questionAnswerItem.getQuestionInfoId());
            studentQuestionAnswer.setStudentId(this.getStudentId());
            studentQuestionAnswer.setQuestionPoints(questionAnswerItem.getQuestionMark());
            studentQuestionAnswer.setStudentAnswer(questionAnswerItem.getStudentAnswer());
            studentQuestionAnswer.setCreateDate(new Date());
            studentQuestionAnswer.setTestPaperId(studentQuestionRequest.getTestPaperInfoId());
            if (QuestionTypeEnum.isObjectiveQuestion(questionAnswerItem.getQuestionType()) ||
                    ObjectUtils.isEmpty(questionAnswerItem.getStudentAnswer())) {
                this.correctQuestionNumber++; // 批改试题数量加1
                this.objectiveQuestionNumber++; // 客观题加1

                String questionAnswer = questionAnswerInfo.get(questionAnswerItem.getQuestionInfoId());
                // 客观题答案不为空
                if (ObjectUtils.isNotEmpty(questionAnswerItem.getStudentAnswer())) {
                    questionAnswer = questionAnswer.replaceAll(StrUtil.COMMA, "");
                    String studentAnswer = questionAnswerItem.getStudentAnswer();
                    String studentAnswerProxy = null;
                    String questionAnswerProxy = ObjectUtils.charSort(questionAnswer);
                    if (ObjectUtils.isNotEmpty(studentAnswer)) {
                        studentAnswerProxy = ObjectUtils.charSort(studentAnswer.replaceAll(StrUtil.COMMA, ""));
                    }

                    if (questionAnswerProxy.equals(studentAnswerProxy)) {
                        studentQuestionAnswer.setMark(questionAnswerItem.getQuestionMark());
                        this.systemMark += questionAnswerItem.getQuestionMark();
                        super.addRightNumber();
                        studentQuestionAnswer.setCorrectStatus(CorrectStatusEnum.RIGHT.getValue());
                    } else {
                        this.newStudentWrongBook(questionAnswerItem);
                        studentQuestionAnswer.setCorrectStatus(CorrectStatusEnum.ERROR.getValue());
                    }
                } else {
                    // 未作答的客观题
                    this.newStudentWrongBook(questionAnswerItem);
                    studentQuestionAnswer.setCorrectStatus(CorrectStatusEnum.ERROR.getValue());
                }
            } else {
                if (ObjectUtils.isEmpty(questionAnswerItem.getStudentAnswer())) {
                    // 未作答的主观题系统直接判为错题
                    this.newStudentWrongBook(questionAnswerItem);
                    studentQuestionAnswer.setCorrectStatus(CorrectStatusEnum.ERROR.getValue());
                } else {
                    // 已作答的主观题设置为待批改状态
                    studentQuestionAnswer.setCorrectStatus(CorrectStatusEnum.CORRECT_RUNNING.getValue());
                }
                // 主观题数量加1
                this.subjectiveQuestionNumber++;
                // 缓存主观题信息
                super.addObjectiveQuestionAnswerList(studentQuestionAnswer);
            }
            studentQuestionAnswerList.add(studentQuestionAnswer);
        }
        return createExamInfo();
    }


    /**
     * 创建考试记录
     * @return
     */
    public ExamInfo createExamInfo() {
        this.examInfo = new ExamInfo();
        this.examInfo.setCreateDate(new Date());
        this.examInfo.setSystemMark(systemMark);
        this.examInfo.setRightNumber(super.getRightQuestionNumber());
        this.examInfo.setErrorNumber(super.getErrorQuestionNumber());
        this.examInfo.setSubjectiveQuestionNumber(subjectiveQuestionNumber);
        this.examInfo.setQuestionNumber(super.getQuestionNumber());
        this.examInfo.setWaitCorrectNumber(getQuestionNumber() - correctQuestionNumber);
        long examTime = super.getExamTime();
        examInfo.setStudentId(getStudentId());
        examInfo.setExamTime(DateUtils.secondToHourMinute(examTime));
        examInfo.setExamTimeLongValue(examTime);
        examInfo.setTestPaperInfoId(super.getTestPaperInfoId());

        // 系统批改试题数量等于试卷总题数
        if (this.correctQuestionNumber == this.getQuestionNumber()) {
            this.examInfo.setCorrectFlag(BooleanEnum.YES.getCode());
            this.examInfo.setCorrectType(CorrectTypeEnum.SYSTEM.getValue());
            this.examInfo.setMark(systemMark);
        }
        return this.examInfo;
    }
}
