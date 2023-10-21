package com.education.common.constants;

/**
 * @author zengjintao
 * @create_at 2021年10月13日 0013 15:37
 * @since version 1.6.5
 */
public interface AuthConstants {

    /**
     * 请求头：鉴权token
     */
    String AUTHORIZATION = "Authorization";

    /**
     * 平台类型
     */
    String PLATFORM = "Platform";

    /**
     * 签名
     */
    String SIGN = "Sign";

    /**
     * jwt token秘钥
     */
    String EDUCATION_SECRET_KEY = "business_education";
}
