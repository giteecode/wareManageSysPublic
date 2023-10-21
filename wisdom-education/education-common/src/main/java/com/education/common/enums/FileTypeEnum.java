package com.education.common.enums;


import java.util.Arrays;

/**
 * @author zjt
 * @create_at 2021年12月30日 0030 13:45
 * @since version 1.6.8
 */
public enum FileTypeEnum {

    VIDEO(1, "视频", "/videos/"),
    IMAGE(2, "图片", "/images/"),
    EXCEL(3, "EXCEL", "/excels/"),
    WORD(4, "Word文档", "/words/"),
    PDF(5, "PDF文档", "/pdf/"),
    ;

    private Integer code;
    private String prefix;
    private String value;

    FileTypeEnum(Integer code, String value, String prefix) {
        this.code = code;
        this.value = value;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }

    public static FileTypeEnum getFileTypeEnumByCode(Integer code) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(code)).findAny()
                .orElse(null);
    }
}
