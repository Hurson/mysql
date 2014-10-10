package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdvertPositionType;

/**
 * 广告类型
 * @author lester
 *
 */
public interface AdvertPositionTypeDao {
	/**
	 * 新增
	 * @param object
	 * @return
	 */
	public int save(final AdvertPositionType object);
	/**
	 * 删除
	 * @param advertPositionTypeId
	 * @return
	 */
	public int remove(final int objectId);
	/**
	 * 更新
	 * @param advertPositionType
	 * @return
	 */
	public int update(final AdvertPositionType object);
	/**
	 * 分页查询
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AdvertPositionType> page(String sql, int start, int end);
	/**
	 * 统计总数
	 * @param sql
	 * @return
	 */
	public int queryTotalCount(String sql);
	/**
	 * 按条件查询
	 * @param sql
	 * @return
	 */
	public List<AdvertPositionType> query(String sql);
	
	public AdvertPositionType get(Integer id);
}
