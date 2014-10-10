package com.dvnchina.advertDelivery.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.CustomerConstant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.ContractRun;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.CustomerBackUp;
import com.dvnchina.advertDelivery.service.ContractBackupService;
import com.dvnchina.advertDelivery.service.ContractRunService;
import com.dvnchina.advertDelivery.service.CustomerService;
import com.dvnchina.advertDelivery.service.common.tools.support.OperatorLocalFileService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.file.FileOperate;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

@SuppressWarnings("deprecation")
public class CustomerAction extends BaseActionSupport<Object> {

	private static final long serialVersionUID = 1L;
	
	private OperateLogService operateLogService = null;
	
	
	private Logger logger = Logger.getLogger(CustomerAction.class);
	private String cId;
	private Customer customer;
	private CustomerBackUp customerBackUp;
	
	private List<CustomerBackUp> listCustomers;
	private CustomerService customerService;
	private List<Customer> list;
	private ContractBackupService contractBackupService;
	private ContractBackup contractBackup;
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	// 文件标题
	private String title;
/*	// 用File数组来封装多个上传文件域对象
	private File[] upload;
	 // 用String数组来封装多个上传文件名
	private String[] uploadFileName; 
	// 用String数组来封装多个上传文件类型
	private String[] uploadcontentType;*/
	
	
	
//---------------------------------------------------------------------------
	
	/**
	 * 保存上传合同文件的目录，相对于Web应用程序的根路径，在struts.xml中配置
	 */
	private String uploadDir; 
	
	/**
	 * 保存上传营业执照文件的目录，相对于Web应用程序的根路径，在struts.xml中配置
	 */
	private String uploadBusinessDir;
	
	/** 
	 * 操作本地文件
	 */
	private OperatorLocalFileService operatorLocalFileService;
	
	private File backgroundImage;
	
	private String backgroundImageFileName;
	
	
	
 //-----------------------------------------------------------------------------
	
	
	//运行期合同
	private ContractRunService contractRunService;
	
	/**
	 * 页号
	 */
	protected int pageNo;
	/**
	 * 每页记录数
	 */
	protected int pageSize;
	
	
	/**
	 * 上传合同扫描件存放的目录 
	 */
	private static final String CONTRACT_FILE_PATH = config.getValueByKey("ftpUploadContractScan");
	
	/**
	 * 上传营业执照到存放的目录
	 */
	private static final String BUSINESS_FILE_PATH = config.getValueByKey("ftpUploadBusinessLicence");
	
	private static String _SEPARATOR = File.separator;
	
	private PageBeanDB pageCustomer;
	
	
	/**
	 * 
	 * 上传营业执照扫描件
	 * 
	 */
	public String uploadBusiness(){
		try {
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadBusinessDir);
			Map param = new HashMap();
			param.put("targetDirectory",uploadBusinessDir);
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        String ss = df.format(new Date());
	        backgroundImageFileName=ss+backgroundImageFileName.substring(backgroundImageFileName.lastIndexOf("."));
	     	
			String result = operatorLocalFileService.copyLocalFileTargetDirectory(this.backgroundImage.getAbsolutePath(), filePath,backgroundImageFileName,param);
			
			
			logger.info("path="+this.backgroundImage.getAbsolutePath());
			
			
			renderHtml(result);
		} catch (Exception e) {
			logger.error("上传背景图出现异常",e);
		}
		return null;
	}
	
	/**
	 * 
	 * 上传合同扫描件图片
	 */
	
	public String uploadContract(){
		
		try {
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
			Map param = new HashMap();
			param.put("targetDirectory",uploadDir);
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        String ss = df.format(new Date());
	        backgroundImageFileName=ss+backgroundImageFileName.substring(backgroundImageFileName.lastIndexOf("."));
	       String result = operatorLocalFileService.copyLocalFileTargetDirectory(this.backgroundImage.getAbsolutePath(), filePath,backgroundImageFileName,param);
			renderHtml(result);
		} catch (Exception e) {
			logger.error("上传背景图出现异常",e);
		}
		
		return null;
	}
	
	/**
	 * 确认删除广告商
	 * 
	 */
	public String submitDeleteCustomerInfo(){
		try
		{
			int count = 0;
			String ids = this.getRequest().getParameter("ids");
			
			String id[] = ids.split(",");
			
			if(!"null".equals(ids) && !"".equals(ids)){
				for(int i=0;i<id.length;i++){
					if(!"".equals(id[i]) && id[i] != null){
						count = customerService.deleteCustomerBackUpById(Integer.parseInt(id[i]));
						if (count > 0) {
							logger.debug("删除用户成功，用户id为：" + id);
						} else {
							logger.warn("删除用户时发生异常，用户id为：" + id);
						}
					}
				}
			}
			this.returnMessage("删除客户成功");
			message = "common.delete.success";//保存成功
		}catch(Exception e){
			message = "common.delete.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
		}
		finally
		{
			operInfo = "删除广告商：ids="+ids;
			operType = "operate.delete";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_CUSTOMER);
			operateLogService.saveOperateLog(operLog);
		}
		return null;
	}
	
	
	
	
	
	/**
	 * 批量删除广告商
	 * 
	 */
	
	public String deleteCustomerBatch(){
		int count = 0;
		int result = 0;
		int userId = 0;
		 
		List<ContractBackup> listContractBackupByCustomerId = new ArrayList<ContractBackup>();
		
		String ids = this.getRequest().getParameter("ids");
		
		String id[] = ids.split(",");
		result = CustomerConstant.CUSTOMER_DETELE;
		if(!"null".equals(ids) && !"".equals(ids)){
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i] != null){
					
					int flag = customerService.checkDelCustomerBackUpById(Integer.parseInt(id[i]));
					if (flag!=0)
					{
					   result = CustomerConstant.CUSTOMER_DETELE_ERROR;
					   logger.info("成功！");
					   break;
					}
					else
					{
						
					}
					/*CustomerBackUp customerBackUp = customerService.getCustomerBackUpById(Integer.parseInt(id[i]));
				   Integer customerId = customerBackUp.getId();
				   String status = customerBackUp.getStatus();
				   //add by rengm
				    //查询运行期表是否有对应记录，如无下一步，否则提示不能删除
				    //查询合同维护期表，如无广告商对应的合同，则下一步，否则提示不能删除（即使合同已完成也不允许删）  
				   
				   //end add
				   
				   //未审核 和 审核不通过状态 可以删除;
				   //先判断审核通过且有合同的广告商
				   if(status.equals(CustomerConstant.CUSTOMER_AUDIT_PASS) ){
					//   listContractBackupByCustomerId = contractBackupService.getContractBackupById(customerId);
					     listContractBackupByCustomerId = customerService.getContractContractBackByCustomerId(customerId);
					   //审核通过且绑定了合同，不能删除
					   if(listContractBackupByCustomerId != null && listContractBackupByCustomerId.size()>0){
						   result = CustomerConstant.CUSTOMER_DETELE_ERROR;
						   System.out.println("成功！");
						   break;
					   }else{
						   //审核通过未绑定的合同，可以删除
						 //  result = CustomerConstant.CUSTOMER_DETELE;
						   System.out.println("审核通过，");
						   break;
					   }
				   }*/
				   
				/*   if(status.equals(CustomerConstant.CUSTOMER_AUDIT_NOT_PASS) || status.equals(CustomerConstant.CUSTOMER_AUDIT_NO_PASS)){
					   result = CustomerConstant.CUSTOMER_DETELE;
					   break;
					//审核不通过
				   }else{
					   listContractBackupByCustomerId = contractBackupService.getContractBackupById(customerId);
					   //审核通过且绑定了合同，不能删除
					   if(listContractBackupByCustomerId != null && listContractBackupByCustomerId.size()>0){
						   result = CustomerConstant.CUSTOMER_DETELE_ERROR;
						   break;
					   }else{
						//审核通过未绑定的合同，可以删除
						   result = CustomerConstant.CUSTOMER_DETELE;
						   break;
					   }
				   }*/
				}
			}
		}
		//符合要求的的付给产出状态。
		
		String json  = "{\"result\":"+result+",\"userId\":"+userId+"}";
		renderJson(json);
		return NONE;
	}
	
	
	
	
	
	
	/**
	 * 上传图片
	 */
	/*public void updateResource(String clientCode){
		
		//得到跟路径
		 String  pathStr = this.getRequest().getSession().getServletContext().getRealPath("/");
		
	   //判断合同号文件夹是否存在
	//	File contractDesDir = new File(pathStr+"uploadFiles\\contractScanFiles\\"+clientCode);
		 File contractDesDir = new File(pathStr+"uploadFiles\\contractScanFiles\\");
		if(!contractDesDir.exists()){
			contractDesDir.mkdirs();
		}
		
		//判断营业执照文件夹是否存在
		//File businessDesDir = new File(pathStr+"uploadFiles\\businessLicence\\"+clientCode);
		File businessDesDir = new File(pathStr+"uploadFiles\\businessLicence\\");
		if(!businessDesDir.exists()){
			businessDesDir.mkdirs();
		}
		
		 // String webPath = ServletActionContext.getServletContext().getRealPath("uploadFiles");
		  String contractPath = "uploadFiles\\contractScanFiles";
		  String businessPath = "uploadFiles\\businessLicence";
		  
		  for(int i = 0; i <2; i++){
			//  String path = webPath + "\\"+this.getUploadFileName()[i];
			  if(i==0){
				  //保存营业执照照片
				  customerBackUp.setBusinessLicence(businessPath+"\\"+this.getUploadFileName()[i]);
				  FileOperate.copyFile2FileSize(this.upload[i],new File(contractDesDir+"\\"+this.getUploadFileName()[i]));
				  String path = contractDesDir+"\\"+this.getUploadFileName()[i];
				 
				  this.getRequest().setAttribute("contractDesDirPic",path);
				  
				//  String pathPic = this.getRequest().getParameter("contractDesDirPic");
				  
				  System.out.println("contractDesDirPic="+path);
				  
			  }else{
				  //保存合同照片
				  customerBackUp.setContract(contractPath+"\\"+this.getUploadFileName()[i]);
				  FileOperate.copyFile2FileSize(this.upload[i],new File(businessDesDir+"\\"+this.getUploadFileName()[i]));
				  String path = businessDesDir+"\\"+this.getUploadFileName()[i];
				  this.getRequest().setAttribute("businessDesDirPic",path );
				  
				  System.out.println("businessDesDirPic="+path);
				  
			  }
		     // FileOperate.copyFile2FileSize(this.upload[i],new File(contractDesDir+"\\"+this.getUploadFileName()[i]));
		  }
		
	}*/
	
	/**
	 * 得到广告商详情(审核页面)
	 * 
	 */
	public String getCustomerAuditInfo(){
		
		logger.debug("----getCustomerAuditInfo 被调用----");
		Integer id = new Integer(cId.trim());
		if (id != null && !"".equals(id)) {
			customerBackUp = customerService.getCustomerBackUpById(id);
		}
		this.getRequest().setAttribute("customerBackUp", customerBackUp);
		String ip=config.getValueByKey("ftp.ip");
	    String viewpath=config.getValueByKey("materila.ftp.tempPath");
	    viewpath=viewpath.substring(5, viewpath.length());
	    String viewPath="http://"+ip+viewpath;
	    this.getRequest().setAttribute("viewPath", viewPath);
		logger.debug("----getCustomerAuditInfo 被调用结束了-----");
		return "customerAuditInfo";
	}
	
	/**
	 * 得到广告商详情
	 * 
	 */
	public String getCustomerInfo(){
		
		logger.debug("----getCustomerInfo 被调用----");
		Integer id = new Integer(cId.trim());
		if (id != null && !"".equals(id)) {
			customerBackUp = customerService.getCustomerBackUpById(id);
		}
		this.getRequest().setAttribute("customerBackUp", customerBackUp);
		String ip=config.getValueByKey("ftp.ip");
	    String viewpath=config.getValueByKey("materila.ftp.tempPath");
	    viewpath=viewpath.substring(5, viewpath.length());
	    String viewPath="http://"+ip+viewpath;
	    this.getRequest().setAttribute("viewPath", viewPath);
		logger.debug("----getCustomerInfo 被调用结束了-----");
		return "customerInfo";
	}
	
	public ContractBackup getContractBackup() {
		return contractBackup;
	}
	public void setContractBackup(ContractBackup contractBackup) {
		this.contractBackup = contractBackup;
	}

	/**
	 * 广告商通过审核
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	
	public String insertGoAuditCustomer() throws UnsupportedEncodingException{
		logger.debug("----审核广告商 方法 insertGoAuditCustomer()被调用------");
		String id ="";
		try
		{
			int count = 0;
			id= this.getRequest().getParameter("cId");
			
			//审核意见
			String examinationOpintions = this.getRequest().getParameter("examinationOpinions");
			
			if(id != null && !"".equals(id)){
				id = id.trim();
				examinationOpintions = examinationOpintions.trim();
				
				//examinationOpintions = new String(examinationOpintions.getBytes("ISO8859-1"),"UTF-8");
				
				count = customerService.insertAuditCustomer(Integer.parseInt(id),examinationOpintions);
				
				if(count > 0){
					logger.debug("广告商审核通过，id为："+id);
				}else{
					logger.debug("审核广告商时发生异常，图片id为："+id);
				}
				
			}else{
				logger.debug("CustomerBackUp 的id 为空");
			}
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
		}
		finally
		{
			operInfo = "广告商ID："+id;
			operType = "operate.auditok";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_CUSTOMER);
			operateLogService.saveOperateLog(operLog);
		}
		logger.debug("审核广告商 方法 insertGoAuditCustomer()调用结束");
		
		return "AuditCustomer";
	}
	
	
	/**
	 * 广告商没有通过审核
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	
	public String insertNoAuditCustomer() throws UnsupportedEncodingException{
		logger.debug("----审核广告商 方法 insertNoAuditCustomer()被调用------");
		String id="" ;
		try
		{
			int count = 0;
			id = this.getRequest().getParameter("cId");
			
			//审核意见
			String examinationOpintions = this.getRequest().getParameter("examinationOpinions");
			
			if(id != null && !"".equals(id)){
				id = id.trim();
				examinationOpintions = examinationOpintions.trim();
				
				//examinationOpintions = new String(examinationOpintions.getBytes("ISO8859-1"),"UTF-8");
				
				count = customerService.insertNoAuditCustomer(Integer.parseInt(id),examinationOpintions);
				
				if(count > 0){
					logger.debug("广告商审核操作正常，id为："+id);
				}else{
					logger.debug("审核广告商时发生异常，id为："+id);
				}
				
			}else{
				logger.debug("CustomerBackUp 的id 为空");
			}
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
		}
		finally
		{
			operInfo = "广告商ID："+id;
			operType = "operate.auditno";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_CUSTOMER);
			operateLogService.saveOperateLog(operLog);
		}
		logger.debug("审核广告商 方法 insertNoAuditCustomer()调用结束");
		
		return "AuditCustomer";
	}
	
	/**
	 * 
	 * 客户审核通过返回页面Json
	 * 
	 */
	
	public String auditCustomerBackUp(){
		
		
		String id = this.getRequest().getParameter("cId");
		
		customerBackUp = customerService.getCustomerBackUpById(Integer.parseInt(id));
		
		String remarkStr = customerBackUp.getRemark();
		
		
		if(remarkStr != null && !"".equals(remarkStr)){
			customerBackUp.setRemarkAudit(remarkStr);
		}else{
			customerBackUp.setRemarkAudit("无描述");
		}
		
		StringBuffer buf = new StringBuffer();
		if(customerBackUp != null && !"".equals(customerBackUp)){
			//StringBuffer buf = new StringBuffer("[");
			//资源id ，资源名称，资源类型，
			buf.append("[");
			buf.append("{id:").append(customerBackUp.getId()).append(
			",advertisersName:'").append(customerBackUp.getAdvertisersName()).append(
			"',clientCode:'").append(customerBackUp.getClientCode()).append("',communicator:'").append(customerBackUp.getCommunicator()).append("',remark:'").append(customerBackUp.getRemarkAudit()).append("'}");
		}
		buf.append("]");
		logger.info("##客户数据：" + buf.toString());
		//返回到页面上
		renderJson(buf.toString());
		return null;
	}
	
	
	/**
	 * 验证客户是否有合同在身
	 * 
	 */
	
	public String deleteAdvalidate(){
		
		String id = this.getRequest().getParameter("cId");
	//	List<ContractRun>  listContractRun = contractRunService.getContractRunByCustomerId(Integer.parseInt(id));
		/*if(listContractRun.size()>0){
			
			Integer ids = listContractRun.get(0).getCustomerId();
			
			
			this.returnMessage("1");
			return null;
		}else{
			customerService.deleteCustomerBackUpById(Integer.parseInt(id));
			this.returnMessage("0");
		}*/
		return null;
	}

	/**
	 * 验证客户代码是否 重复
	 * 
	 */
	public String saveValidateCustomer() {

		logger.debug("----saveValidateCustomer 被调用----");

		String clientCode = this.getRequest().getParameter("clientCode");

		if (customerBackUp == null) {
			customerBackUp = new CustomerBackUp();
		}
		
		List<CustomerBackUp> listCustomerBackUps = new ArrayList<CustomerBackUp>();
		
		listCustomerBackUps = customerService.getCustomerBackUpList();
		
		if (listCustomerBackUps.size() > 0) {
			for (int i = 0; i < listCustomerBackUps.size(); i++) {
				String clientC = listCustomerBackUps.get(i).getClientCode();
				if (clientCode.equals(clientC)) {
					this.returnMessage("1");
					return null;
				}
			}
		}
		this.returnMessage("0");
		return null;
	}

	/**
	 * 修改客户信息
	 */
	public String updateCustomerBackUp() {

		logger.debug("----updateCustomerBackUp 被调用----");
		try
		{
			int count = 0;
	
			if (customerBackUp == null && "".equals(customerBackUp)) {
				customerBackUp = new CustomerBackUp();
			}
			
			customerBackUp.setStatus(CustomerConstant.CUSTOMER_AUDIT_NOT_PASS);
			
			count = customerService.updateCustomerBackUp(customerBackUp);
			logger.info(ServletActionContext.getServletContext().getRealPath(customerBackUp.getContract()));
			String uploadAllfile = ServletActionContext.getServletContext().getRealPath(customerBackUp.getContract());
			
	        /**  发送文件至ftp */
	        boolean isSuccess = false;
	       
	        String uploadPath;
	        if (customerBackUp.getBusinessLicence()!=null && !"".equals(customerBackUp.getBusinessLicence()))
	        {
	        	uploadAllfile = ServletActionContext.getServletContext().getRealPath(customerBackUp.getBusinessLicence());
			 	uploadPath=config.getValueByKey("materila.ftp.tempPath")+"/"+customerBackUp.getBusinessLicence().substring(0,customerBackUp.getBusinessLicence().lastIndexOf("/"));
	            isSuccess = sendFile(uploadAllfile,uploadPath);			
		     }
	        /**  发送文件至ftp */
	        isSuccess = false;
	        if (customerBackUp.getContract()!=null && !"".equals(customerBackUp.getContract()))
	        {
	        	uploadAllfile = ServletActionContext.getServletContext().getRealPath(customerBackUp.getContract());
				uploadPath=config.getValueByKey("materila.ftp.tempPath")+"/"+customerBackUp.getContract().substring(0,customerBackUp.getContract().lastIndexOf("/"));
	        	isSuccess = sendFile(uploadAllfile,uploadPath);
	        }
	        
	        
	        /*
	        
	        String uploadPath=config.getValueByKey("materila.ftp.tempPath")+"/"+customerBackUp.getContract().substring(0,customerBackUp.getContract().lastIndexOf("/"));
	        isSuccess = sendFile(uploadAllfile,uploadPath);
	        uploadAllfile = ServletActionContext.getServletContext().getRealPath(customerBackUp.getBusinessLicence());
			
	        //  发送文件至ftp
	        isSuccess = false;
	        uploadPath=config.getValueByKey("materila.ftp.tempPath")+"/"+customerBackUp.getBusinessLicence().substring(0,customerBackUp.getBusinessLicence().lastIndexOf("/"));
	        isSuccess = sendFile(uploadAllfile,uploadPath);
			*/
	        if (count > 0) {
				logger.debug("更新用户成功");
			} else {
				logger.warn("更新用户时发生异常");
			}
			message = "common.update.success";//保存成功
		}catch(Exception e){
			message = "common.update.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
		}
		finally
		{
			operInfo = customerBackUp.toString();
			operType = "operate.update";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_CUSTOMER);
			operateLogService.saveOperateLog(operLog);
		}
		return SUCCESS;
	}

	/**
	 * 调转到修改页面
	 */
	public String updateCustomerBackUpRedirect() {

		logger.debug("----updateCustomerRedirect 被调用----");

		
		String contract="";
		
		String businessLicence="";
		
		Integer id = new Integer(cId.trim());

		if (id != null && !"".equals(id)) {
			customerBackUp = customerService.getCustomerBackUpById(id);
			contract = customerBackUp.getContract();
			businessLicence = customerBackUp.getBusinessLicence();
		}
		this.getRequest().setAttribute("customerBackUp", customerBackUp);

		logger.debug("----updateCustomerRedirect 被调用结束了-----");
		String ip=config.getValueByKey("ftp.ip");
	    String viewpath=config.getValueByKey("materila.ftp.tempPath");
	    viewpath=viewpath.substring(5, viewpath.length());
	    String viewPath="http://"+ip+viewpath;
	    this.getRequest().setAttribute("viewPath", viewPath);
		return "updateCustomer";
	}

	/**
	 * 保存客户信息
	 */
	public String insertCustomerBackUp(){
		try
		{
			String	clientCode = customerBackUp.getClientCode();
			Date date = new Date();
			String contract = customerBackUp.getContacts();
			String businessLicence = customerBackUp.getBusinessLicence();
			
			customerBackUp.setCreateTime(date);
			customerBackUp.setStatus(CustomerConstant.CUSTOMER_AUDIT_NOT_PASS);
			
			logger.info(ServletActionContext.getServletContext().getRealPath(customerBackUp.getContract()));
			String uploadAllfile = ServletActionContext.getServletContext().getRealPath(customerBackUp.getContract());
			
	        /**  发送文件至ftp */
	        boolean isSuccess = false;
	        String uploadPath;
	        if (customerBackUp.getBusinessLicence()!=null && !"".equals(customerBackUp.getBusinessLicence()))
	        {
	        	uploadAllfile = ServletActionContext.getServletContext().getRealPath(customerBackUp.getBusinessLicence());
			 	uploadPath=config.getValueByKey("materila.ftp.tempPath")+"/"+customerBackUp.getBusinessLicence().substring(0,customerBackUp.getBusinessLicence().lastIndexOf("/"));
	            isSuccess = sendFile(uploadAllfile,uploadPath);			
		     }
	        /**  发送文件至ftp */
	        isSuccess = false;
	        if (customerBackUp.getContract()!=null && !"".equals(customerBackUp.getContract()))
	        {
	        	uploadAllfile = ServletActionContext.getServletContext().getRealPath(customerBackUp.getContract());
				uploadPath=config.getValueByKey("materila.ftp.tempPath")+"/"+customerBackUp.getContract().substring(0,customerBackUp.getContract().lastIndexOf("/"));
	        	isSuccess = sendFile(uploadAllfile,uploadPath);
	        }
			int count = 0;
			if (customerBackUp != null && !"".equals(customerBackUp)) {
				count = customerService.insertCustomerBackUp(customerBackUp);
				if (count > 0) {
					logger.debug("添加用户成功");
				} else {
					logger.warn("添加用户时发生异常");
				}
			} else {
				logger.debug("customerBackUp 值 为空 ");
			}
			message = "common.add.success";//保存成功
		}catch(Exception e){
			message = "common.add.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
		}
		finally
		{
			operInfo = customerBackUp.toString();
			operType = "operate.add";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_CUSTOMER);
			operateLogService.saveOperateLog(operLog);
		}
		logger.debug("saveCustomerBackUp 被调用结束了");
		return SUCCESS;
	}

	/**
	 * 跳转到添加页面
	 */           
	public String saveCustomerBackUpRedirect() {
		return "saveCustomer";
	}

	/**
	 * 删除客户记录
	 */
	public String deleteCustomerBackUp() {
		
		try
		{
			String id = this.getRequest().getParameter("cId");
			int count = 0;
			if (id != null) {
			   //验证是否绑定了合同
				    //客户没有绑定合同，则直接删除
					count = customerService.deleteCustomerBackUpById(Integer.parseInt(id));
					
					if (count > 0) {
						logger.debug("删除用户成功，用户id为：" + id);
					} else {
						logger.warn("删除用户时发生异常，用户id为：" + id);
					}
					
				
			} else {
				logger.debug("----CustomerBackUp的id 值 为空 ");
			}
			logger.debug("-----deleteCustomerBackUp 被调用结束了----");
			// 返回客户端
			this.returnMessage("删除客户成功");
			message = "common.delete.success";//保存成功
		}catch(Exception e){
			message = "common.delete.fail";//保存失败
			operResult = Constant.OPERATE_FAIL;
		}
		finally
		{
			operInfo = customerBackUp.toString();
			operType = "operate.delete";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_CUSTOMER);
			operateLogService.saveOperateLog(operLog);
		}
		return null;
	}

	/**
	 * 通过资源表中客户id得到客户表中的所属合同号
	 * @return
	 */
	public List<CustomerBackUp> getClientCodeByCustomerId(Integer customerId){
		
		List<CustomerBackUp> listCustomerBackUp = new ArrayList<CustomerBackUp>();
		
		customerService.getClientCodeByCustomerId(customerId);
		
		return listCustomerBackUp;
	}
	
	
	
	
	/**
	 * 无过滤，返回整个客户表信息
	 * 
	 * @return
	 */
	public String getAllCustomer() {
		
		List<Customer> cl = new ArrayList<Customer>();

		cl = customerService.getCustomerList(null);
		
		if (null != cl) {
			String json = JSONArray.fromObject(cl).toString() + "";
			json = json.replaceAll("advertisersName", "name");//替换属性名字
			renderJson(json);
		}

		return NONE;
	}
	
	
	/**
	 * 审核查询结果集
	 * 
	 */
	
	public String listAudit(){
		
		if(customerBackUp == null){
			customerBackUp = new CustomerBackUp();
		}
		
		String advertisersName = customerBackUp.getAdvertisersName();
		String clientCode = customerBackUp.getClientCode();
		String communicator = customerBackUp.getCommunicator();
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
		}
		int count = customerService.getCustomerBackUpCount(customerBackUp,"0",customerids);
		
		PageBeanDB page = new PageBeanDB(count, pageNo);
		
		//新分页处理
		if (pageCustomer!=null && pageCustomer.getPageSize()!=0)
		{
			page = new PageBeanDB(count, pageCustomer.getPageNo(),pageCustomer.getPageSize());
		}
		if (count==0)
		{
			page.setPageNo(0);
		}
		//end 新分页处理
		
		listCustomers = customerService.listCustomerBackUpMgr(customerBackUp, page.getBegin(), page.getPageSize(),"0",customerids);
		
		//新分页处理
		page.setDataList(listCustomers);
		pageCustomer = page;
		//end 新分页处理
		if(advertisersName != null){
			this.getRequest().setAttribute("advertisersName",advertisersName);
		}
		if(clientCode != null){
			this.getRequest().setAttribute("clientCode",clientCode);
		}
		if(communicator != null){
			this.getRequest().setAttribute("communicator",communicator);
		}
		
		this.getRequest().setAttribute("page", page);
		
		return "listAuditMetas";
	}
	
	

	/**
	 * 查询结果集
	 * @throws Exception 
	 * 
	 */
	
	public String list() throws Exception{
		String returnPath = "";
		String contractBindingFlag = this.getRequest().getParameter("contractBindingFlag");
	//	String pageSelect = "1";
		if(customerBackUp == null){
			customerBackUp = new CustomerBackUp();
		}
		
		if("1".equals(contractBindingFlag)){
			//转到绑定客户页面
			returnPath = "bindCustomer";
			customerBackUp.setStatus("1");
		}else{
			//转到查询客户页面
			returnPath = "list";
		}
		
		String advertisersName =  customerBackUp.getAdvertisersName();
		String clientCode = customerBackUp.getClientCode();
		String conpanyAddress = customerBackUp.getConpanyAddress();
		String cooperationTime = customerBackUp.getCooperationTime();
		String communicator = customerBackUp.getCommunicator();
		
		String state = customerBackUp.getStatus();
		
		String cAdvertisersName = this.getRequest().getParameter("advertisersName");
		if(StringUtils.isNotBlank(cAdvertisersName)){
			cAdvertisersName = new String(cAdvertisersName.getBytes("ISO8859-1"),"UTF-8");
			customerBackUp.setAdvertisersName(cAdvertisersName);
		}
		
		String cClientCode = this.getRequest().getParameter("clientCode");
		if(StringUtils.isNotBlank(cClientCode)){
			cClientCode = new String(cClientCode.getBytes("ISO8859-1"),"UTF-8");
			customerBackUp.setClientCode(cClientCode);
		}
		
		String cConpanyAddress = this.getRequest().getParameter("conpanyAddress");
		if(StringUtils.isNotBlank(cConpanyAddress)){
			cConpanyAddress = new String(cConpanyAddress.getBytes("ISO8859-1"),"UTF-8");
			customerBackUp.setConpanyAddress(cConpanyAddress);
		}
		String cCooperationTime = this.getRequest().getParameter("cooperationTime");
		if(StringUtils.isNotBlank(cCooperationTime)){
			cCooperationTime = new String(cCooperationTime.getBytes("ISO8859-1"),"UTF-8");
			customerBackUp.setCooperationTime(cCooperationTime);
		}
		String cState = this.getRequest().getParameter("state");
		if(StringUtils.isNotBlank(cState)){
			customerBackUp.setStatus(cState);
		}
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
		}
		
		int count = customerService.getCustomerBackUpCount(customerBackUp,"",customerids);
		
		PageBeanDB page = new PageBeanDB(count, pageNo);
		if (pageCustomer!=null && pageCustomer.getPageSize()!=0)
		{
			//page.setPageSize(pageCustomer.getPageSize());	
			//page.setPageNo(pageCustomer.getPageNo());			
			page = new PageBeanDB(count, pageCustomer.getPageNo(),pageCustomer.getPageSize());
			
		}
		if (count==0)
		{
			page.setPageNo(0);
		}
		listCustomers = customerService.listCustomerBackUpMgr(customerBackUp, page.getBegin(), page.getPageSize(),"",customerids);
		
		page.setDataList(listCustomers);
		pageCustomer = page;
		if(advertisersName != null){
			this.getRequest().setAttribute("advertisersName",advertisersName );
		}
		
		if(cAdvertisersName != null){
			this.getRequest().setAttribute("cadvertisersName",cAdvertisersName );
		}
		
		if(clientCode != null){
			this.getRequest().setAttribute("clientCode",clientCode );
		}
		if(cClientCode != null){
			this.getRequest().setAttribute("cclientCode",cClientCode );
		}
		if(conpanyAddress != null){
			this.getRequest().setAttribute("conpanyAddress",conpanyAddress );
		}
		if(cConpanyAddress != null){
			this.getRequest().setAttribute("cconpanyAddress",cConpanyAddress );
		}
		if(cooperationTime != null){
			this.getRequest().setAttribute("cooperationTime", cooperationTime);
		}
		if(cCooperationTime != null){
			this.getRequest().setAttribute("ccooperationTime", cCooperationTime);
		}
		if(communicator!= null){
			this.getRequest().setAttribute("communicator", communicator);
		}
			
		
		this.getRequest().setAttribute("page", page);
		
		return returnPath;
		
	}
	
	
	
	/*public String list() {

		if (customer == null) {
			customer = new Customer();
		}

		int count = customerService.getCustomerCount(customer);

		PageBeanDB page = new PageBeanDB(count, pageNo);

		listCustomers = customerService.listCustomerMgr(customer, page.getBegin(), page.getPageSize());

		this.getRequest().setAttribute("page", page);

		return "list";
	}*/

	// AJAX 返回客户端方法
	public void returnMessage(String msg) {
		try {

			logger.debug("returnMessage 被调用");

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * render json
	 */
	public void renderJson(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
		//	log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCId() {
		return cId;
	}

	public void setCId(String id) {
		cId = id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<Customer> getList() {
		return list;
	}

	public void setList(List<Customer> list) {
		 this.list = list;
	}
    
	public CustomerBackUp getCustomerBackUp() {
		return customerBackUp;
	}

	public void setCustomerBackUp(CustomerBackUp customerBackUp) {
		this.customerBackUp = customerBackUp;
	}

	public List<CustomerBackUp> getListCustomers() {
		return listCustomers;
	}

	public void setListCustomers(List<CustomerBackUp> listCustomers) {
		this.listCustomers = listCustomers;
	}
	
	public  static String getCONTRACT_FILE_PATH() {
		return CONTRACT_FILE_PATH;
	}


	public static String getBUSINESS_FILE_PATH() {
		return BUSINESS_FILE_PATH;
	}


	public static String get_SEPARATOR() {
		return _SEPARATOR;
	}

	public static void set_SEPARATOR(String _separator) {
		_SEPARATOR = _separator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ContractRunService getContractRunService() {
		return contractRunService;
	}

	public void setContractRunService(ContractRunService contractRunService) {
		this.contractRunService = contractRunService;
	}

	public ContractBackupService getContractBackupService() {
		return contractBackupService;
	}
	public void setContractBackupService(ContractBackupService contractBackupService) {
		this.contractBackupService = contractBackupService;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public OperatorLocalFileService getOperatorLocalFileService() {
		return operatorLocalFileService;
	}
	public void setOperatorLocalFileService(OperatorLocalFileService operatorLocalFileService) {
		this.operatorLocalFileService = operatorLocalFileService;
	}

	public File getBackgroundImage() {
		return backgroundImage;
	}

	public String getBackgroundImageFileName() {
		return backgroundImageFileName;
	}

	public void setBackgroundImage(File backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public void setBackgroundImageFileName(String backgroundImageFileName) {
		this.backgroundImageFileName = backgroundImageFileName;
	}

	public String getUploadBusinessDir() {
		return uploadBusinessDir;
	}
	
	public void setUploadBusinessDir(String uploadBusinessDir) {
		this.uploadBusinessDir = uploadBusinessDir;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageBeanDB getPageCustomer() {
		return pageCustomer;
	}

	public void setPageCustomer(PageBeanDB pageCustomer) {
		this.pageCustomer = pageCustomer;
	}
	/**
     * 发送素材文件数据
     */
    private boolean sendFile(String localFile, String uploadPath){
        FtpUtils ftp = null;
        logger.info("开始发送--"+localFile+"--文件！");
            try {
                ftp = new FtpUtils();
                ftp.connectionFtp();
                ftp.sendFileToFtp(localFile ,  uploadPath);
                logger.info("素材文件"+localFile+"-- 发送成功 --");
            } catch (Exception e) {
                logger.info("素材文件"+localFile+"--发送失败！！！！"+e);
                e.printStackTrace();
                return false;
            } finally{
                if (ftp != null) {
                    ftp.closeFTP();
                }
            }
            return true;
    }

	public OperateLogService getOperateLogService() {
		return operateLogService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
    
}


