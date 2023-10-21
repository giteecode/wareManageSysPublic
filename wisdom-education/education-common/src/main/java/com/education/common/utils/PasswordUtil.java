package com.education.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.kit.HashKit;

/**
 * @author zjt
 * @create_at 2021年12月17日 0017 11:15
 * @since version 1.0.4
 */
public class PasswordUtil {

    public static String createEncrypt() {
        return HashKit.md5(HashKit.sha1(IdUtil.fastSimpleUUID()));
    }

    public static String createPassword(String encrypt, String password) {
        String key = password + StrUtil.COLON + encrypt;
        return HashKit.md5(key);
    }

}
