package com.education.api.controller.admin.education;

import com.education.business.service.education.TestPaperInfoSettingService;
import com.education.common.utils.Result;
import com.education.model.entity.TestPaperInfoSetting;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 试卷设置接口
 * @author zjt
 * @create_at 2022年1月17日 0017 11:05
 * @since 1.0.5
 */
@RestController
@RequestMapping("/testPaper")
public class TestPaperSettingController {

    @Resource
    private TestPaperInfoSettingService testPaperInfoSettingService;

    /**
     * 保存修改试卷设置
     * @param testPaperInfoSetting
     * @return
     */
    @PostMapping("setting")
    public Result saveOrUpdate(@RequestBody @Validated TestPaperInfoSetting testPaperInfoSetting) {
        testPaperInfoSettingService.saveOrUpdate(testPaperInfoSetting);
        return Result.success();
    }

    /**
     * 获取试卷设置信息
     * @param testPaperInfoId
     * @return
     */
    @GetMapping("/{testPaperInfoId}/setting")
    public Result selectByTestPaperId(@PathVariable Integer testPaperInfoId) {
        return Result.success(testPaperInfoSettingService.selectByTestPaperInfoId(testPaperInfoId));
    }
}
