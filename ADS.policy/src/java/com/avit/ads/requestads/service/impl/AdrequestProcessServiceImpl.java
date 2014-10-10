package com.avit.ads.requestads.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.syndata.SynBsmpData;

import com.avit.ads.requestads.bean.AdPlaylistReq;
import com.avit.ads.requestads.bean.AdPlaylistReqContent;
import com.avit.ads.requestads.bean.AdPlaylistReqPContent;
import com.avit.ads.requestads.bean.PlaylistCacheModel;
import com.avit.ads.requestads.bean.PrecisionCacheModel;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.bean.request.AdInsertRequestXmlBean;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.bean.request.AdStatusReportReqXmlBean;
import com.avit.ads.requestads.bean.response.AdContent;
import com.avit.ads.requestads.bean.response.AdInsertResponseBean;
import com.avit.ads.requestads.bean.response.InsertedAd;
import com.avit.ads.requestads.bean.response.InsertedAdList;
import com.avit.ads.requestads.cache.AreaPrecisionCache;
import com.avit.ads.requestads.cache.AssetCache;
import com.avit.ads.requestads.cache.AssetCategoryPrecisionCache;
import com.avit.ads.requestads.cache.AssetKeyPrecisionCache;
import com.avit.ads.requestads.cache.AssetNamePrecisionCache;
import com.avit.ads.requestads.cache.IndustryPrecisionCache;
import com.avit.ads.requestads.cache.LevelPrecisionCache;
import com.avit.ads.requestads.cache.LocationCache;
import com.avit.ads.requestads.cache.LocationComparator;
import com.avit.ads.requestads.cache.NpvrCache;
import com.avit.ads.requestads.cache.NpvrPrecisionCache;
import com.avit.ads.requestads.cache.OrderCache;
import com.avit.ads.requestads.cache.PresisionComparator;
import com.avit.ads.requestads.cache.ProductCache;
import com.avit.ads.requestads.cache.ProductPrecisionCache;
import com.avit.ads.requestads.cache.ReqHistoryCache;
import com.avit.ads.requestads.cache.ReqPlayListCache;
import com.avit.ads.requestads.cache.UserCache;
import com.avit.ads.requestads.cache.UserNoAdCache;
import com.avit.ads.requestads.cache.bean.ContentCacheBean;
import com.avit.ads.requestads.cache.bean.LocationCacheBean;
import com.avit.ads.requestads.cache.bean.TAssetV;
import com.avit.ads.requestads.cache.bean.TBsmpUser;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.dao.impl.ADSurveyDAOImp;
import com.avit.ads.requestads.service.AdrequestProcessService;
import com.avit.ads.requestads.service.DefaultResourceCache;
import com.avit.ads.requestads.service.MediaInfoCache;
import com.avit.ads.requestads.service.MySaveReportThread;
import com.avit.ads.requestads.service.PlaylistCache;
import com.avit.ads.requestads.service.RefreshCacheService;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.InitConfig;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.common.util.StringUtil;
@Service("AdrequestProcessService")
public class AdrequestProcessServiceImpl implements AdrequestProcessService {
//	@Inject
//	ADSurveyDAO aDSurveyDAO;
	private static Logger logger = LoggerFactory.getLogger(AdrequestProcessServiceImpl.class);

	public String GenerateADPlayList(String xml) {
		// TODO Auto-generated method stub
		String responseXml = "";
		AdContent adContent = null;
		//缺少禁播，喜好，问卷
		try {
			JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
			AdInsertRequestXmlBean bean = (AdInsertRequestXmlBean) helper.fromXML(xml);
			
			// 判断用户是否允许播放广告
			String tvnId = bean.getTvnId();
			logger.info("请求 assetid:"+bean.getAssetId());
			logger.info("请求productid:"+bean.getProductId());
			logger.info("请求 tvn:"+bean.getTvnId());
			TBsmpUser user=UserCache.getUser(tvnId);
			if (user==null)
			{
				ADSurveyDAOImp adsurveyDAOImp = (ADSurveyDAOImp)ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
				user = adsurveyDAOImp.getUserByTVN(tvnId);
				if (user!=null)
				{
					LocationCacheBean cacheBean = LocationCache.getChiledLocation(user.getLocationcodevalue());
					if (cacheBean!=null)
					{
						user.setArea1(cacheBean.getLocationcode1());
						user.setArea2(cacheBean.getLocationcode2());
						user.setArea3(cacheBean.getLocationcode3());
						user.setArea4(cacheBean.getLocationcode4());
					}
					UserCache.addMap(user);
				}
				if (user==null)
				{
	//				user = new TBsmpUser();
	//				user.setUsersn(tvnId);
	//				user.setArea1(0L);
	//				user.setArea2(0L);
	//				user.setArea3(0L);
	//				user.setArea4(0L);
	//				user.setIndustrycategory("0");
	//				user.setUserlevel("0");
	//				user.setLocationcodevalue(0L);
					AdInsertResponseBean rbean = new AdInsertResponseBean();
					rbean.setSurveyQueryRespCode(0);				
					responseXml = helper.toXML(rbean);
					logger.error("no tvn"+tvnId);
					return responseXml;
				}
				
			}
			//logger.info("tvn is:"+tvnId);
			
			// 如果用户进行过问卷调查，则会有用户喜爱的广告类型，则该类型为最优先的广告
			// 先获取用户的喜好，然后再进行匹配
			boolean flag = false;//userBeSurvey(tvnId);
			
			// 从cache中获取数据
			adContent = getDataFromCache(bean, flag,user);
			if (adContent!=null && adContent.getInsertedAdList()!=null && adContent.getInsertedAdList().getLstInsertedAd()!=null && (adContent.getInsertedAdList().getLstInsertedAd().get(0).getAdUrl()==null || adContent.getInsertedAdList().getLstInsertedAd().get(0).getAdUrl().equals("")))
			{
				adContent = new AdContent();
			}
			if(adContent == null ){
				//  如果没有匹配到广告，这创建一个对象准备填充默认素材
				adContent = new AdContent();
			}
			
			//如果有素材过期，导致返回没有数据的时候需要填充默认素材
			//adContent = fillDefaultResource(adContent);
//			// 当从cache中找不到数据时，从数据库中获取播出单（几率非常小，方法暂时放在此处）
//			if(lstResponse == null || lstResponse.size() == 0){
//				lstResponse = getDataFromDatabase(bean);
//			}
			
			// 将加工得到的播出单转换成XML返回, 
			responseXml = ProcessPlayListToXml(adContent);

			// 如果调查问卷返回的结果不为空，则保存调查问卷
//			List<UserSurveyResult> answer = bean.getUserSurveyAnswerList().getUserSurveyResult();
//			if(answer != null && answer.size() > 0){
//				SaveSurveyReport(answer);                                                                                    
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responseXml="";
			AdInsertResponseBean rbean = new AdInsertResponseBean();
			rbean.setSurveyQueryRespCode(0);				
			responseXml = JaxbXmlObjectConvertor.getInstance().toXML(rbean);			
		}
		finally
		{
			//responseXml="";
			//return "";
		}
		return responseXml;
	}
	public  AdContent getDataFromCache(AdInsertRequestXmlBean bean , boolean flag,TBsmpUser user)
	{
		String tvn = bean.getTvnId();
		//SynBsmpData bsmp = new SynBsmpData();
		//级别
		String userSort = user.getUserlevel();
		//区域
		Long locationCode = user.getLocationcodevalue();
		Long locationCode1 = user.getArea1();
		Long locationCode2 = user.getArea2();
		Long locationCode3 = user.getArea3();
		Long locationCode4 = user.getArea4();
		String locationCity = user.getLocationCity();
		String userTrade = user.getIndustrycategory();
		
		String deviceType = bean.getDefinition();
		String asset = bean.getAssetId();
		List<PlaylistCacheModel> lstCache = new ArrayList<PlaylistCacheModel>();
		
		String[] typeAds1=getAdType(bean, deviceType,flag, asset);
		//引用类型的变量，新定义一个，以免修改该对象之后，之前的对象也改变了
		String[] typeAds = new String[typeAds1.length];
		for(int i=0;i<typeAds.length;i++){
			typeAds[i]=typeAds1[i];
		}
		if (typeAds==null)
		{
			return null;
		}
		if (typeAds.length<=0)
		{
			return null;
		}
		//禁播处理
		for (int positionIndex=0;positionIndex<typeAds.length;positionIndex++)
		{
			if (UserNoAdCache.isPermitAd(tvn, typeAds[positionIndex])==false)
			{
				typeAds[positionIndex]="0";
			}
			logger.info("PositionCode:"+typeAds[positionIndex]);
		}
		// 获取所对应广告位的缓存数据  目前不分区域 故取固定区域0 : typeads
		List<String> playIdList = getPlayListByAreaandPostioncode("0",typeAds);
		//Map<String, String> typeMap = getMatchCacheWithAdvertPosition(bean, flag, deviceType, asset, lstCache);
		
		// 获取当前时间的秒数，用于确认当前应该播放的播出单
		long time = System.currentTimeMillis()/1000; 
		
		// 用于临时存储过滤好的播出单playlist的数组，返回的该对象如果没有精准属性命中的话，会转换成gateway需要的XML并返回
		AdContent adContent = new AdContent();
		adContent.setTvnId(tvn);
		adContent.setToken(bean.getToken());
		
		// 用于返回给gateway，发送给浏览器进行请求问卷参数填充
		adContent.setLocationCode(locationCode+"");
		adContent.setUserSort(userSort);
		adContent.setUserTrade(userTrade);
		
		// 保存投放记录的数据结构
		List<TempObjectStorgePlaylistContent> lsttmp = new ArrayList<TempObjectStorgePlaylistContent>();
		PlaylistCacheModel playlistCacheModel=null;
		Integer preorderid=0;
		// 遍历cache,使用过滤条件去过滤
		for (String playId : playIdList) {
			playlistCacheModel  = ReqPlayListCache.getPlayModel(playId);
			if (playlistCacheModel==null)
			{
				continue;
			}
			logger.info("ad_playlist_req.id="+playlistCacheModel.getId()+";orderid="+playlistCacheModel.getOrderId());
			//根据产品 assetid 过滤 回看， 回放 ，点播			
			long begin = playlistCacheModel.getBegin();
			long end = playlistCacheModel.getEnd();
			long id = playlistCacheModel.getId();
			String code = playlistCacheModel.getAdSiteCode();
			
			//校验投放次数
			boolean putinNumber = OrderCache.getTimeEnough(playlistCacheModel.getOrderId());
			
			if (preorderid==playlistCacheModel.getOrderId())
			{
				putinNumber=true;
			}
			logger.info("ad_playlist_req.id="+playlistCacheModel.getId()+";orderid="+playlistCacheModel.getOrderId()+";putinNumber="+putinNumber);
			
			//matchPutInTimesCondition(playlistCacheModel.getPlayTimes(), playlistCacheModel.getOrderId());
			if(begin <= time && end >= time && putinNumber){				
				// 随机获取一个播出单对应的内容
				AdPlaylistReqContent obj;
				String resource="";
				String resourceid="";
				obj= playlistCacheModel.getContent();
				if (obj!=null)
				{
					resource= obj.getContentPath();
					resourceid = obj.getContentId();
				}
				// 先从普通的过滤条件过滤后再从精准过滤条件中过滤。
				// 精准命中标识位
				boolean precisionHit = false;
				short level = 0;
				List<String> lstResource =  new ArrayList<String>();
				List<PrecisionCacheModel> lstPrecision = playlistCacheModel.getLstPrecision();
				//根据优先级排序 精准 
				PresisionComparator comparor =  new PresisionComparator();
				Collections.sort(lstPrecision, comparor);  
				ContentCacheBean precisionContent=null;
				for (int i = 0, j = lstPrecision.size(); i < j; i++) {
					
					PrecisionCacheModel objPrecision= lstPrecision.get(i);
					String tvns = objPrecision.getTvnNumber();
					String expression = objPrecision.getTvnExpression();
					boolean matchingTVN = matchTVNPreinstallCondition(tvns, expression, tvn);
					switch (objPrecision.getType()) {
					
					// 类型为产品。
					case ConstantsHelper.PRECISION_TYPE_PRODUCTION:
						if(bean.getProductId() != null )
						{
							precisionContent=ProductPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), StringUtil.toNotNullStr(bean.getProductId()));
							if (precisionContent!=null)
							{
								matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
								if (matchingTVN)
								{
									resource = precisionContent.getContentPath();
									resourceid = precisionContent.getResourcid();
									precisionHit = true;
									break;
								}
							}		
						}
					break;
					// 类型为影片元数据关键字
					case ConstantsHelper.PRECISION_TYPE_MOVES_KEYWORDS:
						List<String> keyList = new ArrayList<String>();
						
						if (bean.getAssetInfo()==null )
						{
							break;
						}
						keyList = bean.getAssetInfo().getAssetDescription();
						if (keyList==null)
						{
							break;
						}
						String precisonkeyName ="";
						for (int keyIndex=0;keyIndex<keyList.size();keyIndex++)
						{
							precisonkeyName=AssetKeyPrecisionCache.getKey(StringUtil.toNotNullStr(objPrecision.getId()));
							if(precisonkeyName != null && keyList.get(keyIndex)!=null && !keyList.get(keyIndex).equals("") && keyList.get(keyIndex).indexOf(precisonkeyName)>=0)
							{
								precisionContent =AssetKeyPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), precisonkeyName);
								if (precisionContent!=null)
								{
									matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
									if (matchingTVN)
									{
										resource = precisionContent.getContentPath();
										resourceid = precisionContent.getResourcid();
										precisionHit = true;
										break;
									}
								}		
							}
						}						
					break;
					// 类型为用户级别
					case ConstantsHelper.PRECISION_TYPE_USER_LEVELS:
						precisionContent=LevelPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), StringUtil.toNotNullStr(userSort));
						if (precisionContent!=null)
						{
							matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
							if (matchingTVN)
							{
								resource = precisionContent.getContentPath();
								resourceid = precisionContent.getResourcid();
								precisionHit = true;
								break;
							}
						}		
					break;
					// 类型为行业级别
					case ConstantsHelper.PRECISION_TYPE_USER_TRADE:
						precisionContent=IndustryPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), StringUtil.toNotNullStr(userTrade));
						if (precisionContent!=null)
						{
							matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
							if (matchingTVN)
							{
								resource = precisionContent.getContentPath();
								resourceid = precisionContent.getResourcid();
								precisionHit = true;
								break;
							}
						}		
					break;
					
					// 类型为影片分类
					case ConstantsHelper.PRECISION_TYPE_MOVES_SORT:
						String assetId = bean.getAssetId();
						List<String> categoryList = new ArrayList<String>();
						categoryList = AssetCache.getCategory(assetId);
						
						if (categoryList==null)
						{
							break;
						}
						for (int categoryIndex=0;categoryIndex<categoryList.size();categoryIndex++)
						{
							precisionContent =AssetCategoryPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), categoryList.get(categoryIndex));
							if (precisionContent!=null)
							{
								matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
								if (matchingTVN)
								{
									resource = precisionContent.getContentPath();
									resourceid = precisionContent.getResourcid();
									precisionHit = true;
									break;
								}
							}		
						}
					break;					
					// 类型为回放频道 
					case ConstantsHelper.PRECISION_TYPE_PLAYBACK_CHANNEL:
						//Map<String, String> channelCache = ProductCache.getInstance().getChannelCahce();
						if(asset != null && !asset.isEmpty() && asset.length() >= 3){
							String assetKeywords = asset.substring(0,3);
							String serviceId = NpvrCache.getServiceId(assetKeywords);		
							precisionContent =NpvrPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), serviceId);
							if (precisionContent!=null)
							{
								matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
								if (matchingTVN)
								{
									resource = precisionContent.getContentPath();
									resourceid = precisionContent.getResourcid();
									precisionHit = true;
									break;
								}
							}		
						}						
					    break;
					// 类型为影片
					case ConstantsHelper.PRECISION_TYPE_PASSED_ASSET:
						TAssetV assetV=AssetCache.getAsset(bean.getAssetId());
						if (assetV==null)
						{
							break;
						}
						String assetName = assetV.getAssetName();
						String precisonassetName ="";
						precisonassetName=AssetNamePrecisionCache.getName(StringUtil.toNotNullStr(objPrecision.getId()));
						System.out.println(assetName.length());
						System.out.println(precisonassetName.length());
						
						//影片名模糊匹配
						if(assetName != null && precisonassetName!=null && assetName.indexOf(precisonassetName)>=0)
						{
							precisionContent =AssetNamePrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), precisonassetName);
							if (precisionContent!=null)
							{
								matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
								if (matchingTVN)
								{
									resource = precisionContent.getContentPath();
									resourceid = precisionContent.getResourcid();
									precisionHit = true;
									break;
								}
							}		
						}
					break;
					// 类型为用户区域
					case ConstantsHelper.PRECISION_TYPE_USER_LOCATION:
						precisionContent=AreaPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), StringUtil.toNotNullStr(locationCode4));
						if (precisionContent!=null)
						{
							matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
							if (matchingTVN)
							{
								resource = precisionContent.getContentPath();
								resourceid = precisionContent.getResourcid();
								precisionHit = true;
								break;
							}
						}						
						precisionContent=AreaPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), StringUtil.toNotNullStr(locationCode3));
						if (precisionContent!=null)
						{
							matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
							if (matchingTVN)
							{
								resource = precisionContent.getContentPath();
								resourceid = precisionContent.getResourcid();
								precisionHit = true;
								break;
							}
						}	
						precisionContent=AreaPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), StringUtil.toNotNullStr(locationCode2));
						if (precisionContent!=null)
						{
							matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
							if (matchingTVN)
							{
								resource = precisionContent.getContentPath();
								resourceid = precisionContent.getResourcid();
								precisionHit = true;
								break;
							}
						}	
						precisionContent=AreaPrecisionCache.getContent(StringUtil.toNotNullStr(objPrecision.getId()), StringUtil.toNotNullStr(locationCode1));
						if (precisionContent!=null)
						{
							matchingTVN = matchTVNPreinstallCondition(precisionContent.getTvns(), precisionContent.getExpression(), tvn);
							if (matchingTVN)
							{
								resource = precisionContent.getContentPath();
								resourceid = precisionContent.getResourcid();
								precisionHit = true;
								break;
							}
						}	
					    break;						
					default:
						break;
					}	
					// 如果没有精准内容命中，则返回普通过滤，如果有，则返回精准过滤
					if(precisionHit){
//						AdPlaylistReqPContent objContent= objPrecision.getPreContent();
//						resource = objContent.getContentPath();
//						lstResource.add(resource);
//						// 如果优先级相同的就随机取一个。
//						if(level > objPrecision.getUseLevel()){
//							int x =  (int)(Math.random()*100000)%lstResource.size();
//							resource = lstResource.get(x);
//							break;
//						}
						break;//for
					} 
					// 记录优先级
					//level = objPrecision.getUseLevel();
				}
				//只有内容不为空时
				if (resource!=null && !resource.equals(""))
				{
					// 根据广告位填充广告内容
					TempObjectStorgePlaylistContent tmpobj = new TempObjectStorgePlaylistContent();
					
					resource  = setContentPath(resource,code,locationCity);
					this.setContentByAdSuitCode(adContent, resource, code, playlistCacheModel.getAnthoring(), tmpobj);
					
					// 填充保存投放记录的数据结构
					tmpobj.setAdSiteCode(code);
					tmpobj.setUserCode(tvn);
					tmpobj.setContractId(playlistCacheModel.getContractId());
					tmpobj.setContentPath(resource);
					tmpobj.setContentId(resourceid);					
					tmpobj.setId(playlistCacheModel.getId());
					tmpobj.setDateTime(new Date());
					tmpobj.setToken(bean.getToken());
					tmpobj.setOrderId(playlistCacheModel.getOrderId());
					lsttmp.add(tmpobj);
					if (preorderid!=playlistCacheModel.getOrderId())
					{
						OrderCache.addTimeEnough(playlistCacheModel.getOrderId());
						preorderid=playlistCacheModel.getOrderId();
						logger.info("OrderId:"+playlistCacheModel.getOrderId()+"++"+resource);
					}
				}
			}
		}
		// 将得到的临时对象中的合同编号，广告位编码，用户编码，播出单号，内容信息保存入广告投放历史信息表中
		// 异步保存广告信息
//		MySaveReportThread myThread = new MySaveReportThread();
//		myThread.setLsttmp(lsttmp);
//		Thread thread = new Thread(myThread);
//		thread.start();
		ReqHistoryCache.addMap(bean.getToken(), lsttmp);
		String adCode="";
		//如果有素材过期，导致返回没有数据的时候需要填充默认素材
		try
		{
			//高清回看暂停
			if (typeAds==ConstantsAdsCode.HD_LOOKBACK_ADVERT_POSITION)
			{
				adCode=ConstantsAdsCode.HD_LOOKBACK_ADVERT_POSITION_PAUSE;
			}
			//标清回看暂停
			if (typeAds==ConstantsAdsCode.SD_LOOKBACK_ADVERT_POSITION)
			{
				adCode=ConstantsAdsCode.SD_LOOKBACK_ADVERT_POSITION_PAUSE;
			}
			//高清回放暂停
			if (typeAds==ConstantsAdsCode.HD_PLAYBACK_ADVERT_POSITION)
			{
				adCode=ConstantsAdsCode.HD_NPVR_ADVERT_POSITION_PAUSE;
			}
			//标清点播暂停
			if (typeAds==ConstantsAdsCode.SD_ASSET_ADVERT_POSITION)
			{
				adCode=ConstantsAdsCode.SD_ASSET_ADVERT_POSITION_PAUSE;
			}
			//高清点播暂停
			if (typeAds==ConstantsAdsCode.HD_ASSET_ADVERT_POSITION)
			{
				adCode=ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_PAUSE;
			}
		
			adContent = fillDefaultResource(adContent,adCode,locationCity);
		}
		catch(Exception e)
		{
			
		}
		return adContent;
	}
	/**
	 * 
	 * 根据参数设置匹配点播返回的广告位对应的所有缓存数据
	 * 
	 * @param bean 请求的xml参数
	 * @param flag 是填写过问卷
	 * @param map 播出单缓存
	 * @param deviceType 高标清设备标识符
	 * @param asset 资产的ID
	 * @return 返回匹配的点播广告位
	 */
	private String[] getAdType(AdInsertRequestXmlBean bean, String deviceType,Boolean flag, String assetId )
	{
		Map<String, String> typeMap = new HashMap<String, String>();
		String[] typearray = null;
		// 判断是否为回看类型，并设置广告位,区别高标清
		if(bean.getProductId() != null && !bean.getProductId().isEmpty()){
			//Map<String, String> productCache =ProductCache.ge;
			// 查询所有未回看的产品，
			String lookbackType = ProductCache.getProductType(bean.getProductId());
			// 设置返回的参数
			if (lookbackType!=null && lookbackType.equals(ConstantsHelper.LOOKBACK_TYPE))
			{
				typeMap.put(ConstantsHelper.LOOKBACK_TYPE, lookbackType);
				// 如果请求的该产品为回看，则lookbackType不为空
				if(lookbackType != null && ConstantsHelper.HIGH_DEFINITION_VIDEO.equals(deviceType)){
					typearray = ConstantsAdsCode.HD_LOOKBACK_ADVERT_POSITION;
					return typearray;
				}else if(lookbackType != null && ConstantsHelper.STANDARD_DEFINITION_VIDEO.equals(deviceType)){
					typearray = ConstantsAdsCode.SD_LOOKBACK_ADVERT_POSITION;
					return typearray;
				}	
			}
		}
		// 判断是否为回放类型，并设置广告位,区别高标清
		if(assetId != null && !assetId.isEmpty() && assetId.length() >= 3){
			String assetKeywords = assetId.substring(0,3);
			String serviceId = NpvrCache.getServiceId(assetKeywords);
			if (serviceId!=null && !serviceId.equals(""))
			{
				typeMap.put(ConstantsHelper.PLAYBACK_TYPE_FALG, serviceId);
				if(serviceId != null && ConstantsHelper.HIGH_DEFINITION_VIDEO.equals(deviceType)){
					typearray = ConstantsAdsCode.HD_PLAYBACK_ADVERT_POSITION;
					return typearray;
				}
			}
			
		}
		// 因为直接传的是点击资源请求，没有传广告位ID，所以需要判断是频道还是影片
		// 并将频道或者影片会对应到得广告位的所有的播出单找出来
		// 如果用户已经填写过问卷，就不会再返回问卷类型的广告了
		// 默认为高清
		if(flag){
			if(ConstantsHelper.STANDARD_DEFINITION_VIDEO.equals(deviceType)){
				typearray = ConstantsAdsCode.SD_ASSET_ADVERT_POSITION_NO_Q;
			} else {
				typearray = ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_NO_Q;
			}
			return typearray;
		}else{
			if(ConstantsHelper.STANDARD_DEFINITION_VIDEO.equals(deviceType)){
				typearray = ConstantsAdsCode.SD_ASSET_ADVERT_POSITION;
			} else {
				typearray = ConstantsAdsCode.HD_ASSET_ADVERT_POSITION;
			}
			return typearray;
		}
		
	}
	public List<String> getPlayListByAreaandPostioncode(String areacode,String []positionCode)
	{
		List<String> rettemp=new ArrayList<String>();
		for (int i=0;i<positionCode.length;i++)
		{
			List<String> temp =ReqPlayListCache.getPlayList(areacode+ConstantsHelper.COLON+positionCode[i]);
			if (temp!=null)
			{
				rettemp.addAll(temp);
			}
		}
		return rettemp;
		
	}
	/**
	 * 匹配精准的TVN预设条件
	 * @param tvns
	 * @param expression
	 * @return
	 */
	private boolean matchTVNPreinstallCondition(String tvns, String expression, String tvn){
		boolean done = false;
		if(tvns == null || tvns.isEmpty() ){
			done = true;
			return done;
		} 
		String[] sTvns = tvns.split(ConstantsHelper.COMMA);
		for (int i = 0; i < sTvns.length; i++) {
			if(ConstantsHelper.PRECISION_TVN_EXPRESSION_EQUAL.equals(expression)){
				// 如果是等于TVN，那么只要有一个满足条件，跳出循环并返回true
				if(sTvns[i].equals(tvn)){
					done = true;
					break;
				}
				
			}else if (ConstantsHelper.PRECISION_TVN_EXPRESSION_UNEQUAL.equals(expression)){
				// 如果是不等于TVN，那么只要有一个等于就不满足条件，跳出循环并返回false
				done = true;
				if(sTvns[i].equals(tvn)){
					done = false;
					break;
				}
			}
		}
		
		return done;
	}
	/**
	 * 根据广告位填充广告内容
	 * @param adContent 被填充的对象
	 * @param resource 广告资源内容的URL
	 * @param code 广告位编码
	 * @param anthoring 插播特征值 0 1/3 2/3 等 
	 */
    private void setContentByAdSuitCode(AdContent adContent, String resource,
			String code, String anthoring, TempObjectStorgePlaylistContent tmpobj) {
    	// 插播广告位
		if(code.indexOf(ConstantsAdsCode.WATCH_VIDEOTAPES) >= 0 
				|| code.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_INSERT) >= 0){
			InsertedAdList adlist = adContent.getInsertedAdList();
			List<InsertedAd> list= null;
			if(adlist == null){
				adlist = new InsertedAdList();
				list = new ArrayList<InsertedAd>();
				InsertedAd obj = new InsertedAd();
				obj.setAdSeq(list.size()+1+"");
				obj.setInsertedTime(anthoring);
				obj.setAdUrl(resource);
				list.add(obj);
				adlist.setLstInsertedAd(list);
				adContent.setInsertedAdList(adlist);
			}else if(adlist != null && adlist.getLstInsertedAd() != null && adlist.getLstInsertedAd().size() > 0){
				list = adlist.getLstInsertedAd();
				InsertedAd obj = new InsertedAd();
				obj.setAdSeq(list.size()+1+"");
				obj.setInsertedTime(anthoring);
				obj.setAdUrl(resource);
				list.add(obj);
				//adlist.setLstInsertedAd(list);
				//adContent.setInsertedAdList(adlist);
			}
			tmpobj.setSeq(list.size()+"");
		} 
		// 暂停广告
		else if(code.indexOf(ConstantsAdsCode.LOOKBACK_ADVERT_POSITION_PAUSE)>= 0
				|| code.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_PAUSE) >= 0){
			adContent.setPausePic(resource);
		} 
		 // 调查问卷广告
		else if(code.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_QUESTIONNAIRE) >= 0){
			adContent.setSurveyURL(resource);
		}
		// 挂角广告
		else if(code.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_TOPRIGHT) >= 0){
			adContent.setRightTopPic(resource);
		}
		// 字幕广告
		else if(code.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_SUBTITLE) >= 0){
			adContent.setSubTitle(resource);
		}
	}
	
	/**
	 * 将播出单的列表处理成XML.
	 *
	 * @param List<AdPlaylistReq> 播出单列表
	 * @return String  response XML
	 */
	private String ProcessPlayListToXml(AdContent adContent) {
		JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
		// 封装返回的bean
		AdInsertResponseBean bean = new AdInsertResponseBean();
		if(adContent.getSurveyURL() != null && !adContent.getSurveyURL().isEmpty()){
			bean.setSurveyQueryRespCode(0);
			
		}else{
			bean.setSurveyQueryRespCode(1);
		}
		bean.setUserSurveyList(adContent);
		String responseXml = helper.toXML(bean);
		return responseXml;
	}
	
	/**
	 * 
	 * 接收gateway发出广告投放情况，并处理
	 * 
	 * @param String gateway发出广告投放情况的XML
	 * @return 处理成功
	 */
	public boolean StartReportRequest(String xml) {
		JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
		boolean success = false;
		try {
			AdStatusReportReqXmlBean bean = (AdStatusReportReqXmlBean) helper.fromXML(xml);
			// 先从数据库中根据token获取初始化的投放记录
			List<TempObjectStorgePlaylistContent> listObj = new ArrayList<TempObjectStorgePlaylistContent>();// aDSurveyDAO.getHistoryByToken(bean.getToken());
			if (bean!=null)
			{
				if (bean.getPausePic()==1)
				{
					TempObjectStorgePlaylistContent temp = new TempObjectStorgePlaylistContent();
					temp.setToken(bean.getToken());
					temp.setSeq(ConstantsAdsCode.HISTORY_SEQ_PAUSEFALG);
					temp.setState(1);					
					listObj.add(temp);
				}
				if (bean.getRightTopPicStatus()==1)
				{
					TempObjectStorgePlaylistContent temp = new TempObjectStorgePlaylistContent();
					temp.setToken(bean.getToken());
					temp.setSeq(ConstantsAdsCode.HISTORY_SEQ_TOPRIGHTFALG);
					temp.setState(1);
					listObj.add(temp);
				}
				if (bean.getSubTitleStaus()==1)
				{
					TempObjectStorgePlaylistContent temp = new TempObjectStorgePlaylistContent();
					temp.setToken(bean.getToken());
					temp.setSeq(ConstantsAdsCode.HISTORY_SEQ_SUBTITLEFALG);
					temp.setState(1);
					listObj.add(temp);
				}
				if (bean.getAdReportList()!=null && bean.getAdReportList().getAdStatus()!=null )
				{
					List<AdStatus> statusList =  bean.getAdReportList().getAdStatus();
					for (int i =0;i<statusList.size();i++)
					{
						TempObjectStorgePlaylistContent temp = new TempObjectStorgePlaylistContent();
						temp.setToken(bean.getToken());
						temp.setSeq(statusList.get(i).getSeq());
						temp.setState(1);
						listObj.add(temp);
					}
					
				}
			}
			if(listObj!=null &&  listObj.size() > 0){

				ReqHistoryCache.addStatusMap(bean.getToken(), listObj);
			}
//			int count = 0;
//			for (TempObjectStorgePlaylistContent obj : listObj) {
//				
//				String position = obj.getAdSiteCode();
//				
//				if(position == null || position.isEmpty()){
//					continue;
//				}
//				// 如果是插播类（回看回放插播或者点播插播）
//				if(position.indexOf(ConstantsAdsCode.LOOKBACK_ADVERT_POSITION_INSERT) >= 0
//						|| position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_INSERT) >= 0){
//					List<AdStatus> list = bean.getAdReportList().getAdStatus();
//					
//					for (AdStatus adStatus : list) {
//						if(obj.getSeq() != null && obj.getSeq().equals(adStatus.getSeq())){
//							obj.setState(adStatus.getStatus());
//							if("1".equals(adStatus.getStatus())){
//								count++;
//								OrderCache.addMapCount(obj.getOrderId());
//							}
//							break;
//						}
//					}
//				}
//				//如果是暂停类的广告
//				else if(position.indexOf(ConstantsAdsCode.LOOKBACK_ADVERT_POSITION_PAUSE) >= 0
//						|| position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_PAUSE) >= 0){
//					obj.setState(bean.getPausePic());
//					if("1".equals(bean.getPausePic())){
//						count++;
//						OrderCache.addMapCount(obj.getOrderId());
//					}
//				}
//				//如果是挂角类的广告
//				else if(position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_TOPRIGHT) >= 0){
//					obj.setState(bean.getRightTopPicStatus());
//					if("1".equals(bean.getPausePic())){
//						count++;
//						OrderCache.addMapCount(obj.getOrderId());
//					}
//				}
//				//如果是字幕类的广告
//				else if(position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_SUBTITLE) >= 0){
//					obj.setState(bean.getSubTitleStaus());
//					if("1".equals(bean.getPausePic())){
//						count++;
//						OrderCache.addMapCount(obj.getOrderId());
//					}
//				}
//			}
//			if(listObj != null){
//				// 将修改了投放状态的投放记录保存
//				ReqHistoryCache.addStatusMap(bean.getToken(), listObj);
//				//aDSurveyDAO.SavePlaylistIntoHistoryReport(listObj);
//				int orderId = listObj.get(0).getOrderId();
//				// 修改订单中的已播放次数
//				//aDSurveyDAO.updatePlaylistPutInTimes(orderId, count);
//			}
			
			success = true;
		} catch (Exception e) {
			logger.error("更新投放历史记录信息时失败 \n"+e.getMessage());
			return success;
		}
		return success;
	}
	/**
	 * 如果有素材过期，导致返回没有数据的时候需要填充默认素材,（假设所有都是高清广告）
	 * @param adContent
	 * @return adContent
	 */
	private AdContent fillDefaultResource(AdContent adContent,String adCode,String locationCity) {
		Map<String, String> map = DefaultResourceCache.getResourceCache();
		
		//如果需要则填充暂停广告位默认素材
		if(adContent.getPausePic() == null || adContent.getPausePic().isEmpty()){
			String content  = map.get(adCode);
			content= setContentPath(content,adCode,locationCity);
			adContent.setPausePic(content);
		}
		/*
		//如果需要则填充挂角广告位默认素材
		if(adContent.getRightTopPic() == null || adContent.getRightTopPic().isEmpty()){
			adContent.setRightTopPic(map.get(ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_TOPRIGHT));
		}
		
		//如果需要则填充字幕广告位默认素材
		if(adContent.getSubTitle() == null || adContent.getSubTitle().isEmpty()){
			adContent.setSubTitle(map.get(ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_SUBTITLE));
		}
		
		//如果需要则填充插播广告位默认素材
		if(adContent.getInsertedAdList() == null || adContent.getInsertedAdList().getLstInsertedAd() == null 
				|| adContent.getInsertedAdList().getLstInsertedAd().size() <= 0){
			InsertedAd ad = new InsertedAd();
			ad.setAdSeq("1");
			ad.setInsertedTime("HD,0");
			ad.setAdUrl(map.get(ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_INSERT));
			List<InsertedAd> list = new ArrayList<InsertedAd>();
			list.add(ad);
			InsertedAdList insertedAdList = new InsertedAdList();
			adContent.setInsertedAdList(insertedAdList);
		}
		*/
		return adContent;
	}
	private String setContentPath(String content,String adsCode,String locationCity) {
		String 	contentPath = content;
		logger.info("adsCode="+adsCode+";locationCity="+locationCity);
		
		if (adsCode.equals(ConstantsAdsCode.SD_ASSET_ADVERT_POSITION_SUBTITLE) || adsCode.equals(ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_SUBTITLE) )
		{
			return contentPath;
		}
		if(contentPath != null && !contentPath.equals("")){
				// 如果有数据库字段中第一个字符是单斜线就直接添加，没有就加上一个单斜线
				String firstChar = contentPath.substring(0,1);
				if(!ConstantsHelper.OBLIQUE_LINE.equals(firstChar)){
					contentPath = ConstantsHelper.OBLIQUE_LINE + contentPath;
				}
				contentPath  = InitConfig.getContentPath(adsCode,locationCity)+contentPath;
				//cont.setContentPath(InitConfig.getContentPath(adPlaylistReq.getAdSiteCode())+contentPath);
			}
		
		return contentPath;
	}
}
