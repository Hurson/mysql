package com.avit.ads.requestads.tast;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.service.DefaultResourceCache;
import com.avit.ads.requestads.service.MediaInfoCache;
import com.avit.ads.requestads.service.PlaylistCache;
import com.avit.ads.util.ConstantsHelper;

public class PlaylistCacheScheduled {
	static Logger logger = LoggerFactory.getLogger(PlaylistCacheScheduled.class);

	/** 是否第一次实例化*/
	private boolean playlistfirstTime = true;
	private boolean mediafirstTime = true;
	//private boolean questionfirstTime = true;
	private boolean defaultResourceTime = true;
	
	private TimerTask playlistCacheTimerTask = new TimerTask() {
		
		@Override
		public void run() {
			// 计数器（）
			int count = 3;
			while(count > 0){
				try {
					// 初始化的时候使用构造函数初始化，其他时候使用调用GenerateCacheModule方法初始化
					if(playlistfirstTime){
						PlaylistCache.getInstance();
						playlistfirstTime = false;
					} else {
						PlaylistCache.getInstance().GenerateCacheModule(true);
					}
					count = 0;
				} catch (Exception e) {
					logger.error("执行定时器更新请求时PlaylistCache时出错。\n"+e.getMessage());
					count = count - 1;
				}
			}
		}
	};
	
	private TimerTask mediaCacheTimerTask = new TimerTask() {
		
		@Override
		public void run() {
			// 计数器（）
			int count = 3;
			while(count > 0){
				try {
					// 初始化的时候使用构造函数初始化，其他时候使用调用GenerateCacheModule方法初始化
					if(mediafirstTime){
						MediaInfoCache.getInstance();
						mediafirstTime = false;
					}else{
						MediaInfoCache.getInstance().generateCache(true);
					}
					count = 0;
				} catch (Exception e) {
					logger.error("执行定时器更新请求时MediaInfoCache时出错。\n"+e.getMessage());
					count = count - 1;
				}
			}
		}
	};
	
	private TimerTask defaultResourceCacheTimerTask = new TimerTask() {
		
		@Override
		public void run() {
			// 计数器（）
			int count = 3;
			while(count > 0){
				try {
					// 初始化的时候使用构造函数初始化，其他时候使用调用GenerateCacheModule方法初始化
					if(defaultResourceTime){
						//DefaultResourceCache.getInstance();
						defaultResourceTime = false;
					} else {
						//DefaultResourceCache.getInstance().generateCache(true);
					}
					count = 0;
				} catch (Exception e) {
					logger.error("执行定时器更新请求时DefaultResourceCache时出错。\n"+e.getMessage());
					count = count - 1;
				}
			}
		}
	};

	public void timerScheduled() {
		
		Timer playlisttimer = new Timer();
		playlisttimer.schedule(playlistCacheTimerTask, getFirstTime(), ConstantsHelper.A_HOUR_CONTAINS_MILLISECONDS);
		
		Timer mediatimer = new Timer();
		mediatimer.schedule(mediaCacheTimerTask, getFirstTime(), ConstantsHelper.TF_HOUR_CONTAINS_MILLISECONDS);
		
		Timer defaultResourcetimer = new Timer();
		defaultResourcetimer.schedule(defaultResourceCacheTimerTask, getFirstTime(),  ConstantsHelper.TF_HOUR_CONTAINS_MILLISECONDS);
	}
	
	/**
	 * 获取第一次执行时间
	 * @return
	 */
    private static Date getFirstTime(){
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, 1);
    	c.set(Calendar.MINUTE, 4);
    	return c.getTime();
    }
}
