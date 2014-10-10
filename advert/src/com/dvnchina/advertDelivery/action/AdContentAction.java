package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import sun.net.ftp.FtpClient;

import com.dvnchina.advertDelivery.bean.PageBean;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.auditMetas.AuditMetasFormBean;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.constant.ResourceMetasConstant;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ContractRun;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.service.AdAssetsService;
import com.dvnchina.advertDelivery.service.AdvertPositionService;
import com.dvnchina.advertDelivery.service.AdvertPositionTypeService;
import com.dvnchina.advertDelivery.service.ContractBackupService;
import com.dvnchina.advertDelivery.service.ContractRunService;
import com.dvnchina.advertDelivery.service.CustomerService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.CookieUtils;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AdContentAction extends ActionSupport implements ServletRequestAware{
	
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(AdContentAction.class);
	
	private ImageMeta imageMeta;
	private VideoMeta videoMeta;
	private MessageMeta messageMeta;
	
	private ImageReal imageReal;
	private VideoReal videoReal;
	private MessageReal messageReal;
	
	// 列表页面查询的list结果集
	private List<Object> listAdAssets;
	private AdAssetsService adAssetsService;
	private HttpServletRequest request;
	
	private Map<Integer,String> mapAdvertPosition;
	
//	private Map<Integer,AdvertPositionType> mapAdvertPosition;
	
	private int currentPage;
	
	//页号
	protected int pageNo;
	
	private PageBean pageBean;
	
	private Resource resource; 
	private ResourceReal resourceReal;
	//列表页面查询的list结果集
	private List<Resource> listAdAssetResource; 
	private List<ResourceReal> listAdAssetResourceReal;
	
	//读取资源的路径
	private String resourcePath;
	
	private AuditMetasFormBean auditMetasFormBean;
	//广告商
	private CustomerService customerService;
	//合同
	private ContractBackupService contractBackupService;
	//广告位
	private AdvertPositionService advertPositionService;
	//合同运行期表
    private ContractRunService contractRunService;
    //广告位类型表
    private AdvertPositionTypeService advertPositionTypeService;
    
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	
	/**
	 * 获取用户id
	 * */
	private Integer getUserId() {
		String userId = CookieUtils.getCookieValueByKey(getRequest(),LoginConstant.COOKIE_USER_ID);
		return new Integer(userId);
	}

	/**
	 * 获取用户名
	 * */
	private String getUserName() {
		String userName = CookieUtils.getCookieValueByKey(getRequest(),LoginConstant.COOKIE_USER_NAME);
		return userName;
	}
	
	
	
	
	/**
	 * ftp 删除文件夹
	 */
	public String deleteFtp(){
		FtpUtils ftpUtil =new FtpUtils();
		FtpClient fc = null;
		
		if(fc == null){
			String ip =config.getValueByKey("resource.ip");
			int port = Integer.valueOf(config.getValueByKey("resource.port"));
			String username = config.getValueByKey("resource.username");
			String password = config.getValueByKey("resource.password");
			//ftp = new FtpUtils(ip,port,username,password);
			ftpUtil.connectionFtp(ip, port, username, password, 2000000);
			
		}
		
		//boolean b = ftpUtil.removeDirectory("../new/14.jpg");
		//执行删除操作
		boolean b = ftpUtil.deleteFile("/new/13.jpg");
		if(b){
			System.out.println("调用删除ftpUtil.deleteFile()返回 true");
		}else{
			System.out.println("调用删除ftpUtil.deleteFile()返回 false");
		}
		
		
		return null;
	}
	
	

	/**
	 * ftp 空间上复制练习
	 * 
	 * @return
	 */
	
	public String test(){
		
		
		FtpUtils ftpUtil =new FtpUtils();
		FtpClient fc = null;
		
		try {
		
			String ip =config.getValueByKey("resource.ip");
			int port = Integer.valueOf(config.getValueByKey("resource.port"));
			String username = config.getValueByKey("resource.username");
			String password = config.getValueByKey("resource.password");
			//ftp = new FtpUtils(ip,port,username,password);
			ftpUtil.connectionFtp(ip, port, username, password, 2000000);
			//远程文件名 本地文件名 远程FTP目录 本地目录
			ftpUtil.download("1.jpg", "2.jpg", "old/", "F:\\");
			// 待上传本地文件 远程目录 上传后的文件名称 若没有则取原有文件名称
			ftpUtil.uploadSimpleFile("F:\\2.jpg", "../new", "14.jpg");
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			ftpUtil.closedFtp();
		}
		return null;
		
	}
	
	/**
	 * 批量删除素材 
	 * 
	 */
	public String deleteResourceInfo(){
		int count = 0;
		String userId = "0";
		int result = 0;
		String ids = this.request.getParameter("ids");
		String id[] = ids.split(",");
		if(!"null".equals(ids) && !"".equals(ids)){
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i] != null){
					Resource resource = new Resource();
					resource = adAssetsService.getResourceById(Integer.parseInt(id[i]));
					Integer adId = Integer.parseInt(id[i]);
					Integer resourceType = resource.getResourceType();
					Character state = resource.getState();
					if(state == ResourceMetasConstant.ONLINE || state == ResourceMetasConstant.DOWN_LINE_DELETE ){
						result = ResourceMetasConstant.DELETE_ERROR;
						break;
					}else{
						result = 11;
					}
				}
			}
		}
		String json  = "{\"result\":"+result+",\"userId\":"+userId+"}";
		renderJson(json);
		return NONE;
	}
	
	
	/**
	 *执行删除素材 
	 * 
	 */
     public String deleteResource(){
		int count = 0;
		String userId = "0";
		int result = 0;
		String ids = this.request.getParameter("ids");
		String id[] = ids.split(",");
		if(!"null".equals(ids) && !"".equals(ids)){
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i] != null){
					Resource resource = new Resource();
					resource = adAssetsService.getResourceById(Integer.parseInt(id[i]));
					Integer adId = Integer.parseInt(id[i]);
					Integer resourceType = resource.getResourceType();
					Character state = resource.getState();
					count = adAssetsService.deleteResourceAbatch(adId,resourceType);
					if(count>0){
						logger.debug("删除素材成功，素材id为："+Integer.parseInt(id[i]));
					}else{
						logger.warn("删除素材时发生异常，素材id为："+Integer.parseInt(id[i]));
					}	
				}
				
			}	
		}
		
		String json  = "{\"result\":"+result+",\"userId\":"+userId+"}";
		renderJson(json);
		return NONE;
	}
	
	
	/**
	 * 获取广告位的信息
	 * 
	 */
	public String getPosition(){
		
		try {
			Map map = null;
			List<AdvertPosition> listAdvertPosition = advertPositionService.find(map);
			String content = Obj2JsonUtil.list2json(listAdvertPosition);
			renderJson(content);
		}catch (Exception e) {
			logger.error("获取广告数据出现异常", e);
			renderText("-1");
		}
		return null;
	}
	
	
	
	
	/**
	 * 已经上线的图片素材下线操作
	 * 
	 */
	public String downLineImageMeta(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		//资源id
		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		String state = ServletActionContext.getRequest().getParameter("state");
		
		int count = 0;
		
		
		if(StringUtils.isNotBlank(id)){
			
			count = adAssetsService.deleteDownImageMeta(Integer.parseInt(id));
			
			if(count>0){
				logger.debug("-----下线图片成功，用户id为：----"+id);
			}else{
				logger.warn("-----下线图片时发生异常，用户id为：--"+id);
			}
		}else{
			logger.debug("-----id 值 为空 -------");
		}
		
		this.returnMessage("下线图片成功");
		return null;
	}
	
	/**
	 * 已经上线的视频素材下线操作
	 * 
	 */
	
	public String downLineVideoMeta(){
		String id = ServletActionContext.getRequest().getParameter("id");
		//资源id
		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		String state = ServletActionContext.getRequest().getParameter("state");
		
		
		int count = 0;
		
		if(StringUtils.isNotBlank(id)){
			count = adAssetsService.deleteDownVideoMeta(Integer.parseInt(id));
			
			if(count>0){
				logger.debug("-----下线视频成功，用户id为：----"+id);
			}else{
				logger.warn("-----下线视频时发生异常，用户id为：--"+id);
			}
		}else{
			logger.debug("-----id 值 为空 -------");
		}
		
		this.returnMessage("下线视频成功");
		return null;
	}
	
	
	/**
	 * 已经上线的文字素材下线操作
	 * 
	 */
	
	public String downLineMessageMeta(){
		String id = ServletActionContext.getRequest().getParameter("id");
		//资源id
		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		String state = ServletActionContext.getRequest().getParameter("state");
		
		int count = 0;
		if(StringUtils.isNotBlank(id)){
			count = adAssetsService.deleteDownMessageMeta(Integer.parseInt(id));
			
			if(count>0){
				logger.debug("-----下线图片成功，用户id为：----"+id);
			}else{
				logger.warn("-----下线图片时发生异常，用户id为：--"+id);
			}
		}else{
			logger.debug("-----id 值 为空 -------");
		}
		
		this.returnMessage("下线文字成功");
		return null;
	}
	
	
	/**
	 * 
	 * 确定素材下线
	 */
	
	public String submitDownMetas(){
		int count = 0;
		String upOpintions = this.request.getParameter("Opintions");
		/*if(StringUtils.isNotBlank(UpOpintions)){
		   UpOpintions = new String(UpOpintions.getBytes("ISO-8859-1"),"UTF-8");
		  }*/
		String ids = (String) this.request.getSession().getAttribute("ids");
		String id[] = ids.split(",");
		
		if(!"null".equals(ids) && !"".equals(ids)){
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i] != null){
					Resource resource = new Resource();
					resource = adAssetsService.getResourceById(Integer.parseInt(id[i]));
					Character state = resource.getState();
					if(state == ResourceMetasConstant.DOWNLINE){
						continue;
					}else{
						ResourceReal resourceReal = adAssetsService.getResourceRealById(Integer.parseInt(id[i]));
						Integer idR = resourceReal.getId();
						Integer resourceId = resourceReal.getResourceId();
						Integer resourceType = resourceReal.getResourceType();
						//图片
						if(resourceType.equals(0)){
						   this.deleteImageMetaReal(idR,resourceId);
						//视频	
						}else if(resourceType.equals(1)){
						   this.deleteVideoMetaReal(idR, resourceId);
						//文字	
						}else if(resourceType.equals(2)){
							this.deleteMessageMetaReal(idR, resourceId);
						}else{
							System.out.println("没有找到要下线的素材类型！！");
						}
						//adAssetsService.deleteResourceRealById(Integer.parseInt(id[i]));
					}
				}
			}	
		}
		return "listUpResourceMetas";
	}
	
	/**
	 * 确定素材上线
	 * 
	 */
	public String submitUpMetas(){
		
		try {
			int count = 0;
			String upOpintions = this.request.getParameter("Opintions");
			/*if(StringUtils.isNotBlank(UpOpintions)){
			   UpOpintions = new String(UpOpintions.getBytes("ISO-8859-1"),"UTF-8");
			  }*/
			String ids = (String) this.request.getSession().getAttribute("ids");
			
			String id[] = ids.split(",");
			if(!"null".equals(ids) && !"".equals(ids)){
				for(int i=0;i<id.length;i++){
					if(!"".equals(id[i]) && id[i] != null){
						Resource resource = new Resource();
						resource = adAssetsService.getResourceById(Integer.parseInt(id[i]));
						Character state = resource.getState();
						if(state == ResourceMetasConstant.ONLINE){
							continue;
						}else{
						    //进行上线操作
							resource = adAssetsService.getResourceById(Integer.parseInt(id[i]));
							Integer idI = resource.getId();
							Integer resourceId = resource.getResourceId();
							Integer resourceType = resource.getResourceType();
							
						    count = adAssetsService.upResourceMetra(idI,resourceId,resourceType,upOpintions);
						    
						    if(count > 0){
								logger.debug("上线素材成功，素材id为："+idI);
							}else{
								logger.debug("上线素材时发生异常，素材id为："+idI);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUpResourceMetas";
	}
	
	/**
	 * 
	 * 下线操作
	 * @return
	 */
	public String getAuditResourceInfoDown(){
		StringBuffer sb = new StringBuffer();
		String userId = "0";
		int result = 0;
		String ids = "";
		ids = this.request.getParameter("ids");
		request.setAttribute("ids",ids);
		String id[] = ids.split(",");
		if(!"null".equals(ids) && !"".equals(ids)){
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i] != null){
					Resource resource = new Resource();
					resource = adAssetsService.getResourceById(Integer.parseInt(id[i]));
					Character state = resource.getState();
					if(state == ResourceMetasConstant.DOWNLINE){
						result = ResourceMetasConstant.DOWN_ERROR;
						break;
					}else{
						/*ResourceReal resourceReal = adAssetsService.getResourceRealById(Integer.parseInt(id[i]));
						Integer idR = resourceReal.getId();
						Integer resourceId = resourceReal.getResourceId();
						Integer resourceType = resourceReal.getResourceType();
						//图片
						if(resourceType == 1){
						   this.deleteImageMetaReal(idR,resourceId);
						//视频	
						}else if(resourceType  == 2){
						   this.deleteVideoMetaReal(idR, resourceId);
						//文字	
						}else if(resourceType == 3){
							this.deleteMessageMetaReal(idR, resourceId);
						}else{
							System.out.println("没有找到要下线的素材类型！！");
						}*/
						result = ResourceMetasConstant.UP;
					}
				}
			}
		}
		String idsArr = sb.toString();
		this.request.getSession().setAttribute("ids", ids);
		String json  = "{\"result\":"+result+",\"userId\":"+userId+"}";
		renderJson(json);
		return NONE;
	}

	/**
	 * 上线操作时，弹出层得到素材的相关信息
	 * 
	 */
	public String getAuditResourceInfoUp(){
		
		StringBuffer sb = new StringBuffer();
		
		String userId = "0";
		int result = 0;
		String ids = "";
		
		ids = this.request.getParameter("ids");
		
		request.setAttribute("ids",ids);
		String id[] = ids.split(",");
		if(!"null".equals(ids) && !"".equals(ids)){
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i] != null){
					Resource resource = new Resource();
					resource = adAssetsService.getResourceById(Integer.parseInt(id[i]));
					Character state = resource.getState();
					if(state == ResourceMetasConstant.ONLINE){
						result = ResourceMetasConstant.UP_ERROR;
						break;
					}else{
						result = ResourceMetasConstant.UP;
						sb.append(id[i]).append(",");
					}
				}
			}
		}
		
		String idsArr = sb.toString();
		//this.request.setAttribute("ids",ids);
		this.request.getSession().setAttribute("ids", ids);
		String json  = "{\"result\":"+result+",\"userId\":"+userId+"}";
		renderJson(json);
		return NONE;
	}
	
	/**
	 * 审核图片时，点击通过按钮，从库中读取相关的记录,将相应的有汉字的字段加入赋予相应的汉字，在页面上显示。
	 * @return
	 */
	public String insertImageRealMeta2(){
		//得到配置文件的路径信息	//temporaryResourceFilePath=http://localhost/
		
		String resourceRealPath= "http://localhost/";
		
	//	String resourceRealPath = ConfigureProperties.getInstance().get("temporaryResourceFilePath");
//------------------------
		//得到FTP的宿主目录
		//String resourceRealPath = FTPConstant.FTP_HOME_DIRECTORY;
		//得到FTP上确定advert文件夹
//		String advertPath = ConfigureProperties.getInstance().get("ftp.advertDirectory");
//-----------------------
		
		//客户表单中客户代码
		String clientCode="";
		//客户表单中主键 客户id
		Integer CustomerId; 
		//资源表单中所属合同号
		Integer C_contractNumber;
		//合同表单中的合同号
		String contractNumber ="";
		
		String id = request.getParameter("id");
		if(auditMetasFormBean == null){
			auditMetasFormBean = new AuditMetasFormBean();
		}
		
		if(id != null && !"".equals(id)){
			resource = adAssetsService.getResourceById(Integer.parseInt(id));
		}
		
			//为了得到客户的所属合同号，先关联客户表单，取得合同号
			Integer customerId = resource.getCustomerId();
			
			C_contractNumber = resource.getContractId();
			
			List<ContractRun>  listContractRun = contractRunService.getContractRunById(C_contractNumber);
			if(listContractRun.size() > 0){
				 contractNumber = listContractRun.get(0).getContractNumber();
			 }
		    
		    //根据客户id得到广告商运行期表中(T_CUSTOMER)的客户代码
		    List<Customer> listCustomer = customerService.getRunClientCodeByCustomerId(customerId); 
		    
		    if(listCustomer.size()>0){
		    	clientCode = listCustomer.get(0).getClientCode();
		    }

		    Integer idResource = resource.getId();
			String resourceName = resource.getResourceName();
			Integer resourceType = resource.getResourceType();
			Integer category = resource.getCategoryId(); 
			Integer resourceId = resource.getResourceId();
			imageMeta = adAssetsService.getImageMetaById(resourceId);
			String temporary = imageMeta.getTemporaryFilePath();
			//得到临时空间完成路径
			resourcePath = resourceRealPath+temporary;
			
			if(idResource != null && !"".equals(idResource) && resourceName != null && !"".equals(resourceName) && resourceType != null && !"".equals(resourceType) && category != null && !"".equals(category) && resourceId != null && !"".equals(resourceId) && resourcePath != null && !"".equals(resourcePath)){
				//设定资源类型
				if(resourceType == ResourceMetasConstant.IMAGE_TYPE){
					auditMetasFormBean.setResourceTypeName("图片");
				}else if(resourceType == ResourceMetasConstant.VIDEO_TYPE){
					auditMetasFormBean.setResourceTypeName("视频");
				}else if(resourceType == ResourceMetasConstant.MESSAGE_TYPE){
					auditMetasFormBean.setResourceTypeName("文字");
				}else{
					System.out.println("---没有可设定的资源类型……---");
				}
				//设定所属内容分类
				//0 为商用  
				if(category == ResourceMetasConstant.CATEGORY_BUSINESS){
					auditMetasFormBean.setCategoryName("商用");
				}else if(category == ResourceMetasConstant.CATEGORY_CHARITY){
					auditMetasFormBean.setCategoryName("公益");
				}else{
					System.out.println("--没有可设定的内容分类类型----");
				}
				auditMetasFormBean.setId(idResource);
				auditMetasFormBean.setCategory(category);
				auditMetasFormBean.setResourceType(resourceType);
				auditMetasFormBean.setResourceId(resourceId);
				auditMetasFormBean.setResourceName(resourceName);
				auditMetasFormBean.setClientCode(clientCode);
				auditMetasFormBean.setContractNumber(contractNumber);
				auditMetasFormBean.setTemporaryFilePath(resourcePath);
			}
			StringBuffer buf = new StringBuffer();
			if(resource != null && !"".equals(resource)){
				//资源id ，资源名称，资源类型，
				buf.append("[");
				buf.append("{id:").append(auditMetasFormBean.getId()).append(
				",resourceName:'").append(auditMetasFormBean.getResourceName()).append(
				"',resourceType:").append(auditMetasFormBean.getResourceType())
				.append(",resourceId:").append(auditMetasFormBean.getResourceId())
				.append(",category:").append(auditMetasFormBean.getCategory()).append(",resourceTypeName:'")
				.append(auditMetasFormBean.getResourceTypeName()).append("',categoryName:'")
				.append(auditMetasFormBean.getCategoryName()).append("',TemporaryFilePath:'")
				.append(auditMetasFormBean.getTemporaryFilePath()).append("',clientCode:'")
				.append(auditMetasFormBean.getClientCode()).append("',contractNumber:'").append(auditMetasFormBean.getContractNumber()).append("'").append("}");
			}
			buf.append("]");
			System.out.println("##素材数据：" + buf.toString());
			//返回到页面上
			renderJson(buf.toString());
			return null;
	    }
	
	/**
	 * 审核视频时，点击通过按钮，从库中读取相关的记录,将相应的有汉字的字段加入赋予相应的汉字，与页面上显示。
	 * @return
	 */
	
	public String insertVideoRealMeta2(){
		//得到配置文件信息
		String resourceRealPath = ConfigureProperties.getInstance().get("temporaryResourceFilePath");
		//客户表单中客户代码
		String clientCode="";
		//客户表单中主键 客户id
		Integer CustomerId; 
		//资源表单中所属合同号
		Integer C_contractNumber;
		//合同表单中的合同号
		String contractNumber ="";
		
		String id = request.getParameter("id");
		if(auditMetasFormBean == null){
			auditMetasFormBean = new AuditMetasFormBean();
		}
		
		if(id != null && !"".equals(id)){
			resource = adAssetsService.getResourceById(Integer.parseInt(id));
		}
			Integer customerId = resource.getCustomerId();
			
			C_contractNumber = resource.getContractId();
			
			List<ContractRun>  listContractRun = contractRunService.getContractRunById(C_contractNumber);
		    
		    if(listContractRun.size()>0){
		    	contractNumber = listContractRun.get(0).getContractNumber();
		    }
		    
		    //根据客户id得到广告商运行期表中(T_CUSTOMER)的客户代码
		    List<Customer> listCustomer = customerService.getRunClientCodeByCustomerId(customerId); 
		    
		    if(listCustomer.size()>0){
		    	clientCode = listCustomer.get(0).getClientCode();
		    }

		    Integer idResource = resource.getId();
			String resourceName = resource.getResourceName();
			Integer resourceType = resource.getResourceType();
			Integer category = resource.getCategoryId(); 
			Integer resourceId = resource.getResourceId();
			videoMeta = adAssetsService.getVideoMetaById(resourceId);
			String temporary = videoMeta.getTemporaryFilePath();
			resourcePath = resourceRealPath+temporary;
			
			if(idResource != null && !"".equals(idResource) && resourceName != null && !"".equals(resourceName) && resourceType != null && !"".equals(resourceType) && category != null && !"".equals(category) && resourceId != null && !"".equals(resourceId) && resourcePath != null && !"".equals(resourcePath)){
				//设定资源类型
				if(resourceType == ResourceMetasConstant.IMAGE_TYPE){
					auditMetasFormBean.setResourceTypeName("图片");
				}else if(resourceType == ResourceMetasConstant.VIDEO_TYPE){
					auditMetasFormBean.setResourceTypeName("视频");
				}else if(resourceType == ResourceMetasConstant.MESSAGE_TYPE){
					auditMetasFormBean.setResourceTypeName("文字");
				}else{
					System.out.println("---没有可设定的资源类型……---");
				}
				//设定所属内容分类
				//0 为商用   
				if(category == ResourceMetasConstant.CATEGORY_BUSINESS){
					auditMetasFormBean.setCategoryName("商用");
				}else if(category == ResourceMetasConstant.CATEGORY_CHARITY){
					auditMetasFormBean.setCategoryName("公益");
				}else{
					System.out.println("--没有可设定的内容分类类型----");
				}
				auditMetasFormBean.setId(idResource);
				auditMetasFormBean.setCategory(category);
				auditMetasFormBean.setResourceType(resourceType);
				auditMetasFormBean.setResourceId(resourceId);
				auditMetasFormBean.setResourceName(resourceName);
				auditMetasFormBean.setClientCode(clientCode);
				auditMetasFormBean.setContractNumber(contractNumber);
				auditMetasFormBean.setTemporaryFilePath(resourcePath);
			}
			StringBuffer buf = new StringBuffer();
			if(resource != null && !"".equals(resource)){
				//资源id ，资源名称，资源类型，
				buf.append("[");
				buf.append("{id:").append(auditMetasFormBean.getId()).append(
				",resourceName:'").append(auditMetasFormBean.getResourceName()).append(
				"',resourceType:").append(auditMetasFormBean.getResourceType())
				.append(",resourceId:").append(auditMetasFormBean.getResourceId())
				.append(",category:").append(auditMetasFormBean.getCategory()).append(",resourceTypeName:'")
				.append(auditMetasFormBean.getResourceTypeName()).append("',categoryName:'")
				.append(auditMetasFormBean.getCategoryName()).append("',TemporaryFilePath:'")
				.append(auditMetasFormBean.getTemporaryFilePath()).append("',clientCode:'")
				.append(auditMetasFormBean.getClientCode()).append("',contractNumber:'").append(auditMetasFormBean.getContractNumber()).append("'").append("}");
			}
			buf.append("]");
			System.out.println("##素材数据：" + buf.toString());
			//返回到页面上
			renderJson(buf.toString());
			return null;
	    }
	
	/**
	 * 审核文字时，点击通过按钮，从库中读取相关的记录,将相应的有汉字的字段加入赋予相应的汉字，与页面上显示。
	 * @return
	 */
	public String insertMessageRealMeta2(){
		
		StringBuffer str = new StringBuffer("");
	    String st = "";
	    
	   //客户表单中客户代码
		String clientCode="";
		//客户表单中主键 客户id
		Integer CustomerId; 
		//资源表单中所属合同号
		Integer C_contractNumber;
		//合同表单中的合同号
		String contractNumber ="";
	    
	    try{
				String id = request.getParameter("id");
				if(auditMetasFormBean == null){
					auditMetasFormBean = new AuditMetasFormBean();
				}
				
				if(id != null && !"".equals(id)){
					resource = adAssetsService.getResourceById(Integer.parseInt(id));
				}
		
				Integer customerId = resource.getCustomerId();
				
				C_contractNumber = resource.getContractId();
				
				List<ContractRun>  listContractRun = contractRunService.getContractRunById(C_contractNumber);
			    
			    if(listContractRun.size()>0){
			    	contractNumber = listContractRun.get(0).getContractNumber();
			    }
			    
				List<Customer> listCustomer = customerService.getRunClientCodeByCustomerId(customerId); 
				    
			    if(listCustomer.size()>0){
			    	clientCode = listCustomer.get(0).getClientCode();
			    }
				
				Integer idResource = resource.getId();
				String resourceName = resource.getResourceName();
				Integer resourceType = resource.getResourceType();
				Integer category = resource.getCategoryId(); 
				Integer resourceId = resource.getResourceId();
				
				messageMeta = adAssetsService.getMessageMetaById(resourceId);
				
			//	java.sql.Blob blob = (java.sql.Blob)messageMeta.getContent();
				
				/*InputStreamReader isr;
				java.sql.Blob contentBlob = (java.sql.Blob) messageMeta.getContent();
				InputStream is = contentBlob.getBinaryStream();
			    isr = new InputStreamReader(is,"gb2312");
			    BufferedReader br = new BufferedReader(isr);
		        String data = "";
		       while((data = br.readLine())!=null){
		        str.append(data+" "); 
		       }
		       */
		       st = str.toString();
			
			  if(idResource != null && !"".equals(idResource) && resourceName != null && !"".equals(resourceName) && resourceType != null && !"".equals(resourceType) && category != null && !"".equals(category) && resourceId != null && !"".equals(resourceId) && st != null && !"".equals(st)){
				//设定资源类型
				if(resourceType == ResourceMetasConstant.IMAGE_TYPE){
					auditMetasFormBean.setResourceTypeName("图片");
				}else if(resourceType == ResourceMetasConstant.VIDEO_TYPE){
					auditMetasFormBean.setResourceTypeName("视频");
				}else if(resourceType == ResourceMetasConstant.MESSAGE_TYPE){
					auditMetasFormBean.setResourceTypeName("文字");
				}else{
					System.out.println("---没有可设定的资源类型……---");
				}
				//设定所属内容分类
				//0 为商用   
				if(category == ResourceMetasConstant.CATEGORY_BUSINESS){
					auditMetasFormBean.setCategoryName("商用");
				}else if(category == ResourceMetasConstant.CATEGORY_CHARITY){
					auditMetasFormBean.setCategoryName("公益");
				}else{
					System.out.println("--没有可设定的内容分类类型----");
				}
				auditMetasFormBean.setId(idResource);
				auditMetasFormBean.setCategory(category);
				auditMetasFormBean.setResourceType(resourceType);
				auditMetasFormBean.setResourceId(resourceId);
				auditMetasFormBean.setResourceName(resourceName);
				auditMetasFormBean.setClientCode(clientCode);
				auditMetasFormBean.setContractNumber(contractNumber);
				auditMetasFormBean.setContent(st);
				
			}else{
				System.out.println("-----idResource,resourceName,resourceType,category,resourceId,st 中某一个参数为空------");
			}
			StringBuffer buf = new StringBuffer();
			if(resource != null && !"".equals(resource)){
				//资源id ，资源名称，资源类型，
				buf.append("[");
				buf.append("{id:").append(auditMetasFormBean.getId()).append(
				",resourceName:'").append(auditMetasFormBean.getResourceName()).append(
				"',resourceType:").append(auditMetasFormBean.getResourceType())
				.append(",resourceId:").append(auditMetasFormBean.getResourceId())
				.append(",category:").append(auditMetasFormBean.getCategory()).append(",resourceTypeName:'")
				.append(auditMetasFormBean.getResourceTypeName()).append("',categoryName:'")
				.append(auditMetasFormBean.getCategoryName()).append("',content:'")
				.append(auditMetasFormBean.getContent()).append("',clientCode:'")
				.append(auditMetasFormBean.getClientCode()).append("',contractNumber:'").append(auditMetasFormBean.getContractNumber()).append("'").append("}");
			}
			buf.append("]");
			System.out.println("##素材数据：" + buf.toString());
			//返回到页面上
			renderJson(buf.toString());
			
	    } catch (Exception e) {
			e.printStackTrace();
	    	
	    }
			return null;
			
	    }
	
	
	/**
	 * 审核时，点击通过按钮，从库中读取相关的记录
	 * 
	 */
	public String insertImageRealMeta(){
		String id = request.getParameter("id");
		
		if(id != null){
			resource = adAssetsService.getResourceById(Integer.parseInt(id));
		}
		
		StringBuffer buf = new StringBuffer();
		if(resource != null && !"".equals(resource)){
			//资源id ，资源名称，资源类型，
			buf.append("[");
			buf.append("{id:").append(resource.getId()).append(
			",resourceName:'").append(resource.getResourceName()).append(
			"',resourceType:").append(resource.getResourceType()).append(",resourceId:").append(resource.getResourceId()).append(",category:").append(resource.getCategoryId()).append("}");
		}
		buf.append("]");
		System.out.println("##素材数据：" + buf.toString());
		//返回到页面上
		renderJson(buf.toString());
		
		return null;
	}
	
	/**
	 * 运营商查询结果集
	 * 
	 */
	public String listReal(){
		
		if(resourceReal == null){
			resourceReal = new ResourceReal();
		}
		
		
		String advertPositionName = resourceReal.getAdvertPositionName();
		
		Integer positionId = resourceReal.getAdvertPositionId();
		
		String resourceName = resourceReal.getResourceName();
		String resourceDesc = resourceReal.getResourceDesc();
		String contractNumber = resourceReal.getContractNumberStr();
		
		
		int count = adAssetsService.getAdContentCountReal(resourceReal,"");
		PageBeanDB page = new PageBeanDB(count,pageNo);
		
		listAdAssetResourceReal = adAssetsService.listAdAssestsMgrReal(resourceReal, page.getBegin(),page.getPageSize(),"");
		
		mapAdvertPosition = new HashMap<Integer, String>();
		
		if(listAdAssetResourceReal!= null && listAdAssetResourceReal.size()>0){
			for(int i= 0 ;i<listAdAssetResourceReal.size();i++){
				Integer id = listAdAssetResourceReal.get(i).getId();
				Integer adPositionTypeId = listAdAssetResourceReal.get(i).getAdvertPositionId();
				
				ResourceReal resourceReal = adAssetsService.getResourceRealById(id);
				
				List<AdvertPosition> listAdvertPositionById = advertPositionService.getAdvertPositionById(adPositionTypeId);
				
				if(listAdvertPositionById != null && listAdvertPositionById.size()>0){
					for(int j=0;j<listAdvertPositionById.size();j++){
						Integer positionTypeId = listAdvertPositionById.get(j).getPositionTypeId();
						AdvertPositionType advertPositionType =advertPositionTypeService.get(positionTypeId);
						mapAdvertPosition.put(resourceReal.getId(),advertPositionType.getPositionTypeCode());
					}
				}
			}
		}
		
		List<AdvertPosition> listAdvertPositionById = advertPositionService.getAdvertPositionById(positionId);
		
		if(listAdvertPositionById != null && listAdvertPositionById.size()>0){
			for(int i = 0;i<listAdvertPositionById.size();i++){
				Integer id = listAdvertPositionById.get(i).getId();
				
				ResourceReal resourceReal = adAssetsService.getResourceRealByAdvertPositionId(id);
				Integer positionTypeId = listAdvertPositionById.get(i).getPositionTypeId();
				AdvertPositionType advertPositionType =advertPositionTypeService.get(positionTypeId);
				mapAdvertPosition.put(resourceReal.getId(), advertPositionType.getPositionTypeCode());
			}
		}
		
		
		if(StringUtils.isNotBlank(advertPositionName)){
			request.setAttribute("advertPositionName",advertPositionName);
		}
		
		if( resourceName !=null){
			request.setAttribute("resourceName",resourceName);
		}
		if(resourceDesc != null){
			request.setAttribute("resourceDesc",resourceDesc);
		}
		if( contractNumber != null){
			request.setAttribute("contractNumber",contractNumber);
		}
		request.setAttribute("page",page);
		return "listReal";
	}
	
	/**
	 * 
	 * 素材内容审核管理查询结果集
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String listAuditMetas(){
	
		if(resource == null){
			resource = new Resource();
		}
		
        Integer userId = this.getUserId();
		
		resource.setUserId(userId);
		
		String resourceName = resource.getResourceName();
		String contractNumber = resource.getContractId().toString();
		
		if(StringUtils.isNotBlank(resourceName)){
			request.setAttribute("resourceName",resourceName);
		}
		
		if(StringUtils.isNotBlank(contractNumber)){
			request.setAttribute("contractNumber",contractNumber);
		}
		
		//记录条数
		int count = adAssetsService.getAdContentCount(resource, "0");
		PageBeanDB page = new PageBeanDB(count,pageNo);
		
		listAdAssetResource = adAssetsService.listAdAssestsMgr(resource, page.getBegin(),page.getPageSize(),"0");
		request.setAttribute("page",page);
		return "listAuditMetas";
	}
	
	
	
	/**
	 * 查询结果集，用于上下线页使用
	 * 
	 */
	public String listUD(){
		
		if(resource == null){
			resource = new Resource();
		}
		
		
        Integer userId = this.getUserId();
		
		resource.setUserId(userId);
		
		String advertPositionName = resource.getAdvertPositionName();
		
		Integer positionId = resource.getAdvertPositionId();
		
		String resourceName = resource.getResourceName();
		String resourceDesc = resource.getResourceDesc();
		String contractNumber = resource.getContractId().toString();
		
		//记录条数
		int count = adAssetsService.getAdContentCount(resource, "");
		
		PageBeanDB page = new PageBeanDB(count,pageNo);
		
		Integer str = resource.getResourceType();
		
		mapAdvertPosition = new HashMap<Integer, String>();
		
		listAdAssetResource = adAssetsService.listAdAssestsMgr(resource, page.getBegin(),page.getPageSize(),"1");
		
		//页面广告位名称
		if(listAdAssetResource != null && listAdAssetResource.size()>0){
			for(int i= 0;i<listAdAssetResource.size();i++){
				//13,
				Integer id = listAdAssetResource.get(i).getId();
				//243
				Integer adPositionTypeId = listAdAssetResource.get(i).getAdvertPositionId();
				
				Resource resource = adAssetsService.getResourceById(id);
				
				List<AdvertPosition> listAdvertPositionById = advertPositionService.getAdvertPositionById(adPositionTypeId);
				
				if(listAdvertPositionById != null && listAdvertPositionById.size()>0){
					for(int j=0;j<listAdvertPositionById.size();j++){
						//1,
						Integer positionTypeId = listAdvertPositionById.get(j).getPositionTypeId();
						AdvertPositionType advertPositionType =advertPositionTypeService.get(positionTypeId);
						mapAdvertPosition.put(resource.getId(),advertPositionType.getPositionTypeCode());
					}
				}
			}
			
		}
		
		List<AdvertPosition> listAdvertPositionById = advertPositionService.getAdvertPositionById(positionId);
		
		if(listAdvertPositionById != null && listAdvertPositionById.size()>0){
			for(int i = 0;i<listAdvertPositionById.size();i++){
				Integer id = listAdvertPositionById.get(i).getId();
				
				Resource resource = adAssetsService.getResourceByAdvertPositionId(id);
				Integer positionTypeId = listAdvertPositionById.get(i).getPositionTypeId();
				AdvertPositionType advertPositionType =advertPositionTypeService.get(positionTypeId);
				mapAdvertPosition.put(resource.getId(), advertPositionType.getPositionTypeCode());
			}
		}
		
		if( positionId != null){
			request.setAttribute("advertPositionName",advertPositionName );
		}
		
		if( resourceName!=null){
			request.setAttribute("resourceName",resourceName );
		}
		
		if( resourceDesc!=null){
			request.setAttribute("resourceDesc",resourceDesc );
		}
		
		if(contractNumber !=null){
			request.setAttribute("contractNumber",contractNumber );
		}
		
		request.setAttribute("page",page);
		return "list_U_D";
	}
	
	
	/**
	 * 查询结果集
	 * 
	 */
	public String list(){
		
		if(resource == null){
			resource = new Resource();
		}
		
		Integer userId = this.getUserId();
		
		resource.setUserId(userId);
		
		
		String advertPositionName = resource.getAdvertPositionName();
		
		Integer positionId = resource.getAdvertPositionId();
		
		String resourceName = resource.getResourceName();
		String resourceDesc = resource.getResourceDesc();
		String contractNumber = resource.getContractId().toString();
		
		//记录条数
		int count = adAssetsService.getAdContentCount(resource, "");
		
		PageBeanDB page = new PageBeanDB(count,pageNo);
		
		Integer str = resource.getResourceType();
		
		mapAdvertPosition = new HashMap<Integer, String>();
		
		listAdAssetResource = adAssetsService.listAdAssestsMgr(resource, page.getBegin(),page.getPageSize(),"");
		
		//页面广告位名称
		if(listAdAssetResource != null && listAdAssetResource.size()>0){
			for(int i= 0;i<listAdAssetResource.size();i++){
				//13,
				Integer id = listAdAssetResource.get(i).getId();
				//243
				Integer adPositionTypeId = listAdAssetResource.get(i).getAdvertPositionId();
				
				Resource resource = adAssetsService.getResourceById(id);
				
				List<AdvertPosition> listAdvertPositionById = advertPositionService.getAdvertPositionById(adPositionTypeId);
				
				if(listAdvertPositionById != null && listAdvertPositionById.size()>0){
					for(int j=0;j<listAdvertPositionById.size();j++){
						//1,
						Integer positionTypeId = listAdvertPositionById.get(j).getPositionTypeId();
						AdvertPositionType advertPositionType =advertPositionTypeService.get(positionTypeId);
						mapAdvertPosition.put(resource.getId(),advertPositionType.getPositionTypeCode());
					}
				}
			}
			
		}
		
		List<AdvertPosition> listAdvertPositionById = advertPositionService.getAdvertPositionById(positionId);
		
		if(listAdvertPositionById != null && listAdvertPositionById.size()>0){
			for(int i = 0;i<listAdvertPositionById.size();i++){
				Integer id = listAdvertPositionById.get(i).getId();
				
				Resource resource = adAssetsService.getResourceByAdvertPositionId(id);
				Integer positionTypeId = listAdvertPositionById.get(i).getPositionTypeId();
				AdvertPositionType advertPositionType =advertPositionTypeService.get(positionTypeId);
				mapAdvertPosition.put(resource.getId(), advertPositionType.getPositionTypeCode());
			}
		}
		
		if(userId != null){
			request.setAttribute("customerId",userId);
		}
		
		if( positionId != null){
			request.setAttribute("advertPositionName",advertPositionName );
		}
		
		if( resourceName!=null){
			request.setAttribute("resourceName",resourceName );
		}
		
		if( resourceDesc!=null){
			request.setAttribute("resourceDesc",resourceDesc );
		}
		
		if(contractNumber !=null){
			request.setAttribute("contractNumber",contractNumber );
		}
		
		request.setAttribute("page",page);
		
		return "list";
	}
	
	
	/**
	 *图片素材审核不通过 
	 */           
	public String noAuditImageMetasPass(){
		logger.debug("-----审核图片素材不通过方法noAuditImageMetasPass()被调用------");
		int count = 0;
		String id = request.getParameter("id");
		//审核意见
		String examinationOpintions = request.getParameter("examinationOpintions");
		
		if(id != null && !"".equals(id)){
			id = id.trim();
			examinationOpintions = examinationOpintions.trim();
			count = adAssetsService.updateNoAuditImageMetas(Integer.parseInt(id),examinationOpintions); 
			if(count > 0){
				logger.debug("审核图片不通过操作成功，图片id为："+id);
			}else{
				logger.debug("审核图片不通过时发生异常，图片id为："+id);
			}
		}else{
			logger.debug("审核素材不通过时传入的素材id 为空");
		}
		logger.debug("审核图片素材不通过方法 noAuditImageMetasPass()调用结束");
		
		return "listAuditMetasG";
		
	}
	
	
	/**
	 * 视频素材审核不通过 
	 */
	public String noAuditVideoMetasPass(){
		logger.debug("----审核视频素材不通过方法 noAuditVideoMetasPass()被调用------");
		int count = 0;
		String id = request.getParameter("id");
		//审核意见
		String examinationOpintions = request.getParameter("examinationOpintions");
		
		if(id != null && !"".equals(id)){
			id = id.trim();
			count = adAssetsService.updateNoAuditVideoMetas(Integer.parseInt(id),examinationOpintions);
			if(count > 0){
				logger.debug("审核视频不通过操作成功，视频id为："+id);
			}else{
				logger.debug("审核视频不通过时发生异常，视频id为："+id);
			}
		}else{
			logger.debug("审核素材不通过时传入的素材id 为空");
		}
		logger.debug("审核视频素材不通过方法 noAuditVideoMetasPass()调用结束");
		return "listAuditMetasG";
	}
	
	/**
	 * 文字素材审核不通过 
	 */
	public String noAuditMessageMetasPass(){
		logger.debug("-----审核文字素材不通过方法 noAuditMessageMetasPass()被调用------");
		int count = 0;
		String id = request.getParameter("id");
		//审核意见
		String examinationOpintions = request.getParameter("examinationOpintions");
		
		if(id != null && !"".equals(id)){
			id = id.trim();
			count = adAssetsService.updateNoAuditMessageMetas(Integer.parseInt(id),examinationOpintions);
			if(count > 0){
				logger.debug("审核文字消息素材不通过操作成功，文字消息id为："+id);
			}else{
				logger.debug("审核文字消息素材不通过时发生异常，文字消息id为："+id);
			}
		}else{
			logger.debug("审核素材不通过时传入的素材id 为空");
		}
		logger.debug("----审核文字素材不通过方法 noAuditMessageMetasPass()调用结束---");
		
		return "listAuditMetasG";
	}
	
	
	/**
	 * 审核图片素材通过
	 * 
	 */
	public String insertGoAuditImageMetas(){
		logger.debug("----审核图片素材 方法 insertGoAuditImageMetas()被调用------");
		int count = 0;
		String id = request.getParameter("id");
		String resourceId = request.getParameter("resourceId");
		//客户代码
		String clientCode = request.getParameter("clientCode");
		//所属合同号
		String contractNumber = request.getParameter("contractNumber");
		
		//审核意见
		String examinationOpintions = request.getParameter("examinationOpintions");
		try {
			examinationOpintions = new String(examinationOpintions.getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(id != null && !"".equals(id) && resourceId != null && !"".equals(resourceId) && clientCode != null && !"".equals(clientCode) && contractNumber != null && !"".equals(contractNumber)){
			id = id.trim();
			resourceId=resourceId.trim();
			examinationOpintions = examinationOpintions.trim();
			clientCode = clientCode.trim();
			contractNumber = contractNumber.trim();
			count = adAssetsService.insertAuditImageMetas(Integer.parseInt(id),Integer.parseInt(resourceId),examinationOpintions,clientCode,contractNumber);
			if(count > 0){
				logger.debug("审核图片通过，图片id为："+id);
			}else{
				logger.debug("审核图片时发生异常，图片id为："+id);
			}
		}else{
			logger.debug("ImageMeta 的id 为空");
		}
		
		logger.debug("审核图片素材 方法 goAuditImageMetas()调用结束");
		
		return "listAuditMetasG";
	}
	
	/**
	 * 审核视频素材通过
	 * 
	 */
	public String insertGoAuditVideoMetas(){
		
		logger.debug("-----审核视频素材方法 goAuditVideoMetas()被调用-----");
		
		int count = 0;
		String id = request.getParameter("id");
		String resourceId = request.getParameter("resourceId");
		//客户代码
		String clientCode = request.getParameter("clientCode");
		//所属合同号
		String contractNumber = request.getParameter("contractNumber");
		//审核意见
		String examinationOpintions = request.getParameter("examinationOpintions");
		
		if(id != null && !"".equals(id) && resourceId != null && !"".equals(resourceId) && clientCode != null && !"".equals(clientCode) && contractNumber != null && !"".equals(contractNumber)){
			id = id.trim();
			resourceId = resourceId.trim();
			examinationOpintions = examinationOpintions.trim();
			count = adAssetsService.insertAuditVideoMetas(Integer.parseInt(id),Integer.parseInt(resourceId),examinationOpintions,clientCode,contractNumber);
			if(count > 0){
				logger.debug("审核视频素材通过，视频id为："+id);
			}else{
				logger.debug("审核视频素材时发生异常，视频id为："+id);
			}
			
		}else{
			logger.debug("VideoMeta 的id 为空");
		}
		
		logger.debug("审核视频素材方法 goAuditVideoMetas()调用结束");
		
		return "listAuditMetasG";
	}
	
	/**
	 * 审核文字素材通过
	 * 
	 */
	public String insertGoAuditMessageMetas(){
		
		logger.debug("----审核文字素材方法 insertGoAuditMessageMetas()被调用------");
		
		int count = 0;
		String id = request.getParameter("id");
		String resourceId = request.getParameter("resourceId");
		//客户代码
		String clientCode = request.getParameter("clientCode");
		//所属合同号
		String contractNumber = request.getParameter("contractNumber");
		//审核意见
		String examinationOpintions = request.getParameter("examinationOpintions");
		
		if(id !=null && !"".equals(id)){
			System.out.println("---MessageMetas 的 id 为-- " + id);
		}else {
			System.out.println("------id 为空---");
			
		}
		
		if(id != null && !"".equals(id) && resourceId != null && !"".equals(resourceId) && clientCode != null && !"".equals(clientCode) && contractNumber != null && !"".equals(contractNumber)){
			id = id.trim();
			resourceId = resourceId.trim();
			examinationOpintions = examinationOpintions.trim();
			count = adAssetsService.insertAuditMessageMetas(Integer.parseInt(id),Integer.parseInt(resourceId),examinationOpintions,clientCode,contractNumber);
			if(count > 0){
				logger.debug("审核文字通过，文字id为："+id);
			}else{
				logger.debug("审核文字时发生异常，文字id为："+id);
			}
			
		}else{
			logger.debug("MessageMeta 的id 为空");
		}
		
		logger.debug("审核文字素材方法 goAuditMessageMetas()调用结束");
		
		return "listAuditMetasG";
	}

	/**
	 * 
	 * 跳转到审核页面
	 */

	public String goAuditRedirect(){
		return "goAuditRedirect";
	} 
	
	/**
	 * 调转到添加页面
	 */
	
	public String goAddMetas(){
		return "goMetasRedirect";
	}
	
	/**
	 * 提交修改图片信息
	 */           
	public String updateImageMeta(){
		logger.debug("updaImageMeta 被调用了");
		
		int count = 0;
		int count2 = 1;
		
		Date date = new Date();
		resource.setModifyTime(date);
		
		Date s = resource.getStartTime();
		
		System.out.println("s="+s);
		
		
		count = adAssetsService.updateImageMeta(imageMeta);
		count2 = adAssetsService.updateResource(resource);
		
		if(count>0 && count2>0 ){
			logger.debug("更新用户成功");
		}else{
			logger.warn("更新用户时发生异常");
		}
		return SUCCESS;
	}
	
	/**
	 * 提交修改视频信息
	 */
	public String updateVideoMeta(){
		logger.debug("updaVideoMeta 被调用了");
		String flag = null;
		int count = 0;
		int count2 = 0;
		
		Date date = new Date();
		resource.setModifyTime(date);
		
		count = adAssetsService.updateVideoMeta(videoMeta);
		count2 =  adAssetsService.updateResource(resource);
		if(count>0 && count2>0){
			flag = SUCCESS;
			logger.debug("更新用户成功");
		}else{
			flag = ERROR;
			logger.warn("更新用户时发生异常");
		}
		return flag;
		
	}
	
	
	/**
	 * 提交修改文字消息信息
	 */
	
	public String updateMessageMeta(){
		logger.debug("updaMessageMeta 被调用了");
		String flag = null;
		int count = 0;
		int count2 = 0;
		
		Date date = new Date();
		resource.setModifyTime(date);
		//修改完素材变为待审核状态
		resource.setState(ResourceMetasConstant.AUDIT_NOT_PASS);
		
		count =  adAssetsService.updateMessageMeta(messageMeta);
		count2 =  adAssetsService.updateResource(resource);
		if(count>0 && count2 >0){
			flag = SUCCESS;
			logger.debug("更新用户成功");
		}else{
			flag = ERROR;
			logger.warn("更新用户时发生异常");
		}
		
		return flag;
		
	}
	
	/**
	 * 进入修改图片素材页面
	 * 
	 */           
	public String updateImageMetaRedirect(){
		
		String positionName = "";
		
		//得到配置文件信息
		String resourceRealPath = ConfigureProperties.getInstance().get("temporaryResourceFilePath");
		
		String id = request.getParameter("id");
		
		String resourceId = request.getParameter("resourceId");
		
		if(imageMeta == null){
			imageMeta = new ImageMeta();
		}
		
		if(id!=null&&!"".equals(id) && resourceId !=null && !"".equals(resourceId) ){
			id = id.trim();
			resourceId = resourceId.trim();
			imageMeta = adAssetsService.getImageMetaById(Integer.parseInt(resourceId));
			
			resource = adAssetsService.getResourceById(Integer.parseInt(id));
			
			Integer advertPositionId = resource.getAdvertPositionId();
			//根据素材资源表中的广告位Id,得到广告位表单数据
			List<AdvertPosition> listAdvertPosition = advertPositionService.getAdvertPositionById(advertPositionId);
			
			String temporary = imageMeta.getTemporaryFilePath();
			
			resourcePath = resourceRealPath+temporary;
			
			if(listAdvertPosition.size()>0){
				//根据素材资源表中的广告位Id,得到广告位表单中的广告位名称
				positionName = listAdvertPosition.get(0).getPositionName();
			}
			
			
		}else {
			logger.debug("ImageMeta id 为空");
			
		}		
		
		request.setAttribute("positionName", positionName);
		request.setAttribute("resourcePath",resourcePath);
		
		request.setAttribute("imageMeta", imageMeta);
		request.setAttribute("resource", resource);
		logger.debug("updateImageMetaRedirect 被调用结束了");
		return "updateImage";
	}
	
	/**
	 * 进入修改视频素材页面
	 * 
	 */
	public String updateVideoMetaRedirect(){
		
		String positionName = "";
		
		//得到配置文件信息
		String resourceRealPath = ConfigureProperties.getInstance().get("temporaryResourceFilePath");
		
		String id = request.getParameter("id");
		
		String resourceId = request.getParameter("resourceId");
		
		if(videoMeta == null){
			videoMeta = new VideoMeta();
		}
		
		if( id != null && !"".equals(id) && resourceId !=null && !"".equals(resourceId) ){
			id= id.trim();
			resourceId = resourceId.trim();
			videoMeta = adAssetsService.getVideoMetaById(Integer.parseInt(resourceId));
			resource = adAssetsService.getResourceById(Integer.parseInt(id));
			
			Integer advertPositionId = resource.getAdvertPositionId();
			//根据素材资源表中的广告位Id,得到广告位表单数据
			List<AdvertPosition> listAdvertPosition = advertPositionService.getAdvertPositionById(advertPositionId);
			
			String temporary = videoMeta.getTemporaryFilePath();
			
			resourcePath = resourceRealPath+temporary;
			
			if(listAdvertPosition.size()>0){
				positionName = listAdvertPosition.get(0).getPositionName(); 
			}
			
			
		}else {
			logger.debug("ImageMeta id 为空");
		}
		
		request.setAttribute("positionName",positionName);
		request.setAttribute("resourcePath",resourcePath);
		
		request.setAttribute("videoMeta", videoMeta);
		request.setAttribute("resource", resource);
		
		logger.debug("----updateVideoMetaRedirect 被调用结束了-----");
		return "updateVideo";
	}
	
	/**
	 * 进入修改文字素材页面
	 * 
	 */
	public String updateMessageMetaRedirect() throws Exception{
		
		String positionName = "";
		
		StringBuffer str = new StringBuffer("");
	    String st = "";
		
		String id = request.getParameter("id");
		
		String resourceId = request.getParameter("resourceId");
		
		System.out.println("----修改信息-----id="+id);
		if( id != null && !"".equals(id) && resourceId !=null && !"".equals(resourceId)){
			id= id.trim();
			resourceId = resourceId.trim();
			messageMeta = adAssetsService.getMessageMetaById(Integer.parseInt(resourceId));
			
			
			/*byte[] content = null;
			BufferedInputStream bis = null;
			try {
				
				byte[] contentBlob = messageMeta.getContent();
				
				bis = new BufferedInputStream(contentBlob.getBinaryStream());
				content = new byte[(int)contentBlob.length()];
				int len = content.length;
				int offest = 0;
				int read = 0;
				while(offest<len&&(read=bis.read(content, offest,len-offest))>0){
					offest+=read;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			/*try {
				InputStreamReader isr;
				java.sql.Blob contentBlob = (java.sql.Blob) messageMeta.getContent();
				InputStream is = contentBlob.getBinaryStream();
			    isr = new InputStreamReader(is,"gb2312");
			    BufferedReader br = new BufferedReader(isr);
		        String data = "";
		       while((data = br.readLine())!=null){
		        str.append(data+" "); 
		       }
		       st = str.toString();
			} catch (Exception e) {
				e.printStackTrace();
		    	
		    }*/
			
			resource = adAssetsService.getResourceById(Integer.parseInt(id));
			
			Integer advertPositionId = resource.getAdvertPositionId();
			//根据素材资源表中的广告位Id,得到广告位表单数据
			List<AdvertPosition> listAdvertPosition = advertPositionService.getAdvertPositionById(advertPositionId);
			
			if(listAdvertPosition.size()>0){
				positionName = listAdvertPosition.get(0).getPositionName(); 
			}
			
		}else {
			logger.debug("MessageMeta id 为空");
		}
		
		request.setAttribute("positionName",positionName);
		request.setAttribute("content", st);
		request.setAttribute("messageMeta", messageMeta);
		request.setAttribute("resource", resource);
		
		logger.debug("updateMessageMetaRedirect 被调用结束了");
		return "updateMessage";
	}
	
	
	/**
	 * 进入修改运行期图片素材页面(Real 表)
	 * 
	 */           
	public String updateImageMetaRealRedirect(){
		
		String positionName = "";
		
		//得到配置文件信息
		//String resourceRealPath = ConfigureProperties.getInstance().get("temporaryResourceFilePath");
		
		String id = request.getParameter("id");
		
		String resourceId = request.getParameter("resourceId");
		
		System.out.println("----修改信息--的id为---"+id.trim()+"----");
		
		System.out.println("----修改信息--的resourceId为---"+resourceId.trim()+"----");
		
		if(imageReal == null){
			imageReal = new ImageReal();
		}
		
		if(resourceReal == null){
			resourceReal = new ResourceReal();
			
		}
		
		if(id!=null&&!"".equals(id) && resourceId !=null && !"".equals(resourceId) ){
			id = id.trim();
			resourceId = resourceId.trim();
			imageReal = adAssetsService.getImageMetaRealById(Integer.parseInt(resourceId));
			resourceReal = adAssetsService.getResourceRealById(Integer.parseInt(id));
			
			Integer advertPositionId = resourceReal.getAdvertPositionId();
			
			//根据素材资源表中的广告位Id,得到广告位表单数据
			List<AdvertPosition> listAdvertPosition = advertPositionService.getAdvertPositionById(advertPositionId);
			
			if(listAdvertPosition.size()>0){
				positionName = listAdvertPosition.get(0).getPositionName(); 
			}
			
		}else {
			logger.debug("ImageReal id 为空");
			
		}		
		
		request.setAttribute("positionName", positionName);
		request.setAttribute("imageReal", imageReal);
		request.setAttribute("resourceReal", resourceReal);
		logger.debug("updateImageMetaRealRedirect 被调用结束了");
		return "updateImageReal";
	}
	
	/**
	 * 进入修改运行期视频素材页面(Real 表)
	 * 
	 */
	public String updateVideoMetaRealRedirect(){
		
		String positionName ="";
		
		String id = request.getParameter("id");
		
		String resourceId = request.getParameter("resourceId");
		
		System.out.println("----修改信息-----id="+id);
		
		
		System.out.println("----修改信息--的resourceId为---"+resourceId.trim()+"----");
		
		if(videoReal == null){
			videoReal = new VideoReal();
		}
		
		if(resourceReal == null){
			resourceReal = new ResourceReal();
			
		}
		
		if( id != null && !"".equals(id) && resourceId !=null && !"".equals(resourceId) ){
			id= id.trim();
			resourceId = resourceId.trim();
			videoReal = adAssetsService.getVideoMetaRealById(Integer.parseInt(resourceId));
			resourceReal = adAssetsService.getResourceRealById(Integer.parseInt(id));
			
			Integer advertPositionId = resourceReal.getAdvertPositionId();
			
			//根据素材资源表中的广告位Id,得到广告位表单数据
			List<AdvertPosition> listAdvertPosition = advertPositionService.getAdvertPositionById(advertPositionId);
			
			if(listAdvertPosition.size()>0){
				positionName = listAdvertPosition.get(0).getPositionName(); 
			}
			
		}else {
			logger.debug("ImageMeta id 为空");
		}
		
		request.setAttribute("positionName",positionName);
		request.setAttribute("videoReal", videoReal);
		request.setAttribute("resourceReal", resourceReal);
		
		logger.debug("----updateVideoMetaRealRedirect 被调用结束了-----");
		return "updateVideoReal";
	}
	
	/**
	 * 进入修改运行期文字素材页面(Real 表)
	 * 
	 */
	public String updateMessageMetaRealRedirect(){
		
		String positionName ="";
		
		String id = request.getParameter("id");
		
		String resourceId = request.getParameter("resourceId");
		
		System.out.println("----修改信息-----id="+id);
		
		if(messageReal == null){
			messageReal = new MessageReal();
		}
		
		if(resourceReal == null){
			resourceReal = new ResourceReal();
		}
		
		if( id != null && !"".equals(id) && resourceId !=null && !"".equals(resourceId)){
			id= id.trim();
			resourceId = resourceId.trim();
			messageReal = adAssetsService.getMessageMetaRealById(Integer.parseInt(resourceId));
			resourceReal = adAssetsService.getResourceRealById(Integer.parseInt(id));
			
			Integer advertPositionId = resourceReal.getAdvertPositionId();
			
			//根据素材资源表中的广告位Id,得到广告位表单数据
			List<AdvertPosition> listAdvertPosition = advertPositionService.getAdvertPositionById(advertPositionId);
			
			if(listAdvertPosition.size()>0){
				positionName = listAdvertPosition.get(0).getPositionName(); 
			}
			
		}else {
			logger.debug("MessageMeta id 为空");
		}
		
		request.setAttribute("positionName",positionName);
		request.setAttribute("messageReal", messageReal);
		request.setAttribute("resourceReal", resourceReal);
		
		logger.debug("----updateMessageMetaRealRedirect 被调用结束了-----");
		return "updateMessageReal";
	}
	
	/**
	 * 进入修改问卷素材页面
	 * 
	 */
	public String  updateQuestionMetaRedirect(){
		String id = request.getParameter("id");
		System.out.println("----修改信息-----id="+id);
		if( id != null){
			id = id.trim();
		}else {
			logger.debug("Question id 为空");
			
		}
		logger.debug("updateQuestionMetaRedirect 被调用结束了");
		return "updateQuestion";
	}
	
	/**
	 * 删除运行期图片信息(Real表)
	 * 
	 */
	public String deleteImageMetaReal(Integer id ,Integer resourceId){
		
   //		String id = ServletActionContext.getRequest().getParameter("id");
		//资源id
  //		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		int count = 0;
		int count2 = 0;
		
		if(id !=null && resourceId != null){
			count2 = adAssetsService.deleteResourceRealById(id);
			count = adAssetsService.deleteImageMetaRealById(resourceId);
			if(count>0 && count2>0){
				logger.debug("-----删除图片成功，用户id为：----"+id);
			}else{
				logger.warn("-----删除图片时发生异常，用户id为：--"+id);
			}
		}else{
			logger.debug("-----ImageMeta的id 值 为空 -------");
		} 
		logger.debug("-----deleteImageMetaReal---- 被调用结束了");
		//返回客户端
		this.returnMessage("删除图片成功");
		
		return null;
	}
	
	/**
	 * 删除运行期视频信息(Real 表)
	 * 
	 */
	public String deleteVideoMetaReal(Integer id,Integer resourceId){
		
	//	String id = ServletActionContext.getRequest().getParameter("id");
		
		//资源id
	//	String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		int count = 0;
		int count2 = 0;
		if(id !=null && resourceId != null){
			count2 = adAssetsService.deleteResourceRealById(id);
			count = adAssetsService.deleteVideoMetaRealById(resourceId);
			if(count>0 && count2>0){
				logger.debug("删除视频成功，用户id为："+id);
			}else{
				logger.warn("删除视频时发生异常，用户id为："+id);
			}
		}else{
			logger.debug("--VideoMetaReal的id 值 为空 ---");
		} 
		
		logger.debug("----deleteVideoMetaReal 被调用结束了----");
		
		this.returnMessage("删除视频成功");
		
		return null;
	}
	
	/**
	 * 删除运行期文字信息(Real 表)
	 * 
	 */
	public String deleteMessageMetaReal(Integer id ,Integer resourceId){
		
	//	String id = ServletActionContext.getRequest().getParameter("id");
		//资源id
	//	String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		int count = 0;
		int count2=0;
		if(id !=null && resourceId != null){
			count2 = adAssetsService.deleteResourceRealById(id);
			count = adAssetsService.deleteMessageMetaRealById(resourceId);
			if(count>0 && count2>0){
				logger.debug("--删除视频成功，用户id为：--"+id);
			}else{
				logger.warn("--删除视频时发生异常，用户id为：--"+id);
			}
		}else{
			logger.debug("---MessageMetaReal的id 值 为空---- ");
		} 
		logger.debug("----deleteMessageMetaReal 被调用结束了-----");
		
		this.returnMessage("删除文字成功");
		return null;
		
	}
	
	/**
	 * 删除图片信息
	 * 
	 */
	public String deleteImageMeta(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		//资源id
		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		String state = ServletActionContext.getRequest().getParameter("state");
		
		//删除下线状态的素材
		if(ResourceMetasConstant.DOWNLINE_STRING.equals(state) && StringUtils.isNotBlank(state)){
			int count = 0;
			int count2 = 0;
			if(id !=null && resourceId != null){
				// 将下线状态 在资产维护期表中 更改为 删除待审核
				count2 = adAssetsService.deleteAuditResource(Integer.parseInt(id));
				
			//	count2 = adAssetsService.deleteResourceRealById(Integer.parseInt(id));
			//	count = adAssetsService.deleteImageMetaRealById(Integer.parseInt(resourceId));		
				count = 1;
				if(count>0 && count2>0){
					logger.debug("删除图片成功，用户id为："+id);
				}else{
					logger.warn("删除图片时发生异常，用户id为："+id);
				}
			}else{
				logger.debug("ImageMeta的id 值 为空 ");
			} 
		}else{
			int count = 0;
			int count2 = 0;
			
			if(id !=null && resourceId != null){
				count = adAssetsService.deleteImageMetaById(Integer.parseInt(resourceId));
				count2 = adAssetsService.deleteResourceById(Integer.parseInt(id));
				if(count>0 && count2>0){
					logger.debug("删除图片成功，用户id为："+id);
				}else{
					logger.warn("删除图片时发生异常，用户id为："+id);
				}
			}else{
				logger.debug("ImageMeta的id 值 为空 ");
			} 
		}
		logger.debug("deleteImageMeta 被调用结束了");
		//返回客户端
		this.returnMessage("删除图片成功");
		
		return null;
	}
	
	/**
	 * 删除视频信息
	 * 
	 */
	public String deleteVideoMeta(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		
		//资源id
		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		String state = ServletActionContext.getRequest().getParameter("state");
		
		//删除下线状态的素材
		if(ResourceMetasConstant.DOWNLINE_STRING.equals(state) && StringUtils.isNotBlank(state)){
			
			int count = 0;
			int count2 = 0;
			if(id !=null && resourceId != null){
				
				// 将下线状态 在资产维护期表中 更改为 删除待审核
				count2 = adAssetsService.deleteAuditResource(Integer.parseInt(id));
				count = 1;
				
		//		count2 = adAssetsService.deleteResourceRealById(Integer.parseInt(id));
		//		count = adAssetsService.deleteVideoMetaRealById(Integer.parseInt(resourceId));
				if(count>0 && count2>0){
					logger.debug("删除视频成功，用户id为："+id);
				}else{
					logger.warn("删除视频时发生异常，用户id为："+id);
				}
			}else{
				logger.debug("VideoMeta的id 值 为空 ");
			} 
		}else{
			int count = 0;
			int count2 = 0;
			if(id !=null && resourceId != null){
				count = adAssetsService.deleteVideoMetaById(Integer.parseInt(resourceId));
				count2 = adAssetsService.deleteResourceById(Integer.parseInt(id));
				if(count>0 && count2>0){
					logger.debug("删除视频成功，用户id为："+id);
				}else{
					logger.warn("删除视频时发生异常，用户id为："+id);
				}
			}else{
				logger.debug("VideoMeta的id 值 为空 ");
			} 
			
		}
		
		logger.debug("---deleteVideoMeta 被调用结束了-----");
		
		this.returnMessage("删除视频成功");
		
		return null;
	}
	
	/**
	 * 删除文字信息
	 * 
	 */
	public String deleteMessageMeta(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		//资源id
		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		
		String state = ServletActionContext.getRequest().getParameter("state");
		
		//删除下线状态的素材
		if("3".equals(state) && StringUtils.isNotBlank(state)){
			int count = 0;
			int count2=0;
			if(id !=null && resourceId != null){
				
				// 将下线状态 在资产维护期表中 更改为 删除待审核
				count2 = adAssetsService.deleteAuditResource(Integer.parseInt(id));
				count = 1;
				  
			//	count2 = adAssetsService.deleteResourceRealById(Integer.parseInt(id));
			//	count = adAssetsService.deleteMessageMetaRealById(Integer.parseInt(resourceId));
				
				if(count>0 && count2>0){
					logger.debug("删除视频成功，用户id为："+id);
				}else{
					logger.warn("删除视频时发生异常，用户id为："+id);
				}
			}else{
				logger.debug("MessageMeta的id 值 为空 ");
			} 
		}else{
			
			int count = 0;
			int count2=0;
			if(id !=null && resourceId != null){
				count = adAssetsService.deleteMessageMetaById(Integer.parseInt(resourceId));
				count2 = adAssetsService.deleteResourceById(Integer.parseInt(id));
				
				if(count>0 && count2>0){
					logger.debug("删除文字成功，用户id为："+id);
				}else{
					logger.warn("删除文字时发生异常，用户id为："+id);
				}
			}else{
				logger.debug("MessageMeta的id 值 为空 ");
			} 
			
		}
		
		logger.debug("----deleteMessageMeta 被调用结束了-----");
		
		this.returnMessage("删除文字成功");
		return null;
		
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
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * render Text
	 */
	public void renderText(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	/**
	 * 取得request
	 * @return
	 */
	public HttpServletRequest getRequest(){
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}
	
	
	
	//AJAX 返回客户端方法
	public void returnMessage(String msg){
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
	public ImageMeta getImageMeta() {
		return imageMeta;
	}
	public void setImageMeta(ImageMeta imageMeta) {
		this.imageMeta = imageMeta;
	}
	public VideoMeta getVideoMeta() {
		return videoMeta;
	}
	public void setVideoMeta(VideoMeta videoMeta) {
		this.videoMeta = videoMeta;
	}
	public MessageMeta getMessageMeta() {
		return messageMeta;
	}
	public void setMessageMeta(MessageMeta messageMeta) {
		this.messageMeta = messageMeta;
	}
	public List<Object> getListAdAssets() {
		return listAdAssets;
	}
	public void setListAdAssets(List<Object> listAdAssets) {
		this.listAdAssets = listAdAssets;
	}
	public AdAssetsService getAdAssetsService() {
		return adAssetsService;
	}

	public void setAdAssetsService(AdAssetsService adAssetsService) {
		this.adAssetsService = adAssetsService;
	}

	public AdContentAction() {
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public List<Resource> getListAdAssetResource() {
		return listAdAssetResource;
	}
	public void setListAdAssetResource(List<Resource> listAdAssetResource) {
		this.listAdAssetResource = listAdAssetResource;
	}
	public ImageReal getImageReal() {
		return imageReal;
	}
	public void setImageReal(ImageReal imageReal) {
		this.imageReal = imageReal;
	}
	public VideoReal getVideoReal() {
		return videoReal;
	}
	public void setVideoReal(VideoReal videoReal) {
		this.videoReal = videoReal;
	}
	public MessageReal getMessageReal() {
		return messageReal;
	}
	public void setMessageReal(MessageReal messageReal) {
		this.messageReal = messageReal;
	}
	public ResourceReal getResourceReal() {
		return resourceReal;
	}
	public void setResourceReal(ResourceReal resourceReal) {
		this.resourceReal = resourceReal;
	}
	public List<ResourceReal> getListAdAssetResourceReal() {
		return listAdAssetResourceReal;
	}
	public void setListAdAssetResourceReal(
			List<ResourceReal> listAdAssetResourceReal) {
		this.listAdAssetResourceReal = listAdAssetResourceReal;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public AuditMetasFormBean getAuditMetasFormBean() {
		return auditMetasFormBean;
	}
	public void setAuditMetasFormBean(AuditMetasFormBean auditMetasFormBean) {
		this.auditMetasFormBean = auditMetasFormBean;
	}
	public String getResourcePath() {
		return resourcePath;
	}
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
	public CustomerService getCustomerService() {
		return customerService;
	}
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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
	public AdvertPositionService getAdvertPositionService() {
		return advertPositionService;
	}
	public void setAdvertPositionService(AdvertPositionService advertPositionService) {
		this.advertPositionService = advertPositionService;
	}
	public Map<Integer, String> getMapAdvertPosition() {
		return mapAdvertPosition;
	}

	public void setMapAdvertPosition(Map<Integer, String> mapAdvertPosition) {
		this.mapAdvertPosition = mapAdvertPosition;
	}

	public AdvertPositionTypeService getAdvertPositionTypeService() {
		return advertPositionTypeService;
	}
	public void setAdvertPositionTypeService(
			AdvertPositionTypeService advertPositionTypeService) {
		this.advertPositionTypeService = advertPositionTypeService;
	}
}
