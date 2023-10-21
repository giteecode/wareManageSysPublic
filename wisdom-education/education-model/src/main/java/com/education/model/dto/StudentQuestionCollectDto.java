package com.education.model.dto;

import com.education.model.entity.StudentQuestionCollect;

/**
 * @author zjt
 * @create_at 2022年1月17日 0017 14:07
 * @since 1.0.5
 */
public class StudentQuestionCollectDto extends StudentQuestionCollect {

    /**
     * 是否收藏
     */
    private Integer collectFlag;

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }
}
