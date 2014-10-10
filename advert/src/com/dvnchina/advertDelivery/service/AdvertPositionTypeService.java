package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.AdvertPositionType;

public interface AdvertPositionTypeService {
	/**
	 * 保存广告位信息
	 * @param advertPositionType
	 * @return
	 */
	public boolean save(AdvertPositionType object);
	/**
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	public List<AdvertPositionType> page(Map condition, int start, int end);
	
	public List<AdvertPositionType> getAll();
	/**
	 * 查询记录数
	 * @param condition
	 * @return
	 */
	public int queryCount(Map condition);
	/**
	 * 检查类型码是否已经存在
	 * @return
	 */
	public boolean checkAdvertPositionType(String typeCode,Integer id);
	/**
	 * 删除指定广告位类型记录
	 * @return
	 */
	public boolean remove(int objectId);
	
	/**
	 * 更新指定广告位类型记录
	 * @return
	 */
	public boolean update(AdvertPositionType object);
	
	public AdvertPositionType get(Integer id);
}
