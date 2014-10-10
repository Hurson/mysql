package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class IndustryPrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(IndustryPrecisionCache.class);
	//key  = KEY:precisid:industrycode   data=contentpath
	private static Map<String,List<ContentCacheBean>> industryPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> tempindustryPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	public static Map<String, List<ContentCacheBean>> getIndustryPrecisionMap() {
		return industryPrecisionMap;
	}
	public static void setIndustryPrecisionMap(
			Map<String, List<ContentCacheBean>> industryPrecisionMap) {
		IndustryPrecisionCache.industryPrecisionMap = industryPrecisionMap;
	}
	
	public static void addMap(String precisid,String industrycode,List<ContentCacheBean> content)
	{
		tempindustryPrecisionMap.put("INDUSTRY:"+precisid+":"+industrycode, content);
	}
	public static void updateMap()
	{
		industryPrecisionMap=tempindustryPrecisionMap;
		tempindustryPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	}
	public static ContentCacheBean getContent(String precisid,String industrycode)
	{
		List<ContentCacheBean> tempList =industryPrecisionMap.get("INDUSTRY:"+precisid+":"+industrycode);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
	
	
}
