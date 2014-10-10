package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;

public class ProductPrecisionCache {
	private static final Logger log = LoggerFactory.getLogger(ProductPrecisionCache.class);
	//key  = PRODUCT:precisid:productid   data=contentpath
	private static Map<String,List<ContentCacheBean>> productPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	private static Map<String,List<ContentCacheBean>> tempproductPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	public static Map<String, List<ContentCacheBean>> getProductPrecisionMap() {
		return productPrecisionMap;
	}
	public static void setProductPrecisionMap(
			Map<String, List<ContentCacheBean>> productPrecisionMap) {
		ProductPrecisionCache.productPrecisionMap = productPrecisionMap;
	}
	public static void addMap(String precisid,String productcode,List<ContentCacheBean> content)
	{
		tempproductPrecisionMap.put("PRODUCT:"+precisid+":"+productcode, content);
	}
	public static void updateMap()
	{
		productPrecisionMap=tempproductPrecisionMap;
		tempproductPrecisionMap = new HashMap<String,List<ContentCacheBean>>();
	}
	public static ContentCacheBean getContent(String precisid,String productcode)
	{
		List<ContentCacheBean> tempList =productPrecisionMap.get("PRODUCT:"+precisid+":"+productcode);
		ContentCacheBean retbean=null;
		
		if (tempList!=null)
		{
			int index = (int)(Math.random()*tempList.size());
			retbean = tempList.get(index);
		}
		return retbean;
	}
}
