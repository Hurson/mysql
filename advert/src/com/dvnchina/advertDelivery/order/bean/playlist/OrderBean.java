package com.dvnchina.advertDelivery.order.bean.playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.dvnchina.advertDelivery.position.bean.AdvertPosition;

public class OrderBean implements RowMapper<OrderBean> {
	private Integer id;
	private String orderCode;
	private Integer ployId;
	private Date startDate;
	private Date endDate;
	private Integer contractId;
	private Integer positionId;
	private Integer orderType;
	private Integer positionPackageId;
	private String positionPackageCode;
	private Integer positionPackageType;
	private AdvertPosition advertPosition;
	private List<PloyBean> ployList;
	private String areas;
	private String serviceIds;
	private String categoryIds;
	private String assetIds;
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


	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getPloyId() {
		return ployId;
	}

	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getPositionPackageId() {
		return positionPackageId;
	}

	public void setPositionPackageId(Integer positionPackageId) {
		this.positionPackageId = positionPackageId;
	}

	public String getPositionPackageCode() {
		return positionPackageCode;
	}

	public void setPositionPackageCode(String positionPackageCode) {
		this.positionPackageCode = positionPackageCode;
	}

	public List<PloyBean> getPloyList() {
		return ployList;
	}

	public void setPloyList(List<PloyBean> ployList) {
		this.ployList = ployList;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public Integer getPositionPackageType() {
		return positionPackageType;
	}

	public void setPositionPackageType(Integer positionPackageType) {
		this.positionPackageType = positionPackageType;
	}

	public AdvertPosition getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(AdvertPosition advertPosition) {
		this.advertPosition = advertPosition;
	}

	public String getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getAssetIds() {
		return assetIds;
	}

	public void setAssetIds(String assetIds) {
		this.assetIds = assetIds;
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

	@Override
	public OrderBean mapRow(ResultSet rs, int num) throws SQLException {
		OrderBean order = new OrderBean();
		order.setId(rs.getInt("id"));
		order.setOrderCode(rs.getString("order_code"));
		order.setPloyId(rs.getInt("ploy_id"));
		order.setStartDate(rs.getDate("start_time"));
		order.setEndDate(rs.getDate("end_time"));
		order.setContractId(rs.getInt("contract_id"));
		order.setPositionId(rs.getInt("position_id"));
		order.setOrderType(rs.getInt("order_type"));
		order.setPositionPackageId(rs.getInt("position_package_id"));
		order.setPositionPackageCode(rs.getString("position_package_code"));
		order.setPositionPackageType(rs.getInt("position_package_type"));
		order.setUserNumber(rs.getInt("user_number"));
		order.setQuestionnaireNumber(rs.getInt("questionnaire_number"));
		order.setIntegralRatio(rs.getString("integral_ratio"));
		
		AdvertPosition ad = new AdvertPosition();
		ad.setPositionCode(rs.getString("position_code"));
		ad.setIsHD(rs.getInt("is_hd"));
		ad.setIsLoop(rs.getInt("is_loop"));
		ad.setTextRuleId(rs.getInt("text_rule_id"));
		ad.setIsChannel(rs.getInt("is_channel"));
		ad.setIsFreq(rs.getInt("is_freq"));
		ad.setIsPlayback(rs.getInt("is_playback"));
		ad.setIsColumn(rs.getInt("is_column"));
		ad.setIsLookbackProduct(rs.getInt("is_lookback_product"));
		ad.setIsAsset(rs.getInt("is_asset"));
		ad.setIsFollowAsset(rs.getInt("is_follow_asset"));
		order.setAdvertPosition(ad);

//		PloyBean ploy = new PloyBean();
//		ploy.setPloyId(rs.getInt("ploy_id"));
//		ploy.setStartTime(rs.getString("pStart"));
//		ploy.setEndTime(rs.getString("pEnd"));
//		ploy.setUserIndustrys(rs.getString("userindustrys"));
//		ploy.setUserLevels(rs.getString("userlevels"));
//		ploy.setPloyNumber(rs.getInt("ploy_number"));
//		ploy.setTvnNumber(rs.getString("tvn_number"));
//		ploy.setAreaId(rs.getInt("area_id"));
//
//		order.setPloy(ploy);
		return order;
	}

}
