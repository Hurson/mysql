package com.dvnchina.advertDelivery.utils.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


/**
 * xml 工具类 
 * 
 * @author chennaidong
 *
 * 
 */
public class XmlUtils {

	/**
	 * 保存xml文件
	 * @param xmlOutputFilePath  文件保存的路径
	 * @param document           Document  文档对象
	 * @return
	 */
	public static boolean saveXMLFile(String xmlOutputFilePath ,Document document) {
		return saveXMLFile( xmlOutputFilePath , document, null);
	}
	
	/**
	 * 保存xml文件
	 * @param xmlOutputFilePath  文件保存的路径
	 * @param document           Document  文档对象
	 * @param encoding 			   编码格式
	 * @return
	 */
	public static boolean saveXMLFile(String xmlOutputFilePath ,Document document,String encoding){
		
		if(StringUtils.isBlank(encoding)){
			encoding = "utf-8";
		}
		File  xmlOutputFile = new File(xmlOutputFilePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		format.setIndent(true);
		format.setNewlines(true);
		XMLWriter xmlWriter;
		try {
			xmlWriter = new XMLWriter(new FileOutputStream(xmlOutputFile), format);
			xmlWriter.write(document);
			xmlWriter.flush();
			xmlWriter.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			xmlWriter = null;
		}
	}
	
	/**
	 * 基础的xml
	 * 
	 * @param realPath
	 * @param treeId
	 * @return
	 * @throws IOException
	 */
	public  Document createTreeXmlHead(String xmlUrl) {
		try {
			String head ="";
			File file = new File(xmlUrl);
			if (!file.exists()) {
				file.createNewFile();
				head = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
			}
			writeXml(head, xmlUrl);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DocumentHelper.createDocument();
	}
	
	/**
	 * 
	 * @param content  内容
	 * @param path     文件的路径
	 */
	public static void writeXml(String content, String path){
		try {
			File file = new File(path);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			out.write(content);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
