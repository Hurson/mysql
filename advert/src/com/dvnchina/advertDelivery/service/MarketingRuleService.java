package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.model.MarketingRule;
/**
 * @author Administrator
 *
 */
public interface MarketingRuleService {
	
	/**
	 * 新增营销规则
	 * @param marketingRule
	 * @return
	 */
	public Map<String, String> insertMarketingRule(MarketingRule marketingRule);
	
	/**
	 * 修改营销规则
	 * @param marketingRule
	 * @return
	 */
	public Map<String, String> updateMarketingRule(MarketingRule marketingRule);

	/**
	 * 根据id删除营销规则
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
	 * 通过ID查找营销规则
	 * @param id
	 * @return
	 */
	public List<MarketingRuleBean> getMarketingRuleById(Integer id);
	
	/**
	 * 取得营销规则总数
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getMarketingRuleCount(Map condition);
	
	/**  
	 *  分页获取所有营销规则 
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<MarketingRuleBean> getAllMarketingRule(final int begin,final int end);
	
	/**
	 * @param condition 查询条件
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarketingRuleBean> page(Map condition, int start, int end);


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
	 *  分页获取地区
	 * @param positionId 
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<MarketingRuleBean> getAreaList(String positionId,final int begin,final int end);
	
	/**  
	 *  分页获取地区
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<MarketingRuleBean> getAreaList(final int begin,final int end);
	
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