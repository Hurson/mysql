package com.avit.ads.pushads.task;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import com.avit.ads.pushads.task.bean.AdPlaylistGis;
import com.avit.ads.pushads.task.service.DataInitService;
import com.avit.ads.pushads.task.service.PushAdsService;
import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.DateUtil;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.PushFalierHelper;
import com.avit.ads.util.bean.Ads;

// TODO: Auto-generated Javadoc
/**
 * 通用投放广告线程. 除开机广告、字幕广告、链接广告外的其他广告投放
 */
public class AutoAllThread extends Thread {
	
	/** The ids. */
	String[] ids;
	
	/** 
	 * 根据配置参数，计算播出单时间=当前时间 + 提前量
	 * 根据配置文件中的广告位，分别启动素材补空线程AutoDefaultThread，准备默认广告素材及临时文件
	 * 根据配置文件中的广告位，分别启动投放线程AutoThread，准备投放素材及临时文件
	 * 执行投放处理PushAdsService.sendAdsData(),投放数据至OCG
	 * 
	 */
	public void run(){
		PushAdsService pushAdsService;
		pushAdsService= (PushAdsService)ContextHolder.getApplicationContext().getBean("PushAdsService");
		PushFalierHelper pushFalierHelper = (PushFalierHelper)ContextHolder.getApplicationContext().getBean("pushFalierHelper");
		DataInitService dataInitService = (DataInitService)ContextHolder.getApplicationContext().getBean("DatainitService");
		
		try
		{
			//系统重新启动，加载已开始发送未完成的播出单
			pushAdsService.sendAllAds(null,"1",new Date());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}finally{
			
		}
		while(true){
			try{
				int length=0;
				Date loopDate = new Date();
				loopDate = DateUtil.addSecond(loopDate,0-InitConfig.getAdsConfig().getPreSecond());

				//初始化区域频点目录
				dataInitService.initAreaTSFile();
				/*	*/
				Thread[] tdArray = new Thread[40];
				List<Ads> adsList = InitConfig.getAdList();//InitConfig.getAdsConfig().getRealTimeAds().getAdsList();
				boolean breaks = false;
				//查询过期播出单，补默认素材线程
				for(length=0;length<adsList.size();length++){
					// (ConstantsAdsCode.PUSH_STARTSTB.equals(adsList.get(length).getAdsCode()) || ConstantsAdsCode.PUSH_LINK.equals(adsList.get(length).getAdsCode()) || ConstantsAdsCode.PUSH_MESSAGE.equals(adsList.get(length).getAdsCode()))
					tdArray[length] = new AutoDefaultThread(loopDate, adsList.get(length).getAdsCode());
					tdArray[length].start();
					Thread.sleep(500);
					//length++;
				}
			
				while (!breaks) {
					breaks = true;
					for (int i = 0; i < length; i++) {
						State state = tdArray[i].getState();
						if (state != State.TERMINATED) {
							breaks = false;
						}
					}
					Thread.sleep(1000);
				}	
				//保存新增播出单，跟踪投放情况，失败则修改播出单、订单状态
				List<AdPlaylistGis> newPlayList = pushAdsService.queryNewPlayList(loopDate, adsList);
				pushFalierHelper.addNewPlayList(newPlayList);				
				
				Thread[] tArray = new Thread[40];
				length = 0;
				//查询待播出播出单，写发送缓存
				for(length=0;length<adsList.size();length++){
					tArray[length] = new AutoThread(loopDate, adsList.get(length).getAdsCode());
					tArray[length].start();
					Thread.sleep(500);
				}
				breaks = false;
				while (!breaks) {
					breaks = true;
					for (int i = 0; i < length; i++) {
						State state = tArray[i].getState();
						if (state != State.TERMINATED) {
							breaks = false;
						}
					}
					Thread.sleep(1000);
				}
				
				//汇聚后自动生成adConfigFile.js，cpsadConfigFile.js，npvradConfigFile.js，自动发布
				pushAdsService.sendAdsData();//(null, null, "", "");
				
				/*
				 * 发送UI广告
				 * 之前投放unt和ui是两个线程执行，但是都使用pushAdsService，引入的ftpService是有状态的
				 * 如果同时投放两种类型的广告，会出现同步问题，为了简单修改起见，直接放一个线程执行
				 */
				
				
				//投放高清开机视频广告
				pushAdsService.sendStartHdVideoAds(loopDate);
				
				//投放高清开机图片广告
				pushAdsService.sendStartHdPicAds(loopDate);
				
				// 高清音频背景和直播下排广告绑定投放
				pushAdsService.sendAdResourceAds(loopDate);
				
				//投放菜单字幕广告
				pushAdsService.sendSubtitleAds();
							
				//投放频道字幕广告
				pushAdsService.sendChannelSubtitleAds();
				
				
				Thread.sleep(60000);
			}catch(Exception ex){
				ex.printStackTrace();
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
