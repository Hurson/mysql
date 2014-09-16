package com.avit.ads.requestads.bean.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "UserSurveyResult")
public class UserSurveyResult {
	
	/** 调查问卷问题号*/
	@XmlElement(name="surveyQuestionId")
	private String surveyQuestionId;
	
	/** 调查问卷用户响应*/
	@XmlElement(name="surveyResult")
	private String surveyResult;

	public String getSurveyQuestionId() {
		return surveyQuestionId;
	}

	public void setSurveyQuestionId(String surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}

	public String getSurveyResult() {
		return surveyResult;
	}

	public void setSurveyResult(String surveyResult) {
		this.surveyResult = surveyResult;
	}

}
