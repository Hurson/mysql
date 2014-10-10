package com.avit.ads.pushads.boss.bean;

public class TvnScore {

	private String tvn;
	private Double score;
	private String month;
	
	public TvnScore() {
		super();
	}
	public TvnScore(String tvn, Double score, String month) {
		super();
		this.tvn = tvn;
		this.score = score;
		this.month = month;
	}
	public String getTvn() {
		return tvn;
	}
	public void setTvn(String tvn) {
		this.tvn = tvn;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
