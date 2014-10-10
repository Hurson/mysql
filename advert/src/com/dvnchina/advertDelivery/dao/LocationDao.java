package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.Location;

/**
 * 地区接口
 * @author Administrator
 *
 */
public interface LocationDao extends BaseDao{
	
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
