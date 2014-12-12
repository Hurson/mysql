package com.dvnchina.advertDelivery.meterial.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.avit.ads.webservice.UploadClient;
import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.bean.MaterialType;
import com.dvnchina.advertDelivery.meterial.bean.QuestionInfo;
import com.dvnchina.advertDelivery.meterial.bean.QuestionType;
import com.dvnchina.advertDelivery.meterial.service.MeterialManagerService;
import com.dvnchina.advertDelivery.model.ContractAD;
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
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.position.service.PositionService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;


public class MeterialManagerAction extends BaseActionSupport<Object> implements ServletRequestAware{	
	private static final long serialVersionUID = -3666982468062423696L;
	private Logger logger = Logger.getLogger(MeterialManagerAction.class);
	private HttpServletRequest request;
	
	private String dataIds;
	private PageBeanDB page;
	private Resource meterialQuery;
	private QuestionnaireTemplate questionnaireTemplateQuery;
	private QuestionnaireTemplate questionTemplate;
	private Resource material;
	private MeterialManagerService meterialManagerService;
	private PositionService positionService = null;
	private List<MaterialCategory> materialCategoryList;
	private List<QuestionnaireTemplate> templateList;
	private List<MaterialType> materialTypeList;
	private List<QuestionType> questionTypeList;
	private List<QuestionInfo> questionInfoList;
	private PageBeanDB adPositionPage=  new PageBeanDB() ;//子广告位列表
	private PageBeanDB contractPage =  new PageBeanDB();//合同列表
	private AdvertPosition adPositionQuery; //子广告位查询条件
	private ContractQueryBean contractQuery;
	private String questionCount;
	private String[] question;
	private String[] answer1;
	private String[] answer2;
	private String[] answer3;
	private String[] answer4;
	private String[] answer5;
	private String[] answer6;
	private Questionnaire questionSubject;
	private Question materialQuestion;
	private MessageMeta textMeta;
	private ImageMeta imageMeta;
	private VideoMeta videoMeta;
	private ImageMeta zipMeta;
	//private static final Map<String, ArrayList<Material>> mKindMap  = MaterialConfig.matkindMap;
	private String localFilePath;
	/**
     * 保存上传文件的目录，相对于Web应用程序的根路径，在struts.xml中配置
     */
    private String uploadDir;
    private String isAuditTag;//判断是用于审核查询列表还是维护查询列表
    private static final int BUFFER_SIZE = 16 * 1024;
    private static ConfigureProperties config = ConfigureProperties.getInstance();
    private String htmlPath;//用于判断修改页面是否有重新上传文件
    private String questionw;
    private String w2;
    private String sssspath;
    private List<Customer> customerPage;
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
	private Integer advertPositionId;
	/**
	 * 批量上传文件名集合
	 */
	private String imageFileNames;
	private String videoFileNames;
	private String zipFileNames;
	
	private String positionPackIds;
	
	private VideoSpecification videoSpecification;
	
	private ImageSpecification imageSpecification;
	
    private ImageSpecification zipSpecification;
    public String getSssspath() {
		return sssspath;
	}


	public void setSssspath(String path) {
		this.sssspath = path;
	}

	private static String jsonMsg  = "";
	
	public void setServletRequest(HttpServletRequest request) {
        this.request = request; 
    }
	
	
	public static void main(String[] args) throws Exception {	    
	    
//	    File source = new File("F:\\gaoqingzongyi.mpg");
//        Encoder encoder = new Encoder();
//        try {
//             MultimediaInfo m = encoder.getInfo(source);
//             long ls = m.getDuration();
// 
//            String format =  m.getFormat();
//            VideoInfo vinfo = m.getVideo();
//            System.out.println(source);
//            vinfo.getBitRate();
//            vinfo.getDecoder();
//            vinfo.getSize();
//            System.out.println("时长 :"+ ls +"格式"+vinfo.getDecoder() +"码率"+ vinfo.getBitRate()+"大小"+vinfo.getSize());
//             
////         System.out.println("此视频时长为:"+ls/60000+"分"+(ls`000)/1000+"秒！");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
	    String ip=config.getValueByKey("ftp.ip");
        String path=config.getValueByKey("materila.ftp.tempPath");
        path=path.substring(5, path.length());
        System.out.println("path:"+path);
        System.out.println("ss:http://"+ip+path);

	}
	
	public static void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {
	            if (inFile.isDirectory()) {
	                File[] files = inFile.listFiles();
	                for (File file:files)
	                    zipFile(file, zos, dir + "\\" + inFile.getName());
	            } else {
	                String entryName = null;
	                if (!"".equals(dir))

	                    entryName = dir + "\\" + inFile.getName();
	                else
	                    entryName = inFile.getName();
	                ZipEntry entry = new ZipEntry(entryName);
	                zos.putNextEntry(entry);
	                InputStream is = new FileInputStream(inFile);
	                int len = 0;
	                while ((len = is.read()) != -1)
	                    zos.write(len);
	                is.close();
	            }

	        }

	
	/**
	 * 
	 * @description: 查询素材列表
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 上午09:26:36
	 */
	public String queryMeterialList(){
	    if (page==null)
        {
	        page  =  new PageBeanDB();
        }
        try {         
            if(meterialQuery==null){
                meterialQuery=new Resource();
            }
            UserLogin userLogin = (UserLogin) this.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
            
            if(isAuditTag!=null&&isAuditTag.equals("1")){
                //审核查询列表              
                List<Integer> ids = userLogin.getPositionIds();
                String positionPackIds ="";
                if(!ids.isEmpty()){
                    for(int i=0;i<ids.size();i++){
                        if(positionPackIds.equals("")){
                            positionPackIds =ids.get(i).toString();
                        }else{
                            positionPackIds =positionPackIds+","+ids.get(i).toString();
                        }
                    }
                }      
                String positionIds ="";
                if(!positionPackIds.equals("")){
                    List<AdvertPosition> adPositionPage = meterialManagerService.getAdvertPositionList(positionPackIds);
                    if(adPositionPage.size()>0){
                        for(int j=0;j<adPositionPage.size();j++){
                            AdvertPosition temp =(AdvertPosition)adPositionPage.get(j);
                            if(positionIds.equals("")){
                                positionIds=temp.getId().toString();
                            }else{
                                positionIds=positionIds+","+temp.getId().toString();
                            }
                        }
                    }else{
                        positionIds="-1";//没有对应的广告位，确保查询数据为空
                    }
                    
                }else{
                    positionIds="-1";//没有对应的广告位，确保查询数据为空
                }
     
                meterialQuery.setPositionIds(positionIds);
                meterialQuery.setState("0".charAt(0));
            }else{
                //维护查询列表
                if(userLogin.getRoleType()==2){
                    //登陆用户为运营商操作员  
                    List<Integer> ids = userLogin.getPositionIds();
                    String positionPackIds ="";
                    if(!ids.isEmpty()){
                        for(int i=0;i<ids.size();i++){
                            if(positionPackIds.equals("")){
                                positionPackIds =ids.get(i).toString();
                            }else{
                                positionPackIds =positionPackIds+","+ids.get(i).toString();
                            }
                        }
                    }
                    
                    String positionIds ="";
                    if(!positionPackIds.equals("")){
                        List<AdvertPosition> adPositionPage = meterialManagerService.getAdvertPositionList(positionPackIds);
                        if(adPositionPage.size()>0){
                            for(int j=0;j<adPositionPage.size();j++){
                                AdvertPosition temp =(AdvertPosition)adPositionPage.get(j);
                                if(positionIds.equals("")){
                                    positionIds=temp.getId().toString();
                                }else{
                                    positionIds=positionIds+","+temp.getId().toString();
                                }
                            }
                        }else{
                            positionIds="-1";//没有对应的广告位，确保查询数据为空
                        }
                        
                    }else{
                        positionIds="-1";//没有对应的广告位，确保查询数据为空
                    }
                    
                    meterialQuery.setPositionIds(positionIds);
                    //meterialQuery.setCustomerId(0);
                }else{
                   //登陆用户为广告商操作员
                    meterialQuery.setCustomerId(userLogin.getCustomerId());
                }
            }
            materialCategoryList=meterialManagerService.getMaterialCategoryList();
            page = meterialManagerService.queryMeterialList(meterialQuery, page.getPageSize(), page.getPageNo());

        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("获取素材列表时出现异常",e);
        }
        
        return SUCCESS;
	}
	
	
	/**
	 * 
	 * @description: 查询问卷模板列表
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-20 上午11:19:34
	 */
	public String queryQuestionTemplateList(){
        if (page==null)
        {
            page  =  new PageBeanDB();
        }
        try {         
            
            page = meterialManagerService.queryQuestionTemplateList(questionnaireTemplateQuery, page.getPageSize(), page.getPageNo());

        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("获取问卷模板列表时出现异常",e);
        }
        
        return SUCCESS;
    }
	
	/**
	 * 进入新增素材页面
	 * @return
	 */
	public String intoAddMaterial(){
	    UserLogin userLogin = (UserLogin) this.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
	    if(userLogin.getRoleType()==1){
	        //登陆用户为广告商操作员
	        positionPackIds ="";
	        List<ContractAD> advertPositionPackList =meterialManagerService.getAdvertPositionPackListByCustomer(userLogin.getCustomerId());
	        if(!advertPositionPackList.isEmpty()){
	            for(int i=0;i<advertPositionPackList.size();i++){
                    if(positionPackIds.equals("")){
                        positionPackIds =advertPositionPackList.get(i).getPositionId().toString();
                    }else{
                        positionPackIds =positionPackIds+","+advertPositionPackList.get(i).getPositionId().toString();
                    }
                }
	        }else{
	            positionPackIds ="-1";//没有对应的广告位，确保查询数据为空
	        }
	    }else{
	        //登陆用户为运营商操作员
	        List<Integer> ids = userLogin.getPositionIds();
	        positionPackIds ="";
	        if(!ids.isEmpty()){
	            for(int i=0;i<ids.size();i++){
	                if(positionPackIds.equals("")){
	                    positionPackIds =ids.get(i).toString();
	                }else{
	                    positionPackIds =positionPackIds+","+ids.get(i).toString();
	                }
	            }
	        }else{
	            positionPackIds ="-1";//没有对应的广告位，确保查询数据为空
	        }
	    }
//	    initDate();
	    materialCategoryList=meterialManagerService.getMaterialCategoryList();
	    
	    if(questionTypeList==null){
            questionTypeList = new ArrayList<QuestionType>();
        }
        QuestionType questionType0= new QuestionType();
        QuestionType questionType1= new QuestionType();
        questionType0.setId("0".charAt(0));
        questionType0.setTypeName(" 调查");
        questionType1.setId("1".charAt(0));
        questionType1.setTypeName("抽奖");
        questionTypeList.add(questionType0);
        questionTypeList.add(questionType1);
        
	    templateList = meterialManagerService.getQuestionTemplateList();
        return SUCCESS;
    }
	
	public String intoAddQuestionTemplate(){
        
        return SUCCESS;
    }
	
	/**
	 * 
	 * @description: 初始化素材类型数据 
	 * @return 
	 * List<MaterialType>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 下午03:31:31
	 */
	public void initDate(){
	    if(materialTypeList==null){
	        materialTypeList = new ArrayList<MaterialType>();
	    }
        MaterialType materialType0= new MaterialType();
        MaterialType materialType1= new MaterialType();
        MaterialType materialType2= new MaterialType();
        MaterialType materialType3= new MaterialType();
        MaterialType materialType4= new MaterialType();
        materialType0.setId(0);
        materialType0.setTypeName("图片");
        materialType1.setId(1);
        materialType1.setTypeName("视频");
        materialType2.setId(2);
        materialType2.setTypeName("文字");
        materialType3.setId(3);
        materialType3.setTypeName("调查问卷");
        materialType4.setId(4);
        materialType4.setTypeName("zip");
        materialTypeList.add(materialType0);
        materialTypeList.add(materialType1);
        materialTypeList.add(materialType2);
        materialTypeList.add(materialType3);
        materialTypeList.add(materialType4);
        
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
	}

	/**
	 * 
	 * @description: 选择子广告位
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 下午04:08:32
	 */
	public String selectAdPosition(){
        if (adPositionPage==null)
        {
            adPositionPage  =  new PageBeanDB();
        }
        try {
            adPositionPage = meterialManagerService.queryAdPositionList(adPositionQuery,positionPackIds, adPositionPage.getPageSize(), adPositionPage.getPageNo());

        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("获取子广告位列表时出现异常",e);
        }
        
        return SUCCESS;
    }
	
	
	public String selectContract(){
	    if (contractPage==null)
        {
            contractPage  =  new PageBeanDB();
        }
        try {
            //从session中获取操作员对应的广告商列表,封装成合同查询条件
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
                        
            customerPage=meterialManagerService.getCustomerList();
            contractPage = meterialManagerService.queryContractList(contractQuery, contractPage.getPageSize(), contractPage.getPageNo());

        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("获取合同列表时出现异常",e);
        }
        
        return SUCCESS;
    }
	
	
	/**
	 * 进入素材修改页面
	 * @return
	 * @throws ParseException
	 */
	public String initMaterial(){    

        try{
        	materialCategoryList=meterialManagerService.getMaterialCategoryList();
            
            int materialId = Integer.valueOf(request.getParameter("materialId"));      
            material = meterialManagerService.getMaterialByID(materialId);
            adPositionQuery = positionService.getAdvertPosition(material.getAdvertPositionId());
    		//返回广告位的JSON信息，用于预览
    		getRequest().setAttribute("positionJson", Obj2JsonUtil.object2json(adPositionQuery));
            
            if(material.getResourceType()==0){
                //图片
                imageMeta=meterialManagerService.getImageMetaByID(material.getResourceId());
                imageSpecification = meterialManagerService.getImageMateSpeci(material.getAdvertPositionId());
            }
            if(material.getResourceType()==1){
                //视频
                videoMeta = meterialManagerService.getVideoMetaByID(material.getResourceId());
                videoSpecification = meterialManagerService.getVideoMateSpeci(material.getAdvertPositionId());
            }
            if(material.getResourceType()==2){
                //文字
                textMeta=meterialManagerService.getTextMetaByID(material.getResourceId());
                if(textMeta.getContent()!=null){
                    byte[] contentBlob =textMeta.getContent();
                    
                    textMeta.setContentMsg(new String(contentBlob,"gbk"));
//                    logger.info("convert before:"+textMeta.getContentMsg());
//                    contentBlob =textMeta.getContentMsg().getBytes("gbk");
                  //  textMeta.setContentMsg(new String(contentBlob)); 
                 //   logger.info("convert end:"+textMeta.getContentMsg());
                  
                }
                
            }
            if(material.getResourceType()==3){
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
            }

            if(material.getResourceType()==4){
                //ZIP
            	zipMeta=meterialManagerService.getImageMetaByID(material.getResourceId());
            	zipSpecification = meterialManagerService.getImageMateSpeci(material.getAdvertPositionId());
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
            
            request.setAttribute("viewPath", viewPath);
            request.setAttribute("material", material);
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("初始化素材信息异常", e);
        }
        
        return SUCCESS;
    }


    public String initQuestionTemplate(){
    	try{
    		int tempId = Integer.valueOf(request.getParameter("tempId"));
            questionTemplate = meterialManagerService.getQuestionTemplateByID(tempId);
    	} 
        catch(Exception e){
        	e.printStackTrace();
        	logger.error("初始化问卷模板信息异常", e);
        }
        return SUCCESS;
    }
    
    /**
     * 效验素材名称是否已存在
     * @return
     */
    public String checkMaterialExist()
	{
		String result="0";
		if (resourceName!=null)
		{	
			if(resourceId!=null&&resourceId!=0){
				//修改
				Resource temp = meterialManagerService.getMaterialByID(resourceId);
				if(!temp.getResourceName().equals(resourceName)){
					int flag = meterialManagerService.checkMaterialExist(resourceName);
					if (flag!=0){
								   result = "1";
								   System.out.println("成功！");
								}
				}
			}else{
				//新增
				int flag = meterialManagerService.checkMaterialExist(resourceName);
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
     * 效验名称模板是否已存在
     * @return
     */
    public String checkQuestionTemplateExist()
	{
		String result="0";
		if (templateName!=null)
		{	
			if(templateId!=null&&templateId!=0){
				//修改
				QuestionnaireTemplate temp = meterialManagerService.getQuestionTemplateByID(templateId);
				if(!temp.getTemplatePackageName().equals(templateName)){
					int flag = meterialManagerService.checkQuestionTemplateExist(templateName);
					if (flag!=0){
								   result = "1";
								   System.out.println("成功！");
								}
				}
			}else{
				//新增
				int flag = meterialManagerService.checkQuestionTemplateExist(templateName);
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
     * (non-Javadoc)
     * <p>Title: addActionError</p> 
     * <p>Description: </p> 
     * @param anErrorMessage 
     * @see com.opensymphony.xwork2.ActionSupport#addActionError(java.lang.String)
     * 
     * 
     * public void addActionError(String anErrorMessage){
        String s=anErrorMessage;
        System.out.println(s);
       }
       public void addActionMessage(String aMessage){
        String s=aMessage;
        System.out.println(s);
       
       }
       public void addFieldError(String fieldName, String errorMessage){
        String s=errorMessage;
        String f=fieldName;
        System.out.println(s);
        System.out.println(f);
       
       }
     */
    

    
    /**
     * 保存素材
     * @return
     * @throws IOException
     */
	public String saveMaterialBackup() {  
	    try{
	        UserLogin userLogin = (UserLogin) this.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
	        
	    	if(material.getResourceTypeTemp()!=null&&material.getResourceType()==null){
		        material.setResourceType(material.getResourceTypeTemp());
		    }
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
                            Resource materialTemp =new Resource();
                            //Contract contract = meterialManagerService.getContractByID(material.getContractId());
                            if(userLogin.getRoleType()==2){
                                //登陆用户为运营商操作员
                                materialTemp.setCustomerId(0);
                            }else{
                                materialTemp.setCustomerId(userLogin.getCustomerId());
                            }
                            
                            materialTemp.setState("0".charAt(0));       
                            materialTemp.setResourceId(videoMetaTemp.getId());                                               
                            materialTemp.setResourceName(resourceName);
                            materialTemp.setKeyWords(videoKeyword);
                            materialTemp.setCreateTime(new Date());//资产创建时间
                            //复制公共属性
                            materialTemp.setResourceType(material.getResourceType());
                            materialTemp.setCategoryId(material.getCategoryId());
                            materialTemp.setAdvertPositionId(material.getAdvertPositionId());
                            materialTemp.setIsDefault(0);
                            materialTemp.setOperationId(userLogin.getUserId());
                            meterialManagerService.saveResource(materialTemp);
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
		                VideoMeta videoMetaTemp = meterialManagerService.getVideoMetaByID(videoMeta.getId());
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
	                material.setState("0".charAt(0));       
	                material.setResourceId(videoMeta.getId());
	                material.setOperationId(userLogin.getUserId());
	                meterialManagerService.saveResource(material);
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
	                            String fileWidth = String.valueOf(bi.getWidth());
	                            String fileHigh = String.valueOf(bi.getHeight());
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
	                        Resource materialTemp =new Resource();
	                        if(userLogin.getRoleType()==2){
                                //登陆用户为运营商操作员
                                materialTemp.setCustomerId(0);
                            }else{
                                materialTemp.setCustomerId(userLogin.getCustomerId());
                            }
	                        
	                        materialTemp.setState("0".charAt(0));                      
	                        materialTemp.setResourceId(imageMetaTemp.getId());
	                        materialTemp.setResourceName(resourceName);
	                        materialTemp.setKeyWords(imageKeyword);
	                        materialTemp.setCreateTime(new Date());//资产创建时间
	                        //复制公共属性
	                        materialTemp.setResourceType(material.getResourceType());
	                        materialTemp.setCategoryId(material.getCategoryId());
	                        materialTemp.setAdvertPositionId(material.getAdvertPositionId());
	                        materialTemp.setIsDefault(0);
	                        materialTemp.setOperationId(userLogin.getUserId());
	                        meterialManagerService.saveResource(materialTemp);
	                   
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
	                    material.setState("0".charAt(0));                      
	                    material.setResourceId(imageMeta.getId());
	                    material.setOperationId(userLogin.getUserId());
	                    meterialManagerService.saveResource(material);
	               
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
	            byte[] contentBlob = textMeta.getContentMsg().getBytes("gbk");
		           
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
	            material.setState("0".charAt(0));  
	            if(material.getId()==null){
	            	operType = "operate.add";
	                //新增
	                material.setCreateTime(new Date());
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
	            
	            material.setOperationId(userLogin.getUserId());
	            meterialManagerService.saveResource(material);
	            //操作日志
		        operInfo = material.toString();
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
				operateLogService.saveOperateLog(operLog);
		    }
		    if(material.getResourceType()==3){
		        //调查问卷
		        
		        //保存问卷主题表
		        if(questionSubject.getId()==null){
		            //新增流程
		            meterialManagerService.saveQuestionSubject(questionSubject);
		        }
		        
		        //保存素材表
//		        Contract contract = meterialManagerService.getContractByID(material.getContractId());
//	            material.setCustomerId(contract.getCustomerId());
	            material.setState("0".charAt(0));  
	            material.setResourceId(questionSubject.getId());
	            if(material.getId()==null){
	            	operType = "operate.add";
	                //新增
	                material.setCreateTime(new Date());
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
	            material.setResourceId(questionSubject.getId());
	            
                material.setOperationId(userLogin.getUserId());
	            meterialManagerService.saveResource(material);
	            //操作日志
		        operInfo = material.toString();
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
				operateLogService.saveOperateLog(operLog);
		        
	            if(questionCount!=null&&!questionCount.equals("")){
	                
	                List<Question> questionList = new ArrayList<Question>();
	                questionCount = questionCount.replace(" ","");
	                String[] questionNumber =questionCount.split(",");
	                
	                for (int i=0;i<questionNumber.length;i++){
	                    Integer index = Integer.parseInt(questionNumber[i]);                                                
	                    for(int j=0;j<6;j++){
	                        Question temp = new Question();
	                        temp.setQuestion(question[index-1]);//设置问题
	                        String var = "answer"+(j+1);
	                        if(var.equals("answer1")&&!answer1[index-1].equals("")){
	                            temp.setOptions(answer1[index-1]);
	                            temp.setQuestionnaireId(questionSubject.getId());
	                            questionList.add(temp);
	                        }
	                        if(var.equals("answer2")&&!answer2[index-1].equals("")){
	                            temp.setOptions(answer2[index-1]);
	                            temp.setQuestionnaireId(questionSubject.getId());
	                            questionList.add(temp);
	                        }
	                        if(var.equals("answer3")&&!answer3[index-1].equals("")){
	                            temp.setOptions(answer3[index-1]);
	                            temp.setQuestionnaireId(questionSubject.getId());
	                            questionList.add(temp);
	                        }
	                        if(var.equals("answer4")&&!answer4[index-1].equals("")){
	                            temp.setOptions(answer4[index-1]);
	                            temp.setQuestionnaireId(questionSubject.getId());
	                            questionList.add(temp);
	                        }
	                        if(var.equals("answer5")&&!answer5[index-1].equals("")){
	                            temp.setOptions(answer5[index-1]);
	                            temp.setQuestionnaireId(questionSubject.getId());
	                            questionList.add(temp);
	                        }
	                        if(var.equals("answer6")&&!answer6[index-1].equals("")){
	                            temp.setOptions(answer6[index-1]);
	                            temp.setQuestionnaireId(questionSubject.getId());
	                            questionList.add(temp);
	                        }
	                        
	                    }
	                    
	                                   
	                }
	                meterialManagerService.saveQuestionList(questionList);
	            }
		        
		    }
		    
		    
		    
		    //-------------------zip start
		   
		    if(material.getResourceType()==4){
	            //zip素材
	                if(material.getId()==null){
	                	operType = "operate.add";
	                    //新增
	                	zipFileNames = zipFileNames.replace(" ","");//获取批量文件名集合
	                    String zipFileNamestemp[]= zipFileNames.split(",");
	                    String resourceName="";
	                    String zipKeyword="";
	                    for (int i=0;i<zipFileNamestemp.length;i++){
	                         resourceName=request.getParameter("zipFileName-"+zipFileNamestemp[i]);
	                         zipKeyword=request.getParameter("zipKeyword-"+zipFileNamestemp[i]);
	                         System.out.println("素材名:"+resourceName);
	                         
	                         System.out.println("---resourceName--"+resourceName);

	                         String name1=zipFileNamestemp[i].substring(0, zipFileNamestemp[i].lastIndexOf("."));
	             	         String name2=zipFileNamestemp[i].substring(zipFileNamestemp[i].lastIndexOf("."), zipFileNamestemp[i].length());
	             	         
	             	         String previewImageString = name1+".jpg";
	                         
	                         String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+zipFileNamestemp[i];
	                         File uploadfile = new File(uploadAllDir);
	                         
	                         String uploadDire = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+config.getValueByKey("recommend.zip.name");
	                         File uploadDireFile = new File(uploadDire);
	                         /**  发送文件至ftp */
	                            boolean isSuccess = false;
	                            String uploadPath=config.getValueByKey("materila.ftp.tempPath");
	                            isSuccess = sendFile(uploadAllDir,uploadPath);
	                            /*发送预览图到FTP*/
	                            String uploadPreviewImage = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+previewImageString;
	                            File uploadImage = new File(uploadPreviewImage);
	                            sendFile(uploadPreviewImage,uploadPath);
	                            String imageFormat = zipFileNamestemp[i].substring(zipFileNamestemp[i].indexOf(".")+1, zipFileNamestemp[i].length());                    
	                            //计算文件大小
	                            InputStream in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
	                            byte[] buffer = new byte[BUFFER_SIZE];
	                            int len = 0;
	                            int bytesum = 0;
	                            while ((len = in.read(buffer)) > 0) {
	                                 bytesum += len;
	                            }
	                            in.close();

	                            deleteFile(uploadfile);
	                            deleteFile(uploadImage);
	                            deleteFile(uploadDireFile);

	                        ImageMeta zipMetaTemp = new ImageMeta();                     

	                        zipMetaTemp.setName(zipFileNamestemp[i]);
	                        zipMetaTemp.setFileSize(String.valueOf(bytesum));
	                        zipMetaTemp.setFileFormat(imageFormat);
	                        zipMetaTemp.setTemporaryFilePath(uploadPath);                  
	                        meterialManagerService.saveImageMaterial(zipMetaTemp);
	                        
	                        
	                      //保存素材表
	                        //Contract contract = meterialManagerService.getContractByID(material.getContractId());
	                        Resource materialTemp =new Resource();
	                        if(userLogin.getRoleType()==2){
                                //登陆用户为运营商操作员
                                materialTemp.setCustomerId(0);
                            }else{
                                materialTemp.setCustomerId(userLogin.getCustomerId());
                            }
	                        
	                        materialTemp.setState("0".charAt(0));                      
	                        materialTemp.setResourceId(zipMetaTemp.getId());
	                        materialTemp.setResourceName(resourceName);
	                        materialTemp.setKeyWords(zipKeyword);
	                        materialTemp.setCreateTime(new Date());//资产创建时间
	                        //复制公共属性
	                        materialTemp.setResourceType(material.getResourceType());
	                        materialTemp.setCategoryId(material.getCategoryId());
	                        materialTemp.setAdvertPositionId(material.getAdvertPositionId());
	                        materialTemp.setIsDefault(0);
	                        materialTemp.setOperationId(userLogin.getUserId());
	                        meterialManagerService.saveResource(materialTemp);
	                   
	                        //操作日志
	                        operInfo = materialTemp.toString();
	                        operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
	                        operateLogService.saveOperateLog(operLog);
	                    }
	                        
	                }else{
	                	operType = "operate.update";
	                	
	                    //修改 
	                    if(localFilePath.equals("")){
	                        //没有重新上传ZIP文件
	                    }else{
	                    		String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
	   	                        String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+materialName;
	   	                        File uploadfile = new File(uploadAllDir);
	   	                        
	   	                     String name1=materialName.substring(0, materialName.lastIndexOf("."));
	             	         String name2=materialName.substring(materialName.lastIndexOf("."), materialName.length());
	             	         
	             	         String previewImageString = name1+".jpg";
	             	         String uploadPreviewImage = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+previewImageString;
	             	         File uploadImage = new File(uploadPreviewImage);
	   	                            /**  发送文件至ftp */
	   	                            boolean isSuccess = false;
	   	                            String uploadPath=config.getValueByKey("materila.ftp.tempPath");
	   	                            isSuccess = sendFile(uploadAllDir,uploadPath);
	   	                            //发送预览图到ftp
	   	                            boolean isImgSuccess = false;
	   	                            isImgSuccess = sendFile(uploadPreviewImage,uploadPath);
	   	                            
	   	                            //解压文件名
	   	                            String uploadDire = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+config.getValueByKey("recommend.zip.name");;
	   	                            File uploadDireFile = new File(uploadDire);
	   	                            
	   	                            InputStream inputStream = new FileInputStream(uploadfile);
	   	                            BufferedImage bi = ImageIO.read(inputStream);   
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

		                            deleteFile(uploadfile);
		                            deleteFile(uploadImage);
		                            deleteFile(uploadDireFile);
	   		                        if(zipMeta==null){
	   		                        	zipMeta = new ImageMeta();
	   		                        }
	   		                        zipMeta.setName(materialName);
	   		                     	zipMeta.setFileSize(String.valueOf(bytesum));
	   		                     	zipMeta.setFileFormat(imageFormat);
	   		                     	zipMeta.setTemporaryFilePath(uploadPath);
	                     
	                          
	                      
	                        
	                        meterialManagerService.saveImageMaterial(zipMeta);
	                    }
	                    material.setModifyTime(new Date());//资产修改时间

	                    material.setState("0".charAt(0));
	                    material.setResourceId(zipMeta.getId());
	                    material.setOperationId(userLogin.getUserId());
	                    meterialManagerService.saveResource(material);
	               
	                    //操作日志
	                    operInfo = material.toString();
	                    operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
	                    operateLogService.saveOperateLog(operLog);
	                }
	                
	                
	        }
		    //-------------------zip end
		    
		    isAuditTag="0";
		    meterialQuery=null;
		    queryMeterialList();
		    
	    }catch(Exception e){
	    	e.printStackTrace();
	    	logger.error("保存素材异常", e);
	    }

	    return SUCCESS;
	}
	
	
	private void deleteFile(File file){ 
		   if(file.exists()){ 
		    if(file.isFile()){ 
		     file.delete(); 
		    }else if(file.isDirectory()){ 
		     File files[] = file.listFiles(); 
		     for(int i=0;i<files.length;i++){ 
		      this.deleteFile(files[i]); 
		     } 
		    } 
		    file.delete(); 
		   }else{ 
		    System.out.println("所删除的文件不存在！"+'\n'); 
		   } 
		} 
	/**
	 * 
	 * @description: 保存问卷模板
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-20 下午06:10:37
	 */
	public String saveQuestionTemplate(){   
	    try{
	    	if(questionTemplate.getId()==null){
		        //新增
		        String templateName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
		        /*Long random = new Date().getTime();
		        String targettemplateName=random.toString()+".zip";
		        copyFile(ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+templateName,ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+targettemplateName);
		        templateName = targettemplateName;*/
		        
		        String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+templateName;
		        
		        File uploadfile = new File(uploadAllDir);        
		        
		        /**  发送文件至ftp */
		        boolean isSuccess = false;
		        String uploadPath=config.getValueByKey("materila.ftp.questionTemplatePath");
		        isSuccess = sendFile(uploadAllDir,uploadPath);
	            //删除临时文件
	            if(uploadfile.exists()&&uploadfile.isFile()){
	            	uploadfile.delete();
	            }
		            
		        questionTemplate.setCreateTime(new Date());
		        questionTemplate.setHtmlPath(uploadPath);
		        questionTemplate.setTemplateName(templateName);
		        meterialManagerService.saveQuestionTemplate(questionTemplate);
		        
		        //操作日志
		        operInfo = questionTemplate.toString();
				operType = "operate.add";
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
				operateLogService.saveOperateLog(operLog);
		    }else{
		        //修改
		        if(localFilePath.equals("")||localFilePath.equals(htmlPath)){
		            questionTemplate.setModifyTime(new Date());
	                meterialManagerService.saveQuestionTemplate(questionTemplate);
		        }else{
		            String templateName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
		           
		            Long random = new Date().getTime();
			        String targettemplateName=random.toString()+".zip";
			        targettemplateName = questionTemplate.getTemplateName();
			        copyFile(ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+templateName,ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+targettemplateName);
			        templateName = targettemplateName;
		            
		            String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+templateName;
		            File uploadfile = new File(uploadAllDir);        
		            
		            /**  发送文件至ftp */
		            boolean isSuccess = false;
		            String uploadPath=config.getValueByKey("materila.ftp.questionTemplatePath");
		            isSuccess = sendFile(uploadAllDir,uploadPath);
		            if(uploadfile.exists()&&uploadfile.isFile()){
		            	uploadfile.delete();
		            }    
		            questionTemplate.setModifyTime(new Date());
		            questionTemplate.setHtmlPath(uploadPath);
		            questionTemplate.setTemplateName(templateName);
		            meterialManagerService.saveQuestionTemplate(questionTemplate);
		        }
		        
		        //操作日志
		        operInfo = questionTemplate.toString();
				operType = "operate.update";
				operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
				operateLogService.saveOperateLog(operLog);
		    }
	    	questionnaireTemplateQuery=null;
	    	queryQuestionTemplateList();
	    }catch(Exception e){
	    	e.printStackTrace();
	    	logger.error("保存问卷模板异常", e);
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
	
	
    public String checkDelMeterial()
	{
		String result="0";
		if (dataIds!=null)
		{
		
			dataIds = dataIds.replace(" ","");	
			String id[] = dataIds.split(",");
			
			if(!"null".equals(dataIds) && !"".equals(dataIds)){
				for(int i=0;i<id.length;i++){
					if(!"".equals(id[i]) && id[i] != null){
						
						int flag = meterialManagerService.checkDelMeterial(Integer.parseInt(id[i]));
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
    
    /**
     * 
     * @description: 删除素材 
     * @return 
     * String
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 上午10:50:09
     */
    public String deleteMaterial(){
        if(StringUtils.isNotBlank(dataIds)){
            try {
                
                boolean flag = meterialManagerService.deleteMaterialByIds(dataIds);
                if(flag){
                	//记录操作日志
                	StringBuffer delInfo = new StringBuffer();
            		delInfo.append("删除素材：");
                    delInfo.append("共").append(dataIds.split(",").length).append("条记录(ids:"+dataIds+")");
        			operType = "operate.delete";
        			operInfo = delInfo.toString();
        			operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
        			operateLogService.saveOperateLog(operLog);
        			
        			isAuditTag="0";
        			meterialQuery=null;
                    queryMeterialList();
        			
                    return SUCCESS;
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                logger.error("删除素材异常", e);
            }
        }
        return NONE;
    }
    
    /**
     * 
     * @description: 删除问卷模板
     * @return 
     * String
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-21 上午11:07:06
     */
    public String deleteQuestionTemplate(){
        if(StringUtils.isNotBlank(dataIds)){
            try {
                
                boolean flag = meterialManagerService.deleteQuestionTemplate(dataIds);
                if(flag){
                	//记录操作日志
                	StringBuffer delInfo = new StringBuffer();
            		delInfo.append("删除问卷模板：");
                    delInfo.append("共").append(dataIds.split(",").length).append("条记录(ids:"+dataIds+")");
        			operType = "operate.delete";
        			operInfo = delInfo.toString();
        			operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
        			operateLogService.saveOperateLog(operLog);
        			
        			questionnaireTemplateQuery=null;
        			queryQuestionTemplateList();
        			
                    return SUCCESS;
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                logger.error("删除问卷模板异常", e);
            }
        }
        return NONE;
    }
	
	
	
    public String auditMaterial() {
        HttpServletRequest request = this.getRequest();
        String auditFlag = request.getParameter("auditFlag");      
        String reason = request.getParameter("reason");
        //reason=new String(reason.getBytes("GBK"),"utf-8");

        String materialId = request.getParameter("materialId");
        //获取素材临时表信息
        Resource materialTemp = meterialManagerService.getMaterialByID(Integer.parseInt(materialId));
        if(auditFlag.equals("1")){
            //审核通过          
            materialTemp.setState("2".charAt(0));           
            materialTemp.setExaminationOpintions(reason);
            materialTemp.setAuditDate(new Date());
            meterialManagerService.saveResource(materialTemp);
            ResourceReal materialReal = copyToResourceReal(materialTemp);
            meterialManagerService.saveResourceReal(materialReal);
            //判断素材类型
            if(materialTemp.getResourceType()==0){
                //图片
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
                        String remoteDirectory=imageMetaTemp.getTemporaryFilePath();
                        ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");
                        ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName);    
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
                        logger.error("图片素材FTP上传异常", e);
                    } finally{
                        if (ftp != null) {
                            ftp.closeFTP();
                        }
                    }
            }
            if(materialTemp.getResourceType()==1){
                //视频
                VideoMeta videoMetaTemp = meterialManagerService.getVideoMetaByID(materialTemp.getResourceId());
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
                        
                        AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(materialTemp.getAdvertPositionId());
                        videoReal.setVideoPumpPath(videoReal.getFormalFilePath());
                        if(advertPosition!=null && advertPosition.getPositionPackageType()!=3){
                        	 //TODO 调用接口复制视频文件到videoPump
                            UploadClient client = new UploadClient();
                            String fileNameToVideoPump = remoteDirectoryReal+"/"+localFileName;
                            String filePathToVideoPump = materialTemp.getAdvertPositionId()+"/"+materialTemp.getId();
                            //client.sendFileVideoPump(fileNameToVideoPump, filePathToVideoPump+"/"+localFileName);
                            client.sendFileVideoPump(fileNameToVideoPump, filePathToVideoPump);
                            videoReal.setVideoPumpPath(filePathToVideoPump);
                        }                                       
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
            if(materialTemp.getResourceType()==3){
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
                        /**
                         * //将生成好的html文件压缩
                        File inFile = new File(".././"+newFileName);

                        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(".././"+localFileName));
                        zos.setComment("多文件处理");
                        zipFile(inFile, zos, "");
                        zos.close();
                        //将新的压缩文件上传到ftp
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.questionRealPath"); 
                        ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName);
                         */
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
            }
            //操作日志
	        operInfo = materialTemp.toString();
			operType = "operate.aduitOk";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
			operateLogService.saveOperateLog(operLog);
        }else{
            //审核不通过
            materialTemp.setState("1".charAt(0));
            materialTemp.setExaminationOpintions(reason);
            materialTemp.setAuditDate(new Date());
            meterialManagerService.saveResource(materialTemp);
            //操作日志
	        operInfo = materialTemp.toString();
			operType = "operate.aduitFalse";
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_RESOURCE);
			operateLogService.saveOperateLog(operLog);
        }
        
        isAuditTag="1";
        meterialQuery=null;
        queryMeterialList();
     
        return SUCCESS;
    }
	
    public boolean DeleteFolder(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        // 判断目录或文件是否存在  
        if (!file.exists()) {  // 不存在返回 false  
            return flag;  
        } else {  
            // 判断是否为文件  
            if (file.isFile()) {  // 为文件时调用删除文件方法  
                return deleteFile(sPath);  
            } else {  // 为目录时调用删除目录方法  
                return deleteDirectory(sPath);  
            }  
        }  
    } 
    
    public boolean deleteFile(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    }
    
    public boolean deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
    
	/**
	 * 
	 * @description: 复制resource表 
	 * @param materialTemp
	 * @return 
	 * ResourceReal
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-15 下午04:14:26
	 */
    public  ResourceReal copyToResourceReal(Resource materialTemp){
        ResourceReal materialReal = new ResourceReal();
        materialReal.setId(materialTemp.getId());
        materialReal.setResourceName(materialTemp.getResourceName());
        materialReal.setResourceType(materialTemp.getResourceType());
        materialReal.setResourceId(materialTemp.getResourceId());
        //if(materialTemp.getResourceDesc()!=null&&!materialTemp.getResourceDesc().equals("")){
            //materialReal.setResourceDesc(materialTemp.getResourceDesc());
        //}
        materialReal.setCustomerId(materialTemp.getCustomerId());
        materialReal.setCategoryId(materialTemp.getCategoryId());
        //materialReal.setContractId(materialTemp.getContractId());
        //materialReal.setStartTime(materialTemp.getStartTime());
        //materialReal.setEndTime(materialTemp.getEndTime());
        materialReal.setIsDefault(materialTemp.getIsDefault());
        materialReal.setAdvertPositionId(materialTemp.getAdvertPositionId());
        materialReal.setState(materialTemp.getState());
        materialReal.setCreateTime(new Date());
        if(materialTemp.getKeyWords()!=null&&!materialTemp.getKeyWords().equals("")){
            materialReal.setKeyWords(materialTemp.getKeyWords());
        }      
        materialReal.setOperationId(materialTemp.getOperationId());

        return  materialReal;
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
     * 
     * @description: 复制问卷主题表 
     * @param questionSubjectTemp
     * @return 
     * QuestionnaireReal
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午05:38:06
     */
    public QuestionnaireReal copyToQuestionnaireReal(Questionnaire questionSubjectTemp){
        QuestionnaireReal questionSubjectReal =new QuestionnaireReal();
        questionSubjectReal.setId(questionSubjectTemp.getId());
        questionSubjectReal.setSummary(questionSubjectTemp.getSummary());
        questionSubjectReal.setQuestionnaireType(questionSubjectTemp.getQuestionnaireType());
        questionSubjectReal.setTemplateId(questionSubjectTemp.getTemplateId());
        questionSubjectReal.setFilePath(questionSubjectTemp.getFilePath());
        questionSubjectReal.setIntegral(questionSubjectTemp.getIntegral());
        
        return questionSubjectReal;
    }
    
    /**
     * 
     * @description: 复制问卷问题列表 
     * @param questionAnswerList
     * @return 
     * List<QuestionReal>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-5-15 下午06:00:29
     */
    public List<QuestionReal> copyToQuestionRealList(List<Question> questionAnswerList){       
        List<QuestionReal> questionAnswerRealList = new ArrayList<QuestionReal>();
        for (int i = 0;i<questionAnswerList.size(); i++) {          
            Question question = questionAnswerList.get(i);
            QuestionReal temp = new QuestionReal();
            temp.setId(question.getId());
            temp.setOptions(question.getOptions());
            temp.setQuestion(question.getQuestion());
            temp.setQuestionnaireId(question.getQuestionnaireId());

            questionAnswerRealList.add(temp);
        }
        
        return questionAnswerRealList;
    }
	
	
	
    private String renderMsg(boolean result ,String msg){
        Map map = new HashMap(); 
        map.put("result", result);
        map.put("msg", msg);
        return Obj2JsonUtil.object2json(map);
    }
	
    /**
     *  @description:异步方法：根据广告位获取该广告位能够加载的广告素材类型
     */
	public void getAdTypeByPosition(){
		String positionId = request.getParameter("positionId");
		List<MaterialType> list = meterialManagerService.getAdTypeByPosition(positionId);
		
	}
	
	/**
	 * 
	 * @description: 根据广告位获取图片规格
	 * @return 
	 * ImageSpecification
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-8-9 上午11:16:10
	 */
	public String getImageMateSpeci(){
	    
	    ImageSpecification imageSpecification = meterialManagerService.getImageMateSpeci(advertPositionId);
	    Map<String,String> resultMap = new HashMap<String,String>();
	    resultMap.put("imageFileWidth",imageSpecification.getImageWidth());
        resultMap.put("imageFileHigh",imageSpecification.getImageHeight());
        resultMap.put("imageFileSize",imageSpecification.getFileSize());
        print(Obj2JsonUtil.map2json(resultMap));
        return NONE;
            
	}
	
	/*
	 * @description: 根据广告位获取Zip规格
	 * @return
	 * ZipMateSpecification
	 */
	public String getZipMateSpeci(){
	    
	    ImageSpecification imageSpecification = meterialManagerService.getImageMateSpeci(advertPositionId);
	    Map<String,String> resultMap = new HashMap<String,String>();
	    resultMap.put("imageFileWidth",imageSpecification.getImageWidth());
        resultMap.put("imageFileHigh",imageSpecification.getImageHeight());
        resultMap.put("imageFileSize",imageSpecification.getFileSize());
        print(Obj2JsonUtil.map2json(resultMap));
        return NONE;
            
	}
	
	public String getAdvertPosition(){
	    adPositionQuery = positionService.getAdvertPosition(advertPositionId);
        //返回广告位的JSON信息，用于预览
	    request.setAttribute("positionJson", Obj2JsonUtil.object2json(adPositionQuery));
	    //positionJson=Obj2JsonUtil.object2json(adPositionQuery);
//        String ip=config.getValueByKey("ftp.ip");
//        String path=config.getValueByKey("materila.ftp.tempPath");
//        path=path.substring(5, path.length());
//        String viewPath="http://"+ip+path;
//        if(videoMeta != null){
//            sssspath = viewPath+"/"+videoMeta.getName();
//        }
//        
//      request.setAttribute("viewPath", viewPath);
	    
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
	 * @description: 根据广告位获取视频规格 
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-8-9 下午02:51:45
	 */
    public String getVideoMateSpeci(){
        
        VideoSpecification videoSpecification = meterialManagerService.getVideoMateSpeci(advertPositionId);
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("videoFileDuration",videoSpecification.getDuration().toString());
        print(Obj2JsonUtil.map2json(resultMap));
        return NONE;
    }
	

	
	
	
	
	
	
	
	
	
	
	
	
	

    public PageBeanDB getPage() {
        return page;
    }



    public void setPage(PageBeanDB page) {
        this.page = page;
    }



    public Resource getMeterialQuery() {
        return meterialQuery;
    }



    public void setMeterialQuery(Resource meterialQuery) {
        this.meterialQuery = meterialQuery;
    }



    public MeterialManagerService getMeterialManagerService() {
        return meterialManagerService;
    }



    public void setMeterialManagerService(MeterialManagerService meterialManagerService) {
        this.meterialManagerService = meterialManagerService;
    }


    public List<MaterialCategory> getMaterialCategoryList() {
        return materialCategoryList;
    }


    public void setMaterialCategoryList(List<MaterialCategory> materialCategoryList) {
        this.materialCategoryList = materialCategoryList;
    }


    public List<MaterialType> getMaterialTypeList() {
        return materialTypeList;
    }


    public void setMaterialTypeList(List<MaterialType> materialTypeList) {
        this.materialTypeList = materialTypeList;
    }


    public PageBeanDB getAdPositionPage() {
        return adPositionPage;
    }


    public void setAdPositionPage(PageBeanDB adPositionPage) {
        this.adPositionPage = adPositionPage;
    }


    public AdvertPosition getAdPositionQuery() {
        return adPositionQuery;
    }


    public void setAdPositionQuery(AdvertPosition adPositionQuery) {
        this.adPositionQuery = adPositionQuery;
    }


    public PageBeanDB getContractPage() {
        return contractPage;
    }


    public void setContractPage(PageBeanDB contractPage) {
        this.contractPage = contractPage;
    }


    public ContractQueryBean getContractQuery() {
        return contractQuery;
    }


    public void setContractQuery(ContractQueryBean contractQuery) {
        this.contractQuery = contractQuery;
    }


    public Resource getMaterial() {
        return material;
    }


    public void setMaterial(Resource material) {
        this.material = material;
    }


    public String getQuestionCount() {
        return questionCount;
    }


    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }


    public String[] getQuestion() {
        return question;
    }


    public void setQuestion(String[] question) {
        this.question = question;
    }


    public Questionnaire getQuestionSubject() {
        return questionSubject;
    }


    public void setQuestionSubject(Questionnaire questionSubject) {
        this.questionSubject = questionSubject;
    }


    public Question getMaterialQuestion() {
        return materialQuestion;
    }


    public void setMaterialQuestion(Question materialQuestion) {
        this.materialQuestion = materialQuestion;
    }


    public String[] getAnswer1() {
        return answer1;
    }


    public void setAnswer1(String[] answer1) {
        this.answer1 = answer1;
    }


    public String[] getAnswer2() {
        return answer2;
    }


    public void setAnswer2(String[] answer2) {
        this.answer2 = answer2;
    }


    public String[] getAnswer3() {
        return answer3;
    }


    public void setAnswer3(String[] answer3) {
        this.answer3 = answer3;
    }


    public String[] getAnswer4() {
        return answer4;
    }


    public void setAnswer4(String[] answer4) {
        this.answer4 = answer4;
    }


    public String[] getAnswer5() {
        return answer5;
    }


    public void setAnswer5(String[] answer5) {
        this.answer5 = answer5;
    }


    public String[] getAnswer6() {
        return answer6;
    }


    public void setAnswer6(String[] answer6) {
        this.answer6 = answer6;
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
    


    public String getLocalFilePath() {
        return localFilePath;
    }


    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }


    public String getUploadDir() {
        return uploadDir;
    }


    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }


    public VideoMeta getVideoMeta() {
        return videoMeta;
    }


    public void setVideoMeta(VideoMeta videoMeta) {
        this.videoMeta = videoMeta;
    }


    public String getDataIds() {
        return dataIds;
    }


    public void setDataIds(String dataIds) {
        this.dataIds = dataIds;
    }


    public String getIsAuditTag() {
        return isAuditTag;
    }


    public void setIsAuditTag(String isAuditTag) {
        this.isAuditTag = isAuditTag;
    }


    public List<QuestionType> getQuestionTypeList() {
        return questionTypeList;
    }


    public void setQuestionTypeList(List<QuestionType> questionTypeList) {
        this.questionTypeList = questionTypeList;
    }


    public QuestionnaireTemplate getQuestionnaireTemplateQuery() {
        return questionnaireTemplateQuery;
    }


    public void setQuestionnaireTemplateQuery(QuestionnaireTemplate questionnaireTemplateQuery) {
        this.questionnaireTemplateQuery = questionnaireTemplateQuery;
    }


    public QuestionnaireTemplate getQuestionTemplate() {
        return questionTemplate;
    }


    public void setQuestionTemplate(QuestionnaireTemplate questionTemplate) {
        this.questionTemplate = questionTemplate;
    }


    public String getHtmlPath() {
        return htmlPath;
    }


    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }


    public List<QuestionnaireTemplate> getTemplateList() {
        return templateList;
    }


    public void setTemplateList(List<QuestionnaireTemplate> templateList) {
        this.templateList = templateList;
    }

    public String getQuestionw() {
        return questionw;
    }

    public void setQuestionw(String questionw) {
        this.questionw = questionw;
    }


	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}


	public List<QuestionInfo> getQuestionInfoList() {
		return questionInfoList;
	}


	public void setQuestionInfoList(List<QuestionInfo> questionInfoList) {
		this.questionInfoList = questionInfoList;
	}


	public List<Customer> getCustomerPage() {
		return customerPage;
	}


	public void setCustomerPage(List<Customer> customerPage) {
		this.customerPage = customerPage;
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


    public Integer getAdvertPositionId() {
        return advertPositionId;
    }


    public void setAdvertPositionId(Integer advertPositionId) {
        this.advertPositionId = advertPositionId;
    }


    public String getImageFileNames() {
        return imageFileNames;
    }


    public void setImageFileNames(String imageFileNames) {
        this.imageFileNames = imageFileNames;
    }


    public String getPositionPackIds() {
        return positionPackIds;
    }


    public void setPositionPackIds(String positionPackIds) {
        this.positionPackIds = positionPackIds;
    }


    public String getVideoFileNames() {
        return videoFileNames;
    }


    public void setVideoFileNames(String videoFileNames) {
        this.videoFileNames = videoFileNames;
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
    
    
    public String getZipFileNames() {
		return zipFileNames;
	}


	public void setZipFileNames(String zipFileNames) {
		this.zipFileNames = zipFileNames;
	}


	public boolean copyFile(String srcPath,String destPath)
    {
    	//被移动的文件夹
    	File file = new File(srcPath);
    	//目标文件夹
    	boolean success = file.renameTo(new File(destPath));
    	return success;
   	}


	public ImageMeta getZipMeta() {
		return zipMeta;
	}


	public void setZipMeta(ImageMeta zipMeta) {
		this.zipMeta = zipMeta;
	}


	public ImageSpecification getZipSpecification() {
		return zipSpecification;
	}


	public void setZipSpecification(ImageSpecification zipSpecification) {
		this.zipSpecification = zipSpecification;
	}
	
	
}
