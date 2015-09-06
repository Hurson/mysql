package com.dvnchina.advertDelivery.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.order.bean.PlayListGis;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.order.bean.playlist.PloyPlayListGisRel;
import com.dvnchina.advertDelivery.order.bean.playlist.PrecisePlayListGisRel;

public interface PlayListGisDao extends PlayListDao {
	/**
	 * 获取策略对应的素材信息
	 * @param orderId
	 * @return
	 */
//	public List<MaterialBean> getPloyMaterialByOrder(Integer orderId);
	
	/**
	 * 获取策略对应的播出单
	 * @param orderId
	 * @param positionCode
	 * @return
	 */
	public List<PloyPlayListGisRel> getPloyPlayListGisByOrder(Integer orderId,String positionCode);
	
	/**
	 * 获取策略对应的播出单  点播随片
	 * @param orderId
	 * @param positionCode
	 * @return
	 */
	public List<PloyPlayListGisRel> getPloyPlayListGisByFollowOrder(Integer orderId,String positionCode);
	
	/**
	 * 获取精准对应的素材信息
	 * @param orderId
	 * @return
	 */
//	public List<MaterialBean> getPreciseMaterialByOrder(Integer orderId);
	/**
	 * 获取投放式精准相关的播出单信息
	 * @param orderId
	 * @return
	 */
	public List<PrecisePlayListGisRel> getPrecisePlayListByNPVROrder(Integer orderId);
	/**
	 * 获取投放式精准相关的播出单信息
	 * @param orderId
	 * @return
	 */
	public List<PrecisePlayListGisRel> getPrecisePlayListByOrder(Integer orderId);
	
	/**
	 * 获取投放式精准相关的播出单信息  点播随片
	 * @param orderId
	 * @return
	 */
	public List<PrecisePlayListGisRel> getPrecisePlayListByFollowOrder(Integer orderId);
	
	
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
	 * 批量插入播出单记录
	 * 
	 * @param playLists
	 *            播出单记录集合
	 */
	public void insertPlayList(List<PlayListGis> playLists);

	/**
	 * 根据订单编码删除对应的播出单记录
	 * 
	 * @param orderId
	 *            订单编码
	 */
	public void deleteAllPlayList(Integer orderId);

	/**
	 * 根据订单编号删除指定时间之后的播出单记录
	 * 
	 * @param orderId
	 *            订单编号
	 * @param endTime
	 *            指定日期时间
	 */
	public void deletePlayListByDate(Integer orderId, String endTime);

	/**
	 * 根据订单编号查询播出单结束日期
	 * 
	 * @param orderId
	 *            订单编号
	 * @return 结束日期
	 */
	public Date getPlayListEndTime(Integer orderId);

	/**
	 * 根据订单编号更新播出单结束时间
	 * 
	 * @param orderId
	 *            订单编号
	 * @param endTime
	 *            结束时间
	 */
	public void updateEndTime(Integer orderId, Date endTime);

	/**
	 * 根据订单编号查询一条播出单记录
	 * 
	 * @param orderId
	 *            订单编号
	 * @return 播出单对象
	 */
	public PlayListGis getPlayList(Integer orderId);

	/**
	 * 根据订单序号查询资源路径
	 * 
	 * @param orderId
	 *            订单号
	 * @return 资源路径
	 */
	public String getContentPath(Integer orderId);
	
	/**
	 * 根据订单ID查询订单对应的播出单ID列表
	 * @param orderId
	 * @return
	 */
	public List<Integer> getPlayListIds(Integer orderId);
	/**
	 * 根据订单ID重新投放
	 * @param orderId
	 * @return
	 */
	public void repush(Integer orderId);
	
	public List<PrecisePlayListGisRel> getNVODMenuPrecisePlayListByOrder(Integer orderId);
	
	public List<PrecisePlayListGisRel> getNVODAnglePrecisePlayListByOrder(Integer orderId);
	
	public List<String> getNVODAngleRelMateAreaCodeByOrder(Integer orderId);
	
}
