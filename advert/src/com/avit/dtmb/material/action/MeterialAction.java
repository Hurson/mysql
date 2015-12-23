package com.avit.dtmb.material.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.ads.webservice.UploadClient;
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
import com.dvnchina.advertDelivery.model.QuestionReal;
import com.dvnchina.advertDelivery.model.Questionnaire;
import com.dvnchina.advertDelivery.model.QuestionnaireReal;
import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
@ParentPackage("default")
@Namespace("/dmaterial")
@Scope("prototype")
@Controller
public class MeterialAction extends BaseAction implements ServletRequestAware{
	private Logger logger = Logger.getLogger(MeterialManagerAction.class);
	/**
	 * 
	 */
	private HttpServletRequest request;
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
	private DResource material;
	  /**操作日志类*/
	public OperateLog operLog;
	private OperateLogService operateLogService;
		
		/**
		 * 接受来自页面中的ajax请求参数
		 */
	private String resourceName;
	private Integer resourceId;
	private String templateName;
	private Integer templateId;
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
	private VideoSpecification videoSpecification;
	
	private ImageSpecification imageSpecification;
	
    private ImageSpecification zipSpecification;
    private String materialId;
    private Object positionJson;
	
	
	
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
            meterialQuery.setStatus("0".charAt(0));
			page = materialService.queryDMaterialList(meterialQuery,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 进入素材修改页面
	 * @return
	 * @throws ParseException
	 */
	@Action(value = "initMaterial",results={@Result(name="success",location="/page/material/new/Dmaterial/auditMaterial.jsp")})
	public String initMaterial(){    

        try{
        	materialCategoryList=meterialManagerService.getMaterialCategoryList();
            
            int materialIdd = Integer.valueOf(materialId);      
            material = materialService.getMaterialByID(materialIdd);
            adPositionQuery = dPositionService.getAdvertPosition(material.getAdvertPositionId());
    		//返回广告位的JSON信息，用于预览
    		getRequest().setAttribute("positionJson", Obj2JsonUtil.object2json(adPositionQuery));
    		positionJson = Obj2JsonUtil.object2json(adPositionQuery);
            if(material.getResourceType()==0){
                //图片
                imageMeta=meterialManagerService.getImageMetaByID(material.getResourceId());
                imageSpecification = meterialManagerService.getImageMateSpeci(Integer.parseInt(material.getAdvertPositionId()));
            }
            if(material.getResourceType()==1){
                //视频
                videoMeta = meterialManagerService.getVideoMetaByID(material.getResourceId());
                videoSpecification = meterialManagerService.getVideoMateSpeci(Integer.parseInt(material.getAdvertPositionId()));
            }
            if(material.getResourceType()==2){
                //文字
                textMeta=meterialManagerService.getTextMetaByID(material.getResourceId());
                if(textMeta.getContent()!=null){
                    byte[] contentBlob =textMeta.getContent();
                    String jsonWord = new String(contentBlob,"gbk");
                    textMeta.setContentMsg(jsonWord);
                    
                    Gson gson = new Gson();
                    List<PriorityWordBean> list = gson.fromJson(jsonWord, new TypeToken<List<PriorityWordBean>>(){ }.getType());
                    textMeta.setPwList(list);
                 
                    
//                    logger.info("convert before:"+textMeta.getContentMsg());
//                    contentBlob =textMeta.getContentMsg().getBytes("gbk");
                  //  textMeta.setContentMsg(new String(contentBlob)); 
                 //   logger.info("convert end:"+textMeta.getContentMsg());
                  
                }
                
            }
           /* if(material.getResourceType()==3){
               //问卷    
                questionSubject = meterialManagerService.getQueMetaByID(material.getResourceId());
                templateList = meterialManagerService.getQuestionTemplateList();
                
                if(questionTypeList==null){
                    questionTypeList = new ArrayList<QuestionType>();
                }
                QuestionType questionType0= new QuestionType();
                QuestionType questionType1= new QuestionType();
                questionType0.setId("0".charAt(0));
                questionType0.setTypeName("调查");
                questionType1.setId("1".charAt(0));
                questionType1.setTypeName("抽奖");
                questionTypeList.add(questionType0);
                questionTypeList.add(questionType1);
                //获取问卷信息
                List<Question> questionList = meterialManagerService.getQuestionList(material.getResourceId());
                //去掉重复项
//                HashSet h = new HashSet(questionList);
//                questionList.clear();
//                questionList.addAll(h);
                if(questionInfoList==null){
                	questionInfoList = new ArrayList<QuestionInfo>();
                }
                for (int i = 0, j=questionList.size(); i < j; i++){
                	List<Question> answerList = meterialManagerService.getAnswerList(questionList.get(i).getQuestion(),material.getResourceId());
                	QuestionInfo questionInfo =new QuestionInfo();
                	questionInfo.setQuestion(questionList.get(i).getQuestion());
                	questionInfo.setAnswerList(answerList);
                	questionInfoList.add(questionInfo);
                }
            }*/

            if(material.getResourceType()==4){
                //ZIP
            	zipMeta=meterialManagerService.getImageMetaByID(material.getResourceId());
            	zipSpecification = meterialManagerService.getImageMateSpeci(Integer.parseInt(material.getAdvertPositionId()));
                String previewImage = zipMeta.getName().substring(0, zipMeta.getName().lastIndexOf("."))+".jpg";
                zipMeta.setFileHeigth(previewImage);
            }
      

            String ip=config.getValueByKey("ftp.ip");
            String path=config.getValueByKey("materila.ftp.tempPath");
            path=path.substring(5, path.length());
            String viewPath="http://"+ip+path;
            if(videoMeta != null){
            	sssspath = viewPath+"/"+videoMeta.getName();
            }
            
           /* request.setAttribute("viewPath", viewPath);
            request.setAttribute("material", material);*/
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("初始化素材信息异常", e);
        }
        
        return SUCCESS;
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
	 * 根据广告位获取视频规格
	 * @return 
	 * @return
	 */
	@Action(value = "getVideo")
	public String getVideo(){
		VideoSpecification videoSpecification = materialService.getVideoSpc(advertPositionId);
		Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("videoFileDuration",videoSpecification.getDuration().toString());
        print(Obj2JsonUtil.map2json(resultMap));
		return NONE;
	}
	/**
	 * 根据广告位获取图片规格
	 * @param str
	 */
	@Action(value = "getImg")
	public String getImg(){
		ImageSpecification imageSpecification = materialService.getImageMateSpeci(advertPositionId);
	    Map<String,String> resultMap = new HashMap<String,String>();
	    resultMap.put("imageFileWidth",imageSpecification.getImageWidth());
        resultMap.put("imageFileHigh",imageSpecification.getImageHeight());
        resultMap.put("imageFileSize",imageSpecification.getFileSize());
        print(Obj2JsonUtil.map2json(resultMap));
		return NONE;
	}
	private void print(String str) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getOutputStream().write(str.getBytes("utf-8"));
		} catch (Exception e) {
			System.out.println("send response error");
		}
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
	        
		    material.setIsDefault(0);
		    if(material.getResourceType()==1){
		      //视频素材	    	
		        if(material.getId()==null){
		        	operType = "operate.add";
		            //新增
		        	videoFileNames = videoFileNames.replace(" ","");//获取批量文件名集合
                    String videoFileNamestemp[]= videoFileNames.split(",");
                    String resourceName="";
                    String videoKeyword="";
                    for (int i=0;i<videoFileNamestemp.length;i++){
                        //String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
                        String[] prams = videoFileNamestemp[i].split("&");
                        resourceName=request.getParameter("videoFileName-"+prams[0]);
                        videoKeyword=request.getParameter("videoKeyword-"+prams[0]);
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
                            
                            materialTemp.setStatus("0".charAt(0));       
                            materialTemp.setResourceId(videoMetaTemp.getId());                                               
                            materialTemp.setResourceName(resourceName);
                          //  materialTemp.setKeyWords(videoKeyword);
                            materialTemp.setCreateTime(new Date());//资产创建时间
                            //复制公共属性
                            materialTemp.setResourceType(material.getResourceType());
                            materialTemp.setCategoryId(material.getCategoryId());
                            materialTemp.setPositionCode(material.getAdvertPositionId());
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
	                      //效验该广告位的视频规格
//	                      VideoSpecification videoSpecification = meterialManagerService.getVideoMateSpeci(material.getAdvertPositionId());
//	                      if(videoSpecification!=null){
//	                          if(!videoMeta.getRunTime().equals(videoSpecification.getDuration().toString())){
//	                              logger.info("上传的视频时长不等于广告位的设置大小### Material_video_duration=" + videoSpecification.getDuration());
//	                          }
//	                      } 
	                        
	                        if(videoMeta==null){
	                            videoMeta = new VideoMeta();
	                        }
	                        videoMeta.setName(materialName);
	                        videoMeta.setTemporaryFilePath(uploadPath);
	                        videoMeta.setFileSize(Integer.valueOf(bytesum).toString());
	                        meterialManagerService.saveVideoMaterial(videoMeta);
	                }
		            material.setModifyTime(new Date());//资产修改时间
		            
		            //保存素材表
	                //Contract contract = meterialManagerService.getContractByID(material.getContractId());
//	                if(userLogin.getRoleType()==2){
//                        //登陆用户为运营商操作员
//	                    material.setCustomerId(0);
//                    }else{
//                        material.setCustomerId(userLogin.getCustomerId());
//                    }
	                material.setStatus("0".charAt(0));       
	                material.setResourceId(videoMeta.getId());
	                //material.setOperationId(userLogin.getUserId());
	                materialService.saveDResource(material);
	                //操作日志
	                operInfo = material.toString();
	                operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
	                operateLogService.saveOperateLog(operLog);
		        }
		        
		        
		    }
		    
		    if(material.getResourceType()==0){
	            //图片素材
	                if(material.getId()==null){
	                	operType = "operate.add";
	                    //新增
	                    //String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
	                    
	                    
	                    imageFileNames = imageFileNames.replace(" ","");//获取批量文件名集合
	                    String imageFileNamestemp[]= imageFileNames.split(",");
	                    String resourceName="";
	                    String imageKeyword="";
	                    String imageUrl="";
	                    for (int i=0;i<imageFileNamestemp.length;i++){
	                         resourceName=request.getParameter("imageFileName-"+imageFileNamestemp[i]);
	                         imageKeyword=request.getParameter("imageKeyword-"+imageFileNamestemp[i]);
	                         imageUrl=request.getParameter("imageUrl-"+imageFileNamestemp[i]);
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
	                        
	                        materialTemp.setStatus("0".charAt(0));                      
	                        materialTemp.setResourceId(imageMetaTemp.getId());
	                        materialTemp.setResourceName(resourceName);
	                        //materialTemp.setDescription(imageKeyword);
	                        materialTemp.setCreateTime(new Date());//资产创建时间
	                        //复制公共属性
	                        materialTemp.setResourceType(material.getResourceType());
	                        materialTemp.setCategoryId(material.getCategoryId());
	                        materialTemp.setAdvertPositionId(material.getAdvertPositionId());
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
	                            //效验该广告位的图片规格
//	                          ImageSpecification imageSpecification =new ImageSpecification();
//	                          imageSpecification = meterialManagerService.getImageMateSpeci(material.getAdvertPositionId());
//	                          if(imageSpecification!=null){
//	                              if(!fileWidth.equals(imageSpecification.getImageWidth())){
//	                                  logger.info("上传的文件宽度不等于广告位的设置大小### Material_image_width=" + imageSpecification.getImageWidth());
//	                              }else{
//	                                  if(!fileHigh.equals(imageSpecification.getImageHeight())){
//	                                      logger.info("上传的文件高度不等于广告位的设置大小### Material_image_high=" + imageSpecification.getImageHeight());
//	                                  }else{
//	                                      String maxVal = imageSpecification.getFileSize();                            
//	                                      if(!maxVal.equals("")&&maxVal!=null){
//	                                          int maxSize = Integer.valueOf(maxVal) * 1024;//图片规格中文件大小单位为K
//	                                          if(bytesum>maxSize){
//	                                              logger.info("上传的文件大小超过广告位的设置大小### Material_image_fileSize=" + imageSpecification.getFileSize()+"K");
//	                                          }
//	                                      }
//	                                  }
//	                              }
//	                          }
	                            
	                          
	                            
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
	                    material.setModifyTime(new Date());//资产修改时间
	                    
	                  //保存素材表
	                    //Contract contract = meterialManagerService.getContractByID(material.getContractId());
//	                    if(userLogin.getRoleType()==2){
//                            //登陆用户为运营商操作员
//	                        material.setCustomerId(0);
//                        }else{
//                            material.setCustomerId(userLogin.getCustomerId());
//                        }
	                    material.setStatus("0".charAt(0));                      
	                    material.setResourceId(imageMeta.getId());
	                    //material.setOperationId(userLogin.getUserId());
	                    materialService.saveDResource(material);
	               
	                    //操作日志
	                    operInfo = material.toString();
	                    operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
	                    operateLogService.saveOperateLog(operLog);
	                }
	                
	                
	        }
		    
		    if(material.getResourceType()==2){
		        //文字素材
		        
	            //保存文字素材子表
	           // byte[] contentBlob = textMeta.getContentMsg().getBytes("utf-8");
		    	
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
	            
//	            String temp = textMeta.getContentMsg();
//	            logger.info("convert before:"+temp);
//               // contentBlob =textMeta.getContentMsg().getBytes("gbk");
//                temp = new String(contentBlob,"gbk"); 
//                logger.info("convert end:"+temp);
//	            
//                temp = new String(contentBlob,"utf8"); 
//                logger.info("convert end:"+temp);
	            meterialManagerService.saveTextMaterial(textMeta);
	            //保存素材表
//	            Contract contract = meterialManagerService.getContractByID(material.getContractId());
//	            material.setCustomerId(contract.getCustomerId());
	            material.setStatus("0".charAt(0));  
	            if(material.getId()==null){
	            	operType = "operate.add";
	                //新增
	                material.setCreateTime(new Date());
	                material.setOperationId(userLogin.getUserId());
	                if(userLogin.getRoleType()==2){
	                    //登陆用户为运营商操作员
	                    material.setCustomerId(0);
	                }else{
	                    material.setCustomerId(userLogin.getCustomerId());
	                }
	            }else{
	            	operType = "operate.update";
	                //修改
	                material.setModifyTime(new Date());
	            }           
	            material.setResourceId(textMeta.getId());
	            
	            materialService.saveDResource(material);
	            //操作日志
		        operInfo = material.toString();
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
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
    @Action(value = "auditMaterial",results={@Result(name="success",location="/page/material/new/Dmaterial/auditMaterial.jsp")})
    public String auditMaterial() {
        HttpServletRequest request = this.getRequest();
        String auditFlag = request.getParameter("auditFlag");      
        String reason = request.getParameter("reason");
        //reason=new String(reason.getBytes("GBK"),"utf-8");

        String materialId = request.getParameter("materialId");
        //获取素材临时表信息
        DResource materialTemp = materialService.getMaterialByID(Integer.parseInt(materialId));
        if(auditFlag.equals("1")){
            //审核通过          
            materialTemp.setStatus("2".charAt(0));           
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
                       /* AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(materialTemp.getAdvertPositionId());
                        if(advertPosition!=null){
                        	if(advertPosition.getPositionPackageType()==0 ||advertPosition.getPositionPackageType()==1){
                            	//双向图片素材审核时调用下面函数上传至 双向资源服务器
                            	UploadClient client  = new UploadClient();
                            	String fileNameToCps = remoteDirectoryReal+"/"+localFileName;
                            	client.sendFtpFileToCps(fileNameToCps, null);
                            }
                        }*/
                        
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

//                        //TODO 调用接口复制视频文件到videoPump
//                        UploadClient client = new UploadClient();
//                        String fileNameToVideoPump = remoteDirectoryReal+"/"+localFileName;
//                        String filePathToVideoPump = materialTemp.getAdvertPositionId()+"/"+materialTemp.getId();
//                        //client.sendFileVideoPump(fileNameToVideoPump, filePathToVideoPump+"/"+localFileName);
//                        client.sendFileVideoPump(fileNameToVideoPump, filePathToVideoPump);
//                        videoReal.setVideoPumpPath(filePathToVideoPump);
//                        AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(materialTemp.getAdvertPositionId());
//                        if(advertPosition!=null){
//                        	if(advertPosition.getPositionPackageType()==3){
//                            	//单向非实时广告
//                        		videoReal.setVideoPumpPath(videoReal.getFormalFilePath());
//                            }
//                        }
                        
                        //AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(materialTemp.getAdvertPositionId());
                        videoReal.setVideoPumpPath(videoReal.getFormalFilePath());
                        /*if(advertPosition!=null && advertPosition.getPositionPackageType()!=3){
                        	 //TODO 调用接口复制视频文件到videoPump
                            UploadClient client = new UploadClient();
                            String fileNameToVideoPump = remoteDirectoryReal+"/"+localFileName;
                            String filePathToVideoPump = materialTemp.getAdvertPositionId()+"/"+materialTemp.getId();
                            //client.sendFileVideoPump(fileNameToVideoPump, filePathToVideoPump+"/"+localFileName);
                            client.sendFileVideoPump(fileNameToVideoPump, filePathToVideoPump);
                            videoReal.setVideoPumpPath(filePathToVideoPump);
                        }    */                                   
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
            /*if(materialTemp.getResourceType()==3){
                 //问卷    
                Questionnaire questionSubject = meterialManagerService.getQueMetaByID(materialTemp.getResourceId());
                List<Question> questionAnswerList = meterialManagerService.getQuestionAnswerList(materialTemp.getResourceId());
                //生成问卷页面
                QuestionnaireTemplate questionTemplate = meterialManagerService.getQuestionTemplateByID(questionSubject.getTemplateId());
                //FTP文件复制转移
                FtpUtils ftp = null;
                    try {
                        ftp = new FtpUtils();
                        ftp.connectionFtp();
                        String localFileName=questionTemplate.getTemplateName();
                        String remoteFileName=questionTemplate.getTemplateName();
                        String remoteDirectory=questionTemplate.getHtmlPath();
                        ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                        //解压缩文件                      
                        String fileName = ".././"+localFileName;
                        String filePath = ".././";
                        ZipFile zipFile = new ZipFile(fileName);
                        Enumeration emu = zipFile.entries();
                        while(emu.hasMoreElements()){
                        ZipEntry entry = (ZipEntry)emu.nextElement();
                        //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                        if (entry.isDirectory()){
                          new File(filePath + entry.getName()).mkdirs();
                          continue;
                        }
                        BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                        File file = new File(filePath + entry.getName());
                        //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                        //而这个文件所在的目录还没有出现过，所以要建出目录来。
                        File parent = file.getParentFile();
                        if(parent != null && (!parent.exists())){
                            parent.mkdirs();
                        }

                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedOutputStream bos = new BufferedOutputStream(fos,2048);

                        int count;
                        byte data[] = new byte[2048];

                        while ((count = bis.read(data, 0, 2048)) != -1){
                        bos.write(data, 0, count);
                        }

                        bos.flush();
                        bos.close();
                        bis.close();
                        }
                        zipFile.close();

                        //生成页面
                        String templateFileName =localFileName.substring(0, localFileName.indexOf("."));                      
                        Configuration cfg = new Configuration();                               
                        cfg.setDefaultEncoding("UTF-8");
                        cfg.setObjectWrapper(new DefaultObjectWrapper());
                        cfg.setDirectoryForTemplateLoading(new File(".././"+templateFileName+"/htmlTemplate/"));
                        Template temp = cfg.getTemplate(templateFileName+".html","UTF-8");
                       
                        Map root = new HashMap();
                        String requestActionName=config.getValueByKey("materila.question.requesrActionName");
                        root.put("questionSubjectId", questionSubject.getId());
                        root.put("questionType", questionSubject.getQuestionnaireType());
                        root.put("subject", questionSubject.getSummary());
                        root.put("integral", questionSubject.getIntegral());
                        root.put("questionSummary", questionSubject.getSummary());
                        root.put("requestActionName", requestActionName);
                        
                        List<Question> questionList = new ArrayList<Question>();
                        List<Question> answerList = new ArrayList<Question>();
                        questionList=meterialManagerService.getQuestionList(questionSubject.getId());
                        for (int i=0; i<questionList.size(); i++) {
                        	Question que = questionList.get(i);
                        	que.setId(i+1);
                        }
                        answerList=meterialManagerService.getQuestionAnswerList(questionSubject.getId());
                        for (int j=0; j<answerList.size(); j++) {
                        	Question ans = answerList.get(j);
                        	ans.setId(0);
                        }
                        String questionJson = Obj2JsonUtil.list2json(questionList);
               		    String answerJson = Obj2JsonUtil.list2json(answerList);
               		    //获取总记录数
               			int recordCount = questionList.size();
               			//获取总页数（默认每页5条记录）
               			int totalPage = (recordCount-1)/5+1;
                    
                        root.put("questionList", questionList);
                        root.put("answerList", answerList);
                        root.put("questionJson", questionJson);
                        root.put("answerJson", answerJson);
                        root.put("pageNo", 1);
                		root.put("pageSize", 5);
                		root.put("recordCount", recordCount);
                		root.put("totalPage", totalPage);
                       
                        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(".././"+templateFileName+"/html/questionReal.html"),"UTF-8"));
                        temp.process(root, out);
                        out.flush();
                        out.close();
                        
                        //删除本地文件(从FTP下载的模板文件压缩包)
                        File localFile =new File(".././"+localFileName);
                        if(localFile.exists()&&localFile.isFile()){
                            localFile.delete();
                        }
                        //将新的文件夹上传到FTP
                        File inFile = new File(".././"+templateFileName);                      
                        String newFileName =questionSubject.getId().toString();                    
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.questionRealPath");
                        logger.info("inFile.getName:"+inFile.getPath()+File.separator+inFile.getName()+"remoteDirectoryReal:"+remoteDirectoryReal);
                        ftp.upload(inFile,remoteDirectoryReal);
                        ftp.removeRemoteDirectory(remoteDirectoryReal+"/"+newFileName);//重命名之前先进行删除,保存重命名成功
                        ftp.renameRemoteFile(remoteDirectoryReal, templateFileName, remoteDirectoryReal, newFileName);//重命名文件
                        //删除本地文件
                        File localFileNewZip =new File(".././"+localFileName);
                        if(localFileNewZip.exists()&&localFileNewZip.isFile()){
                            localFileNewZip.delete();
                        }
                        File localFileNew =new File(".././"+templateFileName);
                        this.DeleteFolder(".././"+templateFileName);

                    } catch (Exception e) {
                    	e.printStackTrace();
                        logger.error("问卷素材FTP上传异常", e);
                    } finally{
                        if (ftp != null) {
                            ftp.closeFTP();
                        }
                    }
                
                    
                    
                    String filePath = "http://"+config.getValueByKey("ftp.ip")+config.getValueByKey("materila.ftp.questionRealPath").substring(5)+"/"
                                       +questionSubject.getId().toString()+"/html/questionReal.html";
                    questionSubject.setFilePath(filePath);
                    QuestionnaireReal questionSubjectReal = copyToQuestionnaireReal(questionSubject);
                    List<QuestionReal> questionAnswerRealList = copyToQuestionRealList(questionAnswerList);
                    meterialManagerService.saveQuestionSubject(questionSubject);
                    meterialManagerService.saveQuestionSubjectReal(questionSubjectReal);
                    meterialManagerService.saveQuestionRealList(questionAnswerRealList);
                
                
                
            }
            if(materialTemp.getResourceType()==4){
            	//ZIP
                ImageMeta  imageMetaTemp = meterialManagerService.getImageMetaByID(materialTemp.getResourceId());
                ImageReal imageMetaReal = copyToImageReal(imageMetaTemp);
                meterialManagerService.saveImageMaterialReal(imageMetaReal);
                //FTP文件复制转移
                FtpUtils ftp = null;
                    try {
                        ftp = new FtpUtils();
                        ftp.connectionFtp();
                        String localFileName=imageMetaTemp.getName();
                        //String localDirectory=config.getValueByKey("resource.locationPath"); ;
                        String remoteFileName=imageMetaTemp.getName();
                        String previewImage = imageMetaTemp.getName().substring(0, imageMetaTemp.getName().lastIndexOf("."))+".jpg";
                        String localPreviewName = previewImage;
                        String remoteDirectory=imageMetaTemp.getTemporaryFilePath();
                        ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");

                        ftp.downloadToLocal(previewImage, localPreviewName, remoteDirectory, null);
                        
                       
                        
                        ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName); 
                        ftp.uploadFileToRemote(".././"+localPreviewName, remoteDirectoryReal, localPreviewName);  
                        AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(materialTemp.getAdvertPositionId());
                        if(advertPosition!=null){
                        	if(advertPosition.getPositionPackageType()==0 ||advertPosition.getPositionPackageType()==1){
                            	//双向图片素材审核时调用下面函数上传至 双向资源服务器
                            	UploadClient client  = new UploadClient();
                            	String fileNameToCps = remoteDirectoryReal+"/"+localFileName;
                            	client.sendFtpFileToCps(fileNameToCps, null);
                            }
                        }
                        
                        //删除本地文件
                        File localFile =new File(".././"+localFileName);
                        if(localFile.exists()&&localFile.isFile()){
                            localFile.delete();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("zip素材FTP上传异常", e);
                    } finally{
                        if (ftp != null) {
                            ftp.closeFTP();
                        }
                    }
            }*/
            //操作日志
	        operInfo = materialTemp.toString();
			operType = "operate.aduitOk";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
			operateLogService.saveOperateLog(operLog);
        }else{
            //审核不通过
            materialTemp.setStatus("1".charAt(0));
            materialTemp.setExaminationOpintions(reason);
            materialTemp.setAuditTime(new Date());
            materialService.saveDResource(materialTemp);
            //操作日志
	        operInfo = materialTemp.toString();
			operType = "operate.aduitFalse";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
			operateLogService.saveOperateLog(operLog);
        }
        
        isAuditTag="1";
        meterialQuery=null;
     
        return SUCCESS;
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
		return material;
	}
	public void setMaterial(DResource material) {
		this.material = material;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
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
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = request; 
		
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
	public Object getPositionJson() {
		return positionJson;
	}
	public void setPositionJson(Object positionJson) {
		this.positionJson = positionJson;
	}
	
	
}
