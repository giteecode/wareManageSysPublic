package com.education.model.response;

import com.education.model.entity.ExamInfo;

/**
 * 考试分析排名
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/5 16:29
 */
public class ExamRanking extends ExamInfo {

    private String testPaperInfoName;
    private String studentName;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTestPaperInfoName() {
        return testPaperInfoName;
    }

    public void setTestPaperInfoName(String testPaperInfoName) {
        this.testPaperInfoName = testPaperInfoName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
