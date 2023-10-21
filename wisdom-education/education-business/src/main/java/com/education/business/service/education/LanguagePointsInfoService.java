package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.LanguagePointsInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.model.dto.LanguagePointsInfoDto;
import com.education.model.entity.LanguagePointsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识点管理service
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 15:10
 */
@Service
public class LanguagePointsInfoService extends BaseService<LanguagePointsInfoMapper, LanguagePointsInfo> {

    @Autowired
    private QuestionLanguagePointsInfoService questionLanguagePointsInfoService;

    /**
     * 获取一级知识点
     * @param languagePointsInfo
     * @return
     */
    public List<LanguagePointsInfoDto> selectList(LanguagePointsInfo languagePointsInfo) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(LanguagePointsInfo.class)
                .eq(LanguagePointsInfo::getParentId, languagePointsInfo.getParentId())
                .eq(ObjectUtils.isNotEmpty(languagePointsInfo.getSubjectId()),
                        LanguagePointsInfo::getSubjectId, languagePointsInfo.getSubjectId())
                .like(ObjectUtils.isNotEmpty(languagePointsInfo.getName()),
                        LanguagePointsInfo::getName, languagePointsInfo.getName())
                .orderByDesc(LanguagePointsInfo::getSort);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(LanguagePointsInfo languagePointsInfo) {
        boolean flag = super.saveOrUpdate(languagePointsInfo);
        if (flag) {
            if (languagePointsInfo.getParentId() != 0) {
                LambdaUpdateWrapper updateWrapper = Wrappers.<LanguagePointsInfo>lambdaUpdate()
                        .set(LanguagePointsInfo::getHasChildren, true)
                        .eq(LanguagePointsInfo::getId, languagePointsInfo.getParentId());
                super.update(updateWrapper);
            }
        }
        return flag;
    }

    public ResultCode deleteById(Integer id) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(LanguagePointsInfo.class)
                .eq(LanguagePointsInfo::getParentId, id);
        LanguagePointsInfo languagePointsInfo = super.getOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(languagePointsInfo)) {
            return new ResultCode(ResultCode.FAIL, "存在子节点,无法删除");
        }

        // 验证知识点是否有试题使用
        boolean flag = questionLanguagePointsInfoService.verificationIsUse(id);
        if (flag) {
            return new ResultCode(ResultCode.FAIL, "知识点已有试题使用,无法删除");
        }
        super.removeById(id);
        return new ResultCode(ResultCode.SUCCESS, "删除成功");
    }
}
