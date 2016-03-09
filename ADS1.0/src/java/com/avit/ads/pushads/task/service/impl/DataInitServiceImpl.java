package com.avit.ads.pushads.task.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.avit.ads.dtmb.bean.DOcgInfo;
import com.avit.ads.dtmb.cache.DtmbAdPositionMap;
import com.avit.ads.dtmb.cache.DtmbChannelMap;
import com.avit.ads.dtmb.dao.DOcgInfoDao;
import com.avit.ads.pushads.ocg.dao.OcgInfoDao;
import com.avit.ads.pushads.task.bean.AdDefault;
import com.avit.ads.pushads.task.bean.OcgInfo;
import com.avit.ads.pushads.task.bean.PicMaterial;
import com.avit.ads.pushads.task.cache.AdvertPositionMap;
import com.avit.ads.pushads.task.cache.ChannelMap;
import com.avit.ads.pushads.task.cache.ChannelNpvrMap;
import com.avit.ads.pushads.task.cache.LookbackCategoryMap;
import com.avit.ads.pushads.task.cache.NvodMenuTypeMap;
import com.avit.ads.pushads.task.dao.DatainitDao;
import com.avit.ads.pushads.task.dao.InitAreasDao;
import com.avit.ads.pushads.task.service.DataInitService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
@Service("DatainitService")
public class DataInitServiceImpl implements DataInitService {
	/** The push ads dao. */
	@Inject
	DatainitDao datainitDao;
	@Inject
	private InitAreasDao initAreasDao;
	@Inject
	private OcgInfoDao ocgInfoDao;
	@Inject
	private DOcgInfoDao docgInfoDao;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public void initChannel() {
		// TODO Auto-generated method stub
		ChannelMap.setChannelMap(datainitDao.queryChannel());
	}
	public void initDtmbChannel() {

		DtmbChannelMap.setDtmbChannelMap(datainitDao.queryDtmbChannel());
	}
	public void initNvodMenuType(){
		NvodMenuTypeMap.setMenuTypeMap(datainitDao.queryNvodMenuType());
	}
	public void initChannelNpvr() {
		// TODO Auto-generated method stub
		ChannelNpvrMap.setChannelMap(datainitDao.queryChannelNpvr());
	}

	public void initLookbackCategory() {
		// TODO Auto-generated method stub
		LookbackCategoryMap.setCategoryMap(datainitDao.queryLookbackCategory());
	}
	public void initAreaTSFile(){
		
		String path = InitConfig.getConfigMap().get(ConstantsHelper.DEST_FILE_PATH);
		List<String> areaCodeList = initAreasDao.getAreas();
		try{
			for(String areaCode : areaCodeList){
				String destPath = path + File.separator + areaCode;
				File file = new File(destPath);
				//创建县级及以下目录
				if(!file.exists()){
					file = new File(destPath + File.separator + ConstantsHelper.NORMAL_FILE);
					file.mkdirs();
					file = new File(destPath + File.separator + ConstantsHelper.CHANNEL_SUBTITLE);
					file.mkdirs();
					file = new File(destPath + File.separator + ConstantsHelper.CHANNEL_RECOMMEND);
					file.mkdirs();
					file = new File(destPath + File.separator + ConstantsHelper.SEND_FILE);
					file.mkdirs();
				}
				List<OcgInfo> ocgInfoList = ocgInfoDao.getOcgMulticastInfoList(areaCode, null);
				for(OcgInfo ocg : ocgInfoList){
					String fileName = destPath + File.separator + ConstantsHelper.CHANNEL_RECOMMEND + File.separator + ocg.getTsId() + ConstantsHelper.DATA_FILE_SUFFIX;
					File tsFile = new File(fileName);
					if(!tsFile.exists()){
						tsFile.createNewFile();
					}
				}
			
			}
		}catch(Exception e){
			logger.error("init area ts file error" + e);
		}
		//logger.info("初始化各县频点信息完成！");
	}
	public void initDtmbAreaTSFile(){
		
		String path = InitConfig.getConfigMap().get(ConstantsHelper.D_DEST_FILE_PATH);
		List<String> areaCodeList = initAreasDao.getAreas();
		try{
			for(String areaCode : areaCodeList){
				String destPath = path + File.separator + areaCode;
				File file = new File(destPath);
				//创建县级及以下目录
				if(!file.exists()){
					file = new File(destPath + File.separator + ConstantsHelper.NORMAL_FILE);
					file.mkdirs();
					file = new File(destPath + File.separator + ConstantsHelper.CHANNEL_SUBTITLE);
					file.mkdirs();
					file = new File(destPath + File.separator + ConstantsHelper.CHANNEL_RECOMMEND);
					file.mkdirs();
					file = new File(destPath + File.separator + ConstantsHelper.SEND_FILE);
					file.mkdirs();
				}
				List<DOcgInfo> ocgInfoList = docgInfoDao.getOcgMulticastInfoList(areaCode, null);
				for(DOcgInfo ocg : ocgInfoList){
					String fileName = destPath + File.separator + ConstantsHelper.CHANNEL_RECOMMEND + File.separator + ocg.getTsId() + ConstantsHelper.DATA_FILE_SUFFIX;
					File tsFile = new File(fileName);
					if(!tsFile.exists()){
						tsFile.createNewFile();
					}
				}
			
			}
		}catch(Exception e){
			logger.error("init wireless area ts file error" + e);
		}
		//logger.info("初始化各县频点信息完成！");
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
	public 	void initDtmbAdPosition(){
		DtmbAdPositionMap.setAdpositionMapMap(datainitDao.queryDtmbAdPosition());
	}
}
