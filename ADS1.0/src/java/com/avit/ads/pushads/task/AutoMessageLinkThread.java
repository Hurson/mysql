package com.avit.ads.pushads.task;



import java.util.Date;

import com.avit.ads.pushads.task.service.PushAdsService;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.InitConfig;



// TODO: Auto-generated Javadoc
/**
 * The Class AutoMessageLinkThread.
 */
public class AutoMessageLinkThread extends Thread {
	
	/** 
	 * 调用PushAdsService.sendMessageLinkAds(),投放字幕和链接广告
	 */
	public void run(){

		while(true)
		{
			try{
				PushAdsService pushAdsService;
				Date loopDate = new Date();
				pushAdsService= (PushAdsService)ContextHolder.getApplicationContext().getBean("PushAdsService");
				pushAdsService.sendMessageLinkAds(loopDate,ConstantsAdsCode.PUSH_MESSAGE,"","");
				pushAdsService.sendMessageLinkAds(loopDate,ConstantsAdsCode.PUSH_LINK,"","");
				
				//pushAdsService.sendMessageLinkAds(second,String adsTypeCode,String adsResFilepath, String adsTargetPath);
				Thread.sleep(InitConfig.getAdsConfig().getPreSecond()*10000);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}finally
			{
			}
		}
	}	
}
