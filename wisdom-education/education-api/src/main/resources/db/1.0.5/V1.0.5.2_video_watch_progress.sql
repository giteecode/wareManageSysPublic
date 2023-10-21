DROP TABLE IF EXISTS `video_watch_progress`;
CREATE TABLE `video_watch_progress`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL COMMENT '课程id',
  `section_node_id` int(11) NOT NULL COMMENT '课程课时id',
  `student_id` int(11) NOT NULL COMMENT '学员id',
  `watch_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '观看时长',
  `watch_end` bigint(20) NOT NULL DEFAULT 0 COMMENT '是否已学完',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频观看时长记录表' ROW_FORMAT = Dynamic;

