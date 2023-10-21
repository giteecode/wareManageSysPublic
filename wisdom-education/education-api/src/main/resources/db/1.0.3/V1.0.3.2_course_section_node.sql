DROP TABLE IF EXISTS `course_section_node`;
CREATE TABLE `course_section_node`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NULL DEFAULT NULL COMMENT '课程id',
  `course_section_id` int(11) NOT NULL COMMENT '课程章节id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点名称',
  `video_info` json NULL COMMENT '视频信息',
  `enclosure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `free_flag` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否免费',
  `synopsis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课时简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '章节课时表' ROW_FORMAT = Dynamic;

