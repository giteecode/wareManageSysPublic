package com.education.business.task;

import com.education.business.service.system.SystemAdminService;
import com.education.business.task.param.AdminLoginTaskParam;
import com.education.common.annotation.EventQueue;
import com.education.common.constants.LocalQueueConstants;
import com.education.common.utils.Ip2regionUtil;
import com.education.model.entity.SystemAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;


/**
 * 异步监听用户登录成功消息推送
 */
@Component
@EventQueue(name = LocalQueueConstants.ADMIN_LOGIN_SUCCESS_QUEUE)
public class AdminLoginSuccessListener implements TaskListener<AdminLoginTaskParam> {

    @Resource
    private SystemAdminService systemAdminService;

    private final Logger logger = LoggerFactory.getLogger(AdminLoginSuccessListener.class);

    @Override
    public void onMessage(AdminLoginTaskParam taskParam) {
        logger.info("监听管理员登录成功业务");
        SystemAdmin systemAdmin = taskParam.getSystemAdmin();
        Integer count = systemAdmin.getLoginCount();
        systemAdmin.setLoginCount(count + 1);
        String ipAddress = Ip2regionUtil.getIpProvince(systemAdmin.getLoginIp());
        systemAdmin.setIpAddress(ipAddress);
        systemAdmin.setLastLoginTime(new Date());
        String oldIp = systemAdmin.getLoginIp();
        // IP 不一样发送警告邮件信息
        if (oldIp.equals(taskParam.getNewLoginIp())) {

        }
        systemAdminService.updateById(systemAdmin);
    }
}
