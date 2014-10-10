package com.avit.ads.requestads.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.avit.ads.requestads.bean.TQuestionPlaylistv;
import com.avit.ads.requestads.bean.TQuestionnaireReal;
import com.avit.ads.requestads.bean.TUserQuestion;
import com.avit.ads.requestads.bean.TUserQuestionnaire;
import com.avit.ads.requestads.dao.QuestionDao;
import com.avit.ads.requestads.service.QuestionService;
import com.avit.common.util.StringUtil;
@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {
	@Inject
	QuestionDao questionDao;
	//TQuestionPlaylistv.retvalue 
	public TQuestionPlaylistv validateQusetionTimes(String tvn, String questionnaire_id) {
		// TODO Auto-generated method stub
		
		return questionDao.validateQusetionTimes(tvn, questionnaire_id);
	}
	/**
	 * 获取问卷信息 
	 * 
	  * @param String questionnaire_id 问卷ID 
	 * @return 
	 */
	public TQuestionnaireReal getQuestionnaireReal(String questionnaire_id)
	{
		return questionDao.getQuestionnaireReal(questionnaire_id);
	}
	public Long getScoreByTVN(String tvn) {
		// TODO Auto-generated method stub
		return questionDao.getScoreByTVN(tvn);
	}

	public boolean save(List<TUserQuestion> questionList,TUserQuestionnaire userQuestionnaire) {
		// TODO Auto-generated method stub
		
		Long score =  questionDao.getScoreByqQuestionnaireId(StringUtil.toNotNullStr(userQuestionnaire.getQuestionnaireId()));
		return questionDao.saveQuestion(questionList, userQuestionnaire, score);
	}

}
