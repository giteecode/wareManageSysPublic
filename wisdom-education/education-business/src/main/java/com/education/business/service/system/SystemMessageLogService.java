package com.education.business.service.system;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.system.MessageLogMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.MessageLog;
import org.springframework.stereotype.Service;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/15 14:01
 */
@Service
public class SystemMessageLogService extends BaseService<MessageLogMapper, MessageLog> {


    public MessageLog selectByMessageId(String messageId) {
        return baseMapper.selectOne(Wrappers.lambdaQuery(MessageLog.class)
                .eq(MessageLog::getCorrelationDataId, messageId));
    }
}
