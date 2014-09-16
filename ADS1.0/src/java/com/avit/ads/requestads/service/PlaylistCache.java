package com.avit.ads.requestads.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import com.avit.ads.requestads.bean.AdPlaylistReq;
import com.avit.ads.requestads.bean.AdPlaylistReqContent;
import com.avit.ads.requestads.bean.AdPlaylistReqPContent;
import com.avit.ads.requestads.bean.AdPlaylistReqPrecision;
import com.avit.ads.requestads.bean.Content;
import com.avit.ads.requestads.bean.PlaylistCacheModel;
import com.avit.ads.requestads.bean.PrecisionCacheModel;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.dao.impl.ADSurveyDAOImp;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.DateUtil;
import com.avit.ads.util.InitConfig;

public final class PlaylistCache {
	
	
	ADSurveyDAO aDSurveyDAO = (ADSurveyDAOImp) ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
	public static PlaylistCache instance  = null;
	
	private Map<String, List<PlaylistCacheModel>> newCache;
	//private Map<String, List<PlaylistCacheModel>> oldCache;
	private Map<String, List<PlaylistCacheModel>> tmpCache;
	
	public Map<String, List<PlaylistCacheModel>> getCache() {
		// /* 日过是30分以前，则返回newCache，以后则返回oldCache，如果是30分，因为生产cache的方法要执行一段时间，*/
		// /* 所以，在执行完以前是需要返回new，执行完以后需要返回old，所以在30分发起的请求则返回old+new   */
		return newCache;
	}
	/**
	 * 私有构造函数
	 */
	private PlaylistCache(){
		this.GenerateCacheModule();
		newCache = tmpCache;
	}
	
	/**
	 * 创建实例的方法，为了降低synchronized的消耗，使用双重加锁检查的方式
	 */
	public static PlaylistCache getInstance(){
		if(instance == null){
			synchronized(PlaylistCache.class){
				if(instance == null){
					instance = new PlaylistCache();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 提供外部调用的创建Cache方法，如果是定时器调用的，则需要执行oldCache = newCache 代码段，如果不是，则只更新newCache
	 * @param innerCall
	 */
	public void GenerateCacheModule(boolean timerCall){
		this.GenerateCacheModule();
		if(timerCall){
			//oldCache = newCache;
			newCache = tmpCache;
		}
	}
	
	/**
	 * 创建Cache模型,结构为 Map<String, List<PlaylistCacheModel>>
	 * 4张表关联查询，包括播出单，播出单内容，精准，精准内容。
	 */
	private void GenerateCacheModule(){
		PlaylistCacheModel objPlaylistCacheModel = null;
		try {
			
		
		
		tmpCache = new HashMap<String, List<PlaylistCacheModel>>();
		// 查询当天某段时间（暂定0点到24点）的播出单
		List<AdPlaylistReq> lstAdPlaylistReq = aDSurveyDAO.getPlaylistByTime(
				ConstantsHelper.CACHE_DIVIDE_BEGIN, 
				ConstantsHelper.CACHE_DIVIDE_END, 
				new Date(System.currentTimeMillis()));
		
		// 将普通内容为空的先清除掉
		for (int i = 0, j = lstAdPlaylistReq.size(); i < j; i++) {
			Set<AdPlaylistReqContent> set = lstAdPlaylistReq.get(i).getColContents();
			if(set == null || set.size() <= 0 || !hasContent(set)){
				lstAdPlaylistReq.remove(i);
			}
		}
		
		List<String> lstAdCodeTmp = new ArrayList<String>();
		for (AdPlaylistReq adPlaylistReq : lstAdPlaylistReq) {
			
			long id = adPlaylistReq.getId();
			// 将开始和结束时间设置为整型为了方便计算。
			String begin = adPlaylistReq.getBegin();
			begin = begin.substring(0, 2)+":"+begin.substring(2, 4)+":"+begin.substring(4, 6);
			String end = adPlaylistReq.getEnd();
			end = end.substring(0, 2)+":"+end.substring(2, 4)+":"+end.substring(4, 6);
			java.util.Date beginDate = DateUtil.toDate(DateUtil.getCurrentDateStr1()+" "+begin);
			java.util.Date endDate = DateUtil.toDate(DateUtil.getCurrentDateStr1()+" "+end);
			long lBegin = beginDate.getTime()/1000;
			long lEnd = endDate.getTime()/1000;
			
			String strLocationIds = adPlaylistReq.getAreas();
			// 区域的集合的结构为 001#002#003， 将之拆分成数组
			String[] locationIds = strLocationIds.split(ConstantsHelper.AREA_SPLIT__SIGN);
			String channels = adPlaylistReq.getChannels();
			
			// 频道的集合结构为 ["001,002","005,007","007,005,002,006"]，使用json格式对其拆分
			// 且区域和频道的集合的长度必须相等
			JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(channels);
			
			Map<String, String> map = new HashMap<String, String>();
			List<PlaylistCacheModel> list = new ArrayList<PlaylistCacheModel>();
			
			for (int i = 0; i < locationIds.length; i++) {
				String[] tmp = jsonArray.getString(i).split(ConstantsHelper.CHANNEL_ID_SPLIT__SIGN);
				for (int j = 0; j < tmp.length; j++) {
					if(tmp[j].isEmpty()){
						tmp[j] = "0";
					}
					// 设置Key值
					String key = id + ":"+ tmp[j] +":" + locationIds[i];
					
					map.put(key, id+"");
				}
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
			// 解析广告内容信息
			Set<AdPlaylistReqContent> set = adPlaylistReq.getColContents();
			
			//填充广告内容的数据结构
			List<Content> lstContent = new ArrayList<Content>();
			for (Iterator<AdPlaylistReqContent> iterator = set.iterator(); iterator.hasNext();) {
				AdPlaylistReqContent adPlaylistReqContent = iterator.next();
				Content con = new Content();
				con.setContentId(adPlaylistReqContent.getContentId());
				String type =adPlaylistReqContent.getContentType();
				con.setContentPath(adPlaylistReqContent.getContentPath());
				// 如果为空则是单个素材，直接放入content对象中，如果不为空则需要解析配置文件
				if(type == null || "0".equals(type)){
					con.setContentType(type);
					con.setContent(InitConfig.getContentPath(adPlaylistReq.getAdSiteCode())+adPlaylistReqContent.getContentPath());
				} else {
					con.setContentType(type);
					String path = adPlaylistReqContent.getContentPath();
					String sourcePath = GetSource(path, type);
					con.setContent(sourcePath);
				}
				lstContent.add(con);
			}
			objPlaylistCacheModel.setLstContents(lstContent);
			
			// 解析精准的信息，主要将Set变为list，因为list的索引是数据库表中的索引，如果数据库表中的索引不是
			//连续的，那么在得到的list中的索引也不是连续的，所以要使用set接收，然后用set转为list方便直接在cache命中指定内容
			List<PrecisionCacheModel> lstPrecision = new ArrayList<PrecisionCacheModel>();
			Set<AdPlaylistReqPrecision> setP = adPlaylistReq.getColPrecisions();
			for (Iterator<AdPlaylistReqPrecision> iterator = setP.iterator(); iterator.hasNext();) {
				AdPlaylistReqPrecision adPlaylistReqPrecision = iterator.next();
				PrecisionCacheModel pCahce = new PrecisionCacheModel();
				Set<AdPlaylistReqPContent> setpC = adPlaylistReqPrecision.getColPreContents();
				List<Content> lstPContent = new ArrayList<Content>();
				// 拷贝对象属性
				pCahce.setUseLevel(adPlaylistReqPrecision.getUseLevel());
				pCahce.setUserArea(adPlaylistReqPrecision.getUserArea());
				pCahce.setUserindustrys(adPlaylistReqPrecision.getUserindustrys());
				pCahce.setTvnNumber(adPlaylistReqPrecision.getTvnNumber());
				
				pCahce.setProductCode(adPlaylistReqPrecision.getProductCode());
				pCahce.setType(adPlaylistReqPrecision.getType());
				pCahce.setBtvChannelId(adPlaylistReqPrecision.getBtvChannelId());
				pCahce.setAssetSortId(adPlaylistReqPrecision.getAssetSortId());
				pCahce.setKey(adPlaylistReqPrecision.getKey());
				pCahce.setUseLevel(adPlaylistReqPrecision.getUseLevel());
				pCahce.setId(adPlaylistReqPrecision.getId());
				
				// 解析精准的内容
				for (Iterator<AdPlaylistReqPContent> iterator2 = setpC.iterator(); iterator2.hasNext();) {
					AdPlaylistReqPContent adPlaylistReqPContent = iterator2.next();
					Content con = new Content();
					
					con.setContentId(adPlaylistReqPContent.getContentId());
					String type =adPlaylistReqPContent.getContentType();
					con.setContentPath(adPlaylistReqPContent.getContentPath());
					// 如果为空则是单个素材，直接放入content对象中，如果不为空则需要解析配置文件
					if(type == null || ConstantsHelper.SIGN_CONTENT_TYPE.equals(type)){
						con.setContentType(type);
						con.setContent(InitConfig.getContentPath(adPlaylistReq.getAdSiteCode())+adPlaylistReqPContent.getContentPath());
						
					} else {
						con.setContentType(type);
						String path = adPlaylistReqPContent.getContentPath();
						String sourcePath = GetSource(path, type);
						con.setContent(sourcePath);
					}
					lstPContent.add(con);
				}
				pCahce.setLstPreContent(lstPContent);
				lstPrecision.add(pCahce);
			}
			
			objPlaylistCacheModel.setLstPrecision(lstPrecision);
			
			list.add(objPlaylistCacheModel);
			
			// 如果在时间段内有多个相同广告位的，需要将相同广告位的播出单数据放入对应的List中，如果没有就直接放入map中
			if(lstAdCodeTmp.contains(adPlaylistReq.getAdSiteCode())){
				List<PlaylistCacheModel> listTemp = tmpCache.get(adPlaylistReq.getAdSiteCode());
				listTemp.addAll(list);
				tmpCache.put(adPlaylistReq.getAdSiteCode(), listTemp);
			} else {
				tmpCache.put(adPlaylistReq.getAdSiteCode(), list);
				lstAdCodeTmp.add(adPlaylistReq.getAdSiteCode());
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断播出单含有的内容对象是否为空，或者是对象中的路径属性是否为空。
	 * @param Set<AdPlaylistReqContent> set
	 * @return boolean flag 含有:true， 没有:false
	 */
	private boolean hasContent(Set<AdPlaylistReqContent> set) {
		boolean flag = true;
		for (Iterator<AdPlaylistReqContent> iterator = set.iterator(); iterator.hasNext();) {
			AdPlaylistReqContent adPlaylistReqContent =  iterator.next();
			if(adPlaylistReqContent.getContentPath() == null || "".equals(adPlaylistReqContent.getContentPath())){
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * 获取素材配置文件中所有的字符串。
	 * 
	 * @param path 配置文件的路径
	 * @return 配置文件的内容
	 */
	private String GetSource(String path, String contentType) {
		StringBuffer buffer = null;
		 String dic = InitConfig.getAdsResourcePath();
		
		// 如果存储的路径找不到文件，则返回空字符串
		try {
            BufferedReader reader = new BufferedReader( new InputStreamReader(new FileInputStream(dic+path),"utf-8"));
            buffer = new StringBuffer();
            String line; // 用来保存每行读取的内容
            if("3".equals(contentType)){
            	line = reader.readLine(); // 读取第一行
                while (line != null) { // 如果 line 为空说明读完了
                    buffer.append(line); // 将读到的内容添加到 buffer 中
                    buffer.append(";"); // 添加换行符
                    line = reader.readLine(); // 读取下一行
                }
            } else if("2".equals(contentType)) {
            	line = reader.readLine();
            	while(line != null){
	            	int index = line.indexOf("=");
	            	// 去除掉=的前缀，拼接出http的前缀
	            	String prefix = InitConfig.getHttpServer().getUrl();
	            	line = prefix+line.substring(index, line.length());
	            	buffer.append(line);
	            	buffer.append(";");
	            	line = reader.readLine();
            	}
            	
            }
            
            
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int len = buffer.toString().length();
        if(len == 0){
        	return "";
        }
		return buffer.toString().substring(0, len -1);
	}

}
