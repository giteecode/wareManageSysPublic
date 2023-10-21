package com.education.common.enums;

public enum CourseStatusEnum {
        DRAUGHT(0, "草稿"), GROUNDING(1, "上架"), UNDERCARRIAGE(2, "下架");

        private Integer value;
        private String name;

        CourseStatusEnum(Integer value, String name) {
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