package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("test_paper_question_info")
public class TestPaperQuestionInfo extends BaseEntity<TestPaperQuestionInfo> {


	@TableField("question_info_id")
	private Integer questionInfoId;
	@TableField("test_paper_info_id")
	private Integer testPaperInfoId;
	private Integer mark;
	private Integer sort;


	public Integer getQuestionInfoId() {
		return questionInfoId;
	}

	public void setQuestionInfoId(Integer questionInfoId) {
		this.questionInfoId = questionInfoId;
	}

	public Integer getTestPaperInfoId() {
		return testPaperInfoId;
	}

	public void setTestPaperInfoId(Integer testPaperInfoId) {
		this.testPaperInfoId = testPaperInfoId;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}