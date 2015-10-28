package com.dvnchina.advertDelivery.ploy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.BeanUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.order.bean.MenuType;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyBackup;
import com.dvnchina.advertDelivery.ploy.bean.PloyTimeCGroup;
import com.dvnchina.advertDelivery.ploy.bean.PreciseConstants;
import com.dvnchina.advertDelivery.ploy.bean.PreciseData;
import com.dvnchina.advertDelivery.ploy.bean.PreciseUiBean;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.dao.PloyDao;
import com.dvnchina.advertDelivery.ploy.service.PloyService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.order.bean.MenuType;

public class PloyServiceImpl implements PloyService {

	PloyDao ployDao;
	
	public PloyDao getPloyDao() {
		return ployDao;
	}

	public void setPloyDao(PloyDao ployDao) {
		this.ployDao = ployDao;
	}
	

	@Override
	public PageBeanDB getAdPloyList(PloyBackup ploy,AdvertPosition adPosition, Integer pageSize,Integer pageNumber, String userIds) {
		return ployDao.getAdPloyList(ploy, adPosition, pageSize, pageNumber, userIds);
	}
	

	@Override
	public PageBeanDB getAdPositionByPackageIds(String packageIds, Integer pageSize, Integer pageNumber) {
		return  ployDao.getAdPositionByPackageIds(packageIds, pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryPloyList(PloyBackup ploy,Contract contract,AdvertPosition adPosition,String customerIds, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return ployDao.queryPloyList(ploy, contract,adPosition,customerIds, pageSize, pageNumber);
	}
	public PageBeanDB queryPloyList(PloyBackup ploy,String customerIds,String positionPackageIds,AdvertPosition adPosition,Integer pageSize, Integer pageNumber)
	{
		return ployDao.queryPloyList(ploy, customerIds, positionPackageIds,adPosition, pageSize, pageNumber);
	}
    
	public   List queryExitsPloyList(PloyBackup ploy)
	{
		return ployDao.queryExitsPloyList(ploy);
	}
	@Override
	public PageBeanDB queryCheckPloyList(PloyBackup ploy,Contract contract,AdvertPosition adPosition,String customerIds, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return ployDao.queryCheckPloyList(ploy, contract,adPosition,customerIds, pageSize, pageNumber);
	}
	@Override
	public PloyBackup getPloyByPloyID(int ployId) {
		PloyBackup ploy = ployDao.getPloyByPloyID(ployId);
		
		/*Ploy ploy = new Ploy();
		ploy.setPloyId(ployId);
		
		PageBeanDB ployPage = new PageBeanDB();
		ployPage= queryPloyList(ploy,null,null,null,1000,1);
		if (ployPage!=null && ployPage.getDataList()!=null && ployPage.getDataList().size()>0)
		{
			return (Ploy)ployPage.getDataList().get(0);
		}
		else
		{
			return null;
		}
		*/
		int contractId = ploy.getContractId();
		int adsiteId = ploy.getPositionId();
		int ruleId = ploy.getRuleId();
		//返回3个名称
		Object[] obj = ployDao.getPloyInfo(contractId, adsiteId, ruleId);
		if(obj != null){
			ploy.setContractName(obj[0].toString());
			ploy.setPositionName(obj[1].toString());
			ploy.setRuleName(obj[2].toString());
		}
		return ploy;
	}

	@Override
	public List<PloyAreaChannel> getAreaChannelsByPloyID(int ployId) {
		 List<PloyAreaChannel> list = ployDao.getAreaChannelsByPloyID(ployId);
		return list;
	}
	public PloyTimeCGroup getPloyTimeCGroupByPloyID(int ployId, String channelGroupType)
	{
		return ployDao.getPloyTimeCGroupByPloyID(ployId, channelGroupType);
	}
	public List getNvodInfoByPloyID(int ployId){
		List<TPreciseMatchBk> preciseList = ployDao.queryPreciseList(String.valueOf(ployId));
		PreciseUiBean preciseUiBean=null;
		List typeMenuList = new ArrayList();
		if (preciseList!=null && preciseList.size()>0){
			for(int i=0;i<preciseList.size();i++){
				if(preciseList.get(i).getMenuTypeCode()!=null && !preciseList.get(i).getMenuTypeCode().equals(""))
				{
					PreciseData datatemp = new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getMenuTypeCode());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					typeMenuList.add(datatemp);
				}
			}
			return typeMenuList;
		}else{
			return null;
		}
	}
	public PreciseUiBean getPreciseUiBeanByPloyID(int ployId)
	{
		List<TPreciseMatchBk> preciseList = ployDao.queryPreciseList(String.valueOf(ployId));
		PreciseUiBean preciseUiBean=null;
		if (preciseList!=null && preciseList.size()>0)
		{
			preciseUiBean = new PreciseUiBean();
			List productIdList = new ArrayList();
			List assetKeyList = new ArrayList();
			List assetSortIdList = new ArrayList();
			List assetNameList = new ArrayList();			
			//private String dtvChannelId;
			List playbackChannelIdList = new ArrayList();
			List lookbackCategoryIdList = new ArrayList();
			List groupIdList= new ArrayList();
			List userAreaList = new ArrayList();
			List userArea2List = new ArrayList();
			List userArea3List = new ArrayList();
			List userindustrysList = new ArrayList();
			List userlevelsList = new ArrayList();
			List userTypeList = new ArrayList();
			List typeMenuList = new ArrayList();
			List tvnNumberList = new ArrayList();
			
			for(int i=0;i<preciseList.size();i++)
			{
				if (preciseList.get(i).getProductId()!=null && !preciseList.get(i).getProductId().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getProductId());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					productIdList.add(datatemp);
					preciseUiBean.setPriority1(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression1(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber1(preciseList.get(i).getTvnNumber());	
				}
				if (preciseList.get(i).getAssetKey()!=null && !preciseList.get(i).getAssetKey().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getAssetKey());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					assetKeyList.add(datatemp);
					preciseUiBean.setPriority2(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression2(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber2(preciseList.get(i).getTvnNumber());	
				}
				if (preciseList.get(i).getAssetSortId()!=null && !preciseList.get(i).getAssetSortId().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getAssetSortId());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					assetSortIdList.add(datatemp);
					preciseUiBean.setPriority4(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression4(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber4(preciseList.get(i).getTvnNumber());	
				}
				if (preciseList.get(i).getPlaybackChannelId()!=null && !preciseList.get(i).getPlaybackChannelId().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getPlaybackChannelId());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					playbackChannelIdList.add(datatemp);
					preciseUiBean.setPriority5(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression5(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber5(preciseList.get(i).getTvnNumber());	
				}
				//回看栏目
				if (preciseList.get(i).getLookbackCategoryId()!=null && !preciseList.get(i).getLookbackCategoryId().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getLookbackCategoryId());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					lookbackCategoryIdList.add(datatemp);
					preciseUiBean.setPriority6(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression6(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber6(preciseList.get(i).getTvnNumber());	
				}
				//影片
				if (preciseList.get(i).getAssetName()!=null && !preciseList.get(i).getAssetName().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getAssetName());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					assetNameList.add(datatemp);
					preciseUiBean.setPriority8(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression8(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber8(preciseList.get(i).getTvnNumber());	
				}
				if(preciseList.get(i).getPrecisetype()==17){
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getTvnNumber());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					tvnNumberList.add(datatemp);
					preciseUiBean.setTvnExpression17(preciseList.get(i).getTvnExpression());
					preciseUiBean.setPriority17(preciseList.get(i).getPriority());
				}
				//投放区域
				/*if (preciseList.get(i).getUserArea()!=null && !preciseList.get(i).getUserArea().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getUserArea());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					userAreaList.add(datatemp);
					preciseUiBean.setPriority11(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression11(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber11(preciseList.get(i).getTvnNumber());	
				}*/
				//频道组
				if (preciseList.get(i).getGroupId()!=null && !preciseList.get(i).getGroupId().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getGroupId());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					groupIdList.add(datatemp);
					preciseUiBean.setPriority10(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression10(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber10(preciseList.get(i).getTvnNumber());	
				}
				//用户区域
				if (preciseList.get(i).getUserArea()!=null && !preciseList.get(i).getUserArea().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getUserArea());
					datatemp.setData2value(preciseList.get(i).getUserArea2());
					datatemp.setData3value(preciseList.get(i).getUserArea3());
					
					datatemp.setDataname(preciseList.get(i).getMatchName());
					userAreaList.add(datatemp);
					preciseUiBean.setPriority11(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression11(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber11(preciseList.get(i).getTvnNumber());	
				}
				//行业
				if (preciseList.get(i).getUserindustrys()!=null && !preciseList.get(i).getUserindustrys().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getUserindustrys());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					userindustrysList.add(datatemp);
					preciseUiBean.setPriority12(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression12(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber12(preciseList.get(i).getTvnNumber());	
				}
				//级别
				if (preciseList.get(i).getUserlevels()!=null && !preciseList.get(i).getUserlevels().equals(""))
				{
					PreciseData datatemp =  new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getUserlevels());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					userlevelsList.add(datatemp);
					preciseUiBean.setPriority13(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression13(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber13(preciseList.get(i).getTvnNumber());	
				}
				if(preciseList.get(i).getMenuTypeCode()!=null && !preciseList.get(i).getMenuTypeCode().equals(""))
				{
					PreciseData datatemp = new PreciseData();
					datatemp.setDatavalue(preciseList.get(i).getMenuTypeCode());
					datatemp.setDataname(preciseList.get(i).getMatchName());
					typeMenuList.add(datatemp);
					preciseUiBean.setPriority18(preciseList.get(i).getPriority());
					preciseUiBean.setTvnExpression18(preciseList.get(i).getTvnExpression());
					preciseUiBean.setTvnNumber18(preciseList.get(i).getTvnNumber());
				}
			}
			preciseUiBean.setProductIdList(productIdList);
			preciseUiBean.setAssetKeyList(assetKeyList);
			preciseUiBean.setAssetSortIdList(assetSortIdList);
			preciseUiBean.setPlaybackChannelIdList(playbackChannelIdList);
			preciseUiBean.setLookbackCategoryIdList(lookbackCategoryIdList);
			preciseUiBean.setAssetNameList(assetNameList);
			preciseUiBean.setUserAreaList(userAreaList);
			preciseUiBean.setGroupIdList(groupIdList);
			preciseUiBean.setUserAreaList(userAreaList);
			preciseUiBean.setUserindustrysList(userindustrysList);
			preciseUiBean.setUserlevelsList(userlevelsList);
			preciseUiBean.setTvnNumberList(tvnNumberList);
			preciseUiBean.setTypeMenuList(typeMenuList);
			
		}
		return preciseUiBean;
	}
	public PageBeanDB queryCustomer(Integer pageSize, Integer pageNumber)
	{
		return ployDao.queryCustomer(pageSize, pageNumber);
	}
	@Override
	public PageBeanDB queryContract( Contract contract,String customerIds,Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return ployDao.queryContract(contract,customerIds, pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryAdPosition(String customerIds,Integer contractId,
			AdvertPosition adPosition,Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return ployDao.queryAdPosition(customerIds,contractId, adPosition, pageSize, pageNumber);
	}
	 /**
 	 * 查询可选广告位
 	 * @param customerIds 广告商ID
 	 * @param positionPackageIds 广告位类型ID
 	 * @return the page bean
 	 */
	@Override
	public PageBeanDB queryAdPosition(String customerIds,String positionPackageIds ,Integer pageSize, Integer pageNumber)
	{
		return ployDao.queryAdPosition(customerIds, positionPackageIds, pageSize, pageNumber);
	}
	
	
	@Override
	public PageBeanDB queryAdPositionRule(Integer contractId, Integer adId,
			MarketingRule rule,Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return ployDao.queryAdPositionRule(contractId, adId, rule, pageSize, pageNumber);
	}

	@Override
	public String checkPloy(PloyBackup ploy) {
		// TODO Auto-generated method stub
		return ployDao.checkPloy(ploy);
	}

	@Override
	public PageBeanDB queryAreaList(PloyBackup ploy, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return ployDao.queryAreaList(ploy, pageSize, pageNumber);
	}
	

	@Override
	public PageBeanDB queryAreaListByCodes(String areaCodes, Integer pageSize, Integer pageNumber) {
		
		return ployDao.queryAreaListByCodes(areaCodes, pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryCityAreaList(PloyBackup ploy, Integer pageSize,
			Integer pageNumber) {
		return ployDao.queryCityAreaList(ploy, pageSize, pageNumber);
	}

	/**
	 * 根据用户获取合同信息
	 */
	@Override
	public List<Contract> getContractByAdMerchantId(Integer usetId) {
		List<Contract> lstContract = ployDao.getContractByAdMerchantId(usetId);
		return lstContract;
	}
	/**
	 * 根据策略ID获取类型信息
	 */

	@Override
	public List<ContractAD> getAdSiteByContract(int contractId) {
		List<ContractAD> lstContractAD = ployDao.getAdSiteByContract(contractId);
		return lstContractAD;
	}

	@Override
	public List<ContractAD> getMarketRuleByAdSiteId(int contractId, int adSiteId) {
		List<ContractAD> lstContractAD = ployDao.getMarketRuleByAdSiteId(contractId, adSiteId);
		return lstContractAD;
	}

	@Override
	public String[] getTimeSegmentsByMarketRule(int ruleId) {
		String[] array = ployDao.getTimeSegmentsByMarketRule(ruleId);
		return array;
	}

	@Override
	public List<ReleaseArea> getChoiceArea(int contractId, int adSiteId, int ruleId,
			String startTime, String endTime) {
		List<ReleaseArea> lstReleaseArea = ployDao.getChoiceArea(contractId, adSiteId, ruleId, startTime, endTime);
		return lstReleaseArea;
	}

	@Override
	public List<Ploy> getChannelListByArea(int contractId, int adSiteId,
			int ruleId, String startTime, String endTime, int areaId) {
		List<Ploy> lstPloy = ployDao.getChannelListByArea(contractId, adSiteId, ruleId, startTime, endTime, areaId);
		return lstPloy;
	}

	@Override
	public boolean deletePloyByIds(String id) {
		boolean flag = ployDao.deletePloyByIds(id);
		return flag;
	}

	
	public boolean savePloy(PloyTimeCGroup ployTimeCGroup, PreciseUiBean preciseUiBean,
			PloyBackup ploy) {
		boolean flag = false;
		try {
			int maxId = ployDao.getMaxId();
			//System.out.println();
			ploy.setPloyId(maxId+1);
			List<PloyBackup> lstPloy =this.getTheAllEntity(ployTimeCGroup, ploy);
			if (ploy.getPositionId()==1 || ploy.getPositionId()==2 
					||ploy.getPositionId()==13 || ploy.getPositionId()==14 
					|| ploy.getPositionId()==45 || ploy.getPositionId()==46
					|| ploy.getPositionId()==47 || ploy.getPositionId()==48   //直播下排
					|| ploy.getPositionId()==49 || ploy.getPositionId()==50
					|| ploy.getPositionId()==44 ||  ploy.getPositionId()==51
					||  ploy.getPositionId()==54||  ploy.getPositionId()==53)
			{
				lstPloy =this.getBTheAllEntity(ployTimeCGroup, ploy);
			}
			//单频道滚动字幕、NVOD字幕广告添加TVN号、用户级别和行业字段
			if(ploy.getPositionId() == 50|| ploy.getPositionId()==53){
				String userIndustrys = preciseUiBean.getUserindustrys();
				String userLevels = preciseUiBean.getUserlevels();
				String tvnNumber = preciseUiBean.getTvnNumber();
				
				for(PloyBackup pb :lstPloy){
					pb.setUserIndustrys(StringUtils.isEmpty(userIndustrys)?"0":userIndustrys);
					pb.setUserLevels(StringUtils.isEmpty(userLevels)?"0":userLevels);
					pb.setTvnNumber(StringUtils.isEmpty(tvnNumber)?"0":tvnNumber);
				}
				
			}
			flag = ployDao.savePloy(lstPloy);
			if (flag==true)
			{
				//添加精准
				List<TPreciseMatchBk> preciseMatchBkList = new ArrayList<TPreciseMatchBk>();
				//产品精准
				String preciseData = trim(preciseUiBean.getProductId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getProductName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.PRODUCT_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setProductId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority1());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression1());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber1());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//影片关键字精准
				preciseData = trim(preciseUiBean.getAssetKey());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getAssetKey()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.ASSET_KEY_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setAssetKey(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority2());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression2());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber2());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//影片分类精准
				preciseData = trim(preciseUiBean.getAssetSortId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getAssetSortName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.ASSET_CATEGORY_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setAssetSortId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority4());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression4());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber4());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//回放频道
				preciseData = trim(preciseUiBean.getPlaybackChannelId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getPlaybackChannelName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.PLAYBACK_CHANNEL_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setPlaybackChannelId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority5());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression5());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber5());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//回看栏目
				preciseData = trim(preciseUiBean.getLookbackCategoryId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getLookbackCategoryName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.LOOKBACK_CATEGORY_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setLookbackCategoryId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority6());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression6());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber6());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//影片名称
				preciseData = trim(preciseUiBean.getAssetName());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getAssetName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.ASSET_NAME_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setAssetName(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority8());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression8());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber8());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				
				//TVN号
				preciseData = trim(preciseUiBean.getTvnNumber());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getTvnNumber()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.TVN_NUMBER_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setTvnNumber(preciseDatas[i]);
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression17());
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority17());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//投放区域
				/*preciseData = trim(preciseUiBean.getUserArea());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getUserArea()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.PUSH_AREA));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setUserArea(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority9());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression9());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber9());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}*/
				//频道组
				preciseData = trim(preciseUiBean.getGroupId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getGroupName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.CHANNEL_GROUP_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setGroupId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority10());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression10());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber10());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//用户区域  二级 三级区域ID如为0 则 处理为空
				preciseData = trim(preciseUiBean.getUserArea());
					if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getUserAreaName()).split(",");
					
					String preciseArea2[]= trim(preciseUiBean.getUserArea2()).split(",");
					String preciseArea3[]= trim(preciseUiBean.getUserArea3()).split(",");
					String preciseAreaName2[]= trim(preciseUiBean.getUserArea2Name()).split(",");
					String preciseAreaName3[]= trim(preciseUiBean.getUserArea3Name()).split(",");
					
					
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.USER_AREA_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
						preciseMatchBk.setUserArea(preciseDatas[i]);
						preciseMatchBk.setUserArea2(preciseArea2[i]);
						preciseMatchBk.setUserArea3(preciseArea3[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						if (!preciseArea2[i].equals("0"))
						{
							//preciseMatchBk.setMatchName(preciseAreaName2[i]);							
						}
						if (!preciseArea3[i].equals("0"))
						{
							//preciseMatchBk.setMatchName(preciseAreaName3[i]);							
						}
						preciseMatchBk.setPriority(preciseUiBean.getPriority11());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression11());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber11());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
					//用户行业
					preciseData = trim(preciseUiBean.getUserindustrys());
					if (!preciseData.equals(""))
					{
						String preciseDatas[]= preciseData.split(",");
						String preciseNames[]= trim(preciseUiBean.getUserindustrysName()).split(",");
						for (int i=0;i<preciseDatas.length;i++)
						{
							TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
							preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.USER_INDUSTRYS_TYPE));
							preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
							preciseMatchBk.setUserindustrys(preciseDatas[i]);
							preciseMatchBk.setMatchName(preciseNames[i]);
							preciseMatchBk.setPriority(preciseUiBean.getPriority12());
							preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression12());
							preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber12());
							preciseMatchBkList.add(preciseMatchBk);
						}
					}
					//用户级别
					preciseData = trim(preciseUiBean.getUserlevels());
					if (!preciseData.equals(""))
					{
						String preciseDatas[]= preciseData.split(",");
						String preciseNames[]= trim(preciseUiBean.getUserlevelName()).split(",");
						for (int i=0;i<preciseDatas.length;i++)
						{
							TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
							preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.USER_RANCK));
							preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
							preciseMatchBk.setUserlevels(preciseDatas[i]);
							preciseMatchBk.setMatchName(preciseNames[i]);
							preciseMatchBk.setPriority(preciseUiBean.getPriority13());
							preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression13());
							preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber13());
							preciseMatchBkList.add(preciseMatchBk);
						}
					}
					//NOVD主界面类型精准
					preciseData = trim(preciseUiBean.getTypeCode());
					if (!preciseData.equals(""))
					{
						String preciseDatas[]= preciseData.split(",");
						String preciseNames[]= trim(preciseUiBean.getTypeName()).split(",");
						for (int i=0;i<preciseDatas.length;i++)
						{
							TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
							preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.NVOD_TYPE));
							preciseMatchBk.setPloyId(Long.valueOf(maxId+1));
							preciseMatchBk.setMenuTypeCode(preciseDatas[i]);
							preciseMatchBk.setMatchName(preciseNames[i]);
							preciseMatchBk.setPriority(preciseUiBean.getPriority18());
							preciseMatchBk.setTvnExpression("0");
							preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber18());
							preciseMatchBkList.add(preciseMatchBk);
						}
						
					}
					if (preciseMatchBkList.size()>0)
					{
						ployDao.saveOrUpdate(preciseMatchBkList,maxId+1);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	
	public boolean updatePloy(PloyTimeCGroup ployTimeCGroup, PreciseUiBean preciseUiBean,
			PloyBackup ploy) {
		boolean flag = false;
		try {
			List<PloyBackup> lstPloy =this.getTheAllEntity(ployTimeCGroup, ploy);
			if (ploy.getPositionId()==13 || ploy.getPositionId()==14 
					|| ploy.getPositionId()==1 || ploy.getPositionId()==2 
					|| ploy.getPositionId()==47 || ploy.getPositionId()==48   //直播下排
					|| ploy.getPositionId()==49 || ploy.getPositionId()==50   //滚动字幕
					|| ploy.getPositionId()==45 || ploy.getPositionId()==46
					|| ploy.getPositionId()== 44|| ploy.getPositionId()==51
					||ploy.getPositionId()==54 ||ploy.getPositionId()==53
					||ploy.getPositionId()==52 ||ploy.getPositionId()==51)
			{
				lstPloy =this.getBTheAllEntity(ployTimeCGroup, ploy);
			}
			
			//单频道滚动字幕添加TVN号、用户级别和行业字段
			if(ploy.getPositionId() == 50){
				String userIndustrys = preciseUiBean.getUserindustrys();
				String userLevels = preciseUiBean.getUserlevels();
				String tvnNumber = preciseUiBean.getTvnNumber();
				
				for(PloyBackup pb :lstPloy){
					pb.setUserIndustrys(StringUtils.isEmpty(userIndustrys)?"0":userIndustrys);
					pb.setUserLevels(StringUtils.isEmpty(userLevels)?"0":userLevels);
					pb.setTvnNumber(StringUtils.isEmpty(tvnNumber)?"0":tvnNumber);
				}
				
			}
			
			flag = ployDao.saveOrUpdate(lstPloy, ploy.getPloyId());
			int ployId = ploy.getPloyId();
			
			if (flag==true)
			{
				//ployDao.deletePrecise(String.valueOf(ployId));
				//添加精准
				List<TPreciseMatchBk> preciseMatchBkList = new ArrayList<TPreciseMatchBk>();
				//产品精准
				String preciseData = trim(preciseUiBean.getProductId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getProductName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.PRODUCT_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setProductId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority1());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression1());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber1());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//影片关键字精准
				preciseData = trim(preciseUiBean.getAssetKey());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getAssetKey()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.ASSET_KEY_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setAssetKey(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority2());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression2());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber2());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//影片分类精准
				preciseData = trim(preciseUiBean.getAssetSortId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getAssetSortName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.ASSET_CATEGORY_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setAssetSortId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority4());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression4());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber4());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//回放频道
				preciseData = trim(preciseUiBean.getPlaybackChannelId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getPlaybackChannelName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.PLAYBACK_CHANNEL_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setPlaybackChannelId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority5());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression5());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber5());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//回看栏目
				preciseData = trim(preciseUiBean.getLookbackCategoryId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getLookbackCategoryName()).split(",");
					//System.out.println(preciseDatas.length);
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.LOOKBACK_CATEGORY_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setLookbackCategoryId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority6());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression6());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber6());
						preciseMatchBkList.add(preciseMatchBk);
						//add
						
					}
					//ployDao.saveOrUpdate(preciseMatchBkList,ployId);
				}
				//影片名称
				preciseData = trim(preciseUiBean.getAssetName());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getAssetName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.ASSET_NAME_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setAssetName(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority8());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression8());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber8());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				
				//TVN号
				preciseData = trim(preciseUiBean.getTvnNumber());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getTvnNumber()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.TVN_NUMBER_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setTvnNumber(preciseDatas[i]);
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression17());
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority17());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				/*//投放区域
				preciseData = trim(preciseUiBean.getUserArea());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getUserArea()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.PUSH_AREA));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setUserArea(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority9());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression9());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber9());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}*/
				//频道组
				preciseData = trim(preciseUiBean.getGroupId());
				if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getGroupName()).split(",");
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.CHANNEL_GROUP_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setGroupId(preciseDatas[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						preciseMatchBk.setPriority(preciseUiBean.getPriority10());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression10());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber10());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
				//用户区域  二级 三级区域ID如为0 则 处理为空
				preciseData = trim(preciseUiBean.getUserArea());
					if (!preciseData.equals(""))
				{
					String preciseDatas[]= preciseData.split(",");
					String preciseNames[]= trim(preciseUiBean.getUserAreaName()).split(",");
					
					String preciseArea2[]= trim(preciseUiBean.getUserArea2()).split(",");
					String preciseArea3[]= trim(preciseUiBean.getUserArea3()).split(",");
					String preciseAreaName2[]= trim(preciseUiBean.getUserArea2Name()).split(",");
					String preciseAreaName3[]= trim(preciseUiBean.getUserArea3Name()).split(",");
					
					
					for (int i=0;i<preciseDatas.length;i++)
					{
						TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
						preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.USER_AREA_TYPE));
						preciseMatchBk.setPloyId(Long.valueOf(ployId));
						preciseMatchBk.setUserArea(preciseDatas[i]);
						preciseMatchBk.setUserArea2(preciseArea2[i]);
						preciseMatchBk.setUserArea3(preciseArea3[i]);
						preciseMatchBk.setMatchName(preciseNames[i]);
						if (!preciseArea2[i].equals("0"))
						{
							//preciseMatchBk.setMatchName(preciseAreaName2[i]);							
						}
						if (!preciseArea3[i].equals("0"))
						{
							//preciseMatchBk.setMatchName(preciseAreaName3[i]);							
						}
						preciseMatchBk.setPriority(preciseUiBean.getPriority11());
						preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression11());
						preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber11());
						preciseMatchBkList.add(preciseMatchBk);
					}
				}
					//用户行业
					preciseData = trim(preciseUiBean.getUserindustrys());
					if (!preciseData.equals(""))
					{
						String preciseDatas[]= preciseData.split(",");
						String preciseNames[]= trim(preciseUiBean.getUserindustrysName()).split(",");
						for (int i=0;i<preciseDatas.length;i++)
						{
							TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
							preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.USER_INDUSTRYS_TYPE));
							preciseMatchBk.setPloyId(Long.valueOf(ployId));
							preciseMatchBk.setUserindustrys(preciseDatas[i]);
							preciseMatchBk.setMatchName(preciseNames[i]);
							preciseMatchBk.setPriority(preciseUiBean.getPriority12());
							preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression12());
							preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber12());
							preciseMatchBkList.add(preciseMatchBk);
						}
					}
					//用户级别
					preciseData = trim(preciseUiBean.getUserlevels());
					if (!preciseData.equals(""))
					{
						String preciseDatas[]= preciseData.split(",");
						String preciseNames[]= trim(preciseUiBean.getUserlevelName()).split(",");
						for (int i=0;i<preciseDatas.length;i++)
						{
							TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
							preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.USER_RANCK));
							preciseMatchBk.setPloyId(Long.valueOf(ployId));
							preciseMatchBk.setUserlevels(preciseDatas[i]);
							preciseMatchBk.setMatchName(preciseNames[i]);
							preciseMatchBk.setPriority(preciseUiBean.getPriority13());
							preciseMatchBk.setTvnExpression(preciseUiBean.getTvnExpression13());
							preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber13());
							preciseMatchBkList.add(preciseMatchBk);
						}
					}
					//NOVD主界面类型精准
					preciseData = trim(preciseUiBean.getTypeCode());
					if (!preciseData.equals(""))
					{
						String preciseDatas[]= preciseData.split(",");
						String preciseNames[]= trim(preciseUiBean.getTypeName()).split(",");
						for (int i=0;i<preciseDatas.length;i++)
						{
							TPreciseMatchBk preciseMatchBk = new TPreciseMatchBk();
							preciseMatchBk.setPrecisetype(Long.valueOf(PreciseConstants.NVOD_TYPE));
							preciseMatchBk.setPloyId(Long.valueOf(ployId));
							preciseMatchBk.setMenuTypeCode(preciseDatas[i]);
							preciseMatchBk.setMatchName(preciseNames[i]);
							preciseMatchBk.setPriority(preciseUiBean.getPriority18());
							preciseMatchBk.setTvnExpression("0");
							preciseMatchBk.setTvnNumber(preciseUiBean.getTvnNumber18());
							preciseMatchBkList.add(preciseMatchBk);
						}
						
					}
					if (preciseMatchBkList.size()>0||trim(preciseUiBean.getLookbackCategoryId()).equals(""))
					{					
						ployDao.saveOrUpdate(preciseMatchBkList,ployId);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private List<PloyBackup> getTheAllEntity(PloyTimeCGroup ployTimeCGroup,
			PloyBackup ploy){
		List<PloyBackup> lstPloy = new ArrayList<PloyBackup>();
		
		ploy.setState("0");
		String starttimearray[] = ployTimeCGroup.getStarttimes().split(",");
		String endtimearray[] = ployTimeCGroup.getEndtimes().split(",");
		String cgrouparray[] = ployTimeCGroup.getChannelgroups().split(",");
		String priorityarray[] = ployTimeCGroup.getPriority().split(",");
		
		if(starttimearray != null && starttimearray.length > 0){
					
			for (int i=0;i<starttimearray.length;i++) {
				if(cgrouparray != null && starttimearray.length > 0){
					{
						for (int j = 0; j < cgrouparray.length; j++) {
							PloyBackup tmpPloy = new PloyBackup();
							BeanUtils.copyProperties(ploy, tmpPloy);
							tmpPloy.setAreaId(0L);
							tmpPloy.setStartTime(starttimearray[i]);
							tmpPloy.setEndTime(endtimearray[i]);
							tmpPloy.setGroupId(Integer.valueOf(cgrouparray[j]));
							tmpPloy.setPriority(Integer.valueOf(priorityarray[j]));
							lstPloy.add(tmpPloy);
						}
					}
				}
			}
		} else {
			lstPloy.add(ploy);
		}
		return lstPloy;
	}
	private List<PloyBackup> getBTheAllEntity(PloyTimeCGroup ployTimeCGroup,
			PloyBackup ploy){
		List<PloyBackup> lstPloy = new ArrayList<PloyBackup>();
		
		ploy.setState("0");
		String starttimearray[] = ployTimeCGroup.getStarttimes().split(",");
		String endtimearray[] = ployTimeCGroup.getEndtimes().split(",");
		String cgrouparray[] = ployTimeCGroup.getChannelgroups().split(",");
		
		String areaarray[] = ployTimeCGroup.getAreas().split(",");
		String priorityarray[] = ployTimeCGroup.getPriority().split(",");
		
		if(starttimearray != null && starttimearray.length > 0){
					
			for (int i=0;i<starttimearray.length;i++) {
				for (int k=0;k<areaarray.length;k++) {
					    if(cgrouparray != null && starttimearray.length > 0){
						{
							for (int j = 0; j < cgrouparray.length; j++) {
								PloyBackup tmpPloy = new PloyBackup();
								BeanUtils.copyProperties(ploy, tmpPloy);
								tmpPloy.setAreaId(Long.valueOf(areaarray[k]));
								tmpPloy.setStartTime(starttimearray[i]);
								tmpPloy.setEndTime(endtimearray[i]);
								tmpPloy.setGroupId(Integer.valueOf(cgrouparray[j]));
								tmpPloy.setPriority(Integer.valueOf(priorityarray[j]));
								lstPloy.add(tmpPloy);
							}
						}
					}
				}
			}
		} else {
			lstPloy.add(ploy);
		}
		return lstPloy;
	}

	@Override
	public List<ChannelInfo> getChoiceChannels(int contractId, int postionId,
			int ruleId, String startTime, String endTime, int areaId) {
		List<ChannelInfo> lstChannels = ployDao.getChoiceChannels(contractId, postionId, ruleId, startTime, endTime, areaId);
		return lstChannels;
	}
	public void CheckPloy(PloyBackup ploy)
	{
		ployDao.CheckPloy(ploy);
	}
	
	/**
	 * @description: 首页代办获取待审批的策略的总数
	 * @return 待审批的策略的总数
	 */
	public int getWaitingAuditPloyCount(String ids){
		return ployDao.getWaitingAuditPloyCount(ids);
	}
		
	
	@Override
	public int getWaitingAuditPloyNum(String accessUserIds) {
		
		return ployDao.getWaitingAuditPloyNum(accessUserIds);
	}

	/**
	 * 校验策略是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelPloyBackUpById(Integer id)
	{
		return ployDao.checkDelPloyBackUpById(id);
	}
	String  trim(String input)
	{
		if (input!=null)
		{
			String temp = input.trim();
			temp =temp.replace(" ", "");
			return temp;
		}
		else
		{
			return "";
		}
	}

	@Override
	public List<MenuType> getTypeNameByTypeCode(int typeCode) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
