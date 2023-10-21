DROP TABLE IF EXISTS `message_log`;
CREATE TABLE `message_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '1.消息发送中 2.消息发送成功3.已消费4.消费失败5.消息发送失败',
  `try_count` tinyint(2) NOT NULL DEFAULT 0 COMMENT '消息重发次数',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `content` json NULL COMMENT '消息内容',
  `correlation_data_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fail_send_cause` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '发送失败原因',
  `consume_cause` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消费失败原因',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `routing_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message_type` tinyint(2) NULL DEFAULT NULL COMMENT '消息类型(1 考试提交消息)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '队列消息发送日志表' ROW_FORMAT = Dynamic;