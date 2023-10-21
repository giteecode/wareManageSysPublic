package com.education.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("exam_info")
public class ExamInfo extends BaseEntity<ExamInfo> {

	@TableField("student_id")
	private Integer studentId;

	@TableField("correct_type")
	private Integer correctType;

	@TableField("subjective_question_number")
	private Integer subjectiveQuestionNumber;

	private Integer mark;

	@TableField("test_paper_info_id")
	private Integer testPaperInfoId;

	@TableField("correct_flag")
	private Integer correctFlag;

	@TableField("question_number")
	private Integer questionNumber;

	@TableField("exam_time")
	private String examTime;

	@TableField("system_mark")
	private Integer systemMark;

	@TableField("teacher_mark")
	private Integer teacherMark;

	@TableField("right_number")
	private Integer rightNumber;

	@TableField("error_number")
	private Integer errorNumber;

	@TableField("admin_id")
	private Integer adminId;

	@TableField("exam_time_long_value")
	private Long examTimeLongValue;

	/**
	 * 待批改试题数量
	 */
	@TableField("wait_correct_number")
	private Integer waitCorrectNumber;

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getSubjectiveQuestionNumber() {
		return subjectiveQuestionNumber;
	}

	public void setSubjectiveQuestionNumber(Integer subjectiveQuestionNumber) {
		this.subjectiveQuestionNumber = subjectiveQuestionNumber;
	}

	public Long getExamTimeLongValue() {
		return examTimeLongValue;
	}

	public void setExamTimeLongValue(Long examTimeLongValue) {
		this.examTimeLongValue = examTimeLongValue;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public Integer getRightNumber() {
		return rightNumber;
	}

	public void setRightNumber(Integer rightNumber) {
		this.rightNumber = rightNumber;
	}

	public Integer getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(Integer errorNumber) {
		this.errorNumber = errorNumber;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getCorrectType() {
		return correctType;
	}

	public void setCorrectType(Integer correctType) {
		this.correctType = correctType;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getTestPaperInfoId() {
		return testPaperInfoId;
	}

	public void setTestPaperInfoId(Integer testPaperInfoId) {
		this.testPaperInfoId = testPaperInfoId;
	}

	public Integer getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(Integer correctFlag) {
		this.correctFlag = correctFlag;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public Integer getSystemMark() {
		return systemMark;
	}

	public void setSystemMark(Integer systemMark) {
		this.systemMark = systemMark;
	}

	public Integer getTeacherMark() {
		return teacherMark;
	}

	public void setTeacherMark(Integer teacherMark) {
		this.teacherMark = teacherMark;
	}

	public Integer getWaitCorrectNumber() {
		return waitCorrectNumber;
	}

	public void setWaitCorrectNumber(Integer waitCorrectNumber) {
		this.waitCorrectNumber = waitCorrectNumber;
	}
}