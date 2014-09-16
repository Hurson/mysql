
package com.avit.ads.requestads.dao.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.avit.ads.requestads.bean.AdPlaylistReq;
import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.bean.request.UserSurveyResult;
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
@Repository
public class ADSurveyDAOImp extends BaseDaoImpl implements ADSurveyDAO  {

	/**
	 *  保存调查问卷的题目和答案的内容
	 *  @param List<UserSurveyResult> 调查问卷的反馈问题和答案列表
	 *  
	 */
	public void SaveSurveyReport(List<UserSurveyResult> list) {
	}

	/**
	 *  保存投放广告的记录
	 *  
	 *  @param List<AdStatus> 保存投放广告的记录的对象列表
	 * @return 
	 */
	public boolean SaveStatusReport(List<AdStatus> list) {
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		for (AdStatus adStatus : list) {
			String hqlUpdate = "UPDATE AD_PLAYLIST_REQ_HISTORY H SET H.STATE = :newState WHERE ID = :id";
			
			Query query = session.createSQLQuery(hqlUpdate);
			query.setInteger("newState", adStatus.getStatus());
			query.setLong("id", adStatus.getSeq());
			int num = query.executeUpdate();
		}
		ts.commit();
		session.flush();
		return true;
	}

	/**
	 * 将加工得到的播出单保存入记录表中，为了保存投放记录的时候能够根据该方法保存的序号存入对应的记录中
	 * 
	 *  @param List<AdPlaylistReq> 加工过滤出的播出单信息
	 *  
	 *  @return boolean true:保存成功，false:保存失败
	 */
	public List<TempObjectStorgePlaylistContent> SavePlaylistIntoHistoryReport(List<TempObjectStorgePlaylistContent> list) {
		return (List<TempObjectStorgePlaylistContent>) this.saveAll(list);
	}

	/**
	 *  获取用户喜爱的广告类型
	 *  
	 *  @param String 用户的唯一标识符
	 *  
	 *  @return List<String> 用户喜爱的广告的类型
	 */
	public List<String> getUserFavType(String tvnId) {
		return null;
	}

	/**
	 *  该用户是否被允许插播广告
	 *  
	 *  @param String 用户的唯一标识符
	 *  
	 *  @return boolean true:允许插播广告，false:不允许插播广告
	 */
	public boolean userBePermitShowAD(String tvnId) {
		return false;
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
		String hql = "from AdPlaylistReq a where begin >= ? and begin <=  ? and begin_date < ? and end_date > ?" ;
		return this.getListForAll(hql, new Object[]{divideBegin, divideEnd, today, today});
	}

}
