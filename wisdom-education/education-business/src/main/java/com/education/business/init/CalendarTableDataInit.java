package com.education.business.init;
import com.education.business.service.system.CalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


/**
 * 初始化日期表
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/29 15:14
 */
@Component
public class CalendarTableDataInit implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CalendarTableDataInit.class);
    @Resource
    private CalendarService calendarService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = calendarService.count();
        if (count == 0) {
            calendarService.init();
            logger.info("calendar 表数据初始化成功");
        }
    }
}
