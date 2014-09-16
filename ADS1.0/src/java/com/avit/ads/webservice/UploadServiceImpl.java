package com.avit.ads.webservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.cps.service.CpsService;
import com.avit.ads.pushads.dtv.service.DtvService;
import com.avit.ads.pushads.ocg.service.OcgService;
import com.avit.ads.pushads.ui.service.UiService;
import com.avit.ads.pushads.video.service.VideoPumpService;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Ocg;
import com.avit.common.ftp.service.FtpService;


@Service
@WebService(endpointInterface = "com.avit.ads.webservice.UploadService", serviceName = "UploadService")
public class UploadServiceImpl implements UploadService {
	private Log log = LogFactory.getLog(this.getClass());
	@Resource(name="FtpService") 
	FtpService ftpService;
	@Resource(name="DtvService") 
	DtvService dtvService;
	@Resource(name="VideoPumpService") 
	VideoPumpService videoService;
	@Resource(name="CpsService") 
	CpsService cpsService;
	@Resource(name="OcgService") 
	OcgService ocgService;
	@Resource(name="UiService") 
	UiService uiService;
//	public String deleteFile(String fileName,String adsTypeCode) {
//		//投放服务器本地
//		String ret="";
//		if (adsTypeCode.equals(ConstantsHelper.FILE_UPLOAD_TYPE_LOCAL))
//		{
//			//删除本地文件
//			delFile(InitConfig.getAdsResourcePath()+File.separator+ fileName);
//			ret = "1";
//		}//HTTP Server
//		if (adsTypeCode.equals(ConstantsHelper.FILE_UPLOAD_TYPE_HTTP))
//		{
//			HttpServer http = InitConfig.getHttpServer();
//			try
//			{
//				ftpService.setServer(http.getIp(),Integer.parseInt(http.getPort()), http.getUser(),http.getPwd());
//			}
//			catch (Exception e)
//			{
//				
//			}
//			ftpService.deleteFile(fileName, InitConfig.getHttpServer().getTargetPath());
//			ret = "1";
//		}
//		//VideoPump
//		if (adsTypeCode.equals(ConstantsHelper.FILE_UPLOAD_TYPE_VIDEO))
//		{
//			VideoPump video = InitConfig.getVideoPumpConfig();
//			try
//			{
//				ftpService.setServer(video.getIp(),Integer.parseInt(video.getPort()), video.getUser(),video.getPwd());
//			}
//			catch (Exception e)
//			{
//				
//			}
//			ftpService.deleteFile(fileName, InitConfig.getVideoPumpConfig().getTargetPath());
//			
//			ret = "1";
//		}
//		return ret;
//	}
//	public String sendFile(ResourceFile resourceFile)
//	{
//		log.info(resourceFile.getFileName());
//		
//		DataHandler handler = resourceFile.getFile();
//		String ret="";
//		//投放服务器本地
//		if (InitConfig.getServerType(resourceFile.getAdsTypeCode(),resourceFile.getFileName().substring(resourceFile.getFileName().lastIndexOf(".")+1)).equals(ConstantsHelper.FILE_UPLOAD_TYPE_LOCAL))
//			{
//			try {
//	
//			InputStream is = handler.getInputStream();
//	
//			OutputStream os = new FileOutputStream(new File(InitConfig.getAdsResourcePath()+File.separator+ resourceFile.getFileName()));
//	
//			byte[] b = new byte[100000];
//	
//			int bytesRead = 0;
//	
//			while ((bytesRead = is.read(b)) != -1) {
//	
//			os.write(b, 0, bytesRead);
//	
//			}
//			
//			os.flush();
//	
//			os.close();
//	
//			is.close();
//			ret = resourceFile.getFileName();
//			} catch (IOException e) {
//	
//			e.printStackTrace();
//	
//			}
//		}
//		//HTTP Server
//		if (InitConfig.getServerType(resourceFile.getAdsTypeCode(),resourceFile.getFileName().substring(resourceFile.getFileName().lastIndexOf(".")+1)).equals(ConstantsHelper.FILE_UPLOAD_TYPE_HTTP))
//		{
//			HttpServer http = InitConfig.getHttpServer();
//			try
//			{
//				ftpService.setServer(http.getIp(),Integer.parseInt(http.getPort()), http.getUser(),http.getPwd());
//			}
//			catch (Exception e)
//			{
//				
//			}
//			try
//			{
//				InputStream is = handler.getInputStream();
//				ftpService.sendAFile2ResourceServer(is, resourceFile.getFileName(),InitConfig.getHttpServer().getTargetPath());
//				ret = resourceFile.getFileName();
//			}
//			catch (IOException e) {				
//				e.printStackTrace();		
//			}
//		}
//		//VideoPump
//		if (InitConfig.getServerType(resourceFile.getAdsTypeCode(),resourceFile.getFileName().substring(resourceFile.getFileName().lastIndexOf(".")+1)).equals(ConstantsHelper.FILE_UPLOAD_TYPE_VIDEO))
//		{
//			VideoPump video = InitConfig.getVideoPumpConfig();
//			try
//			{
//				ftpService.setServer(video.getIp(),Integer.parseInt(video.getPort()), video.getUser(),video.getPwd());
//			}
//			catch (Exception e)
//			{
//				
//			}
//			try
//			{
//				InputStream is = handler.getInputStream();
//				ftpService.sendAFile2ResourceServer(is, resourceFile.getFileName(),InitConfig.getVideoPumpConfig().getTargetPath());
//				ret = resourceFile.getFileName();
//			}
//			catch (IOException e) {				
//				e.printStackTrace();		
//			}
//		}
//		return ret;
//	}
	/**
	 * 获取广告位投放提前量  秒.
	 *
	 * @param adsTypeCode 广告位编码 
	 * @return  
	 */
	public String getPreSecond(String adsTypeCode)
	{
		return String.valueOf(InitConfig.getAdsConfig().getPreSecond());
		
	}
	public void delFile(String filePathAndName)
	  {
		  try
		  {
		  String filePath = filePathAndName;
		  filePath = filePath.toString();
		  File myDelFile = new File(filePath);
		  myDelFile.delete();
		  log.info("删除文件操作 成功执行");
		  }
		  catch (Exception e)
		  {
			  log.error("删除文件操作出错"+e);
		  e.printStackTrace();
		  }
	  }
	/**
	 * DTV 订单审核时调用.
	 *
	 * @param fileName 带完整路径的文件名
	 * @param adsTypeCode 广告位编码   上传类型 1代表上传到投放子系统，2代表上传到HttpServer,3代表上传到VideoPump
	 * @return 1 成功  0失败 
	 */
	public String sendFileDTV(String areaCode,String sourcefile,String targetpath)
	{
		String ret="1";
		log.info("enter sendFileDTV"+sourcefile);
		dtvService.sendFile(areaCode, sourcefile, targetpath);
		log.info("out sendFileDTV");
		
		return ret;
	}
	/**
	 * 非开机，DTV视频素材审核时调用.
	 *
	 * @param fileName 带完整路径的文件名
	 * @param adsTypeCode 广告位编码   上传类型 1代表上传到投放子系统，2代表上传到HttpServer,3代表上传到VideoPump
	 * @return 1 成功  0失败 
	 */
	public String sendFileVideoPump(String sourcefile,String targetpath)
	{
		String ret="1";
		log.info("enter sendFileVideoPump" + sourcefile);
		videoService.sendFile(sourcefile, targetpath);
		log.info("out sendFileVideoPump");
		return ret;
	}
	/**
	 * 双向图片素材审核时调用.
	 *
	 * @param sourcefile 原文件
	 * @param targetpath 目标目录
	 * @return 1 成功  0失败 
	 */
	public String sendFtpFileToCps(String sourcefile,String targetpath)
	{
		String ret="1";
		log.info("enter sendFtpFileToCps" + sourcefile);
		
		cpsService.sendFtpFile(sourcefile, targetpath);
		log.info("out sendFtpFileToCps");
		
		return ret;
	}
	
	/**
	 * 更新UI中的dataDefine-a.dat和 htmlData-a.dat描述符
	 * @param areaCode
	 * @param dataDefine（FTP对应全路径）
	 * @param htmlData（FTP对应全路径）  
	 * @return
	 */
	public String sendUI(String areaCode, String dataDefine, String htmlData){
		List<String> areaList = null;
		String ret = "";
		log.info("更新描述符请求信息：areaCode="+areaCode+",dataDefine="+dataDefine+",htmlData="+htmlData);
		try{
			if(StringUtils.isEmpty(areaCode) || "0".equals(areaCode) || "152000000000".equals(areaCode)){
				//获取所有区域编码列表
				areaList = getAllAreaCode();
			}else{
				areaList = new ArrayList<String>();
				areaList.add(areaCode);
			}
			for(String area : areaList){
				ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(), Integer.valueOf(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(), InitConfig.getAdsConfig().getAdResource().getPwd());
				String bodyContent = "1:1";
				if(StringUtils.isNotBlank(dataDefine)){
					ftpService.download(dataDefine, "dataDefine-c.dat", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath());
					ocgService.sendFile(area, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/dataDefine-c.dat", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
					bodyContent += ";2:dataDefine-c.dat";
				}
				if(StringUtils.isNotBlank(htmlData)){
					ftpService.download(htmlData, "htmldata-c.dat", InitConfig.getAdsResourcePath(), InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath());
					ocgService.sendFile(area, InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/htmldata-c.dat", InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath());
					bodyContent += ";4:htmldata-c.dat";
				}
				//往区域发送NID描述符插入信息
				ret = uiService.sendUiDesc(bodyContent, area);
				if("0".equals(ret)){
					log.info("往"+area+"区域NID描述符插入信息成功！");
					
					// >>>>>>>>>>>>>>> begin   modified by liuwenping
					boolean isFileValidated = true;
					if(StringUtils.isNotBlank(dataDefine)){
						String sourceFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/dataDefine-c.dat";
						String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
						if(!validateBeforePlay(area, sourceFilePath, serverPath)){
							isFileValidated = false;
						}
					}
					if(StringUtils.isNotBlank(htmlData)){
						String sourceFilePath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTempPath()+"/htmldata-c.dat";
						String serverPath = InitConfig.getAdsConfig().getUnRealTimeAds().getAdsTargetPath();
						if(!validateBeforePlay(area, sourceFilePath, serverPath)){
							isFileValidated = false;
						}
					}
					if(isFileValidated){
						ocgService.startPlayPgm(area, InitConfig.getConfigProperty(ConstantsAdsCode.UIPGM),InitConfig.getConfigProperty(ConstantsAdsCode.OCGOUTPUT));
					}
					// <<<<<<<<<<<<<<<<<< end   modified by liuwenping	
				}else{
					log.error("往"+area+"区域NID描述符插入信息失败！return="+ret);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			ret = "3";
		}
		return ret;
	}
	
   /**
    * 获取所有区域编码列表
    * @return
    */
   private List<String> getAllAreaCode(){
	   List<String> areaList = new ArrayList<String>();
	   List<Ocg> ocgList = InitConfig.getAdsConfig().getOcgList();
	   for(Ocg ocg : ocgList){
		   if(!areaList.contains(ocg.getAreaCode()) && !"0".equals(ocg.getAreaCode())){
			   areaList.add(ocg.getAreaCode());
		   }
	   }
	   return areaList;
   }
   
	/*
	 * add by liuwenping
	 * 
	 * 调用ocg的startPlay方法之前，先将文件下下来，看文件大小是否为0（或者是否与原始文件大小相符，本方法采用此种校验方式）。
	 * 如果为0（与原始文件大小不符），则校验不通过，不能播放。
	 */
	private boolean validateBeforePlay(String areaCode, String sourceFilePath, String serverPath ){
		File sourceFile = new File(sourceFilePath);
		if(!sourceFile.exists()){
			return false;
		}
		String downloadDirPath = System.getProperty("user.dir") + File.separator + "tempDir";
		File downloadDir = new File(downloadDirPath);
		if(!downloadDir.exists()){
			downloadDir.mkdir();
		}
		String fileName = sourceFile.getName();
		String downloadFilePath = downloadDirPath + File.separator + fileName;  //本地暂存文件路径
		String serverFilePath = serverPath + File.separator + fileName;        //服务器文件路径
		
		ocgService.downloadFile(areaCode, downloadFilePath, serverFilePath);	//从OCG下载文件	
		
		File downloadFile = new File(downloadFilePath);
		if(!downloadFile.exists()){
			return false;
		}
		if(downloadFile.length() != sourceFile.length()){
			downloadFile.delete(); //删除从OCG下下来的文件	
			return false;
		}
		downloadFile.delete(); //删除从OCG下下来的文件	
		return true;
			
	}
   
}
