package com.avit.ads.requestads.cache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;

public class ReqHistoryCache {
	
	private static final Logger log = LoggerFactory.getLogger(OrderCache.class);
	//key  = token   data=contentpath
	//用于存储历史记录
	//保存数据库使用
	private static Map<String,List<TempObjectStorgePlaylistContent>> historyMap = new HashMap<String,List<TempObjectStorgePlaylistContent>>();
	//添加缓存使用
	private static Map<String,List<TempObjectStorgePlaylistContent>> temphistoryMap = new HashMap<String,List<TempObjectStorgePlaylistContent>>();
	////用于更新历史记录投放状态
	private static Map<String,List<TempObjectStorgePlaylistContent>> historyUpdateMap = new HashMap<String,List<TempObjectStorgePlaylistContent>>();
	//private static Map<String,List<TempObjectStorgePlaylistContent>> temphistoryUpdateMap = new HashMap<String,List<TempObjectStorgePlaylistContent>>();
	private static ConcurrentHashMap<String,List<TempObjectStorgePlaylistContent>> temphistoryUpdateMap = new ConcurrentHashMap<String,List<TempObjectStorgePlaylistContent>>();
	
	
	public static Map<String, List<TempObjectStorgePlaylistContent>> getHistoryUpdateMap() {
		return historyUpdateMap;
	}
	public static void setHistoryUpdateMap(
			Map<String, List<TempObjectStorgePlaylistContent>> historyUpdateMap) {
		ReqHistoryCache.historyUpdateMap = historyUpdateMap;
	}
	public static Map<String, List<TempObjectStorgePlaylistContent>> getHistoryMap() {
		return historyMap;
	}
	public static void setHistoryMap(
			Map<String, List<TempObjectStorgePlaylistContent>> historyMap) {
		ReqHistoryCache.historyMap = historyMap;
	}
	public static void addMap(String token,List<TempObjectStorgePlaylistContent> listHistory)
	{
		temphistoryMap.put(token, listHistory);
	}
	public static void updateMap()
	{
		historyMap=temphistoryMap;
		temphistoryMap = new HashMap<String,List<TempObjectStorgePlaylistContent>>();
	}
	public static void addStatusMap(String token,List<TempObjectStorgePlaylistContent> listHistory)
	{
		temphistoryUpdateMap.put(token, listHistory);
	}
	public static void updateStatusMap()
	{
		historyUpdateMap=temphistoryUpdateMap;
		temphistoryUpdateMap = new ConcurrentHashMap<String,List<TempObjectStorgePlaylistContent>>();
	}
	
}
