package com.education.business.interceptor;

import com.education.common.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 系统接口日志拦截器
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/11/27 20:17
 */
@Component
public class LogInterceptor extends BaseInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private static final String title = "\nSpringMvc" + " action report -------- ";
    private static int maxOutputLengthOfParaValue = 512;

    private final ThreadLocal<Map<String, Long>> threadLocal = new ThreadLocal() {
        @Override
        protected ConcurrentHashMap initialValue() {
            return new ConcurrentHashMap();
        }
    };

    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        threadLocal.get().put("startTime", startTime);
        //解决ajax跨域问题
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Expose-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "3600");//30 分钟
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String target = RequestUtils.getRequestUrl(request);
        StringBuilder sb = new StringBuilder(title).append(sdf.get().format(new Date())).append(" --------------------------\n");
        sb.append("Url          : ").append(request.getMethod()).append(" ").append(target).append("\n");

        if (handler instanceof HandlerMethod) {
            sb.append("Controller   : ").append(((HandlerMethod) handler).getBean().getClass()).append("\n");
            sb.append("Method       : ").append(((HandlerMethod) handler).getMethod().getName()).append("\n");
        }

        if (modelAndView != null)
            sb.append("ViewName     : ").append(modelAndView.getViewName()).append("\n");

        Enumeration<String> e = request.getParameterNames();
        sb.append("Parameter    : ");
        if (e.hasMoreElements()) {
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String[] values = request.getParameterValues(name);
                if (values.length == 1) {
                    sb.append(name).append("=");
                    if (values[0] != null && values[0].length() > maxOutputLengthOfParaValue) {
                        sb.append(values[0], 0, maxOutputLengthOfParaValue).append("...");
                    } else {
                        sb.append(values[0] + "(" + values[0].getClass().getSimpleName() + ")");
                    }
                }
                else {
                    sb.append(name).append("[]={");
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0)
                            sb.append(",");
                        sb.append(values[i]);
                    }
                    sb.append("}");
                }
                sb.append("  ");
            }

        }

        String params = readData(request); // 拼接body参数
        sb.append(params);

        sb.append("\n");
        long endTime = System.currentTimeMillis();
        long startTime = threadLocal.get().get("startTime");
        sb.append("------------------------- 接口响应时间:" +  (endTime - startTime) + "ms------------------------------------");
        logger.info(sb.toString());
    }

    /**
     * 使用异步任务记录 http 请求接口日志
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }
}
