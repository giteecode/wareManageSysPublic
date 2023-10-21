package com.education.business.job;

import com.education.business.mapper.education.TestPaperInfoMapper;
import com.education.common.component.SpringBeanManager;
import com.education.common.constants.CacheKey;
import com.education.common.constants.SystemConstants;
import com.education.common.utils.ObjectUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

public class ExamCountJob extends BaseJob {

    private final Logger logger = LoggerFactory.getLogger(ExamCountJob.class);

    /**
     * 定时同步试卷考试人数到mysql 数据库 (每隔5分钟执行一次)
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        RedisTemplate redisTemplate = SpringBeanManager.getBean(SystemConstants.REDIS_TEMPLATE_BEAN_NAME);
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(CacheKey.EXAM_MONITOR_CACHE_KEY);
        Set<Integer> testPaperIds = boundHashOperations.keys();
        TestPaperInfoMapper testPaperInfoMapper = SpringBeanManager.getBean(TestPaperInfoMapper.class);
        if (ObjectUtils.isNotEmpty(testPaperIds)) {
            testPaperIds.forEach(id -> {
                Integer examNumber = (Integer) boundHashOperations.get(id);
                if (examNumber != null && examNumber > 0) {
                    boolean success = testPaperInfoMapper.updateTestPaperExamNumber(id, examNumber);
                    if (success) {
                        try {
                            boundHashOperations.increment(id, -examNumber); // 缓存中减去去除的数量
                        } catch (Exception e) {
                            logger.error("redis 数据更新异常, 更新考试人数:[" + examNumber + "]", e);
                            // redis 数据更新失败 进行数据库还原
                            testPaperInfoMapper.updateTestPaperExamNumber(id, -examNumber);
                        }
                    }
                }
            });
        }
    }
}
