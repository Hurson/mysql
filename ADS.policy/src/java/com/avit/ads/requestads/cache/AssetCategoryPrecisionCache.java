package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class AssetCategoryPrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(AssetCategoryPrecisionCache.class);
	//key  = KEY:precisid:categoryid   data=contentpath
	private static Map<String,List<ContentCacheBean>> assetCategoryPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> tempassetCategoryPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	
	public static Map<String, List<ContentCacheBean>> getAssetCategoryPrecisionMap() {
		return assetCategoryPrecisionMap;
	}
	public static void setAssetCategoryPrecisionMap(
			Map<String, List<ContentCacheBean>> assetCategoryPrecisionMap) {
		AssetCategoryPrecisionCache.assetCategoryPrecisionMap = assetCategoryPrecisionMap;
	}
	public static void addMap(String precisid,String categoryid,List<ContentCacheBean> content)
	{
		tempassetCategoryPrecisionMap.put("CATEGORY:"+precisid+":"+categoryid, content);
	}
	public static void updateMap()
	{
		assetCategoryPrecisionMap=tempassetCategoryPrecisionMap;
		tempassetCategoryPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	}
	public static ContentCacheBean getContent(String precisid,String categoryid)
	{
		List<ContentCacheBean> tempList =assetCategoryPrecisionMap.get("CATEGORY:"+precisid+":"+categoryid);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
	
}
