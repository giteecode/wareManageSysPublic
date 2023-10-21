
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
    `id` int(64) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `parent_id` int(11) NULL DEFAULT 0,
    `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_date` datetime(0) NULL DEFAULT NULL,
    `update_date` datetime(0) NULL DEFAULT NULL,
    `sort` int(11) NULL DEFAULT 0,
    `type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '类型（1菜单 2. 目录 3 按钮)',
    `create_type` tinyint(2) NULL DEFAULT 1 COMMENT '创建类型（1 系统内置 2. 管理员创建)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单表' ROW_FORMAT = Dynamic;


INSERT INTO `system_menu` VALUES (1, '系统设置', '', 0, '', 'system', '2019-03-23 14:57:58', NULL, 4, 1, 1);
INSERT INTO `system_menu` VALUES (5, '角色管理', '/role', 1, NULL, 'role', '2019-03-23 15:00:10', NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (8, '菜单管理', '/menu', 1, NULL, 'menu', '2020-11-16 11:10:25', NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (14, '管理员管理', '/admin', 1, NULL, 'admin', '2020-11-16 11:10:28', NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (15, '列表', NULL, 14, 'system:admin:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (16, '添加', NULL, 14, 'system:admin:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (17, '列表', NULL, 5, 'system:role:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (18, '添加', NULL, 5, 'system:role:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (19, '列表', '', 8, 'system:menu:list', NULL, '2020-06-15 20:16:43', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (20, '添加', '', 8, 'system:menu:save', NULL, '2020-06-15 20:17:30', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (21, '修改', '', 8, 'system:menu:update', NULL, '2020-06-15 20:21:50', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (22, '删除', '', 8, 'system:menu:deleteById', NULL, '2020-06-15 20:26:16', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (23, '修改', '', 14, 'system:admin:update', NULL, '2020-06-15 20:26:41', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (24, '删除', '', 14, 'system:admin:deleteById', NULL, '2020-06-15 20:27:06', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (25, '删除', '', 5, 'system:role:deleteById', NULL, '2020-06-15 20:29:20', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (26, '修改', '', 5, 'system:role:update', NULL, '2020-06-15 20:29:44', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (27, '重置密码', NULL, 14, 'system:admin:updatePassword', NULL, '2020-11-16 11:10:20', NULL, 0, 1, 1);
INSERT INTO `system_menu` VALUES (28, '权限设置', '', 5, 'system:role:savePermission', NULL, '2020-06-17 18:04:17', NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (35, '日志管理', '/log', 1, NULL, 'log', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (36, '列表', NULL, 35, 'system:log:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (37, '删除', NULL, 35, 'system:log:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (38, '字典管理', '/dict', 1, NULL, 'dict', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (39, '列表', NULL, 38, 'system:dict:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (40, '删除', NULL, 38, 'system:dict:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (42, '添加', NULL, 38, 'system:dict:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (43, '修改', NULL, 38, 'system:dict:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (44, '字典值列表', NULL, 38, 'system:dictValue:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (45, '添加字典值', NULL, 38, 'system:dictValue:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (46, '修改字典值', NULL, 38, 'system:dictValue:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (47, '删除字典值', NULL, 38, 'system:dictValue:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (48, '系统监控', NULL, 0, NULL, 'monitor', NULL, NULL, 3, 1, 1);
INSERT INTO `system_menu` VALUES (49, '在线用户管理', '/onlineUser', 48, NULL, 'admin', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (50, '列表', NULL, 49, '', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (51, '教育教学', NULL, 0, NULL, 'video', NULL, NULL, 0, 1, 1);
INSERT INTO `system_menu` VALUES (52, '科目管理', '/subject', 51, NULL, 'subject', NULL, '2021-08-31 23:13:58', 0, 2, 1);
INSERT INTO `system_menu` VALUES (53, '列表', NULL, 52, 'system:subject:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (54, '添加', NULL, 52, 'system:subject:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (55, '修改', NULL, 52, 'system:subject:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (56, '删除', NULL, 52, 'system:subject:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (57, '课程管理', '/course', 51, NULL, 'course', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (58, '列表', NULL, 57, 'system:course:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (59, '添加', NULL, 57, 'system:course:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (60, '修改', NULL, 57, 'system:course:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (61, '删除', NULL, 57, 'system:course:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (62, '学员管理', '/student', 51, NULL, 'student', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (63, '列表', NULL, 62, 'system:student:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (64, '添加', NULL, 62, 'system:student:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (65, '修改', NULL, 62, 'system:student:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (66, '删除', NULL, 62, 'system:student:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (67, '重置密码', NULL, 62, 'system:student:updatePassword', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (68, '导入', NULL, 62, 'system:student:import', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (69, '试卷批改', '/correctExam', 51, NULL, 'correct', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (70, '列表', NULL, 69, 'system:exam:list', '', NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (71, '批改', NULL, 69, 'system:exam:correct', '', NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (72, '年级管理', '/gradeInfo', 51, NULL, 'zhedie', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (73, '列表', NULL, 72, 'system:grade:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (74, '添加', NULL, 72, 'system:grade:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (75, '修改', NULL, 72, 'system:grade:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (76, '删除', NULL, 72, 'system:grade:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (77, '添加知识点', NULL, 52, 'system:languagePointsInfo:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (78, '知识点列表', NULL, 52, 'system:languagePointsInfo:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (79, '知识点修改', NULL, 52, 'system:languagePointsInfo:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (80, '删除知识点', NULL, 52, 'system:languagePointsInfo:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (81, '考试管理', NULL, 0, NULL, 'exam', NULL, NULL, 1, 1, 1);
INSERT INTO `system_menu` VALUES (82, '试题管理', '/question', 81, NULL, 'question', NULL, '2021-08-31 21:30:25', 0, 2, 1);
INSERT INTO `system_menu` VALUES (83, '列表', NULL, 82, 'system:question:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (84, '添加', NULL, 82, 'system:question:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (85, '修改', NULL, 82, 'system:question:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (86, '删除', NULL, 82, 'system:question:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (87, '试卷管理', '/testPaper', 81, NULL, 'testPaper', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (88, '列表', NULL, 87, 'system:testPaperInfo:list', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (89, '添加', NULL, 87, 'system:testPaperInfo:save', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (90, '修改', NULL, 87, 'system:testPaperInfo:update', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (91, '删除', NULL, 87, 'system:testPaperInfo:deleteById', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (92, '统计分析', NULL, 0, NULL, 'statistics', NULL, '2020-12-09 17:00:30', 2, 1, 1);
INSERT INTO `system_menu` VALUES (93, '考试统计', '/examReport', 92, NULL, 'statistics-exam', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (94, '列表', NULL, 93, 'system:exam:examReportList', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (95, '试题管理', '', 87, 'system:testPaperInfo:relevanceQuestion', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (96, '发布', NULL, 87, 'system:testPaperInfo:publish', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (97, '撤回', NULL, 87, 'system:testPaperInfo:cancel', NULL, NULL, NULL, 0, 3, 1);
INSERT INTO `system_menu` VALUES (98, '试题导入', '/questionImport', 81, NULL, 'importQuestion', NULL, NULL, 0, 2, 1);
INSERT INTO `system_menu` VALUES (99, '测试菜单222', '', 8, '', '', '2021-10-02 13:35:24', NULL, 0, 3, 2);



