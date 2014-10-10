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
import com.avit.ads.requestads.cache.bean.TNoAdPloyView;
import com.avit.ads.util.ConstantsHelper;

public class UserNoAdCache {
	private static final Logger log = LoggerFactory.getLogger(UserNoAdCache.class);
	//key  = 简称   data=servicid
	private static Map<String,TNoAdPloyView> noadMap = new HashMap<String,TNoAdPloyView>();
	private static Map<String,TNoAdPloyView> tempnoadMap = new HashMap<String,TNoAdPloyView>();
	
	
	public static void addMap(List<TNoAdPloyView> noList)
	{
		if (noList!=null)
		{
			for (int i=0;i<noList.size();i++)
			{
				tempnoadMap.put(noList.get(i).getTvn()+ConstantsHelper.COLON+noList.get(i).getPositionCode(), noList.get(i));
			}
		}
	}
	public static void updateMap()
	{
		noadMap=tempnoadMap;
		tempnoadMap = new HashMap<String,TNoAdPloyView>();
	}
	public static boolean isPermitAd(String tvn,String positionCode)
	{
		if (noadMap.get(tvn+ConstantsHelper.COLON+positionCode)!=null)
		{
			return false;
		}
		else
		{
			return true;
		} 
	}
}
