package com.avit.ads.dtmb.service;



// TODO: Auto-generated Javadoc
/**
 * The Interface ClassifyPushAdsService.
 * 投放广告service
 */
public interface PushDtmbAdService {
	
	/**
	 * 实时广告投放流程
	
	 */
	public void sendRealTimeDTMBAd();
	
	/**
	 * 开机视频广告投放流程
	 */
	public void sendStartVideoAds();
	/**
	 * 开机图片广告投放流程
	 */
	public void sendStartPicAds();
	/**
	 * 广播背景和直播下排广告投放流程
	 */
	public void sendAdResourceAds();
	
	/**
	 * 菜单字幕类广告投放流程
	 */
	public void sendSubtitleAds();
	/**
	 * 频道字幕广告投放流程
	 */
	public void sendChannelSubtitleAds();
	
}
