package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class AssetKeyPrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(AssetKeyPrecisionCache.class);
	//key  = KEY:precisid   data=key
	private static Map<String,String> keyMap = new HashMap<String,String>();
	private static Map<String,String> tempkeyMap = new HashMap<String,String>();
	
	//key  = KEY:precisid:key   data=contentpath
	private static Map<String,List<ContentCacheBean>> keyPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> tempkeyPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	
	public static Map<String, List<ContentCacheBean>> getKeyPrecisionMap() {
		return keyPrecisionMap;
	}
	public static void setKeyPrecisionMap(
			Map<String, List<ContentCacheBean>> keyPrecisionMap) {
		AssetKeyPrecisionCache.keyPrecisionMap = keyPrecisionMap;
	}
	public static Map<String, String> getKeyMap() {
		return keyMap;
	}
	public static void setKeyMap(Map<String, String> keyMap) {
		AssetKeyPrecisionCache.keyMap = keyMap;
	}
	public static void addMap(String precisid,String key,List<ContentCacheBean> content)
	{
		tempkeyMap.put(precisid,key);
		tempkeyPrecisionMap.put("KEY:"+precisid+":"+key, content);
	}
	public static void updateMap()
	{
		keyPrecisionMap=tempkeyPrecisionMap;
		tempkeyPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
		keyMap =tempkeyMap;
		tempkeyMap = new HashMap<String,String>();
	}
	public static String getKey(String precisid)
	{
		return keyMap.get(precisid);
	}
	public static ContentCacheBean getContent(String precisid,String key)
	{
		List<ContentCacheBean> tempList =keyPrecisionMap.get("KEY:"+precisid+":"+key);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
	
}
