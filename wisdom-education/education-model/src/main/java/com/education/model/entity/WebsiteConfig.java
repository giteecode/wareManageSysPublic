package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 网站配置表
 * @author zengjintao
 * @create_at 2021年11月2日 0002 17:53
 * @since version 1.0.4
 */
@TableName("website_config")
public class WebsiteConfig extends BaseEntity<WebsiteConfig> {

    /**
     * 网站logo
     */
    @TableField("logo_url")
    @NotBlank(message = "请上传网站logo")
    private String logoUrl;

    /**
     * 网站轮播图(多个以逗号隔开)
     */
    @NotBlank(message = "请上传网站轮播图")
    @TableField("carousel_image")
    private String carouselImage;

    /**
     * 平台类型
     */
    @NotNull(message = "平台类型不能为空")
    private Integer platform;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCarouselImage() {
        return carouselImage;
    }

    public void setCarouselImage(String carouselImage) {
        this.carouselImage = carouselImage;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
}
