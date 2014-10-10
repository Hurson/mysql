package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.dao.TemplateDao;
import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;

public class TemplateDaoImpl extends BaseDaoImpl implements TemplateDao{

	@Override
	public QuestionnaireTemplate getTemplateByName(String name,Character state) {
		String hql = "from QuestionnaireTemplate t where t.templatePackageName=? and t.state="+state;
		return (QuestionnaireTemplate) get(hql,name);
	}
	
	@Override
	public List<QuestionnaireTemplate> listTemplatesByPage(int begin,int pageSize){
		String hql = "from QuestionnaireTemplate t where t.state="+Constant.AVAILABLE;
		return list(hql, begin, pageSize);
	}

	@Override
	public int getTemplatesCount() {
		String hql = "from QuestionnaireTemplate t where t.state="+Constant.AVAILABLE;
		return getCount(hql);
	}
}
