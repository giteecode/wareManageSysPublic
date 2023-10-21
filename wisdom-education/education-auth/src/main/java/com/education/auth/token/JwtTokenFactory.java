package com.education.auth.token;

import com.education.common.model.JwtToken;

/**
 * jwt token 实现
 * @author zengjintao
 * @create_at 2021年11月26日 0026 10:32
 * @since version 1.0.4
 */
public class JwtTokenFactory extends TokenFactory {

    private final JwtToken jwtToken;

    public JwtTokenFactory(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String createToken(Object value, long expirationTime) {
        return jwtToken.createToken(value, expirationTime);
    }

    @Override
    public <T> T parseToken(String token) {
        return (T) jwtToken.parseTokenToString(token);
    }

    @Override
    public boolean isExpiration(String token) {
        Object value = parseToken(token);
        return value == null;
    }

    @Override
    public long getExpirationTime(String token) {
        return jwtToken.getTokenValidDate(token).getTime();
    }
}
