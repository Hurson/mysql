package com.dvnchina.advertDelivery.ploy.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;
import com.dvnchina.advertDelivery.ploy.bean.LocationCode;
import com.dvnchina.advertDelivery.ploy.bean.TAssetinfo;
import com.dvnchina.advertDelivery.ploy.bean.TCategoryinfo;
import com.dvnchina.advertDelivery.ploy.bean.TChannelinfoNpvr;
import com.dvnchina.advertDelivery.ploy.bean.TLoopbackCategory;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.bean.TProductinfo;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;

public interface PreciseMatchService {

	/**
	 * 查询精准列表
	 * 精准名称
	 * 策略ID.
	 * @param preciseMatch the precise match
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 */	
	
    PageBeanDB queryPreciseList(TPreciseMatchBk preciseMatch,Integer pageSize, Integer pageNumber);
	
	 /**
 	 * 获取精准信息
 	 * 精准ID.
 	 *
 	 * @param id the id
 	 * @return the precise match by ploy id
 	 */
    TPreciseMatchBk getPreciseMatchByID(Long id);
	  
  	 /**
 	   * 查询所有广告位.
 	   *
 	   * @return the list
 	   */
	List queryAdPosition();
	
	 /**
 	 * 获取广告位详情
 	 * 广告位ID.
 	 *
 	 * @param id the id
 	 * @return the precise match by ploy id
 	 */
	AdvertPosition getAdvertPositionByID(Long id);
	
	 /**
 	 * 查询可选区域
 	 * 策略ID
 	 * 合同ID
 	 * 广告位ID
 	 * 规则ID.
 	 *
 	 * @param ployId the ploy id
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean db
 	 */
	PageBeanDB queryAreaList(String ployId,Integer pageSize, Integer pageNumber);
	 
 	/**
	  * 查询可选频道
	  * 根据策略编码  查询支持的区域列表 再根据区域code查询可选频道.
	  *
	  * @param ployId the ploy id
	  * @param channel the channel
	  * @param ids 已选频道IDS
	  * @param pageSize the page size
	  * @param pageNumber the page number
	  * @return the page bean db
	  */
	PageBeanDB queryChannelList(String ployId,ChannelInfo channel,String ids,Integer pageSize, Integer pageNumber);
	/**
	 * 查询可选类型
	 */
	PageBeanDB queryTypelist(Integer pageSize, Integer pageNumber);
	/**
	 * 查询可选回放频道
	 * 根据策略编码  查询支持的区域列表 再根据区域code查询可选回放频道.
	 *
	 * @param ployId the ploy id
	 * @param channel the channel
	 * @param ids 已选频道IDS
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 */
	PageBeanDB queryNpvrChannelList(String ployId,TChannelinfoNpvr channel,String ids,Integer pageSize, Integer pageNumber);
	
	/**
	 * 查询可选回看栏目
	 * 根据策略编码  查询支持的区域列表 再根据区域code查询可选回看栏目.
	 *
	 * @param ployId the ploy id
	 * @param columnName the column name
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 */
	PageBeanDB queryColumnList(String ployId,TLoopbackCategory category,Integer pageSize, Integer pageNumber);
	
	/**
	 * 查询可选回看产品.
	 *
	 * @param ployId the ploy id
	 * @param product the product
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the list
	 **/
	PageBeanDB queryProductList(String ployId,TProductinfo product,Integer pageSize, Integer pageNumber);
	
	/**
	 * 查询可选影片类别.
	 *
	 * @param ployId the ploy id
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the list
	 */
	PageBeanDB queryAssetCategoryList(String ployId,TCategoryinfo category, Integer pageSize, Integer pageNumber);
	
	/**
	 * 查询可选影片.
	 *
	 * @param assetName the asset name
	 * @param asset the asset
	 * @param ids 已选影片IDS
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 */
	PageBeanDB queryAssetList(String assetName,TAssetinfo asset,String ids,Integer pageSize, Integer pageNumber);
	/**
 	 * 查询可选区域
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryreleaseAreaList(ReleaseArea releassArea,String ids,Integer pageSize, Integer pageNumber);
	
	/**
 	 * 查询可选行业
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryuserIndustryList(UserIndustryCategory userIndustry,String ids,Integer pageSize, Integer pageNumber);
	
	/**
 	 * 查询可选用户类型
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryuserRankList(UserRank userRank,String ids,Integer pageSize, Integer pageNumber);
	/**
 	 * 查询可选用户类型
 	 * 
 	 * 
 	 * @param channelGroup 
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryChannelGroupList(TChannelGroup channelGroup,String ids,Integer pageSize, Integer pageNumber);
	PageBeanDB queryNpvrChannelGroupList(TNpvrChannelGroup channelGroup,String ids,Integer pageSize, Integer pageNumber);
	PageBeanDB queryLocationCodeList(LocationCode location,Integer pageSize, Integer pageNumber);
	
	boolean saveOrUpdate(TPreciseMatchBk preciseMatch);
	boolean deletePrecise(String dataids);
	boolean checkName(String name,Long id);
}
