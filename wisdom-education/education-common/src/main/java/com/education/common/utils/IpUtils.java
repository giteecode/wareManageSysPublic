package com.education.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class IpUtils {

	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

	/**
	 * 获取客户端ip地址
	 * @return
	 */
   public static String getAddressIp(HttpServletRequest request) {
	   String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	      ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	      ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	      ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	      ip = request.getRemoteAddr();
	    }
	    return ip;
    }

    private static final String TENCENT_URL = "https://apis.map.qq.com/ws/location/v1/ip";

    private static final String TENCENT_URL_KEY = "MYOBZ-OOEW3-KYC3G-YWDXA-DMQJ6-SPBMH";

    /**
     * 获取ip地址
     * @param ip
     * @return
     */
	@Deprecated
    public static String getIpAddress(String ip){
		try {
			Map params = new HashMap<>();
			params.put("key", TENCENT_URL_KEY);
			params.put("ip", ip);
			String content = HttpKit.get(TENCENT_URL, params);
			JSONObject jsonObject = JSONObject.parseObject(content);
			if (jsonObject != null && jsonObject.containsKey("result")) {
				JSONObject location = JSONObject.parseObject(jsonObject.getString("result"));
				if (location != null && location.containsKey("ad_info")) {
					Map<String, String> resultMap = (Map<String, String>) JSONObject.parse(location.getString("ad_info"));
					if (resultMap.containsKey("adcode")) {
						String address = "";
						String nation = resultMap.get("nation");
						if (!"中国".equals(nation)) {
							address += nation;
						}
						String provinceName = resultMap.get("province");
						String cityName = resultMap.get("city");
						// String district = resultMap.get("district");
						return address + provinceName + cityName;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取ip地址异常", e);
		}
		return "其它设备上";
	}

	/**
	 * 获取服务器主机ip
	 * @return
	 */
	public static String getLocalHost() {
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
}
