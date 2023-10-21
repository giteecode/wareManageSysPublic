package com.education.api.controller.admin.system;

import com.education.business.service.system.AdvertisementService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.Advertisement;
import com.education.model.request.PageParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 广告管理接口
 * @author zjt
 * @create_at 2022年1月19日 0019 11:19
 * @since 1.0.5
 */
@RestController
public class AdvertisementController extends BaseController {

    @Resource
    private AdvertisementService advertisementService;

    /**
     * 广告列表
     * @param pageParam
     * @return
     */
    @GetMapping("/advertisement")
    public Result listPage(PageParam pageParam, Advertisement advertisement) {
        return Result.success(advertisementService.selectPage(pageParam, advertisement));
    }

    /**
     * 添加修改广告
     * @param advertisement
     * @return
     */
    @PostMapping("/advertisement")
    public Result saveOrUpdate(@RequestBody @Validated Advertisement advertisement) {
        advertisementService.saveOrUpdate(advertisement);
        return Result.success();
    }

    /**
     * 根据id 删除广告
     * @param id
     * @return
     */
    @DeleteMapping("/advertisement/{id}")
    public Result deleteById(@PathVariable Integer id) {
        advertisementService.removeById(id);
        return Result.success();
    }
}
