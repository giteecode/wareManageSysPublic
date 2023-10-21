package com.education.business.interceptor;

import com.education.auth.AuthUtil;
import com.education.auth.session.UserSession;
import com.education.auth.token.TokenFactory;
import com.education.common.constants.AuthConstants;
import com.education.common.constants.CacheTime;
import com.education.common.constants.SystemConstants;
import com.education.common.enums.PlatformEnum;
import com.education.common.exception.BusinessException;
import com.education.common.utils.IpUtils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.RequestUtils;
import com.education.common.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/12/22 19:43
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

    private static final String PLATFORM = AuthConstants.PLATFORM;
    private static final String AUTHORIZATION = AuthConstants.AUTHORIZATION;

    private final Set<String> platformSet = new HashSet(){
        {
            add(PlatformEnum.SYSTEM_ADMIN.getHeaderValue());
            add(PlatformEnum.SYSTEM_STUDENT.getHeaderValue());
        }
    };


    @Value("${spring.profiles.active}")
    private String env;

    protected void checkHeader(HttpServletRequest request) {
        String ip = IpUtils.getAddressIp(request);
        if (!SystemConstants.ENV_TEST.equals(env) || !SystemConstants.ENV_PROD.equals(env)) {
            return;
        }
        String platform = request.getHeader(PLATFORM);
        if (ObjectUtils.isEmpty(platform)) {
            logger.warn("客户端ip:{}请求头未携带:{}", ip, PLATFORM);
            throw new BusinessException(new ResultCode(ResultCode.UN_AUTH_HEADER, "请求头未携带:" + PLATFORM));
        }

        if (!platformSet.contains(platform)) {
            logger.warn("客户端ip:{} {}:错误请求头:{}", ip, PLATFORM, platform);
            throw new BusinessException(new ResultCode(ResultCode.UN_AUTH_HEADER, PLATFORM + "请求头错误!"));
        }
    }

    /**
     * 校验token 是否合法
     * @param response
     * @return
     */
    protected boolean checkToken(String loginType, HttpServletResponse response) {
        //获取token
        UserSession userSession = AuthUtil.getSession(loginType);
        if (userSession == null) {
            throw new BusinessException(new ResultCode(ResultCode.UN_AUTH_ERROR_CODE, "会话已过期,请重新登录"));
        }
        this.refreshSessionIfNeed(userSession.getToken(), userSession, response);
        return true;
    }

    /**
     * 刷新token
     */
    private void refreshSessionIfNeed(String token, UserSession userSession, HttpServletResponse response) {
        TokenFactory tokenFactory = AuthUtil.getTokenFactory();
        long validTime = tokenFactory.getExpirationTime(token);
        long now = new Date().getTime();
        // 失效时间小于2分钟，重新创建token
        if (validTime > now && validTime - now < CacheTime.TWO_SECOND_MILLIS) {
            String newToken = tokenFactory.createToken(userSession.getId(), CacheTime.TEN_MINUTE_MILLIS);
            response.addHeader(AUTHORIZATION, newToken);
            userSession.setToken(newToken);
            AuthUtil.createNewSession(userSession, token, CacheTime.TEN_MINUTE_SECOND);
        }
    }


    /**
     * 获取json 参数值
     * @param request
     * @return
     */
    protected String readData(HttpServletRequest request) {
       return RequestUtils.readData(request);
    }
}
