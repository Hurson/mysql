package com.avit.ads.util;

public class ConstantsAdsCode {
	
	/** 防止被实例化*/
	private ConstantsAdsCode(){
		
	}
	public static final String PUSH_STARTSTB = "0100";
	public static final String PUSH_STARTSTB_SD = "01001";
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
	public static final String[] CHANNEL_AD_CODE = {"0208", "0209"};
	/** 按频道播放的时候的广告位的集合, 没有问卷广告位*/
	public static final String[] CHANNEL_AD_CODE_NO_QUESTIONNAIRE = {"0208", "0209"};
	
	/** 点播的广告位的集合*/
	public static final String[] SEED_PLAY_AD_CODE = {"0213", "0214", "0215", "0216", "0217"};
	/** 点播的广告位的集合, 没有问卷广告位*/
	public static final String[] SEED_PLAY_AD_CODE_NO_QUESTIONNAIRE = { "0214", "0215", "0216", "0217"};
	//开机,单向字幕，单向主菜单，单向主菜单外框，单向EPG，单向当前后续，单向音量条，轮训，点播菜单CPS ，回看菜单无视频，主菜单外框，点播菜单， 点播字幕
	// 0407=0211 0408=0207
	public static final String PUSH_ADS="0100,0501,0401,0402,0403,0404,0405,0406,0407,0408,0210,0211,0217";
	//回看菜单广告,回看暂停广告,点播随片广告,调查问卷,点播暂停 ,点播挂角
	public static final String[] HTTP_ADS={"02092","02132","02152","02162","02094","02131","02151","02161"};
	//频道回看广告    影片点播广告
	public static final String[] VIDEO_ADS={"02082","02142","02084","02141"};
	
	public static final String WATCH_VIDEOTAPES = "0208";
	
	/** 高清回看广告位集合*/
	public static final String[] HD_LOOKBACK_ADVERT_POSITION = {"02092","02082"};
	
	/** 标清回看广告位集合*/
	public static final String[] SD_LOOKBACK_ADVERT_POSITION = {"02091"};
	
	/** 高清回放广告位集合*/
	public static final String[] HD_PLAYBACK_ADVERT_POSITION = {"02094","02084"};
	
	/** 高清影片广告位集合*/
	public static final String[] HD_ASSET_ADVERT_POSITION = {"02132", "02142", "02152", "02162", "02172"};
	
	/** 高清影片广告位集合 --- 无问卷*/
	public static final String[] HD_ASSET_ADVERT_POSITION_NO_Q = {"02142", "02152", "02162", "02172"};
	
	/** 标清影片广告位集合*/
	public static final String[] SD_ASSET_ADVERT_POSITION = {"02131", "02141", "02151", "02161", "02171"};
	
	/** 标清影片广告位集合  --- 无问卷*/
	public static final String[] SD_ASSET_ADVERT_POSITION_NO_Q = {"02141", "02151", "02161", "02171"};
	
	/** 广告位包: 回看回放插播 */
	public static final String LOOKBACK_ADVERT_POSITION_INSERT = "0208";
	
	/** 广告位包: 回看回放暂停 */
	public static final String LOOKBACK_ADVERT_POSITION_PAUSE= "0209";
	public static final String HD_LOOKBACK_ADVERT_POSITION_PAUSE= "02092";
	public static final String SD_LOOKBACK_ADVERT_POSITION_PAUSE= "02091";
	public static final String HD_NPVR_ADVERT_POSITION_PAUSE= "02094";
	public static final String SD_NPVR_ADVERT_POSITION_PAUSE= "02093";
	
	
	/** 广告位包: 问卷 */
	public static final String ASSET_ADVERT_POSITION_QUESTIONNAIRE= "0213";
	
	/** 广告位包: 挂角 */
	public static final String ASSET_ADVERT_POSITION_TOPRIGHT = "0216";
	public static final String SD_ASSET_ADVERT_POSITION_TOPRIGHT = "02161";
	public static final String HD_ASSET_ADVERT_POSITION_TOPRIGHT = "02162";
	
	/** 广告位包: 插播 */
	public static final String ASSET_ADVERT_POSITION_INSERT = "0214";
	public static final String SD_ASSET_ADVERT_POSITION_INSERT = "02141";
	public static final String HD_ASSET_ADVERT_POSITION_INSERT = "02142";
	
	/** 广告位包: 暂停 */
	public static final String ASSET_ADVERT_POSITION_PAUSE = "0215";
	public static final String SD_ASSET_ADVERT_POSITION_PAUSE = "02151";
	public static final String HD_ASSET_ADVERT_POSITION_PAUSE = "02152";
	/** 广告位包: 字幕 */
	public static final String ASSET_ADVERT_POSITION_SUBTITLE = "0217";
	public static final String SD_ASSET_ADVERT_POSITION_SUBTITLE = "02171";
	public static final String HD_ASSET_ADVERT_POSITION_SUBTITLE = "02172";
	
	public static final String DEAFULT_RESOURCE_P = "(02082,02084,02091,02092,02093,02094,02142,02151,02152,02162,02172)";
	
	public static final String HISTORY_SEQ_PAUSEFALG = "9";
	public static final String HISTORY_SEQ_TOPRIGHTFALG = "8";
	public static final String HISTORY_SEQ_SUBTITLEFALG = "7";
	
	public static final String ZHENGZHOU_HTTP="01";
	public static final String KAIFENG_HTTP="02";
	public static final String LUOYANG_HTTP="03";
	public static final String PINGDINGSHAN_HTTP="04";
	public static final String ANYANG_HTTP="05";
	public static final String JIAOZUO_HTTP="06";
	public static final String HEBI_HTTP="07";
	public static final String XINXIANG_HTTP="08";
	public static final String PUYANG_HTTP="09";
	public static final String XUCHANG_HTTP="10";
	public static final String LUOHE_HTTP="11";
	public static final String SANMENGXIA_HTTP="12";
	public static final String NANYANG_HTTP="13";
	public static final String SHANGQIU_HTTP="14";
	public static final String XINYANG_HTTP="15";
	public static final String ZHOUKOU_HTTP="16";
	public static final String ZHUMADIAN_HTTP="17";
	public static final String JIYUAN_HTTP="18";
	public static final String YUNWEISYS_HTTP="19";
	
}
