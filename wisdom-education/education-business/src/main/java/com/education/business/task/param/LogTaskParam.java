package com.education.business.task.param;

import com.education.common.enums.PlatformTypeEnum;

/**
 * @author zjt
 * @create_at 2022年11月24日 0024 14:59
 * @since 2.0.1
 */
public class LogTaskParam extends TaskParam {

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * http 请求方式
     */
    private String method;

    /**
     * 接口执行时间
     */
    private String requestTime;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 请求系统平台
     */
    private PlatformTypeEnum platformTypeEnum;

    /**
     * 操作详情
     */
    private String operationDesc;

    /**
     * 操作人
     */
    private String operationName;

    /**
     * 请求头contentType
     */
    private String contentType;

    public LogTaskParam(String queueName) {
        super(queueName);
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public PlatformTypeEnum getPlatformTypeEnum() {
        return platformTypeEnum;
    }

    public void setPlatformTypeEnum(PlatformTypeEnum platformTypeEnum) {
        this.platformTypeEnum = platformTypeEnum;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
