package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.Ploy;

/**
 * @author Administrator
 *
 */
public interface PloyService {
	
	/**
	 *  异步方法，根据合同编号获取对应的广告位
	 *  @param contractId 合同编码
	 */
	public void getAdSiteByContract(String contractId);
	
	/**
	 * 异步方法：根据广告位编码获取营销规则
	 * @param adSiteId 广告位编码
	 */
	public void getMarketRuleByAdSiteId(String adSiteId);
	
	/**
	 * 判断策略是否被占用
	 * @param ployId
	 */
	public boolean judgePloyOccupied(String ployId);
	
	/**
	 * 获取可以选择的区域。暂时不清楚业务
	 */
	public void getChoiceArea();
	
	/**
	 * 根据区域查询可选择的频道
	 * @return
	 */
	public String getChannelListByArea();
	
	/**
	 * 添加营销规则
	 * @return
	 */
	public String insertPloy();
	
	
	/**
	 * 修改营销规则
	 * @return
	 */ 
	public String updatePloy();
	
	/**
	 * 保存修改的营销规则
	 * @return
	 */
	public String saveUpdatePloy();
	
	/**
	 * 删除营销规则
	 * @return
	 */
	public String deletePloy(String[] ids);
	
	
	
	/**
	 * 展示营销规则列表
	 * @param j 
	 * @param i 
	 * @param conditionMap 
	 * @return
	 */

	public List<Ploy> getAllPloyList(Map conditionMap, int i, int j);

	public int getployCount(Map conditionMap);

	public void getContractByAdMerchantId(String merchantId);

	public Ploy getPloyInfo(String ployId);


}