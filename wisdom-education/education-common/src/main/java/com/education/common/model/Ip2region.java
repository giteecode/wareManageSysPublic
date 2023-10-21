package com.education.common.model;

/**
 * @author zjt
 * @create_at 2022年9月9日 0009 09:47
 * @since 1.0.6
 */
public class Ip2region {

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 市区
     */
    private String city;


    public Ip2region() {

    }

    public Ip2region(String country, String province, String city) {
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Ip2region{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
