DROP TABLE IF EXISTS `grade_info`;
CREATE TABLE `grade_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `school_type` tinyint(2) NOT NULL COMMENT '所属阶段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '年级信息表' ROW_FORMAT = Dynamic;


INSERT INTO `grade_info` VALUES (1, '一年级', '2020-11-29 14:50:11', NULL, 1);
INSERT INTO `grade_info` VALUES (2, '三年级', '2020-11-29 14:51:44', '2020-11-29 14:52:11', 1);