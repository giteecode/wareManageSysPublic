package com.test.annotation;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.sql.Statement;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 20:04
 */
@Intercepts(
        {@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = {Statement.class})}
)
@Component
public class MapToEntityResultInterceptor {
}
