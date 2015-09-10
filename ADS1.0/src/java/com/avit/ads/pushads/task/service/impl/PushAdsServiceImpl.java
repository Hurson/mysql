package com.avit.ads.pushads.task.service.impl;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.cps.service.CpsService;
import com.avit.ads.pushads.ocg.dao.OcgInfoDao;
import com.avit.ads.pushads.ocg.dao.UntDao;
import com.avit.ads.pushads.ocg.service.OcgService;
import com.avit.ads.pushads.task.bean.AdPlaylistGis;
import com.avit.ads.pushads.task.bean.AdsElement;
import com.avit.ads.pushads.task.bean.AdvertPosition;
import com.avit.ads.pushads.task.bean.ComparatorElement;
import com.avit.ads.pushads.task.bean.ComparatorElementType;
import com.avit.ads.pushads.task.bean.ComparatorPicMaterial;
import com.avit.ads.pushads.task.bean.ImageInfo;
import com.avit.ads.pushads.task.bean.LoopMaterialEntry;
import com.avit.ads.pushads.task.bean.OcgInfo;
import com.avit.ads.pushads.task.bean.PicMaterial;
import com.avit.ads.pushads.task.bean.SendAds;
import com.avit.ads.pushads.task.bean.StartMaterial;
import com.avit.ads.pushads.task.bean.TReleaseArea;
import com.avit.ads.pushads.task.bean.TextMate;
import com.avit.ads.pushads.task.cache.AdvertPositionMap;
import com.avit.ads.pushads.task.cache.SendAdsElementMap;
import com.avit.ads.pushads.task.cache.SendAdsMap;
import com.avit.ads.pushads.task.cache.UiDesMap;
import com.avit.ads.pushads.task.dao.PushAdsDao;
import com.avit.ads.pushads.task.service.PushAdsService;
import com.avit.ads.pushads.ui.service.UiService;
import com.avit.ads.pushads.uix.dao.AreaDao;
import com.avit.ads.pushads.uix.service.UixService;
import com.avit.ads.pushads.unt.bean.AdsLink;
import com.avit.ads.pushads.unt.bean.AdsSubtitle;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.FileDigestUtil;
import com.avit.ads.util.InitAreas;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.PushFalierHelper;
import com.avit.ads.util.SendFlagHelper;
import com.avit.ads.util.UnRealTimeAdsPushHelper;
import com.avit.ads.util.bean.Ads;
import com.avit.ads.util.json.Json2ObjUtil;
import com.avit.ads.util.message.AdsConfigJs;
import com.avit.ads.util.message.AdsImage;
import com.avit.ads.util.message.ChannelSubtitle;
import com.avit.ads.util.message.ChannelSubtitleElement;
import com.avit.ads.util.message.ChannelSubtitleInfo;
import com.avit.ads.util.message.Subtitle;
import com.avit.ads.util.message.SubtitleContent;
import com.avit.ads.util.message.SubtitlePart;
import com.avit.ads.util.message.TvnTarget;
import com.avit.ads.util.type.UIUpdate;
import com.avit.ads.util.warn.WarnHelper;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


// TODO: Auto-generated Javadoc
/**
 * The Class PushAdsServiceImpl.
 */
@Service("PushAdsService")
public class PushAdsServiceImpl implements PushAdsService {
	private Log log = LogFactory.getLog(this.getClass());

	private boolean cpsSendFlag = false;
	private int cpsSendTimes = 3;
	
	private boolean npvrSendFlag = false;
	private int npvrSendTimes = 3;
	
 	
//	private static Map<String, Boolean> sendFlagMap;
	
//	//开机广告、广播收听背景、热点推荐广告投放使用，当投放被打断时，下次接着投放
//	//key: adsCode-areaCode,  value: true/false
//	private static Map<String, Boolean> uiInterruptedMap;
	
	/** The push ads dao. */
	@Inject
	PushAdsDao pushAdsDao;
	@Inject
	OcgService ocgService;

	// @Inject
	UiService uiService;
	@Inject
	ADSurveyDAO adsurveyDAO;
	
	@Autowired
	private UntDao untDao;	
	
	@Inject
	private OcgInfoDao ocgInfoDao;
	
	@Inject
	private FtpService ftpService;
	@Inject
	private CpsService cpsService;
	
	@Autowired
	private WarnHelper warnHelper;
	
	@Autowired
	private SendFlagHelper sendFlagHelper;
	
	@Autowired
	private UnRealTimeAdsPushHelper unRealTimeAdsPushHelper;
	
	@Autowired
	private PushFalierHelper pushFalierHelper;
	
	@Autowired
	private UixService uixService;
	
	@Autowired
	private AreaDao areaDao;
	

	/**
	 * Gets the push ads dao.
	 *
	 * @return the push ads dao
	 */
	public PushAdsDao getPushAdsDao() {
		return pushAdsDao;
	}

	/**
	 * Sets the push ads dao.
	 *
	 * @param pushAdsDao the new push ads dao
	 */
	public void setPushAdsDao(PushAdsDao pushAdsDao) {
		this.pushAdsDao = pushAdsDao;
	}

	public OcgService getOcgService() {
		return ocgService;
	}

	public void setOcgService(OcgService ocgService) {
		this.ocgService = ocgService;
	}

	/**
	 * Send ad gis.
	 * 载入已发送的播出单，生成更新广告位对应配置文件，复制资源文件至临时目录
	 *
	 * @param state 播出单状态
	 * @param adsTypeCode 广告位编码
	 */
	public void sendAllAds(String adsTypeCode,String state,Date startTime)
	{
		List<AdPlaylistGis> adsList = pushAdsDao.queryStartAds( adsTypeCode,state,startTime);
		// TODO 播出单列表不为空
		if (adsList!=null && adsList.size()>0)
		{
			// TODO 循环处理播出单
			for(AdPlaylistGis adGis : adsList){
				try
				{
					if (adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_STARTSTB_HD) || adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_STARTSTB_SD))
					{//开机广告位
						continue;
					}
					else if (adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_FREQUENCE_HD) || adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_FREQUENCE_SD)  || adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_RECOMMEND) )
					{//音频广告位
						continue;
					}
					else if(adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_LIVE_UNDER_HD)){ //直播下排
						continue;
					}
					else if(adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_CHANNEL_SUBTITLE)){ //频道字幕
						continue;
					}
					else if(adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH__SUBTITLE)){ //菜单字幕
						continue;
					}
					//TODO 如为轮询素材，则需读取配置文件
					String resourceFiles = "";
					AdvertPosition adPostion=AdvertPositionMap.getAdvertPositionByCode(adGis.getAdSiteCode());
					if (adPostion==null)
					{
						continue;
					}
					if (adPostion.getIsLoop()==1)
					{
						List<PicMaterial> picList =Json2ObjUtil.getList4Json(adGis.getContentPath(), PicMaterial.class);
						ComparatorPicMaterial comparator=new ComparatorPicMaterial();
						Collections.sort(picList, comparator);
						for (int k=0;k<picList.size();k++)
						{
							resourceFiles =resourceFiles+picList.get(k).getPic();
							if (k<picList.size()-1)
							{
								resourceFiles =resourceFiles+",";
							}
						}
					}
					else
					{
						resourceFiles = adGis.getContentPath();
					}
					//List<String>  areaList = getElementFromData(adGis.getAreas(),ConstantsHelper.SPLIT__SIGN);
					
					List<String>  areaList=null;
					try
					{
						areaList= getElementFromData(adGis.getAreas(),ConstantsHelper.SPLIT__SIGN);
						if (areaList!=null && areaList.size()==0)
						{
							areaList.add("0");
						}
					}
					catch(Exception e)
					{
						areaList =  new ArrayList<String>();
						areaList.add("0");
						e.printStackTrace();
					}
					
					
					
					List<List<String>> paramList=new ArrayList<List<String>>() ;
					Integer priority = adGis.getPriority();
					String isCateOrAsset="0";//0代表的为影片 1代表栏目
					//直播频道
					if (adPostion.getIsChannel()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					//NVOD主菜单
					}else if(ConstantsAdsCode.NVOD_MENU.equals(adPostion.getPositionCode())){
						List<String> listparam = new ArrayList<String>();
						listparam.add(adGis.getMenuTypeCode());
						paramList.add(listparam);
					}
					//NPVR
					else if (adPostion.getIsPlayback()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					}
					//回看栏目
					else if (adPostion.getIsColumn()==1)
					{
						paramList=getElementFromData(adGis.getCategoryId());
					}
					//点播随片
					else if (adPostion.getIsFollowAsset()==1)
					{
						paramList=getElementFromData(adGis.getAssetId());
						
						if( paramList.size() == 0 || paramList.get(0).size() == 0){
							paramList=getElementFromData(adGis.getCategoryId());
							isCateOrAsset="1";
						}
					}
					
					if (adGis.getAdSiteCode().equals(ConstantsAdsCode.CPS_VOD_MENU_HD))
					{
						List<String> listparam = new ArrayList<String>();
						listparam.add("0");
						paramList.add(listparam);
					}
					if (areaList.size()!=paramList.size())
					{
						//属性解析错误
						log.info("adGis.id="+adGis.getId()+" data is error");
						continue;
					}
					if (adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_FRAME_HD) || adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_FRAME_SD) ||adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_HD) ||adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_SD) )
					{
						//sendflag=true;
						String areaCode = areaList.get(0);
						// sendFlagHelper.makeSent(areaCode);   //待定，可能不需要重新发送
					}
					for (int a=0;a<areaList.size();a++)
					{
						for (int c=0;c<paramList.get(a).size();c++)
						{
							log.info("adGis.id="+adGis.getId()+";Areacode:"+areaList.get(a)+";channelCode:"+paramList.get(a).get(c) +";CharacteristicIdentification:"+adGis.getCharacteristicIdentification()+";resourceFile:"+resourceFiles);
							// TODO 写广告配置文件缓存
							if(adGis.getAdSiteCode().equals(ConstantsAdsCode.CPS_VOD_MENU_HD)){
								System.out.println(adGis.getId().intValue());
								SendAdsElementMap.addAdsElement(adGis.getId().intValue(), adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c),resourceFiles );
							}
							else if (adPostion.getIsFollowAsset()==1){
								if(!"0".equals(paramList.get(a).get(c))){
									 SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c)+","+isCateOrAsset,resourceFiles, priority );
								}else{
									SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), "0", resourceFiles, priority );
								}
							}else{
								  SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c),resourceFiles );
							}
						}
					}
				
					log.info("adGis.id="+adGis.getId()+"发送缓存成功");
					//og.info("adGis.id="+adGis.getId()+" send to cache");
				}
				catch(Exception e)
				{
					log.error("adGis.id="+adGis.getId()+"发送缓存失败!"+ e);
					//log.info("adGis.id="+adGis.getId()+" send to cache");
				}
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.avit.ads.pushads.task.service.PushAdsService#sendAllAds(java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendAllAds(Date startTime, String adsTypeCode) {
		
//		if(adsTypeCode.equals("02122")){
//			System.out.println("get in");
//			
//		}
		
		List<AdPlaylistGis> adsList = pushAdsDao.queryStartAds(startTime, adsTypeCode);
		//播出单列表不为空
		if (adsList!=null && adsList.size()>0)
		{
			String resourceFiles = "";
			//循环处理播出单
			for(AdPlaylistGis adGis : adsList){
				try
				{
					AdvertPosition adPostion=AdvertPositionMap.getAdvertPositionByCode(adGis.getAdSiteCode());
					if (adPostion==null)
					{
						continue;
					}
					if (adPostion.getIsLoop()==1)
					{
						List<PicMaterial> picList =Json2ObjUtil.getList4Json(adGis.getContentPath(), PicMaterial.class);
						ComparatorPicMaterial comparator=new ComparatorPicMaterial();
						Collections.sort(picList, comparator);
						for (int k=0;k<picList.size();k++)
						{
							resourceFiles =resourceFiles+picList.get(k).getPic();
							if (k<picList.size()-1)
							{
								resourceFiles =resourceFiles+",";
							}
						}
					}
					else
					{
						resourceFiles = adGis.getContentPath();
					}
					List<String>  areaList=null;
					try
					{
						areaList= getElementFromData(adGis.getAreas(),ConstantsHelper.SPLIT__SIGN);
						if (areaList!=null && areaList.size()==0)
						{
							areaList.add("0");
						}
					}
					catch(Exception e)
					{
						areaList =  new ArrayList<String>();
						areaList.add("0");
						e.printStackTrace();
					}
					List<List<String>> paramList=new ArrayList<List<String>>() ;
					
					String isCateOrAsset="0";//0代表的为影片 1代表栏目
					Integer priority = adGis.getPriority();
					
					//直播频道
					if (adPostion.getIsChannel()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					//NVOD主菜单
					}else if(ConstantsAdsCode.NVOD_MENU.equals(adPostion.getPositionCode())){
						List<String> listparam = new ArrayList<String>();
						listparam.add(adGis.getMenuTypeCode());
						paramList.add(listparam);
					}
					//音频频道
					else if (adPostion.getIsFreq()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					}
					//NPVR
					else if (adPostion.getIsPlayback()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					}
					//回看栏目
					else if (adPostion.getIsColumn()==1)
					{
						paramList=getElementFromData(adGis.getCategoryId());
					}
					//点播随片
					
					else if (adPostion.getIsFollowAsset()==1)
					{
						paramList=getElementFromData(adGis.getAssetId());
						
						if( paramList.size() == 0 || paramList.get(0).size() == 0){
							paramList=getElementFromData(adGis.getCategoryId());
							isCateOrAsset="1";
						}
					}
					
					if (adGis.getAdSiteCode().equals(ConstantsAdsCode.CPS_VOD_MENU_HD))
					{
						List<String> listparam = new ArrayList<String>();
						listparam.add("0");
						paramList.add(listparam);
					}
					if (areaList.size()!=paramList.size())
					{
						//属性解析错误
						log.info("adGis.id="+adGis.getId()+" data is error");
						continue;
					}
					if (adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_FRAME_HD) || adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_FRAME_SD) ||adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_HD) ||adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_SD) )
					{
						//sendflag=true;
						String areaCode = areaList.get(0);
						sendFlagHelper.makeSent(areaCode);
					}
					for (int a=0;a<areaList.size();a++)
					{
						for (int c=0;c<paramList.get(a).size();c++)
						{
							log.info("adGis.id="+adGis.getId()+";Areacode:"+areaList.get(a)+";channelCode:"+paramList.get(a).get(c) +";CharacteristicIdentification:"+adGis.getCharacteristicIdentification()+";resourceFile:"+resourceFiles);
							// TODO 写广告配置文件缓存
							if(adGis.getAdSiteCode().equals(ConstantsAdsCode.CPS_VOD_MENU_HD)){
								SendAdsElementMap.addAdsElement(adGis.getId().intValue(), adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c),resourceFiles );
							}
							else if (adPostion.getIsFollowAsset()==1  ){
								if(!"0".equals(paramList.get(a).get(c))){
									SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c)+","+isCateOrAsset,resourceFiles,priority );
								}else{
									SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c),resourceFiles,priority );
								}
							}else{
								SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c),resourceFiles );
							}
						}
					}
					log.info("adGis.id="+adGis.getId()+" send to cache");
					log.info("adGis.id="+adGis.getId()+"发送缓存成功");
					if (!adGis.getState().equals("1"))
					{
						pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");
					}
				}
				catch(Exception e)
				{
					log.error("adGis.id="+adGis.getId()+"发送缓存失败!",e);
					//log.info("adGis.id="+adGis.getId()+" send to cache");
				}
			}
		}
		
	}

	/** 
	 * 删除过期播出单，并补空
	 * 调用SendAdsMap.deleteAds()
	 *     SendFileMap.deleteFile
	 */
	public void sendAllDefaultAds(Date endTime,String adsTypeCode) {
		/** 
		 * TODO Auto-generated method stub
		 */
		List<AdPlaylistGis> adsList = pushAdsDao.queryEndAds(endTime, adsTypeCode);
		//删除过期播出单，并补空
		//根据广告位编码，特征值，读取配置文件的默认资源名
		// TODO 播出单列表不为空
		if (adsList!=null && adsList.size()>0)
		{
			//广告位默认素材路径
			String resourceFiles = InitConfig.getAdsByCode(adsTypeCode).getDefaultRes();
			// TODO 循环处理播出单
			for(AdPlaylistGis adGis : adsList){
				try
				{
					//TODO 如为轮询素材，则需读取配置文件
					AdvertPosition adPostion=AdvertPositionMap.getAdvertPositionByCode(adsTypeCode);
					if (adPostion==null)
					{
						continue;
					}
					
					List<String>  areaList=null;
					try
					{
						areaList= getElementFromData(adGis.getAreas(),ConstantsHelper.SPLIT__SIGN);
						if (areaList!=null && areaList.size()==0)
						{
							areaList.add("0");
						}
					}
					catch(Exception e)
					{
						areaList =  new ArrayList<String>();
						areaList.add("0");
						e.printStackTrace();
					}
					List<List<String>> paramList=new ArrayList<List<String>>() ;
					String isCateOrAsset="0";//0代表的为影片 1代表栏目
					//直播频道
					if (adPostion.getIsChannel()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					//NVOD主菜单
					}else if(ConstantsAdsCode.NVOD_MENU.equals(adPostion.getPositionCode())){
						List<String> listparam = new ArrayList<String>();
						listparam.add(adGis.getMenuTypeCode());
						paramList.add(listparam);
					}
					//音频频道
					else if (adPostion.getIsFreq()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					}
					//NPVR
					else if (adPostion.getIsPlayback()==1)
					{
						paramList=getElementFromData(adGis.getChannelId());
					}
					//回看栏目
					else if (adPostion.getIsColumn()==1)
					{
						paramList=getElementFromData(adGis.getCategoryId());
					}
					//点播随片
					else if (adPostion.getIsFollowAsset()==1)
					{
						paramList=getElementFromData(adGis.getAssetId());
						
						if( paramList.size() == 0 || paramList.get(0).size() == 0){
							paramList=getElementFromData(adGis.getCategoryId());
							isCateOrAsset="1";
						}
					}
					
					if (adGis.getAdSiteCode().equals(ConstantsAdsCode.CPS_VOD_MENU_HD))
					{
						List<String> listparam = new ArrayList<String>();
						listparam.add("0");
						paramList.add(listparam);
					}
					if (areaList.size()!=paramList.size())
					{
						//属性解析错误
						log.info("adGis.id="+adGis.getId()+" data is error");
						continue;
					}
					if (adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_FRAME_HD) || adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_FRAME_SD) ||adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_HD) ||adGis.getAdSiteCode().equals(ConstantsAdsCode.PUSH_MENU_SD) )
					{
						//sendflag=true;
						String areaCode = areaList.get(0);
						sendFlagHelper.makeSent(areaCode);
					}
					for (int a=0;a<areaList.size();a++)
					{
						for (int c=0;c<paramList.get(a).size();c++)
						{
							log.info("adGis.id="+adGis.getId()+";Areacode:"+areaList.get(a)+";channelCode:"+paramList.get(a).get(c) +";CharacteristicIdentification:"+adGis.getCharacteristicIdentification()+";resourceFile:"+resourceFiles);
							// TODO 写广告配置文件缓存
							
							if (adGis.getAdSiteCode().indexOf(ConstantsAdsCode.CPS_LOOKBACK_MENU)>=0){
								SendAdsElementMap.deleteAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c),resourceFiles );
							}
							//处理点播随片  
							else if(adGis.getAdSiteCode().contains(ConstantsAdsCode.CPS_VOD_ASSET) && !"0".equals(paramList.get(a).get(c))){
								SendAdsElementMap.deleteAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c)+","+isCateOrAsset,resourceFiles );
							}else{
								SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), paramList.get(a).get(c),resourceFiles );
							}
						}
					}
					log.info("adGis.id="+adGis.getId()+" 播出单投放完成");
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
				}
				catch(Exception e)
				{
					log.error("adGis.id="+adGis.getId()+"恢复默认素材失败");
					//log.info("adGis.id="+adGis.getId()+" send to cache");
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.avit.ads.pushads.task.service.PushAdsService#sendAdsData(java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
	public void sendAdsData(String adsConfigFile, List<String> adsConfigFiles,
			String adsResFilepath, String adsTargetPath) {
		// TODO Auto-generated method stub
		
		// TODO 循环处理广告配置文件，合成输出文件
			// TODO 复制资源文件至投放目录
		    for (int i=0;i<5;i++)
		    {
		    	List<SendAds> temp=SendAdsMap.getAdsList("adsTypeCode", "adsIdentification");
		    	//生成广告输出文件adConfig.js
		    }
			
		// TODO 拉起UNT，OCG投放
	}
	/**
	 * Send 合成投放推送式广告配置文件，复制资源文件至投放目录，拉起(OCG)投放.
	 *
	 */
	public void sendAdsData()
	{
		//导航条广告、快捷切换列表广告、音量条广告、收藏列表、预告提示广告、菜单图片广告、菜单视频外框广告
		sendAdsDataRealTime();
		//点播菜单广告、点播随片图片广告、回看菜单广告
	//	sendAdsDataCps();
		//回放菜单广告
	//	sendAdsDataNpvr();	
	}
	/*
	 * add by liuwenping
	 * 
	 * 调用ocg的startPlay方法之前，先将文件下下来，看文件大小是否为0
	 * 如果为0（与原始文件大小不符），则校验不通过，不能播放。
	 */
	private boolean validateBeforePlay(String areaCode, String sourceFilePath, String serverPath ){
		
		File sourceFile = new File(sourceFilePath);
		log.info("download file from ocg and validate " + areaCode + " " + serverPath + "/" + sourceFile.getName());
		if(!sourceFile.exists()){                      //文件不存在，向OCG发送的时候就会告警，此处无需再产生告警信息
			return false;
		}
		String downloadDirPath = System.getProperty("user.dir") + "/" + "tempDir";
		File downloadDir = new File(downloadDirPath);
		if(!downloadDir.exists()){
			downloadDir.mkdirs();
		}
		String fileName = sourceFile.getName();
		String downloadFilePath = downloadDirPath + "/" + fileName;  //本地暂存文件路径
		String serverFilePath = serverPath + "/" + fileName;        //服务器文件路径
		
		ocgService.downloadFile(areaCode, downloadFilePath, serverFilePath);	//从OCG下载文件	
		
		File downloadFile = new File(downloadFilePath);
		
		String errorMsg = "";
		if(!downloadFile.exists()){
			errorMsg = "【从OCG下载的文件不存在】" + serverFilePath;  //文件不存在
			warnHelper.writeWarnMsgToDb(areaCode, errorMsg);
		}else if(downloadFile.length() == 0){
			errorMsg = "【从OCG下载文件大小为0】" + serverFilePath; //文件大小为0
			warnHelper.writeWarnMsgToDb(areaCode, errorMsg);
			ocgService.deleteFile(areaCode, fileName, serverPath); //删除OCG上素材
		}
//		if(!"".equals(errorMsg)){
//			errorMsg += serverFilePath; 
//			warnHelper.writeWarnMsgToDb(errorMsg);
//			if(downloadFile.exists()){
//				downloadFile.delete(); //删除从OCG下下来的文件	
//			}
//			return false;
//		}
		if(downloadFile.exists()){
			downloadFile.delete(); //删除从OCG下下来的文件	
		}
		return true;
			
	}

	
	/**
	 * Send 合成单向向实时广告配置文件，复制资源文件至临时目录，拉起(OCG)投放.
	 *
	 */
	public void sendAdsDataRealTime()
	{
		//TODO 生成adConfig.js
		
		List<AdsElement> list = SendAdsElementMap.getDefaultRealTimeAdsElement();
		ComparatorElement comparator=new ComparatorElement();
		Collections.sort(list, comparator);
		
		List<TReleaseArea> areaEntityList = areaDao.getAllArea();
		for(TReleaseArea entity : areaEntityList){
			if(StringUtils.isNotBlank(entity.getOcsId())){
				sendAreaAdsReal(list,entity.getAreaCode());		
			}
				
		}
	}

	public void sendAreaAdsReal(List<AdsElement> list,String areaCode) {
		String prechannelCode="";
		String dataline = "";
		String configFile = InitConfig.getAdsConfig().getRealTimeAds().getAdsConfigFile();
		String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getRealTimeAds().getAdsTempConfigPath()+"/"+areaCode+"/"+configFile);
		Map<String, AdsElement> adsMap = SendAdsElementMap.getAdsMap();
		try
		{
			BufferedWriter osw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(InitConfig.getAdsConfig().getRealTimeAds().getAdsTempConfigPath()+"/"+areaCode+"/"+configFile,false)));//Files.newBufferedWriter(InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename, "UTF-8",false);
			osw.write(dataline);
			
			for (int i=0;i<list.size();i++)
			{
				//查询是否为单向实时广告
				Ads adsTemp=null;
				adsTemp =InitConfig.getAdsConfig().getRealTimeAds().getAdsByCode(list.get(i).getAdsTypeCode());
				if (adsTemp==null)
				{
					continue;
				}
				//菜单，外框不写配置文件
				if (ConstantsAdsCode.PUSH_MENU.equals(list.get(i).getAdsTypeCode().substring(0,4)) || ConstantsAdsCode.PUSH_MENU_FRAME.equals(list.get(i).getAdsTypeCode().substring(0,4)))
				{
				    continue;
				}
				//NVOD主界面，挂角不写配置文件
				if (ConstantsAdsCode.NVOD_MENU.equals(list.get(i).getAdsTypeCode()) || ConstantsAdsCode.CORNER_APPROACH.equals(list.get(i).getAdsTypeCode()))
				{
				    continue;
				}
				//首次循环
				if (prechannelCode.equals("")) 
				{
						dataline="var advConfig = true;";
						osw.write(dataline);
						osw.newLine();
						dataline="var isHD = true ;";
						osw.write(dataline);
						osw.newLine();						
						dataline="if(System.isHDPlatform == 4 || System.isHDPlatform == 3 || System.isHDPlatform == 1 || System.isHDPlatform == false)isHD = false ;";
						osw.write(dataline);
						osw.newLine();
						dataline="var advInfo = [";
						osw.write(dataline);
						osw.newLine();	
						dataline="{serviceId:"+list.get(i).getParamCode()+",locationVar:"+'"' +"locationVar" +i+'"'+"}";
						osw.write(dataline);
						osw.newLine();
						prechannelCode =list.get(i).getParamCode();
						continue;
				}
				if (!prechannelCode.equals(list.get(i).getParamCode()))
				{
					dataline=",";
					osw.write(dataline);
					//osw.newLine();
					dataline="{serviceId:"+list.get(i).getParamCode()+",locationVar:"+'"' +"locationVar" +i+'"'+"}";
					osw.write(dataline);
					osw.newLine();	
					prechannelCode =list.get(i).getParamCode();
				}
				else 
				{
					continue;
				}
			}
			dataline="];";
			osw.write(dataline);
			osw.newLine();
			
			prechannelCode ="";
			
			for (int i=0;i<list.size();i++)
			{
				Ads adsTemp=null;
				//查询是否为单向实时广告
				adsTemp =InitConfig.getAdsConfig().getRealTimeAds().getAdsByCode(list.get(i).getAdsTypeCode());
				if (adsTemp==null)
				{
					continue;
				}
				//菜单，外框不写配置文件
				if (ConstantsAdsCode.PUSH_MENU.equals(list.get(i).getAdsTypeCode().substring(0,4)) || ConstantsAdsCode.PUSH_MENU_FRAME.equals(list.get(i).getAdsTypeCode().substring(0,4)))
				{
				    continue;
				}
				//NVOD主界面，挂角不写配置文件
				if (ConstantsAdsCode.NVOD_MENU.equals(list.get(i).getAdsTypeCode()) || ConstantsAdsCode.CORNER_APPROACH.equals(list.get(i).getAdsTypeCode()))
				{
				    continue;
				}
				
				if (prechannelCode.equals("") || !prechannelCode.equals(list.get(i).getParamCode())) 
				{
					if (!prechannelCode.equals(""))
					{
						osw.newLine();
						dataline="];";
						osw.write(dataline);
						osw.newLine();
					}
					dataline="var locationVar"+i+" = [";
					osw.write(dataline);
					osw.newLine();							
				}
				AdsElement sdEntity = list.get(i);
				AdsElement hdEntity = list.get(i + 1);
				
				String sdKey = sdEntity.getAdsTypeCode() + ":" + areaCode + ":" + sdEntity.getParamCode();
				String hdKey = hdEntity.getAdsTypeCode() + ":" + areaCode + ":" + hdEntity.getParamCode();
				
				if (prechannelCode.equals(list.get(i).getParamCode())){				
					dataline=",";
					osw.write(dataline);
					osw.newLine();
				}
				//标清文件名
				String sdfilename = list.get(i).getFilepath().substring(list.get(i).getFilepath().lastIndexOf("/")+1);
				if(adsMap.containsKey(sdKey)){
					AdsElement realSdEntity = adsMap.get(sdKey);
					sdfilename = realSdEntity.getFilepath().substring(realSdEntity.getFilepath().lastIndexOf("/")+1);
				}
				//高清文件名
				String hdfilename = list.get(i+1).getFilepath().substring(list.get(i+1).getFilepath().lastIndexOf("/")+1);
				if(adsMap.containsKey(hdKey)){
					AdsElement realHdEntity = adsMap.get(hdKey);
					hdfilename = realHdEntity.getFilepath().substring(realHdEntity.getFilepath().lastIndexOf("/")+1);
				}
				dataline  = "{position:"+'"'+adsTemp.getPosition()+'"'+",areaCode:"+list.get(i).getAreaCode()+",imgSrc:isHD?"+'"'+hdfilename+'"'+":"+'"'+sdfilename+'"'+"}";
				//{position:"1",areaCode:0,imgSrc:isHD?"miniepg_ad_3_HD.png":"miniepg_ad_7.png"}
				osw.write(dataline);										
				
				//高标清两条记录
				i++;
				prechannelCode=list.get(i).getParamCode();
				
			}
			osw.newLine();
			dataline="];";
			osw.write(dataline);
			osw.newLine();			
			osw.flush();
			osw.close();
			log.info("areaCode: " + areaCode + " create adConfig.js successful");
		}
		catch (Exception e)
		{
			log.error("create adConfig.js error", e);
		}
		
		// 从广告ftp下载素材至本地
		boolean rePush = pushFalierHelper.rePush(areaCode);
		
		if ( rePush || sendFlagHelper.needSent(areaCode) || !oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getRealTimeAds().getAdsTempConfigPath()+"/"+areaCode+"/"+configFile)))
		{	
			if(rePush){
				pushFalierHelper.resetRePushFlag(areaCode);
			}
			//删除临时目录非.JS文件       应该不会存在js文件
			deleteAllfile(InitConfig.getAdsConfig().getRealTimeAds().getAdsTempPath()+"/"+areaCode);
			
			/*
			 * 下载广告素材
			 */
			
			String ip = InitConfig.getAdsConfig().getAdResource().getIp();
			int port = Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort());
			String username = InitConfig.getAdsConfig().getAdResource().getUser();
			String password = InitConfig.getAdsConfig().getAdResource().getPwd();
			
			if(!ftpService.connectServer(ip, port, username, password)){
				sendFlagHelper.decreaseSendTimes(areaCode);
				return;
			}			
		
			
			//读取FTP文件至本地临时目录   主菜单广告和视频外框广告不用写配置文件，从ftp下载广告素材文件，文件使用特定命名"adfilename"
			List<String> sentList = new ArrayList<String>();
			String localPicPath = InitConfig.getAdsConfig().getRealTimeAds().getAdsTempPath()+"/"+areaCode;
			for (int i =0;i<list.size();i++)
			{
				//视频外框广告
				if (ConstantsAdsCode.PUSH_MENU_FRAME.equals(list.get(i).getAdsTypeCode().substring(0,4)))
				{
					String fullpath   = list.get(i).getFilepath();
					String key = list.get(i).getAdsTypeCode() + ":" + areaCode + ":" + list.get(i).getParamCode();
					
					if(adsMap.containsKey(key)){
						AdsElement realEntity = adsMap.get(key);
						fullpath = realEntity.getFilepath();
					}
					
					String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
					String resourcPath = fullpath.substring(0,fullpath.lastIndexOf("/"));
					String adfilename = "";
					if (ConstantsAdsCode.PUSH_MENU_FRAME_HD.equals(list.get(i).getAdsTypeCode()))
					{
						adfilename = ConstantsAdsCode.PUSH_MENU_FRAME_HD_FILE;
					}
					if (ConstantsAdsCode.PUSH_MENU_FRAME_SD.equals(list.get(i).getAdsTypeCode()))
					{
						adfilename = ConstantsAdsCode.PUSH_MENU_FRAME_SD_FILE;
					}
					if (!sentList.contains(adfilename))
					{
						log.info("areacode:" + areaCode + " adsCode: " + list.get(i).getAdsTypeCode() +  " download realtime file: "+ resourcPath + "/" + filename + " and rename it to " + adfilename);
						ftpService.downloadFile(filename, adfilename, resourcPath, localPicPath);
						sentList.add(adfilename);
					}
					
				}
				//NVOD主界面广告
				if (ConstantsAdsCode.NVOD_MENU.equals(list.get(i).getAdsTypeCode()))
				{
					String fullpath   = list.get(i).getFilepath();
					String key = list.get(i).getAdsTypeCode() + ":" + areaCode + ":" + list.get(i).getParamCode();
					
					if(adsMap.containsKey(key)){
						AdsElement realEntity = adsMap.get(key);
						fullpath = realEntity.getFilepath();
					}
					
					String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
					String resourcPath = fullpath.substring(0,fullpath.lastIndexOf("/"));
					//TODO
					String adfilename = list.get(i).getParamCode()+".png";
					
					if (!sentList.contains(adfilename))
					{
						log.info("areacode:" + areaCode + " adsCode: " + list.get(i).getAdsTypeCode() +  " download realtime file: "+ resourcPath + "/" + filename + " and rename it to " + adfilename);
						ftpService.downloadFile(filename, adfilename, resourcPath, localPicPath);
						sentList.add(adfilename);
					}
					
				}
				//NVOD挂角广告
				else if (ConstantsAdsCode.CORNER_APPROACH.equals(list.get(i).getAdsTypeCode()))
				{				
					String[] filenames = list.get(i).getFilepath().split(ConstantsHelper.COMMA);
					String key = list.get(i).getAdsTypeCode() + ":" + areaCode + ":" + list.get(i).getParamCode();
					if(adsMap.containsKey(key)){
						AdsElement realEntity = adsMap.get(key);
						filenames = realEntity.getFilepath().split(ConstantsHelper.COMMA);
					}
					for (int j=0;j<filenames.length;j++)
					{
						String fullpath = filenames[j];
						if(StringUtil.isBlank(fullpath)){
							continue;
						}
						String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
						String resourcPath = fullpath.substring(0,fullpath.lastIndexOf("/"));
						//TODO
						String adfilename = ConstantsAdsCode.CORNER_APPROACH_FILE.replace("*", i+"");
						
						if (!sentList.contains(adfilename))
						{
							log.info("areacode:" + areaCode + " adsCode: " + list.get(i).getAdsTypeCode() +  " download realtime file: "+ resourcPath + "/" + filename + " and rename it to " + adfilename);
							ftpService.downloadFile(filename, adfilename, resourcPath, localPicPath);
							sentList.add(adfilename);
						}
					}
				}
				//主菜单广告，素材轮询
				else if (list.get(i).getFilepath().indexOf(ConstantsHelper.COMMA)>=0)
				{				
					String[] filenames = list.get(i).getFilepath().split(ConstantsHelper.COMMA);
					String key = list.get(i).getAdsTypeCode() + ":" + areaCode + ":" + list.get(i).getParamCode();
					if(adsMap.containsKey(key)){
						AdsElement realEntity = adsMap.get(key);
						filenames = realEntity.getFilepath().split(ConstantsHelper.COMMA);
					}
					for (int j=0;j<filenames.length;j++)
					{
						String fullpath = filenames[j];
						String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
						String resourcPath = fullpath.substring(0,fullpath.lastIndexOf("/"));
						String adfilename = "";
						if (ConstantsAdsCode.PUSH_MENU_HD.equals(list.get(i).getAdsTypeCode()))
						{
							adfilename = ConstantsAdsCode.PUSH_MENU_HD_FILE+(j+1)+".png";
						}
						if (ConstantsAdsCode.PUSH_MENU_SD.equals(list.get(i).getAdsTypeCode()))
						{
							adfilename =  ConstantsAdsCode.PUSH_MENU_SD_FILE+(j+1)+".png";
						}
						if (!sentList.contains(adfilename))
						{
							log.info("areacode:" + areaCode + " adsCode: " + list.get(i).getAdsTypeCode() +  " download realtime file: "+ resourcPath + "/" + filename + " and rename it to " + adfilename);
							ftpService.downloadFile(filename, adfilename, resourcPath, localPicPath);
							sentList.add(adfilename);
						}
					}
					continue;
				}
				else{
					String key = list.get(i).getAdsTypeCode() + ":" + areaCode + ":" + list.get(i).getParamCode();
					String fullpath = list.get(i).getFilepath();
					if(adsMap.containsKey(key)){
						AdsElement realEntity = adsMap.get(key);
						fullpath = realEntity.getFilepath();
					}
					String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
					String resourcPath = fullpath.substring(0,fullpath.lastIndexOf("/"));
					if (!sentList.contains(filename))
					{
						log.info("areacode:" + areaCode + " adsCode: " + list.get(i).getAdsTypeCode() +  " download realtime file: "+ resourcPath + "/" + filename);
						ftpService.downloadFile(filename, filename, resourcPath, localPicPath);
						sentList.add(filename);
					}
				}
				
			}
			
			List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
			

			boolean ocgExist = false; //是否配置了该区域的OCG信息
			
			boolean ocgSuccess = false;  //OCG的操作是否成功
			
			String targetConfigDirPath = InitConfig.getAdsConfig().getRealTimeAds().getAdsTargetConfigPath();
			String configFileName = InitConfig.getAdsConfig().getRealTimeAds().getAdsConfigFile();
			String localConfigFilePath = InitConfig.getAdsConfig().getRealTimeAds().getAdsTempConfigPath() + "/"+areaCode+"/" + configFileName;
			String targetPicDir = InitConfig.getAdsConfig().getRealTimeAds().getAdsTargetPath();

			for (OcgInfo ocg : ocgList) {
				if (ocg.getAreaCode().equals(areaCode)) {
					
					ocgExist = true;
					
					String ocgIp = ocg.getIp();
					int ocgPort = Integer.parseInt(ocg.getPort());
					String ocgUser = ocg.getUser();
					String ocgPwd = ocg.getPwd();

					/*
					 *  上传文件至OCG
					 */		
					//建立OCG服务器的FTP连接
					if(!ocgService.connectFtpServer(ocgIp,ocgPort, ocgUser, ocgPwd)){
						
						sendFlagHelper.decreaseSendTimes(areaCode);
						continue;
					}
		 			
					//删除OCG服务器上的素材文件 （配置文件应该不用删，上传会直接覆盖掉原来的）
					ocgService.deleteFtpDirFiles(targetPicDir);
							
					//发送素材文件至OCG
					ocgService.sendDirFilesToFtp(localPicPath, targetPicDir);

					//发送配置文件至OCG
					
					ocgService.sendFileToFtp(localConfigFilePath, targetConfigDirPath);
					
					//断开OCG服务器的FTP连接（非必须，建立连接的时候有断开连接的操作）
					ocgService.disConnectFtpServer();	
					
					/* 
					 * 向OCG发送投放广告消息
					 */			
					String sendPath = getParentPath(targetPicDir);
					boolean ocgPlaySuccess = ocgService.startOcgPlayByIp(ocgIp, sendPath, ConstantsHelper.ALL_CHANNEL, ConstantsHelper.UNT_TYPE);
					
					if(!ocgPlaySuccess){
						sendFlagHelper.decreaseSendTimes(areaCode);
						log.error("start ocg play failed! areacode : " + areaCode + ", ip: " + ocgIp);
						continue;
					}	
					
					ocgSuccess = true;
				}
			}
			
			if(!ocgExist){
				String errMsg = "未配置区域【" + areaCode + "】的OCG FTP连接信息";
				log.error(errMsg);
				warnHelper.writeWarnMsgToDb(areaCode,errMsg);
				sendFlagHelper.decreaseSendTimes(areaCode);
				return;
			}
			
			if(ocgSuccess){
				//发UNT素材更新通知
				AdsImage imageUpdateMsg = new AdsImage();
				String imageUpdatePath = ConstantsHelper.UNT_UPDATE_PATH_PREFIX + getDeepestDir(targetPicDir) + "/";
				imageUpdateMsg.setFilepath(imageUpdatePath);
				imageUpdateMsg.setUiId(ConstantsHelper.UNT_UPDATE_TEMPLATE);
				
				boolean picUpdateSuccess = ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_ADIMAGE, imageUpdateMsg, areaCode, null);			
				
				//发UNT配置文件更新通知
				AdsConfigJs configUpdateMsg = new AdsConfigJs();
				String configUpdatePath = ConstantsHelper.UNT_UPDATE_PATH_PREFIX + getDeepestDir(targetConfigDirPath)  + "/" + configFileName;
				configUpdateMsg.setFilepath(configUpdatePath);
				configUpdateMsg.setUiId(ConstantsHelper.UNT_UPDATE_TEMPLATE);
				boolean confUpdateSuccess = ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_ADCONFIG, configUpdateMsg, areaCode, null);
				
				if(!picUpdateSuccess || !confUpdateSuccess){
					log.error("send unt update message failed! areacode : " + areaCode);
					sendFlagHelper.decreaseSendTimes(areaCode);
					return;
				}
			}else{
				sendFlagHelper.decreaseSendTimes(areaCode);
				return;
			}
			
			log.info("areaCode: " + areaCode + " adConfig.js is changed, send file to ocg,  update unt flag ");
		}
		else if(sendFlagHelper.tryAllChance(areaCode)){
			//修改订单、播出单状态为投放失败【只改新增播出单，过期播出单未投放成功不会改为失败】
			pushFalierHelper.changeStateToFailed(areaCode);
		}
		//从list中清除对象
		pushFalierHelper.deletePlayListEntity(areaCode);
		
		//重置投放失败次数标志
		sendFlagHelper.reset(areaCode);		
	}
	/**
	 * Send 合成CPS广告配置文件
	 *
	 */
	public void sendAdsDataCps()
	{
		// 生成cpsadConfigFile.js
		List<AdsElement> list = SendAdsElementMap.getCpsAdsElement();
		ComparatorElementType comparator=new ComparatorElementType();
		Collections.sort(list, comparator);
//		String preadsCode="";
//		String preareaCode="";
		String preTypeCode="";
//		String preParamCode="";
	
		String paramCode="";
		String dataline = "";
		String configFile = InitConfig.getAdsConfig().getCpsAds().getAdsConfigFile();
		String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getCpsAds().getAdsTempPath()+"/"+configFile);
		
		try
		{
			BufferedWriter osw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(InitConfig.getAdsConfig().getCpsAds().getAdsTempPath()+"/"+configFile,false)));
			//Files.newBufferedWriter(InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename, "UTF-8",false);
			osw.write(dataline);			
			osw.newLine();
			
//			preTypeCode ="";
			String labelName="";
			int listSize = list.size();
			String sdFile="";//标清文件
			String hdFile="";//高清文件
			Integer sdPriority = 0; //标清权限
			Integer hdPriority = 0; //高清权限
			for (int i=0;i<listSize;i++)
			{
				Ads adsTemp=null;
				//查询是否为CPS广告
				adsTemp =InitConfig.getAdsConfig().getCpsAds().getAdsByCode(list.get(i).getAdsTypeCode());
				if (adsTemp==null)
				{
					continue;
				}		
				
				if (preTypeCode.equals("") || !preTypeCode.substring(0,4).equals(list.get(i).getAdsTypeCode().substring(0,4)) ){
					if (preTypeCode.equals("")){
						dataline="var advConfig = true;";
						osw.write(dataline);
						osw.newLine();
						dataline="var isHD = true ;";
						osw.write(dataline);
						osw.newLine();						
						dataline="if(System.isHDPlatform == 4 || System.isHDPlatform == 3 || System.isHDPlatform == 1 || System.isHDPlatform == false)isHD = false ;";
						osw.write(dataline);
						osw.newLine();
//osw.flush();
					}else {
						osw.newLine();
						dataline="];";
						osw.write(dataline);
						osw.newLine();
//osw.flush();
					}
					
					if (list.get(i).getAdsTypeCode().indexOf(ConstantsAdsCode.CPS_LOOKBACK_MENU)>=0)
					{
						dataline="var lookbackmenuInfo= [";
						labelName="categoryId";
					}
					else if (list.get(i).getAdsTypeCode().indexOf(ConstantsAdsCode.CPS_VOD_MENU)>=0)
					{
						dataline="var menuInfo= [";
						labelName="categoryId";
					}
					else if (list.get(i).getAdsTypeCode().indexOf(ConstantsAdsCode.CPS_VOD_ASSET)>=0)
					{
						dataline="var assetInfo= [";
						labelName="assetId";												
					}
					osw.write(dataline);
					osw.newLine();
//osw.flush();
				}
				//相同广告位
				if (!"".equals(preTypeCode)&& preTypeCode.substring(0,4).equals(list.get(i).getAdsTypeCode().substring(0,4)))
				{
					dataline=",";
					osw.write(dataline);
					osw.newLine();	
//osw.flush();
				}
				
				//标清素材
				if (Integer.valueOf(list.get(i).getAdsTypeCode())%2==1)
				{
					sdFile=list.get(i).getFilepath();
					sdFile = sdFile.substring(sdFile.lastIndexOf("/")+1);
					sdPriority = list.get(i).getPriority();
					paramCode = list.get(i).getParamCode();
					if (i<listSize-1)
					{
						//未至最后记录 同一广告位包
						if (list.get(i).getAdsTypeCode().substring(0,4).equals(list.get(i+1).getAdsTypeCode().substring(0,4))  && list.get(i).getParamCode().equals(list.get(i+1).getParamCode()) )
						{
							hdFile=list.get(i+1).getFilepath();
							hdFile = hdFile.substring(hdFile.lastIndexOf("/")+1);
							hdPriority = list.get(i+1).getPriority();
							i++;
						}
						else
						{
							hdFile=InitConfig.getAdsByCode(list.get(i).getAdsTypeCode().substring(0,4)+"2").getDefaultRes();
						}
					}
				}
				//高清素材
				else
				{
					hdFile=list.get(i).getFilepath();
					hdFile = hdFile.substring(hdFile.lastIndexOf("/")+1);
					hdPriority = list.get(i).getPriority();
					paramCode = list.get(i).getParamCode();
					
					if (i<listSize-1)
					{
						//未至最后记录 同一广告位包
						if (list.get(i).getAdsTypeCode().substring(0,4).equals(list.get(i+1).getAdsTypeCode().substring(0,4))  && list.get(i).getParamCode().equals(list.get(i+1).getParamCode()) )
						{
							sdFile=list.get(i+1).getFilepath();
							sdFile = sdFile.substring(sdFile.lastIndexOf("/")+1);
							sdPriority = list.get(i+1).getPriority();
							i++;
						}
						else
						{
							sdFile=InitConfig.getAdsByCode(list.get(i).getAdsTypeCode().substring(0,4)+"1").getDefaultRes();
						}
					}
				}
				if(list.get(i).getAdsTypeCode().contains(ConstantsAdsCode.CPS_VOD_ASSET)){
					dataline = "{"+labelName+":"+'"'+paramCode+'"'+",imgSrc:isHD?"+'"'+(hdFile.equals("")==true?"":"/images/ad/")+hdFile+'"'+":"+'"'+(sdFile.equals("")==true?"":"/images/ad/")+sdFile+'"'+",priority:isHD?"+hdPriority+":"+sdPriority+"}";
				}else{
					dataline  = "{"+labelName+":"+'"'+paramCode+'"'+",imgSrc:isHD?"+'"'+(hdFile.equals("")==true?"":"/images/ad/")+hdFile+'"'+":"+'"'+(sdFile.equals("")==true?"":"/images/ad/")+sdFile+'"'+"}";
				}
				osw.write(dataline);
//osw.flush();				
				preTypeCode=list.get(i).getAdsTypeCode();
			}
	
			osw.newLine();
			dataline="];";
			osw.write(dataline);
			osw.newLine();
//osw.flush();
			
			//写获取图片URL函数
			String imageUrl = "";
			for(AdsElement ads : list){
				if(ads.getAdsTypeCode().equals(ConstantsAdsCode.CPS_VOD_MENU_HD)){
					final Integer playListId = ads.getPlayListId();
					if(null != playListId && playListId > 0){
						imageUrl = pushAdsDao.queryImageUrlByPlayListId(playListId); 
					}
					break;
				}
			}
			if(StringUtils.isNotBlank(imageUrl)){
				imageUrl = imageUrl.trim();
				dataline = "function getMenuAdURL(categoryid){return '" + imageUrl + "';}";
			}else{
				dataline = "function getMenuAdURL(categoryid){return '';}";
			}
			osw.write(dataline);
			osw.newLine();

//osw.flush();


			//写回看菜单广告调用函数
			dataline="//回看菜单广告调用函数";
			osw.write(dataline);
			osw.newLine();
			dataline="function getLookBackMenuAd(categoryid)";
			osw.write(dataline);
			osw.newLine();	
			dataline="{";
			osw.write(dataline);
			osw.newLine();	
			dataline="    var retimgSrc="+'"'+'"'+";";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  if( lookbackmenuInfo!=null &&  lookbackmenuInfo.length > 0)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	     for (var i=0;i<lookbackmenuInfo.length;i++)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    {	";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  if (lookbackmenuInfo[i].categoryId=='0')";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  {";
			osw.write(dataline);
			osw.newLine();	
			//dataline="	  	    	  	retimgSrc = lookbackmenuInfo[i].imgSrc;";
			dataline="	  	    	  	 ";			
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  if (lookbackmenuInfo[i].categoryId==categoryid)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	      	  	retimgSrc = lookbackmenuInfo[i].imgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  	break;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  return retimgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="}";
			osw.write(dataline);
			osw.newLine();	
			
			//写点播菜单广告调用函数
			dataline="//点播菜单广告调用函数";
			osw.write(dataline);
			osw.newLine();
			dataline="function getMenuAd(categoryid)";
			osw.write(dataline);
			osw.newLine();	
			dataline="{";
			osw.write(dataline);
			osw.newLine();	
			dataline="    var retimgSrc="+'"'+'"'+";";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  if( menuInfo!=null &&  menuInfo.length > 0)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	     for (var i=0;i<menuInfo.length;i++)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    {	";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  if (menuInfo[i].categoryId=='0')";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  	retimgSrc = menuInfo[i].imgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  if (menuInfo[i].categoryId==categoryid)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	      	  	retimgSrc = menuInfo[i].imgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  	break;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  return retimgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="}";
			osw.write(dataline);
			osw.newLine();	

			//写点播随片广告调用函数
			dataline="//点播随片广告调用函数";
			osw.write(dataline);
			osw.newLine();
			dataline="function getAssetAd(assetid,categoryid){var assetSrc='',categorySrc='',defaultSrc='',assetPrio=0,categoryPrio=0;if(assetInfo!=null&&assetInfo.length>0){for(var i=0;i<assetInfo.length;i++){var item=assetInfo[i].assetId;if(item=='0'){defaultSrc=assetInfo[i].imgSrc}else{var datas=item.split(',');if(datas[1]=='0'){if(datas[0]==assetid){assetSrc=assetInfo[i].imgSrc;assetPrio=assetInfo[i].priority}}else if(datas[0]==categoryid){categorySrc=assetInfo[i].imgSrc;categoryPrio=assetInfo[i].priority}}if(assetSrc!=''&&categorySrc!=''&&defaultSrc!=''){break}}if(assetSrc!=''&&categorySrc!=''){if(assetPrio>=categoryPrio){return assetSrc}else{return categorySrc}}else if(assetSrc!=''){return assetSrc}else if(categorySrc!=''){return categorySrc}else{return defaultSrc}}}";
			osw.write(dataline);

			osw.flush();
			osw.close();
			log.info("create cpsadConfigFile.js successful");
		}
		catch (Exception e)
		{
			log.error("create cpsadConfigFile.js error");
			e.printStackTrace();
		}
		//发送CPS更新通知
		List<Ads> adsList = InitConfig.getAdsConfig().getCpsAds().getAdsList();
		boolean rePush = pushFalierHelper.rePush(adsList);
		if ( rePush || (cpsSendFlag && cpsSendTimes > 0) || !oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getCpsAds().getAdsTempPath()+"/"+configFile)))
		{
			if(rePush){
				pushFalierHelper.resetRePushFlag(adsList);
			}
			
			//cpsService.sendPath(InitConfig.getAdsConfig().getCpsAds().getAdsTempPath(), InitConfig.getAdsConfig().getCpsAds().getAdsTargetPath());
			boolean success = cpsService.sendLocalFile(InitConfig.getAdsConfig().getCpsAds().getAdsTempPath()+"/"+InitConfig.getAdsConfig().getCpsAds().getAdsConfigFile(), InitConfig.getAdsConfig().getCpsAds().getAdsTargetPath());
			if(!success){
				cpsSendFlag = true;
				cpsSendTimes --;
				log.info("cps push failed, it still has " + cpsSendTimes + " times to try.");
			}else{
				cpsSendFlag = false;
				cpsSendTimes = 3;
				
				pushFalierHelper.deletePlayListEntity(adsList);
			}
			
			//log.info("cpsadConfigFile.js is changed,start cps flag ");
			//cpsService.startCps();   // 不用发送cps消息通知
		}else if(cpsSendFlag && cpsSendTimes <= 0){
			log.info("cps push failed, it has no chance to try again.");
			cpsSendFlag = false;
			cpsSendTimes = 3;
			//修改订单、播出单状态为投放失败
			pushFalierHelper.changeStateToFailed(adsList);
			pushFalierHelper.deletePlayListEntity(adsList);
		}
		
	}
	/**
	 * Send 合成Npvr双向实时广告配置文件
	 *
	 */
	public void sendAdsDataNpvr()
	{
				
		//生成npvradConfig.js
		List<AdsElement> list = SendAdsElementMap.getNpvrAdsElement();
		ComparatorElementType comparator=new ComparatorElementType();
		Collections.sort(list, comparator);
		String preTypeCode="";
		String dataline = "";
		String configFile = InitConfig.getAdsConfig().getNpvrAds().getAdsConfigFile();
		String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getNpvrAds().getAdsTempPath()+"/"+configFile);
		try
		{
			BufferedWriter osw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(InitConfig.getAdsConfig().getNpvrAds().getAdsTempPath()+"/"+configFile,false)));//Files.newBufferedWriter(InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename, "UTF-8",false);
			osw.write(dataline);
			osw.newLine();
			
			preTypeCode ="";
			String labelName="";
			int listSize = list.size();
			for (int i=0;i<listSize;i++)
			{
				Ads adsTemp=null;
				//查询是否为单向实时广告
				adsTemp =InitConfig.getAdsConfig().getNpvrAds().getAdsByCode(list.get(i).getAdsTypeCode());
				if (adsTemp==null)
				{
					continue;
				}
				//首次循环
				if (preTypeCode.equals("") || !preTypeCode.substring(0,4).equals(list.get(i).getAdsTypeCode().substring(0,4))) 
				{
					if (!preTypeCode.equals(""))
					{
						osw.newLine();
						dataline="];";
						osw.write(dataline);
						osw.newLine();
					}
					else //文件头
					{
						dataline="var advConfig = true;";
						osw.write(dataline);
						osw.newLine();
						dataline="var isHD = true ;";
						osw.write(dataline);
						osw.newLine();						
						dataline="if(System.isHDPlatform == 4 || System.isHDPlatform == 3 || System.isHDPlatform == 1 || System.isHDPlatform == false)isHD = false ;";
						osw.write(dataline);
						osw.newLine();
					}
					if (list.get(i).getAdsTypeCode().indexOf(ConstantsAdsCode.NPVR_MENU)>=0)
					{
						dataline="var npvrmenuInfo= [";
						labelName="serviceId";
					}
					osw.write(dataline);
					osw.newLine();		
				}
				
				if (preTypeCode.length()>4 && preTypeCode.substring(0,4).equals(list.get(i).getAdsTypeCode().substring(0,4)))
				{
					dataline=",";
					osw.write(dataline);
					osw.newLine();									
				}
				//高标清两条记录
				String sdFile="";//
				String hdFile="";//
				preTypeCode=list.get(i).getAdsTypeCode();
				if (Integer.valueOf(list.get(i).getAdsTypeCode())%2==1)
				{
					sdFile=list.get(i).getFilepath();
					sdFile = sdFile.substring(sdFile.lastIndexOf("/")+1);
					
					if (i<listSize-1)
					{
						//未至最后记录 同一广告位包
						if (list.get(i).getAdsTypeCode().substring(0,4).equals(list.get(i+1).getAdsTypeCode().substring(0,4))  && list.get(i).getParamCode().equals(list.get(i+1).getParamCode()) )
						{
							hdFile=list.get(i+1).getFilepath();
							hdFile = hdFile.substring(hdFile.lastIndexOf("/")+1);
							i++;
						}
						else
						{
							hdFile=InitConfig.getAdsByCode(list.get(i).getAdsTypeCode()).getDefaultRes();
						}
					}
				}
				else
				{
					hdFile=list.get(i).getFilepath();
					hdFile = hdFile.substring(hdFile.lastIndexOf("/")+1);
					if (i<listSize-1)
					{
						//未至最后记录 同一广告位包
						if (list.get(i).getAdsTypeCode().substring(0,4).equals(list.get(i+1).getAdsTypeCode().substring(0,4))  && list.get(i).getParamCode().equals(list.get(i+1).getParamCode()) )
						{
							sdFile=list.get(i+1).getFilepath();
							sdFile = sdFile.substring(sdFile.lastIndexOf("/")+1);
							
							i++;
						}
						else
						{
							sdFile="";//InitConfig.getAdsByCode(list.get(i).getAdsTypeCode()).getDefaultRes();
						}
					}
				}
				dataline  = "{"+labelName+":"+'"'+list.get(i).getParamCode()+'"'+",imgSrc:isHD?"+'"'+(hdFile.equals("")==true?"":"/images/ad/")+hdFile+'"'+":"+'"'+(sdFile.equals("")==true?"":"/images/ad/")+sdFile+'"'+"}";//{areaCode:"123",serviceId:100,imgSrc:isHD?"miniepg_ad_3_HD.png":"miniepg_ad_7.png"}
				osw.write(dataline);
				
			}
			osw.newLine();
			dataline="];";
			osw.write(dataline);
			osw.newLine();	
			dataline="function getNpvrMenuAd(serviceid)";
			osw.write(dataline);
			osw.newLine();	
			dataline="{";
			osw.write(dataline);
			osw.newLine();	
			dataline="    var retimgSrc="+'"'+'"'+";";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  if( npvrmenuInfo!=null &&  npvrmenuInfo.length > 0)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	     for (var i=0;i<npvrmenuInfo.length;i++)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    {	";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  if (npvrmenuInfo[i].serviceId==0)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  	retimgSrc = npvrmenuInfo[i].imgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  if (npvrmenuInfo[i].serviceId==serviceid)";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  {";
			osw.write(dataline);
			osw.newLine();	
			dataline="	      	  	retimgSrc = npvrmenuInfo[i].imgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  	break;";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  	    }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  }";
			osw.write(dataline);
			osw.newLine();	
			dataline="	  return retimgSrc;";
			osw.write(dataline);
			osw.newLine();	
			dataline="}";
			osw.write(dataline);
			osw.newLine();	
			osw.flush();
			osw.close();
			log.info("create npvradConfig.js successful");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("create npvradConfig.js error");
		}
//		log.info("//发送NPVR更新通知  是否需要待定 ");
		//发送NPVR更新通知  是否需要待定
		List<Ads> adsList = InitConfig.getAdsConfig().getNpvrAds().getAdsList();
		boolean rePush = pushFalierHelper.rePush(adsList);
		if ( rePush || (npvrSendFlag && npvrSendTimes > 0) ||  !oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getNpvrAds().getAdsTempPath()+"/"+configFile)))
		{	
			if(rePush){
				pushFalierHelper.resetRePushFlag(adsList);
			}
			
			log.info("npvradConfig.js is changed,update npvr flag ");
			//NPVR 采用cps相同目录
			boolean success = cpsService.sendLocalFile(InitConfig.getAdsConfig().getNpvrAds().getAdsTempPath()+"/"+InitConfig.getAdsConfig().getNpvrAds().getAdsConfigFile(), InitConfig.getAdsConfig().getCpsAds().getAdsTargetPath());
			//cpsService.startCps();  //取消消息通知
			if(!success){
				npvrSendFlag = true;
				npvrSendTimes --;
				log.info("cps push failed, it still has " + npvrSendTimes + " times to try.");
			}else{
				npvrSendFlag = false;
				npvrSendTimes = 3;
				pushFalierHelper.deletePlayListEntity(adsList);
			}
			
		}else if(npvrSendFlag && npvrSendTimes <= 0){
			log.info("npvr push failed, it  has no chance to try again.");
			npvrSendFlag = false;
			npvrSendTimes = 3;
			//修改订单、播出单状态为投放失败
			pushFalierHelper.changeStateToFailed(adsList);
			pushFalierHelper.deletePlayListEntity(adsList);
		}
	}
	/* (non-Javadoc)
	 * @see com.avit.ads.pushads.task.service.PushAdsService#sendMessageLinkAds(java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendMessageLinkAds(Date startTime,String adsTypeCode,String adsResFilepath, String adsTargetPath) {
		// TODO Auto-generated method stub
		List adsList = pushAdsDao.queryStartAds( startTime, adsTypeCode);
		if (adsList!=null && adsList.size()>0)
		{
			for (int i=0;i<adsList.size();i++)
			{
				//读取播出单信息 ，CONTENT_PATH
				//iframe:aaaa.iframe
				//ts:****.ts
				AdPlaylistGis adGis = (AdPlaylistGis)adsList.get(i);
				if (ConstantsAdsCode.PUSH_MESSAGE.equals(adGis.getAdSiteCode()))
				{
					//写 UNT
					AdsSubtitle subTitle=readMessageFile(adGis.getContentPath());
					if (subTitle==null)
					{
						continue;
					}
					List<AdsSubtitle> adsSubtitleList = new ArrayList<AdsSubtitle>();
					subTitle.setUserIndustry(adGis.getUserindustrys());
					subTitle.setUserlevel(adGis.getUserlevels());
					
					List<String>  areaList = getElementFromData(adGis.getAreas(),ConstantsHelper.SPLIT__SIGN);
					List<List<String>> channelList= getElementFromData(adGis.getChannelId());
					for (int a=0;a<areaList.size();a++)
					{
						for (int c=0;c<channelList.get(a).size();c++)
						{
							log.info("Areacode:"+areaList.get(a)+"channelCode:"+channelList.get(a).get(c));
							if (NumberUtils.isNumber(channelList.get(a).get(c)))							
							{
								subTitle.setServiceId(Integer.parseInt(channelList.get(a).get(c)));//
								AdsSubtitle subTitleTemp = new AdsSubtitle();
								BeanUtils.copyProperties(subTitle, subTitleTemp);
								adsSubtitleList.add(subTitleTemp);
							}
							// TODO 写广告配置文件
							//WriteAdConfig(areaList.get(a),channelList.get(a).get(c),adGis.getPositioncode(),adGis.getCharacteristicIdentification(),resourceFiles);
							// TODO 写广告配置文件缓存
							//SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), channelList.get(a).get(c), adGis.getCharacteristicIdentification(),resourceFiles );
						}
					}
					//untService.addSubtitle(adsSubtitleList);
					log.info("write unt message");
					log.info(adsSubtitleList);
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
					//untService.sendUpdateFlag(ConstantsHelper.UNT_UPDATE_FLAG_MESSAGE,"");
				}
				if (ConstantsAdsCode.PUSH_LINK.equals(adGis.getAdSiteCode()))
				{
					//写 UNT
					AdsLink adsLink=readLinkFile(adGis.getContentPath());
					if (adsLink==null)
					{
						continue;
					}
					List<AdsLink> adsLinkList = new ArrayList<AdsLink>();
					adsLink.setUserIndustry(adGis.getUserindustrys());
					adsLink.setUserlevel(adGis.getUserlevels());
					
					List<String>  areaList = getElementFromData(adGis.getAreas(),ConstantsHelper.SPLIT__SIGN);
					List<List<String>> channelList= getElementFromData(adGis.getChannelId());
					for (int a=0;a<areaList.size();a++)
					{
						for (int c=0;c<channelList.get(a).size();c++)
						{
							log.info("Areacode:"+areaList.get(a)+"channelCode:"+channelList.get(a).get(c));
							if (NumberUtils.isNumber(channelList.get(a).get(c)))							
							{
								adsLink.setServiceId(Integer.parseInt(channelList.get(a).get(c)));//
								AdsLink adsLinkTemp = new AdsLink();
								BeanUtils.copyProperties(adsLink, adsLinkTemp);
								adsLinkList.add(adsLinkTemp);
							}
							// TODO 写广告配置文件
							//WriteAdConfig(areaList.get(a),channelList.get(a).get(c),adGis.getPositioncode(),adGis.getCharacteristicIdentification(),resourceFiles);
							// TODO 写广告配置文件缓存
							//SendAdsElementMap.addAdsElement(adGis.getAdSiteCode(), areaList.get(a), channelList.get(a).get(c), adGis.getCharacteristicIdentification(),resourceFiles );
						}
					}
					//untService.addLink(adsLinkList);
					log.info("write unt message");
					log.info(adsLinkList);
					//untService.sendUpdateFlag(ConstantsHelper.UNT_UPDATE_FLAG_LINK,"");
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");					
				}
			}
			
			// TODO 拉起UNT
			
		}
		
		
		
	}
     
	/** (non-Javadoc)
	 * @see com.avit.ads.pushads.task.service.PushAdsService#sendStartStbAds(java.util.List, java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 * @see com.avit.ads.pushads.task.dao.PushAdsDao#queryStartAds(Date , String )
	 */
	public void sendStartStbAds(Date startTime,String adsTypeCode) {
		
		List adsList;
		try
		{
			//停止播出单，补默认广告素材
			adsList = pushAdsDao.queryEndAds(startTime, adsTypeCode);
			if (adsList!=null && adsList.size()>0)
			{
				for (int i=0;i<adsList.size();i++)
				{
					AdPlaylistGis adGis = (AdPlaylistGis)adsList.get(i);				
					//获取区域列表
					List<String> areaList = getAreaCode(adGis.getAreas());
					for(String areaCode : areaList)
					{
						if (ConstantsAdsCode.PUSH_STARTSTB_SD.equals(adGis.getAdSiteCode()))
						{
							if(!ocgService.deleteFile(areaCode, "initPic-a.iframe", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath())){
								return;
							}
							if(!ocgService.deleteFile(areaCode, "initVideo-a.ts", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath())){
								return;
							}
						}
						else
						{
							if(!ocgService.deleteFile(areaCode, "initPic-c.iframe", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath())){
								return;
							}
							if(!ocgService.deleteFile(areaCode, "initVideo-c.ts", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath())){
								return;
							}
						}
					}
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
				}
			}
		}catch(Exception ie){
			log.error(ie.getMessage());
		}	
		
		//启动播出单
		adsList = pushAdsDao.queryStartAds( startTime, adsTypeCode);
		boolean iframeHDflag=false;
		boolean iframeSDflag=false;
		
		// TODO 循环处理播出单
		if (adsList!=null && adsList.size()>0)
		{
			try {
				ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
			} catch (Exception e) {
				log.error("FTP服务器无法连接",e);
				//warnHelper.writeWarnMsgToDb("FTP服务器无法连接   IP:" + InitConfig.getAdsConfig().getAdResource().getIp());
				return;
			}
			for (int i=0;i<adsList.size();i++)
			{
				AdPlaylistGis adGis = (AdPlaylistGis)adsList.get(i);				
				//获取区域列表,  只会获得一个区域，为0（所有、空）的情况在绑定素材的时候就拆分了
				List<String> areaList = getAreaCode(adGis.getAreas());
				String areaCode = "";
				if(null != areaList && areaList.size() > 0){
					areaCode = areaList.get(0);
				}else{
					log.error("播出单区域码为空 , id: " + adGis.getId());
					continue;
				}
				if(!StringUtils.isNotBlank(areaCode)){
					log.error("播出单区域码为空 , id: " + adGis.getId());
					continue;
				}
				
				String bodyContent = "";
				boolean sendFile1 = false;
				boolean sendFile2 = false;
				if (ConstantsAdsCode.PUSH_STARTSTB_SD.equals(adGis.getAdSiteCode()))
				{
					Gson gson = new Gson();
					StartMaterial startMaterial =(StartMaterial)gson.fromJson(adGis.getContentPath(), StartMaterial.class);//Json2ObjUtil.getObject4JsonString(adGis.getContentPath(), StartMaterial.class);
					if (startMaterial.getPic()!=null && !startMaterial.getPic().equals(""))
					{
						ftpService.download(startMaterial.getPic(), "24.iframe", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeSDTempPath()+"/"+areaCode);
						iframeSDflag=true;	
						UiDesMap.setDefaultstartflag(ConstantsAdsCode.STARTDEFAULT);
					}
					if (startMaterial.getVideo()!=null && !startMaterial.getVideo().equals(""))
					{
						ftpService.download(startMaterial.getVideo(), ConstantsAdsCode.PUSH_STARTSTB_SD_VIDEO_FILE, InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode);
//								UiDesMap.addsdUiDesc("5", ConstantsAdsCode.PUSH_STARTSTB_SD_VIDEO_FILE,areaCode);
						if(!"".equals(bodyContent)){
							bodyContent += ";";
						}
						bodyContent += "5:initVideo-a.ts";
						//视频素材
						boolean success = ocgService.sendFile(areaCode, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/initVideo-a.ts", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
						sendFile1 = true;
						if(!success){
							continue;
						}
					}	
					//0-23.iframe广告图片
					if (startMaterial.getPics()!=null && startMaterial.getPics().size()>0)
					{	
						for (int k=0;k<startMaterial.getPics().size();k++)
						{
							ftpService.download(startMaterial.getPics().get(k).getImage(), startMaterial.getPics().get(k).getTimeInterval()+".iframe", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeSDTempPath()+"/"+areaCode);
						}
						iframeSDflag=true;

						UiDesMap.setDefaultstartflag(ConstantsAdsCode.START24);
					}
					//图片素材
					if (iframeSDflag==true)
					{
						String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE);
					
						if (ConstantsAdsCode.WIN)
						{
							try{
								File inFile = new File(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeSDTempPath());
								ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE));
		                        zos.setComment("多文件处理");
		                        zipFile(inFile, zos, ConstantsAdsCode.START_RESOURCE_PATH);
		                        zos.close(); 
							}catch (Exception e) {
								log.error("压缩文件异常", e);
							}
						}
						else
						{
							String inpath=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeSDTempPath()+"/"+areaCode;
							String outfile=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE;
							linuxRun("zip -r",inpath,outfile);
						}
                        if (oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE)))
                        {//开机图片没有任何变化
//		                        	UiDesMap.addsdUiDesc("1", "1",areaCode);
                        	if(!"".equals(bodyContent)){
								bodyContent += ";";
							}
							bodyContent += "1:1";
                        }else{//开机图片有变化
                        	boolean sendFileFlag =  ocgService.sendFile(areaCode, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
                        	sendFile2 = true;
                        	if(!sendFileFlag){
                        		continue;
                        	}
                        	if(ConstantsAdsCode.START24.equals(UiDesMap.getDefaultstartflag())){//有变化24张图片
//										UiDesMap.addsdUiDesc("1", ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE,areaCode);
								if(!"".equals(bodyContent)){
									bodyContent += ";";
								}
								bodyContent += "1:initPic-a.iframe";
							}else{//有变化的一张默认图片
//										UiDesMap.addsdUiDesc("1", "0",areaCode);
								if(!"".equals(bodyContent)){
									bodyContent += ";";
								}
								bodyContent += "1:0";
							}
                        }
					}
					
					boolean isFileValidated = true;
					if(sendFile1){
						String sourceFilePath1 = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/initVideo-a.ts";
						String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
						if(!validateBeforePlay(areaCode, sourceFilePath1, serverPath)){
							isFileValidated = false;
						}
					}
					if(sendFile2){
						String sourceFilePath2 = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE;
						String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
						if(!validateBeforePlay(areaCode, sourceFilePath2, serverPath)){
							isFileValidated = false;
						}
					}
					
					if(!isFileValidated){
						continue;
					}
					
					//往区域发送NID描述符插入信息
					String ret = uiService.sendUiDesc(bodyContent, areaCode);
					if("0".equals(ret)){
						ocgService.startPlayPgm(areaCode, InitConfig.getConfigProperty(ConstantsAdsCode.UIPGM),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));
					}else{
						log.error("往"+areaCode+"区域NID描述符插入信息失败！return="+ret);
						continue;
					}
					
				}
				else//高清开机处理 
				{
					boolean sendFile1Flag = false;  // added by liuwenping
					boolean sendFile2Flag = false;
					log.info("往区域"+areaCode+"发送高清开机广告开始");
					Gson gson = new Gson();
					StartMaterial startMaterial =(StartMaterial)gson.fromJson(adGis.getContentPath(), StartMaterial.class);//Json2ObjUtil.getObject4JsonString(adGis.getContentPath(), StartMaterial.class);
					if (startMaterial.getVideo()!=null && !startMaterial.getVideo().equals(""))
					{
						ftpService.download(startMaterial.getVideo(), ConstantsAdsCode.PUSH_STARTSTB_HD_VIDEO_FILE, InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode);
						boolean success = ocgService.sendFile(areaCode, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_VIDEO_FILE, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
						if(!success){
							continue;
						}
						sendFile1Flag = true;
//								UiDesMap.addhdUiDesc("5", ConstantsAdsCode.PUSH_STARTSTB_HD_VIDEO_FILE,areaCode);
						if(!"".equals(bodyContent)){
							bodyContent += ";";
						}
						bodyContent += "5:initVideo-c.ts";
						
					}
					if (startMaterial.getPic()!=null && !startMaterial.getPic().equals(""))
					{	
						ftpService.download(startMaterial.getPic(), "24.iframe", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeHDTempPath()+"/"+areaCode);
						iframeHDflag=true;

						UiDesMap.setDefaultstartflag(ConstantsAdsCode.STARTDEFAULT);
					}
					if (startMaterial.getPics()!=null && startMaterial.getPics().size()>0)
					{	
						for (int k=0;k<startMaterial.getPics().size();k++)
						{
							ftpService.download(startMaterial.getPics().get(k).getImage(), startMaterial.getPics().get(k).getTimeInterval()+".iframe", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeHDTempPath()+"/"+areaCode);
						}
						iframeHDflag=true;

						UiDesMap.setDefaultstartflag(ConstantsAdsCode.START24);
					}
					if (iframeHDflag==true)
					{
						String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE);
						
						if (ConstantsAdsCode.WIN)
						{
							try{
								File inFile = new File(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeHDTempPath()+"/"+areaCode);
								ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE));
		                        zos.setComment("多文件处理");
		                        zipFile(inFile, zos,  ConstantsAdsCode.START_RESOURCE_PATH);
		                        zos.close(); 
							}catch(Exception e){
								log.error("压缩文件出现异常",e);
							}
						}
						else
						{
							String inpath=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeHDTempPath()+"/"+areaCode;
							String outfile=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE;
							linuxRun("zip -r",inpath,outfile);
						
						}
                        if (oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE)))
                        {//开机图片没有任何变化
//		                        	UiDesMap.addhdUiDesc("1", "1",areaCode);
                        	if(!"".equals(bodyContent)){
								bodyContent += ";";
							}
							bodyContent += "1:1";
                        }else{//开机图片有变化
                        	boolean success = ocgService.sendFile(areaCode, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
							if(!success){
								continue;
							}
                        	sendFile2Flag = true;
                        	if(ConstantsAdsCode.START24.equals(UiDesMap.getDefaultstartflag())){//有变化24张图片
//										UiDesMap.addhdUiDesc("1", ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE,areaCode);
								if(!"".equals(bodyContent)){
									bodyContent += ";";
								}
								bodyContent += "1:initPic-c.iframe";
							}else{//有变化的一张默认图片
//										UiDesMap.addhdUiDesc("1", "0",areaCode);
								if(!"".equals(bodyContent)){
									bodyContent += ";";
								}
								bodyContent += "1:0";
							}
                        }
					}
					
					boolean isFileValidated = true;
					if(sendFile1Flag){
						String sourceFilePath1 = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_VIDEO_FILE;
						String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
						if(!validateBeforePlay(areaCode, sourceFilePath1, serverPath)){
							isFileValidated = false;
						}
					}
					if(sendFile2Flag){
						String sourceFilePath2 = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE;
						String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
						if(!validateBeforePlay(areaCode, sourceFilePath2, serverPath)){
							isFileValidated = false;
						}
					}
					if(!isFileValidated){
						continue;
					}
					
					//往区域发送NID描述符插入信息
					String ret = uiService.sendUiDesc(bodyContent, areaCode);
					if("0".equals(ret)){
						ocgService.startPlayPgm(areaCode, InitConfig.getConfigProperty(ConstantsAdsCode.UIPGM),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));						
					}else{
						log.error("往"+areaCode+"区域NID描述符插入信息失败！return="+ret);
						continue;
					}
					log.info("往区域"+areaCode+"发送高清开机广告结束");
				}
				
				//更新播出单状态为已执行
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");
			}
		}
		
	}
	
	//查询过期播出单，删除OCG上的广告素材
	private void processOverduePlayList(Date startTime, String adsCode, String adsDesc, String remoteFileName){
		List<AdPlaylistGis> deadAdsList = pushAdsDao.queryEndAds(startTime, adsCode);		
		if (deadAdsList!=null && deadAdsList.size()>0){
			for(AdPlaylistGis adGis : deadAdsList){
				//long playListId = adGis.getId().longValue();
				String areaCode = adGis.getAreas(); //单向投放广告，播出单只会有具体某个地市的区域码
				
				List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
				for (OcgInfo ocg : ocgList) {
					
					if (ocg.getAreaCode().equals(areaCode)) {
						String ocgIp = ocg.getIp();
						int ocgPort = Integer.parseInt(ocg.getPort());
						String ocgUser = ocg.getUser();
						String ocgPwd = ocg.getPwd();
						//建立OCG服务器的FTP连接
						if(ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd)){ //连不上就不删了，UI类广告订单到期删除OCG素材意义不大
							//通过FTP删除OCG上的文件			
							String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
							
							log.info(adsDesc + "广告播出单到期，删除OCG素材   areaCode: " + areaCode + ", ip: " + ocgIp +  ", file: " + remoteDir+ "/" + remoteFileName);
							
							ocgService.deleteFtpFileIfExist(remoteFileName, remoteDir);
							
							//断开OCG服务器的FTP连接
							ocgService.disConnectFtpServer();
						}
					}
				}
				
				//删除描述符
				if(ConstantsAdsCode.PUSH_STARTSTB_HD.equals(adsCode)){
					Gson gson = new Gson();		
					StartMaterial startMaterial =(StartMaterial)gson.fromJson(adGis.getContentPath(), StartMaterial.class);
					if(StringUtils.isNotBlank(startMaterial.getPic())){ //默认开机图片
						uixService.delUiUpdateMsg(areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName(),true);
						//默认图片到期更新adctrl=1
						areaDao.updateAdCtrlByAreaCode(areaCode, "1");
					}else{
						uixService.delUiUpdateMsg(areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName(),false);
					}
					
					
				}else if(ConstantsAdsCode.PUSH_STARTSTB_VIDEO_HD.equals(adsCode)){
					uixService.delUiUpdateMsg(areaCode, UIUpdate.VIDEO.getType(), UIUpdate.VIDEO.getFileName(),false);
				}
				
				//unRealTimeAdsPushHelper.cleanDelMap(playListId);
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
			}
		}
	}
	
	private void failedToPush(Long playListId){
		unRealTimeAdsPushHelper.cleanPushMap(playListId);
		pushAdsDao.updateAdsFlag(playListId, "2");
		unRealTimeAdsPushHelper.changeOrderStateToFailed(playListId);
	}
	
	
	public void sendStartHdVideoAds(Date startTime) {
		
		//查询过期播出单，删除OCG上的广告素材
		processOverduePlayList(startTime, ConstantsAdsCode.PUSH_STARTSTB_VIDEO_HD, "高清开机视频", ConstantsAdsCode.PUSH_STARTSTB_HD_VIDEO_FILE);
			
		//查询新增播出单
		List<AdPlaylistGis> newAdsList = pushAdsDao.queryStartAds( startTime, ConstantsAdsCode.PUSH_STARTSTB_VIDEO_HD);
		if (newAdsList!=null && newAdsList.size()>0)
		{
			for (AdPlaylistGis adGis : newAdsList)
			{
				String areaCode = adGis.getAreas(); //单向投放广告，播出单只会有具体某个地市的区域码
				long playListId = adGis.getId().longValue();
				
				log.info("开始往区域"+areaCode+"发送高清开机视频广告");
				
				//连接素材FTP服务器
				
				boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
				
				if(!conSuccessful){	//还可以重新投放
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{ //投放三次失败
						failedToPush(playListId);
						continue;
					}
				}
								
				Gson gson = new Gson();
				StartMaterial startMaterial =(StartMaterial)gson.fromJson(adGis.getContentPath(), StartMaterial.class);//Json2ObjUtil.getObject4JsonString(adGis.getContentPath(), StartMaterial.class);
				
				if (StringUtils.isNotBlank(startMaterial.getVideo())){
					
					String downLoadPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath() + "/" + areaCode;
					String downLoadFilePath = downLoadPath + "/" + ConstantsAdsCode.PUSH_STARTSTB_HD_VIDEO_FILE;			
//					String oldMd5 = FileDigestUtil.getFileNameMD5(downLoadFilePath);
					
					log.info("下载开机视频素材到本地..");
					ftpService.downloadFile(startMaterial.getVideo(), ConstantsAdsCode.PUSH_STARTSTB_HD_VIDEO_FILE, downLoadPath);
					
//					if(oldMd5.equals(FileDigestUtil.getFileNameMD5(downLoadFilePath))){
//						log.info("素材内容未发生变化，不重新投放");
//						continue;
//					}else{
											
					List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();

					boolean ocgExist = false;
					boolean ocgSuccess = false;
					
					for (OcgInfo ocg : ocgList) {

						if (ocg.getAreaCode().equals(areaCode)) {
							
							ocgExist = true;
							
							String ocgIp = ocg.getIp();
							int ocgPort = Integer.parseInt(ocg.getPort());
							String ocgUser = ocg.getUser();
							String ocgPwd = ocg.getPwd();
							
							//建立OCG服务器的FTP连接
							if(!ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd) ){
								continue;
							}
							//向OCG发送素材					
							log.info("向OCG发送素材..");
							String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
							ocgService.sendFileToFtp(downLoadFilePath, remoteDir);
							
							//断开OCG服务器的FTP连接
							ocgService.disConnectFtpServer();
							
							//通知OCG投放广告
							if(!ocgService.startOcgPlayByIp(ocgIp, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE)){
								continue;
							}
							ocgSuccess = true;
						}
					}
					
					if(!ocgExist){
						String errMsg = "未配置区域【" + areaCode + "】的OCG FTP连接信息";
						log.error(errMsg);
						warnHelper.writeWarnMsgToDb(areaCode,errMsg);
						failedToPush(playListId);
						continue;
					}
					
					if(!ocgSuccess){
						//还可以重试
						if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
							continue;
						}else{  //三次都失败
							failedToPush(playListId);
							continue;
						}
					}
						
						
					if(!uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.VIDEO.getType(), UIUpdate.VIDEO.getFileName())){
						//UI更新通知，若不成功
						if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
							continue;
						}else{
							failedToPush(playListId);
							continue;
						}
					}		
					
					log.info("往区域"+areaCode+"发送高清开机视频广告结束");			
					//}					
					//更新播出单状态为已执行
					unRealTimeAdsPushHelper.cleanPushMap(playListId);
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");			
				}
			}
		}
	}


	public void sendStartHdPicAds(Date startTime) {
		
		//查询过期播出单，删除OCG上的广告素材
		processOverduePlayList(startTime, ConstantsAdsCode.PUSH_STARTSTB_HD, "高清开机图片", ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE);
		
		//查询新增播出单
		List<AdPlaylistGis> newAdsList = pushAdsDao.queryStartAds( startTime, ConstantsAdsCode.PUSH_STARTSTB_HD);
		
		// TODO 循环处理播出单
		if (newAdsList!=null && newAdsList.size()>0)
		{
			for (AdPlaylistGis adGis : newAdsList)
			{
				String areaCode = adGis.getAreas(); //单向投放广告，播出单只会有具体某个地市的区域码
				long playListId = adGis.getId().longValue();
			
				log.info("开始往区域"+areaCode+"发送高清开机图片广告");
								
				//连接素材FTP服务器
				boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
				
				if(!conSuccessful){
					//还可以重新投放
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{ //投放三次失败
						failedToPush(playListId);
						continue;
					}
				}
												
				log.info("下载开机图片素材到本地..");
				String downLoadPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeHDTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.START_RESOURCE_PATH;
				Gson gson = new Gson();				
				StartMaterial startMaterial =(StartMaterial)gson.fromJson(adGis.getContentPath(), StartMaterial.class);
				
				boolean isDefault = false;
				
				if (StringUtils.isNotBlank(startMaterial.getPic())){	
					isDefault = true;
					ftpService.downloadFile(startMaterial.getPic(), "24.iframe", downLoadPath);
					
				}else if (startMaterial.getPics()!=null && startMaterial.getPics().size()>0){	
					
					for (ImageInfo entity : startMaterial.getPics()){
						ftpService.downloadFile(entity.getImage(), entity.getTimeInterval()+".iframe",downLoadPath);
					}
					
				}
		
				log.info("压缩开机图片素材..");
				String zipFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode;
				linuxRun("zip -r",downLoadPath,zipFilePath+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE);
				
				List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();

				boolean ocgExist = false;
				boolean ocgSuccess = false;
				
				for (OcgInfo ocg : ocgList) {

					if (ocg.getAreaCode().equals(areaCode)) {
						
						ocgExist = true;
						
						String ocgIp = ocg.getIp();
						int ocgPort = Integer.parseInt(ocg.getPort());
						String ocgUser = ocg.getUser();
						String ocgPwd = ocg.getPwd();
						
						//建立OCG服务器的FTP连接
						if(!ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd) ){
							continue;
						}
						//向OCG发送开机素材
		            	log.info("向OCG发送开机图片素材..");
		            	String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
		            	ocgService.sendFileToFtp(zipFilePath+"/"+ConstantsAdsCode.PUSH_STARTSTB_HD_IFRAME_FILE, remoteDir);
		            	
		            	//断开OCG服务器的FTP连接
		            	ocgService.disConnectFtpServer();
		            	
		            	//通知OCG投放广告
						if(!ocgService.startOcgPlayByIp(ocgIp, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE)){
							continue;
						}
						ocgSuccess = true;
					}	
				}
				
				if(!ocgExist){
					String errMsg = "未配置区域【" + areaCode + "】的OCG FTP连接信息";
					log.error(errMsg);
					warnHelper.writeWarnMsgToDb(areaCode,errMsg);
					failedToPush(playListId);
					continue;
				}
				
				if(!ocgSuccess){
					//还可以重试
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{  //三次都失败
						failedToPush(playListId);
						continue;
					}
				}

				
				//往区域发送NIT描述符插入信息
				
				boolean uiUpdateSuccess;
				
				if(isDefault){
					uiUpdateSuccess = uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName(),true);
					//默认开机图片更新adctrl值为0
					areaDao.updateAdCtrlByAreaCode(areaCode, "0");
				}else{
					uiUpdateSuccess = uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.PIC.getType(), UIUpdate.PIC.getFileName());
				}
				
				if(!uiUpdateSuccess){
					//UI更新通知，若不成功
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}
								
				log.info("往区域"+areaCode+"发送高清开机图片广告结束");
				
				//更新播出单状态为已执行
				unRealTimeAdsPushHelper.cleanPushMap(playListId);
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");             	
            }				
		}
	}

	public void sendStartSdPicAds(Date startTime) {
		
		//查询过期播出单，删除OCG上的广告素材
		processOverduePlayList(startTime, ConstantsAdsCode.PUSH_STARTSTB_SD, "标清开机图片", ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE);
		
		//查询新增播出单
		List<AdPlaylistGis> newAdsList = pushAdsDao.queryStartAds( startTime, ConstantsAdsCode.PUSH_STARTSTB_SD);
		
		// TODO 循环处理播出单
		if (newAdsList!=null && newAdsList.size()>0)
		{
			for (AdPlaylistGis adGis : newAdsList)
			{
				String areaCode = adGis.getAreas(); //单向投放广告，播出单只会有具体某个地市的区域码
				long playListId = adGis.getId().longValue();
				log.info("开始往区域"+areaCode+"发送标清开机图片广告");
				
				//连接素材FTP服务器
				boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
				
				if(!conSuccessful){
					//还可以重新投放
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{ //投放三次失败
						failedToPush(playListId);
						continue;
					}
				}
			
				log.info("下载开机图片素材到本地..");		
				Gson gson = new Gson();				
				StartMaterial startMaterial =(StartMaterial)gson.fromJson(adGis.getContentPath(), StartMaterial.class);
				String downLoadPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeSDTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.START_RESOURCE_PATH;								
				
				if (StringUtils.isNotBlank(startMaterial.getPic())){
					
					ftpService.downloadFile(startMaterial.getPic(), "24.iframe", downLoadPath);
	
				}else if (startMaterial.getPics()!=null && startMaterial.getPics().size()>0){	
					
					for (ImageInfo entity : startMaterial.getPics()){
						ftpService.downloadFile(entity.getImage(), entity.getTimeInterval()+".iframe", downLoadPath);
					}

				}
		
				log.info("压缩开机图片素材");
				String zipFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode;
				linuxRun("zip -r",downLoadPath,zipFilePath+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE);
				
				//建立OCG服务器的FTP连接
				if(!ocgService.connectFtpServer(areaCode) ){
					//还可以重试
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{  //三次都失败
						failedToPush(playListId);
						continue;
					}
				}
				
				//向OCG发送开机素材
            	log.info("向OCG发送开机图片素材");
                String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
                ocgService.sendFileToFtp(zipFilePath+"/"+ConstantsAdsCode.PUSH_STARTSTB_SD_IFRAME_FILE, remoteDir);	
                
                //断开OCG服务器的FTP连接
                ocgService.disConnectFtpServer();
                
                //通知OCG投放广告
				if(!ocgService.startOcgPlay(areaCode, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE)){
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}
				
				//往区域发送NID描述符插入信息
				if(!uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.PIC.getType(), "initPic-a.iframe")){
					//UI更新通知，若不成功
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}
                
				log.info("往区域"+areaCode+"发送标清开机图片广告结束");
				
				//更新播出单状态为已执行
				unRealTimeAdsPushHelper.cleanPushMap(playListId);
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");               	
			
			}
		}
		
	}
	

	public void sendHdAudioAds(Date startTime) {
		
		//查询过期播出单，删除OCG上的广告素材		
		processOverduePlayList(startTime, ConstantsAdsCode.PUSH_FREQUENCE_HD, "高清音频广播", ConstantsAdsCode.ADVRESOURCE_C);
		
		
		//查询新增播出单
		List<AdPlaylistGis> newAdsList = pushAdsDao.queryStartAds( startTime, ConstantsAdsCode.PUSH_FREQUENCE_HD);
				
		if (newAdsList!=null && newAdsList.size()>0)
		{
			for (AdPlaylistGis adGis : newAdsList)
			{				
				String areaCode = adGis.getAreas(); //单向投放广告，播出单只会有具体某个地市的区域码
				long playListId = adGis.getId().longValue();
				
				log.info("往区域"+areaCode+"发送高清音频广播广告开始");
				
				//连接素材FTP服务器
				boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
				
				if(!conSuccessful){
					//还可以重新投放
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{ //投放三次失败
						failedToPush(playListId);
						continue;
					}
				}
				
				//从素材FTP服务器下载素材
				List<String> serviceIdList = getListFromJson(adGis.getChannelId());
				
				String downLoadPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.ADVRESOURCE_HD_PATH;				
				String zipFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode;
												
				if(serviceIdList == null || serviceIdList.size()<=0 ){
					log.error("广播收听背景播出单 " + adGis.getId() + " 频道列表为空");
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}else{
					log.info("下载广播收听背景素材到本地..");
					for(String serviceId : serviceIdList){
						ftpService.downloadFile(adGis.getContentPath(),serviceId+".iframe", downLoadPath);
					}
				}				

				log.info("压缩素材文件..");
				String sourcePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+"/"+areaCode+"/" + "advResource-c";				
				linuxRun("zip -r",sourcePath,zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_C);
				
				//建立OCG服务器的FTP连接
				if(!ocgService.connectFtpServer(areaCode) ){
					//还可以重试
					if(unRealTimeAdsPushHelper.delDecreaseAndTryAgain(playListId)){
						continue;
					}else{  //三次都失败
						failedToPush(playListId);
						continue;
					}
				}

            	//向OCG发送素材文件
				log.info("向OCG发送广播收听背景素材..");
				String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
            	ocgService.sendFileToFtp(zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_C, remoteDir);
            	
            	ocgService.disConnectFtpServer();
            	
            	//往区域发送NID描述符插入信息
            	String bodyContent = "3:advResource-c.dat";
            	
            	if(ocgService.sendUiDesc(areaCode, bodyContent, remoteDir)){ //UI更新通知成功
					//通知OCG投放广告
					if(!ocgService.startOcgPlay(areaCode, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE)){
						if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
							continue;
						}else{
							failedToPush(playListId);
							continue;
						}
					}
				}else{ //UI更新通知失败
					log.error("往"+areaCode+"区域NID描述符插入信息失败!");
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}
            	
                log.info("往区域"+areaCode+"发送高清音频广播广告结束");
					
				//更新播出单状态为已执行
                unRealTimeAdsPushHelper.cleanPushMap(playListId);
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");
			}
		}
	}

	public void sendSdAudioAds(Date startTime) {
		
		//查询过期播出单，删除OCG上的广告素材		
		processOverduePlayList(startTime, ConstantsAdsCode.PUSH_FREQUENCE_SD, "标清音频广播", ConstantsAdsCode.ADVRESOURCE_A);
		
		//查询新增播出单
		List<AdPlaylistGis> newAdsList = pushAdsDao.queryStartAds( startTime, ConstantsAdsCode.PUSH_FREQUENCE_SD);
				
		if (newAdsList!=null && newAdsList.size()>0)
		{
			for (AdPlaylistGis adGis : newAdsList)
			{				
				String areaCode = adGis.getAreas(); //单向投放广告，播出单只会有具体某个地市的区域码
				long playListId = adGis.getId().longValue();
				
				log.info("往区域"+areaCode+"发送标清音频广播广告开始");
				
				//连接素材FTP服务器
				boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
				
				if(!conSuccessful){
					//还可以重新投放
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{ //投放三次失败
						failedToPush(playListId);
						continue;
					}
				}
				
				//从素材FTP服务器下载素材
				List<String> serviceIdList = getListFromJson(adGis.getChannelId());
				
				String downLoadPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioSDTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.ADVRESOURCE_SD_PATH;				
				String zipFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode;
							
				if(serviceIdList == null || serviceIdList.size()<=0 ){
					log.error("播出单中的频道列表为空");
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}else{
					log.info("下载广播收听背景素材到本地..");
					for(String serviceId : serviceIdList){
						ftpService.downloadFile(adGis.getContentPath(),serviceId+".iframe", downLoadPath);
					}
				}				
		
				log.info("压缩素材文件..");
				String sourcePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+"/"+areaCode+"/" + "advResource-a";		
				linuxRun("zip -r",sourcePath,zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_A);
				
				//建立OCG服务器的FTP连接
				if(!ocgService.connectFtpServer(areaCode) ){
					//还可以重试
					if(unRealTimeAdsPushHelper.delDecreaseAndTryAgain(playListId)){
						continue;
					}else{  //三次都失败
						failedToPush(playListId);
						continue;
					}
				}

            	//向OCG发送素材文件
				log.info("向OCG发送广播收听背景素材..");
				String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
            	ocgService.sendFileToFtp(zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_A, remoteDir);
            	
            	ocgService.disConnectFtpServer();
            	
				//往区域发送NID描述符插入信息
            	String bodyContent = "3:advResource-a.dat";
            	
             	if(ocgService.sendUiDesc(areaCode, bodyContent, remoteDir)){ //UI更新通知成功
					//通知OCG投放广告
					if(!ocgService.startOcgPlay(areaCode, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE)){
						if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
							continue;
						}else{
							failedToPush(playListId);
							continue;
						}
					}
				}else{ //UI更新通知失败
					log.error("往"+areaCode+"区域NID描述符插入信息失败!");
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}

                log.info("往区域"+areaCode+"发送标清音频广播广告结束");
					
				//更新播出单状态为已执行
                unRealTimeAdsPushHelper.cleanPushMap(playListId);
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");
			}
		}	
	}

	public void sendHdRecomendAds(Date startTime) {
		
		//查询过期播出单，修改播出单状态
		List<AdPlaylistGis> deadAdsList = pushAdsDao.queryEndAds(startTime, ConstantsAdsCode.PUSH_RECOMMEND);		
		
		if (deadAdsList!=null && deadAdsList.size()>0){
			for(AdPlaylistGis adGis : deadAdsList){
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
			}
		}
		
		//查询新增播出单
		List<AdPlaylistGis> newAdsList = pushAdsDao.queryStartAds( startTime, ConstantsAdsCode.PUSH_RECOMMEND);

		if (newAdsList!=null && newAdsList.size()>0)
		{
			for (AdPlaylistGis adGis : newAdsList){
				
				String areaCode = adGis.getAreas(); //单向投放广告，播出单只会有具体某个地市的区域码
		
				log.info("往区域"+areaCode+"发送推荐页面广告开始");
				long playListId = adGis.getId().longValue();
							
				//连接素材FTP服务器
				boolean conSuccessful = ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
				
				if(!conSuccessful){
					//还可以重新投放
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{ //投放三次失败
						failedToPush(playListId);
						continue;
					}
				}
				
				String zipFileName = "recomend.zip";   //压缩文件下载到本地，需重命名，这样可比较文件的MD5值
				String downLoadPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath() + "/"+areaCode;
							
				//下载素材到本地
				log.info("下载热点推荐素材到本地..");
				ftpService.downloadFile(adGis.getContentPath(),zipFileName, downLoadPath);
				
				log.info("解压热点推荐素材..");			
				linuxUnZip("unzip -o -d",downLoadPath + "/" + zipFileName, downLoadPath);
				
				String jsFilePath = downLoadPath+"/"+ConstantsAdsCode.RECOMMEND+"/"+ConstantsAdsCode.HOT_RECOMMEND_JS;
				String htmFilePath = downLoadPath+"/"+ConstantsAdsCode.RECOMMEND+"/"+ConstantsAdsCode.HOT_RECOMMEND_HTML;
				String imageFilePath = downLoadPath+"/"+ConstantsAdsCode.RECOMMEND+"/"+ConstantsAdsCode.HOT_RECOMMEND_INAGE;
				String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getRecommendTargetPath();
				
				//建立OCG服务器的FTP连接
				if(!ocgService.connectFtpServer(areaCode) ){
					//还可以重试
					if(unRealTimeAdsPushHelper.delDecreaseAndTryAgain(playListId)){
						continue;
					}else{  //三次都失败
						failedToPush(playListId);
						continue;
					}
				}
				
				log.info("向OCG发送热点推荐素材..");
				ocgService.sendFileToFtp(jsFilePath, remoteDir);					
				ocgService.sendFileToFtp(htmFilePath, remoteDir);					
				ocgService.sendFileToFtp(imageFilePath, remoteDir);
				
				ocgService.disConnectFtpServer();
				
				if(!ocgService.startOcgPlay(areaCode, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.RECOMMEND_TYPE)){
					if(unRealTimeAdsPushHelper.pushDecreaseAndTryAgain(playListId)){
						continue;
					}else{
						failedToPush(playListId);
						continue;
					}
				}
				
				log.info("往区域"+areaCode+"发送推荐页面广告成功");
				unRealTimeAdsPushHelper.cleanPushMap(playListId);
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");												
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void sendAdResourceAds(Date startTime) {	
		//分区域处理	
		List<TReleaseArea> areaEntityList = areaDao.getAllArea();
		for(TReleaseArea areaEntity : areaEntityList){
			
			String areaCode = areaEntity.getAreaCode();
			
			boolean changed = false;
			
			String downLoadPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+"/"+areaCode+"/"+ConstantsAdsCode.ADVRESOURCE_HD_PATH;				
			String zipFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+areaCode;
			
			//查询过期列表，删除本地素材（根据播出单删除素材）	 
	        String hql =  "from AdPlaylistGis ads where ads.state=1 and ads.areas = ? and ads.endTime <=? and (ads.adSiteCode =? or ads.adSiteCode =? )";
	            
	        List<AdPlaylistGis> resultList = pushAdsDao.getTemplate().find(hql, areaCode, startTime, ConstantsAdsCode.PUSH_FREQUENCE_HD, ConstantsAdsCode.PUSH_LIVE_UNDER_HD);
	        
	        if(null != resultList && resultList.size() > 0){
	        	changed = true; 
	        	for(AdPlaylistGis playListEntity : resultList){
	        		if( ConstantsAdsCode.PUSH_FREQUENCE_HD.equals(playListEntity.getAdSiteCode()) ){  //音频背景广告
	        			List<String> serviceIdList = getListFromJson(playListEntity.getChannelId());
	        			if(null != serviceIdList && serviceIdList.size() > 0){
	        				log.info("广播收听背景广告播出单过期，删除本地素材..");
	        				for(String serviceId : serviceIdList){
	        					deleteFile(downLoadPath, serviceId+".iframe");
							}
	        			}
	        		}else if( ConstantsAdsCode.PUSH_LIVE_UNDER_HD.equals(playListEntity.getAdSiteCode()) ){  //直播下排广告
	        			log.info("直播下排广告播出单过期，删除本地素材..");
	        			String f1 = ConstantsAdsCode.LIVE_UNDER_HD_FILE1;
	        			String f2 = ConstantsAdsCode.LIVE_UNDER_HD_FILE2;
	        			String f3 = ConstantsAdsCode.LIVE_UNDER_HD_FILE3;
	        			deleteFile(downLoadPath, f1);
	        			deleteFile(downLoadPath, f2);
	        			deleteFile(downLoadPath, f3);
	        			//再传默认素材进去
	        			log.info("添加默认素材至本地..");
	        			String rootPath = this.getClass().getClassLoader().getResource("").getPath();
	        			try {
	        				File downLoadDir = new File(downLoadPath);
							FileUtils.copyFileToDirectory(new File(rootPath + "/" + f1), downLoadDir);
							FileUtils.copyFileToDirectory(new File(rootPath + "/" + f2), downLoadDir);
							FileUtils.copyFileToDirectory(new File(rootPath + "/" + f3), downLoadDir);
						} catch (IOException e) {
							log.error("复制文件出现异常", e);
						}
	        		}
	        		//播出单状态更新为过期
	        		pushAdsDao.updateAdsFlag(playListEntity.getId().longValue(), "4");
	        	}
	        }
		
			//查询新增列表， 下载素材到本地 
	        String newHql =  "from AdPlaylistGis ads where ads.state = 0 and ads.areas = ? and ads.startTime <=? and ads.endTime >=? and (ads.adSiteCode =? or ads.adSiteCode =? )";
	        List<AdPlaylistGis> newResultList = pushAdsDao.getTemplate().find(newHql, areaCode, startTime, startTime, ConstantsAdsCode.PUSH_FREQUENCE_HD, ConstantsAdsCode.PUSH_LIVE_UNDER_HD);
			if(null != newResultList && newResultList.size() > 0){
				changed = true; 
				Gson gson = new Gson();	
				for(AdPlaylistGis playListEntity : newResultList){
					
					long playListId = playListEntity.getId().longValue();
					
					//连接素材FTP服务器
					ftpService.connectServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
					
					if( ConstantsAdsCode.PUSH_FREQUENCE_HD.equals(playListEntity.getAdSiteCode()) ){
						//从素材FTP服务器下载素材
						List<String> serviceIdList = getListFromJson(playListEntity.getChannelId());
						if(serviceIdList == null || serviceIdList.size()<=0 ){
							log.error("广播收听背景播出单 " + playListEntity.getId() + " 频道列表为空");
							pushAdsDao.updateAdsFlag(playListId, "2");
							continue;						
						}else{
							log.info("下载广播收听背景素材到本地..");
							for(String serviceId : serviceIdList){
								ftpService.downloadFile(playListEntity.getContentPath(),serviceId+".iframe", downLoadPath);
							}
						}			
					}else if(ConstantsAdsCode.PUSH_LIVE_UNDER_HD.equals(playListEntity.getAdSiteCode())){
						 
						Object loopMaterialObj = gson.fromJson(playListEntity.getContentPath(), new TypeToken<List<LoopMaterialEntry>>(){}.getType());
						List<LoopMaterialEntry> materialList = (List<LoopMaterialEntry>)loopMaterialObj;
						if(null != materialList && materialList.size() > 0){
							log.info("下载直播下排素材到本地..");
							for(LoopMaterialEntry entry : materialList){
								String index = entry.getIndex();
								String path = entry.getPic();
								if(StringUtils.isNotBlank(path) && StringUtils.isNotBlank(index)){
									int mid = Integer.parseInt(index) - 1;
									ftpService.downloadFile(path, ConstantsAdsCode.LIVE_UNDER_HD_FILE_PREFIX + mid + ConstantsAdsCode.LIVE_UNDER_HD_FILE_POSTFIX, downLoadPath);
								}
							}
						}
					}					
				}			
				
			}       
	        
			if(changed){
				
				//删除 advResource-c.dat
				deleteFile(zipFilePath, ConstantsAdsCode.ADVRESOURCE_C);
				
				//压缩文件
				log.info("压缩素材文件..");
				String sourcePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+"/"+areaCode+"/" + "advResource-c";				
				linuxRun("zip -r",sourcePath,zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_C);
				
				List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();

				//boolean ocgExist = false;
				
				for (OcgInfo ocg : ocgList) {
					if (ocg.getAreaCode().equals(areaCode)) {
						
						//ocgExist = true;
						
						String ocgIp = ocg.getIp();
						int ocgPort = Integer.parseInt(ocg.getPort());
						String ocgUser = ocg.getUser();
						String ocgPwd = ocg.getPwd();
						
						//建立OCG服务器的FTP连接
						ocgService.connectFtpServer(ocgIp, ocgPort, ocgUser, ocgPwd);
						
						//向OCG发送素材文件
						log.info("向OCG发送广告素材..");
						String remoteDir = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
		            	ocgService.sendFileToFtp(zipFilePath+"/"+ConstantsAdsCode.ADVRESOURCE_C, remoteDir);
		            	
		            	ocgService.disConnectFtpServer();
		            	
		            	//OCG播发广告
		            	ocgService.startOcgPlayByIp(ocgIp, remoteDir, ConstantsHelper.MAIN_CHANNEL, ConstantsHelper.UI_TYPE);
						
					}
				}
            	
            	//UI更新
            	uixService.sendUiUpdateMsg(ConstantsHelper.UI_PLAY, areaCode, UIUpdate.ADV.getType(), UIUpdate.ADV.getFileName());
            	
            	//更新播出单状态
            	if(null != newResultList && newResultList.size() > 0){
            		for(AdPlaylistGis playListEntity : newResultList){
            			pushAdsDao.updateAdsFlag(playListEntity.getId().longValue(), "1");
            		}         		
            	}
                log.info("往区域"+areaCode+"发送广告结束");			
			}
		}
	}
	

	@SuppressWarnings("unchecked")
	public void sendChannelSubtitleAds() {
					
		//查询过期列表（播出单结束时间 < 当前时间），发送不显示字幕，更新播出单状态为4	 
		String overdueHql =  "from AdPlaylistGis ads where ads.state=1 and ads.endTime <=? and ads.adSiteCode =? ";			            
		List<AdPlaylistGis> overdueList = pushAdsDao.getTemplate().find(overdueHql, new Date(), ConstantsAdsCode.PUSH_CHANNEL_SUBTITLE);
		sendChannelSubtitle3(overdueList,"0","4");
		if(overdueList != null && overdueList.size() > 0){
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//查询新增列表（播出单结束时间 > 当前时间，且状态为0），发送显示字幕，更新播出单状态为1
		String newHql =  "from AdPlaylistGis ads where ads.state = 0 and ads.startTime <= ? and ads.endTime >=? and ads.adSiteCode =? ";    
		Date now = new Date();
		List<AdPlaylistGis> newList = pushAdsDao.getTemplate().find(newHql, now, now, ConstantsAdsCode.PUSH_CHANNEL_SUBTITLE);
		sendChannelSubtitle3(newList,"1","1");
		
	}
	
	private List<AdPlaylistGis> pickPlayListByAreaCode(List<AdPlaylistGis> playList, String areaCode){
		List<AdPlaylistGis> resultList = new ArrayList<AdPlaylistGis>();
		if(null != playList){
			for(AdPlaylistGis gis : playList){
				if(gis.getAreas().equals(areaCode)){
					resultList.add(gis);
				}
			}
		}
		return resultList;
	}
	
	/*private void stopChannelSubtitle(List<AdPlaylistGis> overduePlayList){
		List<String> areaList = InitAreas.getInstance().getAreas();
		for(String areaCode : areaList){
			List<AdPlaylistGis> pickedList = pickPlayListByAreaCode(overduePlayList, areaCode);
			if(pickedList.size() > 0){
				log.info("区域" + areaCode + "频道字幕广告到期，发停止消息...");
				
				AdPlaylistGis adGis0 = pickedList.get(0);
				
				//拼装字幕UNT消息
				String tvn = adGis0.getTvn();
				String userIndustrys = adGis0.getUserindustrys();
				String userLevels = adGis0.getUserlevels();
				
				TvnTarget tvnTargetModel = new TvnTarget();
				tvnTargetModel.setTvnType("2"); 
				tvnTargetModel.setTvn(tvn);
				tvnTargetModel.setCaIndustryType(userIndustrys);
				tvnTargetModel.setCaUserLevel(userLevels);
				
				Gson gson = new Gson();
				TextMate text = gson.fromJson(adGis0.getContentPath(), TextMate.class);
				
				MsubtitleInfo subtitleModel = new MsubtitleInfo();
				subtitleModel.setUiId("a");
				subtitleModel.setActionType("0");
				subtitleModel.setTimeout(text.getDurationTime() + "");
				subtitleModel.setFontColor(text.getFontColor());
				subtitleModel.setFontSize(text.getFontSize() + "");
				String[] coordinate = text.getPositionVertexCoordinates().split("\\*");
				subtitleModel.setBackgroundX(coordinate[0]);
				subtitleModel.setBackgroundY(coordinate[1]);
				String[] widthHeight = text.getPositionWidthHeight().split("\\*");
				subtitleModel.setBackgroundWidth(widthHeight[0]);
				subtitleModel.setBackgroundHeight(widthHeight[1]);
				subtitleModel.setBackgroundColor(text.getBkgColor());
				subtitleModel.setShowFrequency(text.getRollSpeed() + "");
				subtitleModel.setWord(text.getContent());
				
				List<ChannelSubtitleElement> list = new ArrayList<ChannelSubtitleElement>();
						
				TvnTarget tvnTarget = new TvnTarget();
				BeanUtils.copyProperties(tvnTargetModel, tvnTarget);  //历史版本是做了遍历处理，用到了拷贝
				tvnTarget.setServiceID("-1");
				
				MsubtitleInfo subtitle = new MsubtitleInfo();
				BeanUtils.copyProperties(subtitleModel, subtitle);
				
				ChannelSubtitleElement elem = new ChannelSubtitleElement();
				elem.setTvnTarget(tvnTarget);
				elem.setSubtitleInfo(subtitle);
				
				list.add(elem);
				
				
				ChannelSubtitle channelSubtitle = new ChannelSubtitle();
				channelSubtitle.setChannelSubtitleElemList(list);

				//向OCG发送UNT消息
				ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNEL_SUBTITLE, channelSubtitle,areaCode);																		
				
				for(AdPlaylistGis adGis : pickedList){					
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
				}
			}
		}
		
	}
	
	private void sendChannelSubtitle(List<AdPlaylistGis> newPlayLists){
		List<String> areaList = InitAreas.getInstance().getAreas();
		for(String areaCode : areaList){
			List<AdPlaylistGis> pickedList = pickPlayListByAreaCode(newPlayLists, areaCode);
			if(pickedList.size() > 0){
				log.info("向区域" + areaCode + "投放频道字幕广告...");
				
				List<ChannelSubtitleElement> list = new ArrayList<ChannelSubtitleElement>();
				//拼装字幕UNT消息
				for(AdPlaylistGis adGis : pickedList){					
					String tvn = adGis.getTvn();
					String userIndustrys = adGis.getUserindustrys();
					String userLevels = adGis.getUserlevels();
					
					TvnTarget tvnTargetModel = new TvnTarget();
					tvnTargetModel.setTvnType("2"); 
					tvnTargetModel.setTvn(tvn);
					tvnTargetModel.setCaIndustryType(userIndustrys);
					tvnTargetModel.setCaUserLevel(userLevels);
					
					Gson gson = new Gson();
					TextMate text = gson.fromJson(adGis.getContentPath(), TextMate.class);
					
					MsubtitleInfo subtitleModel = new MsubtitleInfo();
					subtitleModel.setUiId("a");
					subtitleModel.setActionType("1");
					subtitleModel.setTimeout(text.getDurationTime() + "");
					subtitleModel.setFontColor(text.getFontColor());
					subtitleModel.setFontSize(text.getFontSize() + "");
					String[] coordinate = text.getPositionVertexCoordinates().split("\\*");
					subtitleModel.setBackgroundX(coordinate[0]);
					subtitleModel.setBackgroundY(coordinate[1]);
					String[] widthHeight = text.getPositionWidthHeight().split("\\*");
					subtitleModel.setBackgroundWidth(widthHeight[0]);
					subtitleModel.setBackgroundHeight(widthHeight[1]);
					subtitleModel.setBackgroundColor(text.getBkgColor());
					subtitleModel.setShowFrequency(text.getRollSpeed() + "");
					subtitleModel.setWord(text.getContent());
					
					List<String> serviceIdList = getListFromJson(adGis.getChannelId());
					for(String serviceId : serviceIdList){
						TvnTarget tvnTarget = new TvnTarget();
						BeanUtils.copyProperties(tvnTargetModel, tvnTarget);
						tvnTarget.setServiceID(serviceId);
						
						MsubtitleInfo subtitle = new MsubtitleInfo();
						BeanUtils.copyProperties(subtitleModel, subtitle);
						
						ChannelSubtitleElement elem = new ChannelSubtitleElement();
						elem.setTvnTarget(tvnTarget);
						elem.setSubtitleInfo(subtitle);
						
						list.add(elem);
					}
				}
				
				ChannelSubtitle channelSubtitle = new ChannelSubtitle();
				channelSubtitle.setChannelSubtitleElemList(list);

				//向OCG发送UNT消息
				ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNEL_SUBTITLE, channelSubtitle,areaCode);																		

				for(AdPlaylistGis adGis : pickedList){					
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");
				}
				
			}
		}
	} */

	private void sendChannelSubtitle3(List<AdPlaylistGis> playLists, String actionType, String state){
		List<String> areaList = InitAreas.getInstance().getAreas();
		for(String areaCode : areaList){
			List<AdPlaylistGis> pickedList = pickPlayListByAreaCode(playLists, areaCode);
			if(pickedList.size() > 0){
				if("1".equals(actionType)){
					log.info("向区域" + areaCode + "投放频道字幕广告...");
				}else{
					log.info("区域" + areaCode + "频道字幕广告到期，发停止消息...");
				}
				
				Map<String, List<ChannelSubtitleElement>> channelTsMap =  new HashMap<String, List<ChannelSubtitleElement>>();
				//拼装字幕UNT消息
				for(AdPlaylistGis adGis : pickedList){					
					String tvn = adGis.getTvn();
					String userIndustrys = adGis.getUserindustrys();
					String userLevels = adGis.getUserlevels();
					
					TvnTarget tvnTargetModel = new TvnTarget();
					tvnTargetModel.setTvnType("2"); 
					tvnTargetModel.setTvn(tvn);
					tvnTargetModel.setCaIndustryType(userIndustrys);
					tvnTargetModel.setCaUserLevel(userLevels);
					
					List<String> serviceIdList = getListFromJson(adGis.getChannelId());
					for(String serviceId : serviceIdList){
						
						TvnTarget tvnTarget = new TvnTarget();
						BeanUtils.copyProperties(tvnTargetModel, tvnTarget);
						tvnTarget.setServiceID(serviceId);
						
						Gson gson = new Gson();
						TextMate text = gson.fromJson(adGis.getContentPath(), TextMate.class);
						
						ChannelSubtitleInfo subtitleInfo = new ChannelSubtitleInfo();
						subtitleInfo.setUiId("a");
						subtitleInfo.setActionType(actionType);
						subtitleInfo.setTimeout(text.getDurationTime() + "");
						subtitleInfo.setFontColor(text.getFontColor());
						subtitleInfo.setFontSize(text.getFontSize() + "");
						String[] coordinate = text.getPositionVertexCoordinates().split("\\*");
						subtitleInfo.setBackgroundX(coordinate[0]);
						subtitleInfo.setBackgroundY(coordinate[1]);
						String[] widthHeight = text.getPositionWidthHeight().split("\\*");
						subtitleInfo.setBackgroundWidth(widthHeight[0]);
						subtitleInfo.setBackgroundHeight(widthHeight[1]);
						subtitleInfo.setBackgroundColor(text.getBkgColor());
						subtitleInfo.setShowFrequency(text.getRollSpeed() + "");
						
						String[] words = text.getContent().split("@_@");
						List<SubtitlePart> subtitleList = new ArrayList<SubtitlePart>();
						for(int i = 0; i < words.length; i ++){
							String word = words[i].trim();
							if(StringUtils.isBlank(word)){
								continue;
							}
							
							SubtitlePart part = new SubtitlePart();
							part.setId(i + "");
							part.setWord(word);
							
							subtitleList.add(part);
						}
						SubtitleContent content = new SubtitleContent();
						content.setSubtitleList(subtitleList);
						ChannelSubtitleElement elem = new ChannelSubtitleElement();
						elem.setTvnTarget(tvnTarget);
						elem.setSubtitleInfo(subtitleInfo);
						elem.setSubtitleContent(content);
						
						String tsId = pushAdsDao.getAreaChannelTsId(areaCode, serviceId);
						if(StringUtils.isBlank(tsId)){
							continue;
						}
						List<ChannelSubtitleElement> elementList = channelTsMap.get(tsId);
						if(elementList == null){
							elementList = new ArrayList<ChannelSubtitleElement>();
							channelTsMap.put(tsId, elementList);
						}
						elementList.add(elem);
					}
				}
				boolean success = false;
				for(String tsId : channelTsMap.keySet()){
					List<ChannelSubtitleElement> channelSubtitleList = channelTsMap.get(tsId);
				
					ChannelSubtitle channelSubtitle = new ChannelSubtitle();
					channelSubtitle.setTsId(tsId);
					channelSubtitle.setChannelSubtitleElemList(channelSubtitleList);
					//向OCG发送UNT消息
					success = ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_CHANNEL_SUBTITLE, channelSubtitle, areaCode, tsId);
					if(!success){
						break;
					}
				}
				if(success){
					for(AdPlaylistGis adGis : pickedList){					
						pushAdsDao.updateAdsFlag(adGis.getId().longValue(), state);
					}
				}else{
					for(AdPlaylistGis adGis : pickedList){					
						pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "2");
						pushAdsDao.updateOrderState(adGis.getId().longValue(), "9");
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void sendSubtitleAds() {
		
		//查询过期列表（播出单结束时间 < 当前时间），发送不显示字幕，更新播出单状态为4	 
		String overdueHql =  "from AdPlaylistGis ads where ads.state=1 and ads.endTime <=? and ads.adSiteCode =? ";			            
		List<AdPlaylistGis> overdueList = pushAdsDao.getTemplate().find(overdueHql, new Date(), ConstantsAdsCode.PUSH__SUBTITLE);
		sendSubtitle(overdueList, "0", "4");
		
		if(overdueList != null && overdueList.size() > 0){
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//查询新增列表（播出单结束时间 > 当前时间，且状态为0），发送显示字幕，更新播出单状态为1
		String newHql =  "from AdPlaylistGis ads where ads.state = 0 and ads.startTime <= ? and ads.endTime >=? and ads.adSiteCode =? ";   
		Date now = new Date();
		List<AdPlaylistGis> newList = pushAdsDao.getTemplate().find(newHql, now, now, ConstantsAdsCode.PUSH__SUBTITLE);
		sendSubtitle(newList, "1", "1");
	}
	
	private void sendSubtitle(List<AdPlaylistGis> playLists, String actionType, String state){
		if(null != playLists && playLists.size() > 0){
			for(AdPlaylistGis adGis : playLists){
				String areaCode = adGis.getAreas(); 
				
				if("1".equals(actionType)){
					log.info("向区域" + areaCode + "投放菜单字幕广告...");
				}else{
					log.info("区域" + areaCode + "菜单字幕广告到期，发停止消息...");
				}
				
				Gson gson = new Gson();
				TextMate text = gson.fromJson(adGis.getContentPath(), TextMate.class);
				String[] words = text.getContent().split("@_@");
				
				ChannelSubtitleInfo subtitleInfo = new ChannelSubtitleInfo();
				subtitleInfo.setUiId("a");
				subtitleInfo.setActionType(actionType);
				subtitleInfo.setTimeout(text.getDurationTime() + "");
				subtitleInfo.setFontColor(text.getFontColor());
				subtitleInfo.setFontSize(text.getFontSize() + "");
				String[] coordinate = text.getPositionVertexCoordinates().split("\\*");
				subtitleInfo.setBackgroundX(coordinate[0]);
				subtitleInfo.setBackgroundY(coordinate[1]);
				String[] widthHeight = text.getPositionWidthHeight().split("\\*");
				subtitleInfo.setBackgroundWidth(widthHeight[0]);
				subtitleInfo.setBackgroundHeight(widthHeight[1]);
				subtitleInfo.setBackgroundColor(text.getBkgColor());
				subtitleInfo.setShowFrequency(text.getRollSpeed() + "");
				
				List<SubtitlePart> subtitleInfoList = new ArrayList<SubtitlePart>();
				for(int i =0; i < words.length; i ++){
					String word = words[i].trim();
					if(StringUtils.isBlank(word)){
						continue;
					}
					SubtitlePart part = new SubtitlePart();
					part.setId(i+"");
					part.setWord(word);
					subtitleInfoList.add(part);
				}
				SubtitleContent content = new SubtitleContent();
				content.setSubtitleList(subtitleInfoList);
				Subtitle subtitle = new Subtitle();
				subtitle.setSubtitleInfo(subtitleInfo);
				subtitle.setSubtileContent(content);
		
				//向OCG发送UNT消息
				boolean success = ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_MSUBTITLE, subtitle, areaCode, null);
				
				if(success){
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), state);
				}else{
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "2");
					pushAdsDao.updateOrderState(adGis.getId().longValue(), "9");
				}
			}
		}
	}

	/** (non-Javadoc)
	 * @see com.avit.ads.pushads.task.service.PushAdsService#sendStartStbAds(java.util.List, java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 * @see com.avit.ads.pushads.task.dao.PushAdsDao#queryStartAds(Date , String )
	 */
	public void sendAudioAds(Date startTime,String adsTypeCode) {
		List<AdPlaylistGis> adsList = null;
		try
		{
			//停止播出单，补默认广告素材
			adsList = pushAdsDao.queryEndAds(startTime, adsTypeCode);
			if (adsList!=null && adsList.size()>0)
			{
				for (AdPlaylistGis adGis : adsList)
				{
					//获取区域列表
					List<String> areaList = getAreaCode(adGis.getAreas());
					
					for (String areaCode : areaList)
					{
						if (ConstantsAdsCode.PUSH_FREQUENCE_SD.equals(adGis.getAdSiteCode()))
						{
							boolean success = ocgService.deleteFile(areaCode, "advResource-a.dat", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
							if(!success){
								return;
							}
						}
						else
						{
							boolean success = ocgService.deleteFile(areaCode, "advResource-c.dat", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
							if(!success){
								return;
							}
						}
					}
					pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
				}
			}
		}catch(Exception ie){
			log.error(ie);
		}	
		
		//启动播出单
	
		adsList = pushAdsDao.queryStartAds( startTime, adsTypeCode);
		// TODO 循环处理播出单
		if (adsList!=null && adsList.size()>0)
		{
			try {
				ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
			} catch (Exception e) {
				log.error("FTP服务器无法连接",e);
				//warnHelper.writeWarnMsgToDb("FTP服务器无法连接   IP:" + InitConfig.getAdsConfig().getAdResource().getIp());
				return;
			} 
			Map<String,String> areaMap = new HashMap<String,String>();
			for (int i=0;i<adsList.size();i++)
			{	
				
				AdPlaylistGis adGis = (AdPlaylistGis)adsList.get(i);				
				//获取区域列表,  只会获得一个区域，为0（所有、空）的情况在绑定素材的时候就拆分了
				List<String> areaList = getAreaCode(adGis.getAreas());
				String areaCode = "";
				if(null != areaList && areaList.size() > 0){
					areaCode = areaList.get(0);
				}else{
					log.error("播出单区域码为空 , id: " + adGis.getId());
					continue;
				}
				if(!StringUtils.isNotBlank(areaCode)){
					log.error("播出单区域码为空 , id: " + adGis.getId());
					continue;
				}

				String bodyContent = "1:1";
				//标清音频处理 
				if (ConstantsAdsCode.PUSH_FREQUENCE_SD.equals(adGis.getAdSiteCode()))
				{
					List<String> serviceIdList = getListFromJson(adGis.getChannelId());
					if(serviceIdList == null || serviceIdList.size()<=0 ){
						log.error("播出单中的频道列表为空");
						break;
					}else{
						for(String serviceId : serviceIdList){
							ftpService.download(adGis.getContentPath(),serviceId+".iframe", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+ConstantsAdsCode.ADVRESOURCE_SD_PATH);
						}
					}
					//压缩资源包文件					
					 //将生成好的html文件压缩
					String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A);
					if (ConstantsAdsCode.WIN)
					{
						try{
							File inFile = new File(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath());
							ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A));
	                        zos.setComment("多文件处理");
	                        zipFile(inFile, zos, ConstantsAdsCode.ADVRESOURCE_SD_PATH);
	                        zos.close(); 
						}catch(Exception e){
							log.error(e);
						}
					}
					else
					{
						String inpath=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioSDTempPath();
						String outfile=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A;
						linuxRun("zip -r",inpath,outfile);
					
					}
                    if (!oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A)))
                    {
                    	boolean success = ocgService.sendFile(areaCode, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
                    	if(!success){
                    		continue;
                    	}
//								UiDesMap.addsdUiDesc("3", ConstantsAdsCode.ADVRESOURCE_A,areaList.get(j));
						if(!areaMap.containsKey(areaCode)){
                        	bodyContent += ";3:advResource-a.dat";
        					String sourceFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A;
							String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
							boolean isFileValidated = validateBeforePlay(areaCode, sourceFilePath, serverPath);
                        	if(!isFileValidated){
                        		continue;
                        	}
							//往区域发送NID描述符插入信息
							String ret = uiService.sendUiDesc(bodyContent, areaCode);
							if("0".equals(ret)){
								ocgService.startPlayPgm(areaCode, InitConfig.getConfigProperty(ConstantsAdsCode.UIPGM),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));	
								areaMap.put(areaCode, "");
							}else{
								log.error("往"+areaCode+"区域NID描述符插入信息失败！return="+ret);
								continue;
							}
						}
                    }
				}//高清音频处理 
				else if (ConstantsAdsCode.PUSH_FREQUENCE_HD.equals(adGis.getAdSiteCode()))
				{
					log.info("往区域"+areaCode+"发送高清音频广播广告开始");
					List<String> serviceIdList = getListFromJson(adGis.getChannelId());
					if(serviceIdList == null || serviceIdList.size()<=0 ){
						log.error("播出单中的频道列表为空");
						break;
					}else{
						for(String serviceId : serviceIdList){
							ftpService.download(adGis.getContentPath(),serviceId+".iframe", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+ConstantsAdsCode.ADVRESOURCE_HD_PATH);
						}
					}
					//压缩资源包文件					
					 //将生成好的html文件压缩
					//ZipFile zipFile = new ZipFile(ConstantsAdsCode.ADVRESOURCE_C);
					String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C);
					if (ConstantsAdsCode.WIN)
					{
						try{
							File inFile = new File(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath());
							ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C));
	                        zos.setComment("多文件处理");
	                        zipFile(inFile, zos, ConstantsAdsCode.ADVRESOURCE_HD_PATH);
	                        zos.close(); 
						}catch(Exception e){
							log.error(e);
						}
					}
					else
					{
						String inpath=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath();
						String outfile=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C;
						linuxRun("zip -r",inpath,outfile);
					}
                    if (!oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C)))
                    {
                    	boolean success = ocgService.sendFile(areaCode, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
                    	if(!success){
                    		continue;
                    	}
//								UiDesMap.addhdUiDesc("3", ConstantsAdsCode.ADVRESOURCE_C,areaList.get(j));
                    	if(!areaMap.containsKey(areaCode)){
                        	bodyContent += ";3:advResource-c.dat";
                        	String sourceFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C;
							String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
							boolean isFileValidated = validateBeforePlay(areaCode, sourceFilePath, serverPath);
							if(!isFileValidated){
								continue;
							}
							//往区域发送NID描述符插入信息
							String ret = uiService.sendUiDesc(bodyContent, areaCode);
							if("0".equals(ret)){
								ocgService.startPlayPgm(areaCode, InitConfig.getConfigProperty(ConstantsAdsCode.UIPGM),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));	
								areaMap.put(areaCode, "");
							}else{
								log.error("往"+areaCode+"区域NID描述符插入信息失败！return="+ret);
								continue;
							}
                    	}
                    }
                    log.info("往区域"+areaCode+"发送高清音频广播广告结束");
				}
					
				//更新播出单状态为已执行
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");
			}
		}
				//高清音频背景
				    //局方要求 资源包资源文件，除了音频，其他均不用广告默认素材
//				    if (1==0)
//					//if (uiService.addUiDesc(typeList, nameList))
//					{
//						//下载默认广告
//						for (int i=0;i<InitConfig.getAdsConfig().getRealTimeAds().getAdsList().size();i++)
//						{
//							if (Integer.valueOf(InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode())%2==0)
//							{
//								if (ConstantsAdsCode.PUSH_MENU_HD.equals(InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode()))
//								{
//									String filenames[] = InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes().split(ConstantsHelper.COMMA);
//									for (int j=0;j<filenames.length;j++)
//									{
//										String resourcPath = InitConfig.getAdsResourcePath();
//										String fullpath   = resourcPath+"/"+filenames[j];//"/"+
//										String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
//										resourcPath = fullpath.substring(0,fullpath.lastIndexOf("/"));
//										String adfilename = "";
//										ftpService.download(filename, "ad_"+(j+1)+".png", resourcPath, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//									}
//								}
//								else
//								{
//									ftpService.download(InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes(), InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getFilename(), InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//								}
//							}
//							else
//							{
//								if (ConstantsAdsCode.PUSH_MENU_SD.equals(InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode()))
//								{
//									String filenames[] = InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes().split(ConstantsHelper.COMMA);
//									for (int j=0;j<filenames.length;j++)
//									{
//										String resourcPath = InitConfig.getAdsResourcePath();
//										String fullpath   = resourcPath+"/"+filenames[j];//"/"+
//										String filename = fullpath.substring(fullpath.lastIndexOf("/")+1);
//										resourcPath = fullpath.substring(0,fullpath.lastIndexOf("/"));
//										String adfilename = "";
//										ftpService.download(filename, "ad_"+(j+1)+".png", resourcPath, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioSDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//									}
//								}
//								else
//								{
//									ftpService.download(InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes(), InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getFilename(), InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioSDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//								}
//							}
//						}
//						
//						//下载默认广告
//						for (int i=0;i<InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().size();i++)
//						{
//							if (ConstantsAdsCode.PUSH_STARTSTB.equals(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(i).getAdsCode().substring(0,4)))
//							{
//								continue;
//							}
//							if (Integer.valueOf(InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode())%2==0)
//							{
//								ftpService.download(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(i).getDefaultRes(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(i).getFilename(), InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//							}
//							else
//							{
//								ftpService.download(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(i).getDefaultRes(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(i).getFilename(), InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioSDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//								
//							}
//						}
//						String vodfile = InitConfig.getConfigProperty("VOD_LOADING_FILE");
//						ftpService.download(vodfile, ConstantsAdsCode.VOD_LOADING_FILE, InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//						ftpService.download(vodfile, ConstantsAdsCode.VOD_LOADING_FILE, InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioSDTempPath()+ConstantsAdsCode.ADVRESOURCE_PATH);
//					}					

//					if (audioHDflag==true)
//					{
//						List<String> typeList=new ArrayList();
//						List<String> nameList=new ArrayList();
//						//压缩资源包文件					
//						 //将生成好的html文件压缩
//						//ZipFile zipFile = new ZipFile(ConstantsAdsCode.ADVRESOURCE_C);
//						String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C);
//						if (ConstantsAdsCode.WIN)
//						{
//							File inFile = new File(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath());
//							ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C));
//	                        zos.setComment("多文件处理");
//	                        zipFile(inFile, zos, ConstantsAdsCode.ADVRESOURCE_PATH);
//	                        zos.close(); 
//						}
//						else
//						{
//							String inpath=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath();
//							String outfile=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C;
//							linuxRun("zip -r",inpath,outfile);
//						}
//                        if (!oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C)))
//                        {
//                        	 // TODO 拉起NIT，OCG投放
//                        	ocgService.sendFile("0", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_C, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
//							//ocgService.startPlay("0",InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
//							ocgService.startPlayPgm(InitConfig.getConfigProperty(ConstantsAdsCode.UIPGM),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));
//							
//							//写ui描述符
//							//typeList.add("3");
//							//nameList.add(ConstantsAdsCode.ADVRESOURCE_C);
//							
//							UiDesMap.addhdUiDesc("3", ConstantsAdsCode.ADVRESOURCE_C);
//							if (uiService.addUiDesc(UiDesMap.gethdUiDesc().getTypeList(), UiDesMap.gethdUiDesc().getNameList(),UiDesMap.getDefaultstartflag()))
//							{
//								adsids=adsids.substring(0,adsids.length()-1);
//								pushAdsDao.updateAdsFlag(adsids, "1");
//							}
//                        }
//					}
//					if (audioSDflag==true)
//					{
//						List<String> typeList=new ArrayList();
//						List<String> nameList=new ArrayList();
//						//压缩资源包文件					
//						 //将生成好的html文件压缩
//						String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A);
//						if (ConstantsAdsCode.WIN)
//						{
//							File inFile = new File(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioHDTempPath());
//							ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A));
//	                        zos.setComment("多文件处理");
//	                        zipFile(inFile, zos, ConstantsAdsCode.ADVRESOURCE_PATH);
//	                        zos.close(); 
//						}
//						else
//						{
//							//linuxRun("zip -r "+InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A+" "+InitConfig.getAdsConfig().getUnRealTimeAds().getAdsIframeSDTempPath());
//							String inpath=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsaudioSDTempPath();
//							String outfile=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A;
//							linuxRun("zip -r",inpath,outfile);
//						
//						}
//                        if (!oldmd5.equals(FileDigestUtil.getFileNameMD5(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A)))
//                        {
//                        	 // TODO 拉起NIT，OCG投放
//                        	ocgService.sendFile("0", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.ADVRESOURCE_A, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
//							//ocgService.startPlay("0",InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
//							ocgService.startPlayPgm(InitConfig.getConfigProperty(ConstantsAdsCode.UIPGM),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));
//							
//							//写ui描述符
//							//typeList.add("3");
//							//nameList.add(ConstantsAdsCode.ADVRESOURCE_A);
//							
//							UiDesMap.addsdUiDesc("3", ConstantsAdsCode.ADVRESOURCE_A);
//							if (uiService.addUiDesc(UiDesMap.getsdUiDesc().getTypeList(), UiDesMap.getsdUiDesc().getNameList(),UiDesMap.getDefaultstartflag()))
//							{
//								adsids=adsids.substring(0,adsids.length()-1);
//								pushAdsDao.updateAdsFlag(adsids, "1");
//							}
//                        }
//					}

	}
	
	
	//---------------------------开机热点推荐位广告-------------------------------------------
	public void sendHotRecommendAds(Date startTime,String adsTypeCode) {
		//停止播出单，修改播出单的状态
		List<AdPlaylistGis> adsList = pushAdsDao.queryEndAds(startTime, adsTypeCode);
		if (adsList!=null && adsList.size()>0)
		{
			log.info("修改停止播出单的状态");
			for (AdPlaylistGis adGis : adsList)
			{
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "4");
			}
		}
		
		//启动播出单

		adsList = pushAdsDao.queryStartAds( startTime, adsTypeCode);
		//循环处理播出单
		if (adsList!=null && adsList.size()>0)
		{
			try {
				ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
			} catch (Exception e) {
				log.error("FTP服务器无法连接",e);
				//warnHelper.writeWarnMsgToDb("FTP服务器无法连接   IP:" + InitConfig.getAdsConfig().getAdResource().getIp());
				return;
			}
			for (AdPlaylistGis adGis : adsList){
				
				//获取区域列表,  只会获得一个区域，为0（所有、空）的情况在绑定素材的时候就拆分了
				List<String> areaList = getAreaCode(adGis.getAreas());
				String area = "";
				if(null != areaList && areaList.size() > 0){
					area = areaList.get(0);
				}else{
					log.error("播出单区域码为空 , id: " + adGis.getId());
					continue;
				}
				if(!StringUtils.isNotBlank(area)){
					log.error("播出单区域码为空 , id: " + adGis.getId());
					continue;
				}
		
				log.info("往区域"+area+"发送推荐页面广告开始");
				//开机推荐位广告处理
				String localFileName = adGis.getContentPath().substring(adGis.getContentPath().lastIndexOf("/")+1, adGis.getContentPath().length());
				//下载素材到本地
				ftpService.download(adGis.getContentPath(),localFileName, InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath());
//						String filename = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+localFileName;
//						String zipfilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath();
//						//将文件解压
//						unZip(filename, zipfilePath);
				
				
				String inpath=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+localFileName;
				String outfile=InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath();
				log.info("inpath=="+inpath);
				log.info("outfile=="+outfile);
				linuxUnZip("unzip -o -d",inpath,outfile);

				//将文件夹下的三个文件发送到OCG
				String dirName = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/"+ConstantsAdsCode.HOT_RECOMMEND_DIR;
				String jsName = dirName+"/"+ConstantsAdsCode.HOT_RECOMMEND_JS;
				String htmlName = dirName+"/"+ConstantsAdsCode.HOT_RECOMMEND_HTML;
				String imageName = dirName+"/"+ConstantsAdsCode.HOT_RECOMMEND_INAGE;
				boolean success1 = ocgService.sendFile(area,jsName, InitConfig.getAdsConfig().getUnRealTimeAds().getRecommendTargetPath());
				boolean success2 = ocgService.sendFile(area,htmlName , InitConfig.getAdsConfig().getUnRealTimeAds().getRecommendTargetPath());
				boolean success3 = ocgService.sendFile(area,imageName, InitConfig.getAdsConfig().getUnRealTimeAds().getRecommendTargetPath());
				if(!success1 || !success2 || !success3){
					continue;
				}
				
				log.info("往区域"+area+"发送推荐页面广告成功");

				String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getRecommendTargetPath();
				boolean isFile1Validated = validateBeforePlay(area, jsName, serverPath);
				boolean isFile2Validated = validateBeforePlay(area, htmlName, serverPath);
				boolean isFile3Validated = validateBeforePlay(area, imageName, serverPath);
				if(isFile1Validated && isFile2Validated && isFile3Validated){
					ocgService.startPlayPgm(area, InitConfig.getConfigProperty(ConstantsAdsCode.RECOMMEND),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));
				}else{
					continue;
				}
				pushAdsDao.updateAdsFlag(adGis.getId().longValue(), "1");
			}
		}
	
	}
	
	
	//-----------------------------------------------------------------
	
	
	
	
	
	
	/**
	 * 解析字幕播出单文件的属性.
	 *
	 * @param filename 配置文件名
	 * @return 返回字幕对象
	 */
	public AdsSubtitle readMessageFile(String filename)
	{
		File file;
		try{
			InputStream is= new FileInputStream (new File (InitConfig.getAdsResourcePath()+"/"+filename));
			Properties prop = new Properties();
	 	    prop.load(is);
	 	    is.close();
	 	    Enumeration<?> en = prop.propertyNames();
	 	    String key = "";
	 	    String value = "";
	 	    HashMap<String,String> messageMap =new HashMap<String,String>();
	 	    while(en.hasMoreElements()){
	 		  key = (String)en.nextElement();
	 		  value = new String(prop.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
	 		  messageMap.put(key, value);
	 	    }

	 	   AdsSubtitle  adsSubtitle = new AdsSubtitle();
	 	   adsSubtitle.setAction(messageMap.get("action"));
	 	   adsSubtitle.setContent(messageMap.get("content"));
	 	  adsSubtitle.setDurationTime(Integer.parseInt(messageMap.get("durationTime")));
	 	 adsSubtitle.setFontColor(messageMap.get("fontColor"));
	 	 adsSubtitle.setFontSize(Integer.parseInt(messageMap.get("fontSize")));
	 	 adsSubtitle.setRegionAbove(Integer.parseInt(messageMap.get("regionAbove")));
	 	 adsSubtitle.setRegionLeft(Integer.parseInt(messageMap.get("regionLeft")));
	 	adsSubtitle.setRegionHeight(Integer.parseInt(messageMap.get("regionHeight")));
	 	 adsSubtitle.setRegionWidth(Integer.parseInt(messageMap.get("regionWidth")));
	 	 adsSubtitle.setRegionColor(messageMap.get("regionColor"));	 	 
	 	 adsSubtitle.setSpeed(Integer.parseInt(messageMap.get("speed")));
	 	adsSubtitle.setTvn(Integer.parseInt(messageMap.get("tvn")));
	 	
	 	 return adsSubtitle;
		}catch(IOException ie){
			ie.printStackTrace();
		}
		return null;
	}
	
	
	public List<AdPlaylistGis> queryNewPlayList(Date startTime, List<Ads> adsList) {
		
		return pushAdsDao.queryNewPlayList(startTime, adsList);
	}
	
	

	public List<AdPlaylistGis> queryNewPlayList(Date startTime, String adsCode) {
		
		return pushAdsDao.queryStartAds(startTime, adsCode);
	}

	/**
	 * 解析链接播出单文件的属性.
	 *
	 * @param filename 配置文件名
	 * @return 返回字幕对象
	 */
	public AdsLink readLinkFile(String filename)
	{
		File file;
		try{
			InputStream is= new FileInputStream (new File (InitConfig.getAdsResourcePath()+"/"+filename));
			Properties prop = new Properties();
	 	    prop.load(is);
	 	    is.close();
	 	    Enumeration<?> en = prop.propertyNames();
	 	    String key = "";
	 	    String value = "";
	 	    HashMap<String,String> messageMap =new HashMap<String,String>();
	 	    while(en.hasMoreElements()){
	 		  key = (String)en.nextElement();
	 		  value = new String(prop.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
	 		  messageMap.put(key, value);
	 	    }

	 	   AdsLink  adsLink = new AdsLink();
	 	   adsLink.setUrl(messageMap.get("action"));
	 	   adsLink.setTvn(Integer.parseInt(messageMap.get("tvn")));
	 	   return adsLink;
		}catch(IOException ie){
			ie.printStackTrace();
		}
		return null;
	}
	/**
	 * 解析轮询广告位的播出单文件的属性.
	 *
	 * @param filename  配置文件名
	 * @return 轮询图片名列表
	 */
	public String readLoopFile(String filename)
	{
		File file;
		String retFiles="";
		try{
			InputStream is= new FileInputStream (new File (InitConfig.getAdsResourcePath()+"/"+filename));
			Properties prop = new Properties();
	 	    prop.load(is);
	 	    is.close();
	 	    Enumeration<?> en = prop.propertyNames();
	 	    String key = "";
	 	    String value = "";
	 	    HashMap<String,String> messageMap =new HashMap<String,String>();
	 	    while(en.hasMoreElements()){
	 		  key = (String)en.nextElement();
	 		  value = new String(prop.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
	 		  messageMap.put(key, value);
	 	    }
	 	   String loopfilename = messageMap.get("pic1");
	 	   if (loopfilename!=null && !loopfilename.equals(""))
	 	   {
	 		  retFiles=retFiles+loopfilename;
	 	   }
	 	  loopfilename = messageMap.get("pic2");
	 	   if (loopfilename!=null && !loopfilename.equals(""))
	 	   {
	 		  retFiles=retFiles+","+loopfilename;
	 	   }
	 	  loopfilename = messageMap.get("pic3");
	 	   if (loopfilename!=null && !loopfilename.equals(""))
	 	   {
	 		  retFiles=retFiles+","+loopfilename;
	 	   }
	 	  loopfilename = messageMap.get("pic4");
	 	   if (loopfilename!=null && !loopfilename.equals(""))
	 	   {
	 		  retFiles=retFiles+","+loopfilename;
	 	   }
	 	  loopfilename = messageMap.get("pic5");
	 	   if (loopfilename!=null && !loopfilename.equals(""))
	 	   {
	 		  retFiles=retFiles+","+loopfilename;
	 	   }
	 	  loopfilename = messageMap.get("pic6");
	 	   if (loopfilename!=null && !loopfilename.equals(""))
	 	   {
	 		  retFiles=retFiles+","+loopfilename;
	 	   }

	 	 
		}catch(IOException ie){
			ie.printStackTrace();
		}
		return retFiles;
	}
	
	/**
	 * Read start file.
	 *
	 * @param filename  配置文件名
	 * @return 开机文件序列  Key=TS,IFAME  value=文件名
	 * 
	 */
	public Map<String,String> readStartFile(String filename)
	
	{
		File file;
		Map<String,String> retMap = new HashMap<String,String>();
		try{
			InputStream is= new FileInputStream (new File (InitConfig.getAdsResourcePath()+"/"+filename));
			Properties prop = new Properties();
	 	    prop.load(is);
	 	    is.close();
	 	    Enumeration<?> en = prop.propertyNames();
	 	    String key = "";
	 	    String value = "";
	 	    HashMap<String,String> messageMap =new HashMap<String,String>();
	 	    while(en.hasMoreElements()){
	 		  key = (String)en.nextElement();
	 		  value = new String(prop.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
	 		  messageMap.put(key, value);
	 	    }
	 	   String startfilename = messageMap.get(ConstantsHelper.START_CONTENT_VIDEO_TYPE);
	 	   if (startfilename!=null && !startfilename.equals(""))
	 	   {
	 		  retMap.put(ConstantsHelper.START_CONTENT_VIDEO_TYPE, startfilename);
	 	   }
	 	  startfilename = messageMap.get(ConstantsHelper.START_CONTENT_PIC_TYPE);
	 	 if (startfilename!=null && !startfilename.equals(""))
	 	   {
	 		  retMap.put(ConstantsHelper.START_CONTENT_PIC_TYPE, startfilename);
	 	   }
	 	 
		}catch(IOException ie){
			ie.printStackTrace();
		}
		return retMap;
	}
	/**
	 * 解析区域，用户行业，用户级别属性 结构为 001#002#003
	 * @param data 输入数据
	 * @param split 间隔符
	 * @return 返回数据列表
	 */
	public List<String> getElementFromData(String data,String split)
	{
		String [] temp;
		List<String> retList =new ArrayList<String>() ;
		if (data==null)
		{
			return retList;
		}
		temp= data.split(split);
		
		for (int i=0;i<temp.length;i++)
		{
			if (temp[i]!=null && !temp[i].equals(""))
			{
				retList.add(temp[i]);
			}
		}
		return retList;
	}
	
	/**
	 * 解析频道信息 (注释有误)
	 * @param data 输入数据  频道的集合结构为 ["001,002","005,007","007,005,002,006"]，使用json格式对其拆分
	 *		                                        且区域和频道的集合的长度必须相等
	 * @param split 间隔符
	 * @return 返回数据列表
	 */
	public List<List<String>> getElementFromData(String data)
	{
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(data);
		List<String> tempList = JSONArray.toList(jsonArray);
		List<List<String>> retList = new ArrayList<List<String>>();
		for (int i=0;i<tempList.size();i++)
		{
			retList.add(getElementFromData(tempList.get(i),ConstantsHelper.CHANNEL_SPLIT__SIGN));
		}
		return retList;
	}
	
	/**
	 * 获取频道列表
	 * @param data ["933,901,902,903,900"]
	 * @return
	 */
	public List<String> getListFromJson(String data)
	{
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(data);
		List<String> tempList = JSONArray.toList(jsonArray);
		if(tempList != null && tempList.size() >=0){
			return getElementFromData(tempList.get(0),ConstantsHelper.CHANNEL_SPLIT__SIGN);
		}else{
			return null;
		}
	}
	
	/**
	 * 写广告配置文件.
	 *
	 * @param adTypeCode 广告位编码
	 * @param chaID 特征值
	 * @param filename 文件 如为轮询文件 “,”分隔
	 * @return 1 成功 0失败
	 */
	public boolean WriteAdConfig(String areaCode,String channelCode,String adsTypeCode,String chaID,String filename)
	{
		File file = new File("InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename");
		//写广告位配置缓存
		//复制文件至临时目录
		//记录发送文件缓存列表 播出单IDS
		
		//file
		//var areaCode0=[
		//{serviceId:100,imgSrc:isHD?"miniepg_ad_3_HD.png":"miniepg_ad_7.png"},
		//{serviceId:101,imgSrc:isHD?"miniepg_ad_3_HD.png":"miniepg_ad_7.png"},
		//{serviceId:102,imgSrc:isHD?"miniepg_ad_3_HD.png":"miniepg_ad_7.png"},
		//{serviceId:103,imgSrc:isHD?"miniepg_ad_3_HD.png":"miniepg_ad_7.png"},
		//{serviceId:104,imgSrc:isHD?"miniepg_ad_3_HD.png":"miniepg_ad_7.png"}
		//];		
		try
		{		
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename")));
			//OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename,false));
			BufferedWriter osw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream("InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename",false)));//Files.newBufferedWriter(InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename, "UTF-8",false);
			String readoneline;
			int l;
			boolean flag=false;	
			boolean first=true;	
			while((l = br.read()) != -1){
				readoneline = br.readLine();
				if (first && readoneline.indexOf("serviceId:")>0)
				{
					first=false;
				}
				
				//判断是否为对应频道的广告配置
				if (readoneline.indexOf("serviceId:"+channelCode)>0)
				{
					//更新广告配置　标清　更新位置
					if ("HD".equals(chaID))
					{
						readoneline= readoneline.substring(0,readoneline.lastIndexOf("?")+1) + filename + readoneline.substring(readoneline.lastIndexOf(':')-1) ;
						
					}
					else
					{
						readoneline= readoneline.substring(0,readoneline.lastIndexOf(':')) + '"' + filename + '"' +"},";
					}
					//现有文件中有对应频道的广告配置标识
					flag=true;
				}
				if (readoneline.indexOf("]")>0)
				{
					if (flag==false)
					{
						if ("HD".equals(chaID))
						{
							readoneline= "{serviceId:"+channelCode+",imgSrc:isHD?"+'"'+filename+'"'+"//:"+'"'+'"'+ "}";
						}
						else
						{
							readoneline= "{serviceId:"+channelCode+",imgSrc:isHD?"+'"'+'"'+"//:"+'"'+filename+'"'+"}";
						}
						if (first==false)
						{
							readoneline=","+readoneline;
						}
						osw.write(readoneline);
						osw.newLine();
					}
				}
				osw.write(readoneline,0,readoneline.length());
				osw.newLine();
				log.info(readoneline);
			}
			osw.flush();
			br.close();
			osw.close();
		}
		catch(IOException e){
		   e.printStackTrace();
		}
		return true;
	}
	/**
	 * 合成广告配置文件.	
	 * 读取各广告位的配置文件，生成总的配置文件，并放到临时目录下
	 * @return 1 成功 0失败
	 */
	public boolean ComposeAdConfig()
	{
		//现场文件格式未确定，暂不实现
		return true;
	}
	 public boolean copyFile(String oldPath, String newPath)
	 {  
		 try {
			 int bytesum = 0;
             int byteread = 0; 
             File oldfile = new File(oldPath);
             if (oldfile.exists()) { 
        	   //文件存在时                
        	   InputStream inStream = new FileInputStream(oldPath);
        	   //读入原文件                
        	   FileOutputStream fs = new FileOutputStream(newPath);
        	   byte[] buffer = new byte[1444];                
        	   int length;                
        	   while ( (byteread = inStream.read(buffer)) != -1) 
        	   { 
        		   bytesum += byteread; //字节数 文件大小 
        		    fs.write(buffer, 0, byteread); 
        		}
        	   inStream.close();
        	   } 
         }        
		 catch (Exception e)
		 {           
			 log.error("复制单个文件操作出错"+e); 
			 e.printStackTrace(); 
			 return false;
		 }
	     return true;
	 }
	  public boolean copyFolder(String oldPath, String newPath)
	  {        
		  try 
		  {
			  (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
			  File a=new File(oldPath);
			  String[] file=a.list();
			  File temp=null;
			  for (int i = 0; i < file.length; i++) 
			  {
				  
				  if(oldPath.endsWith("/"))
				  {
					  temp=new File(oldPath+file[i]);
				  }
				  else
				  { 
					  temp=new File(oldPath+"/"+file[i]);
				  }
				  if(temp.isFile())
				  {   
					  //如果是广告配置文件，不复制
					  if (temp.getName().toString().endsWith(".js"))
					  {
						  continue;
					  }
					  FileInputStream input = new FileInputStream(temp);
					  FileOutputStream output = new FileOutputStream(newPath + "/" +(temp.getName()).toString());
					  byte[] b = new byte[1024 * 5];
					  int len;
					  while ( (len = input.read(b)) != -1) 
					  { 
						  output.write(b, 0, len);
					  }
					  output.flush();
					  output.close();
					  input.close();
				  }
				  if(temp.isDirectory())
				  {
					  //如果是子文件夹,不复制 
					  //copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
				  } 
			  }
		  }
		  catch (Exception e)
		  {   
			e.printStackTrace();
			return false;
		  }
		  return true;
	  }
	  public void delFile(String filePathAndName)
	  {
		  try
		  {
		  String filePath = filePathAndName;
		  filePath = filePath.toString();
		  File myDelFile = new File(filePath);
		  myDelFile.delete();
		  log.info("删除文件操作 成功执行");
		  }
		  catch (Exception e)
		  {
			  log.error("删除文件操作出错");
		  e.printStackTrace();
		  }
	  }
	
	   /** 
	    * 删除某个文件夹下的所有文件夹和文件 
	    * 
	    * @param delpath 
	    *            String 
	    * @throws FileNotFoundException 
	    * @throws IOException 
	    * @return boolean 
	    */  
	   public boolean deleteAllfile(String delpath)  {  
	    try {  
	    log.info("clean meterial path: " +delpath);
	     File file = new File(delpath);  
	     // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
	     if (!file.isDirectory()) {  
	        file.delete();  
	     } else if (file.isDirectory()) {  
	      String[] filelist = file.list();  
	      for (int i = 0; i < filelist.length; i++) {  
	       File delfile = new File(delpath + "/"+ filelist[i]);  
	       if (!delfile.isDirectory()) {  
	    	   if (filelist[i].indexOf(".js")==filelist[i].length()-3)
		          {
		        	  continue;//
		          }
	    	   delfile.delete();  
	        
	       } else if (delfile.isDirectory()) {  
	        deleteAllfile(delpath + "\\" + filelist[i]);  
	       }  
	      }  
	     
	      //file.delete();  
	     }  
	    
	    } 	    
	    catch (Exception e)
	    {
	    	log.error("deletpath" +delpath);
	    }
	    return true;  
	   } 
	   public static void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {
	        if (inFile.isDirectory()) {
	            File[] files = inFile.listFiles();
	            for (File file:files)
	                zipFile(file, zos, dir);
	        } else {
	            String entryName = null;
	            if (!"".equals(dir))

	                entryName = dir + "\\" + inFile.getName();
	            else
	                entryName = inFile.getName();
	            ZipEntry entry = new ZipEntry(entryName);
	            zos.putNextEntry(entry);
	            InputStream is = new FileInputStream(inFile);
	            int len = 0;
	            while ((len = is.read()) != -1)
	                zos.write(len);
	            is.close();
	        }

	    }
	   
	   public static void unZip(String fileName,String zipfilePath){
		 //将ZIP文件解压
           //String fileName = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+zipbackgroundImageFileName;
           //String zipfilePath = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/";

		try {
			ZipFile zipFile = new ZipFile(fileName);
			Enumeration emu = zipFile.entries();
	           while(emu.hasMoreElements()){
	           ZipEntry entry = (ZipEntry)emu.nextElement();
	           //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
	           if (entry.isDirectory()){
	             new File(zipfilePath + entry.getName()).mkdirs();
	             continue;
	           }
	           
	           BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
	            
	           File file = new File(zipfilePath + entry.getName());
	           //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
	           //而这个文件所在的目录还没有出现过，所以要建出目录来。
	           File parent = file.getParentFile();
	           if(parent != null && (!parent.exists())){
	               parent.mkdirs();
	           }

	           FileOutputStream fos = new FileOutputStream(file);
	           BufferedOutputStream bos = new BufferedOutputStream(fos,2048);

	           int count;
	           byte data[] = new byte[2048];

	           while ((count = bis.read(data, 0, 2048)) != -1){
	           bos.write(data, 0, count);
	           }
	           bos.flush();
	           bos.close();
	           bis.close();
	           }
	           zipFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
	   }
	   
	   public  void linuxRun(String cmd,String inpath,String outfile)
		{
			try {
				String dir = inpath.substring(0,inpath.lastIndexOf("/"));
				String zippath = inpath.substring(inpath.lastIndexOf("/")+1);
				File workdir =  new File(dir);
				//Process pro1= Runtime.getRuntime().exec("cd "+dir);
				//pro1.waitFor();
				Process pro= Runtime.getRuntime().exec(cmd +" " + outfile+ " "+zippath,null,workdir);
				pro.waitFor();
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	   
	   public  void linuxUnZip(String cmd,String inpath,String outfile){
			try {
				Process pro= Runtime.getRuntime().exec(cmd +" " + outfile+ " "+inpath);
				pro.waitFor();
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	   
	   /**
	    * 获取所有区域编码列表
	    * @return
	    */
	   public List<String> getAreaCode(String areaCode){
		   List<String> areaList = new ArrayList<String>();
		   if(StringUtils.isEmpty(areaCode) || "0".equals(areaCode) || "152000000000".equals(areaCode)){
			   //获取所有区域编码列表
			   //List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
			   //modify  xml-config  to DATABASE  DAO
			   List<OcgInfo> ocgList = ocgInfoDao.getOcgInfoList();
			   for(OcgInfo ocg : ocgList){
				   if(!areaList.contains(ocg.getAreaCode()) && !"0".equals(ocg.getAreaCode())){
					   areaList.add(ocg.getAreaCode());
				   }
			   }
		   }else{
			   areaList = getElementFromData(areaCode,ConstantsHelper.SPLIT__SIGN);
		   }
		   return areaList;
	   }

	   private String getParentPath(String path){
		   if(path.endsWith("/")){
			   path = path.substring(0, path.length() - 1);
		   }
		   return path.substring(0, path.lastIndexOf("/"));
	   }
	   
	   private String getDeepestDir(String path){
		   String[] dirs = path.split("/");
			int length = dirs.length;		
			if(length > 0){
				return dirs[length -1];
			}	   
		   return path;
	   }
	   
	   private void deleteFile(String delDir, String fileName){
		   File f = new File(delDir + "/" + fileName);
		   if(f.exists()){
			   log.info("删除文件 " + f.getAbsolutePath());
			   f.delete();
		   }
	   }

	   
	   
	   public static void main(String[] args) {
		   Map<String, List<String>> tsIdMap = new HashMap<String, List<String>>();
		   List<String> list = new ArrayList<String>();
		   list.add("aaa");
		   list.add("bbb");
		   tsIdMap.put("first", list);
		  
		   Map<String, List<String>> tsIdMap1 = new HashMap<String, List<String>>();
		   List<String> list1 = new ArrayList<String>();
		   list.add("ccc");
		   list.add("ddd");
		   tsIdMap.put("second", list1);
		   tsIdMap.putAll(tsIdMap1);
		   System.out.println(tsIdMap.size());
	}
	   
	   
	   
	   
	   
	   

}
