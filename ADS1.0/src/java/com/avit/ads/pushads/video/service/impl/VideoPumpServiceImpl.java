package com.avit.ads.pushads.video.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.avit.ads.pushads.video.service.VideoPumpService;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Cps;
import com.avit.ads.util.bean.VideoPump;
import com.avit.common.ftp.service.FtpService;
@Service("VideoPumpService")
public class VideoPumpServiceImpl implements VideoPumpService {
	@Inject
	private FtpService ftpService;
	
	public void sendFile(String sourceFile, String targetPath) {
		// TODO Auto-generated method stub
		String localDirectory =InitConfig.getVideoPumpConfig().get(0).getTempPath();
		String localFileName;			
		String fullfilename = InitConfig.getAdsConfig().getAdResource().getAdsResourcePath()+"/"+sourceFile;
		String remoteFileName =fullfilename.substring(fullfilename.lastIndexOf("/")+1);
		String remoteDirectory =fullfilename.substring(0,fullfilename.lastIndexOf("/")+1);
		localFileName =remoteFileName;
		
		try
		{
			//copy文件至本地文件
			List<VideoPump> videoList  =InitConfig.getVideoPumpConfig();
			for (int i=0;i<videoList.size();i++)
			{
				ftpService.setServer(InitConfig.getAdsConfig().getAdResource().getIp(),Integer.parseInt(InitConfig.getAdsConfig().getAdResource().getPort()), InitConfig.getAdsConfig().getAdResource().getUser(),InitConfig.getAdsConfig().getAdResource().getPwd());
				ftpService.download(remoteFileName, localFileName, remoteDirectory, localDirectory);
				ftpService.setServer(videoList.get(i).getIp(),Integer.parseInt(videoList.get(i).getPort()), videoList.get(i).getUser(),videoList.get(i).getPwd());
				ftpService.sendAFile2ResourceServer(localDirectory+"\\"+localFileName,videoList.get(i).getTargetPath()+"/"+targetPath);
			}
		}
		catch (Exception e)
		{
			
		}
						
		//copy本地文件到资源服务上
		//ftpService.sendAFile2ResourceServer(sourceFile, targetPath);	
	}

	public void sendPath(String sourcePath, String targetPath) {
		// TODO Auto-generated method stub

	}

	

}
