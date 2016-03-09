package com.avit.ads.dtmb.thread;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avit.ads.dtmb.service.PushDtmbAdService;
import com.avit.ads.pushads.task.service.DataInitService;
import com.avit.ads.util.ContextHolder;


public class DtmbThread extends Thread {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public void run(){

		PushDtmbAdService pushDtmbAdService= (PushDtmbAdService)ContextHolder.getApplicationContext().getBean("pushDtmbAdService");
		
		try{
			/**
			 * 投放实时广告
			 */
			log.info("投放单向实时....");
			pushDtmbAdService.sendRealTimeDTMBAd();
			/**
			 * 投放开机图片广告
			 */
			pushDtmbAdService.sendStartPicAds();
			/**
			 * 投放开机视频广告
			 */
			pushDtmbAdService.sendStartVideoAds();
			/**
			 * 投放音频广播背景和直播下排广告
			 */
			pushDtmbAdService.sendAdResourceAds();
			/**
			 * 投放投放菜单字幕广告
			 */
			pushDtmbAdService.sendSubtitleAds();
			/**
			 * 投放频道字幕广告
			 */
			pushDtmbAdService.sendChannelSubtitleAds();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
