DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `grade_info_id` int(2) NOT NULL,
    `school_type` int(2) NOT NULL,
    `subject_id` int(11) NOT NULL,
    `create_date` datetime(0) NULL DEFAULT NULL,
    `update_date` datetime(0) NULL DEFAULT NULL,
    `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程编号',
    `sort` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '排序',
    `parent_id` int(11) NULL DEFAULT 0,
    `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `represent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程简介',
    `recommend_index_flag` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否推荐到系统首页',
    `study_number` int(11) NOT NULL DEFAULT 0,
    `status` tinyint(2) NULL DEFAULT 1 COMMENT '状态 （0 草稿 1, 上架 2 下架）',
    `valuate_mark` decimal(4, 2) NOT NULL DEFAULT 10.00 COMMENT '评价分数',
    `comment_number` int(4) NOT NULL DEFAULT 0 COMMENT '评论数量',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程信息表' ROW_FORMAT = Dynamic;