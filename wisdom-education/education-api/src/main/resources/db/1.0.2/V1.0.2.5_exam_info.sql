DROP TABLE IF EXISTS `exam_info`;
CREATE TABLE `exam_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `mark` int(11) NOT NULL DEFAULT 0 COMMENT '总得分',
  `test_paper_info_id` int(11) NOT NULL,
  `correct_flag` tinyint(2) NOT NULL DEFAULT 0 COMMENT '1 已批改 0.待批改',
  `system_mark` int(2) NOT NULL DEFAULT 0 COMMENT '系统判分',
  `teacher_mark` int(2) NOT NULL DEFAULT 0 COMMENT '老师评分',
  `exam_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '考试耗时（字符串存储）',
  `correct_type` tinyint(2) NULL DEFAULT NULL COMMENT '批改形式（1 系统 2 .教师 3 系统 + 教师）',
  `admin_id` int(11) NOT NULL DEFAULT 0 COMMENT '批改人',
  `right_number` int(11) NOT NULL COMMENT '客观题答对题数',
  `error_number` int(11) NOT NULL DEFAULT 0 COMMENT '客观题答错题数（包括主观题未作答数量）',
  `subjective_question_number` int(11) NOT NULL DEFAULT 0 COMMENT '主观题数量',
  `question_number` int(11) NULL DEFAULT NULL COMMENT '试题数量',
  `exam_time_long_value` int(11) NOT NULL DEFAULT 0 COMMENT '考试时间（整数存储）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考试信息表' ROW_FORMAT = Dynamic;