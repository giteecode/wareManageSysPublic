DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement`  (
  `id` int(11) NOT NULL,
  `place` tinyint(2) NOT NULL COMMENT '广告位置',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广告链接',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片链接地址',
  `platform` tinyint(2) NULL DEFAULT NULL COMMENT '平台类型（1 学生web端 2 移动端)',
  `sort` tinyint(2) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '广告表' ROW_FORMAT = Dynamic;

