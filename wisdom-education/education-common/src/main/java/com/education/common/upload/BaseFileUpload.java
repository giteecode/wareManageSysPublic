package com.education.common.upload;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.education.common.config.OssProperties;


/**
 * @author zengjintao
 * @create_at 2021/10/31 15:25
 * @since version 1.0.3
 */
public abstract class BaseFileUpload implements FileUpload {

    protected String env;
    protected String applicationName;
    protected OssProperties ossProperties;
    protected String parentBucketName;
    protected String host;

    public BaseFileUpload(OssProperties ossProperties, String applicationName) {
        this(ossProperties, StrUtil.EMPTY, applicationName);
    }

    public BaseFileUpload(OssProperties ossProperties, String env, String applicationName) {
        Assert.notBlank(ossProperties.getPlatform(), () -> new RuntimeException("platform can not be null or empty!"));
        this.env = env;
        this.ossProperties = ossProperties;
        this.applicationName = applicationName;
        String bucketName = ossProperties.getBucketName();
        if (StrUtil.isBlank(bucketName)) {
            this.parentBucketName = applicationName + StrUtil.DASHED + env;
            if (StrUtil.isNotBlank(ossProperties.getAppId())) {
                this.parentBucketName += StrUtil.DASHED + ossProperties.getAppId();
            }
        } else {
            this.parentBucketName = bucketName;
        }
    }

    protected void checkOssProperty() {
        Assert.notBlank(ossProperties.getSecretId(), () -> new RuntimeException("secretId can not be null or empty!"));
        Assert.notBlank(ossProperties.getSecretKey(), () -> new RuntimeException("secretKey can not be null or empty!"));
    }

    public String getParentBucketName() {
        return parentBucketName;
    }

    protected String generateFileKey(String path) {
        if (path.endsWith(StrUtil.SLASH)) {
            return path;
        }
        return path + StrUtil.SLASH;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
