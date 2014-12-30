package com.avit.ads.pushads.cps.service.impl;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.cps.service.CpsService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Cps;
import com.avit.ads.util.warn.WarnHelper;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.jms.JmsMessageService;

import java.io.IOException;
import java.util.List;
@Service("CpsService")
public class CpsServiceImpl implements CpsService{
	private Log log = LogFactory.getLog(this.getClass());
	@Inject
	private FtpService ftpService;
	@Inject
	private JmsMessageService jmsMessageService;
	
	@Autowired
	private WarnHelper warnHelper;
	
	/**
	 * 发送FTP文件到OCG系统.
	 *
	 * @param sourceFile the source file
	 * @param targetPath the target path
	 */
	public void sendFtpFile(String sourceFile,String targetPath)
	{
		try
		{
			String sourcePath = InitConfig.getAdsResourcePath();
			String cpsTempPath = InitConfig.getAdsConfig().getCpsAds().getAdsTempPath();
			//
			String cpsTargetPath = InitConfig.getAdsConfig().getCpsAds().getAdsImageTargetPath();
			
			sourcePath = sourcePath + sourceFile.substring(0,sourceFile.lastIndexOf("/"));
			sourceFile = sourceFile.substring(sourceFile.lastIndexOf("/")+1);
			//从广告接口服务器下载到本地 
			ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(),Integer.parseInt(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(),InitConfig.getAdsConfig().getAdResource().getPwd());
			ftpService.download(sourceFile, sourceFile, sourcePath, cpsTempPath);
			//copy本地文件到双向资源服务上
			List<Cps> cpsList  =InitConfig.getCpsConfig();
			for (int i=0;i<cpsList.size();i++)
			{
			ftpService.setServer(cpsList.get(i).getIp(),Integer.parseInt(cpsList.get(i).getPort()), cpsList.get(i).getUser(),cpsList.get(i).getPwd());
			ftpService.sendAFile2ResourceServer(cpsTempPath+"/"+sourceFile, cpsTargetPath);	
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
						
			
	}
	/**
	 * 发送本地文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public boolean sendLocalFile(String sourceFile,String targetPath)
	{

		List<Cps> cpsList  =InitConfig.getCpsConfig();
		for (int i=0;i<cpsList.size();i++)
		{
			try {
				ftpService.setServer(cpsList.get(i).getIp(),Integer.parseInt(cpsList.get(i).getPort()), cpsList.get(i).getUser(),cpsList.get(i).getPwd());
			} catch (Exception e) {
				log.error("发送文件到ftp失败", e);
				warnHelper.writeWarnMsgToDb("双向广告FTP服务器无法连接   ip:" + cpsList.get(i).getIp());
				return false;
			} 
			//copy本地文件到资源服务上
			ftpService.sendAFile2ResourceServer(sourceFile, targetPath);	
		}
		return true;
	}
	/**
	 * 发送文件到CPS系统
	 * @param 源目录
	 * @param 目标目录
	 */
	public void sendPath(String sourcePath,String targetPath)
	{
		try
		{
			List<Cps> cpsList  =InitConfig.getCpsConfig();
			for (int i=0;i<cpsList.size();i++)
			{
			ftpService.setServer(cpsList.get(i).getIp(),Integer.parseInt(cpsList.get(i).getPort()), cpsList.get(i).getUser(),cpsList.get(i).getPwd());
			ftpService.sendAFilePath2ResourceServer(sourcePath, targetPath);
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
						
		//copy本地文件到资源服务上
		
	}
	
	
	
	/**
	 * 启动CPS发送
	 * 
	 */
	public void startCps(){
		
		//调用CPS消息通知接口 TODO
		//拉起Ｒｓｙｎｃ
		jmsMessageService.synResources();
	}
}
