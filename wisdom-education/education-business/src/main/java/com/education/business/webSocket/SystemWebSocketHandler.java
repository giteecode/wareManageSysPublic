package com.education.business.webSocket;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.education.common.enums.PlatformEnum;
import com.education.common.enums.PlatformTypeEnum;
import com.education.common.enums.SocketMessageTypeEnum;
import com.education.common.model.JwtToken;
import com.education.common.utils.Ip2regionUtil;
import com.education.model.dto.SocketMessageCommand;
import com.jfinal.kit.HashKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * webSocket 通讯
 * @author taoge
 * @version 1.0.2
 * @update  1.0.4
 * @update  1.0.6
 * @create_at 2018年11月18日下午5:24:06
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
    @Resource
    private JwtToken jwtToken;

    /**
     * 存储webSocket会话信息
     */
    private static final List<WebSocketSessionCache> webSocketSessionCacheList = new ArrayList<>();

    /**
     * 连接 就绪时
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

    }

    /**
     * 处理消息
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String message = String.valueOf(webSocketMessage.getPayload());
        Map<String, Object> attrs = webSocketSession.getAttributes();
        String ip = String.valueOf(attrs.get(HandshakeInterceptor.CLIENT_IP_KEY));
        logger.info("Socket Listener Client Ip:{} Ip归属地:{} Message:{}",
                attrs.get(HandshakeInterceptor.CLIENT_IP_KEY),
                Ip2regionUtil.getIpRegion(ip),
                message);
        SocketMessageCommand socketMessageCommand;
        try {
            socketMessageCommand = JSONUtil.toBean(message, SocketMessageCommand.class);
        } catch (Exception e) {
            logger.warn("{}错误消息格式", message);
            return;
        }
        Integer messageType = socketMessageCommand.getMessageType();
        String socketSessionId = webSocketSession.getId();
        if (!SocketMessageTypeEnum.contains(messageType)) {
            logger.error("错误消息类型:{}", socketMessageCommand.getMessageType());
            return;
        }

        if (SocketMessageTypeEnum.HEART.getCode().equals(messageType)) {
            logger.info("ws心跳包:{}", message);
            this.sendMessage(webSocketSession, message);
            return;
        }


        // 连接成功时消息推送
        if (SocketMessageTypeEnum.isConnectionSuccess(messageType)) {
            String token = socketMessageCommand.getToken();
            if (StrUtil.isBlank(token)) {
                logger.error("{}：websocket通讯未传递token参数", socketSessionId);
                return;
            }

            String userId = jwtToken.parseTokenToString(token);
            // 非法token 移除会话session
            if (StrUtil.isBlank(userId)) {
                logger.error("websocket socket通讯非法token：{}参数", token);
                this.removeBySocketSessionId(socketSessionId);
                return;
            }

            WebSocketSessionCache webSocketSessionCache = getBySocketSessionId(socketSessionId);
            if (webSocketSessionCache != null) {
                logger.error("websocket session:{}已存在", socketSessionId);
                return;
            }

            // socket 连接成功时接收消息
            webSocketSessionCache = new WebSocketSessionCache();
            webSocketSessionCache.setWebSocketSession(webSocketSession);
            webSocketSessionCache.setSocketSessionId(socketSessionId);
            webSocketSessionCache.setUserId(Integer.valueOf(userId));
            webSocketSessionCache.setHashToken(HashKit.md5(token));
            if (SocketMessageTypeEnum.STUDENT_CONNECTION_SUCCESS.getCode().equals(messageType)) {
                webSocketSessionCache.setPlatformType(PlatformEnum.SYSTEM_STUDENT.getCode());
            } else if (SocketMessageTypeEnum.ADMIN_CONNECTION_SUCCESS.getCode().equals(messageType)) {
                webSocketSessionCache.setPlatformType(PlatformEnum.SYSTEM_ADMIN.getCode());
            } else if (SocketMessageTypeEnum.H5_CONNECTION_SUCCESS.getCode().equals(messageType)) {
                webSocketSessionCache.setPlatformType(PlatformEnum.MOBILE_H5.getCode());
            }
            webSocketSessionCacheList.add(webSocketSessionCache);
            logger.info("-------------------------- WebSocket Connection Success ---------------------------");
        }
    }


    public void sendMessage(WebSocketSession webSocketSession, String message) {
        try {
            webSocketSession.sendMessage(new TextMessage(message));
            logger.info("webSocket Server Push SocketSessionId:{} Message:{} Success", webSocketSession.getId(), message);
        } catch (IOException e) {
            logger.error("webSocket 消息发送异常", e);
        }
    }

    /**
     * 处理传输时异常
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession != null) {
            webSocketSession.close();
        }
        logger.warn("-------------------------- WebSocket Connection Error ---------------------------");
    }

    /**
     * 关闭 连接时
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        String sessionId = webSocketSession.getId();
        removeBySocketSessionId(sessionId);
        webSocketSession.close();
        logger.info("-------------------------- WebSocket Close Success ---------------------------");
    }


    /**
     * 移除websocket会话信息
     * @param socketSessionId
     * @since 1.0.6
     */
    private void removeBySocketSessionId(String socketSessionId) {
        WebSocketSessionCache value = getBySocketSessionId(socketSessionId);
        if (value != null) {
            webSocketSessionCacheList.remove(value);
        }
    }

    public WebSocketSessionCache getBySocketSessionId(String socketSessionId) {
        return webSocketSessionCacheList.stream()
                .filter(item -> item.getSocketSessionId().equals(socketSessionId))
                .findAny()
                .orElse(null);
    }


    public WebSocketSessionCache getByUserIdAndPlatformType(Integer userId, Integer platformType) {
        return webSocketSessionCacheList.stream()
                .filter(item -> item.getUserId().equals(userId) && item.getPlatformType().equals(platformType))
                .findAny()
                .orElse(null);
    }


    public WebSocketSessionCache getByHashToken(String hashToken) {
        return webSocketSessionCacheList.stream()
                .filter(item -> item.getHashToken().equals(hashToken))
                .findAny()
                .orElse(null);
    }


    /**
     * 根据用户id 和平台发送websocket消息
     * @param userId
     * @param platformType
     * @since 1.0.6
     * @return
     */
    public void sendMessageByUserId(Integer userId, Integer platformType, String message) {
        WebSocketSessionCache webSocketSessionCache = getByUserIdAndPlatformType(userId, platformType);
        if (webSocketSessionCache == null) {
            logger.warn("{}平台用户id:{}socket session会话信息不存在", userId, PlatformTypeEnum.getByCode(platformType));
        }
        this.sendMessage(webSocketSessionCache.getWebSocketSession(), message);
    }


    /**
     * 根据websocket session会话id发送消息
     * @param hashToken
     * @param message
     * @since 1.0.6
     */
    public void sendMessageByHashToken(String hashToken, String message) {
        WebSocketSessionCache webSocketSessionCache = getByHashToken(hashToken);
        if (webSocketSessionCache == null) {
            logger.warn("hashToken：{} session会话信息不存在", hashToken);
        }
        this.sendMessage(webSocketSessionCache.getWebSocketSession(), message);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
