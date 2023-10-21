DROP TABLE IF EXISTS `system_dict_value`;
CREATE TABLE `system_dict_value`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_dict_id` int(11) NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` int(11) NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT 0,
  `sort` int(11) NOT NULL DEFAULT 0,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型值表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_value
-- ----------------------------
INSERT INTO `system_dict_value` VALUES (1, 1, '小学', 1, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (2, 1, '初中', 2, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (3, 1, '高中', 3, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (4, 2, '一年级', 1, 1, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (5, 2, '二年级', 2, 1, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (6, 2, '三年级', 3, 1, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (7, 2, '四年级', 4, 1, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (8, 2, '五年级', 5, 1, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (9, 2, '六年级', 6, 1, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (10, 2, '初一', 7, 2, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (11, 2, '初二', 8, 2, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (12, 2, '初三', 9, 2, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (13, 2, '高一', 10, 3, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (14, 2, '高二', 11, 3, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (15, 2, '高三', 12, 3, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (18, 3, '单选题', 1, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (19, 3, '多选题', 2, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (16, 3, '填空题', 3, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (17, 3, '综合题', 4, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (20, 3, '不定项', 5, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (21, 3, '判断题', 6, 0, 0, NULL, NULL);
INSERT INTO `system_dict_value` VALUES (22, 3, '计算题', 7, 0, 0, NULL, NULL);


