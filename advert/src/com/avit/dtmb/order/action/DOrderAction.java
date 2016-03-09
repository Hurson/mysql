package com.avit.dtmb.order.action;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.bean.DOrderMateRelTmp;
import com.avit.dtmb.order.service.DOrderService;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.AuditLog;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.AuditLogService;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.UserLogin;

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
	private DOrderMateRelTmp omrTmp;
	private List<ReleaseArea> releaseAreaList;
	private DResource resource;
	private String flag;
	private List<Customer> customerList;

	/**操作日志类*/
	public OperateLog operLog;
	private AuditLog auditLog;
	
	@Resource
	private OperateLogService operateLogService;
	@Resource
	private AuditLogService auditLogService;
	@Resource
	private DOrderService dOrderService;
	
	@Action(value = "queryDOrderList", results = {
			@Result(name = "success", location = "/page/order/dorder/dOrderList.jsp")})
	public String queryDOrderList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dOrderService.queryDTMBOrderList(order, page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}
	
	@Action(value = "queryAuditDOrderList", results = {
			@Result(name = "success", location = "/page/order/dorder/auditDOrderList.jsp")})
	public String queryAuditDOrderList(){
		if(page == null){
			page = new PageBeanDB();
		}
		customerList = dOrderService.getCustomerList();
		page = dOrderService.queryAuditDOrderList(order, page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}
	
	@Action(value = "getDOrder", results = { 
			@Result(name = "success", location = "/page/order/dorder/addDOrder.jsp"),
			@Result(name = "audit", location = "/page/order/dorder/auditDOrder.jsp")})
	public String getDTMBOrder(){
		if(order != null){
			order = dOrderService.getDTMBOrderById(order.getId());
		}else{
			order = new DOrder();
			order.setOrderCode(System.currentTimeMillis() + "");
			dpositionList = dOrderService.queryPositionList();
		}
		roleType = getLoginUser().getRoleType();
		if("audit".equals(operType)){
			return "audit";
		}
		
		return SUCCESS;
	}
	
	@Action(value = "queryDPloyList", results = { 
			@Result(name = "success", location = "/page/order/dorder/selectPloy.jsp")})
	public String queryDPloyList() {
		if(page == null){
			page = new PageBeanDB();
		}
		
		page = dOrderService.queryDTMBPloyList(ploy, page.getPageNo(), page.getPageSize());
		
		return SUCCESS;
	}
	
	@Action(value = "insertDOrderMateRelTmp")
	public void insertDOrderMateRelTmp(){
		dOrderService.insertDOrderMateRelTmp(order);
	}
	
	@Action(value = "queryDOrderMateRelTmp", results = { 
			@Result(name = "success", location = "/page/order/dorder/orderMateRelList.jsp")})
	public String queryDOrderMateRelTmp(){
		if(page == null){
			page = new PageBeanDB();
		}
		releaseAreaList = dOrderService.queryReleaseAreaList();
		page = dOrderService.queryDOrderMateRelTmpList(omrTmp, page.getPageNo(), page.getPageSize());
		
		return SUCCESS;
	}
	
	@Action(value = "getOrderResourceJson")
	public void getOrderResourceJson() {
		String result = dOrderService.getOrderResourceJson(omrTmp);
		this.renderJson(result);
	}
	
	@Action(value = "saveDOrder", results = { @Result(name = "success",type="redirect", location = "queryDOrderList.action") })
	public String saveDOrder(){
		
		if(order.getId() == null){
			operType = "operate.add";
			
			order.setCreateTime(new Date());
			order.setModifyTime(new Date());
			order.setState("0");
			
		}else{
			operType = "operate.update";
			
			if(!"0".equals(order.getState())){
				order.setState("1");
			}
			order.setModifyTime(new Date());
		}
		
		UserLogin user = getLoginUser();
		order.getCustomer().setId(user.getCustomerId());
		order.setOperatorId(user.getUserId());
		dOrderService.saveDOrder(order);
		
		operInfo = order.toString();
        operLog = this.setOperationLog(Constant.OPERATE_MODULE_DPLOY);
        operateLogService.saveOperateLog(operLog);
		
		return SUCCESS;
	}
	
	@Action(value = "deleteDOrder", results = { @Result(name = "success",type="redirect", location = "queryDOrderList.action") })
	public String deleteDOrder(){
		List<String> idList = Arrays.asList(ids.split(","));
		dOrderService.deleteDOrder(idList);
		
		StringBuffer delInfo = new StringBuffer();
		delInfo.append("删除素材：");
        delInfo.append("共").append(ids.split(",").length).append("条记录(ids:"+ids+")");
		operType = "operate.delete";
		operInfo = delInfo.toString();
		operLog = this.setOperationLog(Constant.OPERATE_MODULE_DORDER);
		operateLogService.saveOperateLog(operLog);
		
		return SUCCESS;
	}
	
	@Action(value = "queryDResourceList", results = { 
			@Result(name = "success", location = "/page/order/dorder/selectResource.jsp")})
	public String queryDResourceList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dOrderService.queryDResourceList(resource, page.getPageNo(), page.getPageSize());
		
		return SUCCESS;
	}
	
	@Action(value = "saveDOrderMateRelTmp")
	public void saveDOrderMateRelTmp(){
		String resourceIds = this.getRequest().getParameter("resourceIds");
		dOrderService.saveOrderMateRelTmp(ids, resourceIds);
	}
	
	@Action(value = "checkDPloy")
	public void checkDPloy(){
		
	}
	@Action(value = "auditDOrder")
	public void auditDOrder(){
		String result = dOrderService.auditDTMBOrder(order, flag);
		int state = 0;
		if("-1".equals(flag)){
			state = Integer.parseInt(order.getState()) + 1;
		}else if("1".equals(flag)){
			state = Integer.parseInt(order.getState()) * 2;
			
		}
		saveAuditLog(order.getId(),order.getAuditAdvice(),state);
		this.renderText(result);
	}
	@Action(value = "checkDOrderRule")
	public void checkDOrderRule(){
		String result = "";
		result = dOrderService.checkDOrderRule(order);
		this.renderText(result);
	}
	@Action(value = "delDOrderMateRelTmp",results={@Result(name = "success", type="redirect", location = "queryDOrderMateRelTmp.action",
			params={"omrTmp.orderCode", "${omrTmp.orderCode}"})})
	public String delDOrderMateRelTmp(){
		dOrderService.delDOrderMateRelTmp(ids);
		return SUCCESS;
	}
	@Action(value = "repushOrder")
	public void repushOrder(){
		String result = dOrderService.repushOrder(order.getOrderCode());
		this.renderText(result);
	}
	@Action(value = "previewResource", results = { 
			@Result(name = "success", location = "/page/order/dorder/preview.jsp")})
	public String previewResource(){
		Map<String, String> map = dOrderService.previewResource(resource);
		this.getRequest().setAttribute("map", map);
		return SUCCESS;
	}
	
	/**
	 * 查询订单审核日志
	 * @return
	 */
	@Action(value = "queryDOrderAuditLog", results = { 
			@Result(name = "success", location = "/page/order/orderAuditLogList.jsp")})
	public String queryDOrderAuditLog(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = auditLogService.queryDAuditLogList(auditLog, page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	private void saveAuditLog(Integer orderId,String opinion, int auditState) {
		AuditLog auditLog = new AuditLog();
		auditLog.setRelationType(Constant.AUDIT_RELATION_TYPE_DORDER);
		auditLog.setRelationId(orderId);
		auditLog.setState(auditState);
		auditLog.setOperatorId(getUserId());
		auditLog.setAuditOpinion(opinion);
		auditLogService.saveAuditLog(auditLog);
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
	public DOrderMateRelTmp getOmrTmp() {
		return omrTmp;
	}
	public void setOmrTmp(DOrderMateRelTmp omrTmp) {
		this.omrTmp = omrTmp;
	}
	public List<ReleaseArea> getReleaseAreaList() {
		return releaseAreaList;
	}
	public void setReleaseAreaList(List<ReleaseArea> releaseAreaList) {
		this.releaseAreaList = releaseAreaList;
	}
	public DResource getResource() {
		return resource;
	}
	public void setResource(DResource resource) {
		this.resource = resource;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public AuditLog getAuditLog() {
		return auditLog;
	}

	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}
	
	
}
