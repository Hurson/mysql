package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.bean.cache.ChannelInfoCache;
import com.avit.ads.requestads.bean.cache.ProductionCache;
import com.avit.ads.requestads.cache.bean.ContentCacheBean;
import com.avit.ads.requestads.cache.bean.TAssetV;

public class NpvrCache {
	private static final Logger log = LoggerFactory.getLogger(NpvrCache.class);
	//key  = 简称   data=servicid
	private static Map<String,String> npvrMap = new HashMap<String,String>();
	private static Map<String,String> tempnpvrMap = new HashMap<String,String>();
	
	
	public static void addMap(List<ChannelInfoCache> channelList)
	{
		if (channelList!=null)
		{
			for (int i=0;i<channelList.size();i++)
			{
				tempnpvrMap.put(channelList.get(i).getSummaryShort(), channelList.get(i).getServiceId());
			}
		}
		
	}
	public static void updateMap()
	{
		npvrMap=tempnpvrMap;
		tempnpvrMap = new HashMap<String,String>();
	}
	public static String getServiceId(String summary)
	{
		return npvrMap.get(summary);
	}
}
