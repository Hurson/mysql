package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.precise.PreciseBean;
import com.dvnchina.advertDelivery.model.PreciseMatch;

public interface PreciseDao  extends BaseDao {
	
	/**
	 * 添加精准记录
	* @param precise
	 */
	public void insertPrecise(PreciseMatch precise);
	
	/**
	 * 根据id获得精准
	 * @param sql
	 */
	public PreciseBean getPreciseById(String sql);
	
	/**
	 * 修改精准
	 * @param precise
	 */
	public void updatePrecise(PreciseMatch precise);
	
	/**
	 * 根据ID删除精准
	 * 
	 * @param preciseId
	 */
	public void deletePreciseById(String preciseId);
	
	/**
	 * 取得精准总数
	 * @return
	 */
	public int getPreciseCount(String sql);
	
	/**
	 * 分页取得精准
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PreciseBean> page(String sql, int start, int end);
	
	/**
	 * 取得产品总数
	 * @param sql 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getProductCount(String sql);
	
	/**
	 * @param sql
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getProductList(String sql, int start, int end);
	
	/**
	 * 取得回看频道总数
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getChannelCount(String sql);
	
	/**
	 * @param sql
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getChannelList(String sql, int start, int end);
	
	/**
	 * 取得影片分类总数
	 * @param sql 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAssetSortCount(String sql);
	
	/**
	 * @param sql 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getAssetSortList(String sql, int start, int end);
	
	/**
	 * 取得关键字
	 * @param sql 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getKeywordCount(String sql);
	
	/**
	 * @param sql 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getKeywordList(String sql, int start, int end);
	
	/**
	 * 取得用户区域
	 * @param sql 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUserAreaCount(String sql);
	
	/**
	 * @param sql 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getUserAreaList(String sql, int start, int end);
	
	/**
	 * 取得行业区域
	 * @param sql 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUserIndustrysCount(String sql);
	
	/**
	 * @param sql 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getUserIndustrysList(String sql, int start, int end);
	
	/**
	 * 取得行业级别
	 * @param sql 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUserRankCount(String sql);
	
	/**
	 * @param sql 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getUserRankList(String sql, int start, int end);
	
	/**
	 * 取得行业级别
	 * @param sql 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getPlatCategoryCount(String sql);
	
	/**
	 * @param sql 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getPlatCategoryList(String sql, int start, int end);
	
	/**
	 * 取得行业级别
	 * @param sql 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getPloyCount(String sql);
	
	/**
	 * @param sql 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getPloyList(String sql, int start, int end);
	
	

}