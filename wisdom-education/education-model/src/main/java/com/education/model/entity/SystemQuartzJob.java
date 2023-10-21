package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 定时任务信息表
 */
@TableName("system_quartz_job")
public class SystemQuartzJob extends BaseEntity<SystemQuartzJob> {

    @TableField("job_class_name")
    private String jobClassName;

    private Integer status;

    @TableField("cron_expression")
    private String cronExpression;

    @TableField("job_des")
    private String jobDes;


    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobDes() {
        return jobDes;
    }

    public void setJobDes(String jobDes) {
        this.jobDes = jobDes;
    }
}
