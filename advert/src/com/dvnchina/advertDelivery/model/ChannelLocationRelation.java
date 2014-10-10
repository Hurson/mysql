package com.dvnchina.advertDelivery.model;

import java.io.Serializable;

/**
 * 频道区域关系
 * 
 * @author lester
 * 
 */
public class ChannelLocationRelation implements Serializable {

	private static final long serialVersionUID = 8216195401219487594L;

	private String servicID;
	
	private String networkID;
	
	private String txID;

	public String getServicID() {
		return servicID;
	}

	public void setServicID(String servicID) {
		this.servicID = servicID;
	}

	public String getNetworkID() {
		return networkID;
	}

	public void setNetworkID(String networkID) {
		this.networkID = networkID;
	}

	public String getTxID() {
		return txID;
	}

	public void setTxID(String txID) {
		this.txID = txID;
	}
}
