package com.baidu.ueditor.upload;

import cn.hutool.extra.spring.SpringUtil;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.education.common.enums.OssPlatformEnum;
import com.education.common.upload.FileUpload;
import com.education.common.utils.FileUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {

	public static final State save(HttpServletRequest request,
                                   Map<String, Object> conf) {
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
			if (multipartFile == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
			String savePath = (String) conf.get("savePath");
			String originFileName = multipartFile.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);
			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;
			long maxSize = ((Long) conf.get("maxSize")).longValue();
			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			savePath = PathFormat.parse(savePath, originFileName);
			boolean flag = FileUtils.isOpenOssUpload();
			State storageState;
			if (!flag) {
				String physicalPath = conf.get("basePath") + savePath;
				InputStream is = multipartFile.getInputStream();
				storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
				is.close();
			} else {
				storageState = new BaseState();
				uploadOssFile(savePath, multipartFile);
			}

			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}
			return storageState;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static void uploadOssFile(String file, MultipartFile multipartFile) throws IOException {
		FileUpload fileUpload = SpringUtil.getBean(FileUpload.class);
		fileUpload.putObject(file, multipartFile.getInputStream());
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);
		return list.contains(type);
	}
}
