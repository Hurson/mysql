package com.dvnchina.advertDelivery.accounts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.accounts.service.ContractAccountsService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.Contract;
import com.opensymphony.xwork2.ActionSupport;

public class ContractAccountsAction extends ActionSupport implements ServletRequestAware{
	private Logger logger = Logger.getLogger(ContractAccountsAction.class);
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private Contract contract=new Contract();
	private ContractAccountsService contractAccountsService;
	private PageBeanDB page = new PageBeanDB();
	private ContractAccounts contractAccounts = new ContractAccounts();
	private String queryKey;
	private int contractId;

	/**
	 * 查询台账列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public String queryContractAccountsList(){
		try {
			page.setPageSize(10);
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("accountOrder", queryKey);
			page =contractAccountsService.queryContractAccountsList(page.getPageNo(), page.getPageSize(), queryMap,contractId);
		} catch (Exception e) {
			logger.error("查询合同台账列表出错", e);
		}
		return SUCCESS;
		
	}
	
	/**
	 * 根据ID获取台账信息
	 * @param id
	 * @return ContractAccountsService
	 */
	public String getContractAccountsById(){
		try {
			String id = request.getParameter("accountsId");
			if(!"".equals(id) && id != null){
				int lid = Integer.valueOf(id);
				contractAccounts = contractAccountsService.getContractAccountsById(lid);
			}
		} catch (Exception e) {
			logger.error("根据ID查询合同台账出错", e);
		}
		
		
		return SUCCESS;
	}
	
	/**
	 * 保存台账信息
	 * @param ContractAccountsService
	 * 
	 */
	public String updateContractAccounts(){
		try {
			Integer id = contractAccounts.getAccountsId();
			if(id == null){
				contractAccountsService.saveContractAccounts(contractAccounts);
			} else {
				contractAccountsService.updateContractAccounts(contractAccounts);
			}
		} catch (Exception e) {
			logger.error("更新合同台账出错", e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 跳转到初始化编辑页面
	 * @return
	 */
	public String initAddPage(){
		contractAccounts = null;
		return SUCCESS;
	}
	
	/**
	 * 获取该合同已付款台账的周期最后时间，并设置为下一次付款的周期开始时间，并得到该日期的下月和下季度的时间
	 * @return Json  
	 */
	public void getContractsDeadLine(){
		try {
			
			if(contractId != 0 ){
				// 查询合同付款台账的周期结束时间，并作为下一次付款的周期开始时间，如果查询到得deadline为空，则该
				//合同还未付过款，则不设置json
				Date deadLine = contractAccountsService.getContractsDeadLine(contractId);
				if(deadLine != null){
					// 设置按月付款的下一月的周期结束时间
					Date nextPeriod = this.addMonth(deadLine, 1);
					// 设置按季度付款的下一季度的周期结束时间
					Date nextThreeMonth = this.addMonth(deadLine, 3);
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					
					String strDeadline = sd.format(deadLine);
					String strNextPeriod = sd.format(nextPeriod);
					String strnextThreeMonth = sd.format(nextThreeMonth);
					
					String strJson = "{\"deadline\":\""+strDeadline+"\",\"nextPeriod\":\""+strNextPeriod+"\",\"nextThreeMonth\":\""+strnextThreeMonth+"\"}";
					this.renderJson(strJson);
				}
			}
		} catch (Exception e) {
			logger.error("获取该合同已付款台账的周期最后时间，并设置为下一次付款的周期开始时间，并得到该日期的下月和下季度的时间时出错", e);
		}
		
	}
	
	public String queryContractList()
	{
		try {
			String contractName = contract.getContractName();
			page =  contractAccountsService.queryContract(contractName,page.getPageNo(), page.getPageSize());
		} catch (Exception e) {
			logger.error("查询合同台账合同时错处", e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * render json
	 */
	private void renderJson(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 
     * @方法名称：对某日期加减月
     *
     * @param now
     * @param n
     * @return
     */
    private Date addMonth(Date date, int n)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        
        calendar.add(Calendar.MONTH, n);
        
        return calendar.getTime();
    }
	/***********************************************************************************/
	
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public ContractAccountsService getContractAccountsService() {
		return contractAccountsService;
	}

	public void setContractAccountsService(
			ContractAccountsService contractAccountsService) {
		this.contractAccountsService = contractAccountsService;
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
	
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public ContractAccounts getContractAccounts() {
		return contractAccounts;
	}

	public void setContractAccounts(ContractAccounts contractAccounts) {
		this.contractAccounts = contractAccounts;
	}

	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	
	

}
