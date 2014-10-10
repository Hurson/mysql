package com.avit.ads.webservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.cps.service.CpsService;
import com.avit.ads.pushads.dtv.service.DtvService;
import com.avit.ads.pushads.video.service.VideoPumpService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.HttpServer;
import com.avit.ads.util.bean.Ocg;
import com.avit.ads.util.bean.VideoPump;
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
	public String deleteFile(String fileName,String adsTypeCode) {
		//投放服务器本地
		String ret="";
		if (adsTypeCode.equals(ConstantsHelper.FILE_UPLOAD_TYPE_LOCAL))
		{
			//删除本地文件
			delFile(InitConfig.getAdsResourcePath()+File.separator+ fileName);
			ret = "1";
		}//HTTP Server
		if (adsTypeCode.equals(ConstantsHelper.FILE_UPLOAD_TYPE_HTTP))
		{
			HttpServer http = InitConfig.getHttpServer();
			try
			{
				ftpService.setServer(http.getIp(),Integer.parseInt(http.getPort()), http.getUser(),http.getPwd());
			}
			catch (Exception e)
			{
				
			}
			ftpService.deleteFile(fileName, InitConfig.getHttpServer().getTargetPath());
			ret = "1";
		}
		
		return ret;
	}
	public String sendFile(ResourceFile resourceFile)
	{
		System.out.print(resourceFile.getFileName());
		
		DataHandler handler = resourceFile.getFile();
		String ret="";
		//投放服务器本地
		if (InitConfig.getServerType(resourceFile.getAdsTypeCode(),resourceFile.getFileName().substring(resourceFile.getFileName().lastIndexOf(".")+1)).equals(ConstantsHelper.FILE_UPLOAD_TYPE_LOCAL))
			{
			try {
	
			InputStream is = handler.getInputStream();
	
			OutputStream os = new FileOutputStream(new File(InitConfig.getAdsResourcePath()+File.separator+ resourceFile.getFileName()));
	
			byte[] b = new byte[100000];
	
			int bytesRead = 0;
	
			while ((bytesRead = is.read(b)) != -1) {
	
			os.write(b, 0, bytesRead);
	
			}
			
			os.flush();
	
			os.close();
	
			is.close();
			ret = resourceFile.getFileName();
			} catch (IOException e) {
	
			e.printStackTrace();
	
			}
		}
		//HTTP Server
		if (InitConfig.getServerType(resourceFile.getAdsTypeCode(),resourceFile.getFileName().substring(resourceFile.getFileName().lastIndexOf(".")+1)).equals(ConstantsHelper.FILE_UPLOAD_TYPE_HTTP))
		{
			HttpServer http = InitConfig.getHttpServer();
			try
			{
				ftpService.setServer(http.getIp(),Integer.parseInt(http.getPort()), http.getUser(),http.getPwd());
			}
			catch (Exception e)
			{
				
			}
			try
			{
				InputStream is = handler.getInputStream();
				ftpService.sendAFile2ResourceServer(is, resourceFile.getFileName(),InitConfig.getHttpServer().getTargetPath());
				ret = resourceFile.getFileName();
			}
			catch (IOException e) {				
				e.printStackTrace();		
			}
		}
		
		return ret;
	}
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
		  System.out.println("删除文件操作 成功执行");
		  }
		  catch (Exception e)
		  {
		  System.out.println("删除文件操作出错");
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
		log.info("enter sendFileDTV" + sourcefile);
		ret = dtvService.sendFile(areaCode, sourcefile, targetpath);
		
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
		log.info("enter sendFileVideoPump"+ sourcefile);
		ret = videoService.sendFile(sourcefile, targetpath);
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
		ret = cpsService.sendFtpFile(sourcefile, targetpath);
		log.info("out sendFtpFileToCps");
		
		return ret;
	}
}
