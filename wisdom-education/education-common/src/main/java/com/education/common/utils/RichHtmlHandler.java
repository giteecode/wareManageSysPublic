package com.education.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description:富文本Html处理器，主要处理图片及编码
 * @author:LiaoFei
 * @date :2016-3-28 下午4:13:21
 * @version V1.0
 * 
 */
public class RichHtmlHandler {

	private Document doc = null;
	private String html;

	private String docSrcParent = "";
	private String docSrcLocationPrex = "";
	private String nextPartId;
	private String shapeidPrex;
	private String spidPrex;
	private String typeid;

	private String handledDocBodyBlock;
	private List<String> docBase64BlockResults = new ArrayList<String>();
	private List<String> xmlImgRefs = new ArrayList<String>();

	public String getDocSrcLocationPrex() {
		return docSrcLocationPrex;
	}

	public void setDocSrcLocationPrex(String docSrcLocationPrex) {
		this.docSrcLocationPrex = docSrcLocationPrex;
	}

	public String getNextPartId() {
		return nextPartId;
	}

	public void setNextPartId(String nextPartId) {
		this.nextPartId = nextPartId;
	}

	public String getHandledDocBodyBlock() {
		String raw=   WordHtmlGeneratorHelper.string2Ascii(doc.getElementsByTag("body").html());
		return raw.replace("=3D", "=").replace("=", "=3D");
	}
	
	public String getRawHandledDocBodyBlock() {
		String raw=  doc.getElementsByTag("body").html();
		return raw.replace("=3D", "=").replace("=", "=3D");
	}
	public List<String> getDocBase64BlockResults() {
		return docBase64BlockResults;
	}

	public List<String> getXmlImgRefs() {
		return xmlImgRefs;
	}

	public String getShapeidPrex() {
		return shapeidPrex;
	}

	public void setShapeidPrex(String shapeidPrex) {
		this.shapeidPrex = shapeidPrex;
	}

	public String getSpidPrex() {
		return spidPrex;
	}

	public void setSpidPrex(String spidPrex) {
		this.spidPrex = spidPrex;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getDocSrcParent() {
		return docSrcParent;
	}

	public void setDocSrcParent(String docSrcParent) {
		this.docSrcParent = docSrcParent;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public RichHtmlHandler(String html) {
		this.html = html;
		doc = Jsoup.parse(wrappHtml(this.html));
	}
	
	public void re_init(String html){
		doc = Jsoup.parse(wrappHtml(html));
		docBase64BlockResults.clear();
		xmlImgRefs.clear();
	}
	
	/**
	 * @Description: 获得已经处理过的HTML文件
	 * @param @return
	 * @return String
	 * @throws IOException 
	 * @throws
	 * @author:LiaoFei
	 * @date:2016-3-28 下午4:16:34
	 */
	public void handledHtml(boolean isWebApplication)
			{
		Elements imageElement = doc.getElementsByTag("img");
		if (imageElement == null || imageElement.size() == 0) {
			// 返回编码后字符串
			return;
		}

		// 转换成word mht 能识别图片标签内容，去替换html中的图片标签

		for (Element item : imageElement) {
			String docFileName = "image" + UUID.randomUUID().toString() + "."; // + fileTypeName;
			String base64Content = null;
			String fileTypeName = null;
			// 把文件取出来
			String srcRealPath = item.attr("src");
			String handledDocBodyBlock = null;
			// base64 编码图片
			if (srcRealPath.contains("data:image/")) {
				fileTypeName = "png";
				base64Content = srcRealPath.replaceAll("data:image/png;base64,","");
				String srcLocationShortName = docSrcParent + "/" + docFileName + fileTypeName;
				String styleAttr = item.attr("style"); // 样式
				handledDocBodyBlock = WordImageConvertor.toDocBodyBlock(srcRealPath,
						NumberUtils.getUUID(), 0, 0, styleAttr,
						srcLocationShortName, shapeidPrex, spidPrex, typeid);
			} else {
				String imageFileShortName = null;
				if (srcRealPath.startsWith("http")) {
					int offset = srcRealPath.lastIndexOf("/");
					imageFileShortName = srcRealPath.substring(offset, srcRealPath.length());
				} else {
					File imageFile = new File(srcRealPath);
					imageFileShortName = imageFile.getName();
				}
				fileTypeName = WordImageConvertor.getFileSuffix(srcRealPath);
				String srcLocationShortName = docSrcParent + "/" + docFileName + fileTypeName;

				String styleAttr = item.attr("style"); // 样式
				//高度
				String imagHeightStr=item.attr("height");
				if (StringUtils.isEmpty(imagHeightStr)) {
					imagHeightStr = getStyleAttrValue(styleAttr, "height");
				}
				//宽度
				String imagWidthStr=item.attr("width");
				if (StringUtils.isEmpty(imagHeightStr)) {
					imagHeightStr = getStyleAttrValue(styleAttr, "width");
				}

				imagHeightStr = imagHeightStr.replace("px", "");
				imagWidthStr = imagWidthStr.replace("px", "");
				if (StringUtils.isEmpty(imagHeightStr)) {
					//去得到默认的文件高度
					imagHeightStr = "0";
				}
				if (StringUtils.isEmpty(imagWidthStr)) {
					imagWidthStr = "0";
				}
				int imageHeight = Integer.parseInt(imagHeightStr);
				int imageWidth = Integer.parseInt(imagWidthStr);
				handledDocBodyBlock = WordImageConvertor.toDocBodyBlock(srcRealPath,
						imageFileShortName, imageHeight, imageWidth,styleAttr,
						srcLocationShortName, shapeidPrex, spidPrex, typeid);
				try {
					base64Content = WordImageConvertor.imageToBase64(srcRealPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			item.after(handledDocBodyBlock);
			item.remove();
			String contextLocation = docSrcLocationPrex + "/" + docSrcParent + "/" + docFileName + fileTypeName;
			String docBase64BlockResult = WordImageConvertor.generateImageBase64Block(nextPartId, contextLocation,
							fileTypeName, base64Content);
			docBase64BlockResults.add(docBase64BlockResult);
			String imagXMLHref = "<o:File HRef=3D\"" + docFileName + "\"/>";
			xmlImgRefs.add(imagXMLHref);
		}
	}

	private String getStyleAttrValue(String style, String attributeKey) {
		if (StringUtils.isEmpty(style)) {
			return "";
		}

		// 以";"分割
		String[] styleAttrValues = style.split(";");
		for (String item : styleAttrValues) {
			// 在以 ":"分割
			String[] keyValuePairs = item.split(":");
			if (attributeKey.equals(keyValuePairs[0])) {
				return keyValuePairs[1];
			}
		}

		return "";
	}
	
	private String wrappHtml(String html){
		// 因为传递过来都是不完整的doc
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append(html);

		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}	
}
