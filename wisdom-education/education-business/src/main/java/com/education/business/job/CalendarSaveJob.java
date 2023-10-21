package com.education.business.job;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.system.CalendarService;
import com.education.model.entity.Calendar;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 每天凌晨定时保存当天的日期
 * @author zjt
 * @create_at 2022年7月22日 0022 10:46
 * @since 1.0.5
 */
@Component
public class CalendarSaveJob extends BaseJob {

    private static final Logger logger = LoggerFactory.getLogger(CalendarSaveJob.class);
    @Resource
    private CalendarService calendarService;

    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("【执行定时保存当天的日期Job】");
        Date now = new Date();
        Date dayStartTime = DateUtil.beginOfDay(now);
        String day = DateUtil.format(dayStartTime, DatePattern.NORM_DATE_PATTERN);
        LambdaQueryWrapper queryWrapper = Wrappers.<Calendar>lambdaQuery()
                .eq(Calendar::getDay, day)
                .last(" limit 1");
        Calendar calendar = calendarService.getOne(queryWrapper);
        if (calendar == null) {
            calendar = new Calendar();
            calendar.setDay(day);
            calendar.setDayTime(dayStartTime.getTime());
            calendarService.save(calendar);
            logger.info("日期:{}保存成功", day);
        } else{
            logger.warn("日期:{}已存在", day);
        }
    }
}
