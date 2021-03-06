package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;
import com.avit.ads.requestads.cache.bean.LocationCacheBean;

public class LocationCache {
	private static final Logger log = LoggerFactory.getLogger(LocationCache.class);
	//key  = PRODUCT:precisid:productid   data=contentpath
//	private static Map<Long,List<Long>> locationMap = new HashMap<Long,List<Long>>();
//	private static Map<Long,List<Long>> templocationMap = new HashMap<Long,List<Long>>();
//	public static Map<Long, List<Long>> getLocationMap() {
//		return locationMap;
//	}
//	public static void setLocationMap(Map<Long, List<Long>> locationMap) {
//		LocationCache.locationMap = locationMap;
//	}
//	public static void addMap(Long locationId,List<Long> locationIds)
//	{
//		templocationMap.put(locationId, locationIds);
//	}
//	public static void updateMap()
//	{
//		locationMap=templocationMap;
//		templocationMap = new HashMap<Long,List<Long>>();
//	}
//	public static List<Long> getChiledLocation(Long locationId)
//	{
//		List<Long> tempList =locationMap.get(locationId);		
//		return tempList;
//	}
	
	
	private static Map<Long,LocationCacheBean> locationMap = new HashMap<Long,LocationCacheBean>();
	private static Map<Long,LocationCacheBean> templocationMap = new HashMap<Long,LocationCacheBean>();
	
	
	public static Map<Long, LocationCacheBean> getLocationMap() {
		return locationMap;
	}
	public static void setLocationMap(Map<Long, LocationCacheBean> locationMap) {
		LocationCache.locationMap = locationMap;
	}
	public static void addMap(Long locationId,LocationCacheBean bean)
	{
		templocationMap.put(locationId, bean);
	}
	public static void updateMap()
	{
		locationMap=templocationMap;
		templocationMap = new HashMap<Long,LocationCacheBean>();
		System.out.println("locationMap.size"+locationMap.size());
	}
	public static LocationCacheBean getChiledLocation(Long locationId)
	{
		return locationMap.get(locationId);	
	}
}
