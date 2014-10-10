package com.dvnchina.advertDelivery.utils.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.utils.config.DirectoryUtils;


/**
 * 获取素材配置文件
 * 
 * @author chennaidong
 *
 */
public class PositionConfig {
	
	/**
	 * 素材配置文件
	 */
	private static File MaterialConfigureFile = new File(DirectoryUtils.getSerlvetContextPath()+"/WEB-INF/classes/materialConfig.xml");

	/**
	 * 素材配置DOM节点
	 */
	private static Document MaterialConfigureDom = null;
	
	/**
	 * 素材分类MAP集合 - 键值 -"positionbean"
	 */
	public static Map<String ,ArrayList<AdvertPosition>> positionMap = null;
	

	/**
	 * 加载配置文件,初始化信息
	 */
	static{
		SAXReader reader = new SAXReader();
    	try {
    		MaterialConfigureDom = reader.read(MaterialConfigureFile);
			initData();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据
	 */
	private static void initData(){
		if(MaterialConfigureDom == null){
			reloadConfig();
		}
		//开始加载
		initPosition();
	}
	
	/**
	 * 重新加载配置文件,初始化
	 */
	private static void reloadConfig(){
		SAXReader reader = new SAXReader();
    	try {
    		MaterialConfigureDom = reader.read(MaterialConfigureFile);
			initData();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 开始解析--素材分类
	 */
	@SuppressWarnings("unchecked")
	private static void initPosition(){
		
		positionMap  =  new HashMap<String, ArrayList<AdvertPosition>>();
		String xpath="//positionbean";
		List<Element> nodes = MaterialConfigureDom.selectNodes(xpath);
		
		ArrayList<AdvertPosition> materialKindList = new ArrayList<AdvertPosition>();
		for(Element element:nodes){
			AdvertPosition positionBean = new AdvertPosition();
			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			positionBean.setId(Integer.valueOf(key));
			//positionBean.setPositionNameCn(name);
			materialKindList.add(positionBean);
			
		}
		positionMap.put("positionbean", materialKindList);
		
	}
}
