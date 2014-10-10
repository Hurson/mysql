package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.Column;

/**
 * 栏目接口
 *
 */
public interface ColumnDao  extends BaseDao{

	/**
	 * @return  获取所有栏目
	 */
	public List<Column> getAllColumnList();
	
	/**
	 * @return  分页获取所有栏目
	 */
	public List<Column> getAllColumnList(int first,int pageSize);
	
	/**
	 *  删除栏目
	 * @param columnId
	 */
	public void deleteColumn(String columnId);
	
	/**
	 * 获取子栏目，包含自身
	 * 
	 * @param columnPId 栏目的父ID
	 * @return
	 */
	public List<Column> getChildColumnList(String columnPId);
	
	/**
	 * 获取层级栏目
	 * @param columnPId 栏目的ID
	 * @param level 级别
	 * @return
	 */
	public List<Column> getColumnList(int columnPId,int level);
	
	/**
	 * 根据用户的Id 获取栏目
	 * 
	 * @param userId
	 * @return
	 */
	public List<Column> queryColumnByUserId(String userId);
	
}
