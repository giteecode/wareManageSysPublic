DROP TABLE IF EXISTS `question_language_points_info`;
CREATE TABLE `question_language_points_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_info_id` int(11) NOT NULL COMMENT '试题id',
  `language_points_info_id` int(11) NOT NULL COMMENT '知识点id',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试题知识点关联表'
ROW_FORMAT = Dynamic;