package com.avit.ads.requestads.bean.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "UserSurveyList")
public class AdContent {

	/** 用户标识，与请求的tvnId保持一致 */
	@XmlElement(name = "tvnId")
	private String tvnId;

	/** 会话标识，与请求token保持一致 */
	@XmlElement(name = "token")
	private String token;

	/** 调查问卷URL */
	@XmlElement(name = "surveyURL")
	private String surveyURL;

	/** 字幕的内容 */
	@XmlElement(name = "subTitle")
	private String subTitle;

	/** 挂角广告 */
	@XmlElement(name = "rightTopPic")
	private String rightTopPic;

	/** 暂停广告 */
	@XmlElement(name = "pausePic")
	private String pausePic;

	/** 插播广告 */
	@XmlElement(name = "InsertedAdList")
	private InsertedAdList insertedAdList;

	/** 用户行业类别，用于返回给gateway，发送给浏览器进行请求问卷参数填充 */
	@XmlElement(name = "userType2")
	private String userTrade;

	/** 用户类别，用于返回给gateway，发送给浏览器进行请求问卷参数填充 */
	@XmlElement(name = "userType")
	private String userSort;

	/** 区域编码，用于返回给gateway，发送给浏览器进行请求问卷参数填充 */
	@XmlElement(name = "locationId")
	private String locationCode;

	public String getTvnId() {
		return tvnId;
	}

	public void setTvnId(String tvnId) {
		this.tvnId = tvnId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSurveyURL() {
		return surveyURL;
	}

	public void setSurveyURL(String surveyURL) {
		this.surveyURL = surveyURL;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getRightTopPic() {
		return rightTopPic;
	}

	public void setRightTopPic(String rightTopPic) {
		this.rightTopPic = rightTopPic;
	}

	public String getPausePic() {
		return pausePic;
	}

	public void setPausePic(String pausePic) {
		this.pausePic = pausePic;
	}

	public InsertedAdList getInsertedAdList() {
		return insertedAdList;
	}

	public void setInsertedAdList(InsertedAdList insertedAdList) {
		this.insertedAdList = insertedAdList;
	}

	public String getUserTrade() {
		return userTrade;
	}

	public void setUserTrade(String userTrade) {
		this.userTrade = userTrade;
	}

	public String getUserSort() {
		return userSort;
	}

	public void setUserSort(String userSort) {
		this.userSort = userSort;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

}
