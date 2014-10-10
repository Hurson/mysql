package com.avit.ads.requestads.service;

import java.util.TimerTask;

import com.avit.ads.util.ContextHolder;

public class RefreshTimerTask extends TimerTask {
	RefreshCacheService refreshCacheService; 
	public static boolean startFlag=false;
	public RefreshTimerTask() {
		// TODO Auto-generated constructor stub
		refreshCacheService = (RefreshCacheService)ContextHolder.getApplicationContext().getBean("RefreshCacheService");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		refreshPlayListCache();
		refreshOrder();
	}
	public void refreshPlayListCache()
	{
		refreshCacheService.refreshPlayListCache();
		refreshCacheService.refreshNoAdPloy();
		
	}	
	public void refreshOrder()
	{
		//重新刷新缓存
		refreshCacheService.saveOrderPlayTime();		
		refreshCacheService.refreshOrder();
	}
}
