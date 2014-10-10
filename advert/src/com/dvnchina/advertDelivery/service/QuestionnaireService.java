package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.question.TemplateJsBean;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.model.Questionnaire;

public interface QuestionnaireService {

	public TemplateJsBean getQuesitonnaireContainer(String jsPath);

	public Integer insertQuestionnaire(Questionnaire questionnaire);
	
	public Questionnaire getQuestionnaireById(Integer id);
	
	public List<Questionnaire> listQuestionnaireByPage(int begin,int pageSize);
	
	public int getQuestionnairesCount();

	public void insertQuestions(List<Question> questions, Integer qId);

	public void generateQuestionnaire(String tHtmlPath,
			TemplateJsBean templateJsBean, Questionnaire questionnaire, List<Question> questions);
	
	public void deleteQuestionnaire(Questionnaire q);
			
	public void updateQuestionnaireState(Integer id,char state);

}
