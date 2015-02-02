package com.dvnchina.advertDelivery.contract.bean;

import java.io.Serializable;

public class ContractBackUpArea implements Serializable{
	
	private static final long serialVersionUID = -5779241002800260122L;

	private Integer id;
	
	private Integer contractId;
	
	private String areaCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
}
