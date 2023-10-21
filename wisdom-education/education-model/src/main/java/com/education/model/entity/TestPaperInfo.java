package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 试卷信息表
 */
@TableName("test_paper_info")
public class TestPaperInfo extends BaseEntity<TestPaperInfo> {

	@NotBlank
	private String name;
	private String remark;
	private Integer mark;

	@TableField("school_type")
	@NotNull
	private Integer schoolType;

	@TableField("grade_info_id")
	@NotNull
	private Integer gradeInfoId;

	@TableField("publish_flag")
	private Boolean publishFlag;

	@TableField("subject_id")
	@NotNull
	private Integer subjectId;

	private Integer sort;

	@TableField("exam_number")
	private Integer examNumber;

	@TableField("correct_number")
	private Integer correctNumber;

	@TableField("exam_time")
	private Integer examTime;

	@TableField("question_number")
	private Integer questionNumber;
	@TableField("pass_mark")
	private Integer passMark;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField("publish_time")
	private Date publishTime;
	@TableField("html_download_url")
	private String htmlDownloadUrl;

	public String getHtmlDownloadUrl() {
		return htmlDownloadUrl;
	}

	public void setHtmlDownloadUrl(String htmlDownloadUrl) {
		this.htmlDownloadUrl = htmlDownloadUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}

	public Boolean getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(Boolean publishFlag) {
		this.publishFlag = publishFlag;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getExamNumber() {
		if (examNumber == null) {
			return 0;
		}
		return examNumber;
	}

	public void setExamNumber(Integer examNumber) {
		this.examNumber = examNumber;
	}

	public Integer getCorrectNumber() {
		return correctNumber;
	}

	public void setCorrectNumber(Integer correctNumber) {
		this.correctNumber = correctNumber;
	}

	public Integer getExamTime() {
		return examTime;
	}

	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getPassMark() {
		return passMark;
	}

	public void setPassMark(Integer passMark) {
		this.passMark = passMark;
	}
}