DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar`  (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `day` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日期',
     `day_time` bigint(20) NULL DEFAULT NULL COMMENT '日期当天凌晨对应的时间戳',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日期表' ROW_FORMAT = Dynamic;