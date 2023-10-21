package com.education.common.enums;

/**
     * 登录状态
     */
    public enum LoginStatusEnum {
        LOGIN(1, "登录"), LOGIN_OUT(2, "退出");

        private Integer value;
        private String name;

        LoginStatusEnum(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }