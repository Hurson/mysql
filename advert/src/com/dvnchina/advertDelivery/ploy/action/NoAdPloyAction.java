package com.dvnchina.advertDelivery.ploy.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.action.CustomerAction;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyBackup;
import com.dvnchina.advertDelivery.ploy.bean.PloyChannel;
import com.dvnchina.advertDelivery.ploy.bean.TNoAdPloy;
import com.dvnchina.advertDelivery.ploy.service.NoAdPloyService;
import com.dvnchina.advertDelivery.ploy.service.PloyService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class NoAdPloyAction extends BaseAction implements ServletRequestAware{
	private Logger logger = Logger.getLogger(NoAdPloyAction.class);
	private OperateLogService operateLogService = null;
	private NoAdPloyService noAdPloyService;
	private TNoAdPloy noAdPloy =new TNoAdPloy() ;
	
	private PageBeanDB pageNoAdPloy=  new PageBeanDB() ;
	private PageBeanDB pageAdPosition=  new PageBeanDB() ;
	private AdvertPosition adPosition;
	private String dataIds;
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	/**
	 * 将内容写入对应的response中
	 * @param str 存有播出单列表的字符串
	 */
	private void print(String str) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getOutputStream().write(str.getBytes("utf-8"));
		} catch (Exception e) {
			logger.error("send response error");
		}
	}

	public void initData()
	{
				
		pageAdPosition.setPageSize(1000);
		pageAdPosition = noAdPloyService.queryAdPosition(null, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		//初始化规则
		//pageRule.setPageSize(1000);
	    //pageRule = ployService.queryAdPositionRule(contract.getId(), adPosition.getId(), rule,pageRule.getPageSize(),pageRule.getPageNo());
		
	}
    //查询禁播列表
	public String queryNoAdPloyList(){
		String returnPath = "";
		initData();
		
		try {
		//	int count = ployService.getployCount(conditionMap);
			pageNoAdPloy = noAdPloyService.queryNoAdPloyList(noAdPloy, pageNoAdPloy.getPageSize(), pageNoAdPloy.getPageNo());
		//	List<Ploy> lstPloy = ployService.getAllPloyList(conditionMap,page.getBegin(), page.getEnd());
		//	request.setAttribute("page", page);
		//	request.setAttribute("list", lstPloy);
		} catch (Exception e) {
			returnPath=NONE;
		}
		
		return SUCCESS;
	}
	/**
	 * 获取禁播详情
	 * @return
	 */
	public String getNoAdPloyById(){
		initData();
		if (noAdPloy!=null)
		{
		noAdPloy = noAdPloyService.getNoAdPloyByID(noAdPloy.getId());
		}
		else
		{
			noAdPloy=null;
		}
		return SUCCESS;
	}
	//删除禁播记录	
	public String deleteNoAdPloy()
	{
		try
		{
			dataIds = dataIds.replace(" ","");
			noAdPloyService.deleteNoAdPloy(dataIds);
			message = "common.delete.success";//保存成功
		}catch(Exception e){
			message = "common.delete.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
		}
		finally
		{
			operInfo = "删除禁播策略：IDS="+dataIds;
			operType = "operate.delete";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_PLOY);
			operateLogService.saveOperateLog(operLog);
		}		
		queryNoAdPloyList();
		return SUCCESS;
	}
	public String checkNoAdPloy()
	{
		//名称重复
		
		if ("1".equals(noAdPloyService.checkNoAdPloy(noAdPloy)))
		{
			print("1");
		}
		else{
					print("0");
		}
		return NONE;
	}
	public String saveUpdateNoAdPloy(){

		//ployService.queryAreaList(null,10,1);
		boolean flag = false;
		try {
			
			if (noAdPloy.getId()!=null && noAdPloy.getId()!=0)
			{
				operType = "operate.update";
			}
			else
			{
				operType = "operate.add";
			}
			flag = noAdPloyService.saveOrUpdate(noAdPloy);
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
		}
		finally
		{
			operInfo = noAdPloy.toString();
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_PLOY);
			operateLogService.saveOperateLog(operLog);
		}	
		noAdPloy= null;
		queryNoAdPloyList();
		return SUCCESS;
	}
	public NoAdPloyService getNoAdPloyService() {
		return noAdPloyService;
	}
	public void setNoAdPloyService(NoAdPloyService noAdPloyService) {
		this.noAdPloyService = noAdPloyService;
	}
	public TNoAdPloy getNoAdPloy() {
		return noAdPloy;
	}
	public void setNoAdPloy(TNoAdPloy noAdPloy) {
		this.noAdPloy = noAdPloy;
	}
	public PageBeanDB getPageNoAdPloy() {
		return pageNoAdPloy;
	}
	public void setPageNoAdPloy(PageBeanDB pageNoAdPloy) {
		this.pageNoAdPloy = pageNoAdPloy;
	}
	public PageBeanDB getPageAdPosition() {
		return pageAdPosition;
	}
	public void setPageAdPosition(PageBeanDB pageAdPosition) {
		this.pageAdPosition = pageAdPosition;
	}
	public AdvertPosition getAdPosition() {
		return adPosition;
	}
	public void setAdPosition(AdvertPosition adPosition) {
		this.adPosition = adPosition;
	}
	public String getDataIds() {
		return dataIds;
	}
	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public OperateLogService getOperateLogService() {
		return operateLogService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	
}
