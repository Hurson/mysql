package com.avit.ads.requestads.bean.cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_PRODUCTINFO")
public class ProductionCache {
	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "PRODUCT_ID")
	private String productId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	public ProductionCache(String productId){
		this.productId = productId;
	}
	
	public ProductionCache(){
		
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
