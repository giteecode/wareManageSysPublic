package com.education.business.interceptor;



import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * mybatis sql 执行监控
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/5 10:35
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})})
public class MybatisSqlMonitor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisSqlMonitor.class);

    private static final String title = "\nMybatis Sql" + " take time report -------- ";
    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder(title).append(sdf.get().format(new Date())).append(" --------------------------\n");
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql().replace("\n", "").replace("\t", "").replaceAll("\\s+", " ");
            sb.append("Sql          : ").append(sql).append("\n");
            sb.append("Sql执行时间  :------------------------" +  (endTime - startTime) + "ms---------------------------------");
            logger.info(sb.toString());
        }
    }
}
