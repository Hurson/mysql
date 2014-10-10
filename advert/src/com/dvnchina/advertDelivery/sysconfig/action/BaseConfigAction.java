package com.dvnchina.advertDelivery.sysconfig.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.BaseConfig;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;
import com.opensymphony.xwork2.ActionSupport;



public class BaseConfigAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String queryKey;
	private BaseConfig baseConfig;
	private BaseConfigService baseConfigService;
	private PageBeanDB page = new PageBeanDB();
	

	/**
	 * 分页查信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public String queryConfigList(){
		
		page =baseConfigService.queryConfigList(page.getPageNo(), page.getPageSize(), queryKey);
		return SUCCESS;
		
	}
	
	/**
	 * 根据ID获取配置项信息
	 * @param id
	 * @return BaseConfig
	 */
	public String getBaseConfigById(){
		String id = request.getParameter("id");
		if(!"".equals(id) && id != null){
			int lid = Integer.valueOf(id);
			baseConfig = baseConfigService.getBaseConfigById(lid);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 保存
	 * @param BaseConfig
	 * @return int
	 */
	public String saveBaseConfig(){
		int flag = baseConfigService.saveBaseConfig(baseConfig);
		if(flag == 1){
			return SUCCESS;
		}else {
			return ERROR;
		}
	}
	
	/**
	 * 修改
	 * @param BaseConfig
	 * 
	 */
	public String updateBaseConfig(){
		int id = baseConfig.getId();
		if(id == 0){
			baseConfigService.saveBaseConfig(baseConfig);
		} else {
			baseConfigService.updateBaseConfig(baseConfig);
		}
		return queryConfigList();
	}
	
	/**
	 * 保存
	 * @param List<BaseConfig>
	 * 
	 */
	public void deleteBaseConfig(String ids){
		
		//baseConfigService.deleteBaseConfig(ids);
	}
	
	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public BaseConfig getBaseConfig() {
		return baseConfig;
	}

	public void setBaseConfig(BaseConfig baseConfig) {
		this.baseConfig = baseConfig;
	}

	public BaseConfigService getBaseConfigService() {
		return baseConfigService;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}
	
	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB pageBean) {
		this.page = pageBean;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}
}
