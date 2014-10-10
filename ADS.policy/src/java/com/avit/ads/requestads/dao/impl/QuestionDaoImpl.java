package com.avit.ads.requestads.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.avit.ads.requestads.bean.TQuestionPlaylistv;
import com.avit.ads.requestads.bean.TQuestionnaireReal;
import com.avit.ads.requestads.bean.TUserQuestion;
import com.avit.ads.requestads.bean.TUserQuestionnaire;
import com.avit.ads.requestads.bean.TUserScore;
import com.avit.ads.requestads.cache.bean.TBsmpUser;
import com.avit.ads.requestads.dao.QuestionDao;
import com.avit.ads.requestads.service.impl.AdrequestProcessServiceImpl;
import com.avit.ads.util.InitConfig;
import com.avit.common.page.dao.impl.BaseDaoImpl;
import com.avit.common.util.StringUtil;
@Repository("QuestionDaoImpl")
public class QuestionDaoImpl extends BaseDaoImpl  implements QuestionDao {
	private static Logger logger = LoggerFactory.getLogger(QuestionDaoImpl.class);
	public static int USER_FAIL=2;
	public static int QEUSTION_FAIL=3;
	public static int ORDER_FAIL=4;
	public static int SUCCESS=1;
	
	/**
	 * 校验用户回答问卷次数是否合法 
	 * 
	 * @param String tvn
	 * @param String questionnaire_id 问卷ID 
	 * @return TQuestionPlaylistv.retvalue 1 合法  2 用户回答问卷次数非法 3 问卷总次数非法 4 此问卷当前无订单
	 */
	public TQuestionPlaylistv validateQusetionTimes(String tvn, String questionnaire_id) {
		// TODO Auto-generated method stub
		//查询订单是否合法
		
		TQuestionPlaylistv retuestionPlaylistv =new TQuestionPlaylistv();
		retuestionPlaylistv.setRetvalue(0);
		String sql = "from TQuestionPlaylistv where  questionnaireId="+questionnaire_id;
		Object[] params=null;
		String orderid="";
		int count=0;
		//单个问卷回答 最大次数
		int maxCount=0;// = StringUtil.toInt(InitConfig.getConfigMap().get("maxanswercount_per_question"));
		//用户回答问卷次数
		int usermaxCount =0;// StringUtil.toInt(InitConfig.getConfigMap().get("maxanswercount_per_user"));
		int scoreScale =0;
		List tempList =null;
		//验证是否有此问卷的订单
		try
		{
			sql = sql + " and beginDate<=? and endDate>=?" ;
			params =  new Object[2];
			Date startdate = new Date();
			Date enddate = new Date();
			
			startdate.setHours(0);
			startdate.setMinutes(0);
			startdate.setSeconds(0);
			enddate.setHours(0);
			enddate.setMinutes(0);
			enddate.setSeconds(0);
			params[0]=startdate;
			params[1]=enddate;
			
			tempList = this.getListForAll(sql,params);	
			if (tempList!=null && tempList.size()>0)
			{
				//count = 
				TQuestionPlaylistv temp =new TQuestionPlaylistv();
				temp= (TQuestionPlaylistv)tempList.get(0);
				usermaxCount = temp.getMaxPeruser();
				maxCount     = temp.getMaxPerquestion();
				scoreScale   = temp.getScoreScale();
				retuestionPlaylistv.setOrderId(temp.getOrderId());
				//
				//验证此用户所回答的问卷订单的次数			
				sql = "select count(*) from TUserQuestionnaire where  usersn='"+tvn+"' and questionnaireId="+questionnaire_id +" and orderId="+temp.getOrderId().toString();
				params=null;
				TUserQuestionnaire user=null;
//				int count=0;
//				//单个问卷回答 最大次数
//				int maxCount = StringUtil.toInt(InitConfig.getConfigMap().get("maxanswercount_per_question"));
//				//用户回答问卷次数
//				int usermaxCount = StringUtil.toInt(InitConfig.getConfigMap().get("maxanswercount_per_user"));
				
				try
				{
					count = this.getTotalCountHQL(sql, null);
					logger.info("count=" +count +";sql="+sql);
					logger.info("usermaxCount=" +usermaxCount +";maxCount="+maxCount);
				}
				catch(Exception e)
				{
					logger.error(e.getMessage());
				}
				if (count>=usermaxCount)
				{
					retuestionPlaylistv.setRetvalue(USER_FAIL);
					
					return retuestionPlaylistv;
				}
				else
				{
					//验证此问卷订单总次数
					sql = "select count(*) from TUserQuestionnaire where  questionnaireId="+questionnaire_id +" and orderId="+temp.getOrderId().toString();
					params=null;
					count=0;
					try
					{
						count = this.getTotalCountHQL(sql, null);
					}
					catch(Exception e)
					{
						logger.error(e.getMessage());
					}
					if (count>=maxCount)
					{
						retuestionPlaylistv.setRetvalue(QEUSTION_FAIL);
						return retuestionPlaylistv;
					}
					retuestionPlaylistv.setRetvalue(SUCCESS);
					return retuestionPlaylistv;
				}
				//
			}
			else
			{
				retuestionPlaylistv.setRetvalue(ORDER_FAIL);
//				retuestionPlaylistv.setRetvalue(SUCCESS);
//				retuestionPlaylistv.setOrderId(new BigDecimal(0));
				return retuestionPlaylistv;
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return retuestionPlaylistv;
		}
	}
	/**
	 * 获取问卷信息 
	 * 
	  * @param String questionnaire_id 问卷ID 
	 * @return 
	 */
	public TQuestionnaireReal getQuestionnaireReal(String questionnaire_id)
	{
		
		TQuestionnaireReal temp=null ;
		try
		{
		temp=(TQuestionnaireReal)this.get(TQuestionnaireReal.class, StringUtil.toLong(questionnaire_id));
		
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return temp;
	}
	public Long getScoreByTVN(String tvn) {
		// TODO Auto-generated method stub
		String sql = "from TUserScore where  usersn='"+tvn+"'";
		Object[] params=null;
		TUserScore user=null;
		Long score=0L;
		try
		{
			List temp = this.find(sql);	
			if (temp!=null && temp.size()>0)
			{
				user=(TUserScore)temp.get(0);
				score = user.getScore();
				if (score==null)
				{
					score=0L;
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return score;
	}

	public Long getScoreByqQuestionnaireId(String questionnaire_id) {
		// TODO Aut-generated method stub
		String sql = "from TQuestionnaireReal where  id="+questionnaire_id;
		Long score=0L;
		Object[] params=null;
		TQuestionnaireReal questionnaireReal=null;
		try
		{
			List temp = this.find(sql);	
			if (temp!=null && temp.size()>0)
			{
				questionnaireReal=(TQuestionnaireReal)temp.get(0);
				score =  questionnaireReal.getIntegral();
			}
			if (score==null)
			{
				score=0L;
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return score;
	}

	public boolean saveQuestion(List<TUserQuestion> questionList,TUserQuestionnaire userQuestionnaire,Long score) {
		// TODO Auto-generated method stub
		
	//	this.saveAll(questionList);
		//TUserQuestionnaire userQuestionnaire =  new TUserQuestionnaire();			
		String sql ="";
		Object[] params=null;
		//TUserScore user=null;
		try
		{
			userQuestionnaire.setCreateTime(new Date());
			
			//保存问卷话单 			
			this.save(userQuestionnaire);
			//保存答案详情	
			if (questionList!=null  && questionList.size()>0)
			{
				for (int i=0;i<questionList.size();i++)
				{
					questionList.get(i).setUser_questionnaire_id(userQuestionnaire.getId());
				}
				
				this.saveAll(questionList);
			}
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		
		
		//保存用户积分
		sql = "from TUserScore where  usersn='"+userQuestionnaire.getUsersn()+"'";
		params=null;
		//TUserScore user=null;
		try
		{
			List temp = this.find(sql);	
			if (temp!=null && temp.size()>0)
			{
				String updateSql = "update TUserScore   set score= score+"+score+" where  usersn='"+userQuestionnaire.getUsersn()+"'";
				this.executeByHQL(updateSql, null);
			}
			else
			{
				TUserScore userScore =  new TUserScore();
				userScore.setScore(score);
				userScore.setUsersn(userQuestionnaire.getUsersn());
				this.save(userScore);
				
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}		
		return true;
	}

}
