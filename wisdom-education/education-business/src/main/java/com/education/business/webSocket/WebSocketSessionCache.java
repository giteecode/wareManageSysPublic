package com.education.business.webSocket;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author zjt
 * @create_at 2022年12月9日 0009 09:43
 * @since 1.0.6
 */
public class WebSocketSessionCache {

    /**
     * webSocket session 会话id
     */
    private String socketSessionId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户hash token
     */
    private String hashToken;

    /**
     * webSocketSession
     */
    private WebSocketSession webSocketSession;

    /**
     * 用户类型
     * @see com.education.common.enums.PlatformTypeEnum
     */
    private Integer platformType;


    public String getSocketSessionId() {
        return socketSessionId;
    }

    public void setSocketSessionId(String socketSessionId) {
        this.socketSessionId = socketSessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getHashToken() {
        return hashToken;
    }

    public void setHashToken(String hashToken) {
        this.hashToken = hashToken;
    }
}
