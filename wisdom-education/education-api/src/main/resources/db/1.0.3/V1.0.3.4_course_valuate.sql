DROP TABLE IF EXISTS `course_valuate`;
CREATE TABLE `course_valuate`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL COMMENT '所属课程id',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `valuate_type` tinyint(2) NOT NULL COMMENT '评价类型(1.好评 2.中评 3.差评)',
  `valuate_mark` tinyint(2) NOT NULL COMMENT '评价分数',
  `student_id` int(11) NOT NULL COMMENT '所属学员id',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程评价表' ROW_FORMAT = Dynamic;

