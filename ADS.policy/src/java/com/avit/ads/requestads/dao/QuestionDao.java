package com.avit.ads.requestads.dao;

import java.util.List;

import com.avit.ads.requestads.bean.TQuestionPlaylistv;
import com.avit.ads.requestads.bean.TQuestionnaireReal;
import com.avit.ads.requestads.bean.TUserQuestion;
import com.avit.ads.requestads.bean.TUserQuestionnaire;

public interface QuestionDao {
	/**
	 * 校验用户回答问卷次数是否合法 
	 * 
	 * @param String tvn
	 * @param String questionnaire_id 问卷ID 
	 * @return TQuestionPlaylistv.retvalue 1 合法  2 用户回答问卷次数非法 3 问卷总次数非法 4 此问卷当前无订单
	 */
	public TQuestionPlaylistv validateQusetionTimes(String tvn,String questionnaire_id);
	/**
	 * 获取问卷信息 
	 * 
	  * @param String questionnaire_id 问卷ID 
	 * @return 
	 */
	public TQuestionnaireReal getQuestionnaireReal(String questionnaire_id);
	
	/**
	 * 根据TVN号获取积分 
	 * 
	 * @param String tvn
	 * @return 
	 */
	public Long getScoreByTVN(String tvn);
	/**
	 * 根据问卷积分 
	 * 
	 * @param String tvn
	 * @return 
	 */
	public Long getScoreByqQuestionnaireId(String questionnaire_id);
	/**
	 * 校验用户回答问卷次数是否合法 
	 * 
	 * @param String tvn
	 * @param String questionnaire_id 问卷ID 
	 * @return 
	 */
	public boolean saveQuestion(List<TUserQuestion> questionList ,TUserQuestionnaire userQuestionnaire,Long score);
	
	
}
