package com.education.common.component;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.education.common.annotation.Unique;
import com.education.common.utils.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Lazy(false)
public class ModelUniqueManager implements ApplicationContextAware {

    private static final Map<Class<?>, List<UniqueValueInfo>> uniqueCache = new ConcurrentHashMap();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initEntityUniqueField();
    }

    /**
     * 初始化Unique 注解
     */
    private void initEntityUniqueField() {
        List<TableInfo> tableInfoList = TableInfoHelper.getTableInfos();
        if (tableInfoList != null) {
            tableInfoList.forEach(table -> {
                List<UniqueValueInfo> uniqueFieldList = new ArrayList<>();
                List<TableFieldInfo> fieldList = table.getFieldList();
                fieldList.forEach(tableFieldInfo -> {
                    Field field = tableFieldInfo.getField();
                    Unique unique = field.getAnnotation(Unique.class);
                    if (ObjectUtils.isNotEmpty(unique)) {
                        String column = ObjectUtils.isEmpty(unique.value()) ? field.getName() : unique.value();
                        uniqueFieldList.add(new UniqueValueInfo(field, column));
                    }
                });
                uniqueCache.put(table.getEntityType(), uniqueFieldList);
            });
        }
    }

    public static List<UniqueValueInfo> getUniqueField(Class<?> entityClass) {
        return uniqueCache.get(entityClass);
    }

}
