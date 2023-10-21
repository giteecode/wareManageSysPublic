package com.education.common.upload;

import java.io.File;
import java.io.InputStream;

/**
 * @author zengjintao
 * @create_at 2021/10/31 14:51
 * @since version 1.0.3
 */
public interface FileUpload {

    /**
     * 创建桶
     * @param name
     * @return
     */
    UploadResult createBucket(String name);

    UploadResult putObject(String filePath, InputStream inputStream);

    UploadResult putObject(String filePath, String fileName, File file);

    UploadResult putObject(String bucket, String filePath, String fileName, File file);

    UploadResult putObject(String filePath, String fileName, InputStream inputStream);

    UploadResult putObject(String bucket, String filePath, String fileName, InputStream inputStream);

    void deleteObject(String filePath);

    void deleteObject(String bucket, String filePath);
}
