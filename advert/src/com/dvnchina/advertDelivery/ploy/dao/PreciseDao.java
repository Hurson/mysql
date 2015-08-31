package com.dvnchina.advertDelivery.ploy.dao;

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
import com.dvnchina.advertDelivery.order.bean.MenuType;

public interface PreciseDao {
	/**
	 * 查询精准列表
	 * 精准名称
	 * 策略ID.
	 *
	 * @param ploy the ploy
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean
	 */	
	
    PageBeanDB queryPreciseList(TPreciseMatchBk preciseMatch,Integer pageSize, Integer pageNumber);
	
	 /**
 	 * 获取策略信息
 	 * 策略ID.
 	 *
 	 * @param id 
 	 * @return the TPreciseMatch by  id
 	 */
    TPreciseMatchBk getPreciseMatchByID(Long id);
	  
  	 /**
 	 * 查询所有广告位
 	 *
 	 * @param contractId the contract id
 	 * @param adPosition the ad position
 	 * @return the page bean
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
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryAreaList(String ployId,Integer pageSize, Integer pageNumber);
	 
 	/**
 	 * 查询可选频道
 	 * 根据策略编码  查询支持的区域列表 再根据区域code查询可选频道
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryChannelList(String ployId,ChannelInfo channel,String ids,Integer pageSize, Integer pageNumber);
	
	/**
 	 * 查询可选回放频道
 	 * 根据策略编码  查询支持的区域列表 再根据区域code查询可选回放频道
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryNpvrChannelList(String ployId,TChannelinfoNpvr channel,String ids,Integer pageSize, Integer pageNumber);
	
	/**
 	 * 查询可选回看栏目
 	 * 根据策略编码  查询支持的区域列表 再根据区域code查询可选回看栏目
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryColumnList(String ployId,TLoopbackCategory category,Integer pageSize, Integer pageNumber);
	/**
	 * 查询可选类型
	 */
	PageBeanDB queryMenuType(Integer pageSize, Integer pageNumber);
	/**
 	 * 查询可选回看产品
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryProductList(String ployId,TProductinfo product,Integer pageSize, Integer pageNumber);
	
	/**
 	 * 查询可选影片类别 	 
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	PageBeanDB queryAssetCategoryList(String ployId,TCategoryinfo category, Integer pageSize, Integer pageNumber);
	
	/**
 	 * 查询可选影片
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
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
