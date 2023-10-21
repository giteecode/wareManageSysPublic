DROP TABLE IF EXISTS `system_dict`;
CREATE TABLE `system_dict`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_date` datetime(0) NULL DEFAULT NULL,
    `update_date` datetime(0) NULL DEFAULT NULL,
    `sort` int(11) NOT NULL DEFAULT 0,
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

INSERT INTO `system_dict` VALUES (1, '阶段类型', 'school_type', '2020-05-21 15:50:43', '2021-09-14 10:31:29', 1, NULL);
INSERT INTO `system_dict` VALUES (2, '年级类型', 'grade_type', '2020-05-21 15:51:03', '2020-05-21 15:51:04', 0, NULL);
INSERT INTO `system_dict` VALUES (3, '试题类型', 'question_type', '2020-04-30 14:06:13', '2020-05-06 14:34:34', 0, NULL);
