package com.avit.ads.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avit.ads.dtmb.cache.SendAdsTypesMap;
import com.avit.ads.pushads.task.cache.SendAdsElementMap;
import com.avit.ads.pushads.task.service.DataInitService;
import com.avit.ads.util.bean.Ads;
import com.avit.ads.util.bean.AdsConfig;
import com.avit.ads.util.bean.Cps;
import com.avit.ads.util.bean.Dtv;
import com.avit.ads.util.bean.HttpServer;
import com.avit.ads.util.bean.Ocg;
import com.avit.ads.util.bean.VideoPump;
import com.avit.ads.xml.JaxbXmlObjectConvertor;

public class InitConfig {
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 系统配置
	 */
	private static HashMap<String,String> configMap;
	/** 区域编码与NID关系MAP */
	private static HashMap<String,String> areaMap;
	
	private static AdsConfig adsConfig;
	DataInitService dataInitService;
	//dataInitService= (DataInitService)ContextHolder.getApplicationContext().getBean("DataInitService");

	/**
	 * 初始化系统配置
	 */
	public void initConfig(){
	
		//读取初始化配置文件
		AdsConfig adsConfigTemp = new AdsConfig();
		try{
			InputStream is=this.getClass().getResourceAsStream("/ads.xml");
			StringBuffer sb = new StringBuffer();    
		    BufferedReader br = null;  
		    try {
		            br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		            String tmp = "";
		            while((tmp=br.readLine())!= null) {
		                    sb.append(tmp);
		            }
		    } 
		    catch (IOException e) {
		             throw e;
		    } 
		    finally {
			    if (br != null) {           
			           br.close();
			    }
		    }
		     //return sb.toString();
	 	    is.close();
	 	  
	 	   adsConfigTemp= (AdsConfig)JaxbXmlObjectConvertor.getInstance().fromXML( sb.toString());
	 	   adsConfigTemp.getRealTimeAds().setAdsList(adsConfigTemp.getRealTimeAds().getAdsList());
	 	   adsConfigTemp.getCpsAds().setAdsList(adsConfigTemp.getCpsAds().getAdsList());
	 	   adsConfigTemp.getNpvrAds().setAdsList(adsConfigTemp.getNpvrAds().getAdsList());
	 	   adsConfig=adsConfigTemp;
	 	  
		}catch(IOException ie){
			ie.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		//读取properties配置参数
		configMap = new HashMap<String,String>();
		try{
			InputStream is=this.getClass().getResourceAsStream("/system.properties");
			Properties prop = new Properties();
	 	    prop.load(is);
	 	    is.close();
	 	   Enumeration<?> en = prop.propertyNames();
	 	  String key = "";
	 	  String value = "";
	 	   while(en.hasMoreElements()){
	 		  key = (String)en.nextElement();
	 		  value = new String(prop.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
	 		  configMap.put(key, value);
	 	   }
		}catch(IOException ie){
			ie.printStackTrace();
		}
		if (dataInitService==null)
		{
			dataInitService= (DataInitService)ContextHolder.getApplicationContext().getBean("DatainitService");
		}
		//读取数据库 初始化频道
		dataInitService.initChannel();
		/**
		 * 读取数据库 初始化无线频道
		 */
		dataInitService.initDtmbChannel();
		/**
		 * 初始化无线广告位
		 */
		dataInitService.initDtmbAdPosition();
		SendAdsTypesMap.initRealTimeAdsMap();
		dataInitService.initDtmbAreaTSFile();
		
		//读取数据库 初始化NVOD主界面分类
		dataInitService.initNvodMenuType();
		//读取数据库 初始化默认素材 
		dataInitService.initAdDefalult();
		dataInitService.initAdvertPosition();
		
		//，回放频道 产品 回看栏目
		//dataInitService.initChannelNpvr();
		//dataInitService.initLookbackCategory();
		
		//根据配置文件广告位编码查询默认素材，并设置默认素材
		
		
		initTempPath();
		
		//设置区域编码与NID关系MAP
		//setAreaMap(adsConfig.getOcgList());
		
	}
	
	
	/**
	 * 刷新系统配置
	 */
	public void refreshConfig(){
		//读取初始化配置文件
		AdsConfig adsConfigTemp = new AdsConfig();
		try{
			InputStream is=this.getClass().getResourceAsStream("/ads.xml");
			StringBuffer sb = new StringBuffer();    
		    BufferedReader br = null;  
		    try {
		            br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		            String tmp = "";
		            while((tmp=br.readLine())!= null) {
		                    sb.append(tmp);
		            }
		    } 
		    catch (IOException e) {
		             throw e;
		    } 
		    finally {
			    if (br != null) {           
			           br.close();
			    }
		    }
		     //return sb.toString();
	 	    is.close();
	 	  
	 	   adsConfigTemp= (AdsConfig)JaxbXmlObjectConvertor.getInstance().fromXML( sb.toString());
	 	   adsConfigTemp.getRealTimeAds().setAdsList(adsConfigTemp.getRealTimeAds().getAdsList());
	 	   adsConfigTemp.getCpsAds().setAdsList(adsConfigTemp.getCpsAds().getAdsList());
	 	   adsConfigTemp.getNpvrAds().setAdsList(adsConfigTemp.getNpvrAds().getAdsList());
	 	   adsConfig=adsConfigTemp;
	 	  
		}catch(IOException ie){
			ie.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		//读取properties配置参数
		configMap = new HashMap<String,String>();
		try{
			InputStream is=this.getClass().getResourceAsStream("/system.properties");
			Properties prop = new Properties();
	 	    prop.load(is);
	 	    is.close();
	 	   Enumeration<?> en = prop.propertyNames();
	 	  String key = "";
	 	  String value = "";
	 	   while(en.hasMoreElements()){
	 		  key = (String)en.nextElement();
	 		  value = new String(prop.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
	 		  configMap.put(key, value);
	 	   }
		}catch(IOException ie){
			ie.printStackTrace();
		}
		dataInitService= (DataInitService)ContextHolder.getApplicationContext().getBean("DatainitService");
		//读取数据库 初始化频道
		dataInitService.initChannel();
		//读取数据库 初始化无线频道
		dataInitService.initDtmbChannel();
		//读取数据库 初始化NVOD主界面分类
		dataInitService.initNvodMenuType();
		//读取数据库 初始化默认素材 
		dataInitService.initAdDefalult();
		/**
		 * 读取数据库 初始化无线频道
		 */
		dataInitService.initDtmbChannel();
		/**
		 * 初始化无线广告位
		 */
		dataInitService.initDtmbAdPosition();
		dataInitService.initDtmbAreaTSFile();
		
		SendAdsElementMap.refreshSendAdsElementMap();
		//dataInitService.initAdvertPosition();
		//，回放频道 产品 回看栏目
		//dataInitService.initChannelNpvr();
		//dataInitService.initLookbackCategory();
		
		//根据配置文件广告位编码查询默认素材，并设置默认素材
		
		//设置区域编码与NID关系MAP
		//setAreaMap(adsConfig.getOcgList());
		
	}
	public static AdsConfig getAdsConfig() {
		return adsConfig;
	}
	public static void setAdsConfig(AdsConfig adsConfig) {
		InitConfig.adsConfig = adsConfig;
	}
	
	
	public static List<Cps> getCpsConfig()
	{
		return adsConfig.getCps();
	}
	public static Ocg getOcgConfig(String type)
	{
		List<Ocg> ocgList = adsConfig.getOcgList();
		if(null != ocgList && ocgList.size() > 0){
			for(Ocg ocg : ocgList){
				if(type.equals(ocg.getStreamId())){
					return ocg;
				}
			}
		}
		return null;
	}
	public static Dtv getDtvConfig(String areaCode)
	{
		Dtv defaultDtv=null;
		for (int i=0;i<adsConfig.getOcgList().size();i++)
		{
			if (areaCode.equals(adsConfig.getDtvList().get(i).getAreaCode()))
			{
				return adsConfig.getDtvList().get(i);
			}
			if ("0".equals(adsConfig.getDtvList().get(i).getAreaCode()))
			{
				defaultDtv=adsConfig.getDtvList().get(i);
			}
		}
		return defaultDtv;
	}
	public static HashMap<String, String> getConfigMap() {
		return configMap;
	}
	public static String getConfigProperty(String name) {
		return configMap.get(name);
	}
	public static void setConfigMap(HashMap<String, String> configMap) {
		InitConfig.configMap = configMap;
	}
	public static String getAdsResourcePath() {
		return adsConfig.getAdResource().getAdsResourcePath();
	}
	
	public static HashMap<String, String> getAreaMap() {
		return areaMap;
	}


	public static void setAreaMap(HashMap<String, String> areaMap) {
		InitConfig.areaMap = areaMap;
	}


	public static List<VideoPump> getVideoPumpConfig()
	{
		return adsConfig.getVideoPump();
	}
	public static HttpServer getHttpServer()
	{
		return adsConfig.getHttpServer();
	}
	public static String getServerType(String adsTypeCode,String fileType)
	{		
		if (fileType!=null && ConstantsHelper.MP4_FILE_POSTFIX.equals(fileType))
		{
			return ConstantsHelper.FILE_UPLOAD_TYPE_VIDEO;
		}
		if (ConstantsAdsCode.PUSH_ADS.indexOf(adsTypeCode)>=0)
		{
			return ConstantsHelper.FILE_UPLOAD_TYPE_LOCAL;
		}
		if (ConstantsAdsCode.HTTP_ADS.indexOf(adsTypeCode)>=0)
		{
			return ConstantsHelper.FILE_UPLOAD_TYPE_HTTP;
		}
		if (ConstantsAdsCode.VIDEO_ADS.indexOf(adsTypeCode)>=0)
		{
			return ConstantsHelper.FILE_UPLOAD_TYPE_VIDEO;
		}		
		return ConstantsHelper.FILE_UPLOAD_TYPE_LOCAL;
	}
	public static String getContentPath(String adsTypeCode)
	{		
		if (ConstantsAdsCode.HTTP_ADS.indexOf(adsTypeCode)>=0)
		{
			return adsConfig.getHttpServer().getUrl();
		}
		return "";
	}
	public static List<Ads> getAdList()
	{
		List tempList =  new ArrayList<Ads>();
		tempList.addAll(adsConfig.getRealTimeAds().getAdsList());
		tempList.addAll(adsConfig.getCpsAds().getAdsList());
		tempList.addAll(adsConfig.getNpvrAds().getAdsList());
		return tempList;
	}
	public static Ads getAdsByCode(String adsTypeCode)
	{
		List<Ads> tempList =  new ArrayList<Ads>();
		tempList.addAll(adsConfig.getRealTimeAds().getAdsList());
		tempList.addAll(adsConfig.getCpsAds().getAdsList());
		tempList.addAll(adsConfig.getNpvrAds().getAdsList());
		tempList.addAll(adsConfig.getUnRealTimeAds().getAdsList());
		for (int i=0;i<tempList.size();i++)
		{
			if (tempList.get(i).getAdsCode().equals(adsTypeCode))
			{
				return tempList.get(i);
			}
		}
		return null;
	}
	public void initTempPath()
	{				
		
		(new File(adsConfig.getCpsAds().getAdsTempPath())).mkdirs();
		(new File(adsConfig.getNpvrAds().getAdsTempPath())).mkdirs();
	
		List<String> areaList=InitAreas.getInstance().getAreas();
		for(String areaCode:areaList){
			//单向实时
			(new File(adsConfig.getRealTimeAds().getAdsTempPath()+File.separator+areaCode)).mkdirs();
			(new File(adsConfig.getRealTimeAds().getAdsTempConfigPath()+File.separator+areaCode)).mkdirs();
			
			//单向非实时
			(new File(adsConfig.getUnRealTimeAds().getAdsTempPath()+File.separator+areaCode)).mkdirs();
			//开机图片
			(new File(adsConfig.getUnRealTimeAds().getAdsIframeSDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.START_RESOURCE_PATH)).mkdirs();
			(new File(adsConfig.getUnRealTimeAds().getAdsIframeHDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.START_RESOURCE_PATH)).mkdirs();
			
			//广播背景
			(new File(adsConfig.getUnRealTimeAds().getAdsaudioHDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.ADVRESOURCE_HD_PATH )).mkdirs();
			(new File(adsConfig.getUnRealTimeAds().getAdsaudioSDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.ADVRESOURCE_SD_PATH)).mkdirs();
			
			//无线单向实时
			(new File(adsConfig.getDrealTimeAds().getAdsTempPath()+File.separator+areaCode)).mkdirs();
			(new File(adsConfig.getDrealTimeAds().getAdsTempConfigPath()+File.separator+areaCode)).mkdirs();
			
			//无线单向非实时
			(new File(adsConfig.getDunRealTimeAds().getAdsTempPath()+File.separator+areaCode)).mkdirs();
			//无线开机图片
			(new File(adsConfig.getDunRealTimeAds().getAdsIframeSDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.START_RESOURCE_PATH)).mkdirs();
			(new File(adsConfig.getDunRealTimeAds().getAdsIframeHDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.START_RESOURCE_PATH)).mkdirs();
			
			//无线广播背景
			(new File(adsConfig.getDunRealTimeAds().getAdsaudioHDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.ADVRESOURCE_HD_PATH )).mkdirs();
			(new File(adsConfig.getDunRealTimeAds().getAdsaudioSDTempPath()+File.separator+areaCode+File.separator+ConstantsAdsCode.ADVRESOURCE_SD_PATH)).mkdirs();
		}
		
		for (int i=0;i<adsConfig.getDtvList().size();i++)
		{
			(new File(adsConfig.getDtvList().get(i).getTempPath())).mkdirs();
		}

		(new File(configMap.get(ConstantsHelper.DEST_FILE_PATH))).mkdirs();
		(new File(configMap.get(ConstantsHelper.LOG_FILE_PATH))).mkdirs();
		File untLogFile = new File(configMap.get(ConstantsHelper.LOG_FILE_PATH)+File.separator+ConstantsHelper.LOG_FILE_NAME);
		if(!untLogFile.exists()){
			try {
				untLogFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		(new File(configMap.get(ConstantsHelper.D_DEST_FILE_PATH))).mkdirs();
		(new File(configMap.get(ConstantsHelper.D_LOG_FILE_PATH))).mkdirs();
		File dUntLogFile = new File(configMap.get(ConstantsHelper.D_LOG_FILE_PATH)+File.separator+ConstantsHelper.LOG_FILE_NAME);
		if(!untLogFile.exists()){
			try {
				dUntLogFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置区域编码与NID关系MAP
	 * @param ocgList
	 */
//	private void setAreaMap(List<Ocg> ocgList){
//		if(ocgList != null && ocgList.size()>0){
//			areaMap = new HashMap<String,String>();
//			for(Ocg ocg : ocgList){
//				areaMap.put(ocg.getAreaCode(), ocg.getNid());
//			}
//		}
//	}
	 
}
