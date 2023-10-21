package com.education.common.model;

/**
 * @author zjt
 * @create_at 2021年12月24日 0024 13:55
 * @since version 1.0.4
 */
public class VideoInfo {

    /**
     * 播放时长
     */
    private long duration;

    /**
     * 视频宽度
     */
    private int width;

    /**
     * 视频高度
     */
    private int height;

    /**
     * 播放时长 时分秒
     */
    private String durationStr;


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }
}
