DROP TABLE IF EXISTS `student_course_collect`;
CREATE TABLE `student_course_collect`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci COMMENT = '学员课程收藏表' ROW_FORMAT = Dynamic;
