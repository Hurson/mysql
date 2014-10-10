package com.dvnchina.advertDelivery.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.dvnchina.advertDelivery.constant.TemplateConstant;
import com.dvnchina.advertDelivery.utils.questionnaire.HtmlReader;

public class TemplateFormater {

	private static Log log = LogFactory.getLog(TemplateFormater.class);
	
	/**
	 * 判断是不是zip包
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public boolean IsZIP(File file) {
		boolean flag = true;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file);
		} catch (Exception e) {
			flag = false;
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				zipFile = null;
			}
		}
		return flag;
	}
	
	/**
	 * 如果目录是指定的几种目录之一就返回true
	 * 
	 * @param dirString
	 * @return
	 */
	public boolean formatFlag3(Map map) {
		// 目录的名字是 ：xxxx/aaaaa/ssss/
		// 如果是符合模板直接返回true
		Integer htmlCount = (Integer) map.get("htmlCount");
		List<String> dirList = (List<String>) map.get("dirList");
		if (htmlCount > 1) {
			return true;
		}
		boolean flag = true;
		for (String zipName : dirList) {
			log.info("zipName为：" + zipName);
			String path1 = zipName.substring(zipName.indexOf("/")); // 去掉zip包文件名后的路径
			log.info("去掉zip目录名字后的路径为：" + path1);
			if (path1.equals("/")) {
			} else {
				String path2 = path1.substring(1, path1.lastIndexOf("/"));
				log.info("下级目录名字为：" + path2);
				if (path2.equals(TemplateConstant.EFFECTDRAWING)||path2.equals(TemplateConstant.HTML)||path2.equals(TemplateConstant.SCRIPTS)||path2.equals(TemplateConstant.STYLES)||path2.equals(TemplateConstant.BACKGROUND)||path2.equals(TemplateConstant.IMAGES)) {
				} else{
					return false;
				}
			}
		}
		log.info("最后判断结果：" + flag);
		return flag;
	}
	
	/**
	 * 判断html文件的head中是否有必要的属性
	 * 
	 * @param dirString
	 * @return
	 * @throws IOException 
	 * @throws ZipException 
	 * @throws IOException 
	 * @throws ZipException 
	 * @throws ParserException 
	 */
	public boolean formatFlag6(Map map) throws ZipException, IOException, ParserException{
		boolean flag = true;
		ZipFile zipFile = (ZipFile) map.get("zipFile");
		Enumeration em = zipFile.getEntries();
		String zipName = null;
		ZipEntry zipEntry = null;
		while (em.hasMoreElements()) {
			zipEntry = (ZipEntry) em.nextElement();
			if (zipEntry.isDirectory()) {
			} else {
				zipName = zipEntry.getName();
				String path1 = zipName.substring(zipName.indexOf("/")); // 去掉zip包文件名后的路径
				log.info("去掉zip目录名字后的路径为：" + path1);
				String path2 = path1.substring(1, path1.lastIndexOf("/"));
				log.info("下级目录名字为：" + path2);
				zipName = zipEntry.getName();
				String lowerCaseName = zipName.toLowerCase();
				if(lowerCaseName.endsWith(".htm")||lowerCaseName.endsWith(".html")){
					if(!checkHead(zipFile.getInputStream(zipEntry))){
						return false;
					}
				}
			}
		}
		return flag;
	}
	
	public String parseHTML(InputStream inputStream) {
		String html = "";
		HtmlReader h = new HtmlReader();
		html = h.readHtml(inputStream);
		return html;
	}
	
	private boolean checkHead(InputStream inputStream) throws ParserException {
		String html = parseHTML(inputStream);
		Parser parser = new Parser();
		parser.setEncoding(TemplateConstant.ENCODING);
		parser.setInputHTML(html);
		NodeList nodeList = parser.parse(new TagNameFilter("head"));
		HeadTag h = (HeadTag) nodeList.elementAt(0);
		if(null==h){
			return false;
		}else{
			String image = h.getAttribute("image");
			if(StringUtils.isNotBlank(image)){
				return true;
			}		
		}
		return false;
	}
	
	
	/**
	 * 判断模版文件是否与压缩包名称相同
	 * @param map   zip包文件内的文件集合
	 * @return
	 */
	public boolean formatFlag0(Map map){
		List<String> htmlList = (List<String>) map.get("htmlList");
		if(htmlList.size() == 1){
			for(String zipName:htmlList){
				String path1 = zipName.substring(0,zipName.indexOf("/")); //压缩包的名字
				String path2 = zipName.substring(zipName.lastIndexOf("/")+1,zipName.indexOf("."));//文件的名字
				if(path1.equals(path2)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断JS描述文件是否与压缩包名称相同
	 * @param map   zip包文件内的文件集合
	 * @return
	 */ 
	public boolean formatFlag7(Map map){
		List<String> jsList = (List<String>) map.get("jsList");
		if(jsList.size() != 0){
			for(String zipName:jsList){
				String path1 = zipName.substring(0,zipName.indexOf("/")); //压缩包的名字
				String path2 = zipName.substring(zipName.lastIndexOf("/")+1,zipName.indexOf("."));//文件的名字
				if(path1.equals(path2)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断eff目录下的图片和html中head中的效果图是否对应
	 * 
	 * @param dirString
	 * @return
	 * @throws IOException 
	 * @throws ZipException 
	 */
	public boolean formatFlag1(Map map){
		boolean flag = false;
		Set<String> htmlEffList = (Set<String>) map.get("htmlEffList"); // 存放zip包中效果图
		Set<String> effList = (Set<String>) map.get("effList");
		flag = effList.containsAll(htmlEffList);
		log.info("effectDrawing目录下的文件名有：");
		for (String a : effList) {
			log.info(a);
		}
		log.info("html解析的效果图有：");
		for (String b : htmlEffList) {
			log.info(b);
		}
		return flag;
	}
	
	/**
	 * 判断zip的目录格式,并计算模板数量，存入htmlCount
	 * 
	 * @param zipFile
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ZipException 
	 * @throws IOException
	 * @throws ZipException
	 * @throws ZipException
	 * @throws IOException
	 * @throws ParserException 
	 */
	public Map formatZip(File file) throws ZipException, IOException, ParserException {
		log.info("开始判断目录格式");
		boolean flag = false;
		boolean flag0 = false;	//判断规则（压缩包的名称和模版文件的名称是否相同，html文件数量是否为1）
		boolean flag1 = false; // effectDrawing目录合法
		boolean flag2 = false; // html目录合法（html文件是否在html目录下）
		boolean flag3 = false; // 判断是否存在必要的目录，html目录和eff目录和images目录，script目录，sytle目录
		boolean flag4 = false; // 判断是否是zip文件
		boolean flag5 = true; // 读取zip是否成功
		boolean flag6 = false;// 判断html时候含有必要的属性
		boolean flag7 = false;// 判断压缩包的名称和JS描述的名称是否相同
		//Integer htmlCount = 0;
		List dirList = new ArrayList(); // 存放目录的列表
		flag4 = IsZIP(file);
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			flag5 = false;
		}
		Map map = new HashMap();
		Map parserMap = this.parserZipToMap(zipFile);
		flag0 = formatFlag0(parserMap);//判断模版文件是否与压缩包名称相同
		flag1 = formatFlag1(parserMap);//判断效果图和html中定义的效果图是否一致
	    flag2 = formatFlag2(parserMap);//判断文件存放目录是否正确
		flag3 = formatFlag3(parserMap);//判断是否存在必要的目录
		flag6 = formatFlag6(parserMap);//判断html中是否含有必要的属性
		flag7 = formatFlag7(parserMap);//判断html中是否含有必要的属性
		// 如果各种验证都通过
		if (flag0 && flag1 && flag2 && flag3 && flag4 && flag5&&flag6&&flag7) {
			flag = true;
		}
		log.info("#_1#效果图和html文件中定义的效果图一致结果：" + flag1 + "#_2#:文件存放目录正确与否：" + flag2
				+ "#_3#是否存在必要目录：" + flag3+"#_4# html是否包含必要属性："+flag6+"#_5# 读取zip是否成功："+flag5+"是否是zip文件："+flag4
				+ "#_6#html文件个数为1，名称与压缩包一致：" + flag0+"#_7# js描述文件名称与压缩包名称是否一致："+flag7
		);
		String msg = "";

		if (!flag1) {
			msg += "效果图缺少或者html模板文件中指定的名字与effectDrawing目录中的不相符合;";
		} else if (!flag2) {
			msg += "文件存放目录不相符！（html文件和图片文件和脚本文件和样式文件存放目录）;";
		} else if (!flag3) {
			msg += "zip打包的目录不正确，或者含有不必要的目录;";
		} else if (!flag4) {
			msg += "不是zip文件，请重新上传;";
		} else if (!flag5) {
			msg += "读取zip文件失败，请重新上传;";
		}else if(!flag6){
			msg += "模板文件的head中缺少image属性，或者head标记不完整，请检查后修改后再上传;";
		}else if(!flag0){
			msg += "模版不符合规则，压缩包名称没有与.html文件名字相同或者html文件个数不为1;";
		}
		else if(!flag7){
			msg += "模版不符合规则，压缩包名称没有与JS描述文件名字相同.";
		}
		if(StringUtils.isBlank(msg))
			msg = "验证通过";

		map.put("flag", flag ? "true" : "false");
		map.put("msg", msg);
		map.put("htmlCount", parserMap.get("htmlCount"));
		if(null!=zipFile){
			zipFile.close();
		}
		return map;
	}
	
	/**
	 * 判断对应文件是否在对应目录下（html是否在html目录下，js是否在scripts下，css是否在styles下）
	 * 
	 * @param dirString
	 * @return
	 * @throws IOException 
	 * @throws ZipException 
	 */
	public boolean formatFlag2(Map map){
		boolean flag = false;
		List<String> htmlList = (List<String>) map.get("htmlList"); 
		List<String> jsList = (List<String>) map.get("jsList");
		List<String> cssList = (List<String>) map.get("cssList");
		List<String> imgList = (List<String>) map.get("imgList");
		boolean flagImg = true;
		boolean flagCss = true;
		boolean flagJs = true;
		boolean flaghtml = true;
		for(String zipName:htmlList){
			String path1 = zipName.substring(zipName.indexOf("/")); // 去掉zip包文件名后的路径
			String path2 = path1.substring(1, path1.lastIndexOf("/"));
			if(!path2.equals(TemplateConstant.HTML)){
				flaghtml = false;
				break;
			}
		}
		for(String zipName:jsList){
			String path1 = zipName.substring(zipName.indexOf("/")); // 去掉zip包文件名后的路径
			String path2 = path1.substring(1, path1.lastIndexOf("/"));
			if(!path2.equals(TemplateConstant.SCRIPTS)){
				flagJs = false;
				break;
			}
		}
	
		for(String zipName:cssList){
			String path1 = zipName.substring(zipName.indexOf("/")); // 去掉zip包文件名后的路径
			String path2 = path1.substring(1, path1.lastIndexOf("/"));
			if(!path2.equals(TemplateConstant.STYLES)){
				flagCss = false;		
				break;
			}
		}
		
		for(String zipName:imgList){
			String path1 = zipName.substring(zipName.indexOf("/")); // 去掉zip包文件名后的路径
			String path2 = path1.substring(1, path1.lastIndexOf("/"));
			if(!path2.equals(TemplateConstant.IMAGES)&&!path2.equals(TemplateConstant.EFFECTDRAWING)&&!path2.equals(TemplateConstant.BACKGROUND)){
				flagImg = false;
				break;
			}
		}
		
		if(flaghtml&&flagJs&&flagCss&&flagImg){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 根据zipFile查找目录的名字，和html个数
	 * @param zipFile
	 * @return
	 * @throws IOException 
	 * @throws ZipException 
	 */
	public Map parserZipToMap(ZipFile zipFile) throws ZipException, IOException{
		Map map = new HashMap();
		List<String> dirList = new ArrayList<String>(); // 存放zip包中效果图
		Integer htmlCount = 0;
		Set<String> effList = new HashSet<String>();
		Set<String> htmlEffList = new HashSet<String>();
		List<String> htmlList = new ArrayList<String>();
		List<String> jsList = new ArrayList<String>();
		List<String> cssList = new ArrayList<String>();
		List<String> imgList = new ArrayList<String>();
		Enumeration em = zipFile.getEntries();
		String zipName = null;
		ZipEntry zipEntry = null;
		while (em.hasMoreElements()) {
			zipEntry = (ZipEntry) em.nextElement();
			if (zipEntry.isDirectory()) {
				dirList.add(zipEntry.getName());
			} else {
				zipName = zipEntry.getName();
				String path1 = zipName.substring(zipName.indexOf("/")); // 去掉zip包文件名后的路径
				log.info("去掉zip目录名字后的路径为：" + path1);
				String path2 = path1.substring(1, path1.lastIndexOf("/"));
				log.info("下级目录名字为：" + path2);
				zipName = zipEntry.getName();
				String lowerCaseName = zipName.toLowerCase();
				//如果是html文件
				if (lowerCaseName.endsWith(".htm")
						|| lowerCaseName.endsWith(".html")) { // 判断模板文件的目录格式，loadTemplateManage/html/类型/主模板名字_main
					// 存放模板对应的效果图
					htmlCount++;
					String imgurl = null;
					htmlList.add(zipName);
					imgurl = getAttributeValue("image",
							zipFile.getInputStream(zipEntry));
					htmlEffList
						.add(imgurl.substring(imgurl.lastIndexOf("/") + 1));
				}
				//如果是脚本
				if(lowerCaseName.endsWith(".css")){
					cssList.add(zipName);
				}
				//如果是样式文件
				if(lowerCaseName.endsWith(".js")){
					jsList.add(zipName);
				}
				//如果是图片
				if (lowerCaseName.endsWith(".jpg")) { // 判断效果图存放目录loadTemplateManage/effectDrawingName/主模板名字_main
					if (path2.contains(TemplateConstant.EFFECTDRAWING)) {
						effList.add(zipName
								.substring(zipName.lastIndexOf("/") + 1));
					}
					imgList.add(zipName);
				}
			}
		}
		map.put("htmlCount",htmlCount);
		map.put("dirList",dirList);
		map.put("htmlEffList",htmlEffList);
		map.put("effList",effList);
		map.put("htmlList", htmlList);
		map.put("imgList",imgList);
		map.put("zipFile",zipFile);
		map.put("cssList", cssList);
		map.put("jsList", jsList);
		return map;
	}
	
	public static String getAttributeValue(String attribute, InputStream file) {
		String returnrs = "other";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					file));
			while (reader.ready()) {
				String type = reader.readLine();
				if (type.indexOf("<head") >= 0) {
					String[] temp = type.split("=");
					for (int i = 0; i < temp.length; i++) {
						if (temp[i].indexOf(attribute) >= 0) {
							String temp2 = temp[i + 1];
							returnrs = temp2.split("\"")[1];
						}
					}

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnrs;
	}
	
}
