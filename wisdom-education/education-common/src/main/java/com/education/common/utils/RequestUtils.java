package com.education.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 22:32
 */
public class RequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    static String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
            "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod", "nokia",
            "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma", "docomo",
            "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos", "techfaith",
            "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem", "wellcom", "bunjalloo",
            "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos", "pantech", "gionee", "portalmmm",
            "jig browser", "hiptop", "benq", "haier", "^lct", "320x320", "240x320", "176x220", "w3c ", "acs-", "alav",
            "alca", "amoi", "audi", "avan", "benq", "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
            "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g",
            "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-", "newt", "noki",
            "oper", "palm", "pana", "pant", "phil", "play", "port", "prox", "qwap", "sage", "sams", "sany", "sch-",
            "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar", "sony", "sph-", "symb", "t-mo",
            "teli", "tim-", "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc",
            "winw", "winw", "xda", "xda-", "googlebot-mobile"};


    static final Set<Integer> PORT_SET = new HashSet<Integer>() {
        {
            add(80);
            add(443);
        }
    };

    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public static String readData(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String getRequestUrl() {
        return getRequestUrl(getRequest());
    }

    public static String getRequestUrl(HttpServletRequest request) {
        String contentPath = request.getServletContext().getContextPath();
        int contentPathLength = contentPath.length();
        String target = request.getRequestURI();
        if (contentPathLength != 0){
            target = target.substring(contentPathLength);
        }
        return target;
    }

    /**
     * 获取地址栏域名
     * @return
     */
    public static String getDomain() {
        HttpServletRequest request = getRequest();
        String scheme = request != null ? request.getScheme() : null; //得到协议名 例如：http
        String serverName = request != null ? request.getServerName() : null; //得到域名 localhost
        int port = request.getServerPort();
        if (!PORT_SET.contains(port)) {
            return scheme + "://" + serverName + ":" + port;
        }
        String domain = scheme + "://" + serverName;
        if (domain.contains("www.")) {
            domain = domain.replaceAll("www.", "");
        }
        return domain;
    }

    public static String getUploadDomain() {
        return getDomain() + "/uploads";
    }

    public static HttpServletResponse getResponse() {
        try {
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    public static InputStream getInputStreamFromUrl(String url) {
        try {
            URL resource = new URL(url);
            URLConnection connection = resource.openConnection();
            return connection.getInputStream();
        } catch (Exception e) {
            logger.error("获取流异常,请检查url是否正确", e);
        }
        return null;
    }

    public static String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        return ObjectUtils.isNotEmpty(cookie) ? cookie.getValue() : null;
    }

    public static Cookie getCookie(String cookieName) {
        HttpServletRequest request = RequestUtils.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 清空cookie
     * @param cookieName
     */
    public static void clearCookie(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        HttpServletResponse response = getResponse();
        if (ObjectUtils.isNotEmpty(cookie)) {
            cookie.setMaxAge(0); // 清除cookie
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static void createCookie(String cookieName, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        HttpServletResponse response = RequestUtils.getResponse();
        response.addCookie(cookie);
    }

    /**
     * 是否是手机浏览器
     * @return
     */
    public static boolean isMobileBrowser() {
        String ua = getRequest().getHeader("User-Agent");
        if (StrUtil.isBlank(ua)) {
            return false;
        }
        ua = ua.toLowerCase();
        for (String mobileAgent : mobileAgents) {
            if (ua.indexOf(mobileAgent) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是微信浏览器
     *
     * @return
     */
    public static boolean isWechatBrowser() {
        String ua = getRequest().getHeader("User-Agent");
        return StrUtil.isNotBlank(ua) && ua.toLowerCase().indexOf("micromessenger") > -1;
    }


    /**
     * 获取客户端设备类型
     * @param request
     */
    public static void getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
    }
}
