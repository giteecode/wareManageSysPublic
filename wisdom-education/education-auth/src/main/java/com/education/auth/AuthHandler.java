package com.education.auth;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @create_at 2021年11月27日 0027 13:53
 * @since version 1.0.4
 */
public class AuthHandler implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AuthRealmManager authRealmManager = applicationContext.getBean(AuthRealmManager.class);
        if (authRealmManager == null) {
            throw new RuntimeException("Can Not Find Bean " + AuthRealmManager.class);
        }
        AuthUtil.setAuthRealmManager(authRealmManager);
    }
}
