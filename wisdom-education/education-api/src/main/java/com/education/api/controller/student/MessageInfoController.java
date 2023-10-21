package com.education.api.controller.student;

import com.education.business.service.education.MessageInfoService;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.entity.MessageInfo;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 消息通知接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/13 14:35
 */
@RestController
@RequestMapping("/message")
public class MessageInfoController extends BaseController {

    @Resource
    private MessageInfoService messageInfoService;

    @GetMapping
    public Result<PageInfo<MessageInfo>> list(PageParam pageParam) {
        return Result.success(messageInfoService.selectList(pageParam));
    }

    /**
     * 获取未读消息数量
     * @return
     */
    @GetMapping("/unReadNum")
    public Result getUnReadMessageCount() {
        return Result.success(messageInfoService.getUnReadMessageCount());
    }
}
