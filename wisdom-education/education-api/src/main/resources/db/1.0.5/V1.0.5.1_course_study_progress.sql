DROP TABLE IF EXISTS `course_study_progress`;
CREATE TABLE `course_study_progress`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL COMMENT '课程id',
  `student_id` int(11) NOT NULL COMMENT '学员id',
  `watch_time` bigint(20) NOT NULL COMMENT '课程学习总时长',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程学习进度表' ROW_FORMAT = Dynamic;

