package com.education.business.service.system;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.education.business.mapper.system.CalendarMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.Calendar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zjt
 * @create_at 2022年7月26日 0026 09:56
 * @since 1.0.5
 */
@Service
public class CalendarService extends BaseService<CalendarMapper, Calendar>  {

    @Transactional(rollbackFor = Exception.class)
    public void init() {
        Date now = new Date();
        DateTime starYear = DateUtil.offset(now, DateField.YEAR, -2);
        DateTime yearStarTime = DateUtil.beginOfYear(starYear); // 获取2020年的开始日期
        // 从2020年1月1日到当前时间的日期数组
        List<DateTime> dateTimeList = DateUtil.rangeToList(yearStarTime, now, DateField.DAY_OF_YEAR);
        List<String> dayList = dateTimeList.stream()
                .map(item -> DateUtil.format(item, DatePattern.NORM_DATE_FORMAT))
                .collect(Collectors.toList());
        List<Calendar> list = new ArrayList<>();
        dayList.forEach(day -> {
            Date date = DateUtil.parse(day + " 00:00:00", DatePattern.NORM_DATETIME_FORMAT);
            Calendar calendar = new Calendar();
            calendar.setDay(day);
            calendar.setDayTime(date.getTime());
            list.add(calendar);
        });
        saveBatch(list);
    }
}
