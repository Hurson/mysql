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

import com.dvnchina.advertDelivery.model.Material;
import com.dvnchina.advertDelivery.utils.config.DirectoryUtils;


/**
 * 获取素材配置文件
 * 
 * @author chennaidong
 *
 */
public class MaterialConfig {
	
	/**
	 * 素材配置文件
	 */
	private static File MaterialConfigureFile = new File(DirectoryUtils.getSerlvetContextPath()+"/WEB-INF/classes/materialConfig.xml");

	/**
	 * 素材配置DOM节点
	 */
	private static Document MaterialConfigureDom = null;
	
	/**
	 * 素材分类MAP集合 - 键值 -"kind"
	 */
	public static Map<String ,ArrayList<Material>> matkindMap = null;
	
	/**
	 *  素材类型MAP集合 - 键值 -"type" 
	 */
	public static Map<String ,ArrayList<Material>> matTypeMap = null;
	
	/**
	 *  素材类型MAP集合 - 键值 -"contract" 
	 */
	public static Map<String ,ArrayList<Material>> matContractMap = null;
	

	
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
		initMaterialKind();
		initMaterialType();
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
	 * 开始解析--素材类型
	 */
	@SuppressWarnings("unchecked")
	private static void initMaterialKind(){
		
		matkindMap  =  new HashMap<String, ArrayList<Material>>();
		String xpath="//kind";
		List<Element> nodes = MaterialConfigureDom.selectNodes(xpath);
		
		ArrayList<Material> materialKindList = new ArrayList<Material>();
		for(Element element:nodes){
			Material materialBean = new Material();
			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			materialBean.setId(Integer.valueOf(key));
			materialBean.setName(name);
			materialKindList.add(materialBean);
			
		}
		matkindMap.put("kind", materialKindList);
		
	}
	
	/**
	 * 开始解析--素材内容分类
	 */
	@SuppressWarnings("unchecked")
	static void initMaterialType(){
		matTypeMap  =  new HashMap<String, ArrayList<Material>>();
		String xpath2="//type";
		List<Element> node2s = MaterialConfigureDom.selectNodes(xpath2);
		
		ArrayList<Material> materialTypeList = new ArrayList<Material>();
		for(Element element:node2s){
			Material materialBean = new Material();
			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			materialBean.setId(Integer.valueOf(key));
			materialBean.setName(name);
			materialTypeList.add(materialBean);
			
		}
		matTypeMap.put("type", materialTypeList);
		
	}
	
}
