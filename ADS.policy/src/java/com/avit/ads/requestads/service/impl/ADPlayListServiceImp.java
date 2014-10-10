package com.avit.ads.requestads.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.syndata.SynBsmpData;

import com.avit.ads.requestads.bean.AdPlaylistReqContent;
import com.avit.ads.requestads.bean.AdPlaylistReqPContent;
import com.avit.ads.requestads.bean.AdQuestionnaireData;
import com.avit.ads.requestads.bean.PlaylistCacheModel;
import com.avit.ads.requestads.bean.PrecisionCacheModel;
import com.avit.ads.requestads.bean.Question;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.bean.request.AdInsertRequestXmlBean;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.bean.request.AdStatusReportReqXmlBean;
import com.avit.ads.requestads.bean.request.AssetInfo;
import com.avit.ads.requestads.bean.response.AdContent;
import com.avit.ads.requestads.bean.response.AdInsertResponseBean;
import com.avit.ads.requestads.bean.response.InsertedAd;
import com.avit.ads.requestads.bean.response.InsertedAdList;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.service.ADPlayListService;
import com.avit.ads.requestads.service.DefaultResourceCache;
import com.avit.ads.requestads.service.MediaInfoCache;
import com.avit.ads.requestads.service.MySaveReportThread;
import com.avit.ads.requestads.service.PlaylistCache;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.xml.JaxbXmlObjectConvertor;


/**
 *  决策子系统处理业务类
 *  
 *  @author chenkun
 *  @since 2013-02-22 create
 *  @version 1.0
 *  @modify_History 
 */
@Service
public class ADPlayListServiceImp implements ADPlayListService{

	/** The a d survey dao. */
	@Inject
	ADSurveyDAO aDSurveyDAO;
	private static Logger logger = LoggerFactory.getLogger(ADPlayListServiceImp.class);

	public ADSurveyDAO getaDSurveyDAO() {
		return aDSurveyDAO;
	}

	public void setaDSurveyDAO(ADSurveyDAO aDSurveyDAO) {
		this.aDSurveyDAO = aDSurveyDAO;
	}

	/**
	 * 根据gateway传输过来的XML请求中的参数，过滤出会使用到得播出单，然后将播出单打包并返回.
	 *
	 * @param xml 封装请求参数的XML
	 * @return string 封装请求到得播出单的LIST的XML
	 */
	public String GenerateADPlayList(String xml) {
		String responseXml = "";
		AdContent adContent = null;
		
		try {
			JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
			AdInsertRequestXmlBean bean = (AdInsertRequestXmlBean) helper.fromXML(xml);
			
			// 判断用户是否允许播放广告
			String tvnId = bean.getTvnId();
			boolean enable = userBePermitShowAD(tvnId);
			if(!enable){
				return "";
			}
			// 如果用户进行过问卷调查，则会有用户喜爱的广告类型，则该类型为最优先的广告
			// 先获取用户的喜好，然后再进行匹配
			boolean flag = userBeSurvey(tvnId);
				
			// 从cache中获取数据
			adContent = getDataFromCache(bean, flag);
			if (adContent!=null && adContent.getInsertedAdList()!=null && adContent.getInsertedAdList().getLstInsertedAd()!=null && (adContent.getInsertedAdList().getLstInsertedAd().get(0).getAdUrl()==null || adContent.getInsertedAdList().getLstInsertedAd().get(0).getAdUrl().equals("")))
			{
				adContent = new AdContent();
			}
			if(adContent == null ){
				//  如果没有匹配到广告，这创建一个对象准备填充默认素材
				adContent = new AdContent();
			}
			
			//如果有素材过期，导致返回没有数据的时候需要填充默认素材
			adContent = fillDefaultResource(adContent);
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
		}
		return responseXml;
	}

	

	/**
	 * 如果有素材过期，导致返回没有数据的时候需要填充默认素材,（假设所有都是高清广告）
	 * @param adContent
	 * @return adContent
	 */
	private AdContent fillDefaultResource(AdContent adContent) {
		Map<String, String> map = DefaultResourceCache.getResourceCache();
		
		//如果需要则填充暂停广告位默认素材
		if(adContent.getPausePic() == null || adContent.getPausePic().isEmpty()){
			adContent.setPausePic(map.get(ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_PAUSE));
		}
		
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
		return adContent;
	}

	/**
	 * 从cache中获取数据,先从播出单中过滤，当从播出单中找到内容时，再使用精准过滤出播出内容，
	 * @param bean 请求Xml实体对象
	 * @return List<TempObjectStorgePlaylistContent> 主要用于存放广告内容和保存记录所需要的对象集合
	 */
	private AdContent getDataFromCache(AdInsertRequestXmlBean bean, Boolean flag) {
		String tvn = bean.getTvnId();
		SynBsmpData bsmp = new SynBsmpData();
		HashMap user = bsmp.getUserInfoByTvn(tvn);
		String userSort = (String)user.get("USERLEVEL");
		BigDecimal locationCode = (BigDecimal)user.get("LOCATIONCODEVALUE");
		String userTrade = (String)user.get("INDUSTRYCATEGORY");
		
		String deviceType = bean.getDefinition();
		String asset = bean.getAssetId();
		List<PlaylistCacheModel> lstCache = new ArrayList<PlaylistCacheModel>();
		// 获取所对应广告位的缓存数据
		Map<String, String> typeMap = getMatchCacheWithAdvertPosition(bean, flag, deviceType, asset, lstCache);
		
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
		
		// 遍历cache,使用过滤条件去过滤
		for (PlaylistCacheModel playlistCacheModel : lstCache) {
			long begin = playlistCacheModel.getBegin();
			long end = playlistCacheModel.getEnd();
			long id = playlistCacheModel.getId();
			String code = playlistCacheModel.getAdSiteCode();
			
//			// 如果是字幕广告位，则需要比较用户行业和用户级别,暂时在播出单中不进行过滤
//			if(code != null && code.indexOf(ConstantsAdsCode.SUBTITLE_CODE) >= 0){
//				// 如果字幕广告的行业类别和用户级别不满足要求，则排除
//				if((userSort == null || !checkPrecision(userLev, userSort)) 
//						&& (userTrade == null || !checkPrecision(trade, userTrade))){
//					continue;
//				}
//			} 
		
			// 拼凑命中map所需要的key   key = 播出单ID+频道+区域, 如果是请求的影片，频道ID可能为空，为了不进入循环中判断，将channelId设置为"0"
			String key = id +":" + (locationCode== null?0: locationCode.intValue());
			boolean putinNumber = matchPutInTimesCondition(playlistCacheModel.getPlayTimes(), playlistCacheModel.getOrderId());
			if(begin <= time && end >= time && putinNumber){
				Map<String, String> contentMap = playlistCacheModel.getMap();
				String content = contentMap.get(key);
				String allLocation = id  + ":0";
				String allLocationContent = contentMap.get(allLocation);
				// 如果按区域匹配和全区域匹配都匹配不上的话，则该播出单不符合条件，进入下一个循环
				// 如果不为空则命中
				if(content == null && allLocationContent == null){
					continue;
				}
				
					
				// 随机获取一个播出单对应的内容
				AdPlaylistReqContent obj;
				String resource="";
				
				obj= playlistCacheModel.getContent();
				if (obj!=null)
				{
					resource= obj.getContentPath();
				}
				
				
				// 先从普通的过滤条件过滤后再从精准过滤条件中过滤。
				// 精准命中标识位
				boolean precisionHit = false;
				short level = 0;
				List<String> lstResource =  new ArrayList<String>();
				List<PrecisionCacheModel> lstPrecision = playlistCacheModel.getLstPrecision();
				for (int i = 0, j = lstPrecision.size(); i < j; i++) {
					
					PrecisionCacheModel objPrecision= lstPrecision.get(i);
					String tvns = objPrecision.getTvnNumber();
					String expression = objPrecision.getTvnExpression();
					boolean matchingTVN = matchTVNPreinstallCondition(tvns, expression, tvn);
					switch (objPrecision.getType()) {
					
					// 类型为产品。
					case ConstantsHelper.PRECISION_TYPE_PRODUCTION:
						if(bean.getProductId() != null 
								&& bean.getProductId().equals(objPrecision.getProductCode())
								&& matchingTVN){
							precisionHit = true;
						}
					break;
					// 类型为影片元数据关键字
					case ConstantsHelper.PRECISION_TYPE_MOVES_KEYWORDS:
						if(checkKeywords(bean.getAssetInfo(), objPrecision.getKey())
								&& matchingTVN){
							precisionHit = true;
						}
					break;
					// 类型为用户级别
					case ConstantsHelper.PRECISION_TYPE_USER_LEVELS:
						if(userSort != null && userSort.equals(objPrecision.getUserlevels())
								&& matchingTVN){
							precisionHit = true;
						}
					break;
					// 类型为行业级别
					case ConstantsHelper.PRECISION_TYPE_USER_TRADE:
						if(userTrade != null && userTrade.equals(objPrecision.getUserindustrys())
								&& matchingTVN){
							precisionHit = true;
						}
					break;
					
					// 类型为影片分类
					case ConstantsHelper.PRECISION_TYPE_MOVES_SORT:
						String assetId = bean.getAssetId();
						boolean checkflag = aDSurveyDAO.getCategoryIdByAsset(assetId, objPrecision.getAssetSortId());
						if(assetId != null && checkflag){
							precisionHit = true;
						}
					break;
					// 类型为回看栏目 
					case ConstantsHelper.PRECISION_TYPE_LOOKBACK_CHANNEL:
						String product = typeMap.get(ConstantsHelper.LOOKBACK_TYPE);
						if(product != null && product.equals(objPrecision.getLookbackCategoryId())
								&& matchingTVN){
							precisionHit = true;
						}
					break;
					// 类型为回放频道 
					case ConstantsHelper.PRECISION_TYPE_PLAYBACK_CHANNEL:
						String service = typeMap.get(ConstantsHelper.PLAYBACK_TYPE_FALG);
						if(service != null && service.equals(objPrecision.getPlaybackServiceId())
								&& matchingTVN){
							precisionHit = true;
						}
					break;
					// 类型为影片
					case ConstantsHelper.PRECISION_TYPE_PASSED_ASSET:
						String assetName = MediaInfoCache.getInstance().getAssetCache().get(bean.getAssetId());
						if((bean.getAssetId() != null && assetName != null && assetName.indexOf(objPrecision.getAssetId()) >= 0)
								&& matchingTVN){
							precisionHit = true;
						}
					break;
					// 类型为用户区域
					case ConstantsHelper.PRECISION_TYPE_USER_LOCATION:
						boolean done = matchUserLocation(locationCode, objPrecision.getUserArea());
						if(done){
							precisionHit = true;
						}
					break;
						
					default:
						break;
					}
						
					// 如果没有精准内容命中，则返回普通过滤，如果有，则返回精准过滤
					if(precisionHit){
						AdPlaylistReqPContent objContent= objPrecision.getPreContent();
						resource = objContent.getContentPath();
						lstResource.add(resource);
						// 如果优先级相同的就随机取一个。
						if(level > objPrecision.getUseLevel()){
							int x =  (int)(Math.random()*100000)%lstResource.size();
							resource = lstResource.get(x);
							break;
						}
					} 
					// 记录优先级
					level = objPrecision.getUseLevel();
				}
				// 根据广告位填充广告内容
				TempObjectStorgePlaylistContent tmpobj = new TempObjectStorgePlaylistContent();
				this.setContentByAdSuitCode(adContent, resource, code, playlistCacheModel.getAnthoring(), tmpobj);
				
				// 填充保存投放记录的数据结构
				tmpobj.setAdSiteCode(code);
				tmpobj.setUserCode(tvn);
				tmpobj.setContractId(playlistCacheModel.getContractId());
				tmpobj.setContentPath(resource);
				tmpobj.setId(playlistCacheModel.getId());
				tmpobj.setDateTime(new Date());
				tmpobj.setToken(bean.getToken());
				tmpobj.setOrderId(playlistCacheModel.getOrderId());
				lsttmp.add(tmpobj);
			}
		}
		// 将得到的临时对象中的合同编号，广告位编码，用户编码，播出单号，内容信息保存入广告投放历史信息表中
		// 异步保存广告信息
		MySaveReportThread myThread = new MySaveReportThread();
		myThread.setLsttmp(lsttmp);
		Thread thread = new Thread(myThread);
		thread.start();
		return adContent;
	}

	/**
	 * 匹配用户区域
	 * @param locationCode
	 * @param userArea
	 * @return
	 */
	private boolean matchUserLocation(BigDecimal locationCode, String userArea) {
		boolean done = false;
		if(locationCode == null || userArea == null || locationCode.intValue() == 0 || userArea.isEmpty()){
			return done;
		}
		if(userArea.equals(locationCode.toString())){
			done = true;
		}else{
			done = aDSurveyDAO.matchUserLocation(locationCode.intValue(), userArea);
		}
		return done;
	}

	/**
	 * 如果不是按次投放，返回true，如果是按次投放，投放次数没有达到返回true，反之false
	 * @param orderId
	 * @return 
	 */
	private boolean matchPutInTimesCondition(int playTime, int orderId) {
		if(playTime <= 0){
			return true;
		} else {
			boolean enough = aDSurveyDAO.getPutInTimesEnough(orderId);
			return enough;
		}
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
	 * @return 匹配点播返回的广告位对应的所有缓存数据
	 */
	private Map<String, String> getMatchCacheWithAdvertPosition(
			AdInsertRequestXmlBean bean, Boolean flag, String deviceType,
			String asset, List<PlaylistCacheModel> lstCache) {
		 Map<String, String> typeMap = new HashMap<String, String>();
		String[] array = null;
		Map<String, List<PlaylistCacheModel>> map = PlaylistCache.getInstance().getCache();
		// 判断是否为回看类型，并设置广告位,区别高标清
		if(bean.getProductId() != null && !bean.getProductId().isEmpty()){
			Map<String, String> productCache = MediaInfoCache.getInstance().getProductCache();
			// 查询所有未回看的产品，
			String lookbackType = productCache.get(bean.getProductId());
			// 设置返回的参数
			typeMap.put(ConstantsHelper.LOOKBACK_TYPE, lookbackType);
			// 如果请求的该产品为回看，则lookbackType不为空
			if(lookbackType != null && ConstantsHelper.HIGH_DEFINITION_VIDEO.equals(deviceType)){
				array = ConstantsAdsCode.HD_LOOKBACK_ADVERT_POSITION;
				getCacheWithSite(map, lstCache, array, bean.getTvnId());
				return typeMap;
			}else if(lookbackType != null && ConstantsHelper.STANDARD_DEFINITION_VIDEO.equals(deviceType)){
				array = ConstantsAdsCode.SD_LOOKBACK_ADVERT_POSITION;
				getCacheWithSite(map, lstCache, array, bean.getTvnId());
				return typeMap;
			}
			
		}
		
		// 判断是否为回放类型，并设置广告位,区别高标清
		if(asset != null && !asset.isEmpty() && asset.length() >= 3){
			String assetKeywords = asset.substring(0,3);
			Map<String, String> channelCache = MediaInfoCache.getInstance().getChannelCahce();
			String serviceId = channelCache.get(assetKeywords);
			
			typeMap.put(ConstantsHelper.PLAYBACK_TYPE_FALG, serviceId);
			if(serviceId != null && ConstantsHelper.HIGH_DEFINITION_VIDEO.equals(deviceType)){
				array = ConstantsAdsCode.HD_PLAYBACK_ADVERT_POSITION;
				getCacheWithSite(map, lstCache, array, bean.getTvnId());
				return typeMap;
			}
			
		}
		
		// 因为直接传的是点击资源请求，没有传广告位ID，所以需要判断是频道还是影片
		// 并将频道或者影片会对应到得广告位的所有的播出单找出来
		// 如果用户已经填写过问卷，就不会再返回问卷类型的广告了
		// 默认为高清
		if(flag){
			if(ConstantsHelper.STANDARD_DEFINITION_VIDEO.equals(deviceType)){
				array = ConstantsAdsCode.SD_ASSET_ADVERT_POSITION_NO_Q;
			} else {
				array = ConstantsAdsCode.HD_ASSET_ADVERT_POSITION_NO_Q;
			}
			getCacheWithSite(map, lstCache, array, bean.getTvnId());
			return typeMap;
		}else{
			if(ConstantsHelper.STANDARD_DEFINITION_VIDEO.equals(deviceType)){
				array = ConstantsAdsCode.SD_ASSET_ADVERT_POSITION;
			} else {
				array = ConstantsAdsCode.HD_ASSET_ADVERT_POSITION;
			}
			getCacheWithSite(map, lstCache, array, bean.getTvnId());
			return typeMap;
		}
		
	}

	/**
	 * 根据广告位获取到对应的缓存
	 * @param map
	 * @param lstCache
	 * @param array
	 */
	private void getCacheWithSite(Map<String, List<PlaylistCacheModel>> map,
			List<PlaylistCacheModel> lstCache, String[] array, String tvn) {
		
		// 添加禁播广告位处理  使用查询数据库方式
		List<String> list = aDSurveyDAO.getBanAdvertising(tvn, new java.sql.Date(System.currentTimeMillis()));
		// 添加请求类别对应的广告位
		for (int i = 0; i < array.length; i++) {
			if(map.get(array[i]) != null){
				// 这样会减少算法长度，不用每次去遍历整个循环
				if(list.contains(array[i])){
					continue;
				}
				lstCache.addAll(map.get(array[i]));
			}
		}
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
	 * 获取用户喜爱的广告类型
	 * 
	 * @param tvnId 用户唯一编码
	 * @return 用户喜爱广告类型的列表
	 */
	private boolean userBeSurvey(String tvnId) {
		boolean flag =aDSurveyDAO.userBeSurvey(tvnId);
		return flag;
	}
	
//	/**
//	 * 根据tvnid获取用户的区域行业级别，用户级别等属性
//	 * 
//	 * @param tvnId 用户唯一编码
//	 * @return 用户喜爱广告类型的列表
//	 */
//	private UserInfoSync getLocationByTvnId(String tvnId) {
//		UserInfoSync user = new UserInfoSync();
//		return user;
//	}

//	/**
//	 * 根据产品ID获取该产品的回看类型
//	 * @param productId
//	 * @return String 回看类型
//	 */
//	private String getLookBackTypeByProductId(String productId) {
//		return "";
//	}
//	
	/**
	 * 判断用户是否被允许播放广告
	 * 
	 * @param 用户的标识
	 * @return 是否被允许 true:允许，false:不允许
	 */
	private boolean userBePermitShowAD(String tvnId) {
		boolean enable = aDSurveyDAO.userBePermitShowAD(tvnId);
		return enable;
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
				if(sTvns[i].equals(tvn)){
					break;
				}
			}
		}
		
		return done;
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
			List<TempObjectStorgePlaylistContent> listObj = aDSurveyDAO.getHistoryByToken(bean.getToken());
			if(listObj == null || listObj.size() == 0){
				return success;
			}
			int count = 0;
			for (TempObjectStorgePlaylistContent obj : listObj) {
				
				String position = obj.getAdSiteCode();
				
				if(position == null || position.isEmpty()){
					continue;
				}
				// 如果是插播类（回看回放插播或者点播插播）
				if(position.indexOf(ConstantsAdsCode.LOOKBACK_ADVERT_POSITION_INSERT) >= 0
						|| position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_INSERT) >= 0){
					List<AdStatus> list = bean.getAdReportList().getAdStatus();
					
					for (AdStatus adStatus : list) {
						if(obj.getSeq() != null && obj.getSeq().equals(adStatus.getSeq())){
							obj.setState(adStatus.getStatus());
							if("1".equals(adStatus.getStatus())){
								count++;
							}
							break;
						}
					}
				}
				//如果是暂停类的广告
				else if(position.indexOf(ConstantsAdsCode.LOOKBACK_ADVERT_POSITION_PAUSE) >= 0
						|| position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_PAUSE) >= 0){
					obj.setState(bean.getPausePic());
					if("1".equals(bean.getPausePic())){
						count++;
					}
				}
				//如果是挂角类的广告
				else if(position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_TOPRIGHT) >= 0){
					obj.setState(bean.getRightTopPicStatus());
					if("1".equals(bean.getPausePic())){
						count++;
					}
				}
				//如果是字幕类的广告
				else if(position.indexOf(ConstantsAdsCode.ASSET_ADVERT_POSITION_SUBTITLE) >= 0){
					obj.setState(bean.getSubTitleStaus());
					if("1".equals(bean.getPausePic())){
						count++;
					}
				}
			}
			if(listObj != null){
				// 将修改了投放状态的投放记录保存
				aDSurveyDAO.SavePlaylistIntoHistoryReport(listObj);
				int orderId = listObj.get(0).getOrderId();
				// 修改订单中的已播放次数
				aDSurveyDAO.updatePlaylistPutInTimes(orderId, count);
			}
			
			success = true;
		} catch (Exception e) {
			logger.error("更新投放历史记录信息时失败 \n"+e.getMessage());
			return success;
		}
		return success;
	}
	
	/**
	 * 判断关键字是否命中精准条件, 重载方法
	 * @param info
	 * @param key
	 * @return true 命中精准条件， false 没有命中精准条件
	 */
	private boolean checkKeywords(AssetInfo info, String key){
		boolean validation = false;
		String keys = "";
		// 暂时将assetName当做关键字处理
		String assetName = info.getAssetName();
		if(info != null && info.getAssetDescription() != null && info.getAssetDescription().size() > 0){
			
			List<String> lstKeywrods = info.getAssetDescription();
			
			for (String keyWords : lstKeywrods) {
				keys += keyWords + ",";
			}
		} 
		keys = keys + assetName;
		if(keys.indexOf(key)>= 0){
			validation = true;
		}
		return validation;
	}
	
	/**
	 * 判断关键字是否命中精准条件, 重载方法
	 * @param info
	 * @param key
	 * @return true 命中精准条件， false 没有命中精准条件
	 */
	@SuppressWarnings("unused")
	private boolean checkPrecision(String precision, List<String> list){
		boolean flag = false;
		for (String key : list) {
			flag = checkPrecision(precision, key);
			// 使用影片类型的精准去循环匹配，如果能匹配上，说明精准已经匹配，跳出循环。
			if(flag){
				break;
			}
		}
		return flag;
	}
	

	/**
	 * 
	 * 匹配非关键字的精准条件
	 * @param 精准的属性值
	 * @param 请求的参数
	 * @return
	 */
	private boolean checkPrecision(String arg1, String arg2){
		boolean flag = false;
		if(arg1 == null || arg2 == null || arg1.isEmpty() || arg2.isEmpty()){
			return flag;
		}
		int index1 = arg1.indexOf(arg2);
		if(index1 > 0){
			int indexMid = arg1.indexOf(","+arg2+",");
			int indexEnd = arg1.indexOf(","+arg2);
			if(indexMid >0 || (indexEnd > 0 && indexEnd +arg2.length() == arg1.length() -1)){
				flag = true;
			}
		} else if(index1 == 0){
			int indexFirst = arg1.indexOf(arg2+",");
			if(indexFirst == 0){
				flag = true;
			}
		}
		
		return flag;
	}

	/**
	 * 保存问卷提交数据
	 * @param 精准的属性值
	 * @param 请求的参数
	 */
	public boolean saveSurveySubmit(List<Question> list, AdQuestionnaireData data) {
		try {
			aDSurveyDAO.saveSurveySubmit(data);
			long id = data.getId();
			for (Question question : list) {
				question.setParentId(id);
			}
			aDSurveyDAO.SaveSurveyReport(list);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 填写问卷后，使用喜好问卷填写的内容重新匹配用户喜好的广告 
	 *
	 * @param 喜好问卷填写的喜好内容
	 * @param 广告插入请求的XML
	 * 
	 * @return 广告插入响应的对象，包含的各种广告的内容 
	 */
	
	public AdContent matchFavority(String answers, String xml){
		
		JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
		AdInsertRequestXmlBean bean = null;
		try {
			bean = (AdInsertRequestXmlBean) helper.fromXML(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> lstKeywords = bean.getAssetInfo().getAssetDescription();
		// 将用户以前设置的关键字清除掉，然后使用问卷的喜好作为关键字重新做匹配
		lstKeywords.clear();
		lstKeywords.add(answers);
		// 从cache中取获取用户匹配的广告
		AdContent adContent = getDataFromCache(bean, true);
		return adContent;
		
	}
}
