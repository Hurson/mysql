package com.avit.ads.requestads.bean.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AdInsertResponseBean")
public class AdInsertResponseBean {
	
	/** 调查问卷查询响应代码，0 ：成功，有问卷；1 ：无问卷；其他待定*/
	@XmlElement(name="surveyQueryRespCode")
	private int surveyQueryRespCode;
	
	/** 如果返回码为0，该部分包含调查问卷的内容*/
	@XmlElement(name="UserSurveyList")
	private AdContent userSurveyList;

	public int getSurveyQueryRespCode() {
		return surveyQueryRespCode;
	}

	public void setSurveyQueryRespCode(int surveyQueryRespCode) {
		this.surveyQueryRespCode = surveyQueryRespCode;
	}

	public AdContent getUserSurveyList() {
		return userSurveyList;
	}

	public void setUserSurveyList(AdContent userSurveyList) {
		this.userSurveyList = userSurveyList;
	}
	
}
