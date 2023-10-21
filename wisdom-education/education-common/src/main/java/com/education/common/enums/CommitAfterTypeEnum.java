package com.education.common.enums;

public enum CommitAfterTypeEnum {
        SHOW_MARK_AFTER_CORRECT(1, "批改后显示成绩"),
        SHOW_MARK_NOW(2, "提交立即显示成绩");

        private Integer value;
        private String name;

        CommitAfterTypeEnum(Integer value, String name) {
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