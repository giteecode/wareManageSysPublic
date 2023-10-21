alter table student_wrong_book add biz_type tinyint(2) DEFAULT NULL COMMENT '错题本业务类型(1 试卷试题)';
alter table student_wrong_book add biz_id int(11) DEFAULT NULL COMMENT '对应业务类型表id主键';

