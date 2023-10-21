package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.TestPaperInfoSettingMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.CacheKey;
import com.education.model.entity.TestPaperInfoSetting;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 试卷设置业务层
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/30 21:26
 */
@Service
public class TestPaperInfoSettingService extends BaseService<TestPaperInfoSettingMapper, TestPaperInfoSetting> {

    @Override
    @CacheEvict(cacheNames = CacheKey.PAPER_INFO_SETTING, key = "#testPaperInfoSetting.testPaperInfoId", condition = "#testPaperInfoSetting.id != null")
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(TestPaperInfoSetting testPaperInfoSetting) {
        return super.saveOrUpdate(testPaperInfoSetting);
    }

    /**
     * 获取试卷设置信息
     * @param testPaperInfoId
     * @return
     */
    @Cacheable(cacheNames = CacheKey.PAPER_INFO_SETTING, key = "#testPaperInfoId", unless = "#result == null")
    public TestPaperInfoSetting selectByTestPaperInfoId(Integer testPaperInfoId) {
        LambdaQueryWrapper queryWrapper  = Wrappers.lambdaQuery(TestPaperInfoSetting.class)
                .eq(TestPaperInfoSetting::getTestPaperInfoId, testPaperInfoId);
        return baseMapper.selectOne(queryWrapper);
    }
}
