package com.dvnchina.advertDelivery.ploy.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ProductInfo;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;
import com.dvnchina.advertDelivery.ploy.bean.LocationCode;
import com.dvnchina.advertDelivery.ploy.bean.TAssetinfo;
import com.dvnchina.advertDelivery.ploy.bean.TCategoryinfo;
import com.dvnchina.advertDelivery.ploy.bean.TChannelinfoNpvr;
import com.dvnchina.advertDelivery.ploy.bean.TLoopbackCategory;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.bean.TProductinfo;
import com.dvnchina.advertDelivery.ploy.dao.PreciseDao;
import com.dvnchina.advertDelivery.ploy.service.PreciseMatchService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;

public class PreciseMatchServiceImpl implements PreciseMatchService {

	PreciseDao preciseDao;
	@Override
	public PageBeanDB queryPreciseList(TPreciseMatchBk preciseMatch,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return preciseDao.queryPreciseList(preciseMatch, pageSize, pageNumber);
	}

	@Override
	public TPreciseMatchBk getPreciseMatchByID(Long id) {
		// TODO Auto-generated method stub
		return preciseDao.getPreciseMatchByID(id);
	}
	
	@Override
	public List queryAdPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	 /**
 	 * 获取广告位详情
 	 * 广告位ID.
 	 *
 	 * @param id the id
 	 * @return the precise match by ploy id
 	 */
	public AdvertPosition getAdvertPositionByID(Long id)
	{
		return preciseDao.getAdvertPositionByID(id);
	}
	@Override
	public PageBeanDB queryAreaList(String ployId, Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * NVOD主界面类型
	 */
	public PageBeanDB queryTypelist(Integer pageSize, Integer pageNumber){
		return preciseDao.queryMenuType(pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryChannelList(String ployId, ChannelInfo channel,String ids,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return preciseDao.queryChannelList(ployId, channel, ids, pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryNpvrChannelList(String ployId, TChannelinfoNpvr channel,String ids,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return preciseDao.queryNpvrChannelList(ployId, channel, ids, pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryColumnList(String ployId, TLoopbackCategory category,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return preciseDao.queryColumnList(ployId, category, pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryProductList(String ployId, TProductinfo product,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return preciseDao.queryProductList(ployId, product, pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryAssetCategoryList(String ployId,TCategoryinfo category,  Integer pageSize,
			Integer pageNumber) {
		// TODO Auto-generated method stub
		return preciseDao.queryAssetCategoryList(ployId,category,  pageSize, pageNumber);
	}

	@Override
	public PageBeanDB queryAssetList(String assetName, TAssetinfo asset,String ids,
			Integer pageSize, Integer pageNumber) {
		// TODO Auto-generated method stub
		return preciseDao.queryAssetList(assetName, asset, ids, pageSize, pageNumber);
	}

	@Override
	public boolean saveOrUpdate(TPreciseMatchBk preciseMatch) {
		// TODO Auto-generated method stub
		return preciseDao.saveOrUpdate(preciseMatch);
	}

	public PreciseDao getPreciseDao() {
		return preciseDao;
	}

	public void setPreciseDao(PreciseDao preciseDao) {
		this.preciseDao = preciseDao;
	}
	public boolean deletePrecise(String dataids)
	{
		return preciseDao.deletePrecise(dataids);
	}
	public boolean checkName(String name,Long id)
	{
		return preciseDao.checkName( name, id);
	}
	/**
 	 * 查询可选区域
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryreleaseAreaList(ReleaseArea releassArea,String ids,Integer pageSize, Integer pageNumber)
	{
		return preciseDao.queryreleaseAreaList(releassArea, ids, pageSize, pageNumber);
	}
	
	/**
 	 * 查询可选行业
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryuserIndustryList(UserIndustryCategory userIndustry,String ids,Integer pageSize, Integer pageNumber)
	{
		return preciseDao.queryuserIndustryList(userIndustry, ids, pageSize, pageNumber);
	}
	
	/**
 	 * 查询可选用户类型
 	 * 
 	 * 
 	 * @param ploy the ploy
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryuserRankList(UserRank userRank,String ids,Integer pageSize, Integer pageNumber)
	{
		return preciseDao.queryuserRankList(userRank, ids, pageSize, pageNumber);
	}
	/**
 	 * 查询可选用户类型
 	 * 
 	 * 
 	 * @param channelGroup 
 	 * @param pageSize the page size
 	 * @param pageNumber the page number
 	 * @return the page bean
 	 */
	public PageBeanDB queryChannelGroupList(TChannelGroup channelGroup,String ids,Integer pageSize, Integer pageNumber)
	{
		return preciseDao.queryChannelGroupList(channelGroup, ids, pageSize, pageNumber);
	}
	
	
	
	@Override
	public PageBeanDB queryNpvrChannelGroupList(TNpvrChannelGroup channelGroup,String ids, Integer pageSize, Integer pageNumber) {
		return preciseDao.queryNpvrChannelGroupList(channelGroup, ids, pageSize, pageNumber);
	}

	public PageBeanDB queryLocationCodeList(LocationCode location,Integer pageSize, Integer pageNumber)
	{
		return preciseDao.queryLocationCodeList(location, pageSize, pageNumber);
	}
	
}
