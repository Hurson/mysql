package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class NpvrPrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(NpvrPrecisionCache.class);
	//key  = NPVR:precisid:serviceid   data=contentpath
	private static Map<String,List<ContentCacheBean>> npvrPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> tempnpvrPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	public static Map<String, List<ContentCacheBean>> getNpvrPrecisionMap() {
		return npvrPrecisionMap;
	}
	public static void setNpvrPrecisionMap(
			Map<String, List<ContentCacheBean>> npvrPrecisionMap) {
		NpvrPrecisionCache.npvrPrecisionMap = npvrPrecisionMap;
	}
	public static void addMap(String precisid,String serviceid,List<ContentCacheBean> content)
	{
		tempnpvrPrecisionMap.put("NPVR:"+precisid+":"+serviceid, content);
	}
	public static void updateMap()
	{
		npvrPrecisionMap=tempnpvrPrecisionMap;
		tempnpvrPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	}
	public static ContentCacheBean getContent(String precisid,String serviceid)
	{
		List<ContentCacheBean> tempList =npvrPrecisionMap.get("NPVR:"+precisid+":"+serviceid);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
}
