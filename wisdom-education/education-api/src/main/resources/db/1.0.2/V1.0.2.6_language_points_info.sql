DROP TABLE IF EXISTS `language_points_info`;
CREATE TABLE `language_points_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `grade_info_id` int(11) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT 0,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `hasChildren` tinyint(1) NOT NULL DEFAULT 0,
  `sort` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识点信息表' ROW_FORMAT = Dynamic;