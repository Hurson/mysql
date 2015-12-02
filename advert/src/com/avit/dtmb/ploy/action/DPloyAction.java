package com.avit.dtmb.ploy.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.avit.dtmb.type.PloyType;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;

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
	
	@Resource
	private DPloyService dPloyService;
	public DPloyAction(){
		System.out.println("init DPloyAction() .........");
	}
	
	@Action(value = "queryDPloyList", results = { @Result(name = "success", location = "/page/ploy/dploy/dPloyList.jsp") })
	public String queryDPloyList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dPloyService.queryDTMBPloyList(ploy, page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}
	@Action(value = "getDPloy", results = { @Result(name = "success", location = "/page/ploy/dploy/dPloy.jsp") })
	public String getDTMBPloy(){
		
		ploy = dPloyService.getDTMBPloy(ploy.getId());
		listPosition = dPloyService.queryPositionList();
		
		return SUCCESS;
	}
	public void getPositionPolyType(){
		
		position = dPloyService.getPositionByCode(position.getPositionCode());
		String[] ployTypes = position.getPloyTypes().split("\\|");
		Map<String, String> typeMap = new HashMap<String, String>();
		for(String type : ployTypes){
			typeMap.put(type, PloyType.getValue(type));
		}
		System.out.println(typeMap.toString());
		super.renderJson(typeMap.toString());
	}
	public String saveDPloy(){
		dPloyService.saveDTMBPloy(ploy);
		return SUCCESS;
	}
	public String deleteDPloy(){
		List<String> idList = Arrays.asList(ids.split(","));
		dPloyService.deleteDTMBPloy(idList);
		return SUCCESS;
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
	
}
