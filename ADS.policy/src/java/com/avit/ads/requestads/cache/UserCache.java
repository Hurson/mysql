package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;
import com.avit.ads.requestads.cache.bean.TBsmpUser;
import com.avit.ads.requestads.cache.bean.TOrder;

public class UserCache {
	
	private static final Logger log = LoggerFactory.getLogger(OrderCache.class);
	//key  = PRODUCT:precisid:productid   data=contentpath
	private static Map<String,TBsmpUser> userMap = new HashMap<String,TBsmpUser>();
	private static Map<String,TBsmpUser> tempuserMap = new HashMap<String,TBsmpUser>();
	public static void addTempMap(TBsmpUser user)
	{
		tempuserMap.put(user.getUsersn(), user);
	}
	public static void addMap(TBsmpUser user)
	{
		userMap.put(user.getUsersn(), user);
	}
	public static void updateMap()
	{
		userMap.putAll(tempuserMap);
		tempuserMap = new HashMap<String,TBsmpUser>();
	}
	public static TBsmpUser getUser(String tvn)
	{
		return userMap.get(tvn);	
	}
}
