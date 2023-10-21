DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log`  (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `request_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   `method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   `request_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   `create_date` datetime(0) NULL DEFAULT NULL,
   `user_id` int(11) NULL DEFAULT NULL,
   `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
   `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
   `platform_type` tinyint(2) NULL DEFAULT NULL,
   `operation_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   `operation_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
   `update_date` datetime(0) NULL DEFAULT NULL,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;