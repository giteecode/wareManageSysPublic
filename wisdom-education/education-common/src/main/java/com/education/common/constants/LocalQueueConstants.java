package com.education.common.constants;

/**
 * @author zengjintao
 * @create_at 2021年10月16日 0016 13:56
 * @since version 1.0.5
 */
public interface LocalQueueConstants {

    /**
     * 管理员登录成功
     */
    String ADMIN_LOGIN_SUCCESS_QUEUE = "adminLoginSuccessQueue";

    /**
     * 系统消息队列
     */
    String SYSTEM_SOCKET_MESSAGE = "systemSocketMessageQueue";


    /**
     * 系统消息队列
     */
    String EMAIL_MESSAGE = "systemSocketMessageQueue";

    /**
     * 请求日志队列消息
     */
    String REQUEST_LOG = "requestLog";

    /**
     * 课程评价
     */
    String COURSE_VALUATE = "courseValuate";
}
