DROP TABLE IF EXISTS `subject_info`;
CREATE TABLE `subject_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `school_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '阶段（1 小学 2. 初中 3. 高中)',
  `grade_info_id` int(11) NOT NULL COMMENT '所属年级id',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '科目表' ROW_FORMAT = Dynamic;