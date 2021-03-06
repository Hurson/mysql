package com.avit.dtmb.material.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.material.service.MaterialService;
import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.position.service.DPositionService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.meterial.action.MeterialManagerAction;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.bean.PriorityWordBean;
import com.dvnchina.advertDelivery.meterial.bean.QuestionInfo;
import com.dvnchina.advertDelivery.meterial.bean.QuestionType;
import com.dvnchina.advertDelivery.meterial.service.MeterialManagerService;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@ParentPackage("default")
@Namespace("/dmaterial")
@Scope("prototype")
@Controller
public class MeterialAction extends BaseAction{
	private Logger logger = Logger.getLogger(MeterialManagerAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 3629860605255061545L;
	@Resource
	private MaterialService materialService;
	@Resource
	private MeterialManagerService meterialManagerService;
	@Resource
	private DPositionService dPositionService;
	private PageBeanDB page;
	private DResource meterialQuery;
	private List<MaterialCategory> materialCategoryList;
	private PageBeanDB adPositionPage;
	private Integer advertPositionId;
	private DResource resource;
	  /**操作日志类*/
	public OperateLog operLog;
	private OperateLogService operateLogService;
	
	/**
	 * 批量上传文件名集合
	 */
	private String imageFileNames;
	private String videoFileNames;
	private String uploadDir;
    private String isAuditTag;//判断是用于审核查询列表还是维护查询列表
    private static final int BUFFER_SIZE = 16 * 1024;
    private static ConfigureProperties config = ConfigureProperties.getInstance();
    private String htmlPath;//用于判断修改页面是否有重新上传文件
    private String questionw;
    private String w2;
    private String sssspath;
    private List<Customer> customerPage;
    private String localFilePath;
    private VideoMeta videoMeta;
    private MessageMeta textMeta;
	private ImageMeta imageMeta;
	private ImageMeta zipMeta;
	private DAdPosition adPositionQuery; //子广告位查询条件
	private DAdPosition position; //子广告位查询条件
	private VideoSpecification videoSpecification;
	
	private ImageSpecification imageSpecification;
	
    private ImageSpecification zipSpecification;
    private String materialId;
    private String dataIds;
	
	
	
	public MeterialAction(){
		
	}
	@Action(value = "queryMaterialList",results={@Result(name="success",location="/page/material/new/Dmaterial/DmaterialList.jsp")})
	public String queryMaterialList(){
		materialCategoryList=meterialManagerService.getMaterialCategoryList();
		if(page==null){
			page = new PageBeanDB();
		}
		try{
			if(meterialQuery==null){
                meterialQuery=new DResource();
            }
			page = materialService.queryDMaterialList(meterialQuery,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value = "auditMaterialList",results={@Result(name="success",location="/page/material/new/Dmaterial/auditMaterialList.jsp")})
	public String auditMaterialList(){
		materialCategoryList=meterialManagerService.getMaterialCategoryList();
		if(page==null){
			page = new PageBeanDB();
		}
		try{
			if(meterialQuery==null){
                meterialQuery=new DResource();
            }
			if(getLoginUser().getRoleType() == 1){ //广告商（不具有素材审核的权限）
        		return SUCCESS;
        	}
            meterialQuery.setStatus("0");
			page = materialService.queryDMaterialList(meterialQuery,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 删除素材
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "deleteMaterial",results={@Result(name="success",type="redirect", location="queryMaterialList.action")})
	public String deleteMaterial(){    

        try{
        	materialService.deleteMaterial(dataIds);
        	//记录操作日志
        	StringBuffer delInfo = new StringBuffer();
    		delInfo.append("删除素材：");
            delInfo.append("共").append(dataIds.split(",").length).append("条记录(ids:"+dataIds+")");
			operType = "operate.delete";
			operInfo = delInfo.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_DRESOURCE);
			operateLogService.saveOperateLog(operLog);
			
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("删除素材信息异常", e);
        }
        
        return SUCCESS;
    }
	
	@Action(value = "getMateSpec")
	public void getMateSpec(){
		String result = materialService.getMaterialSpecification(position);
		this.renderJson(result);
	}
	
	@Action(value = "checkMaterialExist")
	public void checkMaterialExist(){
		String result = materialService.checkMaterialExist(resource);
		this.renderText(result);
	}
	
	/**
	 * 进入添加页面
	 * 
	 */
	@Action(value = "initAdd",results={@Result(name="success",location="/page/material/new/Dmaterial/addMaterial.jsp")})
	public String initAdd(){
		materialCategoryList = meterialManagerService.getMaterialCategoryList();
		return SUCCESS;
	}
	
	/**
	 * 选择子广告位
	 * @return 
	 * @return
	 */
	@Action(value = "selectAdPosition",results={@Result(name="success",location="/page/material/new/Dmaterial/DselectAdPosition.jsp")})
	public String selectAdPosition(){
		 if (adPositionPage==null)
	        {
	            adPositionPage  =  new PageBeanDB();
	        }
		 try{
			 adPositionPage = materialService.queryPositonList(adPositionPage.getPageNo(), adPositionPage.getPageSize());
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return SUCCESS;
	}
	
	/**
     * 保存素材
     * @return
     * @throws IOException
     */
	/**
	 * @return
	 */
	@Action(value= "saveMaterialBackup",results={@Result(name="success",location="/page/material/new/Dmaterial/DmaterialList.jsp")})
	public String saveMaterialBackup() {  
	    try{
	        UserLogin userLogin = (UserLogin) this.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
	        uploadDir = "images/material";
		    resource.setIsDefault(0);
		    if(resource.getResourceType()==1){
		      //视频素材	    	
		        if(resource.getId()==null){
		        	operType = "operate.add";
		            //新增
		        	videoFileNames = videoFileNames.replace(" ","");//获取批量文件名集合
                    String videoFileNamestemp[]= videoFileNames.split(",");
                    String resourceName="";
                    String videoKeyword="";
                    for (int i=0;i<videoFileNamestemp.length;i++){
                        //String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
                        String[] prams = videoFileNamestemp[i].split("&");
                        resourceName=this.getRequest().getParameter("videoFileName-"+prams[0]);
                        videoKeyword=this.getRequest().getParameter("videoKeyword-"+prams[0]);
                        String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+prams[0];               
                        System.out.println("素材名:"+resourceName);
                             /**  发送文件至ftp */
                            boolean isSuccess = false;
                            String uploadPath=config.getValueByKey("materila.ftp.tempPath");
                            isSuccess = sendFile(uploadAllDir,uploadPath);                  
                                               
                            //计算文件大小
                            File uploadfile = new File(uploadAllDir);
                            InputStream in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
                            byte[] buffer = new byte[BUFFER_SIZE];
                            int len = 0;
                            int bytesum = 0;
                            while ((len = in.read(buffer)) > 0) {
                                 bytesum += len;
                            }
                            in.close();
                            //删除临时文件
                            if(uploadfile.exists()&&uploadfile.isFile()){
                                uploadfile.delete();
                            }

                            VideoMeta  videoMetaTemp = new VideoMeta();

                            videoMetaTemp.setName(prams[0]);
                            videoMetaTemp.setTemporaryFilePath(uploadPath);
                            videoMetaTemp.setFileSize(Integer.valueOf(bytesum).toString());
                            videoMetaTemp.setRunTime(prams[1]+"秒");
                            meterialManagerService.saveVideoMaterial(videoMetaTemp);                    
                            
                            //保存素材表
                            DResource materialTemp =new DResource();
                            //Contract contract = meterialManagerService.getContractByID(material.getContractId());
                            if(userLogin.getRoleType()==2){
                                //登陆用户为运营商操作员
                                materialTemp.setCustomerId(0);
                            }else{
                                materialTemp.setCustomerId(userLogin.getCustomerId());
                            }
                            
                            materialTemp.setStatus("0");       
                            materialTemp.setResourceId(videoMetaTemp.getId());                                               
                            materialTemp.setResourceName(resourceName);
                          //  materialTemp.setKeyWords(videoKeyword);
                            materialTemp.setCreateTime(new Date());//资产创建时间
                            //复制公共属性
                            materialTemp.setResourceType(resource.getResourceType());
                            materialTemp.setCategoryId(resource.getCategoryId());
                            materialTemp.setPositionCode(resource.getPositionCode());
                            materialTemp.setIsDefault(0);
                            materialTemp.setOperationId(userLogin.getUserId());
                            materialService.saveDResource(materialTemp);
                            //操作日志
                            operInfo = materialTemp.toString();
                            operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
                            operateLogService.saveOperateLog(operLog);
                    }
		        	
		            
		        }else{
		        	operType = "operate.update";
		            //修改
		            if(localFilePath.equals("")){
	                    //没有重新上传视频
		                VideoMeta videoMetaTemp = materialService.getVideoMetaByID(videoMeta.getId());
		                videoMeta.setName(videoMetaTemp.getName());
		                videoMeta.setTemporaryFilePath(videoMetaTemp.getTemporaryFilePath());
		                videoMeta.setFileSize(videoMetaTemp.getFileSize());
		                meterialManagerService.saveVideoMaterial(videoMeta);                
	                }else{
	                    String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
	                    String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+materialName;
	                   
	                         /**  发送文件至ftp */
	                        boolean isSuccess = false;
	                        String uploadPath=config.getValueByKey("materila.ftp.tempPath");
	                        isSuccess = sendFile(uploadAllDir,uploadPath);                        
	                        //计算文件大小
	                        File uploadfile = new File(uploadAllDir);
	                        InputStream in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
	                        byte[] buffer = new byte[BUFFER_SIZE];
	                        int len = 0;
	                        int bytesum = 0;
	                        while ((len = in.read(buffer)) > 0) {
	                             bytesum += len;
	                        }
	                        in.close();
	                        //删除临时文件
	                        if(uploadfile.exists()&&uploadfile.isFile()){
	                        	uploadfile.delete();
	                        }
	                    
	                        
	                        if(videoMeta==null){
	                            videoMeta = new VideoMeta();
	                        }
	                        videoMeta.setName(materialName);
	                        videoMeta.setTemporaryFilePath(uploadPath);
	                        videoMeta.setFileSize(Integer.valueOf(bytesum).toString());
	                        meterialManagerService.saveVideoMaterial(videoMeta);
	                }
		            resource.setModifyTime(new Date());//资产修改时间
		        
	                resource.setStatus("0");       
	                resource.setResourceId(videoMeta.getId());
	                //material.setOperationId(userLogin.getUserId());
	                materialService.saveDResource(resource);
	                //操作日志
	                operInfo = resource.toString();
	                operLog = this.setOperationLog(Constant.OPERATE_MODULE_DRESOURCE);
	                operateLogService.saveOperateLog(operLog);
		        }
		        
		        
		    }
		    
		    if(resource.getResourceType()==0){
	            //图片素材
	                if(resource.getId()==null){
	                	operType = "operate.add";
	                    
	                    imageFileNames = imageFileNames.replace(" ","");//获取批量文件名集合
	                    String imageFileNamestemp[]= imageFileNames.split(",");
	                    String resourceName="";
	                    String imageKeyword="";
	                    String imageUrl="";
	                    for (int i=0;i<imageFileNamestemp.length;i++){
	                         resourceName=this.getRequest().getParameter("imageFileName-"+imageFileNamestemp[i]);
	                         imageKeyword=this.getRequest().getParameter("imageKeyword-"+imageFileNamestemp[i]);
	                         imageUrl=this.getRequest().getParameter("imageUrl-"+imageFileNamestemp[i]);
	                         System.out.println("素材名:"+resourceName);
	                         
	                         String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+imageFileNamestemp[i];
	                         File uploadfile = new File(uploadAllDir);
	                         
	                         /**  发送文件至ftp */
	                            boolean isSuccess = false;
	                            String uploadPath=config.getValueByKey("materila.ftp.tempPath");
	                            isSuccess = sendFile(uploadAllDir,uploadPath);
	                            
	                            InputStream inputStream = new FileInputStream(uploadfile);
	                            BufferedImage bi = ImageIO.read(inputStream); 
	                            String fileWidth = "0";
	                            String fileHigh = "0";
	                            if(bi!=null){
	                            	fileWidth= String.valueOf(bi.getWidth());
		                            fileHigh= String.valueOf(bi.getHeight());
	                            }
	                            String imageFormat = imageFileNamestemp[i].substring(imageFileNamestemp[i].indexOf(".")+1, imageFileNamestemp[i].length());                      
	                            //计算文件大小
	                            InputStream in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
	                            byte[] buffer = new byte[BUFFER_SIZE];
	                            int len = 0;
	                            int bytesum = 0;
	                            while ((len = in.read(buffer)) > 0) {
	                                 bytesum += len;
	                            }
	                            in.close();
	                            inputStream.close();
	                            //删除临时文件
	                            if(uploadfile.exists()&&uploadfile.isFile()){
	                                uploadfile.delete();
	                            }
	                            

	                        ImageMeta imageMetaTemp = new ImageMeta(); 
	                        
	                        imageMetaTemp.setImageUrl(imageUrl);
	                        imageMetaTemp.setName(imageFileNamestemp[i]);
	                        imageMetaTemp.setFileSize(String.valueOf(bytesum));
	                        imageMetaTemp.setFileHeigth(fileHigh);
	                        imageMetaTemp.setFileWidth(fileWidth);
	                        imageMetaTemp.setFileFormat(imageFormat);
	                        imageMetaTemp.setTemporaryFilePath(uploadPath);                  
	                        meterialManagerService.saveImageMaterial(imageMetaTemp);
	                        
	                        
	                      //保存素材表
	                        //Contract contract = meterialManagerService.getContractByID(material.getContractId());
	                        DResource materialTemp =new DResource();
	                        if(userLogin.getRoleType()==2){
                                //登陆用户为运营商操作员
                                materialTemp.setCustomerId(0);
                            }else{
                                materialTemp.setCustomerId(userLogin.getCustomerId());
                            }
	                        
	                        materialTemp.setStatus("0");                      
	                        materialTemp.setResourceId(imageMetaTemp.getId());
	                        materialTemp.setResourceName(resourceName);
	                        //materialTemp.setDescription(imageKeyword);
	                        materialTemp.setCreateTime(new Date());//资产创建时间
	                        //复制公共属性
	                        materialTemp.setResourceType(resource.getResourceType());
	                        materialTemp.setCategoryId(resource.getCategoryId());
	                        materialTemp.setPositionCode(resource.getPositionCode());
	                        materialTemp.setIsDefault(0);
	                        materialTemp.setOperationId(userLogin.getUserId());
	                        materialService.saveDResource(materialTemp);
	                   
	                        //操作日志
	                        operInfo = materialTemp.toString();
	                        operLog = this.setOperationLog(Constant.OPERATE_MODULE_DRESOURCE);
	                        operateLogService.saveOperateLog(operLog);
	                    }
	                    
	                    
	                        
	                }else{
	                	operType = "operate.update";
	                    //修改 
	                    if(localFilePath.equals("")){
	                        //没有重新上传图片
	                    	if(imageMeta != null){
	                    		meterialManagerService.updateImageUrl(imageMeta);
	                        }
	                    }else{
	                        String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
	                        String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+materialName;
	                        File uploadfile = new File(uploadAllDir);
	                        
	                        
	                            /**  发送文件至ftp */
	                            boolean isSuccess = false;
	                            String uploadPath=config.getValueByKey("materila.ftp.tempPath");
	                            isSuccess = sendFile(uploadAllDir,uploadPath);
	                            
	                            InputStream inputStream = new FileInputStream(uploadfile);
	                            BufferedImage bi = ImageIO.read(inputStream);   
	                            String fileWidth = String.valueOf(bi.getWidth());
	                            String fileHigh = String.valueOf(bi.getHeight());
	                            String imageFormat = materialName.substring(materialName.indexOf(".")+1, materialName.length());
	                            //计算文件大小
	                            InputStream in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
	                            byte[] buffer = new byte[BUFFER_SIZE];
	                            int len = 0;
	                            int bytesum = 0;
	                            while ((len = in.read(buffer)) > 0) {
	                                 bytesum += len;
	                            }
	                            in.close();
	                            inputStream.close();
	                            //删除临时文件
	                            if(uploadfile.exists()&&uploadfile.isFile()){
	                            	uploadfile.delete();
	                            }
	                            
	                        if(imageMeta==null){
	                            imageMeta = new ImageMeta();
	                        }
	                        imageMeta.setName(materialName);
	                        imageMeta.setFileSize(String.valueOf(bytesum));
	                        imageMeta.setFileHeigth(fileHigh);
	                        imageMeta.setFileWidth(fileWidth);
	                        imageMeta.setFileFormat(imageFormat);
	                        imageMeta.setTemporaryFilePath(uploadPath);
	                        
	                        meterialManagerService.saveImageMaterial(imageMeta);
	                    }
	                    resource.setModifyTime(new Date());//资产修改时间
	              
	                    resource.setStatus("0");                      
	                    resource.setResourceId(imageMeta.getId());
	                    //material.setOperationId(userLogin.getUserId());
	                    materialService.saveDResource(resource);
	               
	                    //操作日志
	                    operInfo = resource.toString();
	                    operLog = this.setOperationLog(Constant.OPERATE_MODULE_DRESOURCE);
	                    operateLogService.saveOperateLog(operLog);
	                }
	                
	        }
		    
		    if(resource.getResourceType()==2){
		    	
		    	String[] words = textMeta.getContentMsg().replace("－", "-").replace("—", "-").split(",");
		    	String[] priorities = textMeta.getPriority().replace(" ", "").split(",");
		    	List<PriorityWordBean> list = new ArrayList<PriorityWordBean>();
		    	for(int i = 0; i < words.length; i++){
		    		PriorityWordBean entity = new PriorityWordBean();
		    		entity.setWord(words[i].trim());
		    		entity.setPriority(Integer.valueOf(priorities[i]));
		    		list.add(entity);
		    	}
		    	Gson gson = new Gson();
		    	
	            byte[] contentBlob = gson.toJson(list).getBytes("gbk");
		           
	            textMeta.setContent(contentBlob);

	            meterialManagerService.saveTextMaterial(textMeta);
	
	            resource.setStatus("0");  
	            if(resource.getId()==null){
	            	operType = "operate.add";
	                //新增
	                resource.setCreateTime(new Date());
	                resource.setOperationId(userLogin.getUserId());
	                if(userLogin.getRoleType()==2){
	                    //登陆用户为运营商操作员
	                    resource.setCustomerId(0);
	                }else{
	                    resource.setCustomerId(userLogin.getCustomerId());
	                }
	            }else{
	            	operType = "operate.update";
	                //修改
	                resource.setModifyTime(new Date());
	            }           
	            resource.setResourceId(textMeta.getId());
	            
	            materialService.saveDResource(resource);
	            //操作日志
		        operInfo = resource.toString();
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_DRESOURCE);
				operateLogService.saveOperateLog(operLog);
		    }
		    isAuditTag="0";
		    meterialQuery=null;
		    queryMaterialList();
		    
	    }catch(Exception e){
	    	e.printStackTrace();
	    	logger.error("保存素材异常", e);
	    }

	    return SUCCESS;
	}
	/**
     * 发送素材文件数据
     */
    private boolean sendFile(String localPathDir, String uploadPath){
        FtpUtils ftp = null;
        logger.info("开始发送--"+localPathDir+"--文件！");
            try {
                ftp = new FtpUtils();
                ftp.connectionFtp();
                ftp.sendFileToFtp(localPathDir ,  uploadPath);
                logger.info("素材文件"+localPathDir+"-- 发送成功 --");
            } catch (Exception e) {
                logger.info("素材文件"+localPathDir+"--发送失败！！！！"+e);
                e.printStackTrace();
                logger.error("素材文件发送异常", e);
                return false;
            } finally{
                if (ftp != null) {
                    ftp.closeFTP();
                }
            }
            return true;
    }
    /**
     * @author hudongyu
     * 审核素材
     * @return
     */
    @Action(value = "auditMaterial",results={@Result(name="success",type="redirect",location="auditMaterialList.action")})
    public String auditMaterial() {
        HttpServletRequest request = this.getRequest();
        String auditFlag = request.getParameter("auditFlag");      
        String reason = request.getParameter("reason");

        String materialId = request.getParameter("materialId");
        //获取素材临时表信息
        DResource materialTemp = materialService.getMaterialByID(Integer.parseInt(materialId));
        if(auditFlag.equals("1")){
            //审核通过          
            materialTemp.setStatus("2");           
            materialTemp.setExaminationOpintions(reason);
            materialTemp.setAuditTime(new Date());
            materialService.saveDResource(materialTemp);
            //ResourceReal materialReal = copyToResourceReal(materialTemp);
            //meterialManagerService.saveResourceReal(materialReal);
            //判断素材类型
            if(materialTemp.getResourceType()==0){
                //图片
                ImageMeta  imageMetaTemp = materialService.getImageMetaByID(materialTemp.getResourceId());
                ImageReal imageMetaReal = copyToImageReal(imageMetaTemp);
                meterialManagerService.saveImageMaterialReal(imageMetaReal);
                //FTP文件复制转移
                FtpUtils ftp = null;
                    try {
                        ftp = new FtpUtils();
                        ftp.connectionFtp();
                        String localFileName=imageMetaTemp.getName();
                        String localDirectory=config.getValueByKey("resource.locationPath"); ;
                        String remoteFileName=imageMetaTemp.getName();
                        String remoteDirectory=imageMetaTemp.getTemporaryFilePath();
                        ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");
                        ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName);    
                       
                        //删除本地文件
                        File localFile =new File(".././"+localFileName);
                        if(localFile.exists()&&localFile.isFile()){
                            localFile.delete();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("图片素材FTP上传异常", e);
                    } finally{
                        if (ftp != null) {
                            ftp.closeFTP();
                        }
                    }
            }
            if(materialTemp.getResourceType()==1){
                //视频
                VideoMeta videoMetaTemp = materialService.getVideoMetaByID(materialTemp.getResourceId());
                VideoReal videoReal = copyToVideoReal(videoMetaTemp);
                
                //FTP文件复制转移
                FtpUtils ftp = null;
                    try {
                        ftp = new FtpUtils();
                        ftp.connectionFtp();
                        String localFileName=videoMetaTemp.getName();
                        //String localDirectory=config.getValueByKey("resource.locationPath"); 
                        String remoteFileName=videoMetaTemp.getName();
                        String remoteDirectory=videoMetaTemp.getTemporaryFilePath();
                        ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath"); 
                        ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName);
                        videoReal.setVideoPumpPath(videoReal.getFormalFilePath());
                                                        
                       meterialManagerService.saveVideoMaterialReal(videoReal);

                        //删除本地文件
                        File localFile =new File(".././"+localFileName);
                        if(localFile.exists()&&localFile.isFile()){
                            localFile.delete();
                        }

                    } catch (Exception e) {
                    	e.printStackTrace();
                        logger.error("视频素材FTP上传异常", e);
                    } finally{
                        if (ftp != null) {
                            ftp.closeFTP();
                        }
                    }
                
                
            }
            if(materialTemp.getResourceType()==2){
                //文字
                MessageMeta textMetaTemp = meterialManagerService.getTextMetaByID(materialTemp.getResourceId());
                MessageReal textMetaReal = copyToTextReal(textMetaTemp);
                meterialManagerService.saveTextMaterialReal(textMetaReal);
            }
           
            //操作日志
	        operInfo = materialTemp.toString();
			operType = "operate.aduitOk";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_DRESOURCE);
			operateLogService.saveOperateLog(operLog);
        }else{
            //审核不通过
            materialTemp.setStatus("1");
            materialTemp.setExaminationOpintions(reason);
            materialTemp.setAuditTime(new Date());
            materialService.saveDResource(materialTemp);
            //操作日志
	        operInfo = materialTemp.toString();
			operType = "operate.aduitFalse";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_DRESOURCE);
			operateLogService.saveOperateLog(operLog);
        }
        
        isAuditTag="1";
        meterialQuery=null;
     
        return SUCCESS;
    }
    @Action(value = "getAdvertPosition",results={@Result(name="successForImage",location="/page/material/new/Dmaterial/imagePreview.jsp"),
    											 @Result(name="successForVideo",location="/page/material/new/Dmaterial/videoPreview.jsp")})
    public String getAdvertPosition(){
    	HttpServletRequest request = this.getRequest();
	    adPositionQuery = dPositionService.getAdvertPosition(resource.getPositionCode());
        //返回广告位的JSON信息，用于预览
	    request.setAttribute("positionJson", Obj2JsonUtil.object2json(adPositionQuery));
	    
	    String imagePreviewLocation = request.getParameter("imagePreviewLocation"); 
	    request.setAttribute("imagePreviewLocation", imagePreviewLocation); //传递下图片的预览位置
	    
        String imagePreviewName = request.getParameter("imagePreviewName");
        String videoPreviewName = request.getParameter("videoPreviewName");
        String zipImagePreviewName = request.getParameter("zipImagePreviewName");
        if(imagePreviewName==null||imagePreviewName.equals("")){
        	 if(videoPreviewName==null||videoPreviewName.equals("")){
        		 request.setAttribute("zipImagePreviewName", zipImagePreviewName);
                 return "successForZip";
        	 }else{
                 request.setAttribute("videoPreviewName", videoPreviewName);
                 return "successForVideo";
        	 }
        }else{
            request.setAttribute("imagePreviewName", imagePreviewName);
            return "successForImage";
        }
        
        
        //return SUCCESS;
	}
    /**
     * 
     * @description: 复制图片素材表
     * @param imageMetaTemp
     * @return 
     * ImageReal
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:16:38
     */
    public  ImageReal copyToImageReal(ImageMeta imageMetaTemp){
        ImageReal imageReal =new ImageReal();
        imageReal.setId(imageMetaTemp.getId());
        imageReal.setName(imageMetaTemp.getName());
        imageReal.setFileFormat(imageMetaTemp.getFileFormat());
        imageReal.setFileSize(imageMetaTemp.getFileSize());
        imageReal.setFileHeigth(imageMetaTemp.getFileHeigth());
        imageReal.setFileWidth(imageMetaTemp.getFileWidth());
        imageReal.setImageUrl(imageMetaTemp.getImageUrl());
        imageReal.setFormalFilePath(config.getValueByKey("materila.ftp.realPath"));
        
        return imageReal;
    }
    
    /**
     * 
     * @description: 复制视频素材表 
     * @param videoMetaTemp
     * @return 
     * VideoReal
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午04:44:12
     */
    public  VideoReal copyToVideoReal(VideoMeta videoMetaTemp){
        VideoReal videoReal =new VideoReal();
        videoReal.setId(videoMetaTemp.getId());
        videoReal.setName(videoMetaTemp.getName());
        videoReal.setRunTime(videoMetaTemp.getRunTime());
        videoReal.setFormalFilePath(config.getValueByKey("materila.ftp.realPath"));
        if(videoMetaTemp.getFileSize()!=null&&!videoMetaTemp.getFileSize().equals("")){
            videoReal.setFileSize(videoMetaTemp.getFileSize());
        }
        
        
        return videoReal;
    }
	
    /**
     * 
     * @description: 复制文字素材表
     * @param textMetaTemp
     * @return 
     * MessageReal
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:01:08
     */
    public MessageReal copyToTextReal(MessageMeta textMetaTemp){
        MessageReal textReal =new MessageReal();
        textReal.setId(textMetaTemp.getId());
        textReal.setName(textMetaTemp.getName());
        textReal.setURL(textMetaTemp.getURL());
        textReal.setContent(textMetaTemp.getContent());
        
        textReal.setAction(textMetaTemp.getAction());
        textReal.setBkgColor(textMetaTemp.getBkgColor());
        textReal.setDurationTime(textMetaTemp.getDurationTime());
        textReal.setFontColor(textMetaTemp.getFontColor());
        textReal.setFontSize(textMetaTemp.getFontSize());
        textReal.setPositionVertexCoordinates(textMetaTemp.getPositionVertexCoordinates());
        textReal.setPositionWidthHeight(textMetaTemp.getPositionWidthHeight());
        textReal.setRollSpeed(textMetaTemp.getRollSpeed());
        
        return textReal;
    }
    
    /**
	 * 进入素材修改页面
	 * @return
	 * @throws ParseException
	 */
    @Action(value = "initMaterials",results={@Result(name="success",location="/page/material/new/Dmaterial/updateMaterial.jsp"),
    										 @Result(name="audit",location="/page/material/new/Dmaterial/auditMaterial.jsp")})
	public String initMaterials(){    

		  try{
	        	materialCategoryList=meterialManagerService.getMaterialCategoryList();
	            
	            int materialIdd = Integer.valueOf(materialId);
	            String statusStr = resource == null?null :resource.getStatusStr();
	            resource = materialService.getMaterialByID(materialIdd);
	            resource.setStatusStr(statusStr);
	            adPositionQuery = dPositionService.getAdvertPosition(resource.getPositionCode());
	    		//返回广告位的JSON信息，用于预览
	    		getRequest().setAttribute("positionJson", Obj2JsonUtil.object2json(adPositionQuery));
	           
	    		if(resource.getResourceType()==0){
	                //图片
	                imageMeta=meterialManagerService.getImageMetaByID(resource.getResourceId());
	                imageSpecification = materialService.getImageMateSpeci(adPositionQuery.getSpecificationId());
	            }
	            if(resource.getResourceType()==1){
	                //视频
	                videoMeta = meterialManagerService.getVideoMetaByID(resource.getResourceId());
	                videoSpecification = materialService.getVideoSpc(adPositionQuery.getSpecificationId());
	            }
	            if(resource.getResourceType()==2){
	                //文字
	                textMeta=meterialManagerService.getTextMetaByID(resource.getResourceId());
	                if(textMeta.getContent()!=null){
	                    byte[] contentBlob =textMeta.getContent();
	                    String jsonWord = new String(contentBlob,"gbk");
	                    textMeta.setContentMsg(jsonWord);
	                    
	                    Gson gson = new Gson();
	                    List<PriorityWordBean> list = gson.fromJson(jsonWord, new TypeToken<List<PriorityWordBean>>(){ }.getType());
	                    textMeta.setPwList(list);
	                  
	                }
	                
	            }
	                  

	            String ip=config.getValueByKey("ftp.ip");
	            String path=config.getValueByKey("materila.ftp.tempPath");
	            path=path.substring(5, path.length());
	            String viewPath="http://"+ip+path;
	            if(videoMeta != null){
	            	sssspath = viewPath+"/"+videoMeta.getName();
	            }
	            this.getRequest().setAttribute("viewPath", viewPath);
	            
	        }catch(Exception e){
	        	e.printStackTrace();
	        	logger.error("初始化素材信息异常", e);
	        }
	        if("1".equals(isAuditTag)){
	        	return "audit";
	        }
	        return SUCCESS;
        
    }
    
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public DResource getMeterialQuery() {
		return meterialQuery;
	}
	public void setMeterialQuery(DResource meterialQuery) {
		this.meterialQuery = meterialQuery;
	}
	public List<MaterialCategory> getMaterialCategoryList() {
		return materialCategoryList;
	}
	public void setMaterialCategoryList(List<MaterialCategory> materialCategoryList) {
		this.materialCategoryList = materialCategoryList;
	}
	public PageBeanDB getAdPositionPage() {
		return adPositionPage;
	}
	public void setAdPositionPage(PageBeanDB adPositionPage) {
		this.adPositionPage = adPositionPage;
	}
	public Integer getAdvertPositionId() {
		return advertPositionId;
	}
	public void setAdvertPositionId(Integer advertPositionId) {
		this.advertPositionId = advertPositionId;
	}
	public DResource getMaterial() {
		return resource;
	}
	public void setMaterial(DResource material) {
		this.resource = material;
	}
	
	public String getVideoFileNames() {
		return videoFileNames;
	}
	public void setVideoFileNames(String videoFileNames) {
		this.videoFileNames = videoFileNames;
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public MaterialService getMaterialService() {
		return materialService;
	}
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	public MeterialManagerService getMeterialManagerService() {
		return meterialManagerService;
	}
	public void setMeterialManagerService(
			MeterialManagerService meterialManagerService) {
		this.meterialManagerService = meterialManagerService;
	}
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
	public String getImageFileNames() {
		return imageFileNames;
	}
	public void setImageFileNames(String imageFileNames) {
		this.imageFileNames = imageFileNames;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public String getIsAuditTag() {
		return isAuditTag;
	}
	public void setIsAuditTag(String isAuditTag) {
		this.isAuditTag = isAuditTag;
	}
	public static ConfigureProperties getConfig() {
		return config;
	}
	public static void setConfig(ConfigureProperties config) {
		MeterialAction.config = config;
	}
	public String getHtmlPath() {
		return htmlPath;
	}
	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	public String getQuestionw() {
		return questionw;
	}
	public void setQuestionw(String questionw) {
		this.questionw = questionw;
	}
	public String getW2() {
		return w2;
	}
	public void setW2(String w2) {
		this.w2 = w2;
	}
	public String getSssspath() {
		return sssspath;
	}
	public void setSssspath(String sssspath) {
		this.sssspath = sssspath;
	}
	public List<Customer> getCustomerPage() {
		return customerPage;
	}
	public void setCustomerPage(List<Customer> customerPage) {
		this.customerPage = customerPage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static int getBufferSize() {
		return BUFFER_SIZE;
	}
	
	public String getLocalFilePath() {
		return localFilePath;
	}
	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}
	public VideoMeta getVideoMeta() {
		return videoMeta;
	}
	public void setVideoMeta(VideoMeta videoMeta) {
		this.videoMeta = videoMeta;
	}
	public MessageMeta getTextMeta() {
		return textMeta;
	}
	public void setTextMeta(MessageMeta textMeta) {
		this.textMeta = textMeta;
	}
	public ImageMeta getImageMeta() {
		return imageMeta;
	}
	public void setImageMeta(ImageMeta imageMeta) {
		this.imageMeta = imageMeta;
	}
	public ImageMeta getZipMeta() {
		return zipMeta;
	}
	public void setZipMeta(ImageMeta zipMeta) {
		this.zipMeta = zipMeta;
	}
	public DPositionService getdPositionService() {
		return dPositionService;
	}
	public void setdPositionService(DPositionService dPositionService) {
		this.dPositionService = dPositionService;
	}
	public DAdPosition getAdPositionQuery() {
		return adPositionQuery;
	}
	public void setAdPositionQuery(DAdPosition adPositionQuery) {
		this.adPositionQuery = adPositionQuery;
	}
	public VideoSpecification getVideoSpecification() {
		return videoSpecification;
	}
	public void setVideoSpecification(VideoSpecification videoSpecification) {
		this.videoSpecification = videoSpecification;
	}
	public ImageSpecification getImageSpecification() {
		return imageSpecification;
	}
	public void setImageSpecification(ImageSpecification imageSpecification) {
		this.imageSpecification = imageSpecification;
	}
	public ImageSpecification getZipSpecification() {
		return zipSpecification;
	}
	public void setZipSpecification(ImageSpecification zipSpecification) {
		this.zipSpecification = zipSpecification;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public DAdPosition getPosition() {
		return position;
	}
	public void setPosition(DAdPosition position) {
		this.position = position;
	}
	public DResource getResource() {
		return resource;
	}
	public void setResource(DResource resource) {
		this.resource = resource;
	}
	public String getDataIds() {
		return dataIds;
	}
	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
	
}
