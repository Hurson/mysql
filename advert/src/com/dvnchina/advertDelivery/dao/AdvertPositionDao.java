package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.PositionOccupyStatesInfo;

public interface AdvertPositionDao {
	/**
	 * 增加广告位
	 * @return
	 */
	public int saveAdvertPosition(AdvertPosition advertPosition);
	/**
	 * 增加广告位返回主键值
	 * @return 主键值
	 */
	public int saveAdvertPositionReturnPrimaryKey(AdvertPosition advertPosition);
	/**
	 * 批量保存广告位
	 * @param advertPositionList
	 * @return
	 */
	public int[] saveBatchAdvertPosition(final List<AdvertPosition> advertPositionList);
	/**
	 * 删除广告位
	 * @param advertPosition
	 * @return
	 */
	public int removeAdvertPosition(int advertPositionId);
	/**
	 * 更新广告位
	 * @param advertPosition
	 * @return
	 */
	public int updateAdvertPosition(AdvertPosition advertPosition);	
	/**
	 * 批量更新广告位
	 * @param advertPosition
	 * @return
	 */
	public int[] updateBatchAdvertPosition(final List<AdvertPosition> advertPositionList);
	/**
	 * 分页查询广告位
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<AdvertPosition> page(String sql, int start, int end);
	
	/**
	 * 分页查询占用状态广告位
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PositionOccupyStatesInfo> page4OccupyStates(String sql,Integer type,Integer position, int start, int end,String startDate,String endDate);
	/**
	 * 查询总记录数
	 * @param sql
	 * @return
	 */
	public int queryTotalCount(String sql);
	/**
	 * 根据条件查询
	 * @param sql
	 * @return
	 */
	public List<AdvertPosition> find(String sql);
	
	/**
	 * 根据资产表中的广告位id，查询所属的广告位
	 * @param advertPositionId
	 * @return
	 */
	public List<AdvertPosition> getAdvertPositionById(Integer advertPositionId);
	
	public List<Integer> getAdvertPositionOccupyStatus(List<AdvertPosition> advertPositionList); 
	/**
	 * 批量删除
	 * @param apIdList
	 * @return
	 */
	public int[] removeBatchAdvertPosition(final String[] apIdList);
}
