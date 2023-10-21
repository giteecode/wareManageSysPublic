package com.education.common.upload;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.education.common.config.OssProperties;
import com.education.common.constants.SystemConstants;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 腾讯oss 上传
 * @author zengjintao
 * @create_at 2021/10/31 14:52
 * @since version 1.0.3
 */
public class TencentOssFileUpload extends BaseFileUpload {

    private final Logger logger = LoggerFactory.getLogger(TencentOssFileUpload.class);

    private static String PASSWORD_KEY = null;

    public TencentOssFileUpload(OssProperties ossProperties, String env, String applicationName) {
        super(ossProperties, env, applicationName);
        super.checkOssProperty();
        String host = getHost();
        if (StrUtil.isBlank(host)) {
           super.setHost("https://" + parentBucketName);
        }
        boolean flag = getCOSClient().doesBucketExist(this.parentBucketName);
        if (!flag) {
            this.createBucket(parentBucketName);
        }
    }

    private COSClient getCOSClient() {
//        if (PASSWORD_KEY == null) {
//            ApplicationArguments applicationArguments = SpringUtil.getBean(ApplicationArguments.class);
//            List<String> keys = applicationArguments.getOptionValues(SystemConstants.PASSWORD_KEY);
//            if (CollUtil.isEmpty(keys)) {
//                throw new RuntimeException("请配置java 启动类参数（idea 设置 Program arguments , 服务器可以设置为启动环境变量）");
//            }
//            PASSWORD_KEY = keys.get(0);
//        }

        //String secretId = AES.decrypt(ossProperties.getSecretId(), PASSWORD_KEY);
        String secretId = ossProperties.getSecretId();
        //String secretKey = AES.decrypt(ossProperties.getSecretKey(), PASSWORD_KEY);
        String secretKey = ossProperties.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(ossProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cred, clientConfig);
    }


    @Override
    public UploadResult createBucket(String name) {
        COSClient cosClient = getCOSClient();
        try {
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(name);
            createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
            cosClient.createBucket(createBucketRequest);
            return new UploadResult();
        } catch (Exception e) {
            logger.error("{}:桶创建失败...", name, e);
            return null;
        } finally {
            closeClient(cosClient);
        }
    }

    private void closeClient(COSClient cosClient) {
        if (cosClient != null) {
            cosClient.shutdown();
        }
    }

    @Override
    public UploadResult putObject(String file, InputStream inputStream) {
        COSClient cosClient = getCOSClient();
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            cosClient.putObject(parentBucketName, file, inputStream, objectMetadata);
            String fileUrl = getHost() + file;
            return new UploadResult(fileUrl);
        } finally {
            if (inputStream != null) {
                try { inputStream.close();} catch (IOException e) { e.printStackTrace();}
            }
            closeClient(cosClient);
        }
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, File file) {
        return this.putObject(parentBucketName, filePath, fileName, file);
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, File file) {
        COSClient cosClient = getCOSClient();
        try {
            String fileKey = super.generateFileKey(filePath) + fileName;
            cosClient.putObject(bucket, fileKey, file);
            String fileUrl = getHost() + fileKey;
            return new UploadResult(fileUrl);
        } finally {
            this.closeClient(cosClient);
        }
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, InputStream inputStream) {
        return this.putObject(parentBucketName, filePath, fileName, inputStream);
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, InputStream inputStream) {
        COSClient cosClient = getCOSClient();
        try {
            String fileKey = super.generateFileKey(filePath) + fileName;
            ObjectMetadata objectMetadata = new ObjectMetadata();
            cosClient.putObject(bucket, fileKey, inputStream, objectMetadata);
            String fileUrl = super.getHost() + fileKey;
            return new UploadResult(fileUrl);
        } finally {
            if (inputStream != null) {
                try { inputStream.close();} catch (IOException e) { e.printStackTrace();}
            }
            closeClient(cosClient);
        }
    }


    @Override
    public void deleteObject(String filePath) {
        this.deleteObject(parentBucketName, filePath);
    }

    @Override
    public void deleteObject(String bucket, String filePath) {
        COSClient cosClient = getCOSClient();
        try {
            cosClient.deleteObject(bucket, filePath);
        } finally {
            closeClient(cosClient);
        }
    }
}
