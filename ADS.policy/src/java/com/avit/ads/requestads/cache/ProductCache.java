package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.bean.cache.ProductionCache;

public class ProductCache {
	private static final Logger log = LoggerFactory.getLogger(ProductCache.class);
	//key  = productid   data=contentpath
	private static Map<String,String> productMap = new HashMap<String,String>();
	private static Map<String,String> tempproductMap  = new HashMap<String,String>();
	
	
	public static void addMap(List<ProductionCache> productList)
	{
		if (productList!=null)
		{
			for (int i=0;i<productList.size();i++)
			{
				tempproductMap .put(productList.get(i).getProductId(), productList.get(i).getType());
			}
		}
		
	}
	public static void updateMap()
	{
		productMap =tempproductMap ;
		tempproductMap  = new HashMap<String,String>();
	}
	public static String getProductType(String productId)
	{
		return productMap.get(productId);
	}
}
