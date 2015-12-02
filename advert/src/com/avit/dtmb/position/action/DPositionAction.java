package com.avit.dtmb.position.action;


import javax.annotation.Resource;
import javax.annotation.Resources;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.position.service.DPositionService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;

@ParentPackage("default")
@Namespace("/page/dposition")
@Scope("prototype")
@Controller
public class DPositionAction extends BaseAction{
	private PageBeanDB page;
	private DAdPosition adposition;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6922043917965650341L;
	 @Resource
	private DPositionService dPositionService;
	public DPositionAction(){
		
	}
	@Action(value = "queryDPositionPackageList",results = {@Result(name = "success",location = "/page/ploy/dploy/dPloyList.jsp")})
	public  String queryDPositionPackageList(){
		if(page==null){
			PageBeanDB page = new PageBeanDB();
		}
		page = dPositionService.queryDPositionList(adposition, page.getPageNo(), page.getPageSize());
		return SUCCESS;	
	}
}
