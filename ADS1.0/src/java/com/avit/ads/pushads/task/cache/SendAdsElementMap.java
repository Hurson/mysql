package com.avit.ads.pushads.task.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.AdsElement;
import com.avit.ads.pushads.task.bean.NvodMenuType;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Ads;

//TODO: Auto-generated Javadoc
/**
 * 发送广告位配置缓存，用于生成配置文件adConfig.js.
 * Key=adsTypeCode:areaCode:channelServiceid/npvrserviceid/lookbackcategoryid/assetid
 * Data=AdsElement投放元素 
 * 启动播出单投放时，调用addAdsElement,添加/更新元素
 * 播出单结束补默认素材时，调用addAdsElement,添加/更新元素
 * 启动资源数据投放时，调用getAdsElementList
 */
public class SendAdsElementMap {
	
	/**  
	 * 
	 * Key=adsTypeCode:areaCode:channelCode
	 * Data=AdsElement 投放元素
	 * */
	private static HashMap<String,AdsElement> adsMap = new HashMap<String,AdsElement>();
	
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	
	public SendAdsElementMap()
	{
		//添加默认素材　记录
		//0100:0:0 
	}
	public static void initSendAdsElementMap()
	{
		//遍历配置单向实时广告位列表
		//for(int i =0;i<InitConfig.getAdsConfig().getRealTimeAds().getAdsList().size();i++)
		//{
			//System.out.println("Adcode:"+InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode()+";defaultres:"+InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes());
		//}
		// 14 * 328 = 4592  个对象       
		//TODO 菜单图片、视频外框与频道无关，应当另作处理
		//List<Ads> list = InitConfig.getAdsConfig().getRealTimeAds().getAdsList();
		for(int i =0;i<InitConfig.getAdsConfig().getRealTimeAds().getAdsList().size();i++)
		{
			Ads ads = InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i);
			//NVOD主菜单广告
			if(ConstantsAdsCode.NVOD_MENU.equals(ads.getAdsCode())){
				
				List<NvodMenuType> menuTypeList = NvodMenuTypeMap.getMenuTypeMap();
				for (int j = 0;j < menuTypeList.size(); j++){
					NvodMenuType menuType = menuTypeList.get(j);
					AdsElement tempElement = new AdsElement(ads.getAdsCode(),"0",menuType.getTypeCode(),ads.getDefaultRes());
					adsMap.put(ads.getAdsCode()+":0:"+menuType.getTypeCode(), tempElement);
				}
				
			}else if(ConstantsAdsCode.CORNER_APPROACH.equals(ads.getAdsCode())){
				AdsElement tempElement = new AdsElement(ads.getAdsCode(),"0","0",ads.getDefaultRes());
				adsMap.put(ads.getAdsCode()+":0:"+"0", tempElement);
			}else{
				//添加默认素材　记录
				//key=adsTypeCode:0:0 value= AdsConfigElement(高标清特征值采用相同素材)		
				for (int j=0;j<ChannelMap.getChannelMap().size();j++)
				{
					AdsElement tempElement = new AdsElement(ads.getAdsCode(),"0",ChannelMap.getChannelMap().get(j).getServiceId(),ads.getDefaultRes());
					adsMap.put(ads.getAdsCode()+":0:"+ChannelMap.getChannelMap().get(j).getServiceId(), tempElement);
				}
			}
			
		}
		//遍历配置ＣＰＳ广告位列表
		for(int i =0;i<InitConfig.getAdsConfig().getCpsAds().getAdsList().size();i++)
		{
			//添加默认素材　记录
			//key=adsTypeCode:0:0 value= AdsConfigElement(高标清特征值采用相同素材)			
			AdsElement tempElement = new AdsElement(InitConfig.getAdsConfig().getCpsAds().getAdsList().get(i).getAdsCode(),"0","0",InitConfig.getAdsConfig().getCpsAds().getAdsList().get(i).getDefaultRes());
			adsMap.put(InitConfig.getAdsConfig().getCpsAds().getAdsList().get(i).getAdsCode()+":0:"+"0", tempElement);
		}	
		
		//遍历配置NPVR广告位列表
		for(int i =0;i<InitConfig.getAdsConfig().getNpvrAds().getAdsList().size();i++)
		{
			//添加默认素材　记录
			//key=adsTypeCode:0:0 value= AdsConfigElement(高标清特征值采用相同素材)			
			AdsElement tempElement = new AdsElement(InitConfig.getAdsConfig().getNpvrAds().getAdsList().get(i).getAdsCode(),"0","0",InitConfig.getAdsConfig().getNpvrAds().getAdsList().get(i).getDefaultRes());
			adsMap.put(InitConfig.getAdsConfig().getNpvrAds().getAdsList().get(i).getAdsCode()+":0:"+"0", tempElement);
		}
		System.out.println();
	}
	public static void refreshSendAdsElementMap()
	{
		//遍历配置单向实时广告位列表
		for(int i =0;i<InitConfig.getAdsConfig().getRealTimeAds().getAdsList().size();i++)
		{
			System.out.println("Adcode:"+InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode()+";defaultres:"+InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes());
		}
		lock.lock();
		for(int i =0;i<InitConfig.getAdsConfig().getRealTimeAds().getAdsList().size();i++)
		{
			System.out.println("Adcode:"+InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode()+";defaultres:"+InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes());
			
			Ads ads = InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i);
			//NVOD主菜单广告
			if(ConstantsAdsCode.NVOD_MENU.equals(ads.getAdsCode())){
				
				List<NvodMenuType> menuTypeList = NvodMenuTypeMap.getMenuTypeMap();
				for (int j = 0;j < menuTypeList.size(); j++){
					NvodMenuType menuType = menuTypeList.get(j);
					AdsElement tempElement = new AdsElement(ads.getAdsCode(),"0",menuType.getTypeCode(),ads.getDefaultRes());
					adsMap.put(ads.getAdsCode()+":0:"+menuType.getTypeCode(), tempElement);
				}
			}else if(ConstantsAdsCode.CORNER_APPROACH.equals(ads.getAdsCode())){
				AdsElement tempElement = new AdsElement(ads.getAdsCode(),"0","0",ads.getDefaultRes());
				adsMap.put(ads.getAdsCode()+":0:"+"0", tempElement);
			}else{
				//添加默认素材　记录
				//key=adsTypeCode:0:0 value= AdsConfigElement(高标清特征值采用相同素材)			
				for (int j=0;j<ChannelMap.getChannelMap().size();j++)
				{
					AdsElement tempElement = new AdsElement(InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode(),"0",ChannelMap.getChannelMap().get(j).getServiceId(),InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getDefaultRes());
					AdsElement element=null;
					String key = InitConfig.getAdsConfig().getRealTimeAds().getAdsList().get(i).getAdsCode()+":0:"+ChannelMap.getChannelMap().get(j).getServiceId();
					element = adsMap.get(key);
					if (element==null)
					{
						adsMap.put(key, tempElement);
					}
				}
			}
		}
		lock.unlock();
		System.out.println();
	}
	public static void addAdsElement(String adsTypeCode,String areaCode,String paramCode,String path)
	{
		lock.lock();
		///newTJMap.put(nodeElement.attributeValue("firstspell"), subNodeList);ads
		AdsElement element=null;
		String key = adsTypeCode+":"+areaCode+":"+paramCode;
		element = adsMap.get(key);
		if (null==element)
		{
			element = new AdsElement(adsTypeCode,areaCode,paramCode,path);
			adsMap.put(key, element);
		}
		else
		{
			element.setFilepath(path);
		}
		/*
		//单频道所有单向广告位默认素材补空  旧js使用
		for(int i =0;i<InitConfig.getAdsConfig().getAdsList().size();i++)
		{
			if (InitConfig.getAdsConfig().getAdsList().get(i).getAdsCode().equals(ConstantsAdsCode.PUSH_STARTSTB))
			{
				continue;
			}
			key = InitConfig.getAdsConfig().getAdsList().get(i).getAdsCode()+":"+areaCode+":"+channelCode;
			AdsConfigElement elementtemp=null;
			elementtemp = adsMap.get(key);
			if (null==elementtemp)
			{
				//添加默认素材　记录
				//key=adsTypeCode:0:0 value= AdsConfigElement(高标清特征值采用相同素材)			
				AdsConfigElement tempElement = new AdsConfigElement(InitConfig.getAdsConfig().getAdsList().get(i).getAdsCode(),areaCode,channelCode,InitConfig.getAdsConfig().getAdsList().get(i).getDefaultRes(),InitConfig.getAdsConfig().getAdsList().get(i).getDefaultRes());
				adsMap.put(InitConfig.getAdsConfig().getAdsList().get(i).getAdsCode()+":"+areaCode+":"+channelCode, tempElement);
			}
		}
		*/
		lock.unlock();
	}
	
	public static void addAdsElement(String adsTypeCode,String areaCode,String paramCode,String path, Integer priority)
	{
		lock.lock();
		///newTJMap.put(nodeElement.attributeValue("firstspell"), subNodeList);ads
		AdsElement element=null;
		String key = adsTypeCode+":"+areaCode+":"+paramCode;
		element = adsMap.get(key);
		if (null==element)
		{
			element = new AdsElement(adsTypeCode,areaCode,paramCode,path, priority);
			adsMap.put(key, element);
		}
		else
		{
			element.setFilepath(path);
			element.setPriority(priority);
		}
		lock.unlock();
	}
	
	public static void addAdsElement(Integer playListId, String adsTypeCode,String areaCode,String paramCode,String path)
	{
		lock.lock();
		///newTJMap.put(nodeElement.attributeValue("firstspell"), subNodeList);ads
		AdsElement element=null;
		String key = adsTypeCode+":"+areaCode+":"+paramCode;
		element = adsMap.get(key);
		if (null==element)
		{
			element = new AdsElement(playListId, adsTypeCode,areaCode,paramCode,path);
			adsMap.put(key, element);
		}
		else
		{
			element.setFilepath(path);
			element.setPlayListId(playListId);
		}
		lock.unlock();
	}
	
	public static void deleteAdsElement(String adsTypeCode,String areaCode,String paramCode,String path)
	{
		lock.lock();
		///newTJMap.put(nodeElement.attributeValue("firstspell"), subNodeList);ads
		AdsElement element=null;
		String key = adsTypeCode+":"+areaCode+":"+paramCode;
		element = adsMap.get(key);
		if (null==element)
		{
			
		}
		else
		{
			adsMap.remove(key);
		}
		lock.unlock();
	}
	public static List<AdsElement> getAdsElement()
	{
		List<AdsElement> list =  new ArrayList<AdsElement>();
		lock.lock();
		Iterator iter = adsMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry) iter.next();
			//Object key = entry.getKey();
			Object val = entry.getValue();
			list.add((AdsElement)val);
		}
		lock.unlock();
		return list;
	}
	public static List<AdsElement> getRealTimeAdsElement()
	{
		List<AdsElement> list =  new ArrayList<AdsElement>();
		lock.lock();
		Iterator iter = adsMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			String keys[] = ((String)key).split(":");
			
			if (InitConfig.getAdsConfig().getRealTimeAds().getAdsByCode(keys[0])!=null)
			{
				Object val = entry.getValue();
				list.add((AdsElement)val);
			}
			
		}
		lock.unlock();
		return list;
	}
	public static List<AdsElement> getDefaultRealTimeAdsElement()
	{
		List<AdsElement> list =  new ArrayList<AdsElement>();
		lock.lock();
		Iterator iter = adsMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			String keys[] = ((String)key).split(":");
			
			if (InitConfig.getAdsConfig().getRealTimeAds().getAdsByCode(keys[0])!=null)
			{
				AdsElement val = (AdsElement)entry.getValue();
				String entityAreaCode = val.getAreaCode();
				if("0".equals(entityAreaCode)){
					list.add(val);
				}
			}
		}
		lock.unlock();
		return list;
	}
	public static List<AdsElement> getCpsAdsElement()
	{
		List<AdsElement> list =  new ArrayList<AdsElement>();
		lock.lock();
		Iterator iter = adsMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			String keys[] = ((String)key).split(":");
			
			if (InitConfig.getAdsConfig().getCpsAds().getAdsByCode(keys[0])!=null)
			{
				Object val = entry.getValue();
				list.add((AdsElement)val);
			}
			
		}
		lock.unlock();
		return list;
	}
	public static List<AdsElement> getNpvrAdsElement()
	{
		List<AdsElement> list =  new ArrayList<AdsElement>();
		lock.lock();
		Iterator iter = adsMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			String keys[] = ((String)key).split(":");
			
			if (InitConfig.getAdsConfig().getNpvrAds().getAdsByCode(keys[0])!=null)
			{
				Object val = entry.getValue();
				list.add((AdsElement)val);
			}
			
		}
		lock.unlock();
		return list;
	}
	public static HashMap<String, AdsElement> getAdsMap() {
		return adsMap;
	}

}
