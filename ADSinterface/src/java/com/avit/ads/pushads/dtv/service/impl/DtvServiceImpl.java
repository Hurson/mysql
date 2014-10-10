package com.avit.ads.pushads.dtv.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.dtv.service.DtvService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Dtv;
import com.avit.common.ftp.service.FtpService;
@Service("DtvService")
public class DtvServiceImpl implements DtvService{
	private Log log = LogFactory.getLog(this.getClass());
	@Inject
	
	private FtpService ftpService;
/**
	 * 发送文件到DTV系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public String sendFile(String areaCode,String sourceFile,String targetPath)
	{
		String ret=ConstantsHelper.FAIL;		
		//DTV临时目录均以 区域0为准
		String localDirectory =InitConfig.getDtvConfig("0").getTempPath();
		String localFileName;			
		String fullfilename = InitConfig.getAdsConfig().getAdResource().getAdsResourcePath()+"/"+sourceFile;
		String remoteFileName =fullfilename.substring(fullfilename.lastIndexOf("/")+1);
		String remoteDirectory =fullfilename.substring(0,fullfilename.lastIndexOf("/")+1);
		localFileName =remoteFileName;
		try
		{
			//初始化接口服务器FTP
			ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(),Integer.parseInt(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(),InitConfig.getAdsConfig().getAdResource().getPwd());
			//copy文件至本地文件
			
			if (ftpService.download(remoteFileName, localFileName, remoteDirectory, localDirectory))
			{
				ret=ConstantsHelper.SUCCESS;
			}
			else
			{
				log.error("remoteFileName"+remoteFileName+"-localFileName:"+localFileName+"-remoteDirectory:" +remoteDirectory+"-localDirectory:"+localDirectory);
				return ret;
				
			}
			
			//ftpService.sendAFile2ResourceServer(sourceFile, targetFile);
		}
		catch (Exception e)
		{
			ret=ConstantsHelper.FAIL;
			log.error(e);
			return ret;
		}
			
		
		//如果区域编码不为空，则只投放对应区域
		if (areaCode!=null && !areaCode.equals(""))
		{
			Dtv dtv = InitConfig.getDtvConfig(areaCode);
			log.info("start send file to ocg --areaCode:"+areaCode+"sourcePath:"+sourceFile+"gargetPath:"+targetPath);
			try{
				ftpService.setServer(dtv.getIp(),Integer.parseInt(dtv.getPort()), dtv.getUser(),dtv.getPwd());
				if (ftpService.sendAFile2ResourceServer(localDirectory+"/"+localFileName, dtv.getTargetPath()+"/"+targetPath))
				{
					ret=ConstantsHelper.SUCCESS;
				}
				else
				{
					log.error("sendtoDtvServer error:"+localDirectory+"/"+localFileName+" to"+dtv.getTargetPath()+"/"+targetPath);
					ret=ConstantsHelper.FAIL;
				}
			}
			catch(Exception e)
			{
				ret=ConstantsHelper.FAIL;
				log.error(e);
			}
			
		}
		//如果区域编码为空，则投放所有已配置区域
		else
		{
			List<Dtv> dtvList = InitConfig.getAdsConfig().getDtvList();
			for (int i=0;i<dtvList.size();i++)
			{
				Dtv dtv = dtvList.get(i);
				log.info("start send file to ocg --areaCode:"+dtv.getAreaCode());
				try{
					ftpService.setServer(dtv.getIp(),Integer.parseInt(dtv.getPort()), dtv.getUser(),dtv.getPwd());
					if (ftpService.sendAFile2ResourceServer(localDirectory+"/"+localFileName, dtv.getTargetPath()+"/"+targetPath))
					{
						ret=ConstantsHelper.SUCCESS;
					}
					else
					{
						ret=ConstantsHelper.FAIL;
						return ret;
					}
				}
				catch(Exception e)
				{
					ret=ConstantsHelper.FAIL;
					log.error(e);
					return ret;	
				}
				
			}
		}
		return ret;
	}
	/**
	 * 发送文件到DTV系统
	 * @param areaCode 区域ID
	 * @param 源目文件
	 * @param 目标文件
	 */
	public String deleteFile(String areaCode,String sourceFile,String targetPath)
	{
		String ret="1";
		return ret;
	}
	/**
	 * 发送文件到DTV系统
	 * @param areaCode 区域ID
	 * @param 源目录
	 * @param 目标目录
	 */
	public String sendPath(String areaCode,String sourcePath,String targetPath)
	{
		String ret="1";
		return ret;
	}
}
