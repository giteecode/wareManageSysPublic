package com.education.business.webSocket;

import com.education.common.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wangxue
 * @version 1.0
 * @create 2018-08-14 22:24
 **/
@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);

    public static final String CLIENT_IP_KEY = "socketClientIp";

    /**
     * 握手前
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("beforeHandshake");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest)serverHttpRequest;
            HttpServletRequest request = servletServerHttpRequest.getServletRequest();
            String ip = IpUtils.getAddressIp(request);
            attributes.put(CLIENT_IP_KEY, ip);
        }
        return true;
    }

    /**
     * 握手后
     * @param request
     * @param response
     * @param wsHandler
     * @param ex
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        logger.info("afterHandshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
