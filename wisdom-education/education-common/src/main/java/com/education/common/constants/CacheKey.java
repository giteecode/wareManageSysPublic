package com.education.common.constants;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/29 10:17
 */
public interface CacheKey {

    String KEY_GENERATOR_BEAN_NAME = "entityParamGenerator";
    /**
     * 字典类型缓存 cacheName
     */
    String SYSTEM_DICT = "system:dict";

    String SYSTEM_DICT_VALUE = "system:dict:value";

    /**
     * 考试相关缓存名
     */
    String EXAM_CACHE = "exam:cache";

    String TEST_PAPER_INFO_CACHE = "test:paper:info:cache:";

    String QUESTION_INFO_CACHE = "question:info:cache:";

    String EXAM_MONITOR_CACHE_KEY = "exam:monitor:cache:";

    String PAPER_EXAM_NUMBER = "paper_exam_number";

    String PAPER_INFO_SETTING = "paper:info:setting";

    /**
     *  考试排行榜key
     */
    String EXAM_SORT_KEY = "exam:sort:key:";

    /**
     *  试卷设置缓存key
     */
    String PAPER_INFO_SETTING_LOCK = "paper:setting:lock";

    String COURSE_SECTION = "course:section";

    String COURSE_INFO = "course:info";

    String USER_SYNC_LONGIN = "user:sync:login:";


    String STUDENT_USER_INFO_CACHE = "student:user:cache:";

    String USER_ON_LINE_KEY = "user:on:line";

    String UPLOAD_FILE = "upload:file:";

    String USER_LOGIN = "user:login:";
    String RECOMMEND_COURSE = "recommend:course";

    /**
     * 缓存试卷倒计时
     */
    String PAPER_TIME = "paper:time:";
}
