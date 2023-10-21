package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author zjt
 * @create_at 2022年7月22日 0022 10:24
 * @since 1.0.5
 */
@TableName("calendar")
public class Calendar {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 日期
     */
    private String day;

    /**
     * 日期时间戳
     */
    @TableField("day_time")
    private Long dayTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getDayTime() {
        return dayTime;
    }

    public void setDayTime(Long dayTime) {
        this.dayTime = dayTime;
    }
}
