package com.avit.ads.requestads.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.bean.PlaylistCacheModel;
import com.avit.ads.util.ConstantsHelper;
import com.avit.common.util.StringUtil;

public class ReqPlayListCache {
	private static Logger logger = LoggerFactory.getLogger(ReqPlayListCache.class);
	//key=areaCode:adpositioncode  data=List<reqplaylistid>播出单ID 
	private static Map<String, List<String>> areaListMap = new HashMap<String, List<String>>();
	private static Map<String, List<String>> tempareaListMap = new HashMap<String, List<String>>();
	
	private static Map<String, PlaylistCacheModel> reqPlayListMap = new HashMap<String, PlaylistCacheModel>();
	private static Map<String,PlaylistCacheModel>  tempreqPlayListMap =new HashMap<String, PlaylistCacheModel>();
	
	
	
	public static Map<String, List<String>> getAreaListMap() {
		return areaListMap;
	}
	public static void setAreaListMap(Map<String, List<String>> areaListMap) {
		ReqPlayListCache.areaListMap = areaListMap;
	}
	public static void addMap(PlaylistCacheModel playMode)
	{
		tempreqPlayListMap.put(StringUtil.toNotNullStr(playMode.getId()),playMode);
		Map temp = playMode.getMap();
		if (temp!=null)
		{
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString()+ConstantsHelper.COLON+playMode.getAdSiteCode();
			List<String> listTemp = tempareaListMap.get(key);
			if (listTemp==null)
			{
				listTemp = new ArrayList<String>();
			}
			listTemp.add(StringUtil.toNotNullStr(playMode.getId()));
			tempareaListMap.put(key, listTemp);
			}
		}
	}
	public static void updateMap()
	{
		reqPlayListMap=tempreqPlayListMap;
		tempreqPlayListMap = new HashMap<String, PlaylistCacheModel>();
		
		areaListMap=tempareaListMap;
		tempareaListMap= new HashMap<String, List<String>>();
	}
	public static List<String> getPlayList(String areaCode)
	{
		return areaListMap.get(areaCode);
	}
	//
	public static PlaylistCacheModel getPlayModel(String playId)
	{
		PlaylistCacheModel tempMode =null;
		tempMode = reqPlayListMap.get(playId);		
		return tempMode;
	}
	
}
