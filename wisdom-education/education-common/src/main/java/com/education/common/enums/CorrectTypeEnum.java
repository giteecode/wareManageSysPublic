package com.education.common.enums;

public enum CorrectTypeEnum {
        SYSTEM(1, "系统判分"), TEACHER(2, "教师判分"), SYSTEM_AND_TEACHER(3, "系统加教师判分");
        private Integer value;
        private String name;

        CorrectTypeEnum(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }