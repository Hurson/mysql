package com.avit.ads.requestads.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.avit.ads.requestads.bean.AdPlaylistReq;
import com.avit.ads.requestads.bean.AdQuestionnaireData;
import com.avit.ads.requestads.bean.DefaultResource;
import com.avit.ads.requestads.bean.Question;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.bean.cache.AssetBean;
import com.avit.ads.requestads.bean.cache.ChannelInfoCache;
import com.avit.ads.requestads.bean.cache.ProductionCache;
import com.avit.ads.requestads.bean.cache.QuestionnaireReal;
import com.avit.ads.requestads.bean.cache.SurveyReport;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.cache.bean.TAssetV;
import com.avit.ads.requestads.cache.bean.TBsmpUser;
import com.avit.ads.requestads.cache.bean.TCategoryinfo;
import com.avit.ads.requestads.cache.bean.TLocationCode;
import com.avit.ads.requestads.cache.bean.TNoAdPloyView;
import com.avit.ads.requestads.cache.bean.TOrder;

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
	public void SaveSurveyReport(List<Question> list);

	/**
	 *  保存投放广告的记录
	 *  
	 *  @param AdPlaylistReqHistory 保存投放广告的记录的对象
	 */
	public boolean SaveStatusReport(List<AdStatus> list);

	/**
	 * 将加工得到的播出单保存入记录表中，为了保存投放记录的时候能够根据该方法保存的序号存入对应的记录中
	 * 
	 *  @param List<TempObjectStorgePlaylistContent> 加工过滤出的播出单信息
	 *  
	 *  
	 */
	public void SavePlaylistIntoHistoryReport(List<TempObjectStorgePlaylistContent> obj);

	/**
	 *  获取用户是否填写过问卷
	 *  
	 *  @param String 用户的唯一标识符
	 *  
	 *  @return Boolean 
	 */
	public boolean userBeSurvey(String tvnId);

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

	public void saveSurveySubmit(AdQuestionnaireData obj);

	/**
	 * 查询所有的问卷信息
	 * @return 问卷信息列表
	 */
	public List<SurveyReport> getQuestionnaireInfo();

	/**
	 * 查询所有的问卷模板
	 * @return 运行的问卷模板列表
	 */
	public List<QuestionnaireReal> getQuestionnaireReal();
	
	/**
	 * 查询所有会看类型的产品，用于做产品缓存
	 * @return List<ProductionCache>
	 */
	public List<ProductionCache> getProductCache(String type);

	/**
	 * 查询所有的频道信息，用做频道缓存
	 * @return List<ChannelInfoCache>
	 */
	public List<ChannelInfoCache> getChannelCache();

	/**
	 * 查询所有的影片信息，用做影片缓存 
	 * @return List<AssetBean>
	 */
	public List<AssetBean> getAssetCache();
	
	/**
	 * 查询当天的禁播TVN对应的广告位。
	 * @param today
	 * @return 当天禁播的tvn对应的广告位列表
	 */
	public List<String> getBanAdvertising(String tvn, Date today);

	public boolean getCategoryIdByAsset(String assetId, String categoryId);

	public List<TempObjectStorgePlaylistContent> getHistoryByToken(String token);

	public Map<String, String> getImageDefaultResource(int defaultResourceType);

	public Map<String, String> getTextDefaultResource(int defaultResourceType);

	public Map<String, String> getViedoDefaultResource(int defaultResourceType);

	public List<DefaultResource> getDefaultResource(String deafultResourceP);

	public void updatePlaylistPutInTimes(int playListId, int times);

	public boolean getPutInTimesEnough(int orderId);

	public boolean matchUserLocation(Integer locationCode, String userArea);
	
	public List<TCategoryinfo> queryCategory();
	public List<TLocationCode> queryLocationCode();
	public List<TBsmpUser> queryUser(Integer pageSize,Integer pageNumber);
	public TBsmpUser getUserByTVN(String tvn);
	
	public List<TOrder> queryOrder(Date startTime,Date endTime);
	public List<TAssetV> queryAssetV();
	public List<ProductionCache> queryProduct();
	public List<TNoAdPloyView> queryTNoAdPloyView();
	
	public void updateOrder(TOrder order);
	
	public void updateHistoryReport(List<TempObjectStorgePlaylistContent> objList);

}
