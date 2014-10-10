package com.avit.ads.util;

public class ConstantsAdsCode {
	
	/** 防止被实例化*/
	private ConstantsAdsCode(){
		
	}
	public static final String PUSH_STARTSTB = "0100";
	/** 标清开机广告位编码 */
	public static final String PUSH_STARTSTB_SD = "01001";
	/** 高清开机广告位编码 */
	public static final String PUSH_STARTSTB_HD = "01002";
	
	//单向字幕
	public static final String PUSH_MESSAGE = "0501";
	//单向链接
	public static final String PUSH_LINK = "0502";
	//单向主菜单广告
	public static final String PUSH_MENU = "0201";
	//单向菜单视频外框广告  
	public static final String PUSH_MENU_FRAME = "0402";
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
	
	//开机
	public static final String CMA_START="START";
	public static final String CMA_START_POSITION_CODE="01002";
	//主菜单 及外框
	public static final String CMA_MAINMENU="MAINMENU";
	public static final String CMA_MAINMENU_POSITION_CODE="02012";
	public static final String CMA_MAINMENU_FRAME_POSITION_CODE="02102";
	
	//预告提示
	public static final String CMA_GUIDE="GUIDE";
	public static final String CMA_GUIDE_POSITION_CODE="02052";
	//音量条 
	public static final String CMA_VOLUME="VOLUME";
	public static final String CMA_VOLUME_POSITION_CODE="02042";
	//导航条 
	public static final String CMA_MINIEPG="MINIEPG";
	public static final String CMA_MINIEPG_POSITION_CODE="02022";
	//导航条 
	public static final String CMA_DVB="DVB";
	public static final String CMA_DVB_POSITION_CODE="02022";
	//列表 
	public static final String CMA_LIST="LIST";
	public static final String CMA_LIST_POSITION_CODE="02032";
	
	
	//收藏 无 serviceid
	public static final String CMA_FAVORITEB="FAVORITEB";
	public static final String CMA_FAVORITEB_POSITION_CODE="02034";
	//音频 无 serviceid
	public static final String CMA_RADIO="RADIO";
	public static final String CMA_RADIO_POSITION_CODE="02062";
	
	
}
