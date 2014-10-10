package com.dvnchina.advertDelivery.position.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.DefaulResourceAD;

public interface DefaulResourceDao {
	
	/**
	 * 分页查询默认素材信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryResourceList(int pageNo, int pageSize);
	
	/**
	 * 分页查询默认素材信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryDefResourceList(int pageNo, int pageSize);
	
	/**
	 * 根据子广告位ID查询默认素材
	 * @param adId
	 */
	public List<ResourceReal> queryDefResourceListByAdId(Integer adId);
	
	/**
	 * 根据素材ID获取素材信息
	 * @param ids
	 */
	public List<ResourceReal> findResourceListByIds(String ids);
	
	/**
	 * 根据子广告位ID查询已经设置默认素材
	 * @param adId
	 */
	public List<DefaulResourceAD> queryDefResourceADListByAdId(Integer adId);
	
	/**
	 * 根据广告位包ID查询子广告位列表
	 * @param positionPackageId
	 * @return
	 */
	public List<AdvertPosition> queryADList(Integer positionPackageId);
	
	/**
	 * 保存子广告位的默认素材
	 * @param defResourceList
	 */
//	public void saveDefResource(List<DefaulResourceAD> defResourceList);
	
	/**
	 * 根据子广告位ID获取默认素材
	 * @param adId
	 * @return
	 */
	public List<ResourceReal> getDefResourceByAdId(Integer adId);
	
	/**
	 * 根据子广告位ID删除默认素材
	 * @param adId
	 */
	public void delResourceADByAdId(Integer adId);
	
	/**
	 * 保存子广告位的默认素材
	 * @param raList
	 */
	public void saveDefResource(List<DefaulResourceAD> raList);

}
