package com.education.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.annotation.Unique;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@TableName("course_info")
public class CourseInfo extends BaseEntity<CourseInfo> {

	/**
	 * 课程名称
	 */
	@Unique
	@NotBlank(message = "请输入课程名称")
	@Length(min = 1, max = 20)
	private String name;

	/**
	 * 所属年级
	 */
	@Unique("grade_info_id")
	@TableField("grade_info_id")
	@NotNull(message = "请选择年级")
	private Integer gradeInfoId;

	/**
	 * 所属阶段
	 * @see com.education.common.enums.SchoolTypeEnum
	 */
	@TableField("school_type")
	@NotNull(message = "请选择课程阶段")
	private Integer schoolType;

	/**
	 * 科目id
	 */
	@TableField("subject_id")
	@Unique("subject_id")
	@NotNull(message = "请选择所属科目")
	private Integer subjectId;

	/**
	 * 课程简介
	 */
	@Length(max = 100)
	private String represent;

	/**
	 * 课程编码
	 */
	private String code;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 课程状态
	 * @see com.education.common.enums.CourseStatusEnum
	 */
	private Integer status;

	/**
	 * 课程学习人数
	 */
	@TableField("study_number")
	private Integer studyNumber;


	/**
	 *
	 * 是否推荐首页
	 * @see com.education.common.enums.BooleanEnum
	 */
	@TableField("recommend_index_flag")
	private Integer recommendIndexFlag;

	/**
	 *
	 * 封面图
	 */
	@TableField("head_img")
	@NotBlank(message = "请上传课程封面")
	private String headImg;

	/**
	 * 章节点数量
	 */
	@TableField("section_node_number")
	private Integer sectionNodeNumber;

	/**
	 * 章数量
	 */
	@TableField("section_number")
	private Integer sectionNumber;

	/**
	 * 发布时间
	 */
	@TableField("push_time")
	@JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
	private Date pushTime;

	/**
	 * 评价分数
	 */
	@TableField("valuate_Mark")
	private BigDecimal valuateMark;

	/**
	 * 课程评论数
	 */
	@TableField("comment_number")
	private Integer commentNumber;

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Integer getCommentNumber() {
		return commentNumber;
	}

	public void setValuateMark(BigDecimal valuateMark) {
		this.valuateMark = valuateMark;
	}

	public BigDecimal getValuateMark() {
		return valuateMark;
	}

	public Integer getSectionNodeNumber() {
		return sectionNodeNumber;
	}

	public void setSectionNodeNumber(Integer sectionNodeNumber) {
		this.sectionNodeNumber = sectionNodeNumber;
	}

	public Integer getSectionNumber() {
		return sectionNumber;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public void setSectionNumber(Integer sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public String getRepresent() {
		return represent;
	}

	public void setRepresent(String represent) {
		this.represent = represent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStudyNumber() {
		return studyNumber;
	}

	public void setStudyNumber(Integer studyNumber) {
		this.studyNumber = studyNumber;
	}

	public Integer getRecommendIndexFlag() {
		return recommendIndexFlag;
	}

	public void setRecommendIndexFlag(Integer recommendIndexFlag) {
		this.recommendIndexFlag = recommendIndexFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGradeInfoId() {
		return gradeInfoId;
	}

	public void setGradeInfoId(Integer gradeInfoId) {
		this.gradeInfoId = gradeInfoId;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}