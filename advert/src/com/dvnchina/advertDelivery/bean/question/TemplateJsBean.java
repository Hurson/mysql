package com.dvnchina.advertDelivery.bean.question;

import java.util.List;

public class TemplateJsBean {
	private String summaryId;
	private String pictureId;
	private String videoId;
	private int questionNum;
	private List<QuestionForJsBean> question;

	public String getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(String summaryId) {
		this.summaryId = summaryId;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public List<QuestionForJsBean> getQuestion() {
		return question;
	}

	public void setQuestion(List<QuestionForJsBean> question) {
		this.question = question;
	}

	

}
