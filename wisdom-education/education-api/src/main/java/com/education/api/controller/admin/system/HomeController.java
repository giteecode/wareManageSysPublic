package com.education.api.controller.admin.system;

import com.education.business.service.system.HomeService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 首页数据接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/2/16 16:07
 */
@RequestMapping("/home")
@RestController
public class HomeController extends BaseController {

    @Resource
    private HomeService homeService;

    @GetMapping("headDataCount")
    public Result count() {
        return Result.success(homeService.countData());
    }
}
