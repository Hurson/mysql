package com.dvnchina.advertDelivery.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dvnchina.advertDelivery.dao.QuestionnaireSpecificationDao;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.QuestionnaireSpecification;
import com.dvnchina.advertDelivery.service.QuestionnaireSpecificationService;

public class QuestionnaireSpecificationServiceImpl implements
		QuestionnaireSpecificationService {
	
	private QuestionnaireSpecificationDao questionnaireSpecificationDao;
	
	@Override
	public List<QuestionnaireSpecification> page(Map condition, int start, int end) {
		StringBuffer queryQuestionMaterialSpeci = new StringBuffer();
		queryQuestionMaterialSpeci.append("SELECT * FROM T_QUESTIONNAIRE_SPECIFICATION");
		
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryQuestionMaterialSpeci.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryQuestionMaterialSpeci.append(columnName).append(" like ").append("'%").append(entry.getValue()).append("%'");
				count++;
				if(count<mapSize){
					queryQuestionMaterialSpeci.append(" AND ");
				}
			}
		}
		
		return questionnaireSpecificationDao.page(queryQuestionMaterialSpeci.toString(), start, end);
	}

	@Override
	public int queryCount(Map condition) {
		
		StringBuffer queryQuestionMaterialSpeci = new StringBuffer();
		queryQuestionMaterialSpeci.append("SELECT COUNT(*) FROM T_QUESTIONNAIRE_SPECIFICATION");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryQuestionMaterialSpeci.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryQuestionMaterialSpeci.append(columnName).append(" like ").append("'%").append(entry.getValue()).append("%'");
				count++;
				if(count<mapSize){
					queryQuestionMaterialSpeci.append(" AND ");
				}
			}
		}
		return questionnaireSpecificationDao.queryTotalCount(queryQuestionMaterialSpeci.toString());
	}

	@Override
	public Map<String,String> save(
			QuestionnaireSpecification object) {
		boolean flag = false;
		String message = "";
		String method = "";
		Map resultMap = new HashMap();
		String result = "";
		String type = "question";
		Integer questionMaterialSpeciId = null;
		resultMap.put("dataType",type);
		int currentSequence;
		int num;
		if (object!=null&&object.getId()==null){
			//增加
			try {
				method = "save";
				resultMap.put("method",method);
				currentSequence=questionnaireSpecificationDao.getCurrentSequence();
				object.setId(currentSequence);
				num = questionnaireSpecificationDao.save(object);
				if (num>0) {
					message = "success";
				} else {
					message = "failure";
				}
				resultMap.put("flag",message);
			} catch (Exception e) {
				message = "error";
				resultMap.put("flag",message);
			}
		}else if(object!=null&&object.getId()!=null){
			//更新
			try {
				method = "update";
				resultMap.put("method",method);
				num = questionnaireSpecificationDao.update(object);
				if (num>0) {
					message = "success";
				} else {
					message = "failure";
				}
				resultMap.put("flag",message);
			} catch (Exception e) {
				message = "error";
				resultMap.put("flag",message);
			}
		}
		resultMap.put("speciObject",object);
		return resultMap;
	}

	
	@Override
	public boolean remove(int objectId) {
		boolean flag = false;
		int result = questionnaireSpecificationDao.remove(objectId);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean update(QuestionnaireSpecification object) {
		boolean flag = false;
		int result = questionnaireSpecificationDao.update(object);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public Integer getQuestionMaterialSpeciIdByCondition(
			QuestionnaireSpecification questionMaterialSpeci) {
		StringBuffer querySql = new StringBuffer();
		querySql.append("SELECT * FROM T_QUESTIONNAIRE_SPECIFICATION WHERE ");
		querySql.append("TYPE='");
		querySql.append(questionMaterialSpeci.getType()).append("'");
		querySql.append(" AND ");
		querySql.append("FILE_SIZE='");
		querySql.append(questionMaterialSpeci.getFileSize()).append("'");
		querySql.append(" AND ");
		querySql.append("OPTION_NUMBER=");
		querySql.append(questionMaterialSpeci.getOptionNumber());
		querySql.append(" AND ");
		querySql.append("MAX_LENGTH=");
		querySql.append(questionMaterialSpeci.getMaxLength());
		querySql.append(" AND ");
		querySql.append("EXCLUDE_CONTENT='");
		querySql.append(questionMaterialSpeci.getExcludeContent()).append("'");
		querySql.append(" ORDER BY ID DESC");
		System.out.println(querySql.toString());
		List<QuestionnaireSpecification> questionMaterialSpeciList = questionnaireSpecificationDao.query(querySql.toString());
		return (questionMaterialSpeciList!=null)?questionMaterialSpeciList.get(0).getId():null;
	}

	public QuestionnaireSpecificationDao getQuestionnaireSpecificationDao() {
		return questionnaireSpecificationDao;
	}

	public void setQuestionnaireSpecificationDao(
			QuestionnaireSpecificationDao questionnaireSpecificationDao) {
		this.questionnaireSpecificationDao = questionnaireSpecificationDao;
	}

	@Override
	public QuestionnaireSpecification get(Integer id) {
		return questionnaireSpecificationDao.get(id);
	}

}
