package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.ColumnTree;
import com.dvnchina.advertDelivery.model.Column;

/**
 * 
 * 栏目处理接口
 * 
 * 1、栏目和角色绑定了，删除怎么处理？ 
 *  是同时删除栏目和角色的绑定记录吗？
 * 
 * @author Administrator
 *
 */
public interface ColumnService {
	
	/**
	 * 添加栏目
	 * 
	 * @param column
	 */
	public boolean insertColumn(Column column);
	
	/**
	 * 删除栏目
	 * 
	 * @param column
	 */
	public boolean deleteColumn(String columnId);
	
	/**
	 * 修改用户
	 * @param column
	 */
	public boolean updateColumn(Column column);
	
	
	/**
	 * 根据栏目的ID获取一个栏目实体
	 * @param columnId
	 * @return  Column实体
	 */
	public Column getSingleColumn(String columnId);
	
	/**
	 * 获取栏目下的所有栏目，包含自身
	 * 
	 * @param columnPId  
	 * 
	 * @return 栏目下所有栏目的集合
	 */
	public List<Column> getChildColumnList(String columnPId);
	
	/**
	 * 获取栏目下的所有栏目，包含自身
	 * 
	 * 
	 * @return 所有栏目的集合
	 */
	public List<Column> getAllColumnList();
	
	
	/**
	 * 获取树状结构数据
	 * @param level
	 * @return
	 */
//	public String getTreeColumnList();
	
	/**
	 * 获取树状结构数据
	 * 
	 * @param colIds 用户拥有的栏目集合
	 * @return
	 */
	public String getTreeColumnList(List<Integer> columnIdList);
	
	
	/**
	 * 分页获取
	 * 
	 * 获取栏目下的所有栏目，包含自身
	 * 
	 * 
	 * @return 所有栏目的集合
	 */
	public List<Column> getAllColumnList(int first,int pageSize);
	
	/**
	 * 获取一级栏目
	 * 
	 * @return
	 */
	public List<Column> getFirstColumnList();
	
}
