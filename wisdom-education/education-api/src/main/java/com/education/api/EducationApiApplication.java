package com.education.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * 管理后台启动类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 11:11
 */
@SpringBootApplication(scanBasePackages =
   {
       "com.education"
   }
)
@MapperScan("com.education.business.mapper")
@EnableCaching
public class EducationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationApiApplication.class, args);
    }
}
