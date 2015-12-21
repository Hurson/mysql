package com.avit.dtmb.channelGroup.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractDChannelGroup entity provides the base persistence definition of the
 * DChannelGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_channel_group", catalog = "ads_x")
public class DChannelGroup implements java.io.Serializable {

	// Fields

	private Long id;
	private String code;
	private String name;
	private String channelDesc;
	private Integer operatorId;
	private String type;
	

	// Constructors

	/** default constructor */
	public DChannelGroup() {
	}

	/** minimal constructor */
	public DChannelGroup(String name) {
		this.name = name;
	}

	/** full constructor */
	public DChannelGroup(String code, String name, String channelDesc,
			Integer operatorId, String type) {
		this.code = code;
		this.name = name;
		this.channelDesc = channelDesc;
		this.operatorId = operatorId;
		this.type = type;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CODE", length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CHANNEL_DESC", length = 254)
	public String getChannelDesc() {
		return this.channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	@Column(name = "OPERATOR_ID", precision = 10, scale = 0)
	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	} 

	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}