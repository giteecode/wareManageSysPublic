package com.education.common.enums;

/**
 * @author zengjintao
 * @create_at 2021/11/21 12:25
 * @since version 1.0.3
 */
public enum OssPlatformEnum {

    LOCAL("local", "本地文件上传"),
    TENCENT("tencent", "腾讯云oss");

    private String value;
    private String name;

    OssPlatformEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


}
