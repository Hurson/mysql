package com.avit.ads.syncreport.bean;

/*
 * 作为一个document加入索引，
 * 必须的字段（相当于filed）: tvn、position_code（或position_flag)、contentid
 */
//TODO 很多字段是不必须的（性能考虑），可考虑精简
public class LuceneBean {
	private String tvn;
	private String position_code;
	private String position_flag;
	private String operator_time;
	private String service_id;
	private String order_id;
	private String contentid;
	private String area_name;  //区域名，如果要分区域统计，需赋值  modified by liuwenping
	private String playListId; //播出单ID
	private String hourStr;    //时段
	
	public LuceneBean(String tvn, String operator_time, String position_flag, String service_id, String area_name){
		this.tvn = tvn;
		this.operator_time = operator_time;
		this.position_flag = position_flag;
		this.service_id = service_id;
		this.area_name = area_name;
	}
	
	public LuceneBean() {
		super();
	}
	public String getTvn() {
		return tvn;
	}
	public void setTvn(String tvn) {
		this.tvn = tvn;
	}
	public String getPosition_code() {
		return position_code;
	}
	public void setPosition_code(String position_code) {
		this.position_code = position_code;
	}
	public String getPosition_flag() {
		return position_flag;
	}
	public void setPosition_flag(String position_flag) {
		this.position_flag = position_flag;
	}
	public String getOperator_time() {
		return operator_time;
	}
	public void setOperator_time(String operator_time) {
		this.operator_time = operator_time;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	

	public String getPlayListId() {
		return playListId;
	}

	public void setPlayListId(String playListId) {
		this.playListId = playListId;
	}
	

	public String getHourStr() {
		return hourStr;
	}

	public void setHourStr(String hourStr) {
		this.hourStr = hourStr;
	}

	@Override
	public String toString() {
		return "【 " + getTvn() + ", " + getArea_name() + ", " + getContentid() + ", " + getOperator_time()
			    + ", " + getOrder_id() + ", " + getPosition_code() + ", " + getPosition_flag() + ", " 
				+ getService_id() + ", " + getPlayListId() + "】";
	}
	
}
