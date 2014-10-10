
package com.avit.ads.requestads.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.common.page.dao.impl.BaseDaoImpl;

/**
 *  决策子系统DAO
 *  
 *  @author chenkun
 *  @since 2013-02-22 create
 *  @version 1.0
 *  @modify_History 
 */
@Repository("ADSurveyDAOImp")
public class ADSurveyDAOImp extends BaseDaoImpl implements ADSurveyDAO  {

	private static Logger logger = LoggerFactory.getLogger(ADSurveyDAOImp.class);
	/**
	 *  保存调查问卷的题目和答案的内容
	 *  @param List<Question> 调查问卷的反馈问题和答案列表
	 *  
	 */
	public void SaveSurveyReport(List<Question> list) {
		this.saveAll(list);
	}

	/**
	 *  保存投放广告的记录
	 *  
	 *  @param List<AdStatus> 保存投放广告的记录的对象列表
	 * @return 是否执行成功，如果抛出异常则执行失败，其他情况执行成功，如果没有能更新到对应的数据，在日志中输出。
	 */
	public boolean SaveStatusReport(List<AdStatus> list) {
		
	//	Session session = this.getSession();
	//	Transaction ts = session.beginTransaction();
		
		int updatedNumber = 0;
		String failUpdateIds = "";
		for (AdStatus adStatus : list) {
			String hqlUpdate = "UPDATE AD_PLAYLIST_REQ_HISTORY H SET H.STATE = :newState WHERE ID = :id";
			
			//Query query = session.createSQLQuery(hqlUpdate);
			//query.setInteger("newState", adStatus.getStatus());
			//query.setString("id", adStatus.getSeq());
			//int num = query.executeUpdate();
			Object [] params= new Object[2];
			params[0]=adStatus.getStatus();
			params[1]=adStatus.getSeq();
			
			this.executeByHQL(hqlUpdate, params);
			// 记录更新失败的ID
//			if(num == 0){
//				failUpdateIds = failUpdateIds + adStatus.getSeq() + "/";
//			}
//			updatedNumber = updatedNumber + num;
		}
//		ts.commit();
//		session.flush();
//		if(updatedNumber < list.size()){
//			logger.error("在更新投放历史记录时，需要更新"+list.size()+"条记录，实际更新了"+updatedNumber+"条记录，ID为"
//					+ failUpdateIds + "的投放历史记录更新失败。");
//		}
		return true;
	}

	/**
	 * 将加工得到的播出单保存入记录表中，为了保存投放记录的时候能够根据该方法保存的序号存入对应的记录中
	 * 
	 *  @param List<AdPlaylistReq> 加工过滤出的播出单信息
	 *  
	 *  @return boolean true:保存成功，false:保存失败
	 */
	public void SavePlaylistIntoHistoryReport(List<TempObjectStorgePlaylistContent> list) {
		for (TempObjectStorgePlaylistContent tempObjectStorgePlaylistContent : list) {
			this.save(tempObjectStorgePlaylistContent);
		}

	}

	/**
	 *  获取用户喜爱的广告类型
	 *  
	 *  @param String 用户的唯一标识符
	 *  
	 *  @return List<String> 用户喜爱的广告的类型
	 */
	public boolean userBeSurvey(String tvnId) {
		return false;
	}

	/**
	 *  该用户是否被允许插播广告
	 *  
	 *  @param String 用户的唯一标识符
	 *  
	 *  @return boolean true:允许插播广告，false:不允许插播广告
	 */
	public boolean userBePermitShowAD(String tvnId) {
		return true;
	}
	
	/**
	 *  查出当天中被划分出的cache块的所有数据
	 *  
	 *  @param String divideBegin 划分cache的开始时间点（针对begin）
	 *  @param String divideEnd 划分cache的结束时间点（针对begin）
	 *  @param Date 当天
	 *  
	 *  @return List<AdPlaylistReq> 包含某个时间点的所有符合条件的播出单。
	 */
	public List<AdPlaylistReq> getPlaylistByTime(String divideBegin, String divideEnd, Date today) {
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		//String hql = "from AdPlaylistReq a where begin >= ? and end<=? and begin_date <= ? and end_date >= ?" ;
		//return this.getListForAll(hql, new Object[]{divideBegin, divideEnd, today, today});
		String hql = "from AdPlaylistReq a where 1=1 and begin_date <= ? and end_date >= ?" ;
		
		return this.getListForAll(hql, new Object[]{ today, today});
	}

	/**
	 *  保存调查问卷的题目和答案的内容
	 *  @param  obj 调查问卷的反馈问题和答案
	 *  
	 */
	public void saveSurveySubmit(AdQuestionnaireData obj) {
		this.save(obj);
	}

	/**
	 * 查询用户填写所有的问卷记录
	 */
	public List<SurveyReport> getQuestionnaireInfo() {
		String hql = "from AdQuestionnaireData a order by a.tvnId";
		return this.find(hql);
	}

	/**
	 * 查询所有的问卷模板
	 */
	public List<QuestionnaireReal> getQuestionnaireReal() {
		String hql = "from QuestionnaireReal";
		return this.find(hql);
	}
	
	/**
	 * 查询所有会看类型的产品，用于做产品精准请求
	 * @return List<ProductionCache>
	 */
	public List<ProductionCache> getProductCache(String type){
		String hql = "select new com.avit.ads.requestads.bean.cache.ProductionCache(p.productId) from ProductionCache p where p.type = ?";
		return this.find(hql, type);
	}

	/**
	 * 查询所有的频道信息，用做频道精准
	 * @return List<ChannelInfoCache>
	 */
	public List<ChannelInfoCache> getChannelCache() {
		String hql = "select distinct new com.avit.ads.requestads.bean.cache.ChannelInfoCache(c.serviceId, c.summaryShort) from ChannelInfoCache c where c.summaryShort is not null";
		return this.find(hql);
	}

	public List<AssetBean> getAssetCache() {
		String hql = "select distinct new com.avit.ads.requestads.bean.cache.AssetBean(a.assetId, a.keyword, a.director, a.actor, a.assetName) from AssetBean a ";
		return this.find(hql);
	}
	
	/**
	 * 查询当天的禁播TVN对应的广告位。
	 * @param today
	 * @return 当天禁播的tvn对应的广告位列表
	 */
	public List<String> getBanAdvertising(String tvn, Date today){
		Session session = this.getSession();
		String sql = "select distinct a.POSITION_CODE from t_no_ad_ploy b, t_advertposition a where b.POSITIONID = a.ID and b.start_Date <= :start and b.end_Date >= :end and b.tvn= :tvn";
		Query query = session.createSQLQuery(sql);
		
		query.setDate("start", today);
		query.setDate("end", today);
		query.setString("tvn", tvn);
		List<String> list = query.list();
		return list;
	}

	/**
	 * 使用影片Id去匹配精准中栏目的ID，如果没有，则继续向上追溯，如果匹配上，返回true
	 */
	public boolean getCategoryIdByAsset(String assetId, String categoryId) {
		Session session = null;
		boolean success = false;
		String hql = "select s.parentId from SynCpsCategory s where s.resourceId = ? ";
		List<String> list = (List<String>) this.getListForAll(hql, new Object[]{assetId});
		if(list.contains(categoryId)){
			success = true;
		}else{
			session = this.getSession();
			success = this.matchCategoryWithTraceBack(list, session, categoryId);
		}
		return success;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private boolean matchCategoryWithTraceBack(List<String> list, Session session, String categoryId) {
		boolean flag = false;
		if(list != null && list.size() > 0){
			List<String> lstP = null;
			String ids = "(";
			for (int i = 0, j=list.size(); i < j; i++) {
				if(i == j-1){
					ids += list.get(i)+")";
				}else{
					ids += list.get(i) + ",";
				}
			}
			String sql = "SELECT PARENT_ID from t_syn_cps_category where ID in " + ids;
			Query query = session.createSQLQuery(sql);
			List<?> lstObjs = query.list();
			if(lstObjs != null && lstObjs.size() > 0){
				lstP = new ArrayList<String>();
				for (int i = 0, j = lstObjs.size(); i <j; i++) {
					//Object[] obj = (Object[])lstObjs.get(i);
					BigDecimal obj = (BigDecimal)lstObjs.get(i);
					//String pId = (String) obj[0];
					String pId = obj.toString();
					lstP.add(pId);
					if(pId.equals(categoryId)){
						flag = true;
						break;
					}
				}
				if(!flag){
					flag = this.matchCategoryWithTraceBack(lstP, session, categoryId);
				}
			}
		}
		
		return flag;
	}

	/**
	 * 根据token获取保存的投放记录
	 */
	public List<TempObjectStorgePlaylistContent> getHistoryByToken(String token) {
		String hql = "from TempObjectStorgePlaylistContent t where t.token = ?";
		return this.find(hql, token);
	}

	/**
	 * 根据TVN号和问卷ID查询是否有填写过问卷
	 */
	public int getNumberOfWriteQuestionnaire(String tvn, String questionnaireId){
		Session session = this.getSession();
		String hqlUpdate = "SELECT COUNT(*) FROM ad_questionnaire_data a WHERE a.TVN_ID = :tvn and a.SURVEY_ID = :questionnaireId";
		Query query = session.createSQLQuery(hqlUpdate);
		query.setString("tvn", tvn);
		query.setString("questionnaireId", questionnaireId);
		
		if(query.list() != null){
			return query.list().size();
		}
		return 0;
	}

	public Map<String, String> getImageDefaultResource(int defaultResourceType) {
		Map<String, String> map = null;
    	String sql = "select r.id, t.name from t_image_meta t, t_resource r where t.id = r.resource_id and r.is_default = 1 ";
     	List<Object[]> list =(List<Object[]>)this.getDataBySql(sql, null);
		if(list!= null){
			map = new HashMap<String, String>();
			for (Object[] objects : list) {
				BigInteger resourceId = (BigInteger) objects[0];
				String name = (String) objects[1];
				map.put(resourceId.toString(), name);
			}
		}
		return map;
	}

	public Map<String, String> getTextDefaultResource(int defaultResourceType) {
		Map<String, String> map = null;
		String sql = "select r.id, t.id, t.name, t.content, t.url, t.action, t.DURATION_TIME, t.FONT_SIZE," +
				"t.FONT_COLOR, t.BACKGROUND_COLOR, t.POSITION_VERTEX_COORDINATES, t.POSITION_WIDTH_HEIGHT, t.ROLL_SPEED  " +
				"from t_resource r, t_text_meta t where r.RESOURCE_ID = t.id and r.is_default = 1";
		List<Object[]> list =(List<Object[]>)this.getDataBySql(sql, null);
		if(list!= null){
			map = new HashMap<String, String>();
			for (Object[] objects : list) {
				String json = getWritingJsonContent(objects);
				BigInteger resourceId = (BigInteger) objects[0];
				map.put(resourceId.toString(), json);
			}
		}
		return map;
	}

	/**
	 * 查询视频素材的 素材ID和路径， 用于默认素材缓存
	 */
	public Map<String, String> getViedoDefaultResource(int defaultResourceType) {
		Map<String, String> map = null;
		String sql = "select r.id, v.name, v.VIDEO_PUMP_PATH from t_resource r, t_video_meta v where r.RESOURCE_ID = v.id and r.is_default = 1";
		List<Object[]> list =(List<Object[]>)this.getDataBySql(sql, null);
		if(list!= null){
			map = new HashMap<String, String>();
			for (Object[] objects : list) {
				BigInteger resourceId = (BigInteger) objects[0];
				String name = (String) objects[1];
				String path = (String) objects[2];
				map.put(resourceId.toString(), path+"/"+name);
			}
		}
		return map;
	}

	/**
	 * 查询默认素材的广告位，素材类型和素材ID信息，用于默认素材缓存
	 */
	public List<DefaultResource> getDefaultResource(String defaultResourceP) {
		List<DefaultResource> lstDefault = null;
		String sql = "select a.POSITION_CODE, r.RESOURCE_ID, t.RESOURCE_TYPE from t_advertposition a, t_resource_ad r, t_resource t " +
				"where a.id = r.AD_ID and r.RESOURCE_ID = t.id and a.POSITION_CODE in "+defaultResourceP;
		List<Object[]> list =(List<Object[]>)this.getDataBySql(sql, null);
		if(list!= null){
			lstDefault = new ArrayList<DefaultResource>();
			for (Object[] objects : list) {
				DefaultResource defaultResource = new DefaultResource();
				String code = (String) objects[0];
				BigDecimal resourceId = (BigDecimal) objects[1];
				BigDecimal type = (BigDecimal) objects[2];
				defaultResource.setCode(code);
				defaultResource.setResourceId(resourceId.intValue());
				defaultResource.setType(type.intValue());
				lstDefault.add(defaultResource);
			}
		}
		return lstDefault;
	}
	
	/**
	 * 获取文字的JSON内容
	 * @param m
	 * @return
	 */
	private String getWritingJsonContent(Object[] str){
		
		
		StringBuffer jsonContent = new StringBuffer();
		
		jsonContent.append("{\"id\":\"").append((BigInteger)str[1]).append("\",")
		.append("\"name\":\"").append((String)str[2]).append("\",")
		.append("\"content\":\"").append(new String((byte[])str[3])).append("\",")
		.append("\"URL\":\"").append(str[4]==null?"":(String)str[4]).append("\",")
		.append("\"action\":\"").append((String)str[5]).append("\",")
		.append("\"durationTime\":\"").append(str[6]==null?0:(BigDecimal)str[6]).append("\",")
		.append("\"fontSize\":\"").append((BigDecimal)str[7]).append("\",")
		.append("\"fontColor\":\"").append((String)str[8]).append("\",")
		.append("\"bkgColor\":\"").append(str[8]==null?"":(String)str[8]).append("\",")
		.append("\"positionVertexCoordinates\":\"").append((String)str[10]).append("\",")
		.append("\"positionWidthHeight\":\"").append(str[11]==null?"":(String)str[11]).append("\",")
		.append("\"rollSpeed\":\"").append(str[12]==null?0:(BigDecimal)str[12]).append("\"}");
		return jsonContent.toString();
	}

	/**
	 * 更新按此投放播出单的投放次数
	 * @param 订单ID
	 */
	public void updatePlaylistPutInTimes(int orderId, int times) {
		Session session = this.getSession();
		String sql = "update t_order a set a.play_time = a.played_number +"+times+" where a.id = :id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("id", orderId);
		query.executeUpdate();
		session.flush();
	}

	/**
	 * 查看按次投放订单是否已达到投放次数
	 */
	public boolean getPutInTimesEnough(int orderId) {
		Session session = this.getSession();
		String sql = "select play_number, played_number from t_order where id = :id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("id", orderId);
		if(query.list() != null){
			Object[] obj = (Object[])query.list().get(0);
			Integer play = (Integer)obj[0];
			Integer played = (Integer)obj[1];
			if(play > played){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据传入的区域编码和精准中的区域编码判断是否匹配上用户区域的精准
	 */
	public boolean matchUserLocation(Integer locationCode, String userArea) {
		boolean done = false;
		Session session = this.getSession();
		String sql = "select PARENTLOCATION, LOCATIONTYPE  from t_location_code where LOCATIONCODE = :locationCode";
		Query query = session.createSQLQuery(sql);
		query.setInteger("locationCode", locationCode);
		if(query.list() != null && query.list().size() > 0){
			Object[] obj = (Object[])query.list().get(0);
			BigDecimal parentLocationCode = (BigDecimal)obj[0];
			String locationType = (String)obj[1];
			int type = Integer.valueOf(locationType);
			// 拼凑查询所有用户区域编码的SQL,用户所在最小区域是否匹配上精准已经匹配过，所以下面只匹配用户所在最小区域上级
			// 区域和精准的匹配
			StringBuffer sb = new StringBuffer(256);
			sb.append("select ");
			for (int i = 0; i < type-3; i++) {
				if(i == type-4){
					sb.append("t").append(i).append(".").append("LOCATIONCODE ");
				}else{
					sb.append("t").append(i).append(".").append("LOCATIONCODE, ");
				}
			}
			sb.append(" from ");
			for (int i = 0; i < type-3; i++) {
				if(i == type-4){
					sb.append("t_location_code t").append(i);
				}else{
					sb.append("t_location_code t").append(i).append(",");
				}
			}
			sb.append(" where ");
			for (int i = 0; i < type-4; i++) {
				sb.append("t").append(i).append(".LOCATIONCODE = t").append(i+1).append(".PARENTLOCATION").append(" and ");
			}
			sb.append("t").append(type-4).append(".LOCATIONCODE='").append(parentLocationCode).append("'");
			Query query2 = session.createSQLQuery(sb.toString());
			if(query2.list() != null){
				// 获取到所有用户所在最小区域的父区域来进行匹配
				Object[] data = (Object[])query2.list().get(0);
				for (int i = 0; i < data.length; i++) {
					BigDecimal area = (BigDecimal)data[i];
					if(userArea.equals(area.toString())){
						done = true;
						break;
					}
				}
			}
		}else{
			done = false;
		}
		return done;
	}
	public List<TCategoryinfo> queryCategory()
	{
		String sql = "from TCategoryinfo order by parentId ";
		List<TCategoryinfo> temp = this.getListForAll(sql,( Object[] )null);
		return temp;
	}
	public List<TLocationCode> queryLocationCode()
	{
		String sql = "from TLocationCode where locationtype>1 order by locationtype,parentlocation,locationcode";
		List<TLocationCode> temp = this.getListForAll(sql,( Object[] )null);
		return temp;
	}
	
	public List<TBsmpUser> queryUser(Integer pageSize,Integer pageNumber)
	{
		String sql = "from TBsmpUser ";
		List<TBsmpUser> userList =null;
		List temp = this.getListForPage(sql,( Object[] )null,pageNumber,pageSize);
		if (temp!=null)
		{
			userList = (List<TBsmpUser>)temp;
		}
		return userList;
	}
	public List<TOrder> queryOrder(Date startTime,Date endTime)
	{
		String sql = "from TOrder t where t.startTime<= ? and t.endTime >=?";
		Object[] params=new Object[2];
		Date today = startTime ;
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		params[0]=today;
		params[1]=today;
		List temp = this.getListForAll(sql,params);		
		return temp;
	}
	public List<TAssetV> queryAssetV()
	{
		String sql = "from TAssetV ";
		Object[] params=null;
		List temp = this.getListForAll(sql,params);		
		return temp;
	}

	public List<TNoAdPloyView> queryTNoAdPloyView() 
	{
		Date today = new Date(System.currentTimeMillis());
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		String sql = "from TNoAdPloyView where 1=1 and startDate <= ? and endDate >= ?";
		List temp = this.getListForAll(sql,new Object[]{ today, today});	
		return temp;
	}
	public List<ProductionCache> queryProduct()
	{
		String sql = "from ProductionCache ";
		Object[] params=null;
		List temp = this.getListForAll(sql,params);		
		return temp;
	}
	public TBsmpUser getUserByTVN(String tvn)
	{
		String sql = "from TBsmpUser where  usersn='"+tvn+"'";
		Object[] params=null;
		TBsmpUser user=null;
		try
		{
			List temp = this.find(sql);	
			if (temp!=null && temp.size()>0)
			{
				user=(TBsmpUser)temp.get(0);
			}
		}
		catch(Exception e)
		{
			
		}
		return user;
	}
	
	public void updateOrder(TOrder order)
	{
		String sql = "update TOrder t set t.playedNumber = t.playedNumber+"+order.getPlayedNumber()+" where t.playNumber<>0 and t.playedNumber<t.playNumber and t.id ="+order.getId();
		logger.info("update order:"+sql);
		this.executeByHQL(sql, ( Object[] )null);
		if (order.getPlayNumber()>0)
		{
			String updatesql  = "update TOrder t set t.endTime=? where t.playNumber<>0 and t.playedNumber>=t.playNumber and t.id ="+order.getId();
			Object[] params = new Object[1];
			Date  today = new Date();
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			params[0]= today;
			//更新结束时间
			this.executeByHQL(updatesql, params);	
			
			updatesql  = "update AdPlaylistReq p set p.endDate=? where p.orderId in (select t.id from TOrder t where t.playNumber<>0 and t.playedNumber>=t.playNumber and t.id ="+order.getId()+")";
			
			params[0]= today;
			//更新结束时间
			this.executeByHQL(updatesql, params);	
		}
	}
	public void updateHistoryReport(List<TempObjectStorgePlaylistContent> objList)
	{
		if (objList!=null)
		{
			for (int i = 0; i < objList.size(); i++) {
				TempObjectStorgePlaylistContent obj= objList.get(i);
				//扩展了暂停，挂角，字幕的seq为 9，8，7
				String sql = "update TempObjectStorgePlaylistContent t set t.state = "+obj.getState()+" where 1=1 and t.token ='"+obj.getToken()+"'";
			    if (obj.getSeq()!=null)
			    {
			    	sql= sql + " and t.seq='" +obj.getSeq()+"'";
			    }
				this.executeByHQL(sql, ( Object[] )null);	
				//String sqlorder = "update TOrder t set t.playedNumber = t.playedNumber+1 where t.id in (select orderId from TempObjectStorgePlaylistContent where token ='"+obj.getToken()+"')";
				//this.executeByHQL(sqlorder, ( Object[] )null);
			}
		}
	}

}
