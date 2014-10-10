package com.dvnchina.advertDelivery.uiresource.action;

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
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
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
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractBackup;
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
import freemarker.template.TemplateException;


public class UiManagerAction extends BaseActionSupport<Object> implements ServletRequestAware{	
	private static final long serialVersionUID = -3666982468062423696L;
	private Logger logger = Logger.getLogger(UiManagerAction.class);
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
	
	private String positionPackIds;
	
	private VideoSpecification videoSpecification;
	
	private ImageSpecification imageSpecification;
	
    
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
	 * 进入新增素材页面
	 * @return
	 */
	public String intoUploadUiMaterial(){	   
        return SUCCESS;
    }
	
	public String saveUiMaterial(){
        
		 /* imageFileNames = imageFileNames.replace(" ","");//获取批量文件名集合
          String imageFileNamestemp[]= imageFileNames.split(",");
          String resourceName="";
          String imageKeyword="";
          for (int i=0;i<imageFileNamestemp.length;i++){
               resourceName=request.getParameter("imageFileName-"+imageFileNamestemp[i]);
               imageKeyword=request.getParameter("imageKeyword-"+imageFileNamestemp[i]);
               System.out.println("素材名:"+resourceName);
               
               String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+imageFileNamestemp[i];
               File uploadfile = new File(uploadAllDir);
               
             
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
		*/
		//FTP文件复制转移
        FtpUtils ftp = null;
            try {
                ftp = new FtpUtils();
                ftp.connectionFtp();
                String localFileName=ServletActionContext.getServletContext().getRealPath(uploadDir)+File.separator+imageFileNames;
               // String remoteFileName=questionTemplate.getTemplateName();
              // remoteDirectory=questionTemplate.getHtmlPath();
                //解压缩文件                      
                String fileName =localFileName;
                String filePath = localFileName.substring(0,localFileName.lastIndexOf(".zip"))+File.separator;
                ZipFile zipFile = new ZipFile(fileName);
                Enumeration emu = zipFile.entries();
                while(emu.hasMoreElements())
                {
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
              
                //删除本地文件(从FTP下载的模板文件压缩包)
                File localFile =new File(localFileName);
                if(localFile.exists()&&localFile.isFile()){
                    localFile.delete();
                }
               
                //将新的文件夹上传到FTP
                File inFile = new File(filePath+"advResource-c"+File.separator);                      
               // String newFileName =questionSubject.getId().toString();                    
                String remoteDirectoryReal=config.getValueByKey("uiresourcepath");
                ftp.upload(inFile,remoteDirectoryReal);
               // ftp.removeRemoteDirectory(remoteDirectoryReal+"/"+newFileName);//重命名之前先进行删除,保存重命名成功
              //  ftp.renameRemoteFile(remoteDirectoryReal, localFileName, remoteDirectoryReal, newFileName);//重命名文件
                //删除本地文件
               this.DeleteFolder(localFileName.substring(0,localFileName.lastIndexOf(".zip"))+File.separator);

            } catch (Exception e) {
            	e.printStackTrace();
                logger.error("问卷素材FTP上传异常", e);
            } finally{
                if (ftp != null) {
                    ftp.closeFTP();
                }
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
//        request.setAttribute("viewPath", viewPath);
        String imagePreviewName = request.getParameter("imagePreviewName");
        String videoPreviewName = request.getParameter("videoPreviewName");
        if(imagePreviewName==null||imagePreviewName.equals("")){
            request.setAttribute("videoPreviewName", videoPreviewName);
            return "successForVideo";
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
    
	
}
