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
	
	private static AdsConfig adsConfig;
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
		
		//，回放频道 产品 回看栏目
		//dataInitService.initChannelNpvr();
		//dataInitService.initLookbackCategory();
		
		//根据配置文件广告位编码查询默认素材，并设置默认素材
		
		initTempPath();
		
		
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
	public static Ocg getOcgConfig(String areaCode)
	{
		Ocg defaultOcg=null;
		for (int i=0;i<adsConfig.getOcgList().size();i++)
		{
			if (areaCode.equals(adsConfig.getOcgList().get(i).getAreaCode()))
			{
				return adsConfig.getOcgList().get(i);
			}
			if ("0".equals(adsConfig.getOcgList().get(i).getAreaCode()))
			{
				defaultOcg=adsConfig.getOcgList().get(i);
			}
		}
		return defaultOcg;
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

	public static void setConfigMap(HashMap<String, String> configMap) {
		InitConfig.configMap = configMap;
	}
	public static String getAdsResourcePath() {
		return adsConfig.getAdResource().getAdsResourcePath();
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
		return adsConfig.getVideoPump().get(0).getUrl();
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
		(new File(adsConfig.getVideoPump().get(0).getTempPath())).mkdirs();
		for (int i=0;i<adsConfig.getDtvList().size();i++)
		{
			(new File(adsConfig.getDtvList().get(i).getTempPath())).mkdirs();
		}
	}
}
