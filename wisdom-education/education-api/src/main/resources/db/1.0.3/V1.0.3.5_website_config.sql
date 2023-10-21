DROP TABLE IF EXISTS `website_config`;
CREATE TABLE `website_config`  (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `logo_url` varchar(100) NOT NULL COMMENT '网站logo',
   `carousel_image` varchar(100) NOT NULL COMMENT '轮播图(多个以逗号隔开)',
   `create_date` datetime(0) NULL DEFAULT NULL,
   `update_date` datetime(0) NULL DEFAULT NULL,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网站配置表' ROW_FORMAT = Dynamic;

