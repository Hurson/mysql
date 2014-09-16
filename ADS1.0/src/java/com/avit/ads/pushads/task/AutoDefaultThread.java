package com.avit.ads.pushads.task;


import java.util.Date;

import com.avit.ads.pushads.task.service.PushAdsService;
import com.avit.ads.util.ContextHolder;

// TODO: Auto-generated Javadoc
/**
 * 素材补空线程，准备默认广告素材及临时文件.
 */
public class AutoDefaultThread extends Thread {
	
	
	/** The end time. */
	private Date endTime;
	
	/** The ads typecode. */
	private String adsTypecode;
	
	/**
	 * 初始化参数，作为线程运行参数.
	 *
	 * @param endTime 播出单结束时间
	 * @param adsTypeCode 广告位编码
	 */
	public AutoDefaultThread(Date endTime,String adsTypecode){
		this.endTime=endTime;
		this.adsTypecode =adsTypecode;
	}

	/** 
	  * 根据线程参数endTime，adsTypeCode，adsConfigFile,String adsResFilepath，执行PushAdsService.sendAllAds
	 * 查询结束播出单，补默认素材，生成投放文件，拷贝素材文件至临时目录
	 */
	public void run(){
		
			try{
				PushAdsService pushAdsService= (PushAdsService)ContextHolder.getApplicationContext().getBean("PushAdsService");
				pushAdsService.sendAllDefaultAds(endTime,adsTypecode);
				//Thread.sleep(InitConfig.getAdsConfig().getPreSecond());
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
}
