package com.avit.dtmb.ploy.action;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.ploy.service.DPloyService;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.model.ReleaseArea;

@ParentPackage("default")
@Namespace("/dploy")
@Scope("prototype")
@Controller
public class DPloyAction extends BaseAction {

	private static final long serialVersionUID = 6537640372106524826L;
	
	private DPloy ploy;
	private PageBeanDB page;
	private List<DAdPosition> listPosition;
	private DAdPosition position;
	private List<ReleaseArea> areaList;
	
	@Resource
	private DPloyService dPloyService;
	
	@Action(value = "queryDPloyList", results = {
			@Result(name = "success", location = "/page/ploy/dploy/dPloyList.jsp"),
			@Result(name = "audit", location = "/page/ploy/dploy/auditDPloyList.jsp")})
	public String queryDPloyList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dPloyService.queryDTMBPloyList(ploy, page.getPageNo(), page.getPageSize());
		if(ploy != null && ploy.getStatus().equals("1")){
			return "audit";
		}
		return SUCCESS;
	}
	@Action(value = "getDPloy", results = { 
			@Result(name = "success", location = "/page/ploy/dploy/dPloy.jsp"),
			@Result(name = "audit", location = "/page/ploy/dploy/auditDPloy.jsp")})
	public String getDTMBPloy(){
		if(ploy != null){
			ploy = dPloyService.getDTMBPloy(ploy.getId());
		}
		
		listPosition = dPloyService.queryPositionList();
		areaList = dPloyService.listReleaseArea();
		if("audit".equals(operType)){
			return "audit";
		}
		return SUCCESS;
	}
	@Action(value = "getPositionPloy")
	public void getPositionPolyType(){
		position = dPloyService.getPositionByCode(position.getPositionCode());
		
		super.renderJson(position.getPloyTypeJson());
	}
	@Action(value = "saveDPloy", results = { @Result(name = "success", location = "/page/ploy/dploy/dPloyList.jsp") })
	public String saveDPloy(){
		
		ploy.setCreateTime(new Date());
		ploy.getCustomer().setId(0);
		ploy.setModifyTime(new Date());
		ploy.setStatus("1");
		dPloyService.saveDTMBPloy(ploy);
		ploy = null;
		return queryDPloyList();
	}
	@Action(value = "deleteDPloy", results = { @Result(name = "success", type= "redirect", location = "queryDPloyList.action") })
	public String deleteDPloy(){
		List<String> idList = Arrays.asList(ids.split(","));
		dPloyService.deleteDTMBPloy(idList);
		return SUCCESS;
	}
	@Action(value = "checkDPloy")
	public void checkDPloy(){
		 String result = dPloyService.checkDPloyName(ploy);
		this.renderText(result);
	}
	@Action(value = "auditDPloy", results = { @Result(name="success", type="redirect", location="/dploy/queryDPloyList.action?ploy.status=1")})
	public String auditDPloy(){
		DPloy dploy = dPloyService.getDTMBPloy(ploy.getId());
		dploy.setStatus(ploy.getStatus());
		dploy.setAuditTime(new Date());
		dPloyService.saveDTMBPloy(dploy);
		return queryDPloyList();
	}
	public DPloy getPloy() {
		return ploy;
	}

	public void setPloy(DPloy ploy) {
		this.ploy = ploy;
	}

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public DPloyService getdPloyService() {
		return dPloyService;
	}

	public void setdPloyService(DPloyService dPloyService) {
		this.dPloyService = dPloyService;
	}

	public List<DAdPosition> getListPosition() {
		return listPosition;
	}

	public void setListPosition(List<DAdPosition> listPosition) {
		this.listPosition = listPosition;
	}

	public DAdPosition getPosition() {
		return position;
	}

	public void setPosition(DAdPosition position) {
		this.position = position;
	}
	
	public List<ReleaseArea> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<ReleaseArea> areaList) {
		this.areaList = areaList;
	}
	
}
