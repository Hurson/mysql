package com.avit.ads.util;

import java.io.File;

public class ConstantsAdsCode {
	
	/** 防止被实例化*/
	private ConstantsAdsCode(){
		
	}
	public static final String STARTDEFAULT = "0";
	public static final String START24 = "1";
	
	public static final String OCGOUTPUT ="ocgoutput";
	public static final String UIPGM ="uipgm";
	public static final String UNTPGM ="untpgm";
	public static final String RECOMMEND ="recommend";
		
	public static final boolean WIN= false;
	//广告资源压缩目录 
	public static final String ADVRESOURCE_HD_PATH = "advResource-c" + "/" +"htmldata"+"/"+"advresource";
	public static final String ADVRESOURCE_SD_PATH = "advResource-a" + "/"+"htmldata"+"/"+"advresource";
	//广告开机画面压缩 
	public static final String START_RESOURCE_PATH = "startpic";
	
	//广告标清资源包名称  广播收听背景
	public static final String ADVRESOURCE_A = "advResource-a.dat";
	//广告高清资源包名称  广播收听背景
	public static final String ADVRESOURCE_C = "advResource-c.dat";
	//广告标清资源包 vod待机画面名称 
	public static final String VOD_LOADING_FILE = "vod_loading.jpg";
	public static final String PUSH_STARTSTB = "0100";
	
	/** 开机图片广告 **/
	public static final String PUSH_STARTSTB_SD = "01001";
	public static final String PUSH_STARTSTB_HD = "01002";
	
	/** 开机视频广告 **/
	public static final String PUSH_STARTSTB_VIDEO_SD = "01003";
	public static final String PUSH_STARTSTB_VIDEO_HD = "01004";
	
	public static final String PUSH_STARTSTB_SD_VIDEO_FILE = "initVideo-a.ts";
	public static final String PUSH_STARTSTB_HD_VIDEO_FILE = "initVideo-c.ts";
	
	public static final String PUSH_STARTSTB_SD_IFRAME_FILE = "initPic-a.iframe";
	public static final String PUSH_STARTSTB_HD_IFRAME_FILE = "initPic-c.iframe";
	
	/** 高清开机推荐广告位编码*/
	public static final String PUSH_RECOMMEND = "02083";
	public static final String HOT_RECOMMEND_DIR ="recommend";//播发文件夹名称
	public static final String HOT_RECOMMEND_INAGE="bj.jpg";//播发文件图片
	public static final String HOT_RECOMMEND_JS="keyEvent.js";//播发文件js
	public static final String HOT_RECOMMEND_HTML="index.htm";//播发文件页面
	
	//直播下排广告默认素材名称
	public static final String LIVE_UNDER_HD_FILE1 = "initImgAd0.jpg";
	public static final String LIVE_UNDER_HD_FILE2 = "initImgAd1.jpg";
	public static final String LIVE_UNDER_HD_FILE3 = "initImgAd2.jpg";
	
	public static final String LIVE_UNDER_HD_FILE_PREFIX = "initImgAd";
	public static final String LIVE_UNDER_HD_FILE_POSTFIX = ".jpg"; 
			
	
	//单向字幕
	public static final String PUSH_MESSAGE = "0501";
	//单向链接
	public static final String PUSH_LINK = "0502";
	//单向主菜单广告
	public static final String PUSH_MENU = "0201";
	
	//NVOD菜单广告
	public static final String NVOD_MENU = "02402";
	//挂角广告
	public static final String CORNER_APPROACH = "02432";
	
	public static final String PUSH_MENU_HD = "02012";
	public static final String PUSH_MENU_SD = "02011";
	
	public static final String PUSH_MENU_HD_FILE = "indexAd_";
	public static final String PUSH_MENU_SD_FILE = "indexAd_sd_";
	
	public static final String CORNER_APPROACH_FILE = "corner*.png";
	//单向菜单视频外框广告  
	public static final String PUSH_MENU_FRAME = "0210";
	public static final String PUSH_MENU_FRAME_HD = "02102";
	public static final String PUSH_MENU_FRAME_SD = "02101";
	
	public static final String PUSH_MENU_FRAME_HD_FILE = "tvLogoAd.png";
	public static final String PUSH_MENU_FRAME_SD_FILE = "tvLogoAd_sd.png";
	
	//音频背景广告
	public static final String PUSH_FREQUENCE_SD = "02061";
	public static final String PUSH_FREQUENCE_HD = "02062";
	
	//频道字幕广告
	public static final String PUSH_CHANNEL_SUBTITLE = "02302";
	
	//菜单字幕广告
	public static final String PUSH__SUBTITLE = "02301";
	
	//直播下排广告
	public static final String PUSH_LIVE_UNDER_HD = "01012";
	
	//单向EPG
	public static final String PUSH_EPG = "0403";
	//当前后续信息菜单广告
	public static final String PUSH_MINIEPG = "0404";
	//音量条广告
	public static final String PUSH_VOLUMN = "0405";
	//轮询广告
	public static final String PUSH_LOOP = "0406";
	
	//点播菜单广告  CPS 
	public static final String PUSH_VOD_MENU = "0407";
	//回看菜单广告 ？ 
	public static final String PUSH_VOD_BACKTV = "0408";
	
	public static final String CPS_VOD_MENU = "0211";
	public static final String CPS_VOD_MENU_SD = "02111";
	public static final String CPS_VOD_MENU_HD = "02112";
	public static final String CPS_VOD_ASSET = "0212";
	public static final String CPS_LOOKBACK_MENU = "0207";
	public static final String NPVR_MENU = "02074";
	//
	
	/** 字幕广告位*/
	public static final String SUBTITLE_CODE = "0217";

	/** 按频道播放的时候的广告位的集合*/
	
	public static final String[] CHANNEL_AD_CODE = {"0207", "0208", "0209"};
	
	/** 点播的广告位的集合*/
	public static final String[] SEED_PLAY_AD_CODE = {"0212", "0213", "0214", "0215", "0216", "0217"};
	//开机,单向字幕，单向主菜单，单向主菜单外框，单向EPG，单向当前后续，单向音量条，轮训，点播菜单CPS ，回看菜单无视频，主菜单外框，点播菜单， 点播字幕
	// 0407=0211 0408=0207
	public static final String PUSH_ADS="0100,0501,0401,0402,0403,0404,0405,0406,0407,0408,0210,0211,0217";
	//回看菜单广告,回看暂停广告,点播随片广告,调查问卷,点播暂停 ,点播挂角
	public static final String HTTP_ADS="0207,0209,0212,0213,0215,0216";
	//频道回看广告    影片点播广告
	public static final String VIDEO_ADS="0208,0214";
	
}
