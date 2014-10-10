package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;

public interface TemplateDao extends BaseDao{
	
	public QuestionnaireTemplate getTemplateByName(String name, Character state);

	public List<QuestionnaireTemplate> listTemplatesByPage(int begin, int pageSize);
	
	public int getTemplatesCount();
}
