alter table test_paper_info_setting MODIFY id int(11) auto_increment;
alter table test_paper_info_setting add reference_type tinyint(2) DEFAULT NULL COMMENT '答卷次数类型(1. 1次 2.不限次数 3. 自定义)';