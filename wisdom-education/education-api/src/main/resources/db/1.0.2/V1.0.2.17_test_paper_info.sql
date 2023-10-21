DROP TABLE IF EXISTS `test_paper_info`;
CREATE TABLE `test_paper_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '试卷名称',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `mark` int(11) NOT NULL DEFAULT 0 COMMENT '试卷总分',
  `school_type` int(11) NOT NULL COMMENT '学校类型',
  `grade_info_id` int(11) NOT NULL COMMENT '年级id',
  `subject_id` int(11) NOT NULL COMMENT '所属科目id',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `exam_time` int(11) NOT NULL COMMENT '考试时间',
  `exam_number` int(4) NOT NULL DEFAULT 0 COMMENT '考试人数',
  `correct_number` int(4) NULL DEFAULT 0 COMMENT '已批改试卷数量',
  `publish_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否发布',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `question_number` int(11) NOT NULL DEFAULT 0 COMMENT '试题数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷信息表' ROW_FORMAT = Dynamic;