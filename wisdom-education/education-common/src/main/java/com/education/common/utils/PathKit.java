/**
 * Copyright (c) 2017-2018, zengjintao (1913188966@qq.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.education.common.utils;

import java.io.File;
import java.net.URI;

public class PathKit {

	private static String webRootPath;
	private static String rootClassPath;
	private static final String START_FILE = "jar:file:/";
	
	@SuppressWarnings("rawtypes")
	public static String getPath(Class clazz) {
		String path = clazz.getResource("").getPath();
		return new File(path).getAbsolutePath();
	}
	
	public static String getPath(Object object) {
		String path = object.getClass().getResource("").getPath();
		return new File(path).getAbsolutePath();
	}
	
	// 注意：命令行返回的是命令行所在的当前路径
	public static String getRootClassPath() {
		if (rootClassPath == null) {
			try {
				URI uri = PathKit.class.getResource("/").toURI();
				String path = uri.toString();
				if (path.startsWith("jar:file:/")) { // 获取jar路径
					path = path.substring(START_FILE.length(), path.length());
					uri = new URI(path);
				}
				// 非jar 获取路径
				String rootPath = uri.getPath();
				rootClassPath = new File(rootPath).getAbsolutePath();
				if (rootClassPath.endsWith("!")) {
                    rootClassPath = rootClassPath.substring(0, rootClassPath.length() - 1);
                }
			}
			catch (Exception e) {
				String path = PathKit.class.getClassLoader().getResource("").getPath();
				rootClassPath = new File(path).getAbsolutePath();
			}
		}
		return rootClassPath;
	}
	
	public static void setRootClassPath(String rootClassPath) {
		PathKit.rootClassPath = rootClassPath;
	}
	
	public static String getPackagePath(Object object) {
		Package p = object.getClass().getPackage();
		return p != null ? p.getName().replaceAll("\\.", "/") : "";
	}
	
	public static File getFileFromJar(String file) {
		throw new RuntimeException("Not finish. Do not use this method.");
	}
	
	public static String getWebRootPath() {
		if (webRootPath == null) {
			webRootPath = detectWebRootPath();
		}
		return webRootPath;
	}
	
	public static void setWebRootPath(String webRootPath) {
		if (webRootPath == null) {
			return ;
		}
		
		if (webRootPath.endsWith(File.separator)) {
			webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
		}
		PathKit.webRootPath = webRootPath;
	}
	
	// 注意：命令行返回的是命令行所在路径的上层的上层路径
	private static String detectWebRootPath() {
		try {
			URI uri = PathKit.class.getResource("/").toURI();
			String path = uri.toString();
			if (path.startsWith("jar:file:/")) { // 获取jar路径
				path = path.substring(START_FILE.length(), path.length());
				uri = new URI(path);
			} // 非jar 获取路径
			String rootPath = uri.getPath();
			return new File(rootPath).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isAbsolutelyPath(String path) {
		return path.startsWith("/") || path.indexOf(":") == 1;
	}
}
