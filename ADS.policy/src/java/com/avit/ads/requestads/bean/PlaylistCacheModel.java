package com.avit.ads.requestads.bean;

import java.util.List;
import java.util.Map;

public class PlaylistCacheModel {

	/** 播出单ID */
	private int id;
	
	/** 订单ID*/
	private int orderId;

	/** 开始时间，使用long类型为了方便计算 */
	private long begin;

	/** 结束时间，使用long类型为了方便计算 */
	private long end;

	/** 封装内容的Map <"id:区域:频道", "内容存放的数据结构，未定"> */
	private Map<String, String> map;

	/** 特征值，锚定位置和时间 */
	private String anthoring;

	/** 用户级别 */
	private String userLevel;
	
	private int playTimes;

	/** 用户行业级别 */
	private String trade;

	/** 合同号 */
	private long contractId;

	/** 广告位编码 */
	private String adSiteCode;

	/** 播出单中存放的内容 */
	private AdPlaylistReqContent content;

	/** 精准的集合 */
	private List<PrecisionCacheModel> lstPrecision;

	public List<PrecisionCacheModel> getLstPrecision() {
		return lstPrecision;
	}

	public void setLstPrecision(List<PrecisionCacheModel> lstPrecision) {
		this.lstPrecision = lstPrecision;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public AdPlaylistReqContent getContent() {
		return content;
	}

	public void setContent(AdPlaylistReqContent content) {
		this.content = content;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
	}
	
}
