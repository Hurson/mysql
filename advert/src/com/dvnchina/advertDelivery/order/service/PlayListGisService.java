package com.dvnchina.advertDelivery.order.service;

import java.util.Map;

import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;


public interface PlayListGisService {
	/**
	 * 根据订单插入播出单记录[上传资源文件]
	 * @param orderId 订单编号
	 * @return 0-成功，1-失败
	 * */
	public int insertPlayList(Integer orderId);
	
	/**
	 * 根据订单序号删除播出单记录[删除资源文件]
	 * @param orderId 订单编号
	 * @return 0-成功，1-失败
	 */
	public int deleteAllPlayList(Integer orderId);
		
	/**
	 * 根据订单编号删除原有播出单记录[删除资源文件]，插入更新后的播出单记录[上传资源文件]
	 * @param orderId 订单编号
	 * @return 0-成功，1-失败
	 */
	public int updateAllPlayList(Integer orderId);
	
	/**
	 * 根据订单时间更新播出单记录
	 * @param orderId 订单编号
	 * @return 0-成功，1-失败
	 */
	public int updatePlayListByDate(Order order);
	
	/**
	 * 根据精准ids获取精准对应的素材列表
	 * @param preciseIds
	 * @return
	 */
//	public List<MaterialBean> getPreciseMaterialByPreciseIds(String preciseIds);
	
	/**
	 * 根据素材ID获取图片文件大小
	 * @param id
	 * @return
	 */
	public String getImageMateFileSize(Integer id);
	
	/**
	 * 获取素材和文件大小关系
	 * @param mateIds
	 * @return
	 */
	public Map<Integer,String> getImageMateFileSizeMap(String mateIds);
	
	/**
	 * 根据频道ID获取serviceId字符信息
	 * @param channelId
	 * channel
	 * @return
	 */
	public String getDtvServiceById(String channelIds, ChannelInfo channel);
	
	public void repush (Integer orderId);

}
