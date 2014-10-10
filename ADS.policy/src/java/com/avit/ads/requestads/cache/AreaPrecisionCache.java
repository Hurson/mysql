package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class AreaPrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(AreaPrecisionCache.class);
	//key  = AREA:precisid:areacode   data=contentpath
	private static Map<String,List<ContentCacheBean>> areaPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> areaTempPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	public static Map<String, List<ContentCacheBean>> getAreaPrecisionMap() {
		return areaPrecisionMap;
	}
	public static void setAreaPrecisionMap(
			Map<String, List<ContentCacheBean>> areaPrecisionMap) {
		AreaPrecisionCache.areaPrecisionMap = areaPrecisionMap;
	}
	public static void addMap(String precisid,String areaCode,List<ContentCacheBean> content)
	{
		areaTempPrecisionMap.put("AREA:"+precisid+":"+areaCode, content);
	}
	public static void updateMap()
	{
		areaPrecisionMap=areaTempPrecisionMap;
		areaTempPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	}
	public static ContentCacheBean getContent(String precisid,String areaCode)
	{
		List<ContentCacheBean> tempList =areaPrecisionMap.get("AREA:"+precisid+":"+areaCode);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
	
}
