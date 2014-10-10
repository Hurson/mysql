package com.dvnchina.advertDelivery.action;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.service.UserIndustryCategoryService;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;

public class UserIndustryCategoryAction extends BaseActionSupport<Object> {

	private static final long serialVersionUID = -5788818355010612263L;
	
    private  UserIndustryCategory userIndustryCategory;
	
    private  List<UserIndustryCategory> listUserIndustryCategory;
	
	private UserIndustryCategoryService userIndustryCategoryService;
	
	/**
	 * 页号
	 */
	protected int pageNo;
	
	/**
	 * 查询结果集
	 * 
	 */
	public String list(){
		
		int count = 0;
		
		if(userIndustryCategory == null){
			userIndustryCategory = new UserIndustryCategory();
		}
		
		count = userIndustryCategoryService.getUserIndustryCategoryCount(userIndustryCategory, "");
		
		PageBeanDB page = new PageBeanDB(count,pageNo);
		
		listUserIndustryCategory = userIndustryCategoryService.listUserIndustryCategoryMgr(userIndustryCategory, page.getBegin(),page.getPageSize(), "");
		
		this.getRequest().setAttribute("page", page);
		
		return "list";
	}
	
	
	

	public UserIndustryCategory getUserIndustryCategory() {
		return userIndustryCategory;
	}

	public void setUserIndustryCategory(UserIndustryCategory userIndustryCategory) {
		this.userIndustryCategory = userIndustryCategory;
	}
	
	public List<UserIndustryCategory> getListUserIndustryCategory() {
		return listUserIndustryCategory;
	}

	public void setListUserIndustryCategory(
			List<UserIndustryCategory> listUserIndustryCategory) {
		this.listUserIndustryCategory = listUserIndustryCategory;
	}

	public UserIndustryCategoryService getUserIndustryCategoryService() {
		return userIndustryCategoryService;
	}

	public void setUserIndustryCategoryService(
			UserIndustryCategoryService userIndustryCategoryService) {
		this.userIndustryCategoryService = userIndustryCategoryService;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}






