package com.avit.ads.pushads.unt.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.unt.bean.AdsLink;
import com.avit.ads.pushads.unt.bean.AdsPicture;
import com.avit.ads.pushads.unt.bean.AdsSubtitle;
import com.avit.ads.pushads.unt.bean.ResourceUrl;
import com.avit.ads.pushads.unt.bean.UntConfigFile;
import com.avit.ads.pushads.unt.dao.UntDao;
import com.avit.ads.pushads.unt.service.UntService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Ocg;
import com.avit.ads.util.bean.WarnInfo;
import com.avit.ads.util.warn.WarnHelper;
import com.ipanel.http.util.HttpCommon;

@Service
public class UntServiceImpl implements UntService{
	private Log log = LogFactory.getLog(this.getClass());
	//@Inject
	private UntDao untDao;
	
	@Autowired
	private WarnHelper warnHelper;
	
	/**
	 * 添加推荐链接
	 * @param linkList
	 */
	public void addLink(List<AdsLink> linkList){
		log.info("untDao.addLink(linkList)");
	}
	
	/**
	 * 添加单向广告图片
	 * @param pictureList
	 */
	public void addPicture(List<AdsPicture> pictureList){
		//adv://adPic/filename
		log.info("untDao.addPicture(pictureList)");
	}
	
	/**
	 * 添加滚动字幕
	 * @param subtitleList
	 */
	public void addSubtitle(List<AdsSubtitle> subtitleList){
		log.info("untDao.addSubtitle(subtitleList)");
	}
	
	/**
	 * 设置广告配置文件名 ，路径 
	 * @param adsConfigFilename
	 * @param adsConfigFilepath
	 * 
	 */
	public void setConfigFile(UntConfigFile configFile){
		//adv://adConfig/adConfig.js
		untDao.setConfigFile(configFile);
	}
	/**
	 * 发送更新通知.	
	 * @param updateType 更新标识 
	 * @param filename 更新的文件名包括相对路径  1：字幕  2：推荐链接 3：配置文件 
	 */
	public void sendUpdateFlag(String updateType,String filename)
	{
		log.info("UNT::sendUpdateFlag() updateType:"+updateType+"filename:"+filename);
		//写表
		//更新通知
		
		try
		{
			log.info(InitConfig.getAdsConfig().getRealTimeAds().getUntUrl()+filename);
			getDoGetURL(InitConfig.getAdsConfig().getRealTimeAds().getUntUrl()+filename,null);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		
	}
	
	
	
	 public boolean sendUpdateFlag(String areaCode, String updateType,String filename) {
		log.info("UNT::sendUpdateFlag() updateType:"+updateType+"filename:"+filename + "areaCode: " + areaCode);
		List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
		for(Ocg ocg : ocgList){
			if(ocg.getAreaCode().equals(areaCode)){
				try{
					String untUrl = ocg.getUntUrl();
					log.info(InitConfig.getAdsConfig().getRealTimeAds().getUntUrl()+filename);
					return getDoGetURL(untUrl+filename,null);
				}catch(Exception e){
					log.error("发送UNT更新通知失败", e);
					return false;
				}
			}
		}
		return false;
	}

	public void addResourceUrl(ResourceUrl resourceUrl)
	 {
		 untDao.addResourceUrl(resourceUrl);
	 }
	 /**  
     * <p>httpClient的get请求方式</p>  
     * @param url = "https://www.99bill.com/webapp/receiveDrawbackAction.do";  
     * @param charset = ="utf-8";  
     * @return  
     * @throws Exception  
     */   
     public boolean getDoGetURL(String url, String charset) throws Exception {   
  
        HttpClient client = new HttpClient();   
        GetMethod method1 = new GetMethod(url);   
  
         if(null ==url ||!url.startsWith( "http" )) {   
             throw   new  Exception( "请求地址格式不对" );  
        }   
  
         // 设置请求的编码方式   
         if  ( null  != charset) {   
            method1.addRequestHeader( "Content-Type" ,   
                     "application/x-www-form-urlencoded; charset="  + charset);   
        }  else  {   
            method1.addRequestHeader( "Content-Type" ,   
                     "application/x-www-form-urlencoded; charset="  +  "utf-8" );   
        }   
         
         int times = 3; //三次发送不成功，则告警
	     int responsCode = 0;
	     String response = "";
	     while(times > 0 ){
        	responsCode = client.executeMethod(method1);   
        	byte[] responseBody = method1.getResponseBodyAsString().getBytes(method1.getResponseCharSet());
    		response = new String(responseBody,"utf-8" );
        	if(200 == responsCode){        //如果能访问服务器，则直接返回
        		return  true;  
        	}
        	Thread.sleep(3000); //3s后重发请求
        	times--;	        	
	     }
	     //三次连接不上，告警	
    	 warnHelper.writeWarnMsgToDb("【连续三次不能访问UNT服务器】" + "request url: " + url);
    	 return false;         
    }
	 
}
