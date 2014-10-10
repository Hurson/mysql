package com.avit.ads.requestads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.requestads.bean.TempObjectStorgePlaylistContent;
import com.avit.ads.requestads.dao.ADSurveyDAO;
import com.avit.ads.requestads.dao.impl.ADSurveyDAOImp;
import com.avit.ads.util.ContextHolder;

public class MySaveReportThread implements Runnable{

	ADSurveyDAO aDSurveyDAO = (ADSurveyDAOImp) ContextHolder.getApplicationContext().getBean("ADSurveyDAOImp");
	private static Logger logger = LoggerFactory.getLogger(MySaveReportThread.class);
	/**
	 * 需要传入的参数
	 */
	private List<TempObjectStorgePlaylistContent> lsttmp;
	
	public void run() {
		try {
			SavePlaylistIntoHistoryReport(lsttmp);
		} catch (Exception e) {
			logger.error("保存gateway请求默认投放信息时失败");
		}
		
	}

	/**
	 * 将加工得到的播出单保存入广告投放记录表
	 * 
	 * @param obj 播出单列表
	 * 
	 */
	private void SavePlaylistIntoHistoryReport(List<TempObjectStorgePlaylistContent> lsttmp) {
		aDSurveyDAO.SavePlaylistIntoHistoryReport(lsttmp);
	}

	public List<TempObjectStorgePlaylistContent> getLsttmp() {
		return lsttmp;
	}

	public void setLsttmp(List<TempObjectStorgePlaylistContent> lsttmp) {
		this.lsttmp = lsttmp;
	}
	
	
	
	

}
