package com.avit.ads.requestads.bean;

import java.util.List;
import java.util.Map;

public class PlaylistCacheModel {

	/** 播出单ID*/
	private long id;
	
	/** 开始时间，使用long类型为了方便计算*/
	private long begin;
	
	/** 结束时间，使用long类型为了方便计算*/
	private long end;
	
	/** 封装内容的Map  <"id:区域:频道", "内容存放的数据结构，未定">*/
	private Map<String, String> map;
	
	/** 特征值，锚定位置和时间*/
	private String anthoring;
	
	/** 用户级别*/
	private String userLevel;
	
	/** 用户行业级别*/
	private String trade;
	
	/** 合同号*/
	private long contractId;
	
	/** 广告位编码*/
	private String adSiteCode;
	
	/** 播出单中存放的内容*/
	private List<Content> lstContents;
	
	/** 精准的集合*/
	private List<PrecisionCacheModel> lstPrecision;
	
	public List<PrecisionCacheModel> getLstPrecision() {
		return lstPrecision;
	}
	public void setLstPrecision(List<PrecisionCacheModel> lstPrecision) {
		this.lstPrecision = lstPrecision;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getBegin() {
		return begin;
	}
	public void setBegin(long begin) {
		this.begin = begin;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public List<Content> getLstContents() {
		return lstContents;
	}
	public void setLstContents(List<Content> lstContents) {
		this.lstContents = lstContents;
	}
	
	public String getAnthoring() {
		return anthoring;
	}
	public void setAnthoring(String anthoring) {
		this.anthoring = anthoring;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	public String getAdSiteCode() {
		return adSiteCode;
	}
	public void setAdSiteCode(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}
}
