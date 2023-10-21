package com.education.model.dto;

import com.education.common.enums.QuestionTypeEnum;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.TestPaperQuestionInfo;
import com.jfinal.json.JacksonFactory;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 14:05
 */
public class TestPaperQuestionDto extends TestPaperQuestionInfo {

    private Integer questionType;
    private String content;
    private String options;
    private String answer;
    private Integer updateType; // 更新字段
    private String questionTypeName;

    // 选项数组
    private List<Map> optionsList;

    public String getQuestionTypeName() {
        if (questionType != null) {
            return QuestionTypeEnum.getName(this.questionType);
        }
        return questionTypeName;
    }

    public void setOptionsList(List<Map> optionsList) {
        this.optionsList = optionsList;
    }

    public List<Map> getOptionsList() {
        return optionsList;
    }

    public void setOptions(String options) {
        this.options = options;
        if (ObjectUtils.isNotEmpty(options)) {
            setOptionsList(JacksonFactory.me().getJson().parse(options, List.class));
        }
    }

    public String getOptions() {
        return options;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionType() {
        return questionType;
    }
}
