package com.education.model.request;

import com.education.model.entity.TestPaperInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 智能组卷Request 实体类
 * @author zjt
 * @create_at 2022年4月2日 0002 15:50
 * @since 1.6.11
 */
public class AutoCreatePaperRequest {

    /**
     * 试卷基本信息
     */
    @Valid
    private TestPaperInfo testPaperInfo;

    /**
     * 试题类型及类型对应的试题数量集合
     */
    private List<AutoCreatePaperItem> autoCreatePaperItemList;


    public List<AutoCreatePaperItem> getAutoCreatePaperItemList() {
        return autoCreatePaperItemList;
    }

    public void setAutoCreatePaperItemList(List<AutoCreatePaperItem> autoCreatePaperItemList) {
        this.autoCreatePaperItemList = autoCreatePaperItemList;
    }

    public TestPaperInfo getTestPaperInfo() {
        return testPaperInfo;
    }

    public void setTestPaperInfo(TestPaperInfo testPaperInfo) {
        this.testPaperInfo = testPaperInfo;
    }
}
