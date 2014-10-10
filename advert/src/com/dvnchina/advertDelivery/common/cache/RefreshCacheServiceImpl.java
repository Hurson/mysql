package com.dvnchina.advertDelivery.common.cache;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.dvnchina.advertDelivery.dao.CacheDao;
import com.dvnchina.advertDelivery.ploy.bean.LocationCode;
import com.dvnchina.advertDelivery.utils.StringUtil;




public class RefreshCacheServiceImpl implements RefreshCacheService {
	
    private CacheDao cacheDao ;
	
	public CacheDao getCacheDao() {
        return cacheDao;
    }
    public void setCacheDao(CacheDao cacheDao) {
        this.cacheDao = cacheDao;
    }
    public static List<LocationCode> listLocation ;	


	
	public void refreshLocation()
	{
		//List<TLocationCode> 
		listLocation = cacheDao.queryLocationCode();
		Long oldId=0L;
		List<Long> listChild =  new ArrayList<Long> ();
		if (listLocation!=null)
		{
			for (int i=0;i<listLocation.size();i++)
			{
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())>2)
				{
					break;
				}
				LocationCacheBean cacheBean= new LocationCacheBean();
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==2)
				{
					cacheBean.setLocationcode1(listLocation.get(i).getLocationcode());
					cacheBean.setLocationcode2(0L);
					cacheBean.setLocationcode3(0L);
					cacheBean.setLocationcode4(0L);
					
					cacheBean.setLocationName(listLocation.get(i).getLocationname());
					cacheBean.setLocationName1(listLocation.get(i).getLocationname());
				}
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==3)
				{
					cacheBean.setLocationcode2(listLocation.get(i).getLocationcode());
					cacheBean.setLocationcode3(0L);
					cacheBean.setLocationcode4(0L);
				}
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==4)
				{
					cacheBean.setLocationcode3(listLocation.get(i).getLocationcode());
					cacheBean.setLocationcode4(0L);
				}
				if (StringUtil.toInt(listLocation.get(i).getLocationtype())==5)
				{
					cacheBean.setLocationcode4(listLocation.get(i).getLocationcode());
				}
				
				LocationCache.addMap(listLocation.get(i).getLocationcode(), cacheBean);	
				addChildLoction(listLocation.get(i).getLocationcode(),StringUtil.toInt(listLocation.get(i).getLocationtype()),i+1,cacheBean);
				//LocationCache.addMap(listLocation.get(i).getLocationcode(), listChild);
			}
			LocationCache.updateMap();
		}
	}
	public List<Long> addChildLoction(Long parentId,int parentType,int index,LocationCacheBean cacheBean)
	{
		if (listLocation!=null)
		{
			for (int i=index;i<listLocation.size();i++)
			{
				if (parentId.equals(listLocation.get(i).getParentlocation()))
				{
					if (152010000008L==listLocation.get(i).getLocationcode())
					{
						System.out.println();
					}
					if (152010000007L==listLocation.get(i).getLocationcode())
					{
						System.out.println();
					}
					if (parentType==2)
					{
						cacheBean.setLocationcode1(parentId);
						cacheBean.setLocationcode2(0L);
						cacheBean.setLocationcode3(0L);
						cacheBean.setLocationcode4(0L);
						
						cacheBean.setLocationName(listLocation.get(i).getLocationname());
						//cacheBean.setLocationName1(cacheBean.getLocationName1());
						cacheBean.setLocationName2(listLocation.get(i).getLocationname());
						
					
					}
					if (parentType==3)
					{
						cacheBean.setLocationcode2(parentId);
						cacheBean.setLocationcode3(0L);
						cacheBean.setLocationcode4(0L);
						
						cacheBean.setLocationName(listLocation.get(i).getLocationname());
						//cacheBean.setLocationName2(cacheBean.getLocationName());
						cacheBean.setLocationName3(listLocation.get(i).getLocationname());
					}
					if (parentType==4)
					{
						cacheBean.setLocationcode3(parentId);
						cacheBean.setLocationcode4(0L);
						
						cacheBean.setLocationName(listLocation.get(i).getLocationname());
						cacheBean.setLocationName4(listLocation.get(i).getLocationname());
					}
					if (parentType==5)
					{
						cacheBean.setLocationcode4(parentId);
					}
					addChildLoction(listLocation.get(i).getLocationcode(),StringUtil.toInt(listLocation.get(i).getLocationtype()),i+1,cacheBean);
					
					LocationCacheBean temp = new LocationCacheBean();
					temp.setLocationcode(listLocation.get(i).getLocationcode());
					temp.setLocationcode1(cacheBean.getLocationcode1());
					temp.setLocationcode2(cacheBean.getLocationcode2());
					temp.setLocationcode3(cacheBean.getLocationcode3());
					temp.setLocationcode4(cacheBean.getLocationcode4());
					temp.setLocationName(listLocation.get(i).getLocationname());
					temp.setLocationName1(cacheBean.getLocationName1());
					temp.setLocationName2(cacheBean.getLocationName2());
					temp.setLocationName3(cacheBean.getLocationName3());
					LocationCache.addMap(listLocation.get(i).getLocationcode(), temp);					
					
					
				}
			}
			
		}
		return null;
	}
	

}
