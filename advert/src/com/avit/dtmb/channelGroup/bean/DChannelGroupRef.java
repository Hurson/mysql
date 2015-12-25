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
 * AbstractDChannelGroupRef entity provides the base persistence definition of
 * the DChannelGroupRef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "d_channel_group_ref", catalog = "ads_x")
public  class DChannelGroupRef implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1252233680288482434L;
	private Long id;
	private Long groupId;
	private String serviceId;

	// Constructors

	/** default constructor */
	public DChannelGroupRef() {
	}

	/** full constructor */

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
	@Column(name = "GROUP_ID")
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	@Column(name = "SERVICE_ID")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	


}