package com.dvnchina.advertDelivery.order.bean;

import java.util.Date;

/**
 * 请求式播出单实体
 * */
public class PlayListReq {

	/** 序号 */
	private Integer id;

	/** 订单编号 */
	private Integer orderId;

	/** 策略编号 */
	private Integer ployId;

	/** 开始日期 */
	private Date beginDate;

	/** 结束日期 */
	private Date endDate;

	/** 开始时间段 */
	private String begin;

	/** 结束时间段 */
	private String end;

	/** 播放次数 */
	private int playTime;

	/** 广告位编码 */
	private String adSiteCode;

	/** 特征值 
	 * 特征值的格式: 高标清(SD/HD),插播位置(0 1/3 2/3) 如 HD,1/3
	 */
	private String CharacteristicIdentification;

	/** 频道 */
	private String channels;

	/** 投放区域 */
	private String areas;

	/** 用户行业 */
	private String userIndustrys;

	/** 用户级别 */
	private String userLevels;

	/** tvn号段 */
	private String tvn;

	/** 状态 */
	private int state;

	/** 合同编号 */
	private Integer contractId;

	/** 用户总次数 */
	private Integer userNumber = 0;
	/** 问卷总次数 */
	private Integer questionnaireNumber = 0;
	/** 积分兑换人民币比率 */
	private String integralRatio ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getPloyId() {
		return ployId;
	}

	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	public String getAdSiteCode() {
		return adSiteCode;
	}

	public void setAdSiteCode(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}

	public String getCharacteristicIdentification() {
		return CharacteristicIdentification;
	}

	public void setCharacteristicIdentification(
			String characteristicIdentification) {
		CharacteristicIdentification = characteristicIdentification;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getUserIndustrys() {
		return userIndustrys;
	}

	public void setUserIndustrys(String userIndustrys) {
		this.userIndustrys = userIndustrys;
	}

	public String getUserLevels() {
		return userLevels;
	}

	public void setUserLevels(String userLevels) {
		this.userLevels = userLevels;
	}

	public String getTvn() {
		return tvn;
	}

	public void setTvn(String tvn) {
		this.tvn = tvn;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(Integer userNumber) {
		this.userNumber = userNumber;
	}

	public Integer getQuestionnaireNumber() {
		return questionnaireNumber;
	}

	public void setQuestionnaireNumber(Integer questionnaireNumber) {
		this.questionnaireNumber = questionnaireNumber;
	}

	public String getIntegralRatio() {
		return integralRatio;
	}

	public void setIntegralRatio(String integralRatio) {
		this.integralRatio = integralRatio;
	}

}
