package com.avit.ads.dtmb.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.dtmb.bean.DAdPosition;

public class DtmbAdPositionMap {
	private static List<DAdPosition> adpositionMap = new ArrayList<DAdPosition>();
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	public static List<DAdPosition> getAdpositionMapMap() {
		return adpositionMap;
	}
	public static void setAdpositionMapMap(List<DAdPosition> adpositionMap) {
		lock.lock();
		DtmbAdPositionMap.adpositionMap = adpositionMap;
		lock.unlock();
	}
	public static DAdPosition getAdvertPositionByCode(String adCode)
	{
		DAdPosition temp=null;
		lock.lock();
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
	public static List<DAdPosition> getAdvertPositionMapByType(String type){
		List<DAdPosition> positionList = new ArrayList<DAdPosition>();
		lock.lock();
		for (int i=0;i<adpositionMap.size();i++)
		{
			if (type.equals(adpositionMap.get(i).getPositionType()))
			{
				positionList.add(adpositionMap.get(i));
			}
		}
		lock.unlock();
		return positionList;
	}
}
