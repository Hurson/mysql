package com.dvnchina.advertDelivery.utils.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.utils.config.DirectoryUtils;


/**
 * 营销规则配置文件
 * 
 * @author 
 *
 */
public class MarkteingRuleConfig {
	
	/**
	 * 营销规则配置文件
	 */
	private static File RuleConfigureFile = new File(DirectoryUtils.getSerlvetContextPath()+"/WEB-INF/classes/ruleConfig.xml");

	/**
	 * 素材配置DOM节点
	 */
	private static Document RuleConfigureDom = null;
	
	/**
	 * 开始时间
	 */
	public static HashMap<String, ArrayList<MarketingRuleBean>> startTimeMap = null;
	
	/**
	 * 结束时间
	 */
	public static HashMap<String, ArrayList<MarketingRuleBean>> endTimeMap = null;
	

	/**
	 * 加载配置文件,初始化信息
	 */
	static{
		SAXReader reader = new SAXReader();
    	try {
    		RuleConfigureDom = reader.read(RuleConfigureFile);
			initData();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据
	 */
	private static void initData(){
		if(RuleConfigureDom == null){
			reloadConfig();
		}
		//开始加载
		initMarketingRule();
	}
	
	/**
	 * 重新加载配置文件,初始化
	 */
	private static void reloadConfig(){
		SAXReader reader = new SAXReader();
    	try {
    		RuleConfigureDom = reader.read(RuleConfigureFile);
			initData();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 开始解析
	 */
	@SuppressWarnings("unchecked")
	private static void initMarketingRule(){
		
		startTimeMap  =  new HashMap<String, ArrayList<MarketingRuleBean>>();
		String xpath="//startTime";
		List<Element> nodes = RuleConfigureDom.selectNodes(xpath);
		ArrayList<MarketingRuleBean> ruleStartTimeList = new ArrayList<MarketingRuleBean>();
		for(Element element:nodes){
			MarketingRuleBean ruleBean = new MarketingRuleBean();
			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			ruleBean.setRuleName(key);
			ruleBean.setAreaName(name);
			ruleStartTimeList.add(ruleBean);
		}
		startTimeMap.put("startTime", ruleStartTimeList);
		
		//解析结束时间
		endTimeMap  =  new HashMap<String, ArrayList<MarketingRuleBean>>();
		String xpath2="//endTime";
		List<Element> nodes2 = RuleConfigureDom.selectNodes(xpath2);
		ArrayList<MarketingRuleBean> ruleEndTimeList = new ArrayList<MarketingRuleBean>();
		for(Element element:nodes2){
			MarketingRuleBean ruleBean = new MarketingRuleBean();
			String key = element.attributeValue("key");
			String name = element.attributeValue("name");
			ruleBean.setRuleName(key);
			ruleBean.setChannelName(name);
			ruleEndTimeList.add(ruleBean);
		}
		endTimeMap.put("endTime", ruleEndTimeList);
	}
}
