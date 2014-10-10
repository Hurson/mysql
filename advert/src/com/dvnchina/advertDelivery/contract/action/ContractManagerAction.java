package com.dvnchina.advertDelivery.contract.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.accounts.service.ContractAccountsService;
import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractContractADRelation;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.common.ExcelUtils;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.contract.service.ContractManagerService;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.service.ContractBackupService;
import com.dvnchina.advertDelivery.service.ContractRunService;
import com.dvnchina.advertDelivery.utils.CookieUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;

public class ContractManagerAction extends BaseActionSupport<Object> implements ServletRequestAware{
	
	private static final long serialVersionUID = -3666982468062423696L;
	
	private Logger logger = Logger.getLogger(ContractManagerAction.class);
	
	private PageBeanDB contractPage =  new PageBeanDB();//合同列表
	
	private PageBeanDB customerPage=  new PageBeanDB() ;//广告商列表,用于合同列表
	
	private PageBeanDB pageCustomer=  new PageBeanDB() ;//广告商列表,用于新增合同
	
	private PageBeanDB adPositionPackPage=  new PageBeanDB() ;//广告位包列表
	
	private Customer customerQuery;
	
	private HttpServletRequest request;
	
	private String positionIds;
	
	private AdvertPositionPackage advertPositionPackageQuery;
	
	private ContractManagerService contractManagerService;
	
	private ContractAccountsService contractAccountsService;
	
	private OperateLogService operateLogService;
	
	private ContractBackup contract;
	
	private String areasJson;
	
	private String dataIds;
	
	private Integer saveTag;
	//是否已审核过 1 0 查询运行期表是否有对应记录，确定是否已审核
	private Integer audited;
	//用于ajax校验 广告位是否已销售占用
	private Integer contractId;
	private Integer positionId;
	private Date    startDate;
	private Date    endDate;
	/** 合同累计付款金额*/
	private float accountsAmount;
	
	/**
	 * 接受来自页面中的ajax请求参数
	 */
	private String contractParam;
	
	private String contractCode;
	
	/**
	 * 修改前，更新记录时用于修改前后数据比对
	 */
	private String contractParamBefore;
	/**
	 * 用于收集比较结果
	 */
	private String comparedFormResult;
	/**
	 * 合同和中间表主键
	 */
	private String contractAd;
	
	private ContractBackupService contractBackupService;
	
	private ContractRunService contractRunService;
	
	private ContractQueryBean contractQuery;
	
	private String isAuditTag;//判断是用于审核查询列表还是维护查询列表
	
	/** 操作类型 */
//	public String operType;
//	/** 操作结果 */
//	public int operResult = Constant.OPERATE_SECCESS;
//	/** 操作详情 */
//	public String operInfo;
	/**操作日志类*/
	public OperateLog operLog;
	private String selPositionIds;
	private List selPositionIdList;



    public List getSelPositionIdList() {
		return selPositionIdList;
	}

	public void setSelPositionIdList(List selPositionIdList) {
		this.selPositionIdList = selPositionIdList;
	}

	public String getSelPositionIds() {
		return selPositionIds;
	}

	public void setSelPositionIds(String selPositionIds) {
		this.selPositionIds = selPositionIds;
	}

	public void setServletRequest(HttpServletRequest request) {
        this.request = request; 
    }
	
	/**
	 * 获取用户id
	 * */
	private Integer getOpId() {
		String userId = CookieUtils.getCookieValueByKey(getRequest(),
				LoginConstant.COOKIE_USER_ID);
		return new Integer(userId);
	}

	/**
	 * 获取用户名
	 * */
	private String getUserName() {
		String userName = CookieUtils.getCookieValueByKey(getRequest(),
				LoginConstant.COOKIE_USER_NAME);
		return userName;
	}
	
	
	

	public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }

    public PageBeanDB getCustomerPage() {
		return customerPage;
	}

	public void setCustomerPage(PageBeanDB customerPage) {
		this.customerPage = customerPage;
	}

	public ContractQueryBean getContractQuery() {
		return contractQuery;
	}

	public void setContractQuery(ContractQueryBean contractQuery) {
		this.contractQuery = contractQuery;
	}

	public ContractBackup getContract() {
		return contract;
	}

	public void setContract(ContractBackup contract) {
		this.contract = contract;
	}

	public ContractBackupService getContractBackupService() {
		return contractBackupService;
	}

	public void setContractBackupService(ContractBackupService contractBackupService) {
		this.contractBackupService = contractBackupService;
	}

	public ContractRunService getContractRunService() {
		return contractRunService;
	}

	public void setContractRunService(ContractRunService contractRunService) {
		this.contractRunService = contractRunService;
	}
	

	public PageBeanDB getAdPositionPackPage() {
        return adPositionPackPage;
    }

    public void setAdPositionPackPage(PageBeanDB adPositionPackPage) {
        this.adPositionPackPage = adPositionPackPage;
    }

    
    public AdvertPositionPackage getAdvertPositionPackageQuery() {
        return advertPositionPackageQuery;
    }

    public void setAdvertPositionPackageQuery(AdvertPositionPackage advertPositionPackageQuery) {
        this.advertPositionPackageQuery = advertPositionPackageQuery;
    }
    
    

    public String getAreasJson() {
        return areasJson;
    }

    public void setAreasJson(String areasJson) {
        this.areasJson = areasJson;
    }
    
    

    public String getDataIds() {
        return dataIds;
    }

    public void setDataIds(String dataIds) {
        this.dataIds = dataIds;
    }

    

	

	
	public Integer getSaveTag() {
        return saveTag;
    }

    public void setSaveTag(Integer saveTag) {
        this.saveTag = saveTag;
    }
    

    

    public Customer getCustomerQuery() {
        return customerQuery;
    }

    public void setCustomerQuery(Customer customerQuery) {
        this.customerQuery = customerQuery;
    }
    

    public PageBeanDB getPageCustomer() {
        return pageCustomer;
    }

    public void setPageCustomer(PageBeanDB pageCustomer) {
        this.pageCustomer = pageCustomer;
    }
    
    /**
     * 效验合同是否能删除
     * @return
     */
    public String checkDelContract()
	{
		String result="0";
		if (dataIds!=null)
		{
		
			dataIds = dataIds.replace(" ","");	
			String id[] = dataIds.split(",");
			
			if(!"null".equals(dataIds) && !"".equals(dataIds)){
				for(int i=0;i<id.length;i++){
					if(!"".equals(id[i]) && id[i] != null){
						
						int flag = contractManagerService.checkDelContractById(Integer.parseInt(id[i]));
						if (flag!=0)
						{
						   result = "1";
						   System.out.println("成功！");
						   break;
						}
						else
						{
							
						}
					}
				}
			}
		}

		print(result);
		return NONE;
	}
    
    /**
     * 效验合同号是否已存在
     * @return
     */
    public String checkContractExist()
	{
		String result="0";
		if (contractCode!=null)
		{	
			if(contractId!=null&&contractId!=0){
				//修改
				ContractBackup temp = contractManagerService.getContractByID(contractId);
				if(!temp.getContractNumber().equals(contractCode)){
					int flag = contractManagerService.checkContractExist(contractCode);
					if (flag!=0){
								   result = "1";
								   System.out.println("成功！");
								}
				}
			}else{
				//新增
				int flag = contractManagerService.checkContractExist(contractCode);
				if (flag!=0){
							   result = "1";
							   System.out.println("成功！");
							}
			}
									
		}
		print(result);
		return NONE;
	}

    /**
	 * 
	 * @description: 删除合同信息
	 * @param ids
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午03:26:51
	 */
	public String deleteContract(){
        if(StringUtils.isNotBlank(dataIds)){
            try {
                
                boolean flag = contractManagerService.deleteContractByIds(dataIds);
                if(flag){
                	//记录操作日志
                	StringBuffer delInfo = new StringBuffer();
            		delInfo.append("删除合同：");
                    delInfo.append("共").append(dataIds.split(",").length).append("条记录(ids:"+dataIds+")");
        			operType = "operate.delete";
        			operInfo = delInfo.toString();
        			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CONTRACT);
        			operateLogService.saveOperateLog(operLog);
        			
        			isAuditTag="0";
        			contractQuery=null;
                    queryContractList();
        			
                    return SUCCESS;
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                logger.error("deleteContract 删除合同异常",e);
            }
        }
        return NONE;
    }
	
	/**
	 * 
	 * @description: 查询合同列表
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-4-28 上午10:53:22
	 */
	public String queryContractList(){
		initData();
		if (contractPage==null)
		{
			contractPage  =  new PageBeanDB();
		}
		if (contractQuery==null)
        {
		    contractQuery  =  new ContractQueryBean();
        }
		try {
		    /**
		     * //从session中获取操作员对应的广告商列表,封装成合同查询条件
            List<Customer> customerList;
            String customerids  = "";
            customerList = (List<Customer>) this.getRequest().getSession().getAttribute("customer");
            if (customerList!=null && customerList.size()>0)
            {
                for (int i=0;i<customerList.size();i++)
                {
                    customerids = customerids+customerList.get(i).getId();
                    if (i<customerList.size()-1)
                    {
                        customerids = customerids+",";
                    }
                }
                contractQuery.setCustomerids(customerids);
            }
		     */
	        if(isAuditTag!=null&&isAuditTag.equals("1")){
                if(contractQuery==null){
                    contractQuery=new ContractQueryBean();
                }
                contractQuery.setStatus(0);
            }	        
		    
			contractPage = contractManagerService.queryContractList(contractQuery, contractPage.getPageSize(), contractPage.getPageNo());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryContractList方法获取数据列表时出现异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 初始化页面信息
	 */
	public void initData()
	{	
		//初始化广告商列表
		customerPage.setPageSize(1000);
		customerPage =  contractManagerService.queryCustomerList(customerPage.getPageSize(),customerPage.getPageNo());
	}
	
	/**
	 * 
	 * @description: 转向新增页面 
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午11:14:31
	 */
	public String intoAddContract(){
	    //saveTag=1;
		return SUCCESS;
	}
	
	/**
	 * 
	 * @description: 初始化合同信息,转向修改页面
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @throws ParseException 
	 * @date: 2013-5-6 上午11:14:49
	 */
	public String initContract() {
   
        try{
        	int contractId = Integer.valueOf(request.getParameter("contractId"));      
            contract = contractManagerService.getContractByID(contractId);
            if(contract.getContractDesc()!=null&&!contract.getContractDesc().equals("")){
                String contractDesc = contract.getContractDesc().trim();
                contract.setContractDesc(contractDesc);
            }       
            if(contract.getExaminationOpinions()!=null&&!contract.getExaminationOpinions().equals("")){
                String reason = contract.getExaminationOpinions().trim();
                contract.setExaminationOpinions(reason);
            }
            accountsAmount = contractManagerService.getAccountsAmount(contractId);
            List<PositionAD> positionADList = contractManagerService.getPositionADByContractID(contractId);
            request.setAttribute("contract", contract);
            //saveTag=0;
            areasJson = Obj2JsonUtil.list2json(positionADList);
            if (contractManagerService.isAudit(contractId))
            {
            	audited=1;
            }
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("初始化合同信息异常", e);
        }
        return SUCCESS;
    }
	
	/**
	 * 
	 * @description: 查询广告位包列表 
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-4-28 上午10:55:28
	 */
	public String selectAdPositionPackage(){		
		if(selPositionIds!=null&&!selPositionIds.equals("")){
			if(selPositionIdList==null){
				selPositionIdList=new ArrayList(){};			
			}
			
			String[] ids=selPositionIds.split(",");
			for (int i=0;i<ids.length;i++) {
				selPositionIdList.add(ids[i]);
			}
			HashSet h = new HashSet(selPositionIdList);
			selPositionIdList.clear();
			selPositionIdList.addAll(h);

		}
		
        if (adPositionPackPage==null)
        {
            adPositionPackPage  =  new PageBeanDB();
        }
        try {
            adPositionPackPage = contractManagerService.queryAdPositionPackageList(advertPositionPackageQuery, adPositionPackPage.getPageSize(), adPositionPackPage.getPageNo());

        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("获取广告位列表时出现异常",e);
        }
        
        return SUCCESS;
    }
	
	/**
	 * 
	 * @description: 查询操作员关联的广告商
	 * @return
	 * @throws Exception 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-8 上午10:13:28
	 */
	public String selectCustomer() {      
       try{
    	   if (pageCustomer==null)
           {
   	        pageCustomer  =  new PageBeanDB();
           }
           //从session中获取操作员对应的广告商列表
           List<Customer> customerList;
           String customerids  = "";
           /**
            * customerList = (List<Customer>) this.getRequest().getSession().getAttribute("customer");
           if (customerList!=null && customerList.size()>0)
           {
               for (int i=0;i<customerList.size();i++)
               {
                   customerids = customerids+customerList.get(i).getId();
                   if (i<customerList.size()-1)
                   {
                       customerids = customerids+",";
                   }
               }
           }
            * 
            */
           
           pageCustomer = contractManagerService.queryCustomerRealList(customerQuery, customerids,pageCustomer.getPageSize(), pageCustomer.getPageNo());
       }catch(Exception e){
    	   e.printStackTrace();
    	   logger.error("获取广告商列表时发生异常", e);
       }
        
        return SUCCESS;
        
    }
	
	/**
	 * 
	 * @description: 保存合同
	 * @return
	 * @throws ParseException 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-7 上午10:14:48
	 */
	public String saveConstractBackup(){
     
        try{
        	contract.setStatus(0);  
    	    contract.setOperatorId(this.getOpId());
    	    String contractDesc = contract.getContractDesc().trim();
    	    contract.setContractDesc(contractDesc);
    	    ContractBackup contractTemp = contractManagerService.getContractByCode(contract.getContractNumber());
    	    if(contractTemp!=null){
    	        //修改流程
    	        contractManagerService.updateConstractBackup(contract);
    	        //操作日志
    	        operInfo = contract.toString();
    			operType = "operate.update";
    			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CONTRACT);
    			operateLogService.saveOperateLog(operLog);
    	    }else{
    	        //新增流程
    	        contract.setCreateTime(new Date());
    	        contractManagerService.saveConstractBackup(contract);
    	        
    	        //操作日志
    	        operInfo = contract.toString();
    			operType = "operate.add";
    			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CONTRACT);
    			operateLogService.saveOperateLog(operLog);
    	    }
    	    
    	    
    	    List<ContractADBackup> contractADList = new ArrayList<ContractADBackup>();
            
            positionIds = positionIds.replace(" ","");
            String positionIdstemp[]= positionIds.split(",");
            for (int i=0;i<positionIdstemp.length;i++){
                String startDate=request.getParameter("positionStartDate_"+positionIdstemp[i]).replace(" ","");
                String endDate=request.getParameter("positionEndDate_"+positionIdstemp[i]).replace(" ","");
                ContractADBackup temp = new ContractADBackup();
                //temp.setId(contractManagerService.getMaxIdForContractAD()+i+1);
                temp.setContractId(contract.getId());
                temp.setPositionId(Integer.parseInt(positionIdstemp[i]));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                temp.setStartDate(sdf.parse(startDate));
                temp.setEndDate(sdf.parse(endDate));
                temp.setContractCode(contract.getContractCode());
                temp.setContractName(contract.getContractName());
                temp.setEffectiveStartDate(contract.getEffectiveStartDate());
                temp.setEffectiveEndDate(contract.getEffectiveEndDate());
                //获取广告位信息
                AdvertPositionPackage advertPositionPackage = contractManagerService.getAdPositionPackageByID(Integer.parseInt(positionIdstemp[i]));
                temp.setPositionName(advertPositionPackage.getName());
                //获取对应的营销规则
                MarketingRule marketingRule = contractManagerService.getMarketingRuleByPositionId(Integer.parseInt(positionIdstemp[i]));
                if(marketingRule==null){
                    marketingRule = contractManagerService.getMarketingRuleByPositionId(0);
                }
                temp.setMarketingRuleId(marketingRule.getId());
                temp.setMarketingRuleName(marketingRule.getMarketingRuleName());
                
                contractADList.add(temp);
                
                System.out.println("id:"+positionIdstemp[i]+"/startDate:"+startDate+"/endDate:"+endDate);
            }
            if(contractADList!=null&&contractADList.size()>0){
                if(contractTemp!=null){
                    //修改流程
                    contractManagerService.updateConstractADBackupList(contractADList,contract.getId());
                }else{
                    //新增流程
                    contractManagerService.saveConstractADBackupList(contractADList);
                }
            }
            isAuditTag="0";
            contractQuery=null;
            queryContractList();
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("保存合同异常", e);
        }
        
        return SUCCESS;
    }
	
	/**
	 * 
	 * @description: 合同审核
	 * @return
	 * @throws ParseException 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-7 上午10:14:36
	 */
	public String auditConstract() {	    
     
	    try{
	    	HttpServletRequest request = this.getRequest();
		    String auditFlag = request.getParameter("auditFlag");
		    String reason = request.getParameter("reason");
		    String contractId = request.getParameter("contractId");
		    ContractBackup contractTemp = contractManagerService.getContractByID(Integer.parseInt(contractId));
		    if(auditFlag.equals("1")){
		        //审核通过	        
		        contractTemp.setStatus(1);
		        contractTemp.setExaminationOpinions(reason);
		        contractTemp.setAuditDate(new Date());
		        contractManagerService.updateConstractBackup(contractTemp);
		        Contract contractReal = copyToContractReal(contractTemp);
		        contractManagerService.saveConstract(contractReal);
		        //复制合同广告位表
		        List<ContractAD> contractADList = new ArrayList<ContractAD>();
		        List<ContractADBackup> tempList = contractManagerService.getContractADByContractID(Integer.parseInt(contractId));

		        if(tempList!=null&&tempList.size()>0){
		            for (int i=0;i<tempList.size();i++){
		                ContractADBackup temp = (ContractADBackup)tempList.get(i);
		                ContractAD contractADReal = new ContractAD();
		                contractADReal.setId(temp.getId());
		                contractADReal.setContractId(temp.getContractId());
		                contractADReal.setPositionId(temp.getPositionId());
		                contractADReal.setStartDate(temp.getStartDate());
		                contractADReal.setEndDate(temp.getEndDate());
		                contractADReal.setContractCode(temp.getContractCode());
		                contractADReal.setContractName(temp.getContractName());
		                contractADReal.setEffectiveStartDate(temp.getEffectiveStartDate());
		                contractADReal.setEffectiveEndDate(temp.getEffectiveEndDate());           
		                contractADReal.setPositionName(temp.getPositionName());
		                contractADReal.setMarketingRuleId(temp.getMarketingRuleId());
		                contractADReal.setMarketingRuleName(temp.getMarketingRuleName());
		                
		                contractADList.add(contractADReal);        
		            }
		            contractManagerService.deleteContractADReal(Integer.parseInt(contractId));
		            contractManagerService.saveConstractADList(contractADList,Integer.parseInt(contractId));
		        }
		        
		        //操作日志
		        operInfo = contractTemp.toString();
				operType = "operate.aduitOk";
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_CONTRACT);
				operateLogService.saveOperateLog(operLog);
		    }else{
		        //审核不通过
		        contractTemp.setStatus(2);
		        contractTemp.setExaminationOpinions(reason);
		        contractTemp.setAuditDate(new Date());
		        contractManagerService.updateConstractBackup(contractTemp);
		        //操作日志
		        operInfo = contractTemp.toString();
				operType = "operate.aduitFalse";
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_CONTRACT);
				operateLogService.saveOperateLog(operLog);
		    }
		    
		    isAuditTag="1";
		    contractQuery=null;
            queryContractList();
		    
	    }catch(Exception e){
	    	e.printStackTrace();
	    	logger.error("审核合同异常", e);
	    }
        return SUCCESS;
    }
	
	/**
	 * 
	 * @description: 复制合同信息到正式表
	 * @param contractBackup
	 * @return 
	 * Contract
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-7 上午11:51:03
	 */
	public  Contract copyToContractReal(ContractBackup contractBackup){
	    Contract contractReal = new Contract();
	    contractReal.setContractCode(contractBackup.getContractCode());
	    contractReal.setContractName(contractBackup.getContractName());
	    contractReal.setContractNumber(contractBackup.getContractNumber());
	    contractReal.setCreateTime(new Date());
	    contractReal.setCustomerId(contractBackup.getCustomerId());
	    contractReal.setEffectiveStartDate(contractBackup.getEffectiveStartDate());
	    contractReal.setEffectiveEndDate(contractBackup.getApprovalEndDate());
	    contractReal.setFinancialInformation(contractBackup.getFinancialInformation());
	    contractReal.setState(contractBackup.getStatus());
	    contractReal.setSubmitUnits(contractBackup.getSubmitUnits());
	    contractReal.setApprovalCode(contractBackup.getApprovalCode());
	    contractReal.setApprovalStartDate(contractBackup.getApprovalStartDate());
	    contractReal.setApprovalEndDate(contractBackup.getApprovalEndDate());
	    contractReal.setContractDesc(contractBackup.getContractDesc());
	    contractReal.setId(contractBackup.getId());
	    contractReal.setOperatorId(contractBackup.getOperatorId());
	    return  contractReal;
	}

	
	/**
	 * 保存或更新合同信息
	 * @return
	 */
	public String approvalContract(){
		boolean flag = false;
		String message = "";
		StringBuffer url = new StringBuffer();
		
		Map<String,String> resultMap = new HashMap();
		Map responseResult = new HashMap();
		
		try {
			contractParam = URLDecoder.decode(contractParam, "UTF-8");
			resultMap=contractBackupService.updateApprovalContractContractBackup(contractParam,this.getUserId(),this.getUserName());
			//resultMap = contractBackupService.saveContractBackup(contractBackup);
		} catch (Exception e) {
			resultMap.put("flag","error");
			logger.error("参数解析出现异常",e);
		}
		
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		System.out.println(json);
		this.renderJson(json);
		return null;
	}
	
	/**
	 * 删除合同
	 * @return
	 */
	public String remove(){
		boolean flag = false;
		HttpServletRequest request = this.getRequest();
		String ids = "";
		String[] idArray = null; 
		
		try {
			//flag = contractBackupService.removeContractBackup(contract.getId());
			ids = request.getParameter("ids");
			if(StringUtils.isNotBlank(ids)){
				idArray = ids.split(",");
				if(idArray!=null&&idArray.length>0){
					for (String id : idArray) {
						if(StringUtils.isNotBlank(id)){
							flag = contractBackupService.removeContractBackup(Integer.valueOf(id));
							logger.info("删除主键id："+id+"结果："+flag);
						}
					}
				}
			}
			logger.info("删除结果"+flag);
			
		} catch (Exception e) {
			logger.error("删除数据时出现异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转到更新合同页面
	 * @return
	 */
	public String updatePage(){
		
		HttpServletRequest request = this.getRequest();
		int contractStatus ;
		ContractBackup contractInDB = null;
		String positionList = "";
		String contractContractADRelationListStr="";
		List<ContractContractADRelation> contractContractADRelationList = null;
		try {
			//获取表单中的相关数据
			if(contract!=null){
				contractStatus=contract.getStatus().intValue();
				//待审核状态 从 备份表中获取数据
				if(Constant.CONTRACT_AUDIT_STATUS_WAIT==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
					//审核通过状态	 从 运行期表中获取数据
				}else if(Constant.CONTRACT_AUDIT_STATUS_PASS==contractStatus){
					//contractInDB=contractRunService.getContractByContractId(contract.getId());
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_NO_PASS==contractStatus){
					//contractInDB=contractRunService.getContractByContractId(contract.getId());
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_OFFLINE==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				}
				request.setAttribute("contractAd",contractContractADRelationListStr);
				request.setAttribute("contract",contractInDB);
				request.setAttribute("positionList",positionList);
				request.setAttribute("updateFlag","update");
				request.setAttribute("saveOrUpdateFlag","update");
			}else{
				log.info("获取信息失败");
			}
		} catch (Exception e) {
			request.setAttribute("formEnable","false");
			logger.error("获取合同信息时出现异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 跳转到审核合同进入页面
	 * @return
	 */
	public String approvalContractPage(){
		
		HttpServletRequest request = this.getRequest();
		int contractStatus ;
		ContractBackup contractInDB = null;
		String positionList = "";
		String contractContractADRelationListStr="";
		List<ContractContractADRelation> contractContractADRelationList = null;
		try {
			//获取表单中的相关数据
			if(contract!=null){
				contractStatus=contract.getStatus().intValue();
				//待审核状态 从 备份表中获取数据
				if(Constant.CONTRACT_AUDIT_STATUS_WAIT==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
					//审核通过状态	 从 运行期表中获取数据
				}else if(Constant.CONTRACT_AUDIT_STATUS_PASS==contractStatus){
					//contractInDB=contractRunService.getContractByContractId(contract.getId());
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_OFFLINE==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				}
				request.setAttribute("contractAd",contractContractADRelationListStr);
				request.setAttribute("contract",contractInDB);
				request.setAttribute("positionList",positionList);
				request.setAttribute("updateFlag","update");
				request.setAttribute("formEnable", "false");
				
			}else{
				log.info("获取信息失败");
			}
		} catch (Exception e) {
			logger.error("获取合同信息时出现异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 跳转入增加合同页面中
	 * @return
	 */
	public String addPage(){
		this.getRequest().setAttribute("saveOrUpdateFlag","save");
		return SUCCESS;
	}
	
	/**
	 * 查看合同详情
	 * @return
	 */
	public String viewPage(){
		HttpServletRequest request = this.getRequest();
		int contractStatus ;
		ContractBackup contractInDB = null;
		String positionList = "";
		try {
			//获取表单中的相关数据
			if(contract!=null){
				contractStatus=contract.getStatus().intValue();
				//待审核状态 从 备份表中获取数据
				if(Constant.CONTRACT_AUDIT_STATUS_WAIT==contractStatus){
					
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				//审核通过状态	 从 运行期表中获取数据
				}else if(Constant.CONTRACT_AUDIT_STATUS_PASS==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_NO_PASS==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_OFFLINE==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				}
				request.setAttribute("formEnable","false");
				request.setAttribute("contract",contractInDB);
				request.setAttribute("positionList",positionList);
				request.setAttribute("updateFlag","update");
			}else{
				log.info("获取信息失败");
			}
		} catch (Exception e) {
			logger.error("获取合同信息时出现异常",e);
		}
		return SUCCESS;
	}
	
	public static ContractBackup  parseJson(String jsonString){
		ContractBackup contract = null;
		
		if(StringUtils.isNotBlank(jsonString)){
			contract=JSON.toJavaObject(JSON.parseObject(jsonString), ContractBackup.class);
		}
		return contract;
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		String a = "{\"id\":\"\",\"contractNumber\":\"00003\",\"contractCode\":\"C00003\",\"customerId\":\"88\",\"customerName\":\"方法\",\"contractName\":\"C00003名称\",\"submitUnits\":\"C00003送审单位\",\"financialInformation\":\"C00003合同金额\",\"approvalCode\":\"C00003审批文号\",\"metarialPath\":\"\",\"effectiveStartDate\":1363589846000,\"effectiveEndDate\":1363589849000,\"approvalStartDate\":1363589851000,\"approvalEndDate\":1363589854000,\"effectiveStartDateShow\":\"2013-03-18 14:57:26\",\"effectiveEndDateShow\":\"2013-03-18 14:57:29\",\"approvalStartDateShow\":\"2013-03-18 14:57:31\",\"approvalEndDateShow\":\"2013-03-18 14:57:34\",\"otherContent\":\"C00003其他内容\",\"status\":\"\",\"contractDesc\":\"C00003备注描述\",\"bindingPosition\":[{\"id\":\"99\",\"characteristicIdentification\":\"hd_0001\",\"positionName\":\"名称\",\"categoryId\":\"\",\"positionTypeId\":90,\"positionTypeName\":\"类型一\",\"deliveryPlatform\":\"UNT\",\"textRuleId\":\"134\",\"videoRuleId\":\"61\",\"imageRuleId\":\"81\",\"questionRuleId\":\"134\",\"isLoop\":\"1\",\"deliveryMode\":\"0\",\"isHd\":\"1\",\"isAdd\":\"0\",\"materialNumber\":\"0\",\"coordinate\":\"220,350\",\"price\":\"0001价格\",\"discount\":\"0.1元\",\"backgroundPath\":\"images/position/1361090090735.jpg\",\"startTime\":\"01:00:00\",\"endTime\":\"23:59:59\",\"validStartDateShow\":\"2013-03-18 14:56:29\",\"validEndDateShow\":\"2013-03-18 14:56:31\",\"validStartDate\":1363589789000,\"validEndDate\":1363589791000,\"chooseMarketRules\":\"12,\",\"marketRules\":[{\"id\":12,\"ruleName\":\"a12\",\"startTime\":0,\"endTime\":0,\"positionName\":\"名称\",\"areaName\":\"安阳\",\"channelName\":\"摄影\",\"state\":1,\"chooseState\":\"false\"}],\"chooseMarketRulesElement\":\"#alreadyChooseRule0\",\"currentIndex\":0,\"chooseState\":\"false\"},{\"id\":\"1\",\"characteristicIdentification\":\"sd_0001\",\"positionName\":\"名称1\",\"categoryId\":\"\",\"positionTypeId\":90,\"positionTypeName\":\"类型一\",\"deliveryPlatform\":\"UNT\",\"textRuleId\":\"134\",\"videoRuleId\":\"61\",\"imageRuleId\":\"81\",\"questionRuleId\":\"134\",\"isLoop\":\"1\",\"deliveryMode\":\"0\",\"isHd\":\"1\",\"isAdd\":\"0\",\"materialNumber\":\"0\",\"coordinate\":\"220,350\",\"price\":\"0001价格\",\"discount\":\"0.1元\",\"backgroundPath\":\"images/position/1361090090735.jpg\",\"startTime\":\"01:00:00\",\"endTime\":\"23:59:59\",\"validStartDateShow\":\"2013-03-18 14:56:33\",\"validEndDateShow\":\"2013-03-18 14:56:35\",\"validStartDate\":1363589793000,\"validEndDate\":1363589795000,\"chooseMarketRules\":\"7,\",\"marketRules\":[{\"id\":7,\"ruleName\":\"a7\",\"startTime\":0,\"endTime\":0,\"positionName\":\"名称1\",\"areaName\":\"安阳\",\"channelName\":\"摄影\",\"state\":1,\"chooseState\":\"false\"}],\"chooseMarketRulesElement\":\"#alreadyChooseRule1\",\"currentIndex\":1,\"chooseState\":\"false\"}]}";
		System.out.println(parseJson(a));	
	}

	public String getContractParam() {
		return contractParam;
	}

	public void setContractParam(String contractParam) {
		this.contractParam = contractParam;
	}

	public String getContractParamBefore() {
		return contractParamBefore;
	}

	public void setContractParamBefore(String contractParamBefore) {
		this.contractParamBefore = contractParamBefore;
	}

	public String getComparedFormResult() {
		return comparedFormResult;
	}

	public void setComparedFormResult(String comparedFormResult) {
		this.comparedFormResult = comparedFormResult;
	}

	public String getContractAd() {
		return contractAd;
	}

	public void setContractAd(String contractAd) {
		this.contractAd = contractAd;
	}

	public PageBeanDB getContractPage() {
		return contractPage;
	}

	public void setContractPage(PageBeanDB contractPage) {
		this.contractPage = contractPage;
	}

	public ContractManagerService getContractManagerService() {
		return contractManagerService;
	}

	public void setContractManagerService(
			ContractManagerService contractManagerService) {
		this.contractManagerService = contractManagerService;
	}

	public Integer getAudited() {
		return audited;
	}

	public void setAudited(Integer audited) {
		this.audited = audited;
	}
	
	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	
	

	public String getIsAuditTag() {
        return isAuditTag;
    }

    public void setIsAuditTag(String isAuditTag) {
        this.isAuditTag = isAuditTag;
    }
    

    public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	

	public float getAccountsAmount() {
		return accountsAmount;
	}

	public void setAccountsAmount(float accountsAmount) {
		this.accountsAmount = accountsAmount;
	}

	//	public String getOperType() {
//		return operType;
//	}
//
//	public void setOperType(String operType) {
//		this.operType = operType;
//	}
//
//	public int getOperResult() {
//		return operResult;
//	}
//
//	public void setOperResult(int operResult) {
//		this.operResult = operResult;
//	}
//
//	public String getOperInfo() {
//		return operInfo;
//	}
//
//	public void setOperInfo(String operInfo) {
//		this.operInfo = operInfo;
//	}
//
	public OperateLog getOperLog() {
		return operLog;
	}

	public void setOperLog(OperateLog operLog) {
		this.operLog = operLog;
	}
	

	public OperateLogService getOperateLogService() {
		return operateLogService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public String checkPostitionUsed()
	{
		if (contractId==null)
		{
			contractId=0;
		}
		if (contractManagerService.checkPostitionUsed(contractId, positionId, startDate, endDate))
		{
			print("1");
		}
		else
		{
			print("0");
		}
		return NONE;
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
			System.out.println("send response error");
		}
	}
	
	public  String exportContractBatch(){
        try{
            dataIds = dataIds.replace(" ","");  
            String idArr[] = dataIds.split(",");
            
            File f = new File(".././ContractBatch.zip");
            f.createNewFile();
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
            
            for (int i = 0; i < idArr.length; i++) {
                Long id = Long.parseLong(idArr[i].trim());
                contractId=Integer.valueOf(id.toString());
                exportContract();
                ContractBackup temp = contractManagerService.getContractByID(contractId);
                                                 
                    out.putNextEntry(new ZipEntry(temp.getContractName()+".xls")); 
                    out.setEncoding("GBK");
                    //InputStream in =new ByteArrayInputStream(xml.getBytes("UTF-8"));
                    InputStream in = new FileInputStream(".././contract.xls");
                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                    }
                    in.close();

            }
            out.flush();
            out.close();
        }
        
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "success";
    }
	
	public InputStream getInputStreamForBatch() {
        InputStream is = null;
        File tempfile =null;
        
            try {
                tempfile = new File(".././ContractBatch.zip");     
                is = new FileInputStream(tempfile);
            } catch (Exception e) {
                log.error("", e);
            } 
//            finally{                    
//                if(tempfile.exists()){
//                    tempfile.delete();
//                    if(tempfile.exists()){
//                        System.out.println("------删除临时文件失败-------");
//                    }else{
//                        System.out.println("------删除打包产生的临时文件------");
//                    }
//                }
//            }
         
        if(is == null){
            is = new ByteArrayInputStream("".getBytes());
        }

        return is;
    }


    public String getFileNameForBatch() {
        Date dateTemp = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String str = "contractBatch"+df.format(dateTemp);

        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            log.error("", e);
            str = "contractBatch"+df.format(dateTemp);
        }

        return str + ".zip";
    }
	
	public  String exportContract(){    
	    ContractBackup temp = contractManagerService.getContractByID(contractId);
	    List<PositionAD> positionADList = contractManagerService.getPositionADByContractID(contractId);
	    List<ContractAccounts> contractAccountsList = contractManagerService.getContractAccountsList(contractId);
	    int rowNum=9+positionADList.size()+3+contractAccountsList.size();
	    WritableWorkbook wwb = null;    
        try {    
            //首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象    
            //wwb = Workbook.createWorkbook(new File(".././"+temp.getContractName()+".xls"));
            wwb = Workbook.createWorkbook(new File(".././contract.xls"));
            WritableSheet dataSheet = wwb.createSheet("加入页眉",0);   
            ExcelUtils.setHeader(dataSheet, "chb", "2007-03-06", "第1页,共3页");   

        } catch (IOException e) {    
            e.printStackTrace();    
        }    
        if(wwb!=null){    
            //创建一个可写入的工作表    
            //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置    
            WritableSheet ws = wwb.createSheet("sheet1", 0);    
                
            //下面开始添加单元格    
            for(int i=0;i<rowNum;i++){    
                for(int j=0;j<7;j++){    
                    //这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行    
                    //Label labelC = new Label(j, i, "这是第"+(i+1)+"行，第"+(j+1)+"列");
                    Label labelC=null;
                    if(i==0&&j==0){
                        labelC = new Label(j, i, "合同基本信息");
                    }
                    if(i==1){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "合同名称:");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, temp.getContractName());
                            break;
                        case 5:
                            labelC = new Label(j, i, "合同编号:");
                            break;              
                        case 6:             
                            labelC = new Label(j, i, temp.getContractNumber());
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    if(i==2){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "合同号:");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, temp.getContractCode());
                            break;
                        case 5:
                            labelC = new Label(j, i, "广告商:");
                            break;              
                        case 6:             
                            labelC = new Label(j, i, temp.getCustomerName());
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    if(i==3){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "合同金额:");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, temp.getFinancialInformation()+"万元");
                            break;
                        case 5:
                            labelC = new Label(j, i, "广告审批文号:");
                            break;              
                        case 6:             
                            labelC = new Label(j, i, temp.getApprovalCode());
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    if(i==4){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "合同开始日期:");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, temp.getEffectiveStartDate().toString().substring(0, 10));
                            break;
                        case 5:
                            labelC = new Label(j, i, "合同截止日期:");
                            break;              
                        case 6:             
                            labelC = new Label(j, i, temp.getEffectiveEndDate().toString().substring(0, 10));
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    if(i==5){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "合同执行开始日期:");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, temp.getApprovalStartDate().toString().substring(0, 10));
                            break;
                        case 5:
                            labelC = new Label(j, i, "合同执行结束日期:");
                            break;              
                        case 6:             
                            labelC = new Label(j, i, temp.getApprovalEndDate().toString().substring(0, 10));
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    if(i==6){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "送审单位:");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, temp.getSubmitUnits());
                            break;
                        case 5:
                            labelC = new Label(j, i, "描述:");
                            break;              
                        case 6:             
                            labelC = new Label(j, i, temp.getContractDesc());
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    if(i==7&&j==0){
                        labelC = new Label(j, i, "已绑定广告位");
                    }
                    if(i==8){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "序号");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, "广告位名称");
                            break;
                        case 2:
                            labelC = new Label(j, i, "广告位类型");
                            break;              
                        case 3:             
                            labelC = new Label(j, i, "投放方式");
                            break;
                        case 4:             
                            labelC = new Label(j, i, "高标清标识");
                            break;
                        case 5:             
                            labelC = new Label(j, i, "投放开始日期");
                            break;
                        case 6:             
                            labelC = new Label(j, i, "投放结束日期");
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    if(positionADList.size()>0){
                        for(int k=0;k<positionADList.size();k++){
                            if(i==8+(k+1)){
                                switch(j){ 
                                case 0:
                                    labelC = new Label(j, i, Integer.toString(i-8));
                                    break;                                                      
                                case 1:
                                    labelC = new Label(j, i,positionADList.get(k).getPositionName() );
                                    break;
                                case 2:
                                    Integer packageType = positionADList.get(k).getPackageType();
                                    if(packageType==0){
                                        labelC = new Label(j, i, "双向实时广告");
                                    }
                                    if(packageType==1){
                                        labelC = new Label(j, i, "双向实时请求广告");
                                    }
                                    if(packageType==2){
                                        labelC = new Label(j, i, "单向实时广告");
                                    }
                                    if(packageType==3){
                                        labelC = new Label(j, i, "单向非实时广告");
                                    }
                                    
                                    break;              
                                case 3:      
                                    Integer deliveryMode = positionADList.get(k).getDeliveryMode();
                                    if(deliveryMode==0){
                                        labelC = new Label(j, i, "投放式");
                                    }else{
                                        labelC = new Label(j, i, "请求式");
                                    }                                 
                                    break;
                                case 4:                                                
                                    Integer videoType = positionADList.get(k).getVideoType();
                                    if(videoType==0){
                                           labelC = new Label(j, i, "只支持标清");      
                                        }else if(videoType==1){
                                           labelC = new Label(j, i, "只支持高清");
                                       }else{
                                           labelC = new Label(j, i, "高清标清都支持");
                                       }
                                    break;
                                case 5:             
                                    labelC = new Label(j, i, positionADList.get(k).getPositionStartDate().toString().substring(0, 10));
                                    break;
                                case 6:             
                                    labelC = new Label(j, i, positionADList.get(k).getPositionEndDate().toString().substring(0, 10));
                                    break;
                                
                                
                                default:
                                    break;
                                }
                            }
                        }
                    }
                    int s =8+positionADList.size()+1;
                    if(i==s&&j==0){
                        labelC = new Label(j, i, "台帐信息");
                    }
                    if(i==s+1){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "序号");
                            break;                                                      
                        case 1:
                            labelC = new Label(j, i, "台账单号");
                            break;
                        case 2:
                            labelC = new Label(j, i, "付款方式");
                            break;              
                        case 3:             
                            labelC = new Label(j, i, "付款金额（万元）");
                            break;
                        case 4:             
                            labelC = new Label(j, i, "付款时间");
                            break;
                        case 5:             
                            labelC = new Label(j, i, "有效开始日期");
                            break;
                        case 6:             
                            labelC = new Label(j, i, "有效结束日期");
                            break;
                        
                        
                        default:
                            break;
                        }
                    }
                    s=s+1;
                    
                    if(contractAccountsList.size()>0){
                        for(int kk=0;kk<contractAccountsList.size();kk++){
                            if(i==s+(kk+1)){
                                switch(j){ 
                                case 0:
                                    labelC = new Label(j, i, Integer.toString(i-s));
                                    break;                                                      
                                case 1:
                                    labelC = new Label(j, i,contractAccountsList.get(kk).getAccountsId().toString() );
                                    break;
                                case 2:
                                    Integer paySort = contractAccountsList.get(kk).getPaySort();
                                    if(paySort==1){
                                        labelC = new Label(j, i, "按月付款");
                                    }
                                    if(paySort==2){
                                        labelC = new Label(j, i, "按季度付款");
                                    }                                   
                                    break;              
                                case 3:      
                                    labelC = new Label(j, i,contractAccountsList.get(kk).getMoneyAmount().toString() );                                                                  
                                    break;
                                case 4:           
                                    labelC = new Label(j, i,contractAccountsList.get(kk).getPayDay().toString().substring(0, 10) );
                                    break;
                                case 5:             
                                    labelC = new Label(j, i, contractAccountsList.get(kk).getPayVallidityPeriodBegin().toString().substring(0, 10));
                                    break;
                                case 6:             
                                    labelC = new Label(j, i, contractAccountsList.get(kk).getPayVallidityPeriodEnd().toString().substring(0, 10));
                                    break;
                                
                                
                                default:
                                    break;
                                }
                            }
                        }
                    }
                    accountsAmount = contractManagerService.getAccountsAmount(contractId);
                    if(i==s+contractAccountsList.size()+1){
                        switch(j){ 
                        case 0:
                            labelC = new Label(j, i, "合计：");
                            break;                                                                   
                        case 3:             
                            labelC = new Label(j, i, accountsAmount+"万元");
                            break;
                        
                        
                        default:
                            break;
                        }
                    }

                    
                   
                    
                    try {    
                        //将生成的单元格添加到工作表中    
                        if(labelC==null){
                            labelC=new Label(j, i, "");
                        }
                        ws.addCell(labelC);    
                    } catch (RowsExceededException e) {    
                        e.printStackTrace();    
                    } catch (WriteException e) {    
                        e.printStackTrace();    
                    }    
   
                }    
            }    
   
            try {    
                //从内存中写入文件中    
                wwb.write();    
                //关闭资源，释放内存    
                wwb.close(); 
            } catch (IOException e) {    
                e.printStackTrace();    
            } catch (WriteException e) {    
                e.printStackTrace();    
            }    
        }    
        return "success";
    }    
	
	public InputStream getInputStreamu()  {
	    ContractBackup temp = contractManagerService.getContractByID(contractId);
	    InputStream is = null;	
            try {
                //is = new FileInputStream(".././"+temp.getContractName()+".xls");
                is = new FileInputStream(".././contract.xls");
            } catch (Exception e) {
                log.error("", e);
            }

            
            
        if(is == null){
            is = new ByteArrayInputStream("".getBytes());
        }

        
        return is;
    }


    public String getFileName() {
        ContractBackup temp = contractManagerService.getContractByID(contractId);
        String str = temp.getContractName();

        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            log.error("", e);
            str = "contract";
        }

        return str + ".xls";
    }


/**向Excel中加入页眉页脚  
* @param dataSheet 待加入页眉的工作表  
* @param left  
* @param center  
* @param right  
*/  
public static void setHeader(WritableSheet dataSheet,String left,String center,String right){   
HeaderFooter hf = new HeaderFooter();   
hf.getLeft().append(left);   
hf.getCentre().append(center);   
hf.getRight().append(right);   
//加入页眉   
dataSheet.getSettings().setHeader(hf);   
//加入页脚   
//dataSheet.getSettings().setFooter(hf);   
}
}
