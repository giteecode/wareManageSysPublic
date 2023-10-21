package com.education.model.dto;

import com.education.model.entity.WebsiteConfig;
import java.util.List;

/**
 * @author zengjintao
 * @create_at 2021年11月5日 0005 11:31
 * @since version 1.6.5
 */
public class WebsiteConfigDto extends WebsiteConfig {

    private List<String> carouselImageList;

    public void setCarouselImageList(List<String> carouselImageList) {
        this.carouselImageList = carouselImageList;
    }

    public List<String> getCarouselImageList() {
        return carouselImageList;
    }
}
