DROP TABLE IF EXISTS `test_paper_info_setting`;
CREATE TABLE `test_paper_info_setting`  (
  `id` int(11) NOT NULL,
  `test_paper_info_id` int(11) NOT NULL COMMENT '所属试卷id',
  `commit_after_type` tinyint(2) NOT NULL COMMENT '提交后是否立即出分\r\n1. 批改后出分\r\n2. 立即显示分数',
  `show_mark_sort` tinyint(1) NOT NULL COMMENT '是否显示成绩排名榜',
  `show_student_sort` tinyint(2) NOT NULL COMMENT '是否显示学员成绩排名',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `reference_number` int(4) NOT NULL DEFAULT 1 COMMENT '参考次数',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `exam_type` tinyint(2) NOT NULL DEFAULT 2 COMMENT '1.指定时间段 2. 永久有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷设置表' ROW_FORMAT = Dynamic;