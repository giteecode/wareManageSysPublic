package com.education.common.cache;

import com.education.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/18 11:17
 */
public class CaffeineCacheElement implements Serializable {

    private Object value;
    private Integer liveSeconds; // 缓存有效期
    private Date createTime; // 缓存创建时间

    public CaffeineCacheElement(Object value) {
        this.value = value;
    }

    public CaffeineCacheElement(Object value, Integer liveSeconds) {
        this(value);
        this.liveSeconds = liveSeconds;
    }

    /**
     * 缓存是否超时
     * @return
     */
    public boolean isTimeOut() {
        if (this.liveSeconds == null)
            return false;
        long time = DateUtils.dateDiff(createTime, new Date());
        return (time - liveSeconds * 1000) > 0;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Integer getLiveSeconds() {
        return liveSeconds;
    }

    public void setLiveSeconds(Integer liveSeconds) {
        this.liveSeconds = liveSeconds;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
