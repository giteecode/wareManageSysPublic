package com.education.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;

@TableName("question_info")
public class QuestionInfo extends BaseEntity<QuestionInfo> implements IExcelDataModel {

	@TableField("subject_id")
	private Integer subjectId;
	@TableField("video_url")
	private String videoUrl;

	@Excel(name = "答案")
	@NotNull(message = "请输入试题答案")
	private String answer;

	@Excel(name = "试题内容")
	@NotNull(message = "请输入试题内容")
	private String content;

	@TableField("school_type")
	private Integer schoolType;

	@TableField("question_type")
	private Integer questionType;

	@Excel(name = "试题类型")
	@NotNull(message = "请输入试题类型")
	@TableField(exist = false)
	private String questionTypeName;

	@TableField("grade_info_id")
	private Integer gradeInfoId;

	@Excel(name = "选项内容")
	private String options;

	@Excel(name = "试题解析")
	private String analysis;

	@Excel(name = "总结升华")
	private String summarize;


	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getAnswer() {
		return answer;
	}

	public String getQuestionTypeName() {
		return questionTypeName;
	}

	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getSummarize() {
		return summarize;
	}

	public void setSummarize(String summarize) {
		this.summarize = summarize;
	}

	public boolean excelDataIsAllNull() {
		if (StrUtil.isBlank(getContent()) && StrUtil.isBlank(getAnalysis()) && StrUtil.isBlank(getAnswer())
		   && StrUtil.isBlank(getSummarize()) && StrUtil.isBlank(getQuestionTypeName())) {
			return true;
		}
		return false;
	}
}