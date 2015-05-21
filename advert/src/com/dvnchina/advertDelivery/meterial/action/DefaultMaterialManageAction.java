package com.dvnchina.advertDelivery.meterial.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.avit.ads.webservice.UploadClient;
import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.bean.MaterialType;
import com.dvnchina.advertDelivery.meterial.service.DefaultMeterialManageService;
import com.dvnchina.advertDelivery.meterial.service.MeterialManagerService;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
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


public class DefaultMaterialManageAction extends BaseActionSupport<Object> implements ServletRequestAware{	
	private static final long serialVersionUID = -3666982468062423696L;
	private Logger logger = Logger.getLogger(MeterialManagerAction.class);
	private HttpServletRequest request;
	private String dataIds;
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	private PageBeanDB page;
	private Resource meterialQuery;
	private Resource material;
	private MeterialManagerService meterialManagerService;
	private DefaultMeterialManageService defaultMeterialManageService;
	private List<MaterialCategory> materialCategoryList;
	private List<MaterialType> materialTypeList;
	private PageBeanDB adPositionPage=  new PageBeanDB() ;//子广告位列表
	private AdvertPosition adPositionQuery; //子广告位查询条件
	private ImageMeta imageMeta;
	private VideoMeta videoMeta;
	private MessageMeta textMeta;
	private ImageMeta zipMeta;
	private String localFilePath;
	private String sssspath;
	private PositionService positionService;
	private String positionPackIds="";
	private String judge;
	public String getSssspath() {
		return sssspath;
	}


	public void setSssspath(String path) {
		this.sssspath = path;
	}
	/**
     * 保存上传文件的目录，相对于Web应用程序的根路径，在struts.xml中配置
     */
    private String uploadDir;
    private static final int BUFFER_SIZE = 16 * 1024;
	
	public void setServletRequest(HttpServletRequest request) {
        this.request = request; 
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
	  //维护查询列表
        if(userLogin.getRoleType()==2){
            //登陆用户为运营商操作员  
            List<Integer> ids = userLogin.getPositionIds();
            if(!ids.isEmpty()){
                for(int i=0;i<ids.size();i++){
                    if(positionPackIds.equals("")){
                        positionPackIds =ids.get(i).toString();
                    }else{
                        positionPackIds =positionPackIds+","+ids.get(i);
                    }
                }
            }
            String positionIds = "";
            if(!positionPackIds.equals("")){
                List<AdvertPosition> adPositionPage = meterialManagerService.getAdvertPositionList(positionPackIds);
                if(adPositionPage.size()>0){
                    for(int j=0;j<adPositionPage.size();j++){
                        AdvertPosition temp =(AdvertPosition)adPositionPage.get(j);
                        if(positionIds.equals("")){
                            positionIds=temp.getId().toString();
                        }else{
                            positionIds=positionIds+","+temp.getId();
                        }
                    }
                }else{
                    positionIds="-1";//没有对应的广告位，确保查询数据为空
                }
                
            }else{
                positionIds="-1";//没有对应的广告位，确保查询数据为空
            }
            
            meterialQuery.setPositionIds(positionIds);
        }else{
           //登陆用户为广告商操作员
            meterialQuery.setCustomerId(userLogin.getCustomerId());
        }
               
            materialCategoryList=meterialManagerService.getMaterialCategoryList();
            page = defaultMeterialManageService.queryMeterialList(meterialQuery, page.getPageSize(), page.getPageNo());

        } catch (Exception e) {
            logger.error("查询默认素材列表时时出现异常",e);
        }
        
        return SUCCESS;
	}
	
	public String intoAddMaterial(){
		try {
			
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
			//initDate();
		    materialCategoryList=meterialManagerService.getMaterialCategoryList();
		    
		   
		} catch (Exception e) {
			logger.error("初始化默认素材新增是失败", e);
		}
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
        MaterialType materialType4= new MaterialType();
        materialType0.setId(0);
        materialType0.setTypeName("图片");
        materialType1.setId(1);
        materialType1.setTypeName("视频");
        materialType2.setId(2);
        materialType2.setTypeName("文字");
        materialType4.setId(4);
        materialType4.setTypeName("zip");
        materialTypeList.add(materialType0);
        materialTypeList.add(materialType1);
        materialTypeList.add(materialType2);
        materialTypeList.add(materialType4);
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
            adPositionPage = meterialManagerService.queryAdPositionList(adPositionQuery, positionPackIds, adPositionPage.getPageSize(), adPositionPage.getPageNo());
            
        
        } catch (Exception e) {
            logger.error("获取广告位列表时出现异常",e);
        }
        
        return SUCCESS;
    }
	
	
	
	/**
	 * 查看详情，编辑展示素材数据
	 * @return
	 * @throws ParseException
	 */
	public String initMaterial() throws ParseException{
		try {
			
			initDate();
	        materialCategoryList=meterialManagerService.getMaterialCategoryList();
	        
	        int materialId = Integer.valueOf(request.getParameter("materialId"));      
	        material = defaultMeterialManageService.getMaterialByID(materialId);
	        adPositionQuery = positionService.getAdvertPosition(material.getAdvertPositionId());
			//返回广告位的JSON信息，用于预览
			getRequest().setAttribute("positionJson", Obj2JsonUtil.object2json(adPositionQuery));
	        if(material.getResourceType()==0){
	            //图片
	            imageMeta=meterialManagerService.getImageMetaByID(material.getResourceId());
	            String sd = imageMeta.getTemporaryFilePath()+"/"+imageMeta.getName();
	            ImageSpecification imageSpecification = meterialManagerService.getImageMateSpeci(material.getAdvertPositionId());
	            request.setAttribute("imageFileWidth",imageSpecification.getImageWidth());
	            request.setAttribute("imageFileHigh",imageSpecification.getImageHeight());
	            request.setAttribute("imageFileSize",imageSpecification.getFileSize());
	           
	            if(((Integer.valueOf(imageSpecification.getImageHeight()) < Integer.valueOf(imageMeta.getFileHeigth()) && imageSpecification.getImageHeight() != null))
	            		|| ((Integer.valueOf(imageSpecification.getImageWidth()) < Integer.valueOf(imageMeta.getFileWidth())) && imageSpecification.getImageWidth() != null)
	            		|| ((Integer.valueOf(imageSpecification.getFileSize()) < Integer.valueOf(imageMeta.getFileSize())) && imageSpecification.getFileSize() != null)){
	            	
	            	judge = "不通过 ";
	            }else{
	            	judge = "通过 ";
	            }
	            String msg ="大小："+imageMeta.getFileSize()+"，宽度："+imageMeta.getFileWidth()+" px， 高度："+ imageMeta.getFileHeigth()+" px";
	            request.setAttribute("inputImageFileSize",msg);
	        }
	        if(material.getResourceType()==1){
	            //视频
	        	videoMeta = meterialManagerService.getVideoMetaByID(material.getResourceId());
	        	VideoSpecification videoSpecification = meterialManagerService.getVideoMateSpeci(material.getAdvertPositionId());
	        	request.setAttribute("videoFileDuration",videoSpecification.getDuration().toString());
	        	String ss = videoMeta.getRunTime().substring(0,videoMeta.getRunTime().length()-1);
	        	if(Integer.valueOf(ss) > videoSpecification.getDuration()){
	        		judge = "不通过 ";
	        	}else{
	        		judge = "通过 ";
	        	}
	            String msg = videoMeta.getRunTime();
	            request.setAttribute("inputVideoFileSize",msg);
	        }
	        if(material.getResourceType()==2){
	            //文字
	            textMeta=meterialManagerService.getTextMetaByID(material.getResourceId());
	            byte[] contentBlob =textMeta.getContent();
	            if(contentBlob != null){
	            	textMeta.setContentMsg(new String(contentBlob, "gbk"));
	            }
	            
	        }
	        if(material.getResourceType()==4){
	            //ZIP
	        	zipMeta=meterialManagerService.getImageMetaByID(material.getResourceId());
	            String sd = zipMeta.getTemporaryFilePath()+"/"+zipMeta.getName();
	            ImageSpecification zipSpecification = meterialManagerService.getImageMateSpeci(material.getAdvertPositionId());
	            
	            String previewImage = zipMeta.getName().substring(0, zipMeta.getName().lastIndexOf("."))+".jpg";
	            zipMeta.setFileHeigth(previewImage);
	            
	            request.setAttribute("zipFileWidth",zipSpecification.getImageWidth());
	            request.setAttribute("zipFileHigh",previewImage);
	            request.setAttribute("zipFileSize",zipSpecification.getFileSize());
	           
	            if(((Integer.valueOf(zipSpecification.getFileSize()) < Integer.valueOf(zipMeta.getFileSize())) && zipSpecification.getFileSize() != null)){
	            	judge = "不通过 ";
	            }else{
	            	judge = "通过 ";
	            }
	            String msg ="大小："+zipMeta.getFileSize();
	            request.setAttribute("inputZipFileSize",msg);
	        }
	        
	        
	        
	        
	        String ip=config.getValueByKey("ftp.ip");
	        String path=config.getValueByKey("materila.ftp.tempPath");
	        path=path.substring(5, path.length());
	        String viewPath="http://"+ip+path;
	        if(videoMeta != null){
	        	sssspath = viewPath+"/"+videoMeta.getName();
	        }
	        request.setAttribute("viewPath", viewPath);

	  
	        
	        //List<PositionAD> positionADList = contractManagerService.getPositionADByContractID(contractId);
	        request.setAttribute("material", material);
	        //saveTag=0;
	        //areasJson = Obj2JsonUtil.list2json(positionADList);
		} catch (Exception e) {
			logger.error("初始化默认素材时出现异常",e);
		}
        

        return SUCCESS;
    }

	
	/**
	 * @return
	 * @throws IOException
	 */
	public String saveMaterial() throws IOException{
	    
		try {
			if(material.getResourceTypeTemp()!=null&&material.getResourceType()==null){
		        material.setResourceType(material.getResourceTypeTemp());
		    }
			
		    if(material.getResourceType()==1){
		        //视频素材
		    	if(material.getId()==null){
		    		saveVideo();
		    	} else{
		    		//修改
		            if(localFilePath.equals("")){
	                    //没有重新上传视频
//		                VideoMeta videoMetaTemp = meterialManagerService.getVideoMetaByID(videoMeta.getId());
//		                videoMeta.setName(videoMetaTemp.getName());
//		                videoMeta.setTemporaryFilePath(videoMetaTemp.getTemporaryFilePath());
		            }else{
	                    saveVideo();
	                }	            
		            material.setModifyTime(new Date());//资产修改时间
		           
		    	}
		    	
		    	//保存素材表                         
	            material.setResourceId(videoMeta.getId());            
		    }	    
		    if(material.getResourceType()==0){
	            //图片素材
	                if(material.getId()==null){
	                	saveImageMeta();
	                    material.setCreateTime(new Date());//资产修改时间
	                }else{
	                    //修改 
	                    if(localFilePath.equals("")){
	                        //没有重新上传图片
	                    	if(imageMeta != null){
	                    		meterialManagerService.updateImageUrl(imageMeta);
	                    		material.setModifyTime(new Date());//资产修改时间
	                        }
	                    }else{
	                        saveImageMeta();
	                        material.setModifyTime(new Date());//资产修改时间
	                    }
	                }
	                
	                //保存素材表
	                material.setResourceId(imageMeta.getId());
	         
	        }
		    if(material.getResourceType()==2){
		        //文字素材
		        
	            //保存文字素材子表
	            byte[] contentBlob = textMeta.getContentMsg().getBytes("gbk");
	            textMeta.setContent(contentBlob);
	            meterialManagerService.saveTextMaterial(textMeta);
	            
	            MessageReal textMetaReal = null;
	            //保存素材表
	            if(material.getId()==null){
	                //新增
	                material.setCreateTime(new Date());
	                textMetaReal = copyToTextReal(textMeta, null);
	                defaultMeterialManageService.saveTextMaterialReal(textMetaReal);
	            }else{
	                //修改
	                material.setModifyTime(new Date());
	                textMetaReal = copyToTextReal(textMeta, material.getId());
	                defaultMeterialManageService.updateTextMaterialReal(textMetaReal);
	            }      
	            
	            material.setResourceId(textMeta.getId());
	            
		    }
		    if(material.getResourceType()==4){
	            //zip素材
	                if(material.getId()==null){
	                	saveZipMeta();
	                    material.setCreateTime(new Date());//资产修改时间
	                }else{
	                    //修改 
	                    if(localFilePath.equals("")){
	                        //没有重新上传图片
	                    }else{
	                    	saveZipMeta();
	                        material.setModifyTime(new Date());//资产修改时间
	                    }
	                }
	                
	                //保存素材表
	                material.setResourceId(zipMeta.getId());
	         
	        }
		    
		    
		    
		    material.setState('2'); 
		    material.setIsDefault(1);
		    material.setCreateTime(new Date());
		    meterialManagerService.saveResource(material);
		    ResourceReal materialReal = copyToResourceReal(material);
	        meterialManagerService.saveResourceReal(materialReal);
		} catch (Exception e) {

			logger.error("保存素材时出现异常", e);
		}
		
    
	    return SUCCESS;
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
    public String deleteDefault(){
    	String ids = request.getParameter("ids");
    	
        if(StringUtils.isNotBlank(ids)){
            try {
                
                defaultMeterialManageService.deleteMaterialByIds(ids);
            } catch (RuntimeException e) {
            	logger.error("删除素材时出现异常", e);
            	return NONE;
            }
        }
		return SUCCESS;
        
    }
    
	/**
	 * @throws IOException 
	 * 
	 */
	private void saveVideo() throws IOException {
		String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
		String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+materialName;
		InputStream in = null;
		 /**  发送文件至ftp */
		try {
		    boolean isSuccess = false;
		    String uploadPath=config.getValueByKey("materila.ftp.tempPath");
		    isSuccess = sendFile(uploadAllDir,uploadPath);
		    
		    if(videoMeta==null){
		        videoMeta = new VideoMeta();
		    }
		  //计算文件大小
            File uploadfile = new File(uploadAllDir);
            in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            int bytesum = 0;
            while ((len = in.read(buffer)) > 0) {
                 bytesum += len;
            }
		    videoMeta.setName(materialName);
		    videoMeta.setTemporaryFilePath(uploadPath);    
		    videoMeta.setFileSize(Integer.valueOf(bytesum).toString());
		    meterialManagerService.saveVideoMaterial(videoMeta);
		    
	        VideoReal videoReal = copyToVideoReal(videoMeta);
	      //FTP文件复制转移
            FtpUtils ftp = null;
                try {
                    ftp = new FtpUtils();
                    ftp.connectionFtp();
                    String localFileName=videoMeta.getName();
                    //String localDirectory=config.getValueByKey("resource.locationPath"); 
                    String remoteFileName=videoMeta.getName();
                    String remoteDirectory=videoMeta.getTemporaryFilePath();
                    ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                    String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath"); 
                    ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName);

                    // 调用接口复制视频文件到videoPump
                    UploadClient client = new UploadClient();
                    String fileNameToVideoPump = remoteDirectoryReal+"/"+localFileName;
                    String filePathToVideoPump = "default/"+material.getAdvertPositionId();
                    client.sendFileVideoPump(fileNameToVideoPump, filePathToVideoPump);
                    videoReal.setVideoPumpPath(filePathToVideoPump);
                    AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(material.getAdvertPositionId());
                    if(advertPosition!=null){
                    	if(advertPosition.getPositionPackageType()== 3){
                        	//单向非实时广告
                    		videoReal.setVideoPumpPath(videoReal.getFormalFilePath());
                        }
                    }
                    meterialManagerService.saveVideoMaterialReal(videoReal);

                    //删除本地文件
                    File localFile =new File(".././"+localFileName);
                    if(localFile.exists()&&localFile.isFile()){
                        localFile.delete();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    if (ftp != null) {
                        ftp.closeFTP();
                    }
                }
            meterialManagerService.saveVideoMaterialReal(videoReal);
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
			if(in != null){
				in.close();
			}
				
			
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
    private  ResourceReal copyToResourceReal(Resource materialTemp){
        ResourceReal materialReal = new ResourceReal();
        materialReal.setId(materialTemp.getId());
        materialReal.setResourceName(materialTemp.getResourceName());
        materialReal.setResourceType(materialTemp.getResourceType());
        materialReal.setResourceId(materialTemp.getResourceId());
        if(materialTemp.getResourceDesc()!=null&&!materialTemp.getResourceDesc().equals("")){
            materialReal.setResourceDesc(materialTemp.getResourceDesc());
        }
        materialReal.setCustomerId(materialTemp.getCustomerId());
        materialReal.setCategoryId(materialTemp.getCategoryId());
        materialReal.setContractId(materialTemp.getContractId());
        materialReal.setStartTime(materialTemp.getStartTime());
        materialReal.setEndTime(materialTemp.getEndTime());
        materialReal.setAdvertPositionId(materialTemp.getAdvertPositionId());
        materialReal.setState(materialTemp.getState());
        materialReal.setCreateTime(new Date());
        if(materialTemp.getKeyWords()!=null&&!materialTemp.getKeyWords().equals("")){
            materialReal.setKeyWords(materialTemp.getKeyWords());
        }      
        materialReal.setIsDefault(materialTemp.getIsDefault());
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
    private  ImageReal copyToImageReal(ImageMeta imageMetaTemp){
        ImageReal imageReal =new ImageReal();
        imageReal.setId(imageMetaTemp.getId());
        imageReal.setName(imageMetaTemp.getName());
        imageReal.setFileFormat(imageMetaTemp.getFileFormat());
        imageReal.setFileSize(imageMetaTemp.getFileSize());
        imageReal.setFileHeigth(imageMetaTemp.getFileHeigth());
        imageReal.setFileWidth(imageMetaTemp.getFileWidth());
        imageReal.setFormalFilePath(imageMetaTemp.getTemporaryFilePath());
        
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
    private  VideoReal copyToVideoReal(VideoMeta videoMetaTemp){
        VideoReal videoReal =new VideoReal();
        videoReal.setId(videoMetaTemp.getId());
        videoReal.setName(videoMetaTemp.getName());
        videoReal.setFileSize(videoMetaTemp.getFileSize());
        videoReal.setRunTime(videoMetaTemp.getRunTime());
        videoReal.setFormalFilePath(videoMetaTemp.getTemporaryFilePath());
        
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
    private MessageReal copyToTextReal(MessageMeta textMetaTemp, Integer id){
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
	 * 保存图片信息
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveImageMeta() throws IOException {
		String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
		String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+materialName;
		InputStream in = null;
		try {
			File uploadfile = new File(uploadAllDir);
			
			
		    /**  发送文件至ftp */
		    boolean isSuccess = false;
		    String uploadPath= config.getValueByKey("materila.ftp.tempPath");
		    isSuccess = sendFile(uploadAllDir,uploadPath);
		    
		    if(isSuccess){
		    	InputStream inputStream = new FileInputStream(uploadfile);
			    BufferedImage bi = ImageIO.read(inputStream);   
			    String fileWidth = String.valueOf(bi.getWidth());
			    String fileHigh = String.valueOf(bi.getHeight());
			    String imageFormat = materialName.substring(materialName.indexOf(".")+1, materialName.length());
			    //计算文件大小
			    in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
			    byte[] buffer = new byte[BUFFER_SIZE];
			    int len = 0;
			    int bytesum = 0;
			    while ((len = in.read(buffer)) > 0) {
			         bytesum += len;
			    }
			    if(imageMeta==null){
				    imageMeta = new ImageMeta();
				}
			    in.close();
				imageMeta.setName(materialName);
				imageMeta.setFileSize(String.valueOf(bytesum));
				imageMeta.setFileHeigth(fileHigh);
				imageMeta.setFileWidth(fileWidth);
				imageMeta.setFileFormat(imageFormat);
				imageMeta.setTemporaryFilePath(uploadPath);
				meterialManagerService.saveImageMaterial(imageMeta);
				ImageReal imageMetaReal = copyToImageReal(imageMeta);
				//FTP文件复制转移
                FtpUtils ftp = null;
                    try {
                        ftp = new FtpUtils();
                        ftp.connectionFtp();
                        String localFileName=imageMeta.getName();
                        //String localDirectory=config.getValueByKey("resource.locationPath"); ;
                        String remoteFileName=imageMeta.getName();
                        String remoteDirectory=imageMeta.getTemporaryFilePath();
                        ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");
                        ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName);    
                        AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(material.getAdvertPositionId());
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
                    } finally{
                        if (ftp != null) {
                            ftp.closeFTP();
                        }
                    }
                meterialManagerService.saveImageMaterialReal(imageMetaReal);
		    }else{
		    	// 上传FTP失败后返回响应
		    	
		    }
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		} finally {
			if(in != null){
				in.close();
			}
		}
		
	}	
	
	
	/**
	 * 保存ZIP信息
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveZipMeta() throws IOException {
		String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
		String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+materialName;
		InputStream in = null;
		try {
			File uploadfile = new File(uploadAllDir);
			String previewImageString = "";
			if(materialName != "" && materialName != null){
				String name1=materialName.substring(0, materialName.lastIndexOf("."));
		        String name2=materialName.substring(materialName.lastIndexOf("."), materialName.length());
		       
		         previewImageString = name1+".jpg";
			}
			
		    /**  发送文件至ftp */
		    boolean isSuccess = false;
		    String uploadPath= config.getValueByKey("materila.ftp.tempPath");
		    isSuccess = sendFile(uploadAllDir,uploadPath);
		    
		    String uploadDire = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+config.getValueByKey("recommend.zip.name");;
            File uploadDireFile = new File(uploadDire);

               /*发送预览图到FTP*/
               String uploadPreviewImage = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+previewImageString;
               File uploadImage = new File(uploadPreviewImage);
               sendFile(uploadPreviewImage,uploadPath);
                                  
               //计算文件大小
		    if(isSuccess){
		    	InputStream inputStream = new FileInputStream(uploadfile);
			    BufferedImage bi = ImageIO.read(inputStream);   
			    String imageFormat = materialName.substring(materialName.indexOf(".")+1, materialName.length());
			    //计算文件大小
			    in = new BufferedInputStream(new FileInputStream(uploadfile), BUFFER_SIZE);
			    byte[] buffer = new byte[BUFFER_SIZE];
			    int len = 0;
			    int bytesum = 0;
			    while ((len = in.read(buffer)) > 0) {
			         bytesum += len;
			    }
			    if(zipMeta==null){
			    	zipMeta = new ImageMeta();
				}
			    in.close();
			    zipMeta.setName(materialName);
			    zipMeta.setFileSize(String.valueOf(bytesum));
			    zipMeta.setFileFormat(imageFormat);
			    zipMeta.setTemporaryFilePath(uploadPath);
				meterialManagerService.saveImageMaterial(zipMeta);
				ImageReal imageMetaReal = copyToImageReal(zipMeta);
				//FTP文件复制转移
                FtpUtils ftp = null;
                    try {
                        ftp = new FtpUtils();
                        ftp.connectionFtp();
                        String localFileName=zipMeta.getName();
                        //String localDirectory=config.getValueByKey("resource.locationPath");
                        String remoteFileName=zipMeta.getName();
                        String remoteDirectory=zipMeta.getTemporaryFilePath();
                        
                        String previewImage = zipMeta.getName().substring(0, zipMeta.getName().lastIndexOf("."))+".jpg";
                        String localPreviewName = previewImage;
                        ftp.downloadToLocal(remoteFileName, localFileName, remoteDirectory, null);
                        ftp.downloadToLocal(previewImage, localPreviewName, remoteDirectory, null);//下载预览图
                        String remoteDirectoryReal=config.getValueByKey("materila.ftp.realPath");
                        
                        ftp.uploadFileToRemote(".././"+localFileName, remoteDirectoryReal, localFileName);  
                        ftp.uploadFileToRemote(".././"+localPreviewName, remoteDirectoryReal, localPreviewName);  
                        
                        AdvertPosition advertPosition =meterialManagerService.getAdvertPosition(material.getAdvertPositionId());
                        if(advertPosition!=null){
                        	if(advertPosition.getPositionPackageType()==0 ||advertPosition.getPositionPackageType()==1){
                            	//双向图片素材审核时调用下面函数上传至 双向资源服务器
                            	UploadClient client  = new UploadClient();
                            	String fileNameToCps = remoteDirectoryReal+"/"+localFileName;
                            	client.sendFtpFileToCps(fileNameToCps, null);
                            }
                        }
                        
                       /* //删除本地文件
                        File localFile =new File(".././"+localFileName);
                        if(localFile.exists()&&localFile.isFile()){
                            localFile.delete();
                        }*/

                        deleteFile(uploadfile);
                        deleteFile(uploadImage);
                        deleteFile(uploadDireFile);


                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally{
                        if (ftp != null) {
                            ftp.closeFTP();
                        }
                    }
                meterialManagerService.saveImageMaterialReal(imageMetaReal);
		    }else{
		    	// 上传FTP失败后返回响应
		    }
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		} finally {
			if(in != null){
				in.close();
			}
		}
		
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
                return false;
            } finally{
                if (ftp != null) {
                    ftp.closeFTP();
                }
            }
            return true;
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

    public Resource getMaterial() {
        return material;
    }

    public void setMaterial(Resource material) {
        this.material = material;
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

    public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public DefaultMeterialManageService getDefaultMeterialManageService() {
		return defaultMeterialManageService;
	}


	public void setDefaultMeterialManageService(
			DefaultMeterialManageService defaultMeterialManageService) {
		this.defaultMeterialManageService = defaultMeterialManageService;
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


	public MessageMeta getTextMeta() {
		return textMeta;
	}


	public void setTextMeta(MessageMeta textMeta) {
		this.textMeta = textMeta;
	}


	public PositionService getPositionService() {
		return positionService;
	}


	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}


	public String getPositionPackIds() {
		return positionPackIds;
	}


	public void setPositionPackIds(String positionPackIds) {
		this.positionPackIds = positionPackIds;
	}


	public String getJudge() {
		return judge;
	}


	public void setJudge(String judge) {
		this.judge = judge;
	}


	public ImageMeta getZipMeta() {
		return zipMeta;
	}


	public void setZipMeta(ImageMeta zipMeta) {
		this.zipMeta = zipMeta;
	}
	
	
}
