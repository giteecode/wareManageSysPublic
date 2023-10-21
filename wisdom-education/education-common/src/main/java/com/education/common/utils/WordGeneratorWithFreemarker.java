package com.education.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @Description:word导出帮助类 通过freemarker模板引擎来实现
 * @author:LiaoFei
 * @date :2016-3-24 下午3:49:25
 * @version V1.0
 * 
 */
public class WordGeneratorWithFreemarker {
	private static Configuration configuration = null;

	private WordGeneratorWithFreemarker() {

	}

	public static void createDoc(Map<String, Object> dataMap,String templateName, OutputStream out)throws Exception {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassicCompatible(true);
		configuration.setClassForTemplateLoading(
				WordGeneratorWithFreemarker.class,
				"/template");
		Template t = configuration.getTemplate(templateName);
		WordHtmlGeneratorHelper.handleAllObject(dataMap);

		try {
			Writer w = new OutputStreamWriter(out, Charset.forName("utf-8"));
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}


}