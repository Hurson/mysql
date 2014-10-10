package com.dvnchina.advertDelivery.order.dao;

import java.util.List;

import com.dvnchina.advertDelivery.order.bean.playlist.OrderBean;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

public interface PlayListDao {
	/**
	 * 查询订单信息
	 * 
	 * @param orderId
	 *            订单编号
	 * @return 订单对象
	 * */
	public OrderBean getOrderById(Integer orderId);

	/**
	 * 查询策略绑定 的区域
	 * 
	 * @param ployId
	 *            策略编号
	 * @return 区域映射（key-区域编码，value-策略绑定区域下频道的个数
	 * */
//	public Map<String, Integer> getAreaByPloy(Integer ployId);
	
	/**
	 * 根据策略ID获取区域、serviceId列表
	 * @param ployId
	 * @return
	 */
//	public List<PloyBean> getPloyChannelList(Integer ployId);
	
	/**
	 * 根据策略ID获取区域、serviceId列表
	 * @param ployId
	 * @return
	 */
//	public List<PloyBean> getPloyNpvrList(Integer ployId);

	/**
	 * 根据策略编码和区域编码查询订单绑定的频道serviceid
	 * 
	 * @param 策略编码
	 * @param areaId
	 *            区域编码
	 * @return 频道serviceid集合
	 * */
	public List<String> getChannelByArea(Integer ployId, String areaId);
	
	/**
	 * 根据频道ID获取serviceId列表（0获取全部）
	 * @param channelId
	 * @param channel
	 * @return
	 */
	public List<String> getDtvServiceById(String channelIds, ChannelInfo channel);
	
	/**
	 * 获取频道表中的所有serviceId列表
	 */
//	public List<String> getAllServiceIdList();
	
	/**
	 * 回放根据频道ID获取serviceId
	 */
	public List<String> getPlaybackServiceId(String channelIds);
	
	/**
	 * 回看根据ID获取栏目ID
	 */
	public List<String> getLookbackCategortId(String ids);
	
	/**
	 * 根据频道组IDS获取serviceIds
	 */
	public List<String> getServiceIdByGroupIds(String groupIds);
	
	/**
	 * 节目ID获取assetId
	 */
	public List<String> getAssetId(String ids);

	/**
	 * 查询区域总数
	 * 
	 * @return
	 */
	public int getAreaSize();

	/**
	 * 查询频道总数
	 * 
	 * @return
	 */
	public int getChannelSize();
	
}
