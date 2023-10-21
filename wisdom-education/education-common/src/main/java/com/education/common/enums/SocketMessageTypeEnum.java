package com.education.common.enums;

import java.util.Arrays;

/**
 * socket 消息类型枚举
 * @author zengjintao
 * @create_at 2022/1/2 18:40
 * @since version 1.0.4
 */
public enum SocketMessageTypeEnum {

    HEART(10001, "心跳包"),
    ADMIN_CONNECTION_SUCCESS(10010, "管理后台socket连接成功"),
    STUDENT_CONNECTION_SUCCESS(10011, "WEB学生端socket连接成功"),

    H5_CONNECTION_SUCCESS(10014, "WEB学生端socket连接成功"),

    REJECT_SESSION(10012, "账号下线通知"),
    EXAM_CORRECT(10013, "试卷批改通知");

    private Integer code;
    private String name;

    SocketMessageTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    public static boolean contains(Integer code) {
        SocketMessageTypeEnum socketMessageTypeEnum = Arrays.stream(values())
                .filter(item -> item.code.equals(code))
                .findAny().orElse(null);
        return socketMessageTypeEnum != null;
    }

    public static boolean isConnectionSuccess(Integer code) {
        return ADMIN_CONNECTION_SUCCESS.code.equals(code)
                || STUDENT_CONNECTION_SUCCESS.code.equals(code);
    }
}
