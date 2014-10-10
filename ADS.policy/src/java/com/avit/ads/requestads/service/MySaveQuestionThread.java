package com.avit.ads.requestads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.bean.Question;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.dao.impl.ADSurveyDAOImp;
import com.avit.ads.util.ContextHolder;

public class MySaveQuestionThread implements Runnable{

	ADSurveyDAO aDSurveyDAO = (ADSurveyDAOImp) ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
	private static Logger logger = LoggerFactory.getLogger(MySaveReportThread.class);
	
	// 需要传入线程的参数
	private List<Question> listQ;
	
	

	public List<Question> getListQ() {
		return listQ;
	}

	public void setListQ(List<Question> listQ) {
		this.listQ = listQ;
	}

	public void run() {
		try {
			this.saveQuestionnaireResult(listQ);
		} catch (Exception e) {
			logger.error("保存问卷提交请求信息时失败");
		}
		
	}

	/**
	 * 保存问卷内容
	 * @param listQ2
	 */
	private void saveQuestionnaireResult(List<Question> listQ2) {
		// TODO Auto-generated method stub
		
	}
}
