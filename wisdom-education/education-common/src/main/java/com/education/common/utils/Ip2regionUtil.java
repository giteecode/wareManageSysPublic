package com.education.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.education.common.model.Ip2region;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author zjt
 * @create_at 2022年9月9日 0009 09:24
 * @since 2.1
 */
public class Ip2regionUtil {

    private static final Logger LOG = LoggerFactory.getLogger(Ip2regionUtil.class);
    private static final int IP2_REGION_SIZE = 5; // 解析ip 地址返回数组长度
    private static final String XDB_FILE_NAME = "ip2region.xdb";
    private static final String XDB_FILE_PATH;
    private static final Searcher searcher;


    static {
        try {
            String resourceLocation = SpringUtil.getProperty("ip2region.xdb.path");
            Assert.notBlank(resourceLocation, () -> new RuntimeException("ip2region.xdb.path Value Cant Not Be Null"));
            InputStream inputStream;
            if (resourceLocation.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
                XDB_FILE_PATH = resourceLocation.substring(ResourceUtils.CLASSPATH_URL_PREFIX.length());
                inputStream = new ClassPathResource(XDB_FILE_PATH).getInputStream();
            } else {
                XDB_FILE_PATH = resourceLocation;
                inputStream = FileUtil.getInputStream(XDB_FILE_PATH);
            }
            searcher = Searcher.newWithBuffer(IoUtil.readBytes(inputStream));
        } catch (IOException e) {
            LOG.error("【Ip2region】ip2region.xdb文件加载失败, 请检查属性配置ip2region.xdb.path", XDB_FILE_NAME);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取ip 归属地
     * @param ip
     * @return
     */
    public static Ip2region getIpRegion(String ip) {
        try {
            String region = searcher.searchByStr(ip);
            if (StrUtil.isBlank(region)) {
                LOG.warn("【Ip2region】获取ip:{}归属地失败", ip);
                return null;
            }
            String[] regionArray = region.split("\\|");
            if (regionArray.length < IP2_REGION_SIZE) {
                regionArray = Arrays.copyOf(regionArray, IP2_REGION_SIZE); // 默认补齐五位
            }
            String country = regionArray[0];
            String city = regionArray[3];
            String province = regionArray[2];
            return new Ip2region(country, province, city);
        } catch (IOException e) {
            LOG.error("【Ip2region】Failed To Create VectorIndex Cached Searcher With:{}", XDB_FILE_PATH, e);
        } catch (Exception e) {
            LOG.error("【Ip2region】获取ip:{}归属地异常", ip, e);
        }
        return null;
    }

    /**
     * 获取ip 地址归属地省份
     * 返回省份+city
     * @param ip
     * @return
     */
    public static String getIpProvince(String ip) {
        Ip2region ip2region = getIpRegion(ip);
        if (ip2region == null) {
            return "未知";
        }
        return ip2region.getProvince();
    }

    public static String getIpProvinceAndCity(String ip) {
        Ip2region ip2region = getIpRegion(ip);
        if (ip2region == null) {
            return "未知";
        }
        return ip2region.getProvince() + ip2region.getCity();
    }
}
