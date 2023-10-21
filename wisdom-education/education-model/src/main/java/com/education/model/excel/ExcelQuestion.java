package com.education.model.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotBlank;

/**
 * excel试题实体类
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/30 16:40
 */
public class ExcelQuestion {

    private Integer questionType;

    @Excel(name = "答案")
    @NotBlank(message = "请输入试题答案")
    private String answer;

    @Excel(name = "试题内容")
    @NotBlank(message = "请输入试题内容")
    private String content;

    @Excel(name = "试题类型")
    @NotBlank(message = "请输入试题类型")
    @TableField(exist = false)
    private String questionTypeName;

    @Excel(name = "选项内容")
    private String options;

    @Excel(name = "试题解析")
    private String analysis;

    @Excel(name = "总结升华")
    private String summarize;

    public String getAnswer() {
        return answer;
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

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
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

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
}
