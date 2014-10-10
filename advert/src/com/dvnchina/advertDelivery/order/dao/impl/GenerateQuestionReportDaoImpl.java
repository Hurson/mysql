package com.dvnchina.advertDelivery.order.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.order.bean.PlayListResource;
import com.dvnchina.advertDelivery.order.bean.PutInPlayListBean;
import com.dvnchina.advertDelivery.order.bean.RequestPlayListBean;
import com.dvnchina.advertDelivery.order.dao.GenerateQuestionReportDao;
import com.dvnchina.advertDelivery.order.dao.PlayList4OrderDao;

public class GenerateQuestionReportDaoImpl extends JdbcDaoSupport implements GenerateQuestionReportDao {



	
	/**
	 * 
	 * @description: 获取结束的问卷订单报表表头信息(包括广告商,问卷名称,问卷积分,订单起始日期) 
	 * @return 
	 * List<Map<String,Object>>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-12-3 上午10:57:24
	 */
	public List<Map<String, Object>> getQuestionReportTitle() {
		String sql = "select r.CUSTOMER_ID customerId,c.ADVERTISERS_NAME customerName,r.RESOURCE_NAME questionName," +
				" q.INTEGRAL integral,q.ID questionId,o.ID orderId,o.ORDER_CODE orderCode,o.START_TIME orderStartTime,o.END_TIME orderEndTime "+
				"from  t_order o,t_order_mate_rel omr, t_advertposition adp,t_resource r,t_customer c,t_questionnaire_real q"+
				" where o.POSITION_ID=adp.ID and adp.IS_QUESTION=1 and o.ID=omr.ORDER_ID and omr.MATE_ID=r.ID and  c.id=r.CUSTOMER_ID and q.ID=r.RESOURCE_ID "+
				"  and o.END_TIME=?"; 
        
		Date nowDate= new Date();
        Calendar c1 =Calendar.getInstance();
        c1.setTime(nowDate);
        c1.add(Calendar.DATE,-1);
        nowDate=c1.getTime();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return getJdbcTemplate().queryForList(sql,new Object[]{df.format(nowDate)});
		
	}
	
	/**
	 * 
	 * @description: 获取结束的问卷订单报表表细节信息(包括tvn号码,问题,答案,用户所属城市,用户所属街道以及问卷提交时间)  
	 * @param orderId
	 * @param questionId
	 * @return 
	 * List<Map<String,Object>>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-12-3 下午03:50:29
	 */
	public List<Map<String, Object>> getQuestionReportContent(Integer orderId,Integer questionId) {
        String sql = "select uq.create_time submitTime,uq.USERSN tvn,ua.QUESTIONNAME question,ua.OPTIONS_NAME answer,l.LOCATIONNAME city,lc.LOCATIONNAME street " +          
                " from t_user_questionnaire uq,t_user_question ua, t_bsmp_user u,t_location l,t_location_code lc "+
                " where uq.ID=ua.user_questionnaire_id  and uq.order_id=? and uq.QUESTIONNAIRE_id=? "+
                " and uq.USERSN = u.USERSN and u.LOCATIONCODE=l.LOCATIONCODE and u.LOCATIONCODEVALUE = lc.LOCATIONCODE";
        
        return getJdbcTemplate().queryForList(sql,new Object[]{orderId,questionId});
        
    }
	
	
	/**
	 * 
	 * @description: 获取问题列表
	 * @param id
	 * @return 
	 * List<Map<String,Object>>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-12-9 下午05:56:33
	 */
	public List<Map<String, Object>> getQuestionList(Integer id) {
        String sql = "select t.question from t_question_real t where t.questionnaire_id=? group by t.question";
        
        return getJdbcTemplate().queryForList(sql,new Object[]{id});
        
    }
	
	/**
	 * 
	 * @description: 获取问卷提交次数
	 * @param orderId
	 * @param questionId
	 * @return 
	 * List<Map<String,Object>>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-12-9 下午06:20:17
	 */
	public List<Map<String, Object>> getSubmitCount(Integer orderId,Integer questionId) {
        String sql = "select uq.ID submitId,uq.create_time submitTime,uq.USERSN tvn,u.LOCATIONCODEVALUE locationCode from t_user_questionnaire uq , t_bsmp_user u "+
                     "where uq.order_id=? and uq.QUESTIONNAIRE_id=? and uq.USERSN = u.USERSN group by uq.create_time,uq.USERSN";
        
        return getJdbcTemplate().queryForList(sql,new Object[]{orderId,questionId});
        
    }

	/**
	 * 
	 * @description: 获取每一个问题的答案数目 
	 * @param id
	 * @param question
	 * @return 
	 * List<Map<String,Object>>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-12-10 上午09:26:55
	 */
	public int getAnswerCount(Integer id,String question) {
        String sql = "select count(*) answerCount from t_question_real t where t.questionnaire_id=? and t.QUESTION=?";
        
        return getJdbcTemplate().queryForInt(sql,new Object[]{id,question});
        
    }
	
	/**
	 * 
	 * @description: 获取每一个问题的详细答案
	 * @param id
	 * @param question
	 * @return 
	 * int
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-12-10 下午07:12:32
	 */
	public List<Map<String, Object>> getAnswerList(Integer id,String question) {
        String sql = "select t.QUESTION question,t.OPTIONS answer from t_question_real t where t.questionnaire_id=? and t.QUESTION=?";
        
        return getJdbcTemplate().queryForList(sql,new Object[]{id,question});
        
    }
	
	/**
	 * 
	 * @description: 获取每次提交问卷的内容 
	 * @param submitId
	 * @param tvn
	 * @return 
	 * List<Map<String,Object>>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-12-10 上午10:56:04
	 */
	public List<Map<String, Object>> getAnswerContent(Integer submitId,String tvn) {
        String sql = "select t.QUESTIONNAME question,t.OPTIONS_NAME answer,t.user_questionnaire_id submitId,t.USERSN tvn from t_user_question t where t.user_questionnaire_id= ? and t.USERSN=?";
        
        return getJdbcTemplate().queryForList(sql,new Object[]{submitId,tvn});
        
    }

	

}
