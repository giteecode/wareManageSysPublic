package com.education.business.correct;

import cn.hutool.core.collection.CollUtil;
import com.education.business.service.education.ExamQuestionAnswerService;
import com.education.business.service.education.StudentWrongBookService;
import com.education.common.component.SpringBeanManager;
import com.education.common.enums.CorrectStatusEnum;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.entity.ExamWrongBook;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/17 20:44
 */
public abstract class QuestionCorrect {

    protected StudentQuestionRequest studentQuestionRequest;
    protected ExamInfo examInfo;
    private Integer studentId;
    protected int correctQuestionNumber; // 批改试题数量
    private int rightQuestionNumber; // 答对题数
    private int errorQuestionNumber; // 答错题数
    protected int subjectiveQuestionNumber; // 主观题数量
    protected int objectiveQuestionNumber; // 客观题数量
    private int questionNumber; // 试题总数

    private final List<ExamWrongBook> studentWrongBookList = new ArrayList<>(); // 存储学员考试错题

    protected final List<QuestionAnswer> questionAnswerList;

    // 存储批改的学员答题记录
    protected final List<ExamQuestionAnswer> studentQuestionAnswerList = new ArrayList<>();

    // 存储客观题答题记录
    private final List<ExamQuestionAnswer> objectiveQuestionAnswerList = new ArrayList<>();

    protected Map<Integer, String> questionAnswerInfo; // 存储试题答案信息 key 为试题id value 为试题答案

    public QuestionCorrect(StudentQuestionRequest studentQuestionRequest, ExamInfo examInfo, Map<Integer, String> questionAnswerInfo) {
        this.studentQuestionRequest = studentQuestionRequest;
        this.questionNumber = studentQuestionRequest.getQuestionAnswerList().size();
        this.studentId = studentQuestionRequest.getStudentId();
        this.examInfo = examInfo;
        this.questionAnswerList = studentQuestionRequest.getQuestionAnswerList();
        this.questionAnswerInfo = questionAnswerInfo;
    }


    public int getRightQuestionNumber() {
        return rightQuestionNumber;
    }

    public void setRightQuestionNumber(int rightQuestionNumber) {
        this.rightQuestionNumber = rightQuestionNumber;
    }

    public int getErrorQuestionNumber() {
        return errorQuestionNumber;
    }

    public void setErrorQuestionNumber(int errorQuestionNumber) {
        this.errorQuestionNumber = errorQuestionNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void addRightNumber() {
        this.rightQuestionNumber++;
    }

    public void addErrorNumber() {
        this.errorQuestionNumber++;
    }

    public int getCorrectQuestionNumber() {
        return correctQuestionNumber;
    }


    public List<ExamWrongBook> getStudentWrongBookList() {
        return studentWrongBookList;
    }

    protected void newStudentWrongBook(QuestionAnswer questionAnswer) {
        ExamWrongBook studentWrongBook = new ExamWrongBook(this.studentId,
                questionAnswer.getQuestionInfoId(),
                questionAnswer.getQuestionMark());
        studentWrongBook.setStudentAnswer(questionAnswer.getStudentAnswer());
        studentWrongBook.setStudentId(getStudentId());
        studentWrongBook.setCorrectStatus(CorrectStatusEnum.ERROR.getValue());
        this.addErrorNumber(); // 错题数加1
        this.studentWrongBookList.add(studentWrongBook);
    }

    protected ExamQuestionAnswer createStudentQuestionAnswer(QuestionAnswer questionAnswer) {
        ExamQuestionAnswer studentQuestionAnswer = new ExamQuestionAnswer();
        studentQuestionAnswer.setQuestionInfoId(questionAnswer.getQuestionInfoId());
        studentQuestionAnswer.setStudentId(this.studentId);
        studentQuestionAnswer.setComment(questionAnswer.getComment());
        studentQuestionAnswer.setQuestionPoints(questionAnswer.getQuestionMark());
        studentQuestionAnswer.setStudentAnswer(questionAnswer.getStudentAnswer());
        studentQuestionAnswer.setMark(questionAnswer.getQuestionMark());
        return studentQuestionAnswer;
    }

    public void saveStudentQuestionAnswer(Integer testPaperId, String testPaperName, Integer examInfoId, List<ExamQuestionAnswer> studentQuestionAnswerList, List<ExamWrongBook> studentWrongBookList) {
        if (CollUtil.isNotEmpty(studentQuestionAnswerList)) {
            studentQuestionAnswerList.forEach(item -> {
                item.setExamInfoId(examInfoId);
                item.setTestPaperId(testPaperId);
                item.setTestPaperName(testPaperName);
            });
            ExamQuestionAnswerService studentQuestionAnswerService = SpringBeanManager.getBean(ExamQuestionAnswerService.class);
            studentQuestionAnswerService.saveBatch(studentQuestionAnswerList);
        }
        if (CollUtil.isNotEmpty(studentWrongBookList)) {
            StudentWrongBookService studentWrongBookService = SpringBeanManager.getBean(StudentWrongBookService.class);
            studentWrongBookService.saveBatch(studentWrongBookList);
        }
    }

    public List<ExamQuestionAnswer> getStudentQuestionAnswerList() {
        return studentQuestionAnswerList;
    }

    public List<ExamQuestionAnswer> getObjectiveQuestionAnswerList() {
        return objectiveQuestionAnswerList;
    }

    public void addObjectiveQuestionAnswerList(ExamQuestionAnswer studentQuestionAnswer) {
        this.objectiveQuestionAnswerList.add(studentQuestionAnswer);
    }


    protected long getExamTime() {
        return studentQuestionRequest.getExamTime();
    }

    protected Integer getStudentId() {
        return studentQuestionRequest.getStudentId();
    }

    protected Integer getTestPaperInfoId() {
        return studentQuestionRequest.getTestPaperInfoId();
    }
    /**
     * 批改试题
     */
    public abstract ExamInfo correctStudentQuestion();

}
