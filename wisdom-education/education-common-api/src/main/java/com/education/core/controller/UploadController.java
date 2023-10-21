package com.education.core.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.education.business.service.education.CourseSectionNodeService;
import com.education.common.enums.FileTypeEnum;
import com.education.common.exception.BusinessException;
import com.education.common.upload.FileUpload;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 文件上传接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 19:06
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Resource
    private FileUpload fileUpload;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private CourseSectionNodeService courseSectionNodeService;

    private static final Set<String> videoTypes = new HashSet<String>() {
        {
            add("video/mp4");
            add("video/x-ms-wmv");
            add("video/mpeg4");
            add("video/avi");
            add("video/mpeg");
            add("video/3gp");
        }
    };

    /**
     * 文件上传api 接口
     * @param file
     * @param uploadFileType
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "{uploadFileType}", method = {RequestMethod.GET, RequestMethod.POST})
    public Map uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer uploadFileType) {
        Assert.notNull(uploadFileType, () -> new BusinessException("the param uploadFileType can not be null"));
        String fileName = file.getOriginalFilename();
        String suffix = StrUtil.DOT + FilenameUtils.getExtension(fileName);
        Map resultMap = new HashMap<>();
        FileTypeEnum fileTypeEnum = FileTypeEnum.getFileTypeEnumByCode(uploadFileType);
        Assert.notNull(fileTypeEnum, () -> new BusinessException("{uploadFileType}路径参数错误"));
        String prefix = fileTypeEnum.getPrefix();
        String filePath = prefix + ObjectUtils.generateFileByTime();
        String newFileName = ObjectUtils.generateFileBySecond()
                + StrUtil.DASHED + ObjectUtils.generateUuId() + suffix;
        String fileType = fileTypeEnum.getValue();
        try {
            InputStream inputStream = file.getInputStream();
            fileUpload.putObject(filePath, newFileName, inputStream);
            resultMap.put("code", ResultCode.SUCCESS);
            resultMap.put("message", fileType + "上传成功");
            resultMap.put("url", filePath + newFileName);

            // 文件路径加入缓存 每天定时删除上传未使用的图片
           // String url = filePath + newFileName;
           // redisTemplate.boundListOps(CacheKey.UPLOAD_FILE).rightPush(url);
        } catch (Exception e) {
            resultMap.put("code", ResultCode.FAIL);
            resultMap.put("message", fileType + "文件上传失败");
            logger.error(fileType + "上传失败", e);
        }
        return resultMap;
    }
}
