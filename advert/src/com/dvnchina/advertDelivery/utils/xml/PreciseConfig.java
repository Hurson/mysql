package com.dvnchina.advertDelivery.utils.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dvnchina.advertDelivery.model.PreciseMatch;
import com.dvnchina.advertDelivery.utils.config.DirectoryUtils;


/**
 * 精准配置文件
 * 
 * @author 
 *
 */
public class PreciseConfig {
	
	private static File preciseConfigureFile = new File(DirectoryUtils.getSerlvetContextPath()+"/WEB-INF/classes/preciseConfig.xml");

	/**
	 * 精准配置DOM节点
	 */
	private static Document preciseConfigureDom = null;
	
	/**
	 * 精准类型
	 */
	public static HashMap<String, ArrayList<PreciseMatch>> typeMap = null;
	

	/**
	 * 加载配置文件,初始化信息
	 */
	static{
		SAXReader reader = new SAXReader();
    	try {
    		preciseConfigureDom = reader.read(preciseConfigureFile);
			initData();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据
	 */
	private static void initData(){
		if(preciseConfigureDom == null){
			reloadConfig();
		}
		//开始加载
		initMarketingPrecise();
	}
	
	/**
	 * 重新加载配置文件,初始化
	 */
	private static void reloadConfig(){
		SAXReader reader = new SAXReader();
    	try {
    		preciseConfigureDom = reader.read(preciseConfigureFile);
			initData();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 开始解析
	 */
	@SuppressWarnings("unchecked")
	private static void initMarketingPrecise(){
		
		typeMap  =  new HashMap<String, ArrayList<PreciseMatch>>();
		String xpath="//type";
		List<Element> nodes = preciseConfigureDom.selectNodes(xpath);
		ArrayList<PreciseMatch> typeList = new ArrayList<PreciseMatch>();
		for(Element element:nodes){
			PreciseMatch precise = new PreciseMatch();
			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			precise.setKey(key);
			precise.setMatchName(name);
			typeList.add(precise);
		}
		typeMap.put("type", typeList);
	}
}
