package com.avit.ads.requestads.dao;

import java.sql.Date;
import java.util.List;

import com.avit.ads.requestads.bean.AdPlaylistReq;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.bean.request.UserSurveyResult;

/**
 * The Interface ADSurveyDAO.
 * 
 *  @author chenkun
 *  @since 2013-02-22 create
 *  @version 1.0
 *  @modify_History 
 */
public interface ADSurveyDAO {

	/**
	 *  保存调查问卷的题目和答案的内容
	 *  @param List<UserSurveyResult> 调查问卷的反馈问题和答案列表
	 *  
	 */
	public void SaveSurveyReport(List<UserSurveyResult> list);

	/**
	 *  保存投放广告的记录
	 *  
	 *  @param AdPlaylistReqHistory 保存投放广告的记录的对象
	 */
	public boolean SaveStatusReport(List<AdStatus> list);

	/**
	 * 将加工得到的播出单保存入记录表中，为了保存投放记录的时候能够根据该方法保存的序号存入对应的记录中
	 * 
	 *  @param List<AdPlaylistReq> 加工过滤出的播出单信息
	 *  
	 *  
	 */
	public List<TempObjectStorgePlaylistContent> SavePlaylistIntoHistoryReport(List<TempObjectStorgePlaylistContent> obj);

	/**
	 *  获取用户喜爱的广告类型
	 *  
	 *  @param String 用户的唯一标识符
	 *  
	 *  @return List<String> 用户喜爱的广告的类型
	 */
	public List<String> getUserFavType(String tvnId);

	/**
	 *  该用户是否被允许插播广告
	 *  
	 *  @param String 用户的唯一标识符
	 *  
	 *  @return boolean true:允许插播广告，false:不允许插播广告
	 */
	public boolean userBePermitShowAD(String tvnId);
	
	/**
	 *  根据当前的日期和事件查询所有符合条件的播出单
	 *  
	 *  @param String strDate 当天的日期
	 *  @param String strTime 当前的时间点
	 *  
	 *  @return 包含某个时间点的所有符合条件的播出单。
	 */
	public List<AdPlaylistReq> getPlaylistByTime(String divideBegin, String divideEnd, Date today);
	
}
