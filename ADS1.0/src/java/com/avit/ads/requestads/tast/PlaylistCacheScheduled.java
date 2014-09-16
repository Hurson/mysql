package com.avit.ads.requestads.tast;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.service.PlaylistCache;
import com.avit.ads.util.ConstantsHelper;

public class PlaylistCacheScheduled {
	static Logger logger = LoggerFactory.getLogger(PlaylistCacheScheduled.class);
	
	/** 是否第一次实例化*/
	private boolean firstTime = true;
	
	private TimerTask playlistCacheTimerTask = new TimerTask() {
		
		@Override
		public void run() {
			// 计数器（）
			int count = 3;
			while(count > 0){
				try {
					// 初始化的时候使用构造函数初始化，其他时候使用调用GenerateCacheModule方法初始化
					if(firstTime){
						PlaylistCache.getInstance();
						firstTime = false;
					} else {
						PlaylistCache.getInstance().GenerateCacheModule(true);
					}
					count = 0;
				} catch (Exception e) {
					logger.error("执行定时器更新请求时cache时出错。\n"+e.getMessage());
					count = count - 1;
				}
			}
		}
	};

	public void timerScheduled() {
		Timer timer = new Timer();
		logger.info("timerScheduled +++++");
		timer.schedule(playlistCacheTimerTask,
				new Date(System.currentTimeMillis()),
				ConstantsHelper.A_HOUR_CONTAINS_MILLISECONDS);
	}
}
