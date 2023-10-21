package com.education.common.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * oss文件上传属性配置
 * @author zengjintao
 * @create_at 2021/10/31 15:30
 * @since version 1.0.3
 */
@ConfigurationProperties("oss.upload")
@Component
public class OssProperties {

    private String host;
    private String secretId;
    private String secretKey;
    private String platform;
    private String appId;
    private String region;

    private String bucketName;

    /**
     * 部分文件临时上传路径
    */
    private String localTmpFileUploadPath;


    public void setLocalTmpFileUploadPath(String localTmpFileUploadPath) {
        this.localTmpFileUploadPath = localTmpFileUploadPath;
    }

    public String getLocalTmpFileUploadPath() {
        return localTmpFileUploadPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
