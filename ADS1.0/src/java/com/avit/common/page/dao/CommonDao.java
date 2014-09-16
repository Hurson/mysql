/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.common.page.dao;

import java.util.List;

import com.avit.common.page.PageBean;
import com.avit.common.page.dao.BaseDao;

/**
 * @Description:扩展BaseDao的查询方法
 * @author lizhiwei
 * @Date: 2012-5-2
 * @Version: 1.0
 */
public interface CommonDao extends BaseDao {
	/**
	 * 获取数据的总数
	 * @param hql
	 * @param params
	 * @return
	 */
	public int getTotalCountHQL(final String hql, final List params);
	
	/**
	 * 分页查询，返回数据列表
	 * @param hql
	 * @param params
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List getListForPage(final String hql, final List params, final int pageNumber, final int pageSize);
	
	/**
	 * 分页查询，返回分页对象
	 * @param hql
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageBean getPageList(final String hql, final List params, final int pageNumber, final int pageSize);
	
	/**
	 * 查询所有满足条件的数据列表
	 * @param hql
	 * @param params
	 * @return
	 */
	public List getListForAll(final String hql, final  List params);
	
	/**
	 * 使用hql删除数据
	 * @param hql
	 */
	public void delete(final String hql);
}
