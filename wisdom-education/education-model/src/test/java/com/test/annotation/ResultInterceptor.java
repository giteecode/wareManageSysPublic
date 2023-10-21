package com.test.annotation;

import com.education.common.utils.ObjectUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;


/**
 * mybatis 返回结果拦截器
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/2 16:28
 * @since 1.0.0
 */
@Intercepts(
    {@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = {Statement.class})}
)
@Component
public class ResultInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(ResultInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
      /*  Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        // 获取到当前的Statement
        Statement stmt =  (Statement) args[0];*/
        /*if (target instanceof ResultSetHandler) {
            target = new DefaultMapResultSetHandler(stmt);
        }*/
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        try {
            if (target instanceof DefaultResultSetHandler) {
                Executor executor = ObjectUtils.getValue(target, "executor");
                MappedStatement mappedStatement = ObjectUtils.getValue(target, "mappedStatement");
                ParameterHandler parameterHandler = ObjectUtils.getValue(target, "parameterHandler");
                BoundSql boundSql = ObjectUtils.getValue(target, "boundSql");
                RowBounds rowBounds = ObjectUtils.getValue(target, "rowBounds");
                ResultHandler resultHandler = ObjectUtils.getValue(target, "resultHandler");
                ObjectFactory objectFactory = ObjectUtils.getValue(target, "objectFactory");
                return new ModelBeanResultSetHandler(executor, mappedStatement, parameterHandler,
                        resultHandler, boundSql, rowBounds, objectFactory);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
