package com.education.business.task;

import com.education.business.service.system.SystemLogService;
import com.education.business.task.param.LogTaskParam;
import com.education.common.annotation.EventQueue;
import com.education.common.constants.LocalQueueConstants;
import com.education.model.entity.SystemLog;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 异步记录系统日志，提升系统响应速度
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/24 20:54
 */
@Component
@EventQueue(name = LocalQueueConstants.REQUEST_LOG)
public class SystemLogListener implements TaskListener<LogTaskParam> {

    @Resource
    private SystemLogService systemLogService;

    @Override
    public void onMessage(LogTaskParam taskParam) {
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(taskParam.getUserId());
        systemLog.setPlatformType(taskParam.getPlatformTypeEnum().getCode());
        systemLog.setOperationName(taskParam.getOperationName());
        systemLog.setCreateDate(new Date());
        systemLog.setRequestUrl(taskParam.getRequestUrl());
        systemLog.setException(taskParam.getException());
        systemLog.setIp(taskParam.getIp());
        systemLog.setMethod(taskParam.getMethod());
        systemLog.setParams(taskParam.getParams());
        systemLog.setOperationDesc(taskParam.getOperationDesc());
        systemLogService.save(systemLog);
    }
}
