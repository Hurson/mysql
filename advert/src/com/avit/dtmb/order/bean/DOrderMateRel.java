package com.avit.dtmb.order.bean;
// default package

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.avit.dtmb.material.bean.DResource;

/**
 * DOrderMaterialRef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_order_mate_rel")
public class DOrderMateRel implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6902616209233440440L;
	private Integer id;
	private String orderCode;
	private DResource resource;
	private String startTime;
	private String endTime;
	private String areaCode;
	private String ployType;
	private String typeValue;
	private String valueName;
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

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_ID", referencedColumnName ="ID", insertable = false, updatable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	public DResource getResource() {
		return resource;
	}

	public void setResource(DResource resource) {
		this.resource = resource;
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
	
	@Column(name="PLOY_TYPE")
	public String getPloyType() {
		return ployType;
	}

	public void setPloyType(String ployType) {
		this.ployType = ployType;
	}
	@Column(name="TYPE_VALUE")
	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	@Column(name="VALUE_NAME")
	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	@Column(name = "INDEX_NUM")
	public Integer getIndexNum() {
		return this.indexNum;
	}
	
	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

}
