DROP TABLE IF EXISTS `course_question_info`;
CREATE TABLE `course_question_info`  (
  `sort` int(11) NOT NULL DEFAULT 0,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_info_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `mark` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程试题关联表' ROW_FORMAT = Dynamic;