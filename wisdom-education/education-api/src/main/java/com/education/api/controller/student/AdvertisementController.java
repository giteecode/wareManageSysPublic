package com.education.api.controller.student;

import com.education.business.service.system.AdvertisementService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 广告管理接口
 * @author zjt
 * @create_at 2022年1月19日 0019 11:19
 * @since 1.0.5
 */
@RestController("student-advertisement")
public class AdvertisementController extends BaseController {

    @Resource
    private AdvertisementService advertisementService;


    /**
     * 根据位置查询列表
     * @param place
     * @return
     */
    @GetMapping("/advertisement/place/{place}")
    public Result queryByPlace(@PathVariable Integer place) {
        return Result.success(advertisementService.queryByPlace(place));
    }
}
