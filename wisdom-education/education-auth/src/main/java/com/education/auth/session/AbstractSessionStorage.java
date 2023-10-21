package com.education.auth.session;


/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 09:48
 * @since version 1.0.4
 */
public abstract class AbstractSessionStorage implements SessionStorage {

    private final String sessionIdPrefix;

    public AbstractSessionStorage() {
        this.sessionIdPrefix = "";
    }


    public AbstractSessionStorage(String sessionIdPrefix) {
        this.sessionIdPrefix = sessionIdPrefix;
    }

    public String getSessionIdPrefix() {
        return sessionIdPrefix;
    }
}
