package com.avit.ads.requestads.bean.request;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "UserSurveyAnswerList")
public class UserSurveyAnswerList {
	
	/** 调查问卷问题号*/
	@XmlElement(name="UserSurveyResult")
	private List<UserSurveyResult> userSurveyResult;

	public List<UserSurveyResult> getUserSurveyResult() {
		return userSurveyResult;
	}

	public void setUserSurveyResult(List<UserSurveyResult> userSurveyResult) {
		this.userSurveyResult = userSurveyResult;
	}
}
