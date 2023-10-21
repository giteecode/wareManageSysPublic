package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.MessageInfoMapper;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.common.enums.BooleanEnum;
import com.education.common.model.PageInfo;
import com.education.model.entity.MessageInfo;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/13 14:38
 */
@Service
public class MessageInfoService extends BaseService<MessageInfoMapper, MessageInfo> {

    public PageInfo<MessageInfo> selectList(PageParam pageParam) {
        Integer studentId = UserSessionContext.getStudentId();
        // 将未读消息设置为已读状态
        LambdaUpdateWrapper<MessageInfo> updateWrapper = Wrappers.lambdaUpdate(MessageInfo.class)
                .set(MessageInfo::getReadFlag, BooleanEnum.YES.getCode())
                .set(MessageInfo::getUpdateDate, new Date())
                .eq(MessageInfo::getStudentId, studentId);
        super.update(updateWrapper);
        return super.selectPage(pageParam, Wrappers.lambdaQuery(MessageInfo.class)
                .eq(MessageInfo::getStudentId, studentId)
               .orderByDesc(MessageInfo::getId));
    }

    public long getUnReadMessageCount() {
        return baseMapper.countUnReadMessage(UserSessionContext.getStudentId());
    }
}
