DROP TABLE IF EXISTS `course_question_info`;
DROP TABLE IF EXISTS `study_history`;
DROP TABLE IF EXISTS `website_config`;

ALTER TABLE student_info DROP father_name;
ALTER TABLE student_info DROP mother_name;

rename table student_question_answer TO exam_question_answer;
rename table student_wrong_book TO exam_wrong_book;

ALTER TABLE exam_wrong_book CHANGE biz_id exam_info_id int(11);
ALTER TABLE exam_wrong_book DROP biz_type;


