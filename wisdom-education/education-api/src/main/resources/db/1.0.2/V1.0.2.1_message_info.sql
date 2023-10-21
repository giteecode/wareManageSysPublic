DROP TABLE IF EXISTS `message_info`;
CREATE TABLE `message_info`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `student_id` int(11) NOT NULL COMMENT '学员id',
     `create_date` datetime(0) NOT NULL,
     `update_date` datetime(0) NULL DEFAULT NULL,
     `read_flag` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否已读',
     `message_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '消息类型（1 试卷批改通知)',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;