package com.education.core.controller;

import com.education.business.service.system.CalendarService;
import com.education.business.service.system.SystemRegionService;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.common.utils.SpellUtils;
import com.education.model.entity.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 19:11
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private SystemRegionService systemRegionService;
    @Resource
    private CalendarService calendarService;

    /**
     * 获取汉字拼音
     * @param keyWord
     * @return
     */
    @GetMapping("/getSpell")
    public Result getSpell(@RequestParam(defaultValue = "") String keyWord) {
        return Result.success(SpellUtils.getSpellHeadChar(keyWord));
    }

    /**
     * 获取省份列表
     * @return
     */
    @GetMapping("selectProvinceList")
    public Result selectProvinceList() {
        return Result.success(systemRegionService.selectRegionList(String.valueOf(ResultCode.FAIL)));
    }

    public Result selectTreeRegion() {
        return null;
    }

    /**
     * 根据父类id获取地区列表
     * @return
     */
    @GetMapping("selectRegionListByParentId")
    public Result selectRegionListByParentCode(String parentCode) {
        return Result.success(systemRegionService.selectRegionList(parentCode));
    }


    /**
     * 初始化日期表接口
     */
    @PostMapping("/calendar/init")
    public Result init() {
        calendarService.init();
        return Result.success();
    }
}
