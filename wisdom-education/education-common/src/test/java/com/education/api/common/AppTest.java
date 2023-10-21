package com.education.api.common;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jfinal.kit.HashKit;
import com.qcloud.cos.utils.Md5Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    static final String key = "6ee13f7e427fed23";

    static final String SYSTEM_SIGN = "66ed2ac2105917f62a60675725ff5291";
    /**
     * Rigorous Test :-)
     */
   // @Test
   // public void shouldAnswerWithTrue()
    {
        //assertTrue( true );
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "1");
        params.put("test", new String[]{"1", "3"});
        String sign = HashKit.md5(AES.encrypt(JSONUtil.toJsonStr(params), key));
        System.out.println(sign);
        if (SYSTEM_SIGN.equals(sign)) {
            System.out.println("success");
        }
    }
}
