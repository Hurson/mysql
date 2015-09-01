package com.dvnchina.advertDelivery.order.bean;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

/**
 * 投放式播出单实体
 * */
public class PlayListGis implements RowMapper<PlayListGis> {

	/** 序号 */
	private Integer id;

	/** 策略编号 */
	private Integer ployId;

	/** 开始时间 */
	private Date startTime;

	/** 结束时间 */
	private Date endTime;

	/** 素材路径 */
	private String contentPath;

	/** 素材类型 */
	private String contentType;

	/** 广告位编码 */
	private String adSiteCode;

	/**
	 * 特征值的格式: 高标清(SD/HD)
	 */
	private String characteristicIdentification;

	/** 频道SERVICE_ID */
	private String serviceId;

	/** 区域信息 */
	private String areas;

	/** 用户行业 */
	private String userIndustrys;

	/** 用户级别 */
	private String userLevels;

	/** tvn号段 */
	private String tvn;

	/** 状态 */
	private Integer state;

	/** 合同编号 */
	private Integer contractId;

	/** 订单编号 */
	private Integer orderId;

	/** 内容id */
	private String contentId;
	
	/** 回看栏目IDS */
	private String categoryId;
	
	/** 点播随片 */
	private String assetId;
	
	/** 点播随片*/
	private Integer priority;
	/**投放类型NVOD主界面广告*/
	private String menuTypeCode;

	public Integer getPriority() {
		if(null == priority){
			return 0;
		}
		return priority;
	}


	public void setPriority(Integer priority) {
		this.priority = priority;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getPloyId() {
		return ployId;
	}


	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getContentPath() {
		return contentPath;
	}


	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getAdSiteCode() {
		return adSiteCode;
	}


	public void setAdSiteCode(String adSiteCode) {
		this.adSiteCode = adSiteCode;
	}


	public String getCharacteristicIdentification() {
		return characteristicIdentification;
	}


	public void setCharacteristicIdentification(String characteristicIdentification) {
		this.characteristicIdentification = characteristicIdentification;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public Integer getContractId() {
		return contractId;
	}


	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}


	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public String getContentId() {
		return contentId;
	}


	public void setContentId(String contentId) {
		this.contentId = contentId;
	}


	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public String getAssetId() {
		return assetId;
	}


	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	

	public String getMenuTypeCode() {
		return menuTypeCode;
	}


	public void setMenuTypeCode(String menuTypeCode) {
		this.menuTypeCode = menuTypeCode;
	}


	@Override
	public PlayListGis mapRow(ResultSet rs, int num) throws SQLException {
		PlayListGis pl = new PlayListGis();
		pl.setId(rs.getInt("id"));

		pl.setPloyId(rs.getInt("ploy_id"));
		pl.setStartTime(new Date(rs.getTimestamp("start_time").getTime()));
		pl.setEndTime(new Date(rs.getTimestamp("end_time").getTime()));
		pl.setContentPath(rs.getString("content_path"));
		pl.setContentType(rs.getString("content_type"));
		pl.setAdSiteCode(rs.getString("ad_site_code"));
		pl.setCharacteristicIdentification(rs
				.getString("characteristic_identification"));
		Clob serviceId = rs.getClob("service_id");
		Clob areas = rs.getClob("areas");
		pl.setServiceId(serviceId.getSubString((long) 1, (int) serviceId.length()));
		pl.setAreas(areas.getSubString((long) 1, (int) areas.length()));
		pl.setUserIndustrys(rs.getString("userindustrys"));
		pl.setUserLevels(rs.getString("userLevels"));
		pl.setTvn(rs.getString("tvn"));
		pl.setState(rs.getInt("state"));
		pl.setContractId(rs.getInt("contract_id"));
		pl.setOrderId(rs.getInt("order_id"));
		pl.setContentId(rs.getString("content_id"));
		Clob categoryId = rs.getClob("category_id");
		Clob assetId = rs.getClob("asset_id");
		pl.setCategoryId(categoryId.getSubString((long) 1, (int) categoryId.length()));
		pl.setAssetId(assetId.getSubString((long) 1, (int) assetId.length()));
		return pl;
	}
}
