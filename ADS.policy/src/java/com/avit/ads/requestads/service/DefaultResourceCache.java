package com.avit.ads.requestads.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avit.ads.requestads.bean.DefaultResource;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.dao.impl.ADSurveyDAOImp;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.InitConfig;

public class DefaultResourceCache {

	//ADSurveyDAO aDSurveyDAO = (ADSurveyDAOImp) ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
	public static DefaultResourceCache instance  = null;
	private static Map<String, String> resourceCache =new HashMap<String, String>();
	
	private DefaultResourceCache() {
		//this.generateDefaultResourceCache();
	}
	/**
	 * 供外部调用生成缓存方法
	 * @param flag
	 */
	public void generateCache(boolean flag){
	//	if(flag){
		//	this.generateDefaultResourceCache();
		//}
	}
	/**
	 * 创建实例的方法，为了降低synchronized的消耗，使用双重加锁检查的方式
	 */
//	public static DefaultResourceCache getInstance(){
//		if(instance == null){
//			synchronized(DefaultResourceCache.class){
//				if(instance == null){
//					instance = new DefaultResourceCache();
//				}
//			}
//		}
//		return instance;
//	}
	
//	/**
//	 * 创建默认素材缓存
//	 */
//	private void generateDefaultResourceCache(){
//		Map<String, String> resourceCacheTemp = new HashMap<String, String>();
//		//首先查出3种默认素材的内容
//		Map<String, String> mapImage = aDSurveyDAO.getImageDefaultResource(ConstantsHelper.DEFAULT_RESOURCE_TYPE);
//		Map<String, String> mapText = aDSurveyDAO.getTextDefaultResource(ConstantsHelper.DEFAULT_RESOURCE_TYPE);
//		Map<String, String> mapVideo = aDSurveyDAO.getViedoDefaultResource(ConstantsHelper.DEFAULT_RESOURCE_TYPE);
//	
//		//获取默认素材的广告位和对应素材ID
//		List<DefaultResource> list = aDSurveyDAO.getDefaultResource(ConstantsAdsCode.DEAFULT_RESOURCE_P);
//		for (DefaultResource defaultResource : list) {
//			String resourceId = defaultResource.getResourceId()+"";
//			String code = defaultResource.getCode();
//			switch (defaultResource.getType()) {
//			case ConstantsHelper.DEFAULT_RESOURCE_TYPE_IMAGE:
//				String image = mapImage.get(resourceId);
//				String firstChar = image.substring(0,1);
//				if(!ConstantsHelper.OBLIQUE_LINE.equals(firstChar)){
//					image = ConstantsHelper.OBLIQUE_LINE + image;
//				}
//				image = InitConfig.getContentPath(code)+image;
//				resourceCacheTemp.put(code, image);
//				break;
//			case ConstantsHelper.DEFAULT_RESOURCE_TYPE_VIDEO:
//				String video = mapVideo.get(resourceId);
//				resourceCacheTemp.put(code, video);
//				break;
//			case ConstantsHelper.DEFAULT_RESOURCE_TYPE_TEXT:
//				String text = mapText.get(resourceId);
//				resourceCacheTemp.put(code, text);
//				break;
//			default:
//				break;
//			}
//		}
//		
//		resourceCache=resourceCacheTemp;
//	}
	public static Map<String, String> getResourceCache() {
		return resourceCache;
	}
	public static void setResourceCache(Map<String, String> resourceCachetemp) {
		resourceCache = resourceCachetemp;
	}
	
	
}
