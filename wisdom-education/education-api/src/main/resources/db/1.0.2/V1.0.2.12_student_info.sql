DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `age` int(11) NOT NULL,
  `sex` tinyint(2) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mother_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '母亲姓名',
  `father_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `grade_info_id` int(11) NULL DEFAULT NULL,
  `delete_flag` int(1) NOT NULL DEFAULT 0 COMMENT '删除标记',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `encrypt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `disabled_flag` tinyint(1) NOT NULL DEFAULT 0,
  `login_count` int(11) NOT NULL DEFAULT 0,
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学员信息表' ROW_FORMAT = Dynamic;


INSERT INTO `student_info` VALUES (89, 'student', '演示账号', 1, 1, 'sdsd', '18296640717', 'dsd', 'dsd', NULL, '2021-07-11 17:08:56', '/images/2020/12/27d1c635bb182f428e8ba159b4d13212e9.jpg', 1, 0, '94a0cf41db339d8248ee3da1c232d326', 'af2244dbc451f1007a7ba8408e45b16f2939bdc3', 0, 438, '2021-07-11 17:08:56', '127.0.0.1', NULL);