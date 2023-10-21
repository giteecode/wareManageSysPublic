package com.education.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 试题知识点关联表
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 15:21
 */
public class QuestionLanguagePointsInfo extends BaseEntity<QuestionLanguagePointsInfo> {

    @TableField("question_info_id")
    private Integer questionInfoId;
    @TableField("language_points_info_Id")
    private Integer languagePointsInfoId;

    public Integer getQuestionInfoId() {
        return questionInfoId;
    }

    public void setQuestionInfoId(Integer questionInfoId) {
        this.questionInfoId = questionInfoId;
    }

    public Integer getLanguagePointsInfoId() {
        return languagePointsInfoId;
    }

    public void setLanguagePointsInfoId(Integer languagePointsInfoId) {
        this.languagePointsInfoId = languagePointsInfoId;
    }
}
