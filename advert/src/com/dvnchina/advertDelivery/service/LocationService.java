package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.model.Location;

/**
 * 区域信息接口定义
 * 
 * <li>地区信息做初始化数据，故只提供查询方法的接口</li>
 * <li>若想维护T_LOCATION_CODE表，直接调用LocationDao</li>
 * 
 * @author Administrator
 *
 */
public interface LocationService {
	
	/**
	 * 获取所有地区信息
	 * @return 
	 */
	public List<Location> getAllLocation();
	
	/**
	 * 根据父级的ID，查询下面的所有的子，包括本身
	 * @param parentId
	 * @return
	 */
	public List<Location> getChildLocationList(String parentId);
	
	/**
	 * 根据父级的ID，按级别 获取数据 
	 * 
	 * @param parentId   父级的ID
	 * @param level      级别的ID
	 * @return
	 */
	public List<Location> getColumnList(String parentId,int level);
	
	
	
}
