package com.avit.ads.pushads.task.dao;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.avit.ads.dtmb.bean.PlayList;
import com.avit.ads.pushads.task.bean.AdPlaylistGis;
import com.avit.ads.util.bean.Ads;

// TODO: Auto-generated Javadoc
/**
 * The 推送式广告数据库操作接口.
 */
public interface PushAdsDao {
    
	public HibernateTemplate getTemplate();
	
	/**
	 * 查询待发送播出单.
	 *
	 * @param startTime 播出单投放开始时间
	 * @param adsTypeCode 广告位编码
	 * @return the list
	 */
	List<AdPlaylistGis> queryStartAds(Date startTime,String adsTypeCode);
	
	List<AdPlaylistGis> queryNewPlayList(Date startTime,List<Ads> adsList);
	
	/**
	 * 查询待发送播出单.
	 *
	 * @param preSecond 播出单投放提前量 单位秒
	 * @param startTime 播出单投放开始时间
	 * @param adsTypeCode 广告位编码
	 * @return the list
	 */
	List<AdPlaylistGis> queryStartAds(String adsTypeCode,String state,Date startTime);
	/**
	 * 查询发送结束播出单.
	 *
	 * @param endTime 播出单投放结束时间
	 * @param adsTypeCode 广告位编码
	 * @return the list
	 */
	List<AdPlaylistGis> queryEndAds(Date endTime,String adsTypeCode);
	
	String queryImageUrlByPlayListId(Integer playListID);
	
	/**
	 * 更新播出单播出标识.
	 *
	 * @param adsid 广告播出单ID
	 * @param flag 状态；0为未投放，1为已投放，2为投放失败，3为取消投放 4投放完成
	 */
	void updateAdsFlag(Long adsid,String flag);
	/**
	 * 更新播出单播出标识.
	 *
	 * @param adsid 广告播出单ID
	 * @param flag 状态；0为未投放，1为已投放，2为投放失败，3为取消投放 4投放完成
	 */
	void updateAdsFlag(String adsids,String flag);
	
	/**
	 * 
	 * @param adsid
	 * @param flag
	 */
	public void updateDAdsFlag(Integer adsid,String flag);
	
	/**
	 * 通过区域码和serviceId获得频道的频点Id
	 * @param areaCode
	 * @param serviceId
	 * @return
	 */
	public String getAreaChannelTsId(String areaCode, String serviceId);
	
	/**
	 * 更新订单状态为投放失败
	 * @param adsid
	 * @param flag
	 */
	public void updateOrderState(Long adsid,String flag);
	
	/**
	 * 
	 * @param adsid
	 * @param flag
	 */
	public void updateDOrderState(final Integer adsid, final String flag);
	/**
	 * 查询无线实时广告有效播出单
	 */
	public List<PlayList> querySendDTMBAds();
	/**
	 * 
	 * @param adsTypeCode
	 * @return
	 */
	public List<PlayList> queryDStartAds(String adsTypeCode);
	/**
	 * 
	 * @param adsTypeCode
	 * @return
	 */
	public List<PlayList> queryDEndAds(String adsTypeCode);
	
}
