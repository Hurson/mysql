package com.avit.ads.requestads.service;

import javax.inject.Inject;

import org.apache.log4j.Logger;


public class CacheJob {
	@Inject
	private RefreshCacheService refreshService;
	private Logger logger = Logger.getLogger(CacheJob.class);
	private static boolean refreshPlayListCacheFlag=false;
	private static boolean refreshBaseDataFlag=false;
	private static boolean refreshOrderFlag=false;
	private static boolean saveHistoryFlag=false;
	
	public void refreshPlayListCache()
	{
		logger.info("start CacheJob refreshPlayListCache 刷新");
		//第一次初始化未完成
		if (InitTimerTask.startFlag==false)
		{
			logger.info("InitTimerTask.startFlag值为FALSE，未 刷新");
			return ;
		}
		if (refreshPlayListCacheFlag==false)
		{
			refreshPlayListCacheFlag=true;
			refreshService.refreshPlayListCache();
			refreshService.refreshNoAdPloy();
			refreshPlayListCacheFlag=false;
		}
		logger.info("end CacheJob refreshPlayListCache 刷新");
	}
	public void refreshBaseData()
	{
		//第一次初始化未完成
		if (InitTimerTask.startFlag==false)
		{
			return ;
		}
		if (refreshBaseDataFlag==false)
		{
			refreshBaseDataFlag=true;
			refreshService.refreshAseet();
			refreshService.refreshNpvr();
			refreshService.refreshProduct();		
			refreshService.refreshCategory();
			refreshService.refreshLocation();
			refreshBaseDataFlag=false;
		}
		//refreshService.refreshUser();
	}
	public void refreshOrder()
	{
		//第一次初始化未完成
		if (InitTimerTask.startFlag==false)
		{
			return ;
		}
		if (refreshOrderFlag==false)
		{
			refreshOrderFlag=true;
			//存储当前次数
			refreshService.saveOrderPlayTime();
			//重新刷新缓存
			refreshService.refreshOrder();
			refreshOrderFlag=false;
		}
	}
	public void saveHistory()
	{
		//第一次初始化未完成
		if (InitTimerTask.startFlag==false)
		{
			return ;
		}
		if (saveHistoryFlag==false)
		{
			saveHistoryFlag=true;
			//存储决策记录
			refreshService.saveHistory();
			//存储决策投放状态
			refreshService.saveHistoryStatus();
			saveHistoryFlag=false;
		}
	}
}
