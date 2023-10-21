package com.education.common.enums;

/**
     * 学校类型
     */
    public enum SchoolTypeEnum {
        PRIMARY_SCHOOL(1, "小学"), MIDDLE_SCHOOL(2, "初中"), HIGH_SCHOOL(3, "高中");
        private Integer value;
        private String name;

        SchoolTypeEnum(Integer value, String name) {
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