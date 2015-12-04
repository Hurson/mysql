package com.avit.dtmb.order.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DOrderMaterialRef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_order_material_ref", catalog = "ads_x")
public class DOrderMaterialRef implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6902616209233440440L;
	private Integer id;
	private String orderCode;
	private Integer resourceId;
	private String startTime;
	private String endTime;
	private String areaCode;
	private Integer ployType;
	private String typeValue;
	private Integer indexNum;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ORDER_CODE")
	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "RESOURCE_ID")
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "START_TIME", length = 8)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 8)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "AREA_CODE", length = 20)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "PLOY_TYPE")
	public Integer getPloyType() {
		return this.ployType;
	}

	public void setPloyType(Integer ployType) {
		this.ployType = ployType;
	}

	@Column(name = "TYPE_VALUE")
	public String getTypeValue() {
		return this.typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	@Column(name = "INDEX_NUM")
	public Integer getIndexNum() {
		return this.indexNum;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

}
