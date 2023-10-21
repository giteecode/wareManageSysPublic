package com.test.annotation;

import com.education.common.utils.ObjectUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于支持返回 自定义 ModelBean 的子类
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/2 16:32
 * @since 1.0.0
 */
public class ModelBeanResultSetHandler extends DefaultResultSetHandler {

    protected MappedStatement mappedStatement;
    protected ObjectFactory objectFactory;
    private static final Logger logger = LoggerFactory.getLogger(ModelBeanResultSetHandler.class);

    public ModelBeanResultSetHandler(Executor executor, MappedStatement mappedStatement,
                                     ParameterHandler parameterHandler, ResultHandler<?> resultHandler,
                                     BoundSql boundSql, RowBounds rowBounds, ObjectFactory objectFactory) {
        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        this.mappedStatement = mappedStatement;
        this.objectFactory = objectFactory;
    }

    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        final List<Object> multipleResults = new ArrayList<>();
        List<ResultMap> resultMapList = mappedStatement.getResultMaps();
        ResultSet resultSet = stmt.getResultSet();
        if (ObjectUtils.isNotEmpty(resultMapList)) {
            for (ResultMap resultMap : resultMapList) {
                Class<?> clazz = resultMap.getType();
                // 如果返回类型为非ModelBean 子类
                if (clazz.getSuperclass() != ModelBean.class) {
                    return super.handleResultSets(stmt);
                    //  throw new RuntimeException("resultType 返回值类型需要继承父类" + ModelBean.class);
                }
                handlerValue((Class<? extends ModelBean>) clazz, resultSet, multipleResults);
            }
        }
        return multipleResults;
    }

    private void handlerValue(Class<? extends ModelBean> clazz, ResultSet resultSet, List<Object> multipleResults) {

        try {
            ModelBean modelBean = objectFactory.create(clazz);
            ResultSetMetaData rs = resultSet.getMetaData();
            int columnCount = rs.getColumnCount();
            String[] columnNames = new String[columnCount];
            int[] types = new int[columnCount];
            for (int i = 0; i < columnCount; i++) {
                int number = i + 1;
                String columnName = rs.getColumnLabel(number);
                columnNames[i] = columnName;
                types[i] = rs.getColumnType(number);
            }
            while (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Object value = null;
                    int number = i + 1;
                    if (types[i] < Types.BLOB) {
                        value = resultSet.getObject(number);
                    } else {
                        if (types[i] == Types.CLOB) {
                            value = handleClob(resultSet.getClob(number));
                        } else if (types[i] == Types.NCLOB) {
                            value = handleClob(resultSet.getNClob(number));
                        } else if (types[i] == Types.BLOB) {
                            value = handleBlob(resultSet.getBlob(number));
                        } else {
                            value = resultSet.getObject(number);
                        }
                    }
                    modelBean.setAttr(columnNames[i], value);
                }
                multipleResults.add(modelBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public byte[] handleBlob(Blob blob) throws SQLException {
        if (blob == null)
            return null;

        InputStream is = null;
        try {
            is = blob.getBinaryStream();
            if (is == null)
                return null;
            byte[] data = new byte[(int)blob.length()];		// byte[] data = new byte[is.available()];
            if (data.length == 0)
                return null;
            is.read(data);
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (is != null)
                try {is.close();} catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public String handleClob(Clob clob) throws SQLException {
        if (clob == null)
            return null;

        Reader reader = null;
        try {
            reader = clob.getCharacterStream();
            if (reader == null)
                return null;
            char[] buffer = new char[(int)clob.length()];
            if (buffer.length == 0)
                return null;
            reader.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (reader != null)
                try {reader.close();} catch (IOException e) {throw new RuntimeException(e);}
        }
    }
}
