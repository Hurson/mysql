package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import com.dvnchina.advertDelivery.dao.QuestionnaireDao;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.model.Questionnaire;

public class QuestionnaireDaoImpl extends BaseDaoImpl implements QuestionnaireDao{
	@Override
	public List<Questionnaire> listQuestionnairesByPage(int begin,int pageSize){
		String hql = "from Questionnaire";
		return list(hql, begin, pageSize);
	}

	@Override
	public int getQuestionnairesCount() {
		String hql = "from Questionnaire";
		return getCount(hql);
	}

	@Override
	public void deleteQuestion(Integer questionnaireId) {
		String hql = "from Question where questionnaireId=?";
		List<Question> questions = list(hql,questionnaireId);
		for(Question q : questions){
			delete(q);
		}
	}
	
}
