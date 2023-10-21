package com.education.common.base;

import com.education.common.cache.CacheBean;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 11:33
 */
public abstract class BaseController {


    @Resource(name = "redisCacheBean")
    protected CacheBean cacheBean;


    protected static final Set<String> excelTypes = new HashSet<String>() {
        {
            add("application/x-xls");
            add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            add("application/vnd.ms-excel");
            add("text/xml");
        }
    };

    protected static final Set<String> textTypes = new HashSet<String>() {
        {
            add("text/plain");
        }
    };
}
