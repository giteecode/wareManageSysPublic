DROP TABLE IF EXISTS `question_info`;
CREATE TABLE `question_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NOT NULL COMMENT '课程名称',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `video_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '答案',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '试题内容',
  `school_type` int(2) NOT NULL COMMENT '阶段id',
  `question_type` int(2) NOT NULL COMMENT '试题类型',
  `grade_info_id` int(2) NOT NULL,
  `options` json NULL COMMENT '试题选项（多个以逗号隔开）',
  `analysis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '试题解析内容',
  `summarize` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总结升华',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试题信息表' ROW_FORMAT = Dynamic;