package com.dvnchina.advertDelivery.ploy.service;

import java.util.List;

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
import com.dvnchina.advertDelivery.ploy.bean.PreciseUiBean;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;

// TODO: Auto-generated Javadoc
/**
 * The Interface PloySerivce.
 */
public interface PloyService {
	
	public PageBeanDB getAdPloyList(PloyBackup ploy,AdvertPosition adPosition,Integer pageSize, Integer pageNumber, String userIds);
    
	public PageBeanDB getAdPositionByPackageIds(String packageIds, Integer pageSize, Integer pageNumber);
	
	/**
	 * 查询策略列表
	 * 策略名称
	 * 合同ID
	 * 广告位ID
	 * 规则ID.
	 *
	 * @param ploy the ploy
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean
	 */	
	
    PageBeanDB queryPloyList(PloyBackup ploy,Contract contract,AdvertPosition adPosition,String customerIds,Integer pageSize, Integer pageNumber);
    PageBeanDB queryPloyList(PloyBackup ploy,String customerIds,String positionPackageIds,AdvertPosition adPosition,Integer pageSize, Integer pageNumber);
    List queryExitsPloyList(PloyBackup ploy);
    
    PageBeanDB queryCheckPloyList(PloyBackup ploy,Contract contract,AdvertPosition adPosition,String customerIds,Integer pageSize, Integer pageNumber);
	
	 /**
 	 * 获取策略信息
 	 * 策略ID.
 	 *
 	 * @param ployId the ploy id
 	 * @return the ploy by ploy id
 	 */
    PloyBackup getPloyByPloyID(int ployId);
	  
  	/**
  	 * 获取策略的区域频道信息   
  	 * 策略ID.
  	 *
  	 * @param ployId the ploy id
  	 * @return the area channels by ploy id
  	 */
	public List<PloyAreaChannel> getAreaChannelsByPloyID(int ployId);
	
	/**
  	 * 获取策略的时段频道信息   
  	 * 策略ID.
  	 *
  	 * @param ployId the ploy id
  	 * @return the PloyTimeCGroup by ploy id
  	 */
	public PloyTimeCGroup getPloyTimeCGroupByPloyID(int ployId, String channelGroupType);
	/**
	 * 从精准表获取NVOD主界面类型信息
	 * 策略ID
	 * 
	 * @param ployId the ploy id
	 * @return theMenuTypeCode by ploy id
	 */ 
	public List<MenuType> getMenuTypeByPloyID(int ployId);
	/**
  	 * 获取策略的精准信息   
  	 * 策略ID.
  	 *
  	 * @param ployId the ploy id
  	 * @return the PreciseUiBean by ploy id
  	 */
	public PreciseUiBean getPreciseUiBeanByPloyID(int ployId);
	
	/**
	 * 查询广告商
	 * 广告商ID
	 * 合同名称
	 * 合同号
	 * 合同编码.
	 *
	 * @param customerIds the 广告商IDS ","分隔
	 * @param contract the contract
	 * @return the page bean
	 */
	PageBeanDB queryCustomer(Integer pageSize, Integer pageNumber);

	/**
	 * 查询可选合同
	 * 广告商ID
	 * 合同名称
	 * 合同号
	 * 合同编码.
	 *
	 * @param customerIds the 广告商IDS ","分隔
	 * @param contract the contract
	 * @return the page bean
	 */
	PageBeanDB queryContract(Contract contract,String customerIds,Integer pageSize, Integer pageNumber);

	 /**
 	 * 查询可选广告位
 	 * 合同ID
 	 * 广告位类型ID
 	 * 广告位名称.
 	 *
 	 * @param contractId the contract id
 	 * @param adPosition the ad position
 	 * @return the page bean
 	 */
	PageBeanDB queryAdPosition(String customerIds,Integer contractId ,AdvertPosition adPosition,Integer pageSize, Integer pageNumber);
	
	 /**
 	 * 查询可选广告位
 	 * @param customerIds 广告商ID
 	 * @param positionPackageIds 广告位类型ID
 	 * @return the page bean
 	 */
	PageBeanDB queryAdPosition(String customerIds,String positionPackageIds ,Integer pageSize, Integer pageNumber);
	
	
	/**
	 * 查询可选规则
	 * 合同ID
	 * 广告位ID
	 * 规则名称.
	 *
	 * @param adId the ad id
	 * @param rule the rule
	 * @return the page bean
	 */
	PageBeanDB queryAdPositionRule(Integer contractId ,Integer adId ,MarketingRule rule,Integer pageSize, Integer pageNumber);
	
	/**
	 * 校验策略排它.
	 * 合同ID
	 * 广告位ID
	 * 规则ID
	 * 开始时间
	 * 结束时间
	 * @param ploy the ploy
	 * @return true, if successful
	 */
	String checkPloy(PloyBackup ploy);
	
	
	 /**
 	 * 查询可选区域
 	 * 策略ID
 	 * 合同ID
 	 * 广告位ID
 	 * 规则ID.
 	 *
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryAreaList(PloyBackup ploy,Integer pageSize, Integer pageNumber);
	
	PageBeanDB queryAreaListByCodes(String areaCodes,Integer pageSize, Integer pageNumber);
	 
	PageBeanDB queryCityAreaList(PloyBackup ploy,Integer pageSize, Integer pageNumber);
	
	/**
	 * 查询可选择的类型
	 * 
	 */
	/**
	 * 根据用户获取合同信息
	 * @param usetId
	 * @return
	 */
	public List<Contract> getContractByAdMerchantId(Integer usetId);

	public List<ContractAD> getAdSiteByContract(int contractId);

	public List<ContractAD> getMarketRuleByAdSiteId(int contractId, int adSiteId);

	public String[] getTimeSegmentsByMarketRule(int ruleId);

	public List<ReleaseArea> getChoiceArea(int contractId, int adSiteId, int ruleId, String startTime, String endTime);

	public List<Ploy> getChannelListByArea(int contractId, int adSiteId, int ruleId,
			String startTime, String endTime, int areaId);

	public boolean deletePloyByIds(String id);

	public boolean updatePloy(PloyTimeCGroup ployTimeCGroup, PreciseUiBean preciseUiBean, PloyBackup ploy);

	boolean savePloy(PloyTimeCGroup ployTimeCGroup, PreciseUiBean preciseUiBean,PloyBackup ploy);

	List<ChannelInfo> getChoiceChannels(int contractId, int postionId,
			int ruleId, String startTime, String endTime, int areaId);
	public void CheckPloy(PloyBackup ploy);
	
	/**
	 * @description: 首页代办获取待审批的策略的总数
	 * @return 待审批的策略的总数
	 */
	public int getWaitingAuditPloyCount(String ids);
	
	public int getWaitingAuditPloyNum(String accessUserIds);
	/**
	 * 校验策略是否可删除
	 * return 1不可删除 0 可删除
	 */
	public int checkDelPloyBackUpById(Integer id);
}
