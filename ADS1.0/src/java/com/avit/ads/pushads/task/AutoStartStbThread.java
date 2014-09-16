package com.avit.ads.pushads.task;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import com.avit.ads.pushads.task.service.PushAdsService;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.DateUtil;
import com.avit.ads.util.InitConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoStartStbThread 开机广告发送.
 */
public class AutoStartStbThread extends Thread {
	
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public AutoStartStbThread(){
		
	}
	
	/**
	 * 调用PushAdsService.sendStartStbAds(),投放字幕和链接广告
	 */
	public void run(){
		
		while(true)
		{
			try{
				PushAdsService pushAdsService;
				Date loopDate = new Date();
				loopDate = DateUtil.addSecond(loopDate,0-InitConfig.getAdsConfig().getPreSecond());
				pushAdsService= (PushAdsService)ContextHolder.getApplicationContext().getBean("PushAdsService");
				

				//投放高清开机视频广告
				pushAdsService.sendStartHdVideoAds(loopDate);
				
				//投放高清开机图片广告
				pushAdsService.sendStartHdPicAds(loopDate);
				
				//投放标清开机图片广告
				pushAdsService.sendStartSdPicAds(loopDate);
				
				//投放高清广播收听背景广告
				pushAdsService.sendHdAudioAds(loopDate);
				
				//投放标清广播收听背景广告
				pushAdsService.sendSdAudioAds(loopDate);
				
				//投放高清首页热点推荐广告
				pushAdsService.sendHdRecomendAds(loopDate);
				
				//投放开机广告
//				pushAdsService.sendStartStbAds(loopDate,InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(0).getAdsCode());
//				pushAdsService.sendStartStbAds(loopDate,InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(1).getAdsCode());
						
				//广播收听背景
//				pushAdsService.sendAudioAds(loopDate,InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(2).getAdsCode());
//				pushAdsService.sendAudioAds(loopDate,InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(3).getAdsCode());

				
				//热点推荐
//				pushAdsService.sendHotRecommendAds(loopDate,ConstantsAdsCode.PUSH_RECOMMEND);
				Thread.sleep(60000);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}finally
			{
			}
		}
	}
	
	/**
	 * Gets the response.
	 *
	 * @param url the url
	 * @return the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getResponse(String url) throws IOException{
		StringBuffer sb = new StringBuffer();
		try{			
			URL u = new URL(url);		
			URLConnection conn = u.openConnection();
			conn.setConnectTimeout(5000);
			conn.connect();	
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String tmp = "";
			while((tmp = br.readLine())!= null){
				sb.append(tmp);
			}
		}catch(Exception ex){
			
		}
		
		return sb.toString();		
	}
}
