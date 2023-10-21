package com.education.business.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.AdvertisementMapper;
import com.education.business.service.BaseService;
import com.education.common.enums.AdvertisementPlaceEnum;
import com.education.common.exception.BusinessException;
import com.education.model.entity.Advertisement;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告管理
 * @author zjt
 * @create_at 2022年1月19日 0019 11:26
 * @since 1.0.5
 */
@Service
public class AdvertisementService extends BaseService<AdvertisementMapper, Advertisement> {

    public List<Advertisement> queryByPlace(Integer place) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(Advertisement.class)
                .eq(Advertisement::getPlace, place);
        return super.list(queryWrapper);
    }

    @Override
    public boolean saveOrUpdate(Advertisement advertisement) {
        Integer place = advertisement.getPlace();
        int advertisementNumber = countByPlace(place);
        AdvertisementPlaceEnum advertisementPlaceEnum = AdvertisementPlaceEnum.getByCode(place);
        if (advertisementNumber >= advertisementPlaceEnum.getMaxNumber()) {
            throw new BusinessException("当前位置广告最多允许添加" + advertisementPlaceEnum.getMaxNumber() + "条");
        }
        return super.saveOrUpdate(advertisement);
    }

    public int countByPlace(Integer place) {
        int advertisementNumber = count(Wrappers.lambdaQuery(Advertisement.class)
                .eq(Advertisement::getPlace, place));
        return advertisementNumber;
    }
}
