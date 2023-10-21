package com.education.model.response;


import com.education.model.dto.TestPaperInfoDto;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/1 15:26
 */
public class TestPaperInfoReport extends TestPaperInfoDto {

    private float avgSource; // 试卷平均分

    public float getAvgSource() {
        return avgSource;
    }

    public void setAvgSource(float avgSource) {
        this.avgSource = avgSource;
    }
}
