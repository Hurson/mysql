package com.dvnchina.advertDelivery.order.bean;

/**
 * 广告订单与素材临时关系实体
 * */
public class OrderMaterialRelationTmp {
	
	/** 主键 */
	private Integer id;
	
	/** 订单ID */
	private Integer orderId;
	/** 订单编码 */
	private String orderCode;
	
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
	/** 类型\r\ntype=1按回看产品；\r\ntype=2按影片元数据关键字；\r\ntype=3 按受众；
	 //ntype=4按影片分类-CPS栏目；\r\ntype=5 按回放频道；\r\ntype=6 按回看栏目；\r\ntype=7 按频道；\r\ntype=8 按影片模糊名称；
	 *///\r\ntype=9 按区域；\r\ntype=10 按频道分组；\r\ntype=11 按用户区域；\r\ntype=12 按行业；\r\ntype=13 按级别；', */
	private Integer preciseType;
	
	/** 开始时间段*/
	private String startTime;
	
	/** 结束时间段 */
	private String endTime;
	
	/** 区域编码 */
	private String areaCode;
	
	private String areaName;
	
	/** 频道组ID */
	private Integer channelGroupId;
	
	private String channelGroupName;
	
	private String contain;
	
	private boolean isNotNull;
	
	private String menuTypeCode;
	
	private String menuTypeName;
	
	public OrderMaterialRelationTmp(){
		
	}

	public OrderMaterialRelationTmp(Integer id, String orderCode, Integer mateId,
			String mateName, String playLocation, Integer isHD,
			Integer pollIndex, Integer preciseId,Integer type,String startTime,String endTime,String areaCode,Integer channelGroupId,Integer preciseType) {
		this.id = id;
		this.orderCode = orderCode;
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
		this.preciseType=preciseType;
	}

	public Integer getPreciseType() {
		return preciseType;
	}

	public void setPreciseType(Integer preciseType) {
		this.preciseType = preciseType;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public String getPlayLocation() {
		return playLocation;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getChannelGroupId() {
		return channelGroupId;
	}

	public void setChannelGroupId(Integer channelGroupId) {
		this.channelGroupId = channelGroupId;
	}

	public String getChannelGroupName() {
		return channelGroupName;
	}

	public void setChannelGroupName(String channelGroupName) {
		this.channelGroupName = channelGroupName;
	}

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public String getMenuTypeCode() {
		return menuTypeCode;
	}

	public void setMenuTypeCode(String menuTypeCode) {
		this.menuTypeCode = menuTypeCode;
	}

	public String getMenuTypeName() {
		return menuTypeName;
	}

	public void setMenuTypeName(String menuTypeName) {
		this.menuTypeName = menuTypeName;
	}

	

}
