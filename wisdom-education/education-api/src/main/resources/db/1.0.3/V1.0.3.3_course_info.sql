alter table course_info add section_node_number int(4) default 0 COMMENT '课时数量';
alter table course_info add section_number int(4) default 0 COMMENT '章节数量';
alter table course_info add push_time datetime(0) NULL DEFAULT NULL COMMENT '课程发布时间';