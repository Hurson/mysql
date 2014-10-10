package com.dvnchina.advertDelivery.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dvnchina.advertDelivery.bean.config.AdvertPositionBean;
import com.dvnchina.advertDelivery.bean.config.CommonConfigBean;
import com.dvnchina.advertDelivery.bean.config.InterfaceConfigBean;
import com.dvnchina.advertDelivery.bean.config.PlatformConfigBean;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.service.SystemConfigService;
import com.dvnchina.advertDelivery.utils.config.DirectoryUtils;
import com.dvnchina.advertDelivery.utils.xml.XmlUtils;

/**
 * 系统参数配置接口实现类
 * 
 * @author chennaidong
 *
 */
public class SystemConfigServiceImpl  implements SystemConfigService{
	
	/**
	 * 系统参数配置文件  
	 */
	private  String xmlFilePath = DirectoryUtils.getSerlvetContextPath()+"/WEB-INF/classes/sysConfig.xml";

	/**
	 * 素材配置DOM节点
	 */
	private  Document configDom = null;
	
	
	/**
	 * 加载配置文件
	 */
	private synchronized    Document loadConfigFile(){
		SAXReader reader = new SAXReader();
		File configfile = new File(xmlFilePath);
    	try {
    		configDom = reader.read(configfile);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return configDom;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void addOrUpdatePlatformUrl(PlatformConfigBean bean) {
		
		String platform_xpath="//playaddress//platform[@name='"+bean.getName()+"']";
		
		Document document = loadConfigFile();
		
		if(document.selectSingleNode(platform_xpath) != null){
			
			Element elementTar = (Element)	document.selectSingleNode(platform_xpath);
			List<Element> url_nodes = elementTar.elements();
			Element urlElement = null;
			boolean isExist = false;
			for(Element url_element : url_nodes){
				
				String platform_url_id = url_element.attributeValue("id");
				String platform_url_value = url_element.attributeValue("value");
				
				if(bean.getId().equals(platform_url_id)){
					urlElement = url_element;
				}
				if(bean.getValue().equals(platform_url_value)){
					isExist = true;
				}
				
				
			}
			if(!isExist){
				if(urlElement == null){//添加
					
					Element url_element = elementTar.addElement("child");
					long l = new Date().getTime();
					url_element.addAttribute("id", String.valueOf(l));
					url_element.addAttribute("value", bean.getValue());
				}else{//修改
					urlElement.addAttribute("value", bean.getValue());
				}
			}
			
		}else{
			
			Element element = (Element)document.selectSingleNode("//playaddress");
			Element platform_element = element.addElement("platform");
			
			platform_element.addAttribute("name", bean.getName());
			Element url_element = platform_element.addElement("child");
			
			long l = new Date().getTime();
			url_element.addAttribute("id", String.valueOf(l));
			url_element.addAttribute("value", bean.getValue());
		}
		
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
	}
	
	/**
	 * 删除平台的配置
	 * @param urlId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean deletePlatformUrl(String urlId){
		
		Document document = loadConfigFile();
		List<Element> nodes = document.selectNodes("//playaddress//platform");
		
		for(Element platformElement : nodes){
			
			List<Element> urlNodes = platformElement.elements();
			for(Element urlElement : urlNodes){
				if(urlId.equals(urlElement.attributeValue("id"))){
					platformElement.remove(urlElement);
				}
			}
		}
		
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
		return true;
	}
	
	
	
	/**
	 * 获取平台的配置列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlatformConfigBean> getPlatformUrl(){
		
		List<PlatformConfigBean> resultL = new ArrayList<PlatformConfigBean>();
		
		Document document = loadConfigFile();
		List<Element> nodes = document.selectNodes("//playaddress//platform");
		
		for(Element platformElement : nodes){
			
			List<Element> urlNodes = platformElement.elements();
			for(Element urlElement : urlNodes){
				
				PlatformConfigBean bean = new PlatformConfigBean();
				bean.setId(urlElement.attributeValue("id"));
				bean.setName(platformElement.attributeValue("name"));
				bean.setValue(urlElement.attributeValue("value"));
				resultL.add(bean);
			}
		}
		
		return resultL;
	}
	/*********************************************************************************************/
	
	public void addOrUpdateInterface(InterfaceConfigBean bean) {
		
		String platform_xpath="//datasyninterface//interface[@name='"+bean.getName()+"']";
		
		Document document = loadConfigFile();
		
		if(document.selectSingleNode(platform_xpath) != null){
			
			Element elementTar = (Element)	document.selectSingleNode(platform_xpath);
			List<Element> url_nodes = elementTar.elements();
			Element urlElement = null;
			boolean isExist = false;
			for(Element url_element : url_nodes){
				
				String platform_url_id = url_element.attributeValue("id");
				String platform_url_value = url_element.attributeValue("value");
				
				if(bean.getId().equals(platform_url_id)){
					urlElement = url_element;
				}
				if(bean.getValue().equals(platform_url_value)){
					isExist = true;
				}
				
				
			}
			if(!isExist){
				if(urlElement == null){//添加
					
					Element url_element = elementTar.addElement("child");
					long l = new Date().getTime();
					url_element.addAttribute("id", String.valueOf(l));
					url_element.addAttribute("value", bean.getValue());
				}else{//修改
					urlElement.addAttribute("value", bean.getValue());
				}
			}
			
		}else{
			
			Element element = (Element)document.selectSingleNode("//datasyninterface");
			Element platform_element = element.addElement("interface");
			
			platform_element.addAttribute("name", bean.getName());
			Element url_element = platform_element.addElement("child");
			
			long l = new Date().getTime();
			url_element.addAttribute("id", String.valueOf(l));
			url_element.addAttribute("value", bean.getValue());
		}
		
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
	}
	
	
	/**
	 * 删除接口的配置
	 * @param urlId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteInterface(String urlId){
		
		Document document = loadConfigFile();
		List<Element> nodes = document.selectNodes("//datasyninterface//interface");
		
		for(Element platformElement : nodes){
			
			List<Element> urlNodes = platformElement.elements();
			for(Element urlElement : urlNodes){
				if(urlId.equals(urlElement.attributeValue("id"))){
					platformElement.remove(urlElement);
				}
			}
		}
		
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
		return true;
	}
	
	/**
	 * 获取数据同步接口的配置列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InterfaceConfigBean> getInterfaceConfigList(){
		
		List<InterfaceConfigBean> resultL = new ArrayList<InterfaceConfigBean>();
		
		Document document = loadConfigFile();
		List<Element> nodes = document.selectNodes("//datasyninterface//interface");
		
		for(Element platformElement : nodes){
			
			List<Element> urlNodes = platformElement.elements();
			for(Element urlElement : urlNodes){
				
				InterfaceConfigBean bean = new InterfaceConfigBean();
				bean.setId(urlElement.attributeValue("id"));
				bean.setName(platformElement.attributeValue("name"));
				bean.setValue(urlElement.attributeValue("value"));
				resultL.add(bean);
			}
		}
		
		return resultL;
	}
	
	/********************************************************************************/
	
	/**
	 * 添加和修改插播次数
	 * @param bean
	 * @return
	 */
	public boolean addOrUpdataPlayCount(CommonConfigBean bean){
		
		String xpath="//playcount";
		String xpath_add="//config";
		
		Document document = loadConfigFile();
		
		if(document.selectSingleNode(xpath) != null){
			
			Element elementTar = (Element)	document.selectSingleNode(xpath);
			List<Element> nodes = elementTar.elements();
			Element element = null;
			boolean isExist = false;
			for(Element node_element : nodes){
				
				String node_id = node_element.attributeValue("id");
				String node_value = node_element.attributeValue("value");
				
				if(bean.getId().equals(node_id)){
					element = node_element;
				}
				if(bean.getValue().equals(node_value)){
					isExist = true;
				}
				
				
			}
			if(!isExist){
				if(element == null){//添加
					
					Element add_element = elementTar.addElement("child");
					long l = new Date().getTime();
					add_element.addAttribute("id", String.valueOf(l));
					add_element.addAttribute("value", bean.getValue());
				}else{//修改
					element.addAttribute("value", bean.getValue());
				}
			}
			
		}else{
			
			Element element = (Element)document.selectSingleNode(xpath_add);
			Element add_element = element.addElement("playcount");
			
			add_element.addAttribute("name", bean.getName());
			Element addChild_element = add_element.addElement("child");
			
			long l = new Date().getTime();
			addChild_element.addAttribute("id", String.valueOf(l));
			addChild_element.addAttribute("value", bean.getValue());
		}
		
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
		
		return true;
		
	}
	
	/**
	 * 字幕显示位置配置列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommonConfigBean> getShowPlayCountList(){
		
		List<CommonConfigBean> resultL = new ArrayList<CommonConfigBean>();
		
		Document document = loadConfigFile();
		List<Element> nodes = document.selectNodes("/config/playcount");
		
		for(Element platformElement : nodes){
			
			List<Element> urlNodes = platformElement.elements();
			for(Element urlElement : urlNodes){
				
				CommonConfigBean bean = new CommonConfigBean();
				bean.setId(urlElement.attributeValue("id"));
				bean.setName(platformElement.attributeValue("name"));
				bean.setValue(urlElement.attributeValue("value"));
				resultL.add(bean);
			}
		}
		
		return resultL;
	}
	
	
	
	/***********************************************************************/
	@SuppressWarnings("unchecked")
	public void addOrUpdateShowPosition(CommonConfigBean bean) {
		
		String platform_xpath="//showposition//position[@name='"+bean.getName()+"']";
		
		Document document = loadConfigFile();
		
		if(document.selectSingleNode(platform_xpath) != null){
			
			Element elementTar = (Element)	document.selectSingleNode(platform_xpath);
			List<Element> url_nodes = elementTar.elements();
			Element urlElement = null;
			boolean isExist = false;
			for(Element url_element : url_nodes){
				
				String platform_url_id = url_element.attributeValue("id");
				String platform_url_value = url_element.attributeValue("value");
				
				if(bean.getId().equals(platform_url_id)){
					urlElement = url_element;
				}
				if(bean.getValue().equals(platform_url_value)){
					isExist = true;
				}
				
				
			}
			if(!isExist){
				if(urlElement == null){//添加
					
					Element url_element = elementTar.addElement("child");
					long l = new Date().getTime();
					url_element.addAttribute("id", String.valueOf(l));
					url_element.addAttribute("value", bean.getValue());
				}else{//修改
					urlElement.addAttribute("value", bean.getValue());
				}
			}
			
		}else{
			
			Element element = (Element)document.selectSingleNode("//showposition");
			Element platform_element = element.addElement("position");
			
			platform_element.addAttribute("name", bean.getName());
			Element url_element = platform_element.addElement("child");
			
			long l = new Date().getTime();
			url_element.addAttribute("id", String.valueOf(l));
			url_element.addAttribute("value", bean.getValue());
		}
		
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
	}
	
	/**
	 * 删除字幕显示位置的配置
	 * @param urlId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteShowPosition(String urlId){
		
		Document document = loadConfigFile();
		List<Element> nodes = document.selectNodes("//showposition//position");
		
		for(Element platformElement : nodes){
			
			List<Element> urlNodes = platformElement.elements();
			for(Element urlElement : urlNodes){
				if(urlId.equals(urlElement.attributeValue("id"))){
					platformElement.remove(urlElement);
				}
			}
		}
		
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
		return true;
	}
	
	
	
	/**
	 * 字幕显示位置配置列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommonConfigBean> getShowPositionList(){
		
		List<CommonConfigBean> resultL = new ArrayList<CommonConfigBean>();
		
		Document document = loadConfigFile();
		List<Element> nodes = document.selectNodes("//showposition//position");
		
		for(Element platformElement : nodes){
			
			List<Element> urlNodes = platformElement.elements();
			for(Element urlElement : urlNodes){
				
				CommonConfigBean bean = new CommonConfigBean();
				bean.setId(urlElement.attributeValue("id"));
				bean.setName(platformElement.attributeValue("name"));
				bean.setValue(urlElement.attributeValue("value"));
				resultL.add(bean);
			}
		}
		
		return resultL;
	}
	/*********************************************************************************************/
	
	public boolean addPositionDefaultMaterial(AdvertPosition bean ,String m_type,String m_path){
		
			String advert_xpath="//advert//advert_position[@id='"+bean.getId()+"']";
			
			Document document = loadConfigFile();
			boolean isExist = false;
			
			if(document.selectSingleNode(advert_xpath) != null){
				
				Element elementTar = (Element)	document.selectSingleNode(advert_xpath);
				List<Element> url_nodes = elementTar.elements();
				for(Element url_element : url_nodes){
					
					String platform_url_value = url_element.attributeValue("filepath");
					
					if(m_path.equals(platform_url_value)){
						isExist = true;
						return false;
					}
					
				}
				if(!isExist)
					addDefaultMaterialNode(elementTar, m_type, m_path);
				
			}else{
				
				Element element = (Element)document.selectSingleNode("//advert");
				Element advert_position_node = addAdvertElement(element, bean);
				addDefaultMaterialNode(advert_position_node, m_type, m_path);
				
				
			}
			
			XmlUtils.saveXMLFile(xmlFilePath, document);
			String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
			XmlUtils.saveXMLFile(xmlFilePath, document);
			XmlUtils.saveXMLFile(xmlPath, document);
			return true;
		}
	
	@SuppressWarnings("unchecked")
	public List<AdvertPositionBean> queryDefaultMaterial(String id){
		
		String advert_xpath="//advert//advert_position[@id='"+id+"']";
		
		Document document = loadConfigFile();
		
		List<AdvertPositionBean> resultList = new ArrayList<AdvertPositionBean>();
		
		if(document.selectSingleNode(advert_xpath) != null){
			
			Element elementTar = (Element)	document.selectSingleNode(advert_xpath);
			List<Element> child_nodes = elementTar.elements();
			
			for(Element child_element : child_nodes){
				AdvertPositionBean bean  = new AdvertPositionBean();
				
				bean.setChild_id(child_element.attributeValue("id"));
				bean.setM_type(child_element.attributeValue("filetype"));
				bean.setM_path(child_element.attributeValue("filepath"));
				
				resultList.add(bean);
			}
		}
		return resultList;
	}
	
	public boolean deletePositionDefaultMaterial(String positionId,String m_id){
		
		Document document = loadConfigFile();
		String advert_xpath="//advert//advert_position[@id='"+positionId+"']";
		
		if(document.selectSingleNode(advert_xpath) != null){
			
			Element elementTar = (Element)	document.selectSingleNode(advert_xpath);
			List<Element> child_nodes = elementTar.elements();
			for(Element child_node : child_nodes){
				if(m_id.equals(child_node.attributeValue("id"))){
					elementTar.remove(child_node);
				}
			}
			
		}else{
			return false;
		}
		XmlUtils.saveXMLFile(xmlFilePath, document);
		String xmlPath = xmlFilePath.replace("webapp/WEB-INF/classes","resources" );
		XmlUtils.saveXMLFile(xmlFilePath, document);
		XmlUtils.saveXMLFile(xmlPath, document);
		
		return true;
	}
	
	/**
	 * 添加广告位的默认配置节点
	 * 
	 * @param element
	 * @param bean
	 */
	private Element addAdvertElement(Element element ,AdvertPosition bean){
		
		Element advert_position = element.addElement("advert_position");
		
		advert_position.addAttribute("id", bean.getId()+"");
		
		// 特征标识
		advert_position.addAttribute("eigenValue", bean.getCharacteristicIdentification()+"");

		// 广告位名称
		advert_position.addAttribute("positionName", bean.getPositionName()+"");
		
		//状态  0 可用(默认)  1 不可用
		advert_position.addAttribute("state", bean.getState()+"");
		
		//是否高清（0-否  1-有 2-两个都有）
		advert_position.addAttribute("isHD", bean.getIsHd()+"");
		
		// 是否叠加  0-否 1-是
		advert_position.addAttribute("isOverlying", bean.getIsAdd()+"");
		
		// 是否轮询 0-否 1-是
		advert_position.addAttribute("isPolling", bean.getIsLoop()+"");
		
		// 轮询素材个数
		advert_position.addAttribute("pollingCount", bean.getMaterialNumber()+"");
		
		//投放方式 以供订单使用
		advert_position.addAttribute("advertiseWay", bean.getDeliveryMode()+"");
		
		//投放平台 生成播出计划单使用
		advert_position.addAttribute("positionPlatform", bean.getDeliveryPlatform()+"");
		
		
		return advert_position;
	}
	
	/**
	 * 添加 默认素材节点配置
	 * 
	 * @param advert_position_node
	 * @param m_type
	 * @param m_path
	 * @return
	 */
	private Element addDefaultMaterialNode(Element advert_position_node,String m_type, String m_path){
		
		Element child_element = advert_position_node.addElement("default_material");
		
		long l = new Date().getTime();
		child_element.addAttribute("id", String.valueOf(l));
		child_element.addAttribute("filetype", m_type);
		child_element.addAttribute("filepath", m_path);
		
		return child_element;
	}
	
	
	
	
}
