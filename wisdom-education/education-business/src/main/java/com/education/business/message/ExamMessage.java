package com.education.business.message;

import com.education.model.entity.ExamInfo;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.entity.ExamWrongBook;
import java.util.List;

/**
 * @Auther: zjt
 * @Date: 2021/4/2 15:49
 * @Version:2.1.0
 */
public class ExamMessage extends QueueMessage {

    /**
     * 错题本列表
     */
    private List<ExamWrongBook> studentWrongBookList;

    /**
     * 答题记录列表
     */
    private List<ExamQuestionAnswer> studentQuestionAnswerList;

    /**
     * 考试记录
     */
    private ExamInfo examInfo;


    public List<ExamWrongBook> getStudentWrongBookList() {
        return studentWrongBookList;
    }

    public void setStudentWrongBookList(List<ExamWrongBook> studentWrongBookList) {
        this.studentWrongBookList = studentWrongBookList;
    }

    public List<ExamQuestionAnswer> getStudentQuestionAnswerList() {
        return studentQuestionAnswerList;
    }

    public void setStudentQuestionAnswerList(List<ExamQuestionAnswer> studentQuestionAnswerList) {
        this.studentQuestionAnswerList = studentQuestionAnswerList;
    }

    public void setExamInfo(ExamInfo examInfo) {
        this.examInfo = examInfo;
    }

    public ExamInfo getExamInfo() {
        return examInfo;
    }
}
