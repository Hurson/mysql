package com.avit.ads.pushads.task;


import java.util.Date;

import com.avit.ads.pushads.task.service.PushAdsService;
import com.avit.ads.util.ContextHolder;

// TODO: Auto-generated Javadoc
/**
 * 投放线程，准备投放素材及临时文件
 */
public class AutoThread extends Thread {
	
	
	private Date startTime;
	private String adsTypeCode;
	
	/**
	 * 初始化参数，作为线程运行参数.
	 *
	 * @param startTime 播出单开始时间
	 * @param adsTypeCode 广告位编码
	 */
	public AutoThread(Date startTime,String adsTypeCode){
		this.startTime=startTime;
		this.adsTypeCode =adsTypeCode;
	}

	/** 
	 * 根据线程参数startTime，adsTypeCode，adsConfigFile,String adsResFilepath，执行PushAdsService.sendAllAds
	 * 查询带投放播出单，生成投放文件，拷贝素材文件至临时目录
	 */
	public void run(){
			try{
				PushAdsService pushAdsService;
				pushAdsService= (PushAdsService)ContextHolder.getApplicationContext().getBean("PushAdsService");
				pushAdsService.sendAllAds(startTime, adsTypeCode);
				//Thread.sleep(Integer.parseInt(InitConfig.getConfigMap().get("sleepTime")));
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
	}
}
