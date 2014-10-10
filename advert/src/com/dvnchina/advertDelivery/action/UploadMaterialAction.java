package com.dvnchina.advertDelivery.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.model.Material;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoSpecification;
import com.dvnchina.advertDelivery.order.bean.CommonBean;
import com.dvnchina.advertDelivery.service.UploadMaterialService;
import com.dvnchina.advertDelivery.service.common.tools.support.OperatorLocalFileService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.CookieUtils;
import com.dvnchina.advertDelivery.utils.file.FileOperate;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.dvnchina.advertDelivery.utils.xml.MaterialConfig;

public class UploadMaterialAction extends BaseActionSupport<Object> implements ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private static final Map<String, ArrayList<Material>> mKindMap  = MaterialConfig.matkindMap;
	private static final Map<String, ArrayList<Material>> mTypeMap  = MaterialConfig.matTypeMap;
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	
	private File backgroundImage;
	private File backgroundImage2;
	private File zipbackgroundImage;
	
	private String backgroundImageFileName;
	private String backgroundImage2FileName;
	private String zipbackgroundImageFileName;
	
//	private OrderService orderService;
	
	/** 合同id */
	private Integer contractId;
	private Integer advertPositionId;//子广告位ID,用于效验广告位的素材规格
	
	/**
	 * 上传文件存放的临时目录
	 */
	private static final String MATERIAL_TEMP_DIR = config.getValueByKey("upload.file.temp.dir");
	/**
	 * 保存上传文件的目录，相对于Web应用程序的根路径，在struts.xml中配置
	 */
	private String uploadDir;
	/**
	 * 文件目录分割符
	 */
	private static final String _SEPARATOR = File.separator;
	/**
	 * 操作本地文件
	 */
	private OperatorLocalFileService operatorLocalFileService;
	
	private static String jsonMsg  = "";
	private UploadMaterialService uploadMaterialService;
	private Logger logger = Logger.getLogger(UploadMaterialAction.class);
	
	/**
	 * 上传的文件名
	 */
	protected String uploadFileName;
	
	/**
	 * 根据合同查询广告位
	 * */
//	public String getPositionsByContract() {
//		try {
//			
//			String contractId = this.getRequest().getParameter("contractId");
//			List<OrderPositionBean> positions = orderService.getPositionsByContract(Integer.parseInt(contractId));
//			String content = Obj2JsonUtil.list2json(positions);
//			renderJson(content);
//		} catch (Exception e) {
//			logger.error("获取广告数据出现异常", e);
//			renderText("-1");
//		}
//		return null;
//	}

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
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}
	
	/**
	 * 素材上传页面系统配置数据展示
	 * @return
	 */
//	public String showUploaldMaterial(){
//		//读取配置文件中的信息
//		ArrayList<Material> mKindList= mKindMap.get("kind");
//		
//		try {
//			List<Contract> contracts = orderService.getContractsByUser(this.getUserId());
//			List<AdvertPositionType> positionTypes = orderService.getPositionType();
//			CommonBean[] positionModes = this.getPositionMode();
//			HttpServletRequest request = getRequest();
//			request.setAttribute("contracts", contracts);
//			request.setAttribute("contractJson", Obj2JsonUtil.list2json(contracts));
//			request.setAttribute("positionTypes", positionTypes);
//			request.setAttribute("positionModes", positionModes);
//			
//			
//			
//		} catch (Exception e) {
//			logger.error("创建订单获取信息出现异常", e);
//		}
//		
//		
//	    ArrayList<Material> mTypeList= mTypeMap.get("type");
//	 /* List<AdvertPosition>  pList = uploadMaterialService.getAllPosition();
//		List<ContractBackup>  cList = uploadMaterialService.getAllContract();
//		request.setAttribute("pList", pList);
//		request.setAttribute("cList", cList);*/
//	    request.setAttribute("mTypeList", mTypeList);
//		request.setAttribute("mKindList", mKindList);
//		return SUCCESS;
//	}
	
	
	/**
	 * 获取广告位投放方式数据
	 * */
	private CommonBean[] getPositionMode() {
		try {
			CommonBean[] positionModes = new CommonBean[2];
			CommonBean c1 = new CommonBean();
			c1.setId(Constant.ADVERT_POSITION_WAY_ADVERT);
			c1.setName("投放式");
			positionModes[0] = c1;
			CommonBean c2 = new CommonBean();
			c2.setId(Constant.ADVERT_POSITION_WAY_REQUEST);
			c2.setName("请求式");
			positionModes[1] = c2;
			return positionModes;
		} catch (Exception e) {
			logger.error("获取广告位投放模式数据出现异常", e);
			return null;
		}
	}
	
	/**
	 * 渲染返回信息到页面
	 * @param result  boolean
	 * 				消息返回结果
	 * @param msg	  String
	 * 				消息详细内容
	 */
	@SuppressWarnings("unchecked")
	private String renderMsg(boolean result ,String msg){
		Map map = new HashMap(); 
		map.put("result", result);
		map.put("msg", msg);
		return Obj2JsonUtil.object2json(map);
	}
	
	@SuppressWarnings("unchecked")
	public String checkFileName(){
		
		try {
			// 素材上传的地址
			String uploadPath = request.getParameter("uploadPath");
			
			if(StringUtils.isBlank(uploadPath)){
				
				jsonMsg = this.renderMsg(false, "数据不正确联系管理员！");
				this.renderJson(jsonMsg);
				return NONE;
			}
			
			File treeNodeDir = new File(uploadPath);
			File[] nodeFiles = treeNodeDir.listFiles();
			String materialName = this.getUploadFileName();
			boolean isHasSamename = false;
			if(null != nodeFiles && nodeFiles.length > 0){
				for(int i = 0 ; i < nodeFiles.length; i++){
					if(nodeFiles[i].getName().equals(materialName)){
						isHasSamename = true;
					}
				}
			}
			Map map = new HashMap(); 
			map.put("result", true);
			map.put("msg", "上传文件在当前素材节点下有相同的名字");
			map.put("isHasSame", isHasSamename);
			jsonMsg = Obj2JsonUtil.object2json(map);
			this.renderJson(jsonMsg);
			
		} catch (NumberFormatException e) {
			logger.info("数据不正确联系管理员#### ", e);
			jsonMsg = this.renderMsg(false, "数据不正确联系管理员！");
			this.renderJson(jsonMsg);
			return NONE;
		}
		return NONE;
	}

	/**
	 * 写入素材表
	 */
	public String writeMaterial(){
		/**	 素材的自身类型 ：0-视频， 1-图片，2-文字 */
		String materialType = request.getParameter("materialType");
		
		/**  文字名称 */
		String messageName = request.getParameter("messageName");
		
		/**  文字内容 */
		String content = request.getParameter("content");
		
		/**  文字URL */
		String url = request.getParameter("url");
		
		/**  素材文件时长 */
		String runtime = request.getParameter("runtime");
		
		/** 素材描述 */
		String desc = request.getParameter("desc");
		
		/**  素材本地路径 */
		String localFilePath = request.getParameter("localFilePath");
		
		/**  合同编号 */
		String contractId = request.getParameter("contractId");
		
		/**  素材上传路径 */
		String uploadPath = uploadMaterialService.getMetarialPath(contractId);
		
		/**  广告位的ID */
		String positionId = request.getParameter("positionId");
		
		if(Integer.valueOf(materialType)==0){
			uploadPath = uploadPath+"/video";
		}else if(Integer.valueOf(materialType)==1){
			uploadPath = uploadPath+"/image";
		}
		
		String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());

		String tempPath = MATERIAL_TEMP_DIR + _SEPARATOR + materialName;
		
		String uploadAllDir = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+materialName;
		
		File uploadfile = new File(uploadAllDir);
		
		File dstFile = new File(tempPath);
		
		String maxVal = "",maxWidth = "",maxHigh = "",fileFormat = "",textLength = "";
		Integer duration = 0;
		String[] fileFormatArr = null;
		int fileSize = 0;
		InputStream inputStream;
		String fileWidth = null;
		String fileHigh = null;
		String imageFormat = null;
		boolean isSuccess = false;
		if (StringUtils.isNotBlank(positionId)) {
			Integer positionID = Integer.valueOf(positionId);
			/** 检验文件 */
			
			if(Integer.valueOf(materialType)!=2){
				/** 文件是否已经存在 */
				Integer resourceId = uploadMaterialService.getResourceId(Integer.valueOf(materialType), materialName, uploadPath);
				if(resourceId != null){
					logger.info("文件已经存在！");
					jsonMsg = this.renderMsg(false, "文件已经存在！");
					this.renderJson(jsonMsg);
					return NONE;
				}
				/**  取得文件规格信息 */
				if(Integer.valueOf(materialType)==0){
					VideoSpecification videoSpeci = uploadMaterialService.getVideoFileSpeci(positionID).get(0);
					maxVal = videoSpeci.getFileSize();
					duration = videoSpeci.getDuration();
					fileFormat = videoSpeci.getType();
					if(fileFormat.indexOf(",") > 0){
						fileFormatArr = fileFormat.split(",");
					}else {
						fileFormatArr = new String[1];
						fileFormatArr[0] = fileFormat;
					}
				}else if(Integer.valueOf(materialType)==1){ 
					ImageSpecification imageSpeci = uploadMaterialService.getImageFileSpeci(positionID).get(0);
					maxVal = imageSpeci.getFileSize();
					maxWidth = imageSpeci.getImageWidth();
					maxHigh = imageSpeci.getImageLength();
					fileFormat = imageSpeci.getType();
					if(fileFormat != null&&!"".equals(fileFormat)){
						if(fileFormat.indexOf(",") > 0){
							fileFormatArr = fileFormat.split(",");
						}else {
							fileFormatArr = new String[1];
							fileFormatArr[0] = fileFormat;
						}
					}
				}
				
				/** 初始化 文件最大尺寸 */
				int maxSize = -1;
				String regx = "^[[1-9]+[0-9]*]*[\\.]{1}[[0-9]{1,2}]*$|^[[1-9]+[0-9]*]*$";
				
				if (StringUtils.isNotBlank(maxVal)) {
					if(maxVal.matches(regx)){
						maxSize = Integer.valueOf(maxVal);
					}else{
						if(maxVal.contains("K")){
							maxVal = maxVal.replaceAll("[^0-9\\u4e00-\\u9fa5]", "");
							maxSize = Integer.valueOf(maxVal) * 1024;
						}else if(maxVal.contains("M")){
							maxVal = maxVal.replaceAll("[^0-9\\u4e00-\\u9fa5]", "");
							maxSize = Integer.valueOf(maxVal) * 1024 * 1024;
						}
						
					}
				} else {
					logger.info("数据为空！### Material_max_value=" + maxVal);
					jsonMsg = this.renderMsg(false, "广告位的最大尺寸数据不能为空！");
					this.renderJson(jsonMsg);
					return NONE;
				}
	
				/**  复制到临时文件夹内 */
				try {
					fileSize = FileOperate.copyFile2FileSize(uploadfile, dstFile);
					if(fileSize > 0){ //复制成功后，删除Struts 文件流字节的大小
						FileOperate.delAllFile(dstFile.getParent());
					}else{
						logger.info("上传的文件超过struts配置的大小！");
						jsonMsg = this.renderMsg(false, "上传的文件超过广告位的大小！");
						this.renderJson(jsonMsg);
						return NONE;
					}
					
					/** 上传文件的大小和广告位的大小进行验证*/
					if (fileSize > maxSize) {
						logger.info("上传的文件超过广告位的大小### Material_max_value=" + maxVal);
						jsonMsg = this.renderMsg(false, "上传的文件超过广告位的大小！");
						this.renderJson(jsonMsg);
						return NONE;
					}
				} catch (Exception e1) {
					logger.info("上传的文件超过struts配置的大小！",e1);
					jsonMsg = this.renderMsg(false, "上传的文件超过广告位的大小！");
					this.renderJson(jsonMsg);
					return NONE;
				}
				
				/**  校验图片的宽高 */
				if(Integer.valueOf(materialType) == 1){
					try {
						inputStream = new FileInputStream(uploadfile);
						BufferedImage bi = ImageIO.read(inputStream);   
						fileWidth = String.valueOf(bi.getWidth());
						fileHigh = String.valueOf(bi.getHeight());
					} catch (Exception e1) {
						logger.info("获取图片的宽和高值失败！" + e1);
					}
					if (Integer.valueOf(fileWidth) > Integer.valueOf(maxWidth)) {
						logger.info("上传的文件的宽度超过广告位的大小### maxWidth=" + maxWidth);
						jsonMsg = this.renderMsg(false, "上传的文件的宽度超过广告位的大小！");
						this.renderJson(jsonMsg);
						return NONE;
					}
					if (Integer.valueOf(fileHigh) > Integer.valueOf(maxHigh)) {
						logger.info("上传的文件的高度超过广告位的大小### maxHigh=" + maxHigh);
						jsonMsg = this.renderMsg(false, "上传的文件的高度超过广告位的大小！");
						this.renderJson(jsonMsg);
						return NONE;
					}
				}
				
				/**  校验文件格式 */
				imageFormat = materialName.substring(materialName.indexOf(".")+1, materialName.length());
				if(fileFormatArr.length > 0){
					boolean f = false;
					for(String formatName : fileFormatArr){
						System.out.println(formatName);
						if (formatName.equals(imageFormat)) {
							f = true;
						}
					}
					if(!f){
						logger.info("上传的文件的格式不符合广告位的格式！");
						jsonMsg = this.renderMsg(false, "上传的文件的格式不符合广告位的格式！");
						this.renderJson(jsonMsg);
						return NONE;
					}
				}
				 
				/**  校验视频文件的时长 */
				if(Integer.valueOf(materialType) == 0){
					if(runtime != null && !"".equals(runtime)){
						if (Integer.valueOf(runtime) > duration) {
							logger.info("上传的文件的时长超过广告位的大小### duration=" + duration);
							jsonMsg = this.renderMsg(false, "上传的文件的时长超过广告位的大小！");
							this.renderJson(jsonMsg);
							return NONE;
						}
					}
				}
				
				/**  发送文件至ftp */
				try {
					isSuccess = sendFile(uploadAllDir,uploadPath);
					//FileOperate.delAllFile(uploadfile.getParent());
				} catch (Exception e) {
					e.printStackTrace();
					return NONE;
				}
			}else if(Integer.valueOf(materialType)==2){ /** 检验文字 */
				TextSpecification contentSpeci = uploadMaterialService.getMessageSpeci(positionID).get(0);
				/** 检验文字是否存在 */
				MessageMeta messageMeta = uploadMaterialService.getMessageMeta(messageName);
				if(messageMeta != null){
					jsonMsg = this.renderMsg(false, "内容已经存在！");
					this.renderJson(jsonMsg);
					logger.error("内容已经存在！");
					return NONE;
				}
				textLength = contentSpeci.getTextLength();
				/**  校验文字的长度 */
				if(Integer.valueOf(materialType) == 2){
					if(content != null && !"".equals(content)){
						if (content.length() > Integer.valueOf(textLength)) {
							logger.info("上传的文字长度超过广告位的大小### duration=" + duration);
							jsonMsg = this.renderMsg(false, "上传的文字长度超过广告位的大小！");
							this.renderJson(jsonMsg);
							return NONE;
						}
					}
				}
			}
		}
		if(isSuccess == true){
			/**  写入素材数据库 */
			try {
				switch (Integer.valueOf(materialType)) {
				case 0:
					VideoMeta videoModel = new VideoMeta();
					videoModel.setName(materialName);
					videoModel.setDescription(desc);
					videoModel.setRunTime(runtime);
					videoModel.setTemporaryFilePath(uploadPath);
					uploadMaterialService.addVideoMaterial(videoModel);
					break;

				case 1:
					ImageMeta imageModel = new ImageMeta();
					imageModel.setName(materialName);
					imageModel.setDescription(desc);
					imageModel.setFileSize(String.valueOf(fileSize));
					imageModel.setFileWidth(fileWidth);
					imageModel.setFileHeigth(fileHigh);
					imageModel.setFileFormat(imageFormat);
					imageModel.setTemporaryFilePath(uploadPath);
					uploadMaterialService.addImageMaterial(imageModel);
					break;
				case 2:
					MessageMeta messageModel = new MessageMeta();
					messageModel.setName(messageName);
					byte[] contentBlob = content.getBytes("gbk");
					messageModel.setContent(contentBlob);
					messageModel.setURL(url);
					uploadMaterialService.addMessageMaterial(messageModel);
					break;
				}
				logger.info("上传文件成功！");
				jsonMsg = this.renderMsg(true, "上传文件成功！");
				this.renderJson(jsonMsg);
			} catch (Exception e) {
				logger.error("写入素材出现异常",e);
			}
		}else{
			logger.info("上传文件失败！");
			jsonMsg = this.renderMsg(false, "上传文件没有成功，请检查ftp连接是否正常！");
			this.renderJson(jsonMsg);
		}
		return NONE;
	}
	
	/**
	 * 写入资产表
	 */
	public String writeResource(){
		/**	 素材的自身类型 ：0-视频， 1-图片，2-文字，3-压缩包 */
		String materialType = request.getParameter("materialType");
		
		/** 素材描述 */
		String desc = request.getParameter("desc");
		
		/**  素材本地路径 */
		String localFilePath = request.getParameter("localFilePath");
		
		/**  素材上传名称 */
		String materialName = localFilePath.substring(localFilePath.lastIndexOf("/")+1,localFilePath.length());
		
		/**  文字名称 */
		String messageName = request.getParameter("messageName");

		/**  广告位的ID */
		String positionId = request.getParameter("positionId");
		
		/**  所属广告商ID */
		String businessId = request.getParameter("businessId");
		
		/**  所属合同编号 */
		String contractCode = request.getParameter("contractCode");
		
		/**  所属内容分类 */
		String contentSort = request.getParameter("contentSort");
		
		/**  有效期开始时间 */
		String startTime = request.getParameter("startTime");
		
		/**  有效期结束时间 */
		String endTime = request.getParameter("endTime");
		
		/**  素材上传路径 */
		String uploadPath = request.getParameter("uploadPath");
		
		/**  操作员ID */
		Integer operationId = (Integer) request.getSession().getAttribute("operationId");
		
		try {
			/**  写入资产表 */
			Resource resource = new Resource();
			resource.setResourceType(Integer.valueOf(materialType));
			Integer resourceId = 0;
			
			if(Integer.valueOf(materialType)==2){
				MessageMeta messageModel = uploadMaterialService.getMessageMeta(messageName);
				resourceId = messageModel.getId();
				String content = new String(messageModel.getContent());
				Resource resourceOld = uploadMaterialService.getResource(messageName,resourceId);
				if(resourceOld != null){
					jsonMsg = this.renderMsg(false, "内容已经存在！");
					this.renderJson(jsonMsg);
					logger.error("内容已经存在！");
					return NONE;
				}
				resource.setResourceName(messageName);
				resource.setResourceId(resourceId);
				resource.setResourceDesc(content);
				
			}else{
				if(Integer.valueOf(materialType)==0){
					resourceId = uploadMaterialService.getResourceId(0,materialName,uploadPath);
				}
				if(Integer.valueOf(materialType)==1){
					resourceId = uploadMaterialService.getResourceId(1,materialName,uploadPath);
				}
				Resource resourceOld = uploadMaterialService.getResource(materialName,resourceId);
				if(resourceOld != null){
					jsonMsg = this.renderMsg(false, "内容已经存在！");
					this.renderJson(jsonMsg);
					logger.error("内容已经存在！");
					return NONE;
				}
				resource.setResourceId(resourceId);
				resource.setResourceDesc(desc);
				resource.setResourceName(materialName);
				
			}
			
			if(businessId != null&&!"".equals(businessId)){
				resource.setCustomerId(Integer.valueOf(businessId));
			}
			if(contentSort != null&&!"".equals(contentSort)){
				resource.setCategoryId(Integer.valueOf(contentSort));
			}
			if(contractCode != null&&!"".equals(contractCode)){
				resource.setContractId(Integer.valueOf(contractCode));
			}
			ContractBackup contract = uploadMaterialService.getContract(contractCode);
			Date startC = contract.getEffectiveStartDate();
			Date endC = contract.getEffectiveEndDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startT = sdf.parse(startTime);
			Date endT = sdf.parse(endTime);
			if(startT.compareTo(startC)<0 || endT.compareTo(endC)>0){
				jsonMsg = this.renderMsg(false, "素材的生效日期必能在合同日期之内！");
				this.renderJson(jsonMsg);
				logger.error("素材的有效日期与合同日期不符！");
				return NONE;
			}
			resource.setStartTime(startT);
			resource.setEndTime(endT);
			if(positionId != null&&!"".equals(positionId)){
				resource.setAdvertPositionId(Integer.valueOf(positionId));
			}
			resource.setState("0".charAt(0));
			resource.setCreateTime(new Date());
			resource.setOperationId(operationId);
			uploadMaterialService.addResource(resource);
			jsonMsg = this.renderMsg(true, "资产保存成功！");
			this.renderJson(jsonMsg);
		} catch (Exception e) {
			jsonMsg = this.renderMsg(false, "资产保存失败！");
			this.renderJson(jsonMsg);
			logger.error("写入素材出现异常",e);
		}
		return NONE;
	}
	
	/**
	 * 上传素材
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void uploadMaterial(){
		try {
		    //效验广告位的素材规格advertPositionId
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
			request.setAttribute("imageFileName", backgroundImageFileName);
			Map param = new HashMap();
			param.put("targetDirectory",uploadDir);
			param.put("advertPositionId",advertPositionId);
			//文件重命名,规则为:原名_yyyyMMdd
	        String name1=backgroundImageFileName.substring(0, backgroundImageFileName.lastIndexOf("."));
	        String name2=backgroundImageFileName.substring(backgroundImageFileName.lastIndexOf("."), backgroundImageFileName.length());
	        //backgroundImageFileName=name1+"_"+new Date().getTime()+name2;
	        backgroundImageFileName="image_"+new Date().getTime()+name2;

	        param.put("oldFileName",name1);
			String result = operatorLocalFileService.copyLocalFileTargetDirectory(this.backgroundImage.getAbsolutePath(), filePath,backgroundImageFileName,param);
			renderHtml(result);
		} catch (Exception e) {
			logger.error("上传图片素材出现异常",e);
		}
	}
	
	public void uploadMaterialVideo(){
		try {
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
			Map param = new HashMap();
			param.put("targetDirectory",uploadDir);
			param.put("advertPositionId",advertPositionId);
			//文件重命名,规则为:原名_yyyyMMdd
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String ss = df.format(new Date());
            String name1=backgroundImage2FileName.substring(0, backgroundImage2FileName.lastIndexOf("."));
            String name2=backgroundImage2FileName.substring(backgroundImage2FileName.lastIndexOf("."), backgroundImage2FileName.length());
            backgroundImage2FileName="video_"+new Date().getTime()+name2;
			
            param.put("oldFileName",name1);
			String result = operatorLocalFileService.copyLocalFileTargetDirectory(this.backgroundImage2.getAbsolutePath(), filePath,backgroundImage2FileName,param);
			renderHtml(result);
		} catch (Exception e) {
			logger.error("上传视频素材出现异常",e);
		}
	}
	
	public void uploadQuestionTemplate(){
        try {
            String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
            request.setAttribute("imageFileName", backgroundImageFileName);
            Map param = new HashMap();
            param.put("targetDirectory",uploadDir);
            String result = operatorLocalFileService.copyLocalFileTargetDirectory(this.backgroundImage.getAbsolutePath(), filePath,backgroundImageFileName,param);
            renderHtml(result);
        } catch (Exception e) {
            logger.error("上传问卷模板出现异常",e);
        }
    }
	
	//-----------------upload zip start
	public void uploadMaterialZip(){
		try {
		    //效验广告位的素材规格advertPositionId
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
			request.setAttribute("imageFileName", zipbackgroundImageFileName);
			Map param = new HashMap();
			param.put("targetDirectory",uploadDir);
			param.put("advertPositionId",advertPositionId);
			zipbackgroundImageFileName.getBytes("UTF-8");
			//文件重命名,规则为:原名_yyyyMMdd
	        String name1=zipbackgroundImageFileName.substring(0, zipbackgroundImageFileName.lastIndexOf("."));
	        String name2=zipbackgroundImageFileName.substring(zipbackgroundImageFileName.lastIndexOf("."), zipbackgroundImageFileName.length());
	        //backgroundImageFileName=name1+"_"+new Date().getTime()+name2;
	        zipbackgroundImageFileName="zip"+new Date().getTime()+name2;
	        String zipPreviewImageFileName = zipbackgroundImageFileName.substring(0, zipbackgroundImageFileName.lastIndexOf("."))+".jpg";
	        param.put("oldFileName",name1);
	        param.put("zipName", name1);
	        //将文件移动，并重命名
	        String result = operatorLocalFileService.copyLocalFileTargetDirectory(this.zipbackgroundImage.getAbsolutePath(), filePath,zipbackgroundImageFileName,param);
	       
			
			//将ZIP文件解压
            String fileName = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/"+zipbackgroundImageFileName;
            String zipfilePath = ServletActionContext.getServletContext().getRealPath(uploadDir)+"/";
          
            String dirName = "";
            ZipFile zipFile = new ZipFile(fileName);
            Enumeration emu = zipFile.entries();
            while(emu.hasMoreElements()){
            ZipEntry entry = (ZipEntry)emu.nextElement();
            //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
            if (entry.isDirectory()){
              new File(zipfilePath + entry.getName()).mkdirs();
              dirName = entry.getName();
              continue;
            }
            dirName = dirName.split("/")[0];
            BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
             
            File file = new File(zipfilePath + entry.getName());
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
            String zipDirName = config.getValueByKey("recommend.zip.name");//压缩包内文件夹名称
            String prewImg = config.getValueByKey("recommend.zip.previewImg");//压缩包内图片名称
            
           //解压之后将文件夹内图片重命名做为预览
            operatorLocalFileService.copyLocalFile2TargetDirectory(filePath+"/"+dirName+"/"+prewImg,filePath,zipPreviewImageFileName);
			renderHtml(result);
		} catch (Exception e) {
			logger.error("上传ZIP素材出现异常",e);
		}
	}
	//-----------------upload zip end
	/**
	 * 上传TVN
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void uploadTvn(){
		try {
		    //效验广告位的素材规格advertPositionId
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
			request.setAttribute("imageFileName", backgroundImageFileName);
			Map param = new HashMap();
			param.put("targetDirectory",uploadDir);
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        String ss = df.format(new Date());
	        backgroundImageFileName=ss+backgroundImageFileName.substring(backgroundImageFileName.lastIndexOf("."));
	     
			String aaa = operatorLocalFileService.copyLocalFileTargetDirectory(this.backgroundImage.getAbsolutePath(), filePath,backgroundImageFileName,param);
			
			
			
			File file = new File(filePath+File.separator+backgroundImageFileName);
			// InputStream stream = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file))); 
			String result="";
			String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                result=result+line+",";
                
            }
            result=result.substring(0,result.length()-1);
            br.close();
            renderHtml(result);
		} catch (Exception e) {
			logger.error("上传背景图出现异常",e);
		}
	}
	/**
	 * 上传TVN
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void uploadUiresource(){
		try {
		    //效验广告位的素材规格advertPositionId
			String filePath = ServletActionContext.getServletContext().getRealPath(uploadDir);
			request.setAttribute("imageFileName", backgroundImageFileName);
			Map param = new HashMap();
			param.put("targetDirectory",uploadDir);
			param.put("advertPositionId",advertPositionId);
			//文件重命名,规则为:原名_yyyyMMdd
	        String name1=backgroundImageFileName.substring(0, backgroundImageFileName.lastIndexOf("."));
	        String name2=backgroundImageFileName.substring(backgroundImageFileName.lastIndexOf("."), backgroundImageFileName.length());
	        //backgroundImageFileName=name1+"_"+new Date().getTime()+name2;
	        backgroundImageFileName="image_"+new Date().getTime()+name2;

	        param.put("oldFileName",name1);
			String result = operatorLocalFileService.copyLocalFileTargetDirectory(this.backgroundImage.getAbsolutePath(), filePath,backgroundImageFileName,param);
			renderHtml(result);
		} catch (Exception e) {
			logger.error("上传图片素材出现异常",e);
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
	
	/**
	 * render JSON  方法重载
	 */
	public void renderJson(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/json");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setUploadMaterialService(UploadMaterialService uploadMaterialService) {
		this.uploadMaterialService = uploadMaterialService;
	}

	public File getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(File backgroundImage) {
		this.backgroundImage = backgroundImage;
	}


	public String getBackgroundImageFileName() {
		return backgroundImageFileName;
	}

	public void setBackgroundImageFileName(String backgroundImageFileName) {
		this.backgroundImageFileName = backgroundImageFileName;
	}

	public OperatorLocalFileService getOperatorLocalFileService() {
		return operatorLocalFileService;
	}

	public void setOperatorLocalFileService(
			OperatorLocalFileService operatorLocalFileService) {
		this.operatorLocalFileService = operatorLocalFileService;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

//	public OrderService getOrderService() {
//		return orderService;
//	}
//
//	public void setOrderService(OrderService orderService) {
//		this.orderService = orderService;
//	}

	public UploadMaterialService getUploadMaterialService() {
		return uploadMaterialService;
	}

	public File getBackgroundImage2() {
		return backgroundImage2;
	}

	public void setBackgroundImage2(File backgroundImage2) {
		this.backgroundImage2 = backgroundImage2;
	}

    public String getBackgroundImage2FileName() {
        return backgroundImage2FileName;
    }

    public void setBackgroundImage2FileName(String backgroundImage2FileName) {
        this.backgroundImage2FileName = backgroundImage2FileName;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getAdvertPositionId() {
        return advertPositionId;
    }

    public void setAdvertPositionId(Integer advertPositionId) {
        this.advertPositionId = advertPositionId;
    }

    
	public File getZipbackgroundImage() {
		return zipbackgroundImage;
	}

	public void setZipbackgroundImage(File zipbackgroundImage) {
		this.zipbackgroundImage = zipbackgroundImage;
	}

	public String getZipbackgroundImageFileName() {
		return zipbackgroundImageFileName;
	}

	public void setZipbackgroundImageFileName(String zipbackgroundImageFileName) {
		this.zipbackgroundImageFileName = zipbackgroundImageFileName;
	}
	
}
