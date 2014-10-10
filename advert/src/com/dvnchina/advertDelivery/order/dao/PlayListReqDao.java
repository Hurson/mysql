package com.dvnchina.advertDelivery.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.order.bean.PlayListReq;
import com.dvnchina.advertDelivery.order.bean.PlayListReqContent;
import com.dvnchina.advertDelivery.order.bean.PlayListReqPContent;
import com.dvnchina.advertDelivery.order.bean.PlayListReqPrecise;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;

public interface PlayListReqDao extends PlayListDao {
	/**
	 * 根据订单编号查询绑定的素材
	 * 
	 * @param orderId
	 *            订单编号
	 * @return 素材集合
	 */
	public List<MaterialBean> getMaterialByOrder(Integer orderId);
	
	/**
	 * 根据订单编号查询订单结束日期
	 * 
	 * @param orderId
	 *            订单编号
	 * @return 结束日期
	 */
	public Date getOrderEndDate(Integer orderId);

	/**
	 * 批量插入播出单记录
	 * 
	 * @param playLists
	 *            播出单记录集合
	 */
	public void insertPlayList(List<PlayListReq> playLists);
	
	/**
	 * 插入播出单记录
	 * 
	 * @param playLists
	 *            播出单记录
	 */
	public void insertPlayList(PlayListReq playList);
	
	/**
	 * 根据订单编号查询播出单id
	 * @param orderId 订单编号
	 * @return 播出单id
	 */
	public Integer getPlayListId(Integer orderId);
	
	/**
	 * 根据订单ID获取请求播出单列表
	 * @param orderId
	 * @return
	 */
	public List<PlayListReq> getPlayList(Integer orderId);
	
	/**
	 * key-id,value-特征值
	 * */
	public Map<Integer,String> getPlayListMap(Integer orderId);

	/**
	 * 保存播出单内容
	 * @param contents
	 */
	public void insertContents(List<PlayListReqContent> contents);

	/**
	 * 保存播出单精准
	 * @param precise
	 * @return
	 */
	public Integer insertPrecise(PlayListReqPrecise precise);

	/**
	 * 保存播出单精准内容
	 * @param pContents
	 */
	public void insertPContent(List<PlayListReqPContent> pContents);

	/**
	 * 根据订单编号删除对应的播出单记录
	 * 
	 * @param orderId
	 *            订单编号
	
	public void deletePlayList(Integer orderId); */

	/**
	 * 根据订单编号更新播出单结束时间
	 * 
	 * @param orderId
	 *            订单编号
	 * @param endTime
	 *            结束时间
	 */
	public void updateEndTime(Integer orderId, Date endDate);
	
	/**
	 * 根据播出单序号查询精准序号
	 * @param playListIds
	 * @return
	 */
	public List<Integer> getPreciseIds(List<Integer> playListIds);
	

	/**
	 * 删除播出单，播出单内容，播出单精准，精准内容表的记录
	 * @param playListIds
	 * @param preciseIds
	 */
	public void deletePlayLists(List<Integer> playListIds,List<Integer> preciseIds);
	
	/**
	 * 删除播出单，播出单内容表的记录
	 * @param playListIds
	 * @param preciseIds
	 */
	public void deletePlayLists(List<Integer> playListIds);

	/**
	 * 根据播出单序号查询内容中内容类型为字幕的资源路径
	 * @param playListIds
	 * @return
	 */
	public List<String> getWritingContentPath(List<Integer> playListIds);

	/**
	 * 根据播出单精准序号查询精准内容中内容类型为字幕的资源路径
	 * @param playListIds
	 * @return
	 */
	public List<String> getPWritingContentPath(List<Integer> preciseIds);
	
	/**
	 * 根据订单ID查询订单对应的播出单ID列表
	 * @param orderId
	 * @return
	 */
	public List<Integer> getPlayListIds(Integer orderId);
}
