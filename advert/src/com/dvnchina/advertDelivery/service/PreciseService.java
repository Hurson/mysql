package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.bean.precise.PreciseBean;
import com.dvnchina.advertDelivery.model.PreciseMatch;
/**
 * @author Administrator
 *
 */
public interface PreciseService {
	
	/**
	 * 新增精准
	 * @param precise
	 * @return
	 */
	public Map<String, String> insertPrecise(PreciseMatch precise);
	
	/**
	 * 根据id获得精准
	 * @param preciseId
	 */
	public PreciseBean getPreciseById(Integer preciseId);
	
	/**
	 * 修改精准
	 * @param precise
	 * @return
	 */
	public Map<String, String> updatePrecise(PreciseMatch precise);

	/**
	 * 根据id删除精准
	 * @param preciseId
	 */
	public void deletePreciseById(String preciseId);
	
	/**
	 * 取得精准总数
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getPreciseCount(Map condition,String ployId);
	
	/**
	 * @param condition 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> page(String ployId,Map condition, int start, int end);
	
	/**
	 * 取得产品总数
	 * @param productName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getProductCount(String productName);
	
	/**
	 * @param productName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getProductList(String productName, int start, int end);
	
	/**
	 * 取得回看频道总数
	 * @param channelName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getChannelCount(String channelName);
	
	/**
	 * @param channelName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getChannelList(String channelName, int start, int end);
	
	/**
	 * 取得影片分类总数
	 * @param assetSortName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getAssetSortCount(String assetSortName);
	
	/**
	 * @param assetSortName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getAssetSortList(String assetSortName, int start, int end);
	
	/**
	 * 取得关键字
	 * @param keyword 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getKeywordCount(String keyword);
	
	/**
	 * @param keyword 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getKeywordList(String keyword, int start, int end);
	
	/**
	 * 取得用户区域
	 * @param locationName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUserAreaCount(String locationName);
	
	/**
	 * @param locationName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getUserAreaList(String locationName, int start, int end);
	
	/**
	 * 取得行业区域
	 * @param userIndustrysName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUserIndustrysCount(String userIndustrysName);
	
	/**
	 * @param userIndustrysName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getUserIndustrysList(String userIndustrysName, int start, int end);
	
	/**
	 * 取得行业级别
	 * @param userRankName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUserRankCount(String userRankName);
	
	/**
	 * @param userRankName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getUserRankList(String userRankName, int start, int end);
	
	/**
	 * 取得行业级别
	 * @param categoryName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getPlatCategoryCount(String categoryName);
	
	/**
	 * @param categoryName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getPlatCategoryList(String categoryName, int start, int end);
	
	/**
	 * 取得行业级别
	 * @param ployName 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getPloyCount(String ployName);
	
	/**
	 * @param ployName 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PreciseBean> getPloyList(String ployName, int start, int end);
	

}