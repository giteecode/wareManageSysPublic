package com.education.common.template;

import cn.hutool.core.io.FileUtil;
import com.education.common.exception.BusinessException;
import com.education.common.upload.FileUpload;
import com.education.common.utils.FileUtils;
import com.education.common.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public abstract class BaseTemplate {

    private final Logger logger = LoggerFactory.getLogger(BaseTemplate.class);

    protected String template;
    protected String outputDir;

    public BaseTemplate(String template, String outputDir) {
        this.template = template;
        this.outputDir = outputDir;
    }

    public void generateTemplate(Map data, String fileName) {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String target = outputDir + File.separator + fileName;
        File file = new File(target);
        String serverUrl = RequestUtils.getDomain();
        data.put("serverUrl", serverUrl);
        this.writeToFile(data, file);
    }

    /**
     * 文件上传oss
     * @param data
     * @param fileName
     * @param fileUpload
     * @since 1.0.4
     */
    public void generateTemplateToOss(Map data, String filePath, String fileName, FileUpload fileUpload) {
       this.generateTemplate(data, fileName);
        File file = new File(outputDir + fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileUpload.putObject(filePath, fileName, fileInputStream);
        } catch (Exception e) {
            logger.error("文件上传oss异常", e);
            throw new BusinessException("系统异常");
        } finally {
            FileUtil.del(file); // 上传之后删除文件
        }
    }

    /**
     * 文件保存本地
     * @param data
     * @param outPutFile
     */
    protected abstract void writeToFile(Map data, File outPutFile);
}
