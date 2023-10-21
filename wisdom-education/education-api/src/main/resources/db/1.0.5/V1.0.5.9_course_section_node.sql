alter table course_section_node add video_url varchar(100) DEFAULT NULL COMMENT '视频路径';
update course_section_node set video_url = video_info ->> '$.url';
alter table course_section_node drop column video_info;
alter table course_section_node add video_name varchar(64) DEFAULT NULL COMMENT '视频名称';
alter table course_section_node add width int(4) DEFAULT 0 COMMENT '视频分辨率宽';
alter table course_section_node add height int(4) DEFAULT 0 COMMENT '视频分辨率高';
alter table course_section_node add duration int(4) DEFAULT 0 COMMENT '视频时长';