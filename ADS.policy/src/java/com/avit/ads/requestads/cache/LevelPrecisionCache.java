package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class LevelPrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(LevelPrecisionCache.class);
	//key  = KEY:precisid:userrankcode   data=contentpath
	private static Map<String,List<ContentCacheBean>> levelPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> templevelPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	public static Map<String, List<ContentCacheBean>> getLevelPrecisionMap() {
		return levelPrecisionMap;
	}
	public static void setLevelPrecisionMap(
			Map<String, List<ContentCacheBean>> levelPrecisionMap) {
		LevelPrecisionCache.levelPrecisionMap = levelPrecisionMap;
	}
	public static void addMap(String precisid,String levelcode,List<ContentCacheBean> content)
	{
		templevelPrecisionMap.put("LEVEL:"+precisid+":"+levelcode, content);
	}
	public static void updateMap()
	{
		levelPrecisionMap=templevelPrecisionMap;
		templevelPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	}
	public static ContentCacheBean getContent(String precisid,String levelcode)
	{
		List<ContentCacheBean> tempList =levelPrecisionMap.get("LEVEL:"+precisid+":"+levelcode);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
}
