package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.cache.bean.ContentCacheBean;
import com.avit.ads.requestads.cache.bean.TBsmpUser;
import com.avit.ads.requestads.cache.bean.TOrder;

public class OrderCache {
	private static final Logger log = LoggerFactory.getLogger(OrderCache.class);
	//key  = PRODUCT:precisid:productid   data=contentpath
	private static Map<Integer,TOrder> orderMap = new HashMap<Integer,TOrder>();
	private static Map<Integer,TOrder> temporderMap = new HashMap<Integer,TOrder>();
	
	public static Map<Integer, TOrder> getOrderMap() {
		return orderMap;
	}
	public static void setOrderMap(Map<Integer, TOrder> orderMap) {
		OrderCache.orderMap = orderMap;
	}
	public static void addMap(TOrder order)
	{
		temporderMap.put(order.getId().intValue(), order);
	}
	public static void addMapCount(Integer orderid)
	{
		TOrder temp = orderMap.get(orderid);
		if (temp!=null)
		{
			temp.setPlayedNumber(temp.getPlayedNumber()+1);
			orderMap.put(orderid,temp);
		}
	}
	public static void updateMap()
	{
		orderMap=temporderMap;
		temporderMap = new HashMap<Integer,TOrder>();
	}
	//取次数是否合法
	public static boolean getTimeEnough(int orderId)
	{
		TOrder order= orderMap.get(orderId);
		if (order==null)
		{
			return false;
		}
		int tt = order.getPlayNumber();
		tt = order.getPlayedNumber();
		if (order.getPlayNumber()==0)
		{
			return true;
		}
		if (order.getPlayNumber()==-1)
		{
			return false;
		}
		if ((order.getPlayedNumber())>=order.getPlayNumber() )
		{
			return false;
		}
		else
		{
			//由于广告响应报告里无订单属性 ，故只能每处理一次先加一次，此处可加快订单刷新缓存时间
			//order.setPlayedNumber(order.getPlayedNumber()+1);
			//orderMap.put(orderId,order);
			return true;
		}	
	}
	
	//取次数是否合法
	public static boolean addTimeEnough(int orderId)
	{
		TOrder order= orderMap.get(orderId);
		if (order==null)
		{
			return true;
		}
		//由于广告响应报告里无订单属性 ，故只能每处理一次先加一次，此处可加快订单刷新缓存时间
		order.setPlayedNumber(order.getPlayedNumber()+1);
		log.info(order.getId()+"order.playednumber="+order.getPlayedNumber());
		orderMap.put(orderId,order);
		return true;
		
	}
}
