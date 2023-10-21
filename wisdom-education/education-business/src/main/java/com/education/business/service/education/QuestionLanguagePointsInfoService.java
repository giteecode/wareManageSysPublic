package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.QuestionLanguagePointsInfoMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.QuestionLanguagePointsInfo;
import org.springframework.stereotype.Service;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 15:24
 */
@Service
public class QuestionLanguagePointsInfoService extends BaseService<QuestionLanguagePointsInfoMapper, QuestionLanguagePointsInfo> {

    public boolean verificationIsUse(Integer languagePointsId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(QuestionLanguagePointsInfo.class)
                .eq(QuestionLanguagePointsInfo::getLanguagePointsInfoId, languagePointsId)
                .last(" limit 1");
        return ObjectUtils.isNotEmpty(super.getOne(queryWrapper));
    }
}
