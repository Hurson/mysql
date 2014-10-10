package com.avit.ads.requestads.service;

public interface RefreshCacheService {
	//缓存播出单
	public void refreshPlayListCache();	
	//缓存播出单
	public void refreshNoAdPloy();	
	
	//刷新影片分类缓存
	public void refreshCategory();
	public void refreshLocation();
	//刷新用户缓存
	public void refreshUser();
	//刷新产品缓存
	public void refreshProduct();
	//刷新NPVR缓存
	public void refreshNpvr();
	//刷新影片缓存
	public void refreshAseet();
	
	
	//缓存订单投放次数
	public void refreshOrder();
	//存储投放次数至数据库
	public void saveOrderPlayTime();
	
	
	
	
	//存储日志记录
	public void saveHistory();
	//存储投放状态
	public void saveHistoryStatus();
	
}
