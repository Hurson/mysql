package com.avit.ads.pushads.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.dtv.service.impl.DtvServiceImpl;
import com.avit.ads.pushads.task.bean.AdDefault;
import com.avit.ads.pushads.task.bean.PicMaterial;
import com.avit.ads.pushads.task.cache.AdvertPositionMap;
import com.avit.ads.pushads.task.cache.ChannelMap;
import com.avit.ads.pushads.task.cache.ChannelNpvrMap;
import com.avit.ads.pushads.task.cache.LookbackCategoryMap;
import com.avit.ads.pushads.task.dao.DatainitDao;
import com.avit.ads.pushads.task.service.DataInitService;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.json.Obj2JsonUtil;
@Service("DatainitService")
public class DataInitServiceImpl implements DataInitService {
	/** The push ads dao. */
	@Inject
	DatainitDao datainitDao;
	private Logger logger = Logger.getLogger(this.getClass());
	
	public void initChannel() {
		// TODO Auto-generated method stub
		ChannelMap.setChannelMap(datainitDao.queryChannel());
	}

	public void initChannelNpvr() {
		// TODO Auto-generated method stub
		ChannelNpvrMap.setChannelMap(datainitDao.queryChannelNpvr());
	}

	public void initLookbackCategory() {
		// TODO Auto-generated method stub
		LookbackCategoryMap.setCategoryMap(datainitDao.queryLookbackCategory());
	}
	public void  initAdDefalult()
	{
		List<AdDefault> defaultList = datainitDao.queryAdDefalult();
		List<PicMaterial>   loopSdList,loopHdList;
		loopSdList = new ArrayList();
		loopHdList = new ArrayList();
		
		for (int i=0;i<defaultList.size();i++)
		{
			for (int j=0;j<InitConfig.getAdList().size();j++)
			{
				if (defaultList.get(i).getPOSITIONCODE().equals(InitConfig.getAdList().get(j).getAdsCode()))
				{
					logger.info(defaultList.get(i).getPOSITIONCODE()+":"+defaultList.get(i).getDEFAULTFILE());
					InitConfig.getAdList().get(j).setDefaultRes(defaultList.get(i).getDEFAULTFILE());
					break;
				}				
			}
			if (defaultList.get(i).getPOSITIONCODE().equals("02011"))
			{
				PicMaterial loopPic = new PicMaterial(); 
				loopPic.setPic(defaultList.get(i).getDEFAULTFILE());
				loopSdList.add(loopPic);
			}
			if (defaultList.get(i).getPOSITIONCODE().equals("02012"))
			{
				PicMaterial loopPic = new PicMaterial(); 
				loopPic.setPic(defaultList.get(i).getDEFAULTFILE());
				loopHdList.add(loopPic);
			}
		}
		
		for (int i=0;i<defaultList.size();i++)
		{
			for (int j=0;j<InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().size();j++)
			{
				if (defaultList.get(i).getPOSITIONCODE().equals("01001"))
				{
					break;
				}
				if (defaultList.get(i).getPOSITIONCODE().equals("01002"))
				{
					break;
				}
				if (defaultList.get(i).getPOSITIONCODE().equals(InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(j).getAdsCode()))
				{
					logger.info(defaultList.get(i).getPOSITIONCODE()+":"+defaultList.get(i).getDEFAULTFILE());
					InitConfig.getAdsConfig().getUnRealTimeAds().getAdsList().get(j).setDefaultRes(defaultList.get(i).getDEFAULTFILE());
					break;
				}				
			}
			
		}
		
		
		for (int j=0;j<InitConfig.getAdList().size();j++)
		{
			if (InitConfig.getAdList().get(j).getAdsCode().equals("02011"))
			{
				String temp ="";// Obj2JsonUtil.list2json(loopSdList);
				for (int k=0;k<loopSdList.size();k++)
				{
					temp=temp+loopSdList.get(k).getPic();
					if (k<loopSdList.size()-1)
					{
						temp=temp+",";
					}
				}
				if (!temp.equals(""))
				{
				InitConfig.getAdList().get(j).setDefaultRes(temp);
				}
				continue;
			}
			if (InitConfig.getAdList().get(j).getAdsCode().equals("02012"))
			{
				String temp="";// = Obj2JsonUtil.list2json(loopHdList);
				for (int k=0;k<loopHdList.size();k++)
				{
					temp=temp+loopHdList.get(k).getPic();
					if (k<loopHdList.size()-1)
					{
						temp=temp+",";
					}
				}
				if (!temp.equals(""))
				{
				InitConfig.getAdList().get(j).setDefaultRes(temp);
				}
				continue;
			}				
		}
	//	InitConfig.getAdsConfig().getAdList().getAdsList().get(1).setDefaultRes("");
	//	defaultList.get(2).getDefaultFile();
		
	}
	public 	void initAdvertPosition()
	{
		AdvertPositionMap.setAdpositionMapMap(datainitDao.queryAdvertPosition());
	}
}
