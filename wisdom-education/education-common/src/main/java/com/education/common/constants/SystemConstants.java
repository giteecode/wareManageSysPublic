package com.education.common.constants;


/**
 * 系统常量类
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/11/27 20:01
 */
public final class SystemConstants {

    public static final String SESSION_COOKIE_NAME = "u_id_key"; // session 会话cookie名称
    public static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";// session 会话默认cookie名称

    public static final String[] IMAGE_ALIAS = new String[] {"head_img", "image"};


    public static final double PASS_MARK_RATE = 0.6; // 及格比例
    public static final double NICE_MARK_RATE = 0.8; // 优秀比例

    public static final String SESSION_KEY = "user:session:cache:";

    public static final String PAPER_INFO_TEMPLATE = "/template/enjoy/paperInfoTemplate.html";

    public static final Integer SEND_RUNNING = 1;

    public static final Integer SEND_SUCCESS = 2;

    public static final Integer CONSUME_SUCCESS = 3;

    public static final Integer CONSUME_FAIL = 4;

    public static final Integer SEND_FAIL = 5;

    // 消息发送最大次数
    public static final Integer MAX_SEND_COUNT = 3;

    public static final String REDIS_TEMPLATE_BEAN_NAME = "redisTemplate";

    public static final String DEFAULT_GROUP_JOB = "default_job";

    public static final Integer COURSE_VALUATE_MARK = 10;

    public static final String ENV_PROD = "prod";

    public static final String ENV_TEST = "test";

    public static final String HTML = ".html";

    /**
     * windows 默认文件上传临时路径
     */
    public static final String DEFAULT_LOCAL_TMP_FILE_UPLOAD_PATH = "D:\\uploads\\";

    /**
     * Linux 默认文件上传临时路径
     */
    public static final String DEFAULT_LINUX_TMP_FILE_UPLOAD_PATH = "/home/upload/";

    public static final String FILE_SEPARATOR = "/";

    /**
     * aes 加密解密秘钥key
     */
    public static final String PASSWORD_KEY = "mpw.key";

    /**
     * 百分号
     */
    public static final String PERCENT = "%";

}
