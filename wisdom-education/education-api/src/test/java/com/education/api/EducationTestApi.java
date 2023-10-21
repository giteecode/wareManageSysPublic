package com.education.api;


import com.education.business.service.education.GradeInfoService;
import com.education.common.cache.CacheBean;
import com.education.common.cache.CaffeineCacheBean;
import com.education.common.cache.EhcacheBean;
import com.education.model.entity.GradeInfo;
import com.jfinal.kit.Kv;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/22 12:01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EducationTestApi {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheBean redisCacheBean;

    @Autowired
    private GradeInfoService gradeInfoService;

    private CacheBean caffeineCacheBean = new CaffeineCacheBean();

    private CacheBean ehcacheBean = new EhcacheBean();

    /**
     * redis 存储string value
     */
    @Test
    public void redisStringValue() throws InterruptedException {
        redisTemplate.opsForValue().set("test", "test:value", 10, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("test"));
        Thread.sleep(10050);
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    public void redisHashValue() {
        Kv kv = Kv.create().set("id", 1).set("age", 10).set("address", "江西");
        redisTemplate.opsForHash().putAll("params", kv);
        Map map = redisTemplate.opsForHash().entries("params");
        System.out.println(map);
    }

    @Test
    public void redisList() {

        redisTemplate.opsForList().remove("list", 0, 11);
        for (int i = 0; i < 5; i++) {
            redisTemplate.opsForList().leftPush("list", i);
        }

        this.getRedisList();
        redisTemplate.opsForList().remove("list", 0, 2);
        this.getRedisList();
    }


    @Test
    public void getRedisList() {
        int size = redisTemplate.opsForList().size("list").intValue();
        List<Integer> list = redisTemplate.opsForList().range("list", 0, size - 1);
        System.out.println(list);
    }

    @Test
    public void testRedisZset() {

        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setName("jdbc 读写分离测试");
        gradeInfo.setCreateDate(new Date());
        gradeInfo.setSchoolType(2);
        gradeInfoService.save(gradeInfo);

        System.out.println(gradeInfoService.list());
        //Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
       // redisTemplate.opsForZSet().add("2012-12-10:10", 10, 10);
    }

    @Test
    public void testCache() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            redisCacheBean.put("test", i);
        }
        System.out.println("redisCacheBean 耗时:" + (System.currentTimeMillis() - start));
    }


}
