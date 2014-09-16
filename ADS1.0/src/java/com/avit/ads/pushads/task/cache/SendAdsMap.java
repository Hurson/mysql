package com.avit.ads.pushads.task.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.SendAds;

// TODO: Auto-generated Javadoc
/**
 * 发送文件缓存.
 * Key=广告位编码:频道ID
 * Data=List(SendAds) 投放数据Bean
 * 启动播出单投放时，调用addAds,添加播出单
 * 启动资源数据投放时，调用getAdsList
 * 
 * 播出单结束补默认素材时，调用deleteFile，删除播出单对应记录，如无其他播出单时候用，直接删除资源文件
 */
public class SendAdsMap {
	
	/**  
	 * 发送文件缓存.
	 * Key=filename
	 * Data=List(播出单单号). 
	 * */
	private static HashMap<String,SendAds> adsMap = new HashMap<String,SendAds>();
	
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();

	/**
	 * 启动播出单时调用,添加播出单列表
	 * @param key 广告位编码:频道ID
	 * @param ads 播出单
	 * @return 返回1 成功 0 失败
	 */
	public static int addAds(String key,SendAds ads)
	{
		lock.lock();
		///newTJMap.put(nodeElement.attributeValue("firstspell"), subNodeList);ads
		adsMap.put(key, ads);
		lock.unlock();
		return 1;
	}
	
	/**
	 * 补空线程在播出单结束时调用
	 * 删除播出单对应记录，更新结束播出单的单号为0.
	 * 写数据库，记录播出单状态为已完成
	 *
	 * @param endTime 结束时间
	 * @param adsTypeCode 广告位编码
	 * @param adsIdentification 特征值
	 * @param filename    默认素材
	 * @return 返回 结束的播出单列表
	 */
	public static List<SendAds> deleteAds(Date endTime,String adsTypeCode,String adsIdentification,String filename)
	{
		List retList=null;
		lock.lock();
		
		lock.unlock();
		return retList;
	}
	
	/**
	 * 投放数据时调用，读取现有投放文件列表.
	 *
	 * @param adsTypeCode 广告位编码
	 * @return the file list
	 */
	public static List<SendAds> getAdsList(String adsTypeCode,String adsIdentification)
	{
		List retList=null;
		lock.lock();
		
		lock.unlock();
		return retList;
	}
	
}
