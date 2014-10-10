package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.model.MarketingRule;

public interface MarketingRuleDao  extends BaseDao {
	
	/**
	 * 添加营销规则记录
	* @param MarketingRule
	 */
	public void insertMarketingRule(MarketingRule marketingRule);
	
	/**
	 * 修改营销规则
	 * @param MarketingRule
	 */
	public void updateMarketingRule(MarketingRule marketingRule);
	
	/**
	 * 根据ID删除营销规则
	 * 
	 * @param marketingRuleId
	 */
	public void deleteMarketingRuleById(String marketingRuleId);
	
	/**
	 * 根据id上线营销规则
	 * @param marketingRuleId
	 */
	public void upLineMarketingRule(String marketingRuleId);

	/**
	 * 根据id下线营销规则
	 * @param marketingRuleId
	 */
	public void downLineMarketingRule(String marketingRuleId);
	
	/**
	 * 根据ID查询营销规则
	 * 
	 * @param marketingRuleId
	 */
	public List<MarketingRuleBean> getMarketingRuleById(Integer marketingRuleId);
	
	/**
	 * 查询所有营销规则
	 */
	public List<MarketingRuleBean> getAllMarketingRule(int begin, int end);
	
	/**
	 * 取得营销规则总数
	 * @return
	 */
	public int getMarketingRuleCount(String sql);
	
	/**
	 * 取得区域总数
	 * @return
	 */
	public int getAreaCount();
	
	/**
	 * 取得区域总数
	 * @param positionId
	 * @return
	 */
	public int getAreaCount(String positionId);
	
	/**
	 * @param positionId
	 * 查询区域
	 */
	public List<MarketingRuleBean> getAreaList(String positionId,final int begin,final int end);
	
	/**
	 * 查询区域
	 */
	List<MarketingRuleBean> getAreaList(int begin, int end);
	
	/**
	 * 取得频道总数
	 * @param positionId
	 * @param areaId
	 * @return
	 */
	public int getChannelCount(String positionId,String areaId);
	
	/**
	 * 取得频道总数
	 * @param positionId
	 * @return
	 */
	public int getChannelCount(String positionId);
	
	/**
	 * 取得频道总数
	 * @return
	 */
	public int getChannelCount();
	
	/**  
	 *  分页获取频道
	 * @param positionId 
	 * @param areaId
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<MarketingRuleBean> getChannelList(String positionId,String areaId,final int begin,final int end);
	
	/**  
	 *  分页获取频道
	 * @param positionId 
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<MarketingRuleBean> getChannelList(String positionId,final int begin,final int end);
	
	/**  
	 *  分页获取频道
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<MarketingRuleBean> getChannelList(final int begin,final int end);
	
	/**
	 * 分页取得营销规则总数
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<MarketingRuleBean> page(String sql, int start, int end);
	
	/**
	 * 取得广告位已有规则总数
	 * @param positionId
	 * @return
	 */
	public int getRuleCount(String positionId);
	
	/**  
	 *  分页获取广告位已有规则
	 * @param positionId 
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<MarketingRuleBean> getRuleList(String positionId,final int begin,final int end);
	
}