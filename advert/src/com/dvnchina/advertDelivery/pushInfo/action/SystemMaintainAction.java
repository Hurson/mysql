package com.dvnchina.advertDelivery.pushInfo.action;

import java.util.List;

import com.avit.ads.webservice.StandbyServerWs;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.pushInfo.bean.SystemMaintain;
import com.dvnchina.advertDelivery.pushInfo.service.SystemMaintainService;



public class SystemMaintainAction extends BaseAction {
	public final static String NO_STANDBY = "8";//不待机
	private static final long serialVersionUID = 1L;
	private SystemMaintainService maintainService;
	private StandbyServerWs standbyServiceClient;
	private SystemMaintain maintainList;
	private SystemMaintain maintain;
	private String message;
	private List<ReleaseArea> areaList;

	public String getAllMaintain(){
		message=null;
		maintainList=maintainService.getAllMaintain();
		areaList = maintainService.getUserRelaArea(this.getUserId());
		return SUCCESS;
	}
	public String saveMaintain(){
		try {
			//待机数据保存
			maintainService.saveOrUpdate(maintain);
			standbyServiceClient.sendStandByToUnt();
			message="common.save.success";
		} catch (Exception e) {
			message=e.getMessage();
		}finally{
			maintainList=maintainService.getAllMaintain();
			areaList = maintainService.getUserRelaArea(this.getUserId());
		}
		return SUCCESS;
	}
	
	public SystemMaintainService getMaintainService() {
		return maintainService;
	}

	public void setMaintainService(SystemMaintainService maintainService) {
		this.maintainService = maintainService;
	}

	public SystemMaintain getMaintainList() {
		return maintainList;
	}
	public void setMaintainList(SystemMaintain maintainList) {
		this.maintainList = maintainList;
	}
	public SystemMaintain getMaintain() {
		return maintain;
	}
	public void setMaintain(SystemMaintain maintain) {
		this.maintain = maintain;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public StandbyServerWs getStandbyServiceClient() {
		return standbyServiceClient;
	}
	public void setStandbyServiceClient(StandbyServerWs standbyServiceClient) {
		this.standbyServiceClient = standbyServiceClient;
	}
	public List<ReleaseArea> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<ReleaseArea> areaList) {
		this.areaList = areaList;
	}
	
}
