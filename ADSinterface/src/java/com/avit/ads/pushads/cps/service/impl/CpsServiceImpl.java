package com.avit.ads.pushads.cps.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.cps.service.CpsService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Cps;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.jms.JmsMessageService;
import com.avit.common.warn.WarnHelper;
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
	public String sendFtpFile(String sourceFile,String targetPath)
	{
		String ret=ConstantsHelper.FAIL;

		String sourcePath = InitConfig.getAdsResourcePath();
		String cpsTempPath = InitConfig.getAdsConfig().getCpsAds().getAdsTempPath();
		//
		String cpsTargetPath = InitConfig.getAdsConfig().getCpsAds().getAdsImageTargetPath();
		
		sourcePath = sourcePath + sourceFile.substring(0,sourceFile.lastIndexOf("/"));
		sourceFile = sourceFile.substring(sourceFile.lastIndexOf("/")+1);
		//从广告接口服务器下载到本地 
		try {
			ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(),Integer.parseInt(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(),InitConfig.getAdsConfig().getAdResource().getPwd());
		} catch (Exception e) {
			warnHelper.writeWarnMsgToDb("广告素材ftp服务器连接失败   IP:" + InitConfig.getAdsConfig().getAdResource().getIp());
			log.error("广告素材ftp服务器连接失败", e);
			return ret;		
		}
		if (ftpService.download(sourceFile, sourceFile, sourcePath, cpsTempPath))
		{		
			//copy本地文件到双向资源服务上
			List<Cps> cpsList  =InitConfig.getCpsConfig();
			for (int i=0;i<cpsList.size();i++)
			{
				try {
					ftpService.setServer(cpsList.get(i).getIp(),Integer.parseInt(cpsList.get(i).getPort()), cpsList.get(i).getUser(),cpsList.get(i).getPwd());
				} catch (Exception e) {
					warnHelper.writeWarnMsgToDb("双向资源ftp服务器连接失败   IP:" + cpsList.get(i).getIp());
					log.error("双向资源ftp服务器连接失败", e);
					return ret;		
				}
				if (ftpService.sendAFile2ResourceServer(cpsTempPath+"/"+sourceFile, cpsTargetPath))
				{
					ret=ConstantsHelper.SUCCESS;
				}
			}
		}
		return ret;				
			
	}
	/**
	 * 发送本地文件到OCG系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	//此接口暂时未用
	public String sendLocalFile(String sourceFile,String targetPath)
	{
		String ret=ConstantsHelper.FAIL;
		try
		{
			ftpService.setServer(InitConfig.getCpsConfig().get(0).getIp(),Integer.parseInt(InitConfig.getCpsConfig().get(0).getPort()), InitConfig.getCpsConfig().get(0).getUser(),InitConfig.getCpsConfig().get(0).getPwd());
			//copy本地文件到资源服务上
			if (ftpService.sendAFile2ResourceServer(sourceFile, targetPath))	
			{
				ret=ConstantsHelper.SUCCESS;
			}			
		}
		catch (Exception e)
		{
			ret=ConstantsHelper.FAIL;
			log.error(e);
		}	
		return ret;
	}
	/**
	 * 发送文件到CPS系统
	 * @param 源目录
	 * @param 目标目录
	 */
	//此接口暂时未用
	public String sendPath(String sourcePath,String targetPath)
	{
		String ret=ConstantsHelper.FAIL;
		try
		{
			ftpService.setServer(InitConfig.getCpsConfig().get(0).getIp(),Integer.parseInt(InitConfig.getCpsConfig().get(0).getPort()), InitConfig.getCpsConfig().get(0).getUser(),InitConfig.getCpsConfig().get(0).getPwd());
			//copy本地文件到资源服务上
			if (ftpService.sendAFilePath2ResourceServer(sourcePath, targetPath))
			{
				ret=ConstantsHelper.SUCCESS;
			}			
		}
		catch (Exception e)
		{
			ret=ConstantsHelper.FAIL;
			log.error(e);
		}						
		return ret;
	}
	
	
	
	/**
	 * 启动CPS发送
	 * 
	 */
	public String startCps(){
		String ret=ConstantsHelper.SUCCESS;		
		//调用CPS消息通知接口 TODO
		//拉起Ｒｓｙｎｃ
		jmsMessageService.synResources();
		return ret;
	}
}
