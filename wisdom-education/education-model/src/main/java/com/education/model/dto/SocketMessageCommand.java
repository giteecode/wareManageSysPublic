package com.education.model.dto;

import com.education.common.enums.PlatformEnum;

/**
 * webSocket 消息指令dto
 * @author zengjintao
 * @create_at 2022/1/2 17:02
 * @since version 1.0.4
 */
public class SocketMessageCommand {

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 通讯平台
     * @see PlatformEnum
     */
    private String platform;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * token 凭证
     */
    private String token;

    /**
     * 消息内容
     */
    private String msgContent;


    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "WebSocketMessageCommand{" +
                "messageType=" + messageType +
                ", platform='" + platform + '\'' +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", msgContent='" + msgContent + '\'' +
                '}';
    }
}
