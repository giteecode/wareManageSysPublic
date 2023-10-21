package com.education.business.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.system.SystemRegionMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.SystemRegion;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/22 10:33
 */
@Service
public class SystemRegionService extends BaseService<SystemRegionMapper, SystemRegion> {

    public List<SystemRegion> selectRegionList(String parentCode) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(SystemRegion.class)
                .eq(SystemRegion::getParentCode, 0);
        return super.list(queryWrapper);
    }

    public List selectTreeRegion() {
        return null;
    }
}
