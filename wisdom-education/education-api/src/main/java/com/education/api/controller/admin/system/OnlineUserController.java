package com.education.api.controller.admin.system;

import com.education.business.service.system.OnlineUserUserService;
import com.education.common.annotation.SystemLog;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.entity.SystemAdmin;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/5/14 16:17
 */
@RestController
@RequestMapping("/online")
public class OnlineUserController {

    @Resource
    private OnlineUserUserService onlineUserUserService;
    /**
     * 获取在线用户
     * @return
     */
    @SystemLog(describe = "获取在线用户列表")
    @GetMapping
    public Result<PageInfo<SystemAdmin>> getOnlineUserList(PageParam pageParam, String loginType) {
        return Result.success(onlineUserUserService.getOnlineUserList(pageParam, loginType));
    }
}
