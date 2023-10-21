DROP TABLE IF EXISTS `student_question_answer`;
CREATE TABLE `student_question_answer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL COMMENT '学员id',
  `question_info_id` int(11) NOT NULL COMMENT '试题id',
  `student_answer` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学员答案',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `mark` int(11) NOT NULL DEFAULT 0 COMMENT '答题得分',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评语',
  `question_points` int(11) NOT NULL DEFAULT 0 COMMENT '试题分数',
  `correct_status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '批改状态 0 错误 1 正确 2 待批改 3.已批改（针对主观题）',
  `exam_info_id` int(11) NOT NULL COMMENT '考试记录id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考生答题记录表' ROW_FORMAT = Dynamic;