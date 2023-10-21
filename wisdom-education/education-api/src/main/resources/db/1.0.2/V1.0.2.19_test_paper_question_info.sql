DROP TABLE IF EXISTS `test_paper_question_info`;
CREATE TABLE `test_paper_question_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_info_id` int(11) NOT NULL,
  `test_paper_info_id` int(11) NOT NULL,
  `mark` int(11) NOT NULL DEFAULT 0,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `sort` int(4) NOT NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷试题关联表' ROW_FORMAT = Dynamic;