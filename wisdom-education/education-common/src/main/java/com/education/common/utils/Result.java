package com.education.common.utils;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @descript: 返回结果类
 * @Auther: zengjintao
 * @Date: 2019/12/31 13:46
 * @Version:2.1.0
 */
public final class Result<T> implements Serializable {

    private T data;
    private Integer code;
    private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    private static final String DEFAULT_FAIL_MESSAGE = "操作失败";
    private String message = DEFAULT_SUCCESS_MESSAGE;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return ResultCode.SUCCESS.equals(this.code);
    }

    public Result() {
        this.code = ResultCode.SUCCESS;
        this.message = DEFAULT_SUCCESS_MESSAGE;
    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static <T> Result success(Integer code, String message, T data) {
        return new Result(code, message, data);
    }

    public static <T> Result success(Integer code, T data) {
        return new Result(code, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static <T> Result success() {
        return new Result(ResultCode.SUCCESS, DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result success(Integer code, String message) {
        return new Result(code, message);
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result success(T data) {
        return new Result(ResultCode.SUCCESS, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static <T> Result success(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage());
    }

    public static Result fail(Integer code) {
        return new Result(code, DEFAULT_FAIL_MESSAGE);
    }

    public static Result fail(Integer code, String message) {
        return new Result(code, message);
    }

    public static Result fail() {
        return new Result(ResultCode.FAIL, DEFAULT_FAIL_MESSAGE);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        if (code == ResultCode.FAIL) {
            this.message = "数据请求失败";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    static final String contentType = "application/json; charset=utf-8";

    public static void renderJson(HttpServletResponse response, Result result) {
        String dataJson = JSONObject.toJSONString(result);
        PrintWriter writer;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(contentType);
            writer = response.getWriter();
            writer.write(dataJson);
            writer.flush();
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
