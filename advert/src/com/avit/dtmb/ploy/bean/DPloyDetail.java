package com.avit.dtmb.ploy.bean;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DPloyDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_ploy_detail")
public class DPloyDetail implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4386797421050075935L;
	
	private Integer id;
	private Integer ployId;
	private String ployType;
	private String typeValue;
	private Integer priority;
	private String valueName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PLOY_ID")
	public Integer getPloyId() {
		return this.ployId;
	}

	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}

	@Column(name = "PLOY_TYPE", length = 2)
	public String getPloyType() {
		return this.ployType;
	}

	public void setPloyType(String ployType) {
		this.ployType = ployType;
	}

	@Column(name = "TYPE_VALUE")
	public String getTypeValue() {
		return this.typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	
	@Column(name = "PRIORITY")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	@Column(name = "VALUE_NAME")
	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

}
