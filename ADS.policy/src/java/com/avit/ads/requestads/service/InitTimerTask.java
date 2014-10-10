package com.avit.ads.requestads.service;

import java.util.TimerTask;

import com.avit.ads.util.ContextHolder;

public class InitTimerTask extends TimerTask {
	RefreshCacheService refreshCacheService; 
	public static boolean startFlag=false;
	public InitTimerTask() {
		// TODO Auto-generated constructor stub
		refreshCacheService = (RefreshCacheService)ContextHolder.getApplicationContext().getBean("RefreshCacheService");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		refreshPlayListCache();
		refreshOrder();
		refreshBaseData();
		startFlag=true;
	}
	public void refreshPlayListCache()
	{
		refreshCacheService.refreshPlayListCache();
		refreshCacheService.refreshNoAdPloy();
		
	}
	public void refreshBaseData()
	{
		refreshCacheService.refreshAseet();
		refreshCacheService.refreshNpvr();
		refreshCacheService.refreshProduct();		
		refreshCacheService.refreshCategory();
		refreshCacheService.refreshUser();
	}
	public void refreshOrder()
	{
		//重新刷新缓存
		refreshCacheService.refreshOrder();
	}
}
