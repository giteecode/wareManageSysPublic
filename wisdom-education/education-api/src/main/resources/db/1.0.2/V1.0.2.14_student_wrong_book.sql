DROP TABLE IF EXISTS `student_wrong_book`;
CREATE TABLE `student_wrong_book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL COMMENT '学员id',
  `question_info_id` int(11) NOT NULL COMMENT '试题id',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `question_mark` int(4) NOT NULL DEFAULT 0 COMMENT '试题分数',
  `student_answer` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学员答案',
  `mark` int(2) NOT NULL DEFAULT 0 COMMENT '试题得分',
  `correct_status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '批改状态 0 错误 1 正确 2 待批改 3.已批改（针对主观题）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学员错题本信息表' ROW_FORMAT = Dynamic;