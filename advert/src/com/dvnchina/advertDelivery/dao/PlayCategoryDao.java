package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.PlayCategory;

public interface PlayCategoryDao {
	
	
	/**
	 * 删除节点
	 */
	
	public int removePlayCategory(Integer playCategoryId);
	
	/**
	 * 得到所有表单中的记录
	 * @param playCategory
	 * @return
	 */
	public List<PlayCategory> getAllPlayCategory();
	
	/**
	 * 保存节点信息
	 * @param playCategory
	 * @return
	 */
	public int savePlayCategory(PlayCategory playCategory);
	
	public boolean deletePlayCategory(PlayCategory playCategory);
	
	/**
	 * 更新节点信息
	 * @param playCategory
	 * @return
	 */
	public int updatePlayCategory(PlayCategory playCategory);
	
	public List<PlayCategory> page(String sql, int start, int end);
	
	public int queryTotalCount(String sql);
}
