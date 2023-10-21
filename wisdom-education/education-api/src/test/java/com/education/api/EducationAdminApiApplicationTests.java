package com.education.api;

import com.alibaba.fastjson.JSON;
import com.education.business.service.education.QuestionInfoService;
import com.education.business.service.system.SystemAdminService;
import com.education.common.cache.CacheBean;
import com.education.common.cache.EhcacheBean;
import com.education.model.entity.CourseInfo;
import com.education.model.entity.StudentInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.json.JacksonFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EducationAdminApiApplicationTests {

    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private CacheBean cacheBean = new EhcacheBean();

    @Autowired
    private SystemAdminService systemAdminService;

    static final String SCORE_RANK = "score_rank";

    @Resource
    ObjectMapper objectMapper;

  /*  public static void main(String[] args) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setPushTime(new Date());
        System.out.println(JacksonFactory.me().getJson().toJson(courseInfo));
    }*/

    @Test
    public void createOss() {

    }

    @Test
    public void testRedis() {

        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setPushTime(new Date());
        JacksonFactory.me().getJson().toJson(courseInfo);
       // redisTemplate.opsForList().rightPop("test_token");

        redisTemplate.boundHashOps("token_teste").increment("id:1", 1);

        System.out.println(redisTemplate.boundHashOps("token_teste").get("id:1"));
     //   System.out.println(redisTemplate.hasKey("test_token"));
    }

    @Test
    public void redisSort() {
        Set<ZSetOperations.TypedTuple<StudentInfo>> tuples = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setId(i);
           // DefaultTypedTuple<String> tuple = new DefaultTypedTuple<String>("张三" + i, 1D + i);

            DefaultTypedTuple<StudentInfo> tuple = new DefaultTypedTuple(studentInfo, 1D + i);
            tuples.add(tuple);
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));
        Long num = redisTemplate.opsForZSet().add(SCORE_RANK, tuples);
        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + num);

        list();
    }

    @Test
    public void list() {
        Set<String> range = redisTemplate.opsForZSet().reverseRange(SCORE_RANK, 0, 10);
        System.out.println("获取到的排行列表:" + JSON.toJSONString(range));
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(SCORE_RANK, 0, 10);
        System.out.println("获取到的排行和分数列表:" + JSON.toJSONString(rangeWithScores));
    }

}
