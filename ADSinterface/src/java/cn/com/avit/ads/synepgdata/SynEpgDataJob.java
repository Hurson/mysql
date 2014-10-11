package cn.com.avit.ads.synepgdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Epgftp;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.ftp.service.impl.FtpServiceImpl;
import com.avit.common.page.dao.BaseDao;
import com.avit.common.page.dao.impl.BaseDaoImpl;
import com.avit.common.warn.WarnHelper;

public class SynEpgDataJob {
	
	
	//每天执行的job，从EPG获得单向频道信息（通过FTP方式获取数据文件），更新数据库
	
	public void synEpgData(){
		
		Log log = LogFactory.getLog(this.getClass());
		
		FtpService ftpService = (FtpServiceImpl)ContextHolder.getApplicationContext().getBean("FtpService");	
		WarnHelper warnHelper = (WarnHelper)ContextHolder.getApplicationContext().getBean("warnHelper");	
		
		//ads.xml中添加EPG提供的FTP访问方式	
		Epgftp epgFtp = InitConfig.getAdsConfig().getEpgftp();
				
		String ip = epgFtp.getIp();
		Integer port = Integer.parseInt(epgFtp.getPort());
		String username = epgFtp.getUser();
		String password = epgFtp.getPwd();
		
		try {
			log.info("connect to epg ftp server..");
			ftpService.setServer(ip, port, username, password);
		} catch (IOException e) {
			warnHelper.writeWarnMsgToDb("EPG系统ftp连接失败   ip:" + ip + ", port: " + port + " , username: " + username + ", pwd: " + password);
			log.error("EPG系统ftp连接失败", e);
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String remoteFileName =  sdf.format(new Date()) + ".txt";  	
		String localFileName = remoteFileName;
		String remoteDirectory = InitConfig.getAdsConfig().getRealTimeAds().getEpgChannelInfoPath();
		String localDirectory = InitConfig.getAdsConfig().getRealTimeAds().getEpgTempPath();
		
		log.info("download channelInfo file from epg ftp");
		if(!ftpService.downloadFile(remoteFileName, localFileName, remoteDirectory, localDirectory)){
			return;
		}
		
		File channelInfoFile = new File(localDirectory + "/" + localFileName);	
		if(channelInfoFile.exists()){
			log.info("parse file and update database's channelInfo table..");
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(channelInfoFile)));
				String line = "";
				while(null != (line = br.readLine())){
					parseInfoAndUpdateDb(line);
				} 
				
			} catch (FileNotFoundException e) {
				log.error("读取文件异常",e);
			}catch (IOException e) {
				log.error("读取文件异常",e);
			}finally{
				try {
					br.close();
				} catch (IOException e) {
					log.error("关闭文件流异常",e);
				}
			}
			deleteOtherFiles(localDirectory, localFileName);
		}
	}
	
	private void deleteOtherFiles(String localDir, String fileName){
		File folder = new File(localDir);
		for(File file : folder.listFiles()){
			if(!file.getName().equals(fileName)){
				file.delete();
			}
		}
	}
	
	private void parseInfoAndUpdateDb(String line){
		String[] datas = line.split("|");	
		if(datas.length == 4){  // 频道名称|servieID|tsid|频道类型	
			String channelName = datas[0].trim();
			String serviceId = datas[1].trim();		
			String channelType = datas[2].trim();
			Long tsId = Long.parseLong(datas[3]);
			
			BaseDao daoImpl = (BaseDaoImpl)ContextHolder.getApplicationContext().getBean("baseDao");
			List resultList = daoImpl.find("from ChannelInfo ci where ci.serviceId=?", serviceId);
			ChannelInfo entity = null;
			if(null != resultList && resultList.size() > 0){
				entity = (ChannelInfo)resultList.get(0);
			}else{
				entity = new ChannelInfo();
				entity.setServiceId(serviceId);
			}
			entity.setChannelName(channelName);
			entity.setChannleType(channelType);
			entity.setTsId(tsId);	
			
			daoImpl.save(entity);	
			
		}	
	}

}
