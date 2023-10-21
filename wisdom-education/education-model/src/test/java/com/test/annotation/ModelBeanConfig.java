package com.test.annotation;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @descript:
 * @Auther: zengjintao
 * @Date: 2020/1/10 09:48
 * @Version:2.1.0
 */
public class ModelBeanConfig {

    private JdbcTemplate jdbcTemplate;

    public ModelBeanConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
