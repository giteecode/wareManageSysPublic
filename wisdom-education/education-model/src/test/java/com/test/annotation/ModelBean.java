package com.test.annotation;

import com.jfinal.plugin.hikaricp.HikariCpPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体类基类
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/8 17:29
 * @since 1.0.0
 */
public abstract class ModelBean<T extends ModelBean> {

    private final Map<String, Object> attrs = new HashMap();

    public void setAttr(String key, Object value) {
        attrs.put(key, value);
    }

    public T putAll(Map params) {
        attrs.putAll(params);
        return (T) this;
    }
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/education_final?serverTimezone=GMT%2B8&useSSL=true&useUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void main(String[] args) {
        HikariCpPlugin druidPlugin = new HikariCpPlugin(URL, USER, PASS);
        druidPlugin.start();
        ModelGenerate modelKit = new ModelGenerate("com.education.model.entity",druidPlugin.getDataSource());
        modelKit.create();
    }
    public Map<String, Object> getAttrs() {
        return attrs;
    }
}
