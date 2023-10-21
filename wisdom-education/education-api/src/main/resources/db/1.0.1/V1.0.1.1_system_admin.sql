DROP TABLE IF EXISTS `system_admin`;
CREATE TABLE `system_admin`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
     `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
     `encrypt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码加密hash',
     `create_date` datetime(0) NULL DEFAULT NULL,
     `disabled_flag` tinyint(2) NOT NULL DEFAULT 0 COMMENT '1 是 0 否',
     `login_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录ip',
     `login_count` int(11) NOT NULL DEFAULT 0 COMMENT '登录次数',
     `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
     `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
     `create_type` tinyint(2) NOT NULL DEFAULT 2 COMMENT '创建类型 （1 系统默认 2. 管理员创建)',
     `mobile` varchar(255) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT NULL COMMENT '手机号',
     `super_flag` tinyint(2) NOT NULL DEFAULT 0 COMMENT '1 是 0 否',
     `update_date` datetime(0) NULL DEFAULT NULL,
     `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统管理员表' ROW_FORMAT = Dynamic;

INSERT INTO `system_admin` VALUES (1, 'admin', '94a0cf41db339d8248ee3da1c232d326', 'af2244dbc451f1007a7ba8408e45b16f2939bdc3', '2020-04-25 09:32:14', 0, '223.84.238.44', 2248, '王军', '2021-10-02 13:34:47', 1, '12123123123', 1, '2021-09-01 15:01:42', '未知地址');



