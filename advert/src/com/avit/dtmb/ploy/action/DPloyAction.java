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

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.ploy.service.DPloyService;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;
import com.dvnchina.advertDelivery.sysconfig.service.UserRankService;

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
	private UserRank userRank;
	private  UserIndustryCategory userIndustryCategory;
	private DChannelGroup channelGroup;
	
	/**操作日志类*/
	public OperateLog operLog;
	
	@Resource
	private OperateLogService operateLogService;
   	@Resource
	private DPloyService dPloyService;
	private UserRankService userRankService;
	
	@Action(value = "queryDPloyList", results = {
			@Result(name = "success", location = "/page/ploy/dploy/dPloyList.jsp"),
			@Result(name = "audit", location = "/page/ploy/dploy/auditDPloyList.jsp")})
	public String queryDPloyList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dPloyService.queryDTMBPloyList(ploy, page.getPageNo(), page.getPageSize());
		if(ploy != null && "1".equals(ploy.getStatus())){
			return "audit";
		}
		return SUCCESS;
	}
	@Action(value = "getDPloy", results = { 
			@Result(name = "success", location = "/page/ploy/dploy/addDPloy.jsp"),
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
	@Action(value = "saveDPloy", results = { @Result(name = "success",type="redirect", location = "queryDPloyList.action") })
	public String saveDPloy(){
		if(ploy.getId() == null){
			operType = "operate.add";
			
			ploy.setCreateTime(new Date());
			UserLogin user = getLoginUser();
			ploy.getCustomer().setId(user.getCustomerId());
			ploy.setOperatorId(user.getUserId());
		}
		
		ploy.setModifyTime(new Date());
		ploy.setStatus("1");
		dPloyService.saveDTMBPloy(ploy);
		
		operType = "operate.update";
		operInfo = ploy.toString();
        operLog = this.setOperationLog(Constant.OPERATE_MODULE_DPLOY);
        operateLogService.saveOperateLog(operLog);
		
		ploy = null;
		return SUCCESS;
	}
	@Action(value = "deleteDPloy", results = { @Result(name = "success", type= "redirect", location = "queryDPloyList.action") })
	public String deleteDPloy(){
		List<String> idList = Arrays.asList(ids.split(","));
		dPloyService.deleteDTMBPloy(idList);
		
		StringBuffer delInfo = new StringBuffer();
		delInfo.append("删除素材：");
        delInfo.append("共").append(ids.split(",").length).append("条记录(ids:"+ids+")");
		operType = "operate.delete";
		operInfo = delInfo.toString();
		operLog = this.setOperationLog(Constant.OPERATE_MODULE_DPLOY);
		operateLogService.saveOperateLog(operLog);
		
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
		
		operInfo = dploy.toString();
		operType = "2".equals(ploy.getStatus())?"operate.aduitOk":"operate.aduitFalse";
		operLog = this.setOperationLog(Constant.OPERATE_MODULE_DPLOY);
		operateLogService.saveOperateLog(operLog);
		
		return SUCCESS;
	}
	@Action(value = "queryChannelGroupList", results = { @Result(name = "success", location = "/page/ploy/dploy/bindingChannelGroup.jsp")})
	public String queryChannelGroupList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dPloyService.queryChanelGroupList(channelGroup, page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}
	@Action(value = "queryUserRankList", results = { @Result(name = "success", location = "/page/ploy/dploy/bindingUserRank.jsp")})
	public String queryUserRankList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = userRankService.queryUserRankList(userRank, page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}
	@Action(value = "queryUserIndustryList", results = { @Result(name = "success", location = "/page/ploy/dploy/bindingUserIndustry.jsp")})
	public String queryUserIndustryList(){
		if(page == null){
			page = new PageBeanDB();
		}
		page = dPloyService.queryUserIndustryList(userIndustryCategory, page.getPageNo(), page.getPageSize());
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
	
	public List<ReleaseArea> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<ReleaseArea> areaList) {
		this.areaList = areaList;
	}
	public UserRank getUserRank() {
		return userRank;
	}
	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;
	}
	public UserRankService getUserRankService() {
		return userRankService;
	}
	public void setUserRankService(UserRankService userRankService) {
		this.userRankService = userRankService;
	}
	public UserIndustryCategory getUserIndustryCategory() {
		return userIndustryCategory;
	}
	public void setUserIndustryCategory(UserIndustryCategory userIndustryCategory) {
		this.userIndustryCategory = userIndustryCategory;
	}
	public DChannelGroup getChannelGroup() {
		return channelGroup;
	}
	public void setChannelGroup(DChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}
		
}
