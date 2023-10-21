package com.education.api;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/2/21 19:40
 */
public class AesTest {

    public static void main(String[] args) {
        System.out.println(AES.generateRandomKey());
    }
}
