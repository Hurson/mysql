package com.avit.ads.pushads.video.service.impl;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.video.service.VideoPumpService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Cps;
import com.avit.ads.util.bean.VideoPump;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.warn.WarnHelper;
@Service("VideoPumpService")
public class VideoPumpServiceImpl implements VideoPumpService {
	private Log log = LogFactory.getLog(this.getClass());
	
	@Inject
	private FtpService ftpService;
	
	@Autowired
	private WarnHelper warnHelper;
	
	public String sendFile(String sourceFile, String targetPath) {
		// TODO Auto-generated method stub
		String ret=ConstantsHelper.FAIL;
		
		String localDirectory =InitConfig.getVideoPumpConfig().get(0).getTempPath();
		String localFileName;			
		String fullfilename = InitConfig.getAdsConfig().getAdResource().getAdsResourcePath()+"/"+sourceFile;
		String remoteFileName =fullfilename.substring(fullfilename.lastIndexOf("/")+1);
		String remoteDirectory =fullfilename.substring(0,fullfilename.lastIndexOf("/")+1);
		localFileName =remoteFileName;
		
	
		//copy文件至本地文件
		try {
			ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(),Integer.parseInt(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(),InitConfig.getAdsConfig().getAdResource().getPwd());
		} catch (Exception e) {
			warnHelper.writeWarnMsgToDb("广告素材ftp服务器连接失败   IP:" + InitConfig.getAdsConfig().getAdResource().getIp());
			log.error("广告素材ftp服务器连接失败", e);
			return ret;		
		} 
		if (ftpService.download(remoteFileName, localFileName, remoteDirectory, localDirectory))
		{
			List<VideoPump> videoList  =InitConfig.getVideoPumpConfig();
			for (int i=0;i<videoList.size();i++)
			{
				try {
					ftpService.setServer(videoList.get(i).getIp(),Integer.parseInt(videoList.get(i).getPort()), videoList.get(i).getUser(),videoList.get(i).getPwd());
				} catch (Exception e) {
					warnHelper.writeWarnMsgToDb("视频ftp服务器连接失败   ip:" + videoList.get(i).getIp());
					log.error("视频ftp服务器连接失败", e);
					return ret;		
				} 
				if (ftpService.sendAFile2ResourceServer(localDirectory+"/"+localFileName,videoList.get(i).getTargetPath()+"/"+targetPath))
				{
					ret=ConstantsHelper.SUCCESS;
				}
			}
		}
		
		return ret;			
		//copy本地文件到资源服务上
		//ftpService.sendAFile2ResourceServer(sourceFile, targetPath);	
	}

	public String sendPath(String sourcePath, String targetPath) {
		// TODO Auto-generated method stub
		return ConstantsHelper.FAIL;
		
	}

	

}
