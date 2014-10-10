package com.avit.ads.requestads.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.bean.cache.QuestionnaireReal;
import com.avit.ads.requestads.bean.cache.SurveyReport;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.dao.impl.ADSurveyDAOImp;
import com.avit.ads.util.ContextHolder;

public class QuestionnaireCache {

	ADSurveyDAO aDSurveyDAO = (ADSurveyDAOImp) ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
	public static QuestionnaireCache instance  = null;
	private static Logger logger = LoggerFactory.getLogger(QuestionnaireCache.class);
	private Map<String, List<String>> questionnaireCache;
	private List<QuestionnaireReal> lstQuestionnaireReal;
	
	public Map<String, List<String>> getQuestionnaireCache() {
		return questionnaireCache;
	}

	public List<QuestionnaireReal> getLstQuestionnaireReal() {
		return lstQuestionnaireReal;
	}
	
	private QuestionnaireCache(){
		this.GenerateQuestionnaireRecordCache();
		this.GenerateQuestionnaireCache();
	}
	
	/**
	 * 更新问卷缓存缓存数据
	 * @param cacheName
	 * @param cacheValue
	 */
	public void updateCache(SurveyReport cacheValue){
		if(questionnaireCache == null){
			questionnaireCache = new HashMap<String, List<String>>();
		}
		String tvnId = cacheValue.getTvnId();
		List<String> list = questionnaireCache.get(tvnId);
		list.add(cacheValue.getSurveyId());
	}
	
	/**
	 * 创建实例的方法，为了降低synchronized的消耗，使用双重加锁检查的方式
	 */
	public static QuestionnaireCache getInstance(){
		if(instance == null){
			synchronized(QuestionnaireCache.class){
				if(instance == null){
					instance = new QuestionnaireCache();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 创建问卷缓存
	 */
	private void GenerateQuestionnaireRecordCache(){
		try {
			List<SurveyReport> list = aDSurveyDAO.getQuestionnaireInfo();
			String key = "";
			List<String> listSurveyId = new ArrayList<String>();
			for (int i = 0, j = list.size(); i < j; i++) {
				SurveyReport surveyReport = list.get(i);
				// 将TVNID:问卷ID作为缓存的key，将整个对象作为缓存的value(一种类型的问卷只能填一次)
				String tvnId = surveyReport.getTvnId();
				String surveyId = surveyReport.getSurveyId();
				if(i > 0 && i != (j - 1) && key.equals(tvnId)){
					listSurveyId.add(surveyId);
					continue;
				}else if(i > 0 && !key.equals(tvnId)){
					questionnaireCache.put(key, listSurveyId);
					listSurveyId = new ArrayList<String>();
				}
				listSurveyId.add(surveyId);
				if(i == (j -1)){
					questionnaireCache.put(tvnId, listSurveyId);
				}
				key = tvnId;
			}
		} catch (Exception e) {
			logger.error("QuestionnaireCache.GenerateQuestionnaireRecordCache()-----------------封装问卷填写记录缓存时出错");
			logger.error(e.getMessage());
		}
		
	}
	
	private void GenerateQuestionnaireCache(){
		try {
			lstQuestionnaireReal = aDSurveyDAO.getQuestionnaireReal();
		} catch (Exception e) {
			logger.error("QuestionnaireCache.GenerateQuestionnaireCache()-----------------封装在运行问卷缓存时出错");
			logger.error(e.getMessage());
		}
		
	}
}
