package com.avit.ads.requestads.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avit.ads.requestads.bean.cache.AssetBean;
import com.avit.ads.requestads.bean.cache.ChannelInfoCache;
import com.avit.ads.requestads.bean.cache.ProductionCache;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.dao.impl.ADSurveyDAOImp;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.ContextHolder;

public class MediaInfoCache {

	ADSurveyDAO aDSurveyDAO = (ADSurveyDAOImp) ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
	public static MediaInfoCache instance  = null;
	private Map<String, String> productCache;
	private Map<String, String> channelCahce;
	private Map<String, String> assetCache;
	
	private MediaInfoCache() {
		this.generateProductCache();
		this.generateAssetCache();
		this.generateChannelCache();
	} 

	/**
	 * 提供外部调用方法
	 * @param outterCall
	 */
	public void generateCache(boolean outterCall){
		if(outterCall){
			this.generateProductCache();
			this.generateAssetCache();
			this.generateChannelCache();
		}
	}
	
	/**
	 * 创建实例的方法，为了降低synchronized的消耗，使用双重加锁检查的方式
	 */
	public static MediaInfoCache getInstance(){
		if(instance == null){
			synchronized(MediaInfoCache.class){
				if(instance == null){
					instance = new MediaInfoCache();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 生成产品缓存
	 */
	private void generateProductCache(){
		List<ProductionCache> list = aDSurveyDAO.getProductCache(ConstantsHelper.LOOKBACK_TYPE);
		productCache = new HashMap<String, String>();
		for (ProductionCache productionCache : list) {
			String productId = productionCache.getProductId();
			productCache.put(productId, productionCache.getType());
		}
	}
	
	/**
	 * 生成频道缓存
	 */
	private void generateChannelCache() {
		List<ChannelInfoCache> list = aDSurveyDAO.getChannelCache();
		channelCahce = new HashMap<String, String>();
		for (ChannelInfoCache channelInfoCache : list) {
			String summary = channelInfoCache.getSummaryShort();
			String serviceId = channelInfoCache.getServiceId();
			channelCahce.put(summary, serviceId);
		}
		
	}

	/**
	 * 生成影片缓存
	 */
	private void generateAssetCache() {
		List<AssetBean> list = aDSurveyDAO.getAssetCache();
		assetCache = new HashMap<String, String>();
		for (AssetBean assetBean : list) {
			String assetId = assetBean.getAssetId();
			assetCache.put(assetId, assetBean.getAssetName());
		}

	}

	public Map<String, String> getProductCache() {
		return productCache;
	}

	public void setProductCache(Map<String, String> productCache) {
		this.productCache = productCache;
	}

	public Map<String, String> getChannelCahce() {
		return channelCahce;
	}

	public void setChannelCahce(Map<String, String> channelCahce) {
		this.channelCahce = channelCahce;
	}

	public Map<String, String> getAssetCache() {
		return assetCache;
	}

	public void setAssetCache(Map<String, String> assetCache) {
		this.assetCache = assetCache;
	}
	
	
	
}
