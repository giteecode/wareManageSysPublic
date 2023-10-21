package com.test.annotation;

import com.education.common.utils.ObjectUtils;
import com.google.common.collect.Lists;
import com.jfinal.kit.PathKit;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * model类生成工具类
 * @author zengjintao
 *  @version 1.0
 * @create_at 2017年8月22日
 */
public class ModelGenerate {

	private String packageName;
	
	private DataSource dataSource;
	
	public static final String DEFAULT = "/src/main/java/";
	
	private static final List<String> tables = Lists.newArrayList();
	
	public ModelGenerate(String packageName, DataSource dataSource){
		this.packageName = DEFAULT + packageName;
		this.dataSource = dataSource;
	}

	public void create(){
		long start = System.currentTimeMillis();
		try {
			Connection connection = dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});	// 不支持 view 生成
			while (resultSet.next()) {
				tables.add(resultSet.getString("TABLE_NAME"));
			}
			for (Iterator<String> iterator = tables.iterator(); iterator.hasNext();) {
				String tableName = iterator.next();
				PreparedStatement pt = connection.prepareStatement("select * from " + tableName);
			    ResultSetMetaData metaData= pt.getMetaData();
			    int count = metaData.getColumnCount();
			    String[] columnNames = new String[count];
			    String[] typeName = new String[count];
			    int[] columnDisplaySize = new int[count];
		        for (int i = 0; i < count; i++) {
		        	String columnName = metaData.getColumnName(i+1);
		        	if (columnName.contains("_")) {
		        		String[] columnNameArrays = columnName.split("_");
		        		columnName="";
		        		for (int j = 0; j < columnNameArrays.length; j++) {
		        			columnName += j == 0 ? columnNameArrays[j] : ObjectUtils.totoUpperCaseFirst(columnNameArrays[j]);
						}
			      	    columnNames[i] = columnName;//列名
		        	} else {
		        		columnNames[i] = metaData.getColumnName(i + 1);//列名
		        	}
			      	typeName[i] = metaData.getColumnTypeName(i + 1);//列类型
			      	columnDisplaySize[i] = metaData.getColumnDisplaySize(i + 1);
			    }
		        String newTableName = getNewTableName(tableName);
				String context = createModel(columnNames,typeName,columnDisplaySize,newTableName);
				generateModel(context,newTableName);
		    }
			long end = System.currentTimeMillis();
			System.out.println("生成model成功,耗时" + (end - start) + "ms");
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("生成model异常");
		}
	}

	/**
	 * 将类写入文件
	 * @param context
	 */
	private  void generateModel(String context, String tableName) {
		FileWriter filterWriter = null;
		try {
			String filePath = PathKit.getWebRootPath()+ packageName.replace(".", "\\");
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			File source = new File(file, ObjectUtils.totoUpperCaseFirst(tableName) + ".java");
			if (!source.exists()) {
				source.createNewFile();
			}
			filterWriter = new FileWriter(source);
			filterWriter.write(context);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				filterWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String createModel(String[] columnName, String[] typeName,int[] columnDisplaySize,String tableName) {
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer packBuffer = new StringBuffer();
		generatePackage(packBuffer);
		stringBuffer.append("public class  "+ ObjectUtils.totoUpperCaseFirst(tableName) + "{\n\n");
		generateFiled(stringBuffer, columnName, columnDisplaySize, typeName);
		for (int i = 0; i < columnName.length; i++) {
			stringBuffer.append("\tpublic void set" + ObjectUtils.totoUpperCaseFirst(columnName[i])
					+ "(" + getType(typeName[i],columnDisplaySize[i]) +" " + columnName[i] + "){");
			stringBuffer.append("\n\t\t" + "this."+ columnName[i] + " = " + columnName[i]+";\n\t}\n\n");
			stringBuffer.append("\tpublic  "+ getType(typeName[i], columnDisplaySize[i]));
			String type = getType(typeName[i], columnDisplaySize[i]);
			
			if ("boolean".equals(type) || "Boolean".equals(type)) {
				stringBuffer.append("  is");
			} else {
				stringBuffer.append("  get");
			}
			stringBuffer.append(ObjectUtils.totoUpperCaseFirst(columnName[i])+"(){");
			stringBuffer.append("\n\t\t" + "return\t" + columnName[i] + ";\n\t}\n");
		}
		List<String> backTypeName = Arrays.asList(typeName);
		if (backTypeName.contains("DATETIME")) {
			packBuffer.append("import java.util.Date;\n\n");
		}
		if (backTypeName.contains("DECIMAL")) {
			packBuffer.append("import java.math.BigDecimal;\n");
		}
		stringBuffer.append("}");
		return  packBuffer.toString() + stringBuffer.toString();
	}

	/**
	 * 生成属性
	 * @param stringBuffer
	 * @param columnName
	 * @param typeName
	 */
	private void generateFiled(StringBuffer stringBuffer, String[] columnName,int[] columnDisplaySize, String[] typeName) {
		for (int i = 0; i < columnName.length; i++) {
			stringBuffer.append("\tprivate "+getType(typeName[i],columnDisplaySize[i])+" "+columnName[i]+";\n\n");
		}
	}

	private void generatePackage(StringBuffer packBuffer) {
		packBuffer.append("package "+ packageName.replace("/src/main/java/", "") + ";\n\n");
	}

	/**
	 * 获取数据库表字段类型
	 * @param typeName
	 * @return
	 */
	private  String  getType(String typeName,int size) {
		if (typeName.equals("VARCHAR")||typeName.equals("NVARCHAR")) {
			return "String";
		} else if(typeName.equals("DATETIME")) {
			return "Date";  
		} else if(typeName.equals("MONEY")) {
			return "double";  
		} else if(typeName.equals("INT")) {
			return "int";
		} else if(typeName.equals("TINYINT")||typeName.equals("SMALLINT")) {
			if (size == 1) {
				return "boolean";
			}
			return "int";
		} else if(typeName.equals("DECIMAL")) {
			return "BigDecimal";
		} else  if(typeName.equals("FLOAT")) {
			return "float";
		} else  if(typeName.equals("DOUBLE")) {
			return "double";
		} else if(typeName.equals("BIGINT")) {
			return "long";
		} else if(typeName.equals("BIT")) {
			return "long";
		}
		return null;
	}
	
	private String getNewTableName(String tableName){
		if(tableName.contains("_")){
			String[] tableNameArray = tableName.split("_");
			String newTableName = "";
			for (int i = 0; i < tableNameArray.length; i++) {
				newTableName += ObjectUtils.totoUpperCaseFirst(tableNameArray[i]);
			}
			return newTableName;
		}
		return tableName;
	}
}
