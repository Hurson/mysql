package com.avit.common.page.dao;

import com.avit.common.page.PageBean;

public interface PageDao extends BaseDao {

	/**
	 * @param myclass
	 *            需要分页的类
	 * @param pagebean
	 *            分页Bean
	 * @return
	 * @throws Exception
	 */
	public PageBean getListForPage(Class myclass, PageBean pagebean)
			throws Exception;

	/**
	 * @param myclass
	 *            需要分页的类
	 * @param parasneeded
	 *            只需要分页的类中的部分数据
	 * @param pagebean
	 *            分页Bean
	 * @return
	 * @throws Exception
	 */
	public PageBean getListForPage(Class myclass, String[] parasneeded,
			PageBean pagebean) throws Exception;

	/**
	 * @param hsql
	 *            HQL语句
	 * @param pagebean
	 *            分页Bean
	 * @return
	 * @throws Exception
	 */
	public PageBean getListForPage(String hsql, PageBean pagebean)
			throws Exception;

	/**
	 * 
	 * @param hsql
	 *            HQL语句
	 * @param sqlParas
	 *            hsql的参数
	 * @param pagebean
	 * @return
	 * @throws Exception
	 */
	public PageBean getListForPage(String hsql, Object[] sqlParas,
			PageBean pagebean) throws Exception;

}
