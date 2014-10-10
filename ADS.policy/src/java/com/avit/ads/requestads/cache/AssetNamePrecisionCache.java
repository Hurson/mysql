package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class AssetNamePrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(AssetNamePrecisionCache.class);
	//key  = KEY:precisid   data=assetname
	private static Map<String,String> nameMap = new HashMap<String,String>();
	private static Map<String,String> tempnameMap = new HashMap<String,String>();
	//key  = KEY:precisid:key   data=contentpath
	private static Map<String,List<ContentCacheBean>> namePrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> tempnamePrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	
	public static Map<String, List<ContentCacheBean>> getNamePrecisionMap() {
		return namePrecisionMap;
	}
	public static void setNamePrecisionMap(
			Map<String, List<ContentCacheBean>> namePrecisionMap) {
		AssetNamePrecisionCache.namePrecisionMap = namePrecisionMap;
	}
	public static Map<String, String> getNameMap() {
		return nameMap;
	}
	public static void setNameMap(Map<String, String> nameMap) {
		AssetNamePrecisionCache.nameMap = nameMap;
	}
	public static void addMap(String precisid,String name,List<ContentCacheBean> content)
	{
		tempnameMap.put(precisid,name);
		tempnamePrecisionMap.put("NAME:"+precisid+":"+name, content);
	}
	public static void updateMap()
	{
		namePrecisionMap=tempnamePrecisionMap;
		tempnamePrecisionMap = new HashMap<String,List<ContentCacheBean>>();
		nameMap =tempnameMap;
		tempnameMap = new HashMap<String,String>();
	}
	public static String getName(String precisid)
	{
		return nameMap.get(precisid);
	}
	public static ContentCacheBean getContent(String precisid,String name)
	{
		List<ContentCacheBean> tempList =namePrecisionMap.get("NAME:"+precisid+":"+name);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
	
	
}
