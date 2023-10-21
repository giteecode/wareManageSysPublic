package com.education.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* @Description:word 网页导出（单文件网页导出，mht文件格式）
* @author:LiaoFei  
* @date :2016-3-28 上午11:17:38  
* @version V1.0  
*   
*/
public class WordHtmlGeneratorHelper  {
	
	/**   
	* @Description: 将字符换成3Dus-asci，十进制Accsii码
	* @param @param source
	* @param @return    
	* @return String    
	* @throws
	* @author:LiaoFei
	* @date:2016-3-28 上午11:18:39
	*/ 
	public static String string2Ascii(String source){
		if(source==null || source==""){
			return null;
		}
		StringBuilder sb=new StringBuilder();
		
		char[] c=source.toCharArray();
		for(char item : c){
			String itemascii="";
			if(item>=19968 && item<40623){
				itemascii=itemascii="&#"+(item & 0xffff)+";";
			}else{
				itemascii=item+"";
			}
			sb.append(itemascii);
		}
		
		return sb.toString();
		
	}
	
	/**   
	* @Description: 将object的所有属性值转成成3Dus-asci编码值
	* @param @param object
	* @param @return    
	* @return T    
	* @throws
	* @author:LiaoFei
	* @date:2016-3-29 下午2:56:24
	*/ 
	public static <T extends Object> T handleObject2Ascii(final T toHandleObject){
		
		class myFieldsCallBack  implements FieldCallback {

			@Override
			public void doWith(Field f) throws IllegalArgumentException,
					IllegalAccessException {
				if(f.getType().equals(String.class)){
					//如果是字符串类型
					f.setAccessible(true);
					String oldValue=(String)f.get(toHandleObject);
					if(!StringUtils.isEmpty(oldValue)){
						f.set(toHandleObject, string2Ascii(oldValue));
					}
					
					//f.setAccessible(false);
				}
			}
		}
	
		ReflectionUtils.doWithFields(toHandleObject.getClass(), new myFieldsCallBack());
		
		return toHandleObject;
	}
	
	
	public static <T extends Object> List<T> handleObjectList2Ascii(final List<T> toHandleObjects){
		
		for (T t : toHandleObjects) {
			handleObject2Ascii(t);
		}
		
		return toHandleObjects;
	}
	
	
	public static void handleAllObject(Map<String, Object> dataMap) {
		
		//去处理数据
    	for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
    		Object item = entry.getValue();

    		if (ObjectUtils.isNotEmpty(item)) {
				//判断object是否是primitive type
				if (isPrimitiveType(item.getClass())) {
					if (String.class.equals(item.getClass())) {
						item= WordHtmlGeneratorHelper.string2Ascii((String)item);
						entry.setValue(item);
					}
				} else if (isCollection(item.getClass())) {
					for (Object itemObject : (Collection)item) {
						WordHtmlGeneratorHelper.handleObject2Ascii(itemObject);
					}
				} else {
					WordHtmlGeneratorHelper.handleObject2Ascii(item);
				}
			}
    	}
		
	}
	
	public static String joinList(List<String> list,String join ){
		StringBuilder sb=new StringBuilder();
		for (String t : list) {
			sb.append(t);
			if(!StringUtils.isEmpty(join)){
				sb.append(join);
			}
		}
		
		return sb.toString();
	} 
	
	
	private static boolean isPrimitiveType(Class<?> clazz){
    	return clazz.isEnum() ||
		CharSequence.class.isAssignableFrom(clazz) ||
		Number.class.isAssignableFrom(clazz) ||
		Date.class.isAssignableFrom(clazz);
    	
    }
    private static boolean isCollection(Class<?> clazz){
    	return Collection.class.isAssignableFrom(clazz);
    }
	
	
	
}
