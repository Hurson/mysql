package com.avit.ads.requestads.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.avit.ads.requestads.bean.Content;
import com.avit.ads.requestads.bean.PlaylistCacheModel;
import com.avit.ads.requestads.bean.PrecisionCacheModel;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.bean.request.AdInsertRequestXmlBean;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.bean.request.AdStatusReportReqXmlBean;
import com.avit.ads.requestads.bean.request.AssetInfo;
import com.avit.ads.requestads.bean.request.UserSurveyResult;
import com.avit.ads.requestads.bean.response.AdInsertRespContent;
import com.avit.ads.requestads.bean.response.AdInsertResponsetPlaylistXmlBean;
import com.avit.ads.requestads.bean.response.InsertedAd;
import com.avit.ads.requestads.bean.response.InsertedAdList;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.service.ADPlayListService;
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
	static Logger logger = LoggerFactory.getLogger(ADPlayListServiceImp.class);

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
		List<TempObjectStorgePlaylistContent> lstResponse = null;
		
		try {
			JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
			AdInsertRequestXmlBean bean = (AdInsertRequestXmlBean) helper.fromXML(xml);
			if(bean.getChannelId().isEmpty()){
				logger.info("接收到，区域："+bean.getLocationId()+"，用户："+bean.getTvnId()+"发起的影片："+bean.getAssetId()+"请求");
			} else {
				logger.info("接收到，区域："+bean.getLocationId()+"，用户："+bean.getTvnId()+"发起的频道："+bean.getChannelId()+"请求");
			}
			
			// 判断用户是否允许播放广告
//			String tvnId = bean.getTvnId();
//			boolean enable = userBePermitShowAD(tvnId);
//			if(!enable){
//				return "";
//			}
			// 如果用户进行过问卷调查，则会有用户喜爱的广告类型，则该类型为最优先的广告
//			List<String> lstType = getUserFavType(tvnId);
			// TODO 将该类型的打包返回
			// 从cache中获取数据
			lstResponse = getDataFromCache(bean);
			if(lstResponse == null || lstResponse.size() == 0){
				return "";
			}
			
//			// 当从cache中找不到数据时，从数据库中获取播出单（几率非常小，方法暂时放在此处）
//			if(lstResponse == null || lstResponse.size() == 0){
//				lstResponse = getDataFromDatabase(bean);
//			}
			// 将得到的临时对象中的合同编号，广告位编码，用户编码，播出单号，内容信息保存入广告投放历史信息表中
			lstResponse = SavePlaylistIntoHistoryReport(lstResponse);
			// 将加工得到的播出单转换成XML返回, 
			responseXml = ProcessPlayListToXml(lstResponse, bean);
		
			
			
			
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
	 * 从cache中获取数据,先从播出单中过滤，当从播出单中找到内容时，再使用精准过滤出播出内容，
	 * @param bean 请求Xml实体对象
	 * @return List<TempObjectStorgePlaylistContent> 主要用于存放广告内容和保存记录所需要的对象集合
	 */
	private List<TempObjectStorgePlaylistContent> getDataFromCache(AdInsertRequestXmlBean bean) {
		Map<String, List<PlaylistCacheModel>> map = PlaylistCache.getInstance().getCache();
		String channel = bean.getChannelId();
		String asset = bean.getAssetId();
		List<PlaylistCacheModel> lstCache = new ArrayList<PlaylistCacheModel>();
		
		// 因为直接传的是点击资源请求，没有传广告位ID，所以需要判断是频道还是影片
		// 并将频道或者影片会对应到得广告位的所有的播出单找出来
		if(!channel.isEmpty() && channel != null){
			String[] array =ConstantsAdsCode.CHANNEL_AD_CODE;
			for (int i = 0; i < array.length; i++) {
				if(map.get(array[i]) != null){
					lstCache.addAll(map.get(array[i]));
				}
			}
		} else if (!asset.isEmpty() && asset != null) {
			String[] array =ConstantsAdsCode.SEED_PLAY_AD_CODE;
			for (int i = 0; i < array.length; i++) {
				if(map.get(array[i]) != null){
					lstCache.addAll(map.get(array[i]));
				}
				
			}
		} else {
			return null;
		}
		// 用于临时存储过滤好的播出单playlist的数组，返回的该对象如果没有精准属性命中的话，会转换成gateway需要的XML并返回
		List<TempObjectStorgePlaylistContent> lstResonse = new ArrayList<TempObjectStorgePlaylistContent>();
		// 获取当前时间的秒数，用于确认当前应该播放的播出单
		long time = System.currentTimeMillis()/1000;
		
		// 遍历cache,使用过滤条件去过滤
		for (PlaylistCacheModel playlistCacheModel : lstCache) {
			long begin = playlistCacheModel.getBegin();
			long end = playlistCacheModel.getEnd();
			long id = playlistCacheModel.getId();
			String trade =playlistCacheModel.getTrade();
			String userLev = playlistCacheModel.getUserLevel();
			String code = playlistCacheModel.getAdSiteCode();
			
			// 如果是字幕广告位，则需要比较用户行业和用户级别
			if(ConstantsAdsCode.SUBTITLE_CODE.equals(code)){
				// 如果字幕广告的行业类别和用户级别不满足要求，则排除
				if((bean.getUserType() == 0 || !checkPrecision(userLev, bean.getUserType()+"")) 
						&& (bean.getUserType2() == null || !checkPrecision(trade, bean.getUserType2()))){
					continue;
				}
			} else {
				// TODO 因为暂定字幕广告的特征值为空，所以高标清特征值匹配在此判断
				// 判断请求的视频清晰度,
				String definition = bean.getDefinition();
				// 请求为高清，必须返回高清；请求为标清，必须返回标清
				if(playlistCacheModel.getAnthoring() == null || playlistCacheModel.getAnthoring().isEmpty()){
					continue;
				}
				if((ConstantsHelper.HIGH_DEFINITION_VIDEO.equals(definition) 
							&& playlistCacheModel.getAnthoring().indexOf(ConstantsHelper.HIGH_DEFINITION_VIDEO) < 0)
						|| (ConstantsHelper.STANDARD_DEFINITION_VIDEO.equals(definition) 
							&& playlistCacheModel.getAnthoring().indexOf(ConstantsHelper.STANDARD_DEFINITION_VIDEO) < 0)){
					continue;
				}
			}
			
			
			
			// 拼凑命中map所需要的key   key = 播出单ID+频道+区域, 如果是请求的影片，频道ID可能为空，为了不进入循环中判断，将channelId设置为"0"
			String key = id +":" + ((bean.getChannelId()== null||bean.getChannelId().isEmpty())?"0": bean.getChannelId() + ":" + bean.getLocationId());
			if(begin <= time && end >= time ){
				Map<String, String> contentMap = playlistCacheModel.getMap();
				String content = contentMap.get(key);
				boolean loop = false;
				// TODO 此处需要考虑优化
				int count = 0;
				while(content == null && !loop){
					// 定义三种全区域的key，构造成数组的结构
					
					String allChannel = id +":" + "0:"+bean.getLocationId();
					String allLocation = id +":" + bean.getChannelId() + ":0";
					String both = id +":" + "0:0";
					String[] keys = {allChannel, allLocation, both};
					
					content = contentMap.get(keys[count]);
					if(count == 2){
						loop = true;
					}
					count++;
				}
				// 如果所有的区域都比较过后都没有匹配的，则该播出单不符合条件，进入下一个循环
				if(content == null){
					continue;
				}  // 如果不为空则命中
					
				// 随机获取一个内容
				int s =  (int)(Math.random()*100000 )%playlistCacheModel.getLstContents().size();
				Content obj= playlistCacheModel.getLstContents().get(s);
				TempObjectStorgePlaylistContent tempObj = new TempObjectStorgePlaylistContent();
				tempObj.setId(playlistCacheModel.getId());
				tempObj.setContractId(playlistCacheModel.getContractId());
				tempObj.setAnthoring(playlistCacheModel.getAnthoring());
				tempObj.setAdSiteCode(playlistCacheModel.getAdSiteCode());
				tempObj.setUserCode(bean.getTvnId());
				tempObj.setContentId(obj.getContentId());
				tempObj.setContentType(obj.getContentType());
				tempObj.setContent(obj.getContent());
				tempObj.setContentPath(obj.getContentPath());
				tempObj.setState(ConstantsHelper.PLAYED_SUCCESSFULLY);
				tempObj.setDateTime(new Date(System.currentTimeMillis()));
				
				// 先从普通的过滤条件过滤后再从精准过滤条件中过滤。
				PrecisionCacheModel tmpMax = null;
				int tmpMaxUseLev = 0;
				List<PrecisionCacheModel> lstPrecision = playlistCacheModel.getLstPrecision();
				for (int i = 0, j = lstPrecision.size(); i < j; i++) {
					PrecisionCacheModel objPrecision= lstPrecision.get(i);
					switch (objPrecision.getType()) {
					
					// 类型为产品。
					case ConstantsHelper.PRECISION_TYPE_PRODUCTION:
						if(bean.getProductId() != null 
								&& checkPrecision(objPrecision.getProductCode(), bean.getProductId()) 
								&& objPrecision.getUseLevel() > tmpMaxUseLev){
							tmpMax = objPrecision;
							tmpMaxUseLev = objPrecision.getUseLevel();
						}
						break;
					// 类型为影片元数据关键字
					case ConstantsHelper.PRECISION_TYPE_MOVES_KEYWORDS:
						if(checkKeywords(bean.getAssetInfo(), objPrecision.getKey())
								&& objPrecision.getUseLevel() > tmpMaxUseLev){
							tmpMax = objPrecision;
							tmpMaxUseLev = objPrecision.getUseLevel();
						}
						break;
					// 类型为受众
					case ConstantsHelper.PRECISION_TYPE_USER_SORT:
						if((bean.getTvnId() != null && checkPrecision(objPrecision.getTvnNumber(), bean.getTvnId()))
								|| (bean.getLocationId() != null && checkPrecision(objPrecision.getUserArea(), bean.getLocationId())) 
								|| (bean.getUserType2() != null && checkPrecision(objPrecision.getUserindustrys(), bean.getUserType2())) 
								|| (bean.getUserType() > 0 && checkPrecision(objPrecision.getUserlevels(), bean.getUserType()+""))
								&& objPrecision.getUseLevel() > tmpMaxUseLev){
							tmpMax = objPrecision;
							tmpMaxUseLev = objPrecision.getUseLevel();
						}
						break;
					// 类型为影片分类
					case ConstantsHelper.PRECISION_TYPE_MOVES_SORT:
						if((bean.getAssetId() != null && checkPrecision(objPrecision.getUserArea(), bean.getAssetId())) 
								&& objPrecision.getUseLevel() > tmpMaxUseLev){
							tmpMax = objPrecision;
							tmpMaxUseLev = objPrecision.getUseLevel();
						}
						break;
					// 类型为回看频道
					case ConstantsHelper.PRECISION_TYPE_PASSED_CHANNEL:
						if((bean.getChannelId() != null && checkPrecision(objPrecision.getBtvChannelId(), bean.getChannelId()))
								&& objPrecision.getUseLevel() > tmpMaxUseLev){
							tmpMax = objPrecision;
							tmpMaxUseLev = objPrecision.getUseLevel();
						}
						break;
						
					default:
						break;
					}
					// 如果没有精准内容命中，则返回普通过滤，如果有，则返回精准过滤
					if(tmpMax != null){
						int x =  (int)(Math.random()*100000 )%tmpMax.getLstPreContent().size();
						Content objContent= tmpMax.getLstPreContent().get(x);
						tempObj.setContentId(objContent.getContentId());
						tempObj.setContentType(objContent.getContentType());
						tempObj.setContent(objContent.getContent());
					} 
				}
				lstResonse.add(tempObj);
			}
		}
		return lstResonse;
	}

//	/**
//	 * @unused
//	 * 当从cache中找不到数据时，从数据库中获取播出单（几率非常小，方法暂时放在此处）
//	 * @param bean
//	 * @return
//	 */
//	private List<InsertedAd> getDataFromDatabase(AdInsertRequestXmlBean bean) {
//		List<AdPlaylistReq> tmpList = new ArrayList<AdPlaylistReq>();
//		List<AdPlaylistReq> lstAdPlaylistReq = null;
//		
//		
//		String strDate = DateUtil.getCurrentDateStr1();
//		String strTime = DateUtil.getCurrentTimeStr1();
//		String strLocationId = bean.getLocationId();
//		String strChannelId = bean.getChannelId();
//		List<InsertedAd> lstResponse = null;
//		if("0".equals(strLocationId)){
//			// 使用区域信息过滤出策略
//			lstAdPlaylistReq = aDSurveyDAO.getPlaylistByLocationId(strLocationId, strDate, strTime, strChannelId);
//			// 使用locationId和channelId并集关联得到真正的请求的OBJ
//			// TODO split and filter
//			for (AdPlaylistReq adPlaylistReq : lstAdPlaylistReq) {
//				
//				// 拆分区域编码得到对应的数组
//				String strArea = adPlaylistReq.getAreas();
//				
//				// 区域的集合的结构为 001#002#003， 将之拆分成数组
//				String[] locationIds = strArea.split(ConstantsHelper.AREA_SPLIT__SIGN);
//				
//				// 频道的集合结构为 ["001,002","005,007","007,005,002,006"]，使用json格式对其拆分
//				// 且区域和频道的集合的长度必须相等
//				String strChannel = adPlaylistReq.getChannels();
//				JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(strChannel);
//				List<?> lstChannel = (List<?>) JSONSerializer.toJava(jsonArray);
//				
//				// 如果区域或者频道为空，或者是他们两个的长度不相等，则数据有错
//				if(locationIds == null ||lstChannel == null || lstChannel.size() != locationIds.length ){
//					//TODO 如果区域或者频道为空，或者是他们两个的长度不相等，则数据有错
//					return null;
//				}
//				
//				// 循环匹配对应index中的locationId和channelId都能匹配上的播出单
//				for (int i = 0,  j = locationIds.length; i < j; i++) {
//					String Channels =  lstChannel.get(i).toString();
//					String areaCode = locationIds[i];
//					if(strLocationId.equals(areaCode) && Channels.indexOf(strChannel) > 0){
//						// 填充数据
//						// tmpList.add(adPlaylistReq);
//					}
//				}
//				lstAdPlaylistReq = tmpList;
//			}
//			
//		} else if("0".equals(strLocationId)){
//			// 如果locationId = 0 表示所有的区域，则只使用频道作为过滤条件
//			lstAdPlaylistReq = aDSurveyDAO.getPlaylistByChannelId(strChannelId, strDate, strTime);
//		}
//		// 判断是否包含精准信息
//		PrecisionFilter filter = hasPrecisionFilter(bean);
//		if(filter != null){
//			// 添加精准过滤,得到待插入广告列表
//			lstResponse = InvokePrecisionFilter(bean, lstAdPlaylistReq);
//		}
//		// 将加工得到的播出单保存入广告投放记录表
////		boolean success = SavePlaylistIntoHistoryReport(lstAdPlaylistReq);
//		return lstResponse;
//	}
	
	/**
	 * 将加工得到的播出单保存入广告投放记录表
	 * 
	 * @param obj 播出单列表
	 * @return 是否保存成功 true:保存成功，false:保存失败
	 */
	private List<TempObjectStorgePlaylistContent> SavePlaylistIntoHistoryReport(List<TempObjectStorgePlaylistContent> list) {
		list = aDSurveyDAO.SavePlaylistIntoHistoryReport(list);	
		
		logger.info("已记录响应的播出单");
		return list;
	}

	/**
	 * 获取用户喜爱的广告类型
	 * 
	 * @param tvnId 用户唯一编码
	 * @return 用户喜爱广告类型的列表
	 */
	private List<String> getUserFavType(String tvnId) {
		List<String> list =aDSurveyDAO.getUserFavType(tvnId);
		return list;
	}

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
	 * 
	 * 保存跟随请求单一起传入的问卷调查记录.
	 *
	 * @param List<UserSurveyResult> 问卷调查列表
	 */
	private void SaveSurveyReport(List<UserSurveyResult> list) {
		aDSurveyDAO.SaveSurveyReport(list);
	}

	/**
	 * 将播出单的列表处理成XML.
	 *
	 * @param List<AdPlaylistReq> 播出单列表
	 * @return String  response XML
	 */
	private String ProcessPlayListToXml(List<TempObjectStorgePlaylistContent> lstPlayList, AdInsertRequestXmlBean bean) {
		JaxbXmlObjectConvertor helper = JaxbXmlObjectConvertor.getInstance();
		// 封装返回的bean
		StringBuffer sb = new StringBuffer(256);
		List<InsertedAd> list = new ArrayList<InsertedAd>();
		for (TempObjectStorgePlaylistContent  PlaylistContent: lstPlayList) {
			InsertedAd insertedAd = new InsertedAd();
			String code = PlaylistContent.getAdSiteCode();
			if(ConstantsAdsCode.SUBTITLE_CODE.equals(code)){
				insertedAd.setAdText(PlaylistContent.getContent());
			}else {
				insertedAd.setAdUrl(PlaylistContent.getContent());
			}
			String type = PlaylistContent.getContentType();
			if(ConstantsHelper.SIGN_CONTENT_TYPE.equals(type) || type.isEmpty()){
				// 设置资源类型，如果是视频则为5，图片为4
				if(PlaylistContent.getContent().indexOf(ConstantsHelper.MP4_FILE_POSTFIX) >= 0){
					insertedAd.setAdType(ConstantsHelper.RESPONSE_CONTENT_TYPE_VIDEO);
				} else {
					insertedAd.setAdType(ConstantsHelper.RESPONSE_CONTENT_TYPE_PICTURE);
				}
			} else {
				insertedAd.setAdType(type);
			}
			insertedAd.setAdCode(PlaylistContent.getAdSiteCode());
			insertedAd.setAdSeq(PlaylistContent.getAdContentSeq()+"");
			String anthoring = PlaylistContent.getAnthoring();
			// TODO 去掉HD SD标识，但是结构未定
//			if(anthoring != null && !anthoring.isEmpty()){
//				anthoring = PlaylistContent.getAnthoring().substring(2,PlaylistContent.getAnthoring().length());
//			}
			insertedAd.setInsertedTime(anthoring);
			sb.append("响应的广告位：").append(code)
			.append("，播出单为：").append(PlaylistContent.getId())
			.append("，插播位置为：").append(PlaylistContent.getAnthoring())
			.append("，广告内容为：").append(PlaylistContent.getContent()).append("\n");
			list.add(insertedAd);
		}
		InsertedAdList obj = new InsertedAdList();
		obj.setLstInsertedAd(list);
		
		AdInsertRespContent content = new AdInsertRespContent();
		content.setInsertedAdList(obj);
		content.setToken(bean.getToken());
		content.setTvnId(bean.getTvnId());
		final int SUCCESS_CODE = 0;
		if(sb.length() > 0){
			logger.info(sb.toString());
		} else {
			logger.info("没有匹配到广告内容");
		}
		AdInsertResponsetPlaylistXmlBean responseBean = new AdInsertResponsetPlaylistXmlBean(SUCCESS_CODE, content);
		String responseXml = helper.toXML(responseBean);
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
			List<AdStatus> list = bean.getAdReportList().getAdStatus();
			success = aDSurveyDAO.SaveStatusReport(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return success;
		}
		return success;
	}
	
	/**
	 * 判断关键字是否命中精准条件
	 * @param info
	 * @param key
	 * @return
	 */
	private boolean checkKeywords(AssetInfo info, String key){
		boolean validation = false;
		if(info != null && info.getAssetDescription() != null && info.getAssetDescription().size() > 0){
			List<String> lstKeywrods = info.getAssetDescription();
			for (String keyWords : lstKeywrods) {
				if(key.indexOf(keyWords) > 0){
					validation = true;
					break;
				}
			}
		}
		return validation;
	}

	/**
	 * 匹配非关键字的精准条件
	 * @param arg1
	 * @param arg2
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
}
