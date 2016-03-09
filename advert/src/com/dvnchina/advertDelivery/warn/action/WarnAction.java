package com.dvnchina.advertDelivery.warn.action;

import com.dvnchina.advertDelivery.warn.service.WarnService;
import com.opensymphony.xwork2.ActionSupport;

public class WarnAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7905739456597978601L;

	private Integer id;
	
	private WarnService warnService;
	
	public void deleteInfo(){
		if(null != id){
			warnService.deleteWarnInfo(id);
		}
	}
	

	public WarnService getWarnService() {
		return warnService;
	}


	public void setWarnService(WarnService warnService) {
		this.warnService = warnService;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
