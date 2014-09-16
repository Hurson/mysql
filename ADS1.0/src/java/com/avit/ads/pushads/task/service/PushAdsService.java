package com.avit.ads.pushads.task.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.avit.ads.pushads.task.bean.AdPlaylistGis;
import com.avit.ads.pushads.unt.bean.AdsSubtitle;
import com.avit.ads.util.bean.Ads;

// TODO: Auto-generated Javadoc
/**
 * The Interface AdGisQueueService.
 * 推送式广告素材准备
 */
public interface PushAdsService {
	
	/**
	 * Send ad gis.
	 * 载入已发送的播出单，生成更新广告位对应配置文件，复制资源文件至临时目录
	 * @param adsTypeCode 广告位编码
	 * @param state 播出单状态
	 * @param startTime 开始时间
	 */
	public void sendAllAds(String adsTypeCode,String state,Date startTime);
	
	
	/**
	 * Send ad gis.
	 * 监听推送式广告播出单，生成更新广告位对应配置文件，复制资源文件至临时目录
	 *
	 * @param startTime 播出单开始时间
	 * @param adsTypeCode 广告位编码
	 * @param adsConfigFile 广告位的配置文件
	 * @param adsResFilepath 广告资源临时路径
	 */
	public void sendAllAds(Date startTime,String adsTypeCode);
	
	/**
	 * Send ad gis default.
	 * 补空档广告，监听结束的推送式广告播出单，生成更新广告位对应配置文件，复制资源文件至临时目录
	 * 
	 * @param endTime 播出单结束时间
	 * @param adsTypeCode 广告位编码
	 * @param adsConfigFile 广告位的配置文件
	 * @param adsResFilepath 广告资源临时路径
	 */
	public void sendAllDefaultAds(Date endTime,String adsTypeCode);
	
	/**
	 * Send 合成投放推送式广告配置文件，复制资源文件至投放目录，拉起(UNT,OCG)投放.
	 *
	 * @param adsConfigFile 所有推送式广告的配置文件.js
	 * @param adsConfigFiles 各个推送式广告位的配置文件
	 * @param adsResFilepath  广告资源临时路径
	 * @param adsTargetPath 广告资源投放路径
	 */
	public void sendAdsData(String adsConfigFile,List<String> adsConfigFiles,String adsResFilepath,String adsTargetPath);
	/**
	 * Send 合成投放推送式广告配置文件，复制资源文件至投放目录，拉起(OCG)投放.
	 *
	 */
	public void sendAdsData();
	
	
	/**
	 * Send 发送字幕广告、推荐链接广告.
	 * 写UNT数据库，复制图片资源文件至投放目录
	 * 
	 * @param startTime 播出单开始时间
	 * @param adsTypeCode 广告位编码
	 * @param adsResFilepath 广告资源临时路径
	 * @param adsTargetPath 广告资源投放路径
	 */
	public void sendMessageLinkAds(Date startTime,String adsTypeCode,String adsResFilepath,String adsTargetPath);
	
	
	
	
	
	/**
	 * Send发送开机广告.
	 * 根据投放区域，查询待投放广告，写NIT描述，复制资源文件至投放目录
	 * 不同区域的资源文件名是否会重复，不同区域的投放目录是否不同，需现场确认，这里暂认为相同，如不同则资源路径，和投放路径均由adsArea确定
	 * @param adsAreas 开机广告投放区域列表
	 * @param startTime 播出单开始时间
	 * @param adsTypeCode 广告位编码
	 * @param adsResFilepath 广告资源临时路径
	 * @param adsTargetPath 广告资源投放路径
	 */
	public void sendStartStbAds(Date startTime,String adsTypeCode);

	public void sendStartHdVideoAds(Date startTime);
	
	public void sendStartHdPicAds(Date startTime);
	
	public void sendStartSdPicAds(Date startTime);
	
	public void sendHdAudioAds(Date startTime);
	
	public void sendSdAudioAds(Date startTime);
	
	public void sendHdRecomendAds(Date startTime);
	/**
	 * 发送音频广告
	 * */
	public void sendAudioAds(Date startTime,String adsTypeCode);
	
	public void sendHotRecommendAds(Date startTime,String adsTypeCode); 	
	
	public List<AdPlaylistGis> queryNewPlayList(Date startTime,List<Ads> adsList);
	
	public List<AdPlaylistGis> queryNewPlayList(Date startTime,String adsCode);
	
	/**
	 * 解析字幕播出单文件的属性.
	 *
	 * @param filename 配置文件名
	 * @return 返回字幕对象
	 */
	public AdsSubtitle readMessageFile(String filename);
	
	/**
	 * 解析轮询广告位的播出单文件的属性.
	 *
	 * @param filename  配置文件名
	 * @return 轮询图片名 ","分隔
	 */
	public String readLoopFile(String filename);
	
	/**
	 * Read start file.
	 *
	 * @param filename  配置文件名
	 * @return 开机文件序列  Key=TS,IFAME  value=文件名
	 * 
	 */
	public Map<String,String> readStartFile(String filename);
	
	/**
	 * 解析区域，用户行业，用户级别属性 结构为 001#002#003.
	 *
	 * @param data 输入数据
	 * @param split 间隔符
	 * @return 返回数据列表
	 */
	public List<String> getElementFromData(String data,String split);
	
	/**
	 * 解析频道信息.
	 *
	 * @param data 输入数据  频道的集合结构为 ["001,002","005,007","007,005,002,006"]，使用json格式对其拆分
	 * 且区域和频道的集合的长度必须相等
	 * @return 返回数据列表
	 */
	public List<List<String>> getElementFromData(String data);
	
	/**
	 * 写广告配置文件.
	 *
	 * @param adTypeCode 广告位编码
	 * @param chaID 特征值
	 * @param filename 文件 如为轮询文件 “,”分隔
	 * @return 
	 */
	public boolean WriteAdConfig(String areaCode,String channelCode,String adsTypeCode,String chaID,String filename);
	/**
	 * 合成广告配置文件.	
	 * 读取各广告位的配置文件，生成总的配置文件，并放到临时目录下
	 * @return 
	 */
	public boolean ComposeAdConfig();
	/**
	 * 复制单个文件
	 *  @param oldPath String 原文件路径 如：c:/fqf.txt      
	 *  @param newPath String 复制后路径 如：f:/fqf.txt      
	 *  @return boolean      */    
	public boolean copyFile(String oldPath, String newPath);
	/**
	 * 复制整个文件夹内容文件
	 *  @param oldPath String 原文件路径 如：c:/fqf.txt      
	 *  @param newPath String 复制后路径 如：f:/fqf.txt      
	 *  @return boolean      */    
	public boolean copyFolder(String oldPath, String newPath);
	public void delFile(String filePathAndName);

}
