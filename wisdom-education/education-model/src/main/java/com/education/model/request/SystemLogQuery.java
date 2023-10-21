package com.education.model.request;

import com.education.model.entity.SystemLog;

/**
 * @author zengjintao
 * @create_at 2022/1/1 13:53
 * @since version 1.0.4
 */
public class SystemLogQuery extends SystemLog {

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     * 查询结束时间
     */
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
