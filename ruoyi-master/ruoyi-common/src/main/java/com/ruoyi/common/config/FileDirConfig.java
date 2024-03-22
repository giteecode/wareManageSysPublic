package com.ruoyi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileDirConfig {



    public static final String FILE_DOWNLOAD_URL = "/file/";
    public static final String FILE_PREVIEW_URL = "/file/preview/";

    public static String FILE_DIR ="";

    public static String FILE_TEMP = "";

    public static String FILE_GYTZ = "";

    @Value("${filedir}")
    public void setFileDir(String fileDir) {
        FILE_DIR = fileDir;
        File file = new File(fileDir);
        if (!file.exists()){
            file.mkdirs();
        }
        FILE_GYTZ = FILE_DIR + "GYTZ/";
        file = new File(FILE_GYTZ);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    @Value("${filetemp}")
    public void setFileTemp(String fileDir) {
        FILE_TEMP = fileDir;
        File file = new File(fileDir);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 创建一个新的文件
     *
     * @return
     */
    public static File getTempFile(String filename) {
        File dir = new File(FileDirConfig.FILE_TEMP);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(FileDirConfig.FILE_TEMP, filename);
        return file;
    }


    /**
     * 获取磁盘上的文件
     *
     * @param filename
     * @return
     */
    public static File getDiskFile(String dirs, String filename) {
        File dir = new File(FileDirConfig.FILE_DIR + dirs);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(FileDirConfig.FILE_DIR + dirs, filename);
        return file;
    }


}
