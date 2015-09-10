package com.avit.ads.pushads.task.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "n_menu_type")
public class NvodMenuType {
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="TYPE_CODE")
	private String typeCode;
	
	@Column(name="TYPE_NAME")
	private String typeName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	

}
