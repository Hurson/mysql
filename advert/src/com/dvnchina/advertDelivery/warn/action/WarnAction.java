package com.dvnchina.advertDelivery.warn.action;

import com.dvnchina.advertDelivery.warn.service.WarnService;

public class WarnAction {
	
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
