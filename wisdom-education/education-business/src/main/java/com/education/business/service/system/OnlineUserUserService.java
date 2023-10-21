package com.education.business.service.system;

import cn.hutool.core.collection.CollUtil;
import com.education.auth.AuthUtil;
import com.education.auth.session.UserSession;
import com.education.business.session.AdminUserSession;
import com.education.common.model.PageInfo;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/5/14 16:18
 */
@Service
public class OnlineUserUserService {

    /**
     * 在线用户分页列表
     * @param pageParam
     * @return
     */
    public PageInfo<AdminUserSession> getOnlineUserList(PageParam pageParam, String loginType) {
        List<UserSession> userSessionList = AuthUtil.getSessionStorage().getActiveSessions(loginType);
        List<UserSession> data = CollUtil.page(pageParam.getPageNumber() - 1,
                pageParam.getPageSize(), userSessionList);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal(userSessionList.size());
        pageInfo.setDataList(data);
        return pageInfo;
    }
}
