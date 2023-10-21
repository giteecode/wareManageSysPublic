package com.education.auth.token;

/**
 * token 缓存信息
 * @author zengjintao
 * @create_at 2021年11月30日 0030 13:48
 * @since version 1.0.4
 */
public final class TokenInfo {

    private String token;
    private Object value;
    private long expirationTime;

    public TokenInfo(String token, Object value, long expirationTime) {
        this.token = token;
        this.value = value;
        this.expirationTime = expirationTime;
    }

    public Object getValue() {
        return value;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
