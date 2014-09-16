package com.avit.ads.pushads.unt.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//@Entity
//@Table(name = "resource_url")
//@SequenceGenerator(name = "resource_seq", sequenceName= "resource_url_SEQ", allocationSize = 25)
public class ResourceUrl implements Serializable{

	//@Id
	//@Column(name = "ID", unique = true, nullable = false, precision = 38, scale = 0)
	private Long id;
	//@Column(name = "TYPE")
	private Long type;
	//@Column(name = "URL")
	private String url;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
