package com.education.business.task;

import cn.hutool.json.JSONUtil;
import com.education.business.session.AdminUserSession;
import com.education.business.session.StudentSession;
import com.education.business.session.UserSessionContext;
import com.education.business.task.param.LogTaskParam;
import com.education.common.annotation.SystemLog;
import com.education.common.constants.LocalQueueConstants;
import com.education.common.enums.PlatformTypeEnum;
import com.education.common.utils.IpUtils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.RequestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 日志切面
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/1/3 19:32
 */
@Component
@Aspect
public final class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private TaskManager taskManager;


    /**
     * execution表达式示例
     * execution(* com.education.api.controller..*.*(..))
     * 详述：
     * execution()，表达式的主体
     * 第一个“*”符号，表示返回值类型任意；
     * com.sample.service.impl，AOP所切的服务的包名，即我们的业务部分
     * 包名后面的“..”，表示当前包及子包
     * 第二个“*”，表示类名，*即所有类
     * .*(..)，表示任何方法名，括号表示参数，两个点表示任何参数类型
     * 使用异步任务记录 http 请求接口日志
     * @param pjp
     * @return
     */                       //  com.education.api.controller.admin.education.CourseInfoController
    @Around("execution(public * com.education.*.controller..*.*(..))")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        if (systemLog == null) {
            return pjp.proceed();
        }
        LogTaskParam taskParam = new LogTaskParam(LocalQueueConstants.REQUEST_LOG);
        if (ObjectUtils.isNotEmpty(systemLog)) {
            taskParam.setOperationDesc(systemLog.describe());
        }
        HttpServletRequest request = RequestUtils.getRequest();
        String requestUrl = RequestUtils.getRequestUrl(request);
        taskParam.setRequestUrl(requestUrl);
        String contentType = request.getHeader("Content-Type");
        String methodName = request.getMethod();
        taskParam.setMethod(methodName);
        taskParam.setContentType(contentType);
        taskParam.setIp(IpUtils.getAddressIp(request));
        StringBuilder jsonParams = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();
        if (ObjectUtils.isNotEmpty(params)) {
            jsonParams.append(JSONUtil.toJsonStr(params));
        } else {
            jsonParams.append(RequestUtils.readData(request));
        }
        taskParam.setParams(jsonParams.toString());
        if (requestUrl.startsWith("/system")) {
            AdminUserSession adminUserSession = UserSessionContext.getAdminUserSession();
            if (adminUserSession != null) {
                taskParam.setOperationName(adminUserSession.getSystemAdmin().getLoginName());
                taskParam.setUserId(adminUserSession.getSystemAdmin().getId());
            }
            taskParam.setPlatformTypeEnum(PlatformTypeEnum.WEB_ADMIN);
        } else if (requestUrl.startsWith("/student")) {
            StudentSession studentUserSession = UserSessionContext.getStudentUserSession();
            if (studentUserSession != null) {
                taskParam.setOperationName(studentUserSession.getLoginName());
                taskParam.setUserId(studentUserSession.getId());
            }
            taskParam.setPlatformTypeEnum(PlatformTypeEnum.WEB_FRONT);
        } else {
            taskParam.setOperationName("匿名用户");
        }

        Object result;
        try {
            long startTime = System.currentTimeMillis();
            result = pjp.proceed();
            taskParam.setRequestTime((System.currentTimeMillis() - startTime) + "ms");
            taskManager.pushTask(taskParam);
            return result;
        } catch (Throwable throwable) {
            StringBuffer error = new StringBuffer();
            StackTraceElement[] stackTraceElements = throwable.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                error.append(stackTraceElement.toString() + "   ");
            }
            taskParam.setException(error.toString());
            taskManager.pushTask(taskParam);
            logger.error(error.toString(), throwable);
            throw throwable;
        }
    }
}
