package com.avit.ads.requestads.cache;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;
import com.avit.ads.requestads.cache.bean.TAssetV;

public class AssetCache {
	private static final Logger log = LoggerFactory.getLogger(AssetCache.class);
	//key  = PRODUCT:precisid:productid   data=contentpath
	private static Map<String,TAssetV> assetMap = new HashMap<String,TAssetV>();
	private static Map<String,TAssetV> tempassetMap = new HashMap<String,TAssetV>();
	private static Map<String,List<String>> assetCategoryMap = new HashMap<String,List<String>>();
	private static Map<String,List<String>> tempassetCategoryMap = new HashMap<String,List<String>>();
	
	
	public static void addMap(List<TAssetV> assetList)
	{
		List<String> listtemp=null;
		if (assetList!=null)
		{
			for (int i=0;i<assetList.size();i++)
			{
				tempassetMap.put(assetList.get(i).getAssetId(), assetList.get(i));
				listtemp=tempassetCategoryMap.get(assetList.get(i).getAssetId());
				if (listtemp==null)
				{
					listtemp =  new ArrayList<String>();
				}
				if (assetList.get(i).getAssetId().equals("VODC2011061412100903"))
				{
					assetList.get(i).getAssetId();
				}
				if (!listtemp.contains(assetList.get(i).getCategoryId()))
				{
					listtemp.add(assetList.get(i).getCategoryId());
				}
				tempassetCategoryMap.put(assetList.get(i).getAssetId(),listtemp);
			}
		}
		
	}
	public static void updateMap()
	{
		assetMap=tempassetMap;
		tempassetMap = new HashMap<String,TAssetV>();
		assetCategoryMap =tempassetCategoryMap;
		tempassetCategoryMap = new HashMap<String,List<String>>();
	}
	public static TAssetV getAsset(String assetId)
	{
		TAssetV tempList =assetMap.get(assetId);		
		return tempList;
	}
	public static List<String> getCategory(String assetId)
	{
		return assetCategoryMap.get(assetId);
	}
}
