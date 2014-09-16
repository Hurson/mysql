package com.avit.ads.pushads.task.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.AdvertPosition;
import com.avit.ads.pushads.task.bean.SendAds;
import com.avit.ads.pushads.task.bean.TChannelinfo;

public class AdvertPositionMap {
	private static List<AdvertPosition> adpositionMap = new ArrayList<AdvertPosition>();
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	public static List<AdvertPosition> getAdpositionMapMap() {
		return adpositionMap;
	}
	public static void setAdpositionMapMap(List<AdvertPosition> adpositionMap) {
		lock.lock();
		AdvertPositionMap.adpositionMap = adpositionMap;
		lock.unlock();
	}
	public static AdvertPosition getAdvertPositionByCode(String adCode)
	{
		AdvertPosition temp=null;
		lock.lock();
		AdvertPositionMap.adpositionMap = adpositionMap;
		for (int i=0;i<adpositionMap.size();i++)
		{
			if (adpositionMap.get(i).getPositionCode().equals(adCode))
			{
				temp=adpositionMap.get(i);
			}
		}
		lock.unlock();
		return temp;
	}
}
