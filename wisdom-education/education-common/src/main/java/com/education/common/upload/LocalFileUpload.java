package com.education.common.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import com.education.common.config.OssProperties;
import com.jfinal.kit.FileKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.InputStream;

/**
 * @author zengjintao
 * @create_at 2021年11月3日 0003 13:43
 * @since version 1.6.5
 */
public class LocalFileUpload extends BaseFileUpload {

    private final Logger logger = LoggerFactory.getLogger(LocalFileUpload.class);

    private String uploadPath;

    public LocalFileUpload(OssProperties ossProperties, String applicationName) {
        super(ossProperties, applicationName);
        this.uploadPath = super.getParentBucketName();
        this.createBucket(uploadPath);
    }

    public LocalFileUpload(OssProperties ossProperties, String env, String applicationName) {
        super(ossProperties, env, applicationName);
        this.uploadPath = super.getParentBucketName();
        this.createBucket(uploadPath);
    }


    @Override
    public UploadResult createBucket(String name) {
        Assert.notBlank(ossProperties.getBucketName(), () -> new RuntimeException("BucketName can not be null or empty"));
        try {
            File file = new File(name);
            if (!file.exists()) {
                file.mkdirs();
            }
            return new UploadResult();
        } catch (Exception e) {
            logger.error("{}:文件创建失败....", name, e);
            return null;
        }
    }

    @Override
    public UploadResult putObject(String filePath, InputStream inputStream) {
        FileUtil.writeFromStream(inputStream, new File(uploadPath + filePath));
        return new UploadResult(host + filePath);
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, File file) {
        return null;
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, File file) {
        return null;
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, InputStream inputStream) {
        FileUtil.writeFromStream(inputStream, new File(uploadPath + filePath + fileName));
        return new UploadResult(host + filePath);
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, InputStream inputStream) {
        return null;
    }

    @Override
    public void deleteObject(String filePath) {
        FileKit.delete(new File(filePath));
    }

    @Override
    public void deleteObject(String bucket, String filePath) {

    }
}
