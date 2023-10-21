package com.education.common.utils;

import com.jfinal.kit.Base64Kit;
import org.apache.commons.codec.binary.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.UUID;


/**
* @Description:WORD 文档图片转换器
* @author:LiaoFei  
* @date :2016-3-28 上午11:21:06  
* @version V1.0  
*   
*/
public class WordImageConvertor {
	
	//private static Const WORD_IMAGE_SHAPE_TYPE_ID="";
	
	/**   
	* @Description: 将图片转换成base64编码的字符串  
	* @param @param imageSrc 文件路径
	* @param @return    
	* @return String   
	 * @throws IOException 
	 * @throws
	* @author:LiaoFei
	* @date:2016-3-28 上午11:22:26
	*/ 
	public static String imageToBase64(String imageSrc) throws IOException {
		InputStream inputStream = null;
		if (imageSrc.startsWith("http")) {
			inputStream = RequestUtils.getInputStreamFromUrl(imageSrc);
		} else {
			//判断文件是否存在
			File file = new File(imageSrc);
			if (!file.exists()) {
				throw new FileNotFoundException("文件不存在！");
			}
			inputStream = new FileInputStream(file);
		}
		StringBuilder pictureBuffer = new StringBuilder();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		//读取文件
        Base64 base64 = new Base64();
		byte[] temp = new byte[1024];
        for (int len = inputStream.read(temp); len != -1;len = inputStream.read(temp)){
            out.write(temp, 0, len);

        }
        pictureBuffer.append(new String( base64.encodeBase64Chunked(out.toByteArray())));
		inputStream.close();
		return pictureBuffer.toString();
	}
	
	
	
	public static String toDocBodyBlock(
			String imageFilePath,
			String imageFileShortName,
			int imageHeight,
			int imageWidth,
			String imageStyle,
			String srcLocationShortName,
			String shapeidPrex,String spidPrex,String typeid){
		//shapeid
		//mht文件中针对shapeid的生成好像规律，其内置的生成函数没法得知，但是只要保证其唯一就行
		//这里用前置加32位的uuid来保证其唯一性。
		String shapeid = shapeidPrex;
		shapeid += UUID.randomUUID().toString();
		
		//spid ,同shapeid处理
		String spid=spidPrex;
		spid+=UUID.randomUUID().toString();

		StringBuilder sb1=new StringBuilder();
		
		sb1.append(" <!--[if gte vml 1]>");
		sb1.append("<v:shape id=3D\"" + shapeid+"\"");
		sb1.append("\n");
		sb1.append(" o:spid=3D\""+ spid +"\"" );
		sb1.append(" type=3D\""+  typeid +"\" alt=3D\"" + imageFileShortName +"\"");
		sb1.append("\n");
		sb1.append( " style=3D' " + generateImageBodyBlockStyleAttr(imageFilePath,imageHeight,imageWidth) + imageStyle +"'");
		sb1.append(">");
		sb1.append("\n");
		sb1.append(" <v:imagedata src=3D\"" + srcLocationShortName +"\""  );
		sb1.append("\n");
		sb1.append(" o:title=3D\"" + imageFileShortName.split("\\.")[0]+"\""  );
		sb1.append("/>");
		sb1.append("</v:shape>");
		sb1.append("<![endif]-->");
		return sb1.toString();
	}
	
	/**   
	* @Description: 生成图片的base4块  
	* @param @param nextPartId
	* @param @param contextLoacation
	* @param @param ContentType
	* @param @param base64Content
	* @param @return    
	* @return String    
	* @throws
	* @author:LiaoFei
	* @date:2016-3-28 下午4:02:05
	*/ 
	public static String generateImageBase64Block(String nextPartId,String contextLoacation,
									String fileTypeName,String base64Content){

		StringBuilder sb=new StringBuilder();
		sb.append("\n");
		sb.append("\n");
		sb.append("------=_NextPart_"+nextPartId);
		sb.append("\n");
		sb.append("Content-Location: "+ contextLoacation);
		sb.append("\n");
		sb.append("Content-Transfer-Encoding: base64");
		sb.append("\n");
		sb.append("Content-Type: " + getImageContentType(fileTypeName));
		sb.append("\n");
		sb.append("\n");
		sb.append(base64Content);
		
		return sb.toString();
	}
	
	
	private static String generateImageBodyBlockStyleAttr(String imageFilePath, int height, int width) {
		InputStream inputStream = null;
		if (imageFilePath.startsWith("http")) {
			inputStream = RequestUtils.getInputStreamFromUrl(imageFilePath);
		} else if (imageFilePath.contains("data:image/")) {
			imageFilePath = imageFilePath.replaceAll("data:image/png;base64,","");
			byte[] decoderBytes = Base64Kit.decode(imageFilePath);
			inputStream = new ByteArrayInputStream(decoderBytes);
			/*
			 jdk 1.8 以下版本支持
			BASE64Decoder decoder = new BASE64Decoder();

			try {
				imageFilePath = imageFilePath.replaceAll("data:image/png;base64,","");
				byte[] decoderBytes = decoder.decodeBuffer(imageFilePath);
				inputStream = new ByteArrayInputStream(decoderBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
		else {
			try {
				inputStream = new FileInputStream(imageFilePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		StringBuilder sb=new StringBuilder();
		
		BufferedImage sourceImg;
		try {
			sourceImg = ImageIO.read(inputStream);
			if(height==0){
				height=sourceImg.getHeight();
			}
			if(width==0){
				width=sourceImg.getWidth();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//将像素转化成pt 
	    BigDecimal heightValue=new BigDecimal(height*12/16);
	    heightValue= heightValue.setScale(2, BigDecimal.ROUND_HALF_UP);
	    BigDecimal widthValue=new BigDecimal(width*12/16);
	    widthValue= widthValue.setScale(2, BigDecimal.ROUND_HALF_UP);
	  
		sb.append("height:"+heightValue +"pt;");
		sb.append("width:"+widthValue +"pt;");
		sb.append("visibility:visible;");
		sb.append("mso-wrap-style:square; ");
		
		
		return sb.toString();
	}
	
	private static String getImageContentType(String fileTypeName){
		String result="image/jpeg";
		//http://tools.jb51.net/table/http_content_type
		if(fileTypeName.equals("tif") || fileTypeName.equals("tiff")){
			result="image/tiff";
		}else if(fileTypeName.equals("fax")){
			result="image/fax";
		}else if(fileTypeName.equals("gif")){
			result="image/gif";
		}else if(fileTypeName.equals("ico")){
			result="image/x-icon";
		}else if(fileTypeName.equals("jfif") || fileTypeName.equals("jpe") 
					||fileTypeName.equals("jpeg")  ||fileTypeName.equals("jpg")){
			result="image/jpeg";
		}else if(fileTypeName.equals("net")){
			result="image/pnetvue";
		}else if(fileTypeName.equals("png") || fileTypeName.equals("bmp") ){
			result="image/png";
		}else if(fileTypeName.equals("rp")){
			result="image/vnd.rn-realpix";
		}else if(fileTypeName.equals("rp")){
			result="image/vnd.rn-realpix";
		}
		
		return result;
	}
	
	
	public static String getFileSuffix(String srcRealPath){
		String suffix = srcRealPath.substring(srcRealPath.indexOf(".") + 1);
		return suffix;
	}
}
