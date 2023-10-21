package com.education.auth.token;


/**
 * token 创建接口
 * @author zengjintao
 * @create_at 2021年11月26日 0026 10:36
 * @since version 1.0.4
 */
public interface ITokenFactory {

    /**
     * 创建token 并设置有效期
     * @param value
     * @param expirationTime 单位： 毫秒
     * @return
     */
    String createToken(Object value, long expirationTime);

    /**
     * 解析token
     * @param token
     * @param <T>
     * @return
     */
    <T> T parseToken(String token);

    /**
     * 校验token 是否失效
     * @param token
     * @return
     */
    boolean isExpiration(String token);

    /**
     * 获取token 到期时间
     * @param token
     * @return
     */
    long getExpirationTime(String token);
}
