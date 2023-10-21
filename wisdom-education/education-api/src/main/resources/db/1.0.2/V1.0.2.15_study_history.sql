DROP TABLE IF EXISTS `study_history`;
CREATE TABLE `study_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update` datetime(0) NULL DEFAULT NULL,
  `student_id` int(11) NULL DEFAULT NULL,
  `study_time` int(11) NULL DEFAULT 0 COMMENT '学习时间（单位秒）',
  `update_date` datetime(0) NULL DEFAULT NULL,
  `course_id` int(11) NULL DEFAULT NULL,
  `start_date` datetime(0) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci  COMMENT = '学习记录表' ROW_FORMAT = Dynamic;