package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.Questionnaire;

public interface QuestionnaireDao extends BaseDao{

	public List<Questionnaire> listQuestionnairesByPage(int begin, int pageSize);

	public int getQuestionnairesCount();
	
	public void deleteQuestion(Integer questionnaireId);

}
