package com.baidu.ueditor.upload;

import cn.hutool.extra.spring.SpringUtil;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.education.common.upload.FileUpload;
import com.education.common.utils.FileUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.util.Map;

public final class Base64Uploader {

	public static State save(String content, Map<String, Object> conf) {
		
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"), (String) conf.get("filename"));

		savePath = savePath + suffix;

		State storageState;
		if (FileUtils.isOpenOssUpload()) {
			FileUpload fileUpload = SpringUtil.getBean(FileUpload.class);
			fileUpload.putObject(savePath, new ByteArrayInputStream(data));
			storageState = new BaseState();
		} else {
			String physicalPath = conf.get("rootPath") + savePath;
			storageState = StorageManager.saveBinaryFile(data, physicalPath);
		}

		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}