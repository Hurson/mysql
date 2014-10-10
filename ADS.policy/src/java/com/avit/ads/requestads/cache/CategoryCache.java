package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class CategoryCache {
	private static final Logger log = LoggerFactory.getLogger(CategoryCache.class);
	//key  = PRODUCT:precisid:productid   data=contentpath
	private static Map<String,List<String>> categoryMap = new HashMap<String,List<String>>();
	private static Map<String,List<String>> tempcategoryMap = new HashMap<String,List<String>>();
	
	public static void addMap(String categoryId,List<String> childIds)
	{
		tempcategoryMap.put(categoryId, childIds);
	}
	public static void updateMap()
	{
		categoryMap=tempcategoryMap;
		tempcategoryMap = new HashMap<String,List<String>>();
	}
	public static List<String> getChiledCatetory(String categoryId)
	{
		List<String> tempList =categoryMap.get(categoryId);		
		return tempList;
	}
}
