package com.education.business.service.system;

import com.education.business.mapper.system.SystemQuartzJobMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.SystemConstants;
import com.education.common.exception.BusinessException;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.model.entity.SystemQuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemQuartzJobService extends BaseService<SystemQuartzJobMapper, SystemQuartzJob> {

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加定时任务
     * @param systemQuartzJob
     */
    public void saveQuartzJob(SystemQuartzJob systemQuartzJob) {
        try {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(systemQuartzJob.getCronExpression());
            //根据name 和group获取当前trgger 的身份
            TriggerKey triggerKey = TriggerKey.triggerKey(systemQuartzJob.getJobClassName(), SystemConstants.DEFAULT_GROUP_JOB);
            //获取 触发器的信息
            CronTrigger triggerOld = (CronTrigger) scheduler.getTrigger(triggerKey);

            if (triggerOld == null) {
                //将job加入到jobDetail中
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) ObjectUtils.getClass(systemQuartzJob.getJobClassName()))
                        .withIdentity(systemQuartzJob.getJobClassName(), SystemConstants.DEFAULT_GROUP_JOB).build();

                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(systemQuartzJob.getJobClassName(), SystemConstants.DEFAULT_GROUP_JOB)
                        .withSchedule(cronScheduleBuilder).build();
                //执行任务
                scheduler.scheduleJob(jobDetail, trigger);
            }

            // 保存定时任务
            super.save(systemQuartzJob);
        } catch (Exception e) {
            logger.error("添加定时任务异常...........", e);
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "添加定时任务异常"));
        }
    }
}
