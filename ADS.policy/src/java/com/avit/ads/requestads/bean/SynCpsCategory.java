package com.avit.ads.requestads.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "T_SYN_CPS_CATEGORY")
public class SynCpsCategory {
	
	public SynCpsCategory() {
	}
	
	@Id
	@Column(name = "ID")
	private long id;
	
	@Column(name = "RESOURCE_ID")
	private String resourceId;
	
	@Column(name = "PARENT_ID")
	private String parentId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
}
