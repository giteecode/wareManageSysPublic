package com.education.model.dto;

import com.education.model.entity.LanguagePointsInfo;
import com.education.model.entity.QuestionInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 11:06
 */
public class QuestionInfoDto extends QuestionInfo {

    /**
     * 年纪名称
     */
    private String gradeInfoName;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 试题类型
     */
    private String questionTypeName;


    /**
     * 试题类型对应的试题数量
     */
    private Integer numberGroupByType;


    /**
     * 知识点列表
     */
    private List<LanguagePointsInfo> languagePointsInfoList;

    /**
     * 试题总作答次数
     */
    private Integer totalAnswerNum;

    /**
     * 答对次数
     */
    private Integer rightAnswerNum;

    /**
     * 答对次数
     */
    private Integer errorAnswerNum;

    /**
     * 未答次数
     */
    private Integer noAnswerNum;

    /**
     * 正确率
     */
    private String rightRate;

    /**
     * 错误率
     */
    private String errorRate;

    public List<LanguagePointsInfo> getLanguagePointsInfoList() {
        return languagePointsInfoList;
    }

    public void setLanguagePointsInfoList(List<LanguagePointsInfo> languagePointsInfoList) {
        this.languagePointsInfoList = languagePointsInfoList;
    }

    /**
     * 知识点列表
     */
    private List<Integer> languagePointsInfoId;

    public void setLanguagePointsInfoId(List<Integer> languagePointsInfoId) {
        this.languagePointsInfoId = languagePointsInfoId;
    }

    public List<Integer> getLanguagePointsInfoId() {
        return languagePointsInfoId;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public String getQuestionTypeName() {
        return this.questionTypeName;
    }

    public void setGradeInfoName(String gradeInfoName) {
        this.gradeInfoName = gradeInfoName;
    }

    public String getGradeInfoName() {
        return gradeInfoName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getNumberGroupByType() {
        return numberGroupByType;
    }

    public void setNumberGroupByType(Integer numberGroupByType) {
        this.numberGroupByType = numberGroupByType;
    }

    public Integer getTotalAnswerNum() {
        return totalAnswerNum;
    }

    public void setTotalAnswerNum(Integer totalAnswerNum) {
        this.totalAnswerNum = totalAnswerNum;
    }

    public Integer getRightAnswerNum() {
        return rightAnswerNum;
    }

    public void setRightAnswerNum(Integer rightAnswerNum) {
        this.rightAnswerNum = rightAnswerNum;
    }

    public Integer getErrorAnswerNum() {
        return errorAnswerNum;
    }

    public void setErrorAnswerNum(Integer errorAnswerNum) {
        this.errorAnswerNum = errorAnswerNum;
    }

    public Integer getNoAnswerNum() {
        return noAnswerNum;
    }

    public void setNoAnswerNum(Integer noAnswerNum) {
        this.noAnswerNum = noAnswerNum;
    }


    public String getRightRate() {
        return rightRate;
    }

    public void setRightRate(String rightRate) {
        this.rightRate = rightRate;
    }

    public String getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(String errorRate) {
        this.errorRate = errorRate;
    }
}
