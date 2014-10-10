package com.dvnchina.advertDelivery.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import sun.net.ftp.FtpClient;

import com.dvnchina.advertDelivery.constant.FTPConstant;
import com.dvnchina.advertDelivery.constant.ResourceMetasConstant;
import com.dvnchina.advertDelivery.dao.AdAssetsDao;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.service.AdAssetsService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class AdAssetsServiceImpl implements AdAssetsService{
	
	private Logger logger = Logger.getLogger(AdAssetsServiceImpl.class);
	
	// Dao 层
	private AdAssetsDao adAssetsDao;
	
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	
	private FtpUtils ftpUtil =new FtpUtils();
	private FtpClient ftpClient = null;
	
	/**
	 * 文件分割符
	 */
	private String SEP = File.separator;
	
	
	
	@Override
	public int deleteDownImageMeta(Integer id) {
		
		int count = 0;
		
		if(id != null && !"".equals(id)){
			//更改运行期表数据
			ResourceReal resourceReal = adAssetsDao.getResourceRealById(id);
			resourceReal.setState(ResourceMetasConstant.DOWNLINE);
			adAssetsDao.updateResourceReal(resourceReal);
			//更改维护期表状态
			Resource resource = adAssetsDao.getResourceById(id);
			resource.setState(ResourceMetasConstant.DOWNLINE);
			adAssetsDao.updateResource(resource);
			count = 1;
			
		}
		
		return count;
	}

	@Override
	public int deleteDownMessageMeta(Integer id) {
		
		int count = 0;
		if(id != null && !"".equals(id)){
			//更改运行期表数据
			ResourceReal resourceReal = adAssetsDao.getResourceRealById(id);
			resourceReal.setState(ResourceMetasConstant.DOWNLINE);
			adAssetsDao.updateResourceReal(resourceReal);
			//更改维护期表状态
			Resource resource = adAssetsDao.getResourceById(id);
			resource.setState(ResourceMetasConstant.DOWNLINE);
			adAssetsDao.updateResource(resource);
			count = 1;
			
		}
		
		return count;
	}

	@Override
	public int deleteDownVideoMeta(Integer id) {
		
		int count = 0;
		if(id != null && !"".equals(id)){
			
			//更改运行期表数据
			ResourceReal resourceReal = adAssetsDao.getResourceRealById(id);
			resourceReal.setState(ResourceMetasConstant.DOWNLINE);
			adAssetsDao.updateResourceReal(resourceReal);
			//更改维护期表状态
			Resource resource = adAssetsDao.getResourceById(id);
			resource.setState(ResourceMetasConstant.DOWNLINE);
			adAssetsDao.updateResource(resource);
			count = 1;
		}
		return count;
	}
	
	
	
	@Override
	public int deleteAuditResource(Integer id) {
		logger.debug("------deleteAuditImageReal  start--------");
		int count = 0;
		if(id != null){
			Resource resource = adAssetsDao.getResourceById(id);
			resource.setState(ResourceMetasConstant.DOWN_LINE_DELETE);
			adAssetsDao.updateResource(resource);
			count = 1;
		}
		return count;
	}
	
	/**
	 * 运营商遍历结果集
	 */
	public List listAdAssestsMgrReal(ResourceReal resourceReal,int x, int y,String state){
		logger.debug("------listAdAssestsMgrReal  start--------");
		return adAssetsDao.listAdAssestsMgrReal(resourceReal, x, y,state);
		
	}
	
	/**
	 * 遍历结果集
	 */
	public List listAdAssestsMgr(Resource resource,int x, int y,String state){
		logger.debug("------listAdAssestsMgr  start--------");
		
		Integer userId = resource.getUserId();
		
//		List<Integer> cIds = orderContentDao.getCustomerIdByUser(userId);
		
		return adAssetsDao.listAdAssestsMgr(resource, x, y,state,null);
	}
	
	/**
	 * 添加文字消息素材
	 * 
	 */
	public int insertMessageMetaInfo(MessageMeta messageMeta) {
		logger.debug("insertMessageMetaInfo  start");
		int count = 0;
		
		if(messageMeta != null){
			adAssetsDao.insertMessageMetaInfo(messageMeta);
			count = 1;
		}
		return count;
	}
	
	/**
	 * 添加视频素材
	 */
	public int insertVideoMetaInfo(VideoMeta videoMeta) {
		logger.debug("insertVideoMetaInfo  start");
		int count = 0;
		if(videoMeta != null){
			adAssetsDao.insertVideoMetaInfo(videoMeta);
			count = 1;
		}
		
		return count;
	}
	
	/**
	 * 增加图片素材
	 * 
	 */
	public int insertImagMetaInfo(ImageMeta imageMeta) {
		logger.debug("insertVideoMetaInfo  start");
		int count = 0;
		if(imageMeta != null){
			adAssetsDao.insertImagMetaInfo(imageMeta);
			count = 1;
		}
		
		return count;
	}
	
	/**
	 * 得到资源对象
	 */
	
	public ResourceReal getResourceRealById(Integer id){
		logger.debug("----getResourceRealById  start------");
		ResourceReal resourceReal = null;
		
		if(id != null ){
			resourceReal = adAssetsDao.getResourceRealById(id);
		}
		return resourceReal;
	}
	
	
	/**
	 * 得到运行期图片对象(Real 表)
	 */
	public ImageReal getImageMetaRealById(Integer id) {
		
		logger.debug("----getImageMetaRealById  start------");
		ImageReal imageReal = null;
		
		if(id != null){
			imageReal = adAssetsDao.getImageMetaRealById(id);
		}else{
			System.out.println("---imageMeta--id---为空---");
		}
		
		return imageReal;
	}

	/**
	 * 得到运行期视频对象(Real 表)
	 */
	public VideoReal getVideoMetaRealById(Integer id) {
		logger.debug("---getVideoMetaRealById  start----");
		
		VideoReal videoReal= null;
		
		if(id != null){
			videoReal=adAssetsDao.getVideoMetaRealById(id);
		}else{
			System.out.println("---VideoMeta--id---为空---");
		}
		return videoReal;
	}

	/**
	 * 得到运行期文字对象(Real 表)
	 */
	public MessageReal getMessageMetaRealById(Integer id) {
		logger.debug("----getMessageMetaRealById  start-----");
		MessageReal messageReal = null;
		
		if(id != null){
			messageReal = adAssetsDao.getMessageMetaRealById(id);
		}else{
			System.out.println("---messageMeta--id---为空---");
		}
		return messageReal;
	}
	
	/**
	 * 得到资源对象
	 */
	
	public Resource getResourceById(Integer id){
		logger.debug("----getResourceById  start------");
		Resource resource = null;
		
		if(id != null ){
			resource = adAssetsDao.getResourceById(id);
		}
		return resource;
	}
	
	
	/**
	 * 得到图片对象
	 */
	public ImageMeta getImageMetaById(Integer id) {
		
		logger.debug("----getImageMetaById  start------");
		ImageMeta imageMeta = null;
		
		if(id != null){
			imageMeta = adAssetsDao.getImageMetaById(id);
		}else{
			System.out.println("---imageMeta--id---为空---");
		}
		
		return imageMeta;
	}

	/**
	 * 得到视频对象
	 */
	public VideoMeta getVideoMetaById(Integer id) {
		logger.debug("---getVideoMetaById  start----");
		
		VideoMeta videoMeta= null;
		
		if(id != null){
			videoMeta=adAssetsDao.getVideoMetaById(id);
		}else{
			System.out.println("---VideoMeta--id---为空---");
		}
		return videoMeta;
	}

	/**
	 * 得到文字对象
	 */
	public MessageMeta getMessageMetaById(Integer id) {
		logger.debug("getMessageMetaById  start");
		MessageMeta messageMeta = null;
		
		if(id != null){
			messageMeta = adAssetsDao.getMessageMetaById(id);
		}else{
			System.out.println("---messageMeta--id---为空---");
		}
		return messageMeta;
	}

	/**
	 * 修改资源对象
	 */
	public int updateResource(Resource resource) {
		logger.debug("----getMessageMetaById  start-----");
		int count = 0;
		if(resource != null){
			adAssetsDao.updateResource(resource);
			count = 1;
		}
		return count;
	}
	
	/**
	 * 修改图片对象
	 */
	public int updateImageMeta(ImageMeta imageMeta) {
		logger.debug("getMessageMetaById  start");
		int count = 0;
		if(imageMeta != null){
			adAssetsDao.updateImageMeta(imageMeta);
			count = 1;
		}
		return count;
	}
	
	/**
	 * 修改视频对象
	 */
	public int updateVideoMeta(VideoMeta videoMeta) {
		int count = 0;
		if(videoMeta != null){
			adAssetsDao.updateVideoMeta(videoMeta);
			count = 1;
		 }
		return count;
	}
	
	/**
	 * 修改文字对象
	 */
	public int updateMessageMeta(MessageMeta messageMeta) {
		int count = 0;
		
		if(messageMeta != null){
			adAssetsDao.updateMessageMeta(messageMeta);
			count = 1;
		}
		
		return count;
	}
	
	/**
	 * 通过Id 值删除运行期资源对象
	 * 
	 */
	public int deleteResourceRealById(Integer id){
		int count = 0;
		if(id != null){
			ResourceReal resourceReal = adAssetsDao.getResourceRealById(id);
			adAssetsDao.deleteResourceReal(resourceReal);
			
			Resource resource = adAssetsDao.getResourceById(id);
			//将该审核通过的素材在维护表中对应的记录的状态设置为 删除已审核 状态
			//
			resource.setState(ResourceMetasConstant.DOWNLINE);
			adAssetsDao.updateResource(resource);
			count = 1;
		}else{
			System.out.println("---ImageMeta---的id 为空----");
		}
		return count;
	}
	
	/**
	 * 通过 id 值删除运行期图片对象(Real 表)
	 */
	public int deleteImageMetaRealById(Integer id) {
		int count = 0;
		if(id != null){
			
			ImageReal imageReal = adAssetsDao.getImageMetaRealById(id);
			
			String formalFilePath = imageReal.getFormalFilePath();
		//	String formalFilePath = "official/mmmmm/2222/image/2.jpg";
			//执行删除 ftp 上正式存储空间的数据
			try {
				String ip =config.getValueByKey("ftp.ip");
				int port = Integer.valueOf(config.getValueByKey("ftp.port"));
				String username = config.getValueByKey("ftp.username");
				String password = config.getValueByKey("ftp.password");
				ftpUtil.connectionFtp(ip, port, username, password, 2000000);
				
				//ftp上执行删除操作
				boolean b = ftpUtil.deleteFile(formalFilePath);
				
				if(b){
					System.out.println("--删除ftp空间上图片素材成功----");
				}else{
					System.out.println("--删除ftp空间上图片素材不成功----");
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				ftpUtil.closedFtp();
			}
			//删除对应图片运行期表的记录
			adAssetsDao.deleteImageMetaReal(imageReal);
			count = 1;
		}else{
			System.out.println("---ImageMeta---的id 为空----");
		}
		return count;
	}
	
	/**
	 * 通过 id 值删除运行期视频对象(Real 表)
	 */
	public int deleteVideoMetaRealById(Integer id) {
		int count = 0;
		if(id != null){
			VideoReal videoReal = adAssetsDao.getVideoMetaRealById(id);
			
			String formalFilePath = videoReal.getFormalFilePath();
			//执行删除 ftp 上正式存储空间的数据
			try {
				String ip =config.getValueByKey("ftp.ip");
				int port = Integer.valueOf(config.getValueByKey("ftp.port"));
				String username = config.getValueByKey("ftp.username");
				String password = config.getValueByKey("ftp.password");
				ftpUtil.connectionFtp(ip, port, username, password, 2000000);
				
				//ftp上执行删除操作
				boolean b = ftpUtil.deleteFile(formalFilePath);
				
				if(b){
					System.out.println("--删除ftp空间上视频素材成功----");
				}else{
					System.out.println("--删除ftp空间上视频素材不成功----");
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				ftpUtil.closedFtp();
			}
			//删除运行期表中相关记录
			adAssetsDao.deleteVideoMetaReal(videoReal);
			count = 1;
		}else{
			System.out.println("----VideoMeta--id--为空---");
		}
		return count;
	}
	
	/**
	 * 通过 id 值删除运行期文字对象(Real 表)
	 */
	public int deleteMessageMetaRealById(Integer id) {
		int count = 0;
		if(id != null){
			MessageReal messageReal = adAssetsDao.getMessageMetaRealById(id);
			adAssetsDao.deleteMessageMetaReal(messageReal);
			count = 1;
		}else{
			System.out.println("----MessageReal----id 为空------");
		}
		return count;
	}
	
	/**
	 * 通过Id 值删除资源对象
	 * 
	 */
	public int deleteResourceById(Integer id){
		int count = 0;
		if(id != null){
			Resource resource = adAssetsDao.getResourceById(id);
			adAssetsDao.deleteResource(resource);
			count = 1;
		}else{
			System.out.println("---ImageMeta---的id 为空----");
		}
		return count;
	}
	
	/**
	 * 通过 id 值删除图片对象
	 */
	public int deleteImageMetaById(Integer id) {
		int count = 0;
		if(id != null){
			ImageMeta imageMeta = adAssetsDao.getImageMetaById(id);
			
			String temporaryFilePath = imageMeta.getTemporaryFilePath();
			
			try {
				String ip =config.getValueByKey("ftp.ip");
				int port = Integer.valueOf(config.getValueByKey("ftp.port"));
				String username = config.getValueByKey("ftp.username");
				String password = config.getValueByKey("ftp.password");
				ftpUtil.connectionFtp(ip, port, username, password, 2000000);
				
				//ftp上执行删除操作
				boolean b = ftpUtil.deleteFile(temporaryFilePath);
				
				if(b){
					System.out.println("--删除ftp空间上视频素材成功----");
				}else{
					System.out.println("--删除ftp空间上视频素材不成功----");
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				ftpUtil.closedFtp();
			}
			//删除记录
			adAssetsDao.deleteImageMeta(imageMeta);
			count = 1;
		}else{
			System.out.println("---ImageMeta---的id 为空----");
		}
		return count;
	}

	/**
	 * 通过 id 值删除文字对象
	 */
	public int deleteMessageMetaById(Integer id) {
		int count = 0;
		if(id != null){
			MessageMeta messageMeta = adAssetsDao.getMessageMetaById(id);
			
			/*String URL = messageMeta.getURL();
			try {
				String ip =config.getValueByKey("resource.ip");
				int port = Integer.valueOf(config.getValueByKey("resource.port"));
				String username = config.getValueByKey("resource.username");
				String password = config.getValueByKey("resource.password");
				ftpUtil.connectionFtp(ip, port, username, password, 2000000);
				
				//执行删除操作
				boolean b = ftpUtil.deleteFile(URL);
				
				if(b){
					System.out.println("--删除ftp空间上文字素材成功----");
				}else{
					System.out.println("--删除ftp空间上文字素材不成功----");
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				ftpUtil.closedFtp();
			}*/
			//删除记录
			adAssetsDao.deleteMessageMeta(messageMeta);
			count = 1;
		}else{
			System.out.println("----MessageMeta----id 为空------");
		}
		return count;
	}

	/**
	 * 通过 id 值删除视频对象
	 */
	public int deleteVideoMetaById(Integer id) {
		int count = 0;
		if(id != null){
			VideoMeta videoMeta = adAssetsDao.getVideoMetaById(id);
			
			String temporaryFilePath = videoMeta.getTemporaryFilePath();
			
			try {
				String ip =config.getValueByKey("ftp.ip");
				int port = Integer.valueOf(config.getValueByKey("ftp.port"));
				String username = config.getValueByKey("ftp.username");
				String password = config.getValueByKey("ftp.password");
				ftpUtil.connectionFtp(ip, port, username, password, 2000000);
				
				//执行删除操作
				boolean b = ftpUtil.deleteFile(temporaryFilePath);
				
				if(b){
					System.out.println("--删除ftp空间上图片素材成功----");
				}else{
					System.out.println("--删除ftp空间上图片素材不成功----");
				}
			}catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				ftpUtil.closedFtp();
			}
			
			//删除记录
			adAssetsDao.deleteVideoMeta(videoMeta);
			count = 1;
		}else{
			System.out.println("----VideoMeta--id--为空---");
		}
		return count;
	}
	
	
	/**
	 * 资源素材审核通过
	 */
	public int insertAuditResource(Integer id,Integer resourceId,String examinationOpintions){
		logger.debug("-----AuditImageMetas()  start-----");
		int count = 0;
		if(id != null && !"".equals(id)){
			Resource resource = adAssetsDao.getResourceById(id);
			
			ResourceReal resourceReal = new ResourceReal();
			
			resourceReal.setId(resource.getId());
			resourceReal.setResourceName(resource.getResourceName());
			resourceReal.setResourceType(resource.getResourceType());
			resourceReal.setResourceId(resourceId);
			resourceReal.setResourceDesc(resource.getResourceDesc());
			resourceReal.setCustomerId(resource.getCustomerId());
			resourceReal.setCategoryId(resource.getCategoryId());
			resourceReal.setContractId(resource.getContractId());
			resourceReal.setStartTime(resource.getStartTime());
			resourceReal.setEndTime(resource.getEndTime());
			resourceReal.setAdvertPositionId(resource.getAdvertPositionId());
			//设置在审核通过表后的状态为 审核通过 上线
			resourceReal.setState(ResourceMetasConstant.ONLINE);
			resourceReal.setCreateTime(resource.getCreateTime());
			resourceReal.setModifyTime(resource.getModifyTime());
			resourceReal.setOperationId(resource.getOperationId());
			//在元数据表中设置状态为 审核通过
			resource.setState(ResourceMetasConstant.ONLINE);
			resource.setAuditDate(new Date());
	//保存审核人员信息
	//resource.setAuditTaff("");
			//把审核意见也放入资产素材表中。
			resource.setExaminationOpintions(examinationOpintions);
			//保存到信息到 资产审核通过表单中
			adAssetsDao.insertAuditResourceReal(resourceReal);
			//更新元数据表信息
			adAssetsDao.updateResource(resource);
			
			count = 1;
		}
		return count;
	}
	
	
	/**
	 * 图片素材通过审核
	 */
	public int insertAuditImageMetas(Integer id,Integer resourceId,String examinationOpintions,String clientCode,String contractNumber) {
		logger.debug("-----AuditImageMetas()  start-----");
		int count = 0;
		
		//回到FTP文件宿主目录
		String rootFTP = FTPConstant.FTP_HOME_DIRECTORY;
		//确定项目文件夹
		String ftpAdvertDirectory="";
		//存储入库路径
		String ftpOfficialPathToDB= "";
		
		//ftp上 存放素材正式的根目录
		String ftpTempPath="";
		//ftp上 存放素材正式的根目录
		String ftpOfficialPath="";
		//图片的名称
		String imageName = "";
		//图片下载到本地的路径
		String locationPath ="";
		//ftp上完成的图片临时存储路径
		String allTempFilePath ="";
		//ftp上完成的图片正式存储路径
		String allOfficialFilePath ="";
		//ftp上临时存储空间中，客户代码下一层，存放图片的文件夹的名称
		String ftpImageFileName ="";
		//远程ftp存放图片的临时目录
		String ftpRemotionTempFilePath = "";
		//远程ftp存放图片的正式目录
		String ftpRemotionOfficialFilePath = "";
		//图片从ftp上下载下来后，存到本地后的完整路径
		String locationImagePath="";
		
		//得到ftp上存放素材的文件夹名称
		ftpAdvertDirectory = config.getValueByKey("ftp.advertDirectory"); //  /root/advertres/
		
		//远程ftp 临时存储空间地址，跟目录下一层
		ftpTempPath = config.getValueByKey("resource.ftpTempPath");   // temp/
		//远程ftp 正式存储空间地址，跟目录下一层
		ftpOfficialPath = config.getValueByKey("resource.ftpOfficialPath");  //offical
		//下载到本地的路径
		locationPath = config.getValueByKey("resource.locationPath");     // F://
		//ftp上临时存储空间中，客户代码下一层，存放图片的文件夹的名称
		ftpImageFileName = config.getValueByKey("resource.ftpImageFileName");  // image/
		
		if (id != null && !"".equals(id) && resourceId != null && !"".equals(resourceId) ) {
			//得到要审核的图片对象
			ImageMeta imageMeta = adAssetsDao.getImageMetaById(resourceId);
			
			String tempFilePath = imageMeta.getTemporaryFilePath();
			
			String[] strs = tempFilePath.split("/");
			if(strs.length > 0){
				for(int i = 0 ;i<strs.length;i++){
					if(i == strs.length-1){
						imageName = strs[i];
					}
					
				}
				
			}
			
		  //根据图片位置，拼出图片的临时存储目录的完整路径
		  allTempFilePath=ftpAdvertDirectory+ftpTempPath+clientCode+"/"+contractNumber+ftpImageFileName+imageName; 
		  
		  //根据图片位置，拼出图片的正式存储目录的完整路径
		  allOfficialFilePath=rootFTP+ftpAdvertDirectory+ftpOfficialPath+clientCode+"/"+contractNumber+ftpImageFileName+imageName;
		  //存储入库路径
		  ftpOfficialPathToDB=ftpAdvertDirectory+ftpOfficialPath+clientCode+"/"+contractNumber+ftpImageFileName+imageName;
		  
		  //图片的远程ftp临时目录
		  ftpRemotionTempFilePath=ftpAdvertDirectory+ftpTempPath+clientCode+"/"+contractNumber+ftpImageFileName;
		  
		  //图片的远程ftp正式目录
		  ftpRemotionOfficialFilePath =rootFTP+ftpAdvertDirectory+ftpOfficialPath+clientCode+"/"+contractNumber+ftpImageFileName;
		  
		  //图片从ftp上下载下来后，存到本地后的完整路径
		  locationImagePath = locationPath + imageName;
		  
		  //连接ftp，移动相关的素材，由临时空间到正式空间
			try {
				String ip =config.getValueByKey("ftp.ip");
				int port = Integer.valueOf(config.getValueByKey("ftp.port"));
				String username = config.getValueByKey("ftp.username");
				String password = config.getValueByKey("ftp.password");
				ftpUtil.connectionFtp(ip, port, username, password, 2000000);
				//远程文件名 本地文件名 远程FTP目录 本地目录
				ftpUtil.download(imageName, imageName, ftpRemotionTempFilePath,locationPath);
				// 待上传本地文件 远程目录 上传后的文件名称 若没有则取原有文件名称
				ftpUtil.uploadSimpleFile(locationImagePath, ftpRemotionOfficialFilePath,imageName);
				
			}catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				ftpUtil.closedFtp();
			}
			//将审核前的信息添加到审核后的表单
			//图片审核实体类
			ImageReal imageReal = new ImageReal();
			
			imageReal.setId(imageMeta.getId());
			imageReal.setName(imageMeta.getName());
			imageReal.setDescription(imageMeta.getDescription());
			imageReal.setFileSize(imageMeta.getFileSize());
			imageReal.setFileHeigth(imageMeta.getFileHeigth());
			imageReal.setFileWidth(imageMeta.getFileWidth());
			imageReal.setFileFormat(imageMeta.getFileFormat());
			//正式存储空间
			//imageReal.setFormalFilePath(imageMeta.getTemporaryFilePath());
			imageReal.setFormalFilePath(ftpOfficialPathToDB);
			
			//将素材表的记录全部存储到通过审核后的表单里
			adAssetsDao.insertAuditImageReal(imageReal);
			
			Integer imageID = imageReal.getId();
			
			this.insertAuditResource(id, imageID,examinationOpintions);
			
			count = 1;
		}else{
			logger.debug("ImageMeta 的 id 为空");
			count = 0;
		}
		return count;
	}

	/**
	 * 视频素材通过审核
	 */
	public int insertAuditVideoMetas(Integer id,Integer resourceId,String examinationOpintions,String clientCode,String contractNumber) {
		logger.debug("-----insertAuditVideoMetas()  start------");
		int count = 0;
		
		//回到FTP文件宿主目录
		String rootFTP = FTPConstant.FTP_HOME_DIRECTORY;
		
		//确定项目文件夹
		String ftpAdvertDirectory="";
		//存储入库路径
		String ftpOfficialPathToDB= "";
		//ftp上 存放素材正式的根目录
		String ftpTempPath="";
		//ftp上 存放素材正式的根目录
		String ftpOfficialPath="";
		//视频的名称
		String videoName = "";
		//视频下载到本地的路径
		String locationPath ="";
		//ftp上完成的视频临时存储路径
		String allTempFilePath ="";
		//ftp上完成的视频正式存储路径
		String allOfficialFilePath ="";
		//ftp上临时存储空间中，客户代码下一层，存放视频的文件夹的名称
		String ftpVideoFileName ="";
		//远程ftp存放视频的临时目录
		String ftpRemotionTempFilePath = "";
		//远程ftp存放视频的正式目录
		String ftpRemotionOfficialFilePath = "";
		//视频从ftp上下载下来后，存到本地后的完整路径
		String locationVideoPath="";
	
		//得到ftp上存放素材的文件夹名称
		ftpAdvertDirectory = config.getValueByKey("ftp.advertDirectory"); //  advert/
		//远程ftp 临时存储空间地址，跟目录下一层
		ftpTempPath = config.getValueByKey("resource.ftpTempPath");  //temp
		//远程ftp 正式存储空间地址，跟目录下一层
		ftpOfficialPath = config.getValueByKey("resource.ftpOfficialPath"); //offical
		//下载到本地的路径
		locationPath = config.getValueByKey("resource.locationPath");  //f:\\
		//ftp上临时存储空间中，客户代码下一层，存放图片的文件夹的名称
		ftpVideoFileName = config.getValueByKey("resource.ftpVideoFileName"); //video/
		
		if(id !=null && !"".equals(id) && resourceId != null && !"".equals(resourceId)){
			//得到要审核的视频对象
			VideoMeta videoMeta = adAssetsDao.getVideoMetaById(resourceId);
			
            String tempFilePath = videoMeta.getTemporaryFilePath();
			
			String[] strs = tempFilePath.split("/");
			if(strs.length > 0){
				for(int i = 0 ;i<strs.length;i++){
					if(i == strs.length-1){
						videoName = strs[i];
					}
					
				}
				
			}
		 //根据视频位置，拼出视频的临时存储目录的完整路径
		  allTempFilePath =ftpAdvertDirectory.trim()+ftpTempPath.trim()+clientCode.trim()+"/"+contractNumber.trim()+ftpVideoFileName+videoName; 
		  //根据视频位置，拼出视频的正式存储目录的完整路径
		  allOfficialFilePath =rootFTP+ftpAdvertDirectory.trim()+ftpOfficialPath.trim() +clientCode.trim()+"/"+contractNumber+ftpVideoFileName+videoName;
		  //存储入库路径
		  ftpOfficialPathToDB = ftpAdvertDirectory+ftpOfficialPath.trim() +clientCode.trim()+"/"+contractNumber.trim()+ftpVideoFileName.trim()+videoName;
		  //视频的远程ftp临时目录
		  ftpRemotionTempFilePath =ftpAdvertDirectory.trim()+ftpTempPath.trim()+clientCode.trim()+"/"+contractNumber.trim()+ftpVideoFileName+"/";
		  //视频的远程ftp正式目录
		  ftpRemotionOfficialFilePath =rootFTP+ftpAdvertDirectory.trim()+ftpOfficialPath.trim()+clientCode.trim()+"/"+contractNumber.trim()+ftpVideoFileName;
		  //视频从ftp上下载下来后，存到本地后的完整路径
		  locationVideoPath = locationPath+videoName;	
			
		//连接ftp，移动相关的素材，由临时空间到正式空间
		try {
			String ip =config.getValueByKey("ftp.ip");
			int port = Integer.valueOf(config.getValueByKey("ftp.port"));
			String username = config.getValueByKey("ftp.username");
			String password = config.getValueByKey("ftp.password");
			ftpUtil.connectionFtp(ip, port, username, password, 2000000);
			//远程文件名 本地文件名 远程FTP目录 本地目录
			ftpUtil.download(videoName, videoName, ftpRemotionTempFilePath,locationPath);
			// 待上传本地文件 远程目录 上传后的文件名称 若没有则取原有文件名称
			ftpUtil.uploadSimpleFile(locationVideoPath, ftpRemotionOfficialFilePath,videoName);
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			ftpUtil.closedFtp();
		}
			
			//视频审核实体类
			VideoReal videoReal = new VideoReal();
			
			videoReal.setId(videoMeta.getId());
			videoReal.setName(videoMeta.getName());
			videoReal.setDescription(videoMeta.getDescription());
			videoReal.setRunTime(videoMeta.getRunTime());
			//正式存储路径
			videoReal.setFormalFilePath(ftpOfficialPathToDB);
			//审核通过后，将信息保存到 T_VIDEO_META_REAL 表中
			adAssetsDao.insertAuditVideoReal(videoReal);
			
			Integer videoId = videoReal.getId();
			
			this.insertAuditResource(id, videoId,examinationOpintions);
			count = 1;
		}else{
			logger.debug("视频素材的id 为 空");
			count = 0;
		}
		return count;
	}

	/**
	 * 文字素材通过审核
	 */
	public int insertAuditMessageMetas(Integer id,Integer resourceId,String examinationOpintions,String clientCode,String contractNumber) {
		logger.debug("-----insertAuditMessageMetas  start-------");
		int count = 0;
		if (id != null && !"".equals(id) && resourceId != null && !"".equals(resourceId)) {
			//得到要审核的文字消息对象
			MessageMeta messageMeta = adAssetsDao.getMessageMetaById(resourceId);
		
			//文字审核实体类
			MessageReal messageReal = new MessageReal();
			
			messageReal.setId(messageMeta.getId());
			messageReal.setName(messageMeta.getName());
			messageReal.setContent(messageMeta.getContent());
			messageReal.setURL(messageMeta.getURL());
			
			//审核通过后，将信息保存到 T_MESSAGE_META_REAL 表中
			adAssetsDao.insertAuditMessageReal(messageReal);

			Integer messageId = messageReal.getId();
			
			this.insertAuditResource(id,messageId,examinationOpintions);
			count = 1;
		} else {
			logger.debug("文字素材的id 为 空");
			count = 0;
		}
		
		return count;
	}
	
	/**
	 * 图片素材没有通过审核
	 */
	public int updateNoAuditImageMetas(Integer id,String examinationOpintions) {
		logger.debug("---noAuditImageMetas()  start-----");
		int count = 0;
		if(id !=null && !"".equals(id)){
			Resource resource = adAssetsDao.getResourceById(id);
			//设置审核状态 为 未通过
			resource.setState(ResourceMetasConstant.AUDIT_NO_PASS);
			resource.setExaminationOpintions(examinationOpintions);
			adAssetsDao.updateResource(resource);
			count = 1;
		}else{
			logger.debug("图片素材的id 为 空");
			count = 0;
		}
		
		return count;
	}

	/**
	 * 视频素材没有通过审核
	 */
	public int updateNoAuditVideoMetas(Integer id,String examinationOpintions) {
		logger.debug("----noAuditMessageMetas()  start-----");
		int count = 0;
		if (id != null && !"".equals(id)) {
			
			Resource resource = adAssetsDao.getResourceById(id);
			//设置审核状态 为 未通过
			resource.setState(ResourceMetasConstant.AUDIT_NO_PASS);
			resource.setExaminationOpintions(examinationOpintions);
			adAssetsDao.updateResource(resource);
			count = 1;
		} else {
			logger.debug("视频素材的id 为 空");
			count = 0;
		}
		return count;
	}
	
	/**
	 * 文字消息素材没有通过审核
	 */
	public int updateNoAuditMessageMetas(Integer id,String examinationOpintions) {
		logger.debug("---noAuditMessageMetas()  start----");
		int count = 0;

		if (id != null && !"".equals(id)) {
			Resource resource = adAssetsDao.getResourceById(id);
			//设置审核状态 为 未通过
			resource.setState(ResourceMetasConstant.AUDIT_NO_PASS);
			resource.setExaminationOpintions(examinationOpintions);
			adAssetsDao.updateResource(resource);
			count = 1;
		} else {
			logger.debug("文字消息素材的id 为 空");
			count = 0;
		}
		return count;
	}
	
	//资源
	public int getAdContentCount(Resource resource,String state){
		System.out.println("---------getAdContentCount() start--------");
		
		Integer userId = resource.getUserId();
		
//		List<Integer> cIds = orderContentDao.getCustomerIdByUser(userId);
		
		return adAssetsDao.getAdContentCount(resource,state,null);
	}
	//运营商记录数目
	public int getAdContentCountReal(ResourceReal resourceReal,String state){
		System.out.println("---------getAdContentCount() start--------");
		return adAssetsDao.getAdContentCountReal(resourceReal,state);
	}
	
	public AdAssetsDao getAdAssetsDao() {
		return adAssetsDao;
	}

	public void setAdAssetsDao(AdAssetsDao adAssetsDao) {
		this.adAssetsDao = adAssetsDao;
	}

	@Override
	public AdvertPositionType getAdvertPositionTypeById(Integer id) {
		return adAssetsDao.getAdvertPositionTypeById(id);
	}

	@Override
	public Resource getResourceByAdvertPositionId(Integer id) {
		return adAssetsDao.getResourceByAdvertPositionId(id);
	}

	@Override
	public ResourceReal getResourceRealByAdvertPositionId(Integer id) {
		return adAssetsDao.getResourceRealByAdvertPositionId(id);
	}

	@Override
	public int upResourceMetra(Integer id,Integer resourceId,Integer resourceType,String upOpintions) {
		int count = 0;
		if(id != null && resourceId != null && upOpintions != null){
			//图片
			//if(resourceType == ResourceMetasConstant.IMAGE_TYPE_IN){
				//	if(resourceType == Integer.parseInt(ResourceMetasConstant.IMAGE_TYPE_STR)){
			    //得到要审核的图片对象
				
			    if(resourceType.equals(ResourceMetasConstant.IMAGE_TYPE_IN)){
			
				ImageMeta imageMeta = adAssetsDao.getImageMetaById(resourceId);
				ImageReal imageReal = new ImageReal();
				imageReal.setId(imageMeta.getId());
				imageReal.setName(imageMeta.getName());
				imageReal.setDescription(imageMeta.getDescription());
				imageReal.setFileSize(imageMeta.getFileSize());
				imageReal.setFileHeigth(imageMeta.getFileHeigth());
				imageReal.setFileWidth(imageMeta.getFileWidth());
				imageReal.setFileFormat(imageMeta.getFileFormat());
				//正式存储空间
				imageReal.setFormalFilePath(imageMeta.getFileFormat());
				//将上线的记录全部存储到运行期表单里
				adAssetsDao.insertAuditImageReal(imageReal);
				Integer imageID = imageReal.getId();
				this.insertAuditResource(id, imageID,upOpintions);
				count = 1;
			//视频	
			}else if(resourceType.equals(Integer.parseInt(ResourceMetasConstant.VIDEO_TYPE_STR))){
				//得到要审核的视频对象
				VideoMeta videoMeta = adAssetsDao.getVideoMetaById(resourceId);
				//视频审核实体类
				VideoReal videoReal = new VideoReal();
				videoReal.setId(videoMeta.getId());
				videoReal.setName(videoMeta.getName());
				videoReal.setDescription(videoMeta.getDescription());
				videoReal.setRunTime(videoMeta.getRunTime());
				//正式存储路径
				videoReal.setFormalFilePath(videoMeta.getTemporaryFilePath());
				//审核通过后，将信息保存到 T_VIDEO_META_REAL 表中
				adAssetsDao.insertAuditVideoReal(videoReal);
				Integer videoId = videoReal.getId();
				this.insertAuditResource(id, videoId,upOpintions);
				count = 1;
			//文字	
			}else if(resourceType.equals(Integer.parseInt(ResourceMetasConstant.MESSAGE_TYPE_STR))){
				MessageMeta messageMeta = adAssetsDao.getMessageMetaById(resourceId);
				//文字审核实体类
				MessageReal messageReal = new MessageReal();
				messageReal.setId(messageMeta.getId());
				messageReal.setName(messageMeta.getName());
				messageReal.setContent(messageMeta.getContent());
				messageReal.setURL(messageMeta.getURL());
				//审核通过后，将信息保存到 T_MESSAGE_META_REAL 表中
				adAssetsDao.insertAuditMessageReal(messageReal);
				Integer messageId = messageReal.getId();
				this.insertAuditResource(id,messageId,upOpintions);
				count = 1;
			}
		}
		return count;
	}

	@Override
	public int deleteResourceAbatch(Integer id, Integer resourceType) {
		int count = 0;
		
		Resource resource = new Resource();
		resource = this.getResourceById(id);
		Integer resourceId = resource.getResourceId();
		if(resourceType == 0){
			this.deleteResourceById(id);
			this.deleteImageMetaById(resourceId);
			count= 1;
		}else if(resourceType == 1){
			this.deleteResourceById(id);
			this.deleteVideoMetaById(resourceId);
			count= 1;
		}else if(resourceType ==2){
			this.deleteResourceById(id);
			this.deleteMessageMetaById(resourceId);
			count= 1;
		}else{
			System.out.println("对不起，没有找到要删除的素材类型，请确认！");
		}
		return count;
	}
	
}
