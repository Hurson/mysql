package com.dvnchina.advertDelivery.order.bean;

/**
 * 广告订单与素材关系实体
 * */
public class OrderMaterialRelation {
	
	/** 主键 */
	private Integer id;
	
	/** 订单ID */
	private Integer orderId;
	
	/** 绑定资产ID */
	private Integer mateId;
	/** 绑定资产名称 */
	private String mateName;
	
	/** 插播位置 */
	private String playLocation;
	
	/** 是否高清 */
	private Integer isHD;
	
	/** 轮询索引 */
	private Integer pollIndex;
	
	/** 精准主键 */
	private Integer preciseId;
	
	/** 精准/分策略类型：0表示precise_id对应精准表ID，1表示precise_id对应策略表ID */
	private Integer type;
	
	
	/** 开始时间段*/
	private String startTime;
	
	/** 结束时间段 */
	private String endTime;
	
	/** 区域编码 */
	private String areaCode;
	
	/** 频道组ID */
	private Integer channelGroupId;
	
	private String menuTypeCode;
	
	public OrderMaterialRelation(){
		
	}

	public OrderMaterialRelation(Integer id, Integer orderId, Integer mateId,
			String mateName, String playLocation, Integer isHD,
			Integer pollIndex, Integer preciseId,Integer type,String startTime,String endTime,String areaCode,Integer channelGroupId) {
		this.id = id;
		this.orderId = orderId;
		this.mateId = mateId;
		this.mateName = mateName;
		this.playLocation = playLocation;
		this.isHD = isHD;
		this.pollIndex = pollIndex;
		this.preciseId = preciseId;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
		this.areaCode = areaCode;
		this.channelGroupId = channelGroupId;
		
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getChannelGroupId() {
		return channelGroupId;
	}

	public void setChannelGroupId(Integer channelGroupId) {
		this.channelGroupId = channelGroupId;
	}

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

	public String getPlayLocation() {
		return playLocation;
	}

	public Integer getMateId() {
		return mateId;
	}

	public void setMateId(Integer mateId) {
		this.mateId = mateId;
	}

	public String getMateName() {
		return mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
	}

	public void setPlayLocation(String playLocation) {
		this.playLocation = playLocation;
	}

	public Integer getIsHD() {
		return isHD;
	}

	public void setIsHD(Integer isHD) {
		this.isHD = isHD;
	}

	public Integer getPollIndex() {
		return pollIndex;
	}

	public void setPollIndex(Integer pollIndex) {
		this.pollIndex = pollIndex;
	}

	public Integer getPreciseId() {
		return preciseId;
	}

	public void setPreciseId(Integer preciseId) {
		this.preciseId = preciseId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMenuTypeCode() {
		return menuTypeCode;
	}

	public void setMenuTypeCode(String menuTypeCode) {
		this.menuTypeCode = menuTypeCode;
	}
	

}
