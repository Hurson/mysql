package com.avit.dtmb.order.action;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.service.DOrderService;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;

@ParentPackage("default")
@Namespace("/dorder")
@Scope("prototype")
@Controller
public class DOrderAction extends BaseAction {

	private static final long serialVersionUID = 6537640372106524826L;
	
	private DOrder order;
	private DPloy ploy;
	private PageBeanDB page;
	private List<DAdPosition> dpositionList;
	private Integer roleType;
	
	
	@Resource
	private DOrderService dOrderService;
	
	@Action(value = "queryDOrderList", results = {
			@Result(name = "success", location = "/page/order/dorder/dOrderList.jsp"),
			@Result(name = "audit", location = "/page/order/dorder/auditDOrderList.jsp")})
	public String queryDOrderList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dOrderService.queryDTMBOrderList(order, page.getPageNo(), page.getPageSize());
		if(order != null && order.getState().equals("1")){
			return "audit";
		}
		return SUCCESS;
	}
	@Action(value = "getDOrder", results = { 
			@Result(name = "success", location = "/page/order/dorder/addOrderArea.jsp"),
			@Result(name = "audit", location = "/page/ploy/dploy/auditDPloy.jsp")})
	public String getDTMBOrder(){
		if(order != null){
			order = dOrderService.getDTMBOrderById(order.getId());
		}else{
			order = new DOrder();
			order.setOrderCode(System.currentTimeMillis() + "");
			dpositionList = dOrderService.queryPositionList();
		}
		roleType = getLoginUser().getRoleType();
		
		return SUCCESS;
	}
	@Action(value = "queryDPloyList", results = { 
			@Result(name = "success", location = "/page/order/dorder/addOrderArea.jsp")})
	public String queryDPloyList() {
		if(page == null){
			page = new PageBeanDB();
		}
		
		page = dOrderService.queryDTMBPloyList(ploy, page.getPageNo(), page.getPageSize());
		
		return SUCCESS;
	}
	
	@Action(value = "saveDPloy", results = { @Result(name = "success", location = "/page/ploy/dploy/dPloyList.jsp") })
	public String saveDPloy(){
		return SUCCESS;
	}
	@Action(value = "deleteDPloy", results = { @Result(name = "success", location = "/page/ploy/dploy/dPloyList.jsp") })
	public String deleteDPloy(){
		List<String> idList = Arrays.asList(ids.split(","));
		dOrderService.deleteDOrder(idList);
		return SUCCESS;
	}
	@Action(value = "checkDPloy")
	public void checkDPloy(){
		
	}
	@Action(value = "auditDPloy", results = { @Result(name="success", type="redirect", location="/dploy/queryDPloyList.action?ploy.status=1")})
	public String auditDPloy(){
		return SUCCESS;
	}
	
	public DOrder getOrder() {
		return order;
	}
	public void setOrder(DOrder order) {
		this.order = order;
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
	
	public List<DAdPosition> getDpositionList() {
		return dpositionList;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	
}
