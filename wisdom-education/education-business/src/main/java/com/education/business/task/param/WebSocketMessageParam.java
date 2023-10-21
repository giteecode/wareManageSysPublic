package com.education.business.task.param;

import com.education.common.enums.SocketMessageTypeEnum;

/**
 * @author zjt
 * @create_at 2022年11月24日 0024 14:50
 * @since 2.0.1
 */
public class WebSocketMessageParam extends TaskParam {

    /**
     * 学员id
     */
    private Integer studentId;

    /**
     * socket session会话id
     */
    private String socketSessionId;

    /**
     * 用户hashToken
     */
    private String hashToken;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 试卷id
     */
    private Integer testPaperId;

    /**
     * socket 消息类型枚举
     */
    private SocketMessageTypeEnum socketMessageTypeEnum;

    public WebSocketMessageParam(String queueName) {
        super(queueName);
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getSocketSessionId() {
        return socketSessionId;
    }

    public void setSocketSessionId(String socketSessionId) {
        this.socketSessionId = socketSessionId;
    }

    public String getHashToken() {
        return hashToken;
    }

    public void setHashToken(String hashToken) {
        this.hashToken = hashToken;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(Integer testPaperId) {
        this.testPaperId = testPaperId;
    }

    public SocketMessageTypeEnum getSocketMessageTypeEnum() {
        return socketMessageTypeEnum;
    }

    public void setSocketMessageTypeEnum(SocketMessageTypeEnum socketMessageTypeEnum) {
        this.socketMessageTypeEnum = socketMessageTypeEnum;
    }
}
