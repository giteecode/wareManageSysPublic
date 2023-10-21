package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 广告管理表
 * @author zjt
 * @create_at 2022年1月19日 0019 11:20
 * @since 1.0.5
 */
@TableName("advertisement")
public class Advertisement extends BaseEntity<Advertisement> {

    /**
     * 广告位置
     */
    @NotNull(message = "请选择广告位置")
    private Integer place;

    /**
     * 图片url
     */
    @TableField("img_url")
    @NotNull(message = "请至少上传一张图片")
    private String imgUrl;

    /**
     * 广告链接
     */
    @Length(max = 100)
    private String link;

    /**
     * 排序
     */
    private Integer sort;

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
