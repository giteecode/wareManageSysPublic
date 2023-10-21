package com.education.common.constants;

/**
 * @author zengjintao
 * @create_at 2021年10月8日 0008 15:17
 * @since version 1.6.5
 */
public interface CacheTime {

    Integer MILLIS = 1000;

    Integer ONE_HOUR_SECOND = 3600;

    long THREE_HOUR_SECOND = ONE_HOUR_SECOND * 3;

    long ONE_HOUR_MILLIS = 3600 * 1000;
    Integer ONE_WEEK_SECOND = 7 * 24 * 60 * 60;
    long ONE_WEEK_MILLIS = ONE_WEEK_SECOND * 1000;

    long TEN_MINUTE_MILLIS = 10 * 60 * 1000;
    Integer TEN_MINUTE_SECOND = 10 * 60;

    Integer TWO_SECOND_MILLIS = 2 * 60 * 1000;
    Integer ONE_DAY_SECOND = 24 * 60 * 60;
}
