package com.avit.ads.requestads.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.avit.ads.requestads.bean.AdPlaylistReq;
import com.avit.ads.requestads.bean.AdPlaylistReqContent;
import com.avit.ads.requestads.bean.AdPlaylistReqPContent;
import com.avit.ads.requestads.bean.AdPlaylistReqPrecision;
import com.avit.ads.requestads.bean.DefaultResource;
import com.avit.ads.requestads.bean.PlaylistCacheModel;
import com.avit.ads.requestads.bean.PrecisionCacheModel;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.bean.cache.ChannelInfoCache;
import com.avit.ads.requestads.bean.cache.ProductionCache;
import com.avit.ads.requestads.cache.AreaPrecisionCache;
import com.avit.ads.requestads.cache.AssetCache;
import com.avit.ads.requestads.cache.AssetCategoryPrecisionCache;
import com.avit.ads.requestads.cache.AssetKeyPrecisionCache;
import com.avit.ads.requestads.cache.AssetNamePrecisionCache;
import com.avit.ads.requestads.cache.CategoryCache;
import com.avit.ads.requestads.cache.IndustryPrecisionCache;
import com.avit.ads.requestads.cache.LevelPrecisionCache;
import com.avit.ads.requestads.cache.LocationCache;
import com.avit.ads.requestads.cache.LocationComparator;
import com.avit.ads.requestads.cache.NpvrCache;
import com.avit.ads.requestads.cache.NpvrPrecisionCache;
import com.avit.ads.requestads.cache.OrderCache;
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
import com.avit.ads.requestads.cache.bean.TCategoryinfo;
import com.avit.ads.requestads.cache.bean.TLocationCode;
import com.avit.ads.requestads.cache.bean.TNoAdPloyView;
import com.avit.ads.requestads.cache.bean.TOrder;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.service.DefaultResourceCache;
import com.avit.ads.requestads.service.RefreshCacheService;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.DateUtil;
import com.avit.ads.util.InitConfig;
import com.avit.common.util.StringUtil;
@Repository("RefreshCacheService")
public class RefreshCacheServiceImpl implements RefreshCacheService {
	@Inject
	ADSurveyDAO aDSurveyDAO ;//= (ADSurveyDAOImp) ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
	private static Logger logger = LoggerFactory.getLogger(RefreshCacheServiceImpl.class);
	public static List<TLocationCode> listLocation ;	
	public void refreshPlayListCache() {
		// TODO Auto-generated method stub
		PlaylistCacheModel objPlaylistCacheModel = null;
		try {
			// 查询当天某段时间（暂定0点到24点）的播出单
			List<AdPlaylistReq> lstAdPlaylistReq = aDSurveyDAO.getPlaylistByTime(
					ConstantsHelper.CACHE_DIVIDE_BEGIN, 
					ConstantsHelper.CACHE_DIVIDE_END, 
					new Date(System.currentTimeMillis()));
		
			List<String> lstAdCodeTmp = new ArrayList<String>();
			for (AdPlaylistReq adPlaylistReq : lstAdPlaylistReq) {
				// 是否添加播出单的标志位
				boolean addPlaylist = true ;
				long id = adPlaylistReq.getId();
				// 将开始和结束时间设置为整型为了方便计算。
				String begin = adPlaylistReq.getBegin();
				String end = adPlaylistReq.getEnd();
				java.util.Date beginDate = DateUtil.toDate(DateUtil.getCurrentDateStr1()+" "+begin);
				java.util.Date endDate = DateUtil.toDate(DateUtil.getCurrentDateStr1()+" "+end);
				long lBegin = beginDate.getTime()/1000;
				long lEnd = endDate.getTime()/1000;
				
				String strLocationIds = adPlaylistReq.getAreas();
				// 区域的集合的结构为 001#002#003， 将之拆分成数组
				String[] locationIds = strLocationIds.split(ConstantsHelper.AREA_SPLIT__SIGN);
				
				Map<String, String> map = new HashMap<String, String>();
				List<PlaylistCacheModel> list = new ArrayList<PlaylistCacheModel>();
				//只处理区域ID 
				for (int i = 0; i < locationIds.length; i++) {
					String key = locationIds[i];
					map.put(key, id+"");
				}
				
				// 设置缓存的播出单信息
				objPlaylistCacheModel = new PlaylistCacheModel();
				objPlaylistCacheModel.setBegin(lBegin);
				objPlaylistCacheModel.setEnd(lEnd);
				objPlaylistCacheModel.setMap(map);
				objPlaylistCacheModel.setId(adPlaylistReq.getId());
				objPlaylistCacheModel.setAnthoring(adPlaylistReq.getCharacteristicIdentification());
				objPlaylistCacheModel.setTrade(adPlaylistReq.getUserindustrys());
				objPlaylistCacheModel.setUserLevel(adPlaylistReq.getUserlevels());
				objPlaylistCacheModel.setAdSiteCode(adPlaylistReq.getAdSiteCode());
				objPlaylistCacheModel.setOrderId(adPlaylistReq.getOrderId());
				objPlaylistCacheModel.setPlayTimes(adPlaylistReq.getPlayTime());
				AdPlaylistReqContent cont = setContentPath(adPlaylistReq);
				
				objPlaylistCacheModel.setContent(cont);
				
				/*解析精准的信息，主要将Set变为list，因为list的索引是数据库表中的索引，如果数据库表中的索引不是
				连续的，那么在得到的list中的索引也不是连续的，所以要使用set接收，然后用set转为list方便直接在cache命中指定内容*/
				
				// 使用set接收的是无序的对象集合，如果使用list接收，需要设置排序条件（order by）才能清除掉list中的为null值的索引位
				// 精准是按照use_level的倒序排列，用于优化匹配精准算法
				List<PrecisionCacheModel> lstPrecision = new ArrayList<PrecisionCacheModel>();
				List<AdPlaylistReqPrecision> lstP = adPlaylistReq.getColPrecisions();
				for (Iterator<AdPlaylistReqPrecision> iterator = lstP.iterator(); iterator.hasNext();) {
					// 判断是否添加该精准的标识位
					AdPlaylistReqPrecision adPlaylistReqPrecision = iterator.next();
					PrecisionCacheModel pCahce = new PrecisionCacheModel();
					
					// 拷贝对象属性
					pCahce.setId(adPlaylistReqPrecision.getId());
					pCahce.setType(adPlaylistReqPrecision.getType());
					pCahce.setUseLevel(adPlaylistReqPrecision.getUseLevel());
//					pCahce.setUserArea(adPlaylistReqPrecision.getUserArea());
//					pCahce.setUserindustrys(adPlaylistReqPrecision.getUserindustrys());
//					pCahce.setTvnNumber(adPlaylistReqPrecision.getTvnNumber());
//					pCahce.setTvnExpression(adPlaylistReqPrecision.getTvnExpression());
//					pCahce.setProductCode(adPlaylistReqPrecision.getProductCode());
//					pCahce.setType(adPlaylistReqPrecision.getType());
//					pCahce.setBtvChannelId(adPlaylistReqPrecision.getDtvServiceId());
//					pCahce.setAssetSortId(adPlaylistReqPrecision.getAssetSortId());
//					pCahce.setKey(adPlaylistReqPrecision.getKey());
//					pCahce.setUseLevel(adPlaylistReqPrecision.getUseLevel());
//					pCahce.setId(adPlaylistReqPrecision.getId());
//					pCahce.setAssetId(adPlaylistReqPrecision.getAssetId());
//					pCahce.setPlaybackServiceId(adPlaylistReqPrecision.getPlaybackServiceId());
//					pCahce.setLookbackCategoryId(adPlaylistReqPrecision.getLookbackCategoryId());
					
					AdPlaylistReqPContent contp = setPresionContent(adPlaylistReq, adPlaylistReqPrecision); 
					pCahce.setPreContent(contp);
					//调整所有广告位 非叠加属性 
					List<ContentCacheBean> listContent =  new ArrayList<ContentCacheBean>();
					if (contp!=null)
					{
						ContentCacheBean contentTemp = new ContentCacheBean();
						contentTemp.setContentPath(contp.getContentPath());
						contentTemp.setResourcid(contp.getContentId());
						contentTemp.setTvns(adPlaylistReqPrecision.getTvnNumber());
						contentTemp.setExpression(adPlaylistReqPrecision.getTvnExpression());
						listContent.add(contentTemp);
					}
					switch (adPlaylistReqPrecision.getType()) {
					
					// 类型为产品。
					case ConstantsHelper.PRECISION_TYPE_PRODUCTION:
						
						ProductPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getProductCode(), listContent);
					break;
					// 类型为影片元数据关键字
					case ConstantsHelper.PRECISION_TYPE_MOVES_KEYWORDS:
						AssetKeyPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getKey(), listContent);
					break;
					// 类型为用户级别
					case ConstantsHelper.PRECISION_TYPE_USER_LEVELS:
						LevelPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getUserlevels(), listContent);
					break;
					// 类型为行业级别
					case ConstantsHelper.PRECISION_TYPE_USER_TRADE:
						IndustryPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getUserindustrys(), listContent);
						
					break;
					
					// 类型为影片分类
					case ConstantsHelper.PRECISION_TYPE_MOVES_SORT:
						AssetCategoryPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getAssetSortId(), listContent);
						//循环处理子目录，增加至缓存 
						
					break;
					// 类型为回看栏目 
					case ConstantsHelper.PRECISION_TYPE_LOOKBACK_CHANNEL:
						//LevelPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getUserlevels(), listContent);
						
					break;
					// 类型为回放频道 
					case ConstantsHelper.PRECISION_TYPE_PLAYBACK_CHANNEL:
						NpvrPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getPlaybackServiceId(), listContent);
						
					break;
					// 类型为影片
					case ConstantsHelper.PRECISION_TYPE_PASSED_ASSET:
						AssetNamePrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getAssetId(), listContent);
						
					break;
					// 类型为用户区域
					case ConstantsHelper.PRECISION_TYPE_USER_LOCATION:
						AreaPrecisionCache.addMap(StringUtil.toNotNullStr(adPlaylistReqPrecision.getId()), adPlaylistReqPrecision.getUserArea(), listContent);
						
					break;
						
					default:
						break;
					}
					
					lstPrecision.add(pCahce);
				}
				objPlaylistCacheModel.setLstPrecision(lstPrecision);
				
				ReqPlayListCache.addMap(objPlaylistCacheModel);		
				
			}
			ReqPlayListCache.updateMap();
			NpvrPrecisionCache.updateMap();
			AreaPrecisionCache.updateMap();
			AssetNamePrecisionCache.updateMap();
			AssetCategoryPrecisionCache.updateMap();
			IndustryPrecisionCache.updateMap();
			LevelPrecisionCache.updateMap();
			AssetKeyPrecisionCache.updateMap();
			ProductPrecisionCache.updateMap();
			
		} catch (Exception e) {
			logger.error("执行更新cache时出错\n"+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 给素材路径加上http前缀
	 * @param adPlaylistReq
	 * @return
	 */
	private AdPlaylistReqContent setContentPath(AdPlaylistReq adPlaylistReq) {
		AdPlaylistReqContent cont = adPlaylistReq.getContents();
		if (cont==null)
		{
			return cont;
		}
		
		String type = cont.getContentType();
		// 如果为空则是单个素材，直接放入content对象中
		if("1".equals(type)||type == null){
			String contentPath = cont.getContentPath();
			if(contentPath != null && !contentPath.isEmpty()){
				// 如果有数据库字段中第一个字符是单斜线就直接添加，没有就加上一个单斜线
				String firstChar = contentPath.substring(0,1);
				if(!ConstantsHelper.OBLIQUE_LINE.equals(firstChar)){
					contentPath = ConstantsHelper.OBLIQUE_LINE + contentPath;
				}
				//cont.setContentPath(InitConfig.getContentPath(adPlaylistReq.getAdSiteCode())+contentPath);
			}
			
		}
		return cont;
	}
	/**
	 * 给精准的素材路径加上http前缀
	 * @param adPlaylistReq, AdPlaylistReqPrecision
	 * @return
	 */
	private AdPlaylistReqPContent setPresionContent(
			AdPlaylistReq adPlaylistReq,
			AdPlaylistReqPrecision adPlaylistReqPrecision) {
		AdPlaylistReqPContent contp = adPlaylistReqPrecision.getPreContent();
		
		String type =contp.getContentType();
		// 如果为空则是单个素材，直接放入content对象中，如果不为空则需要解析配置文件
		if("1".equals(type)||type == null){
			String pContentPath = contp.getContentPath();
			if(pContentPath != null && !pContentPath.isEmpty()){
				// 如果有数据库字段中第一个字符是单斜线就直接添加，没有就加上一个单斜线
				String firstChar = pContentPath.substring(0,1);
				if(!ConstantsHelper.OBLIQUE_LINE.equals(firstChar)){
					pContentPath = ConstantsHelper.OBLIQUE_LINE + pContentPath;
				}
				//contp.setContentPath(InitConfig.getContentPath(adPlaylistReq.getAdSiteCode())+pContentPath);
			}
			
		}
		return contp;
	}
	public void refreshCategory()
	{
		List<TCategoryinfo> listCategory = aDSurveyDAO.queryCategory();
		Long oldId=0L;
		List<String> listChild =  new ArrayList<String> ();
		if (listCategory!=null)
		{
			for (int i=0;i<listCategory.size();i++)
			{
				if (listCategory.get(i).getParentId().equals(-1))
				{
					continue;
				}
				listChild = addChildCategory(listCategory,listCategory.get(i).getId());
				CategoryCache.addMap(listCategory.get(i).getCategoryId(), listChild);
			}
			CategoryCache.updateMap();
		}
	}
	public List<String> addChildCategory(List<TCategoryinfo> listCategory,Long parentId)
	{
		List<String> listChild =  new ArrayList<String> ();
		if (listCategory!=null)
		{
			for (int i=0;i<listCategory.size();i++)
			{
				if (parentId.equals(listCategory.get(i).getParentId()))
				{
					List<String> listChildTemp= addChildCategory(listCategory,listCategory.get(i).getId());
					listChild.add(listCategory.get(i).getCategoryId());
					listChild.addAll(listChildTemp);
				}
			}
			if (listChild!=null && listChild.size()>0)
			{
				return listChild;
			}
		}
		return listChild;
	}
	
	public void refreshLocation()
	{
		//List<TLocationCode> 
		listLocation = aDSurveyDAO.queryLocationCode();
		Long oldId=0L;
		List<Long> listChild =  new ArrayList<Long> ();
		if (listLocation!=null)
		{
			for (int i=0;i<listLocation.size();i++)
			{
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())>2)
				{
					break;
				}
				LocationCacheBean cacheBean= new LocationCacheBean();
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==2)
				{
					cacheBean.setLocationcode1(listLocation.get(i).getLocationcode());
					cacheBean.setLocationcode2(0L);
					cacheBean.setLocationcode3(0L);
					cacheBean.setLocationcode4(0L);
				}
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==3)
				{
					cacheBean.setLocationcode2(listLocation.get(i).getLocationcode());
					cacheBean.setLocationcode3(0L);
					cacheBean.setLocationcode4(0L);
				}
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==4)
				{
					cacheBean.setLocationcode3(listLocation.get(i).getLocationcode());
					cacheBean.setLocationcode4(0L);
				}
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==5)
				{
					cacheBean.setLocationcode4(listLocation.get(i).getLocationcode());
				}
				LocationCache.addMap(listLocation.get(i).getLocationcode(), cacheBean);	
				addChildLoction(listLocation.get(i).getLocationcode(),StringUtil.toInt(listLocation.get(i).getLocationtype()),i+1,cacheBean);
				//LocationCache.addMap(listLocation.get(i).getLocationcode(), listChild);
			}
			LocationCache.updateMap();
		}
	}
	public List<Long> addChildLoction(Long parentId,int parentType,int index,LocationCacheBean cacheBean)
	{
		if (listLocation!=null)
		{
			for (int i=index;i<listLocation.size();i++)
			{
				if (parentId.equals(listLocation.get(i).getParentlocation()))
				{
					if (152010000008L==listLocation.get(i).getLocationcode())
					{
						System.out.println();
					}
					if (152010000007L==listLocation.get(i).getLocationcode())
					{
						System.out.println();
					}
					if (parentType==2)
					{
						cacheBean.setLocationcode1(parentId);
						cacheBean.setLocationcode2(0L);
						cacheBean.setLocationcode3(0L);
						cacheBean.setLocationcode4(0L);
					}
					if (parentType==3)
					{
						cacheBean.setLocationcode2(parentId);
						cacheBean.setLocationcode3(0L);
						cacheBean.setLocationcode4(0L);
					}
					if (parentType==4)
					{
						cacheBean.setLocationcode3(parentId);
						cacheBean.setLocationcode4(0L);
					}
					if (parentType==5)
					{
						cacheBean.setLocationcode4(parentId);
					}
					addChildLoction(listLocation.get(i).getLocationcode(),StringUtil.toInt(listLocation.get(i).getLocationtype()),i+1,cacheBean);
					
					LocationCacheBean temp = new LocationCacheBean();
					temp.setLocationcode(listLocation.get(i).getLocationcode());
					temp.setLocationcode1(cacheBean.getLocationcode1());
					temp.setLocationcode2(cacheBean.getLocationcode2());
					temp.setLocationcode3(cacheBean.getLocationcode3());
					temp.setLocationcode4(cacheBean.getLocationcode4());
					LocationCache.addMap(listLocation.get(i).getLocationcode(), temp);					
					
					//listChild.add(listLocation.get(i).getLocationcode());
					///listChild.addAll(listChildTemp);
				}
			}
			
		}
		return null;
	}
	
	public void refreshUser()
	{
		logger.info("refreshLocation start");
		refreshLocation();
		logger.info("refreshLocation end");
		logger.info("refreshUser start");
		List<TBsmpUser> listUser =null; 
		Long oldId=0L;
		Integer pageSize=100000;
		Integer pageNumber=0;
		listUser=aDSurveyDAO.queryUser(pageSize, pageNumber++);
//		Map<Long,List<Long>> locationMap =null;//= LocationCache.getLocationMap();
//		List arrayList = new ArrayList(locationMap.entrySet()); 
//		LocationComparator comparator=new LocationComparator();
//		Collections.sort(arrayList, comparator);  
		List<Long> childList=null;
		while (listUser!=null && listUser.size()>0)
		{
			for (int i=0;i<listUser.size();i++)
			{
				TBsmpUser userTemp=listUser.get(i);
				LocationCacheBean cacheBean = LocationCache.getChiledLocation(userTemp.getLocationcodevalue());
				
				if (cacheBean!=null)
				{
					userTemp.setArea1(cacheBean.getLocationcode1());
					userTemp.setArea2(cacheBean.getLocationcode2());
					userTemp.setArea3(cacheBean.getLocationcode3());
					userTemp.setArea4(cacheBean.getLocationcode4());
					
				}
//				Long key =0L;
//				int level=1;			    
//			    for(Iterator iterator = arrayList.iterator();iterator.hasNext();)
//			    	{  
//			    	//key = iterator.next();
//			    	Map.Entry entry =(Map.Entry)iterator.next();
//			    	key = StringUtil.toLong(entry.getKey());
//			    	childList = locationMap.get(key);
//			        if (childList!=null && childList.contains(userTemp.getLocationcodevalue()))
//			        {
//			        	if (level==1)
//			        	{
//			        		userTemp.setArea1(key);
//			        	}
//			        	if (level==2)
//			        	{
//			        		userTemp.setArea2(key);
//			        	}
//			        	if (level==3)
//			        	{
//			        		userTemp.setArea3(key);
//			        	}
//			        	if (level==4)
//			        	{
//			        		userTemp.setArea4(key);
//			        		break;
//			        	}
//			        	level++;
//			        }
//			    }   //end while
			    UserCache.addTempMap(userTemp);
			}//end for 
			listUser=aDSurveyDAO.queryUser(pageSize, pageNumber++);
			//测试机内存不足
			logger.info("refreshUser pageNumber:"+pageNumber);
			if (pageNumber>2)
			{
				//break;
			}
		}//end while
		UserCache.updateMap();
		logger.info("refreshUser end");
	}
	//刷新产品缓存
	public void refreshProduct()
	{
		List<ProductionCache> produrctList=aDSurveyDAO.queryProduct();
		ProductCache.addMap(produrctList);
		ProductCache.updateMap();
	}
	//刷新NPVR缓存
	public void refreshNpvr()
	{
		List<ChannelInfoCache> channelList=aDSurveyDAO.getChannelCache();
		NpvrCache.addMap(channelList);
		NpvrCache.updateMap();
	}
	//刷新影片缓存
	public void refreshAseet()
	{
		List<TAssetV> assetList=aDSurveyDAO.queryAssetV();
		AssetCache.addMap(assetList);
		AssetCache.updateMap();
	}
	//缓存订单投放次数
	public void refreshOrder()
	{
		Date startTime= new Date();
		Date endTime=new Date();
		List<TOrder> listOrder = aDSurveyDAO.queryOrder(startTime, endTime);
		if (listOrder!=null)
		{
			for (int i=0;i<listOrder.size();i++)
			{
				TOrder temp = listOrder.get(i);
				//temp.setPlayedNumber(0);
				//只记录当前次数
				if (temp.getPlayNumber()!=0)
				{
					temp.setPlayNumber(temp.getPlayNumber()-temp.getPlayedNumber());
					temp.setPlayedNumber(0);	
					if (temp.getPlayNumber()<=0)
					{
						temp.setPlayNumber(-1);
					}
				}
//				if (temp.getPlayedNumber()==null)
//				{
//					temp.setPlayedNumber(0);	
//				}
				temp.setPlayedNumber(0);
				OrderCache.addMap(temp);
			}
			
		}
		OrderCache.updateMap();
		//DefaultResourceCache.getInstance().generateCache(true);
		generateDefaultResourceCache();
	}
	/**
	 * 创建默认素材缓存
	 */
	private void generateDefaultResourceCache(){
		Map<String, String> resourceCacheTemp = new HashMap<String, String>();
		//首先查出3种默认素材的内容
		Map<String, String> mapImage = aDSurveyDAO.getImageDefaultResource(ConstantsHelper.DEFAULT_RESOURCE_TYPE);
		Map<String, String> mapText = aDSurveyDAO.getTextDefaultResource(ConstantsHelper.DEFAULT_RESOURCE_TYPE);
		Map<String, String> mapVideo = aDSurveyDAO.getViedoDefaultResource(ConstantsHelper.DEFAULT_RESOURCE_TYPE);
		
		//获取默认素材的广告位和对应素材ID
		List<DefaultResource> list = aDSurveyDAO.getDefaultResource(ConstantsAdsCode.DEAFULT_RESOURCE_P);
		for (DefaultResource defaultResource : list) {
			String resourceId = defaultResource.getResourceId()+"";
			String code = defaultResource.getCode();
			switch (defaultResource.getType()) {
			case ConstantsHelper.DEFAULT_RESOURCE_TYPE_IMAGE:
				String image = mapImage.get(resourceId);
				String firstChar = image.substring(0,1);
				if(!ConstantsHelper.OBLIQUE_LINE.equals(firstChar)){
					image = ConstantsHelper.OBLIQUE_LINE + image;
				}
				//image = InitConfig.getContentPath(code)+image;
				resourceCacheTemp.put(code, image);
				break;
			case ConstantsHelper.DEFAULT_RESOURCE_TYPE_VIDEO:
				String video = mapVideo.get(resourceId);
				resourceCacheTemp.put(code, video);
				break;
			case ConstantsHelper.DEFAULT_RESOURCE_TYPE_TEXT:
				String text = mapText.get(resourceId);
				resourceCacheTemp.put(code, text);
				break;
			default:
				break;
			}
		}
		
		DefaultResourceCache.setResourceCache(resourceCacheTemp);		
	}
	public void refreshNoAdPloy()
	{
		List<TNoAdPloyView> list = aDSurveyDAO.queryTNoAdPloyView();
		UserNoAdCache.addMap(list);
		UserNoAdCache.updateMap();
	}
	//存储投放次数至数据库
	public void saveOrderPlayTime()
	{
		Map<Integer,TOrder> orderMap = OrderCache.getOrderMap();
		Iterator iter = orderMap.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			TOrder val = (TOrder)entry.getValue();
			aDSurveyDAO.updateOrder(val);
			val.setPlayedNumber(0);
			//OrderCache.addMap(val);
		}
	}
	//存储日志记录
	public void saveHistory()
	{
		ReqHistoryCache.updateMap();
		Map<String,List<TempObjectStorgePlaylistContent>> historyMap = ReqHistoryCache.getHistoryMap();
		Iterator iter = historyMap.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			List<TempObjectStorgePlaylistContent> val = (List<TempObjectStorgePlaylistContent>)entry.getValue();
			aDSurveyDAO.SavePlaylistIntoHistoryReport(val);
		}
		
	}
	//存储投放状态
	public void saveHistoryStatus()
	{
		//复制当前数据至保存MAP
		ReqHistoryCache.updateStatusMap();
		Map<String,List<TempObjectStorgePlaylistContent>> historyUpdatMap = ReqHistoryCache.getHistoryUpdateMap();
		Iterator iter = historyUpdatMap.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			List<TempObjectStorgePlaylistContent> val = (List<TempObjectStorgePlaylistContent>)entry.getValue();
			aDSurveyDAO.updateHistoryReport(val);
		}
		
	}
}
