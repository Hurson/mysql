package cn.com.avit.ads.synepgdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avit.ads.util.ContextHolder;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Dtmbftp;
import com.avit.ads.util.bean.Epgftp;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.ftp.service.impl.FtpServiceImpl;
import com.avit.common.page.dao.BaseDao;
import com.avit.common.page.dao.impl.BaseDaoImpl;
import com.avit.common.warn.WarnHelper;

public class DSynEpgDataJob {
	
	public void synEpgData(){
		
		Logger log = LoggerFactory.getLogger(this.getClass());
		
		FtpService ftpService = (FtpServiceImpl)ContextHolder.getApplicationContext().getBean("FtpService");	
		
		WarnHelper warnHelper = (WarnHelper)ContextHolder.getApplicationContext().getBean("warnHelper");	
		
		//ads.xml中添加EPG提供的FTP访问方式	
		Dtmbftp dtmbFtp = InitConfig.getAdsConfig().getDtmbFtp();
				
		String ip = dtmbFtp.getIp();
		Integer port = Integer.parseInt(dtmbFtp.getPort());
		String username = dtmbFtp.getUser();
		String password = dtmbFtp.getPwd();
		
		try {
			log.info("connect to DTMB ftp server..");
			ftpService.setServer(ip, port, username, password);
		} catch (IOException e) {
			warnHelper.writeWarnMsgToDb("DPTB系统ftp连接失败   ip:" + ip);
			log.error("DTMB系统ftp连接失败", e);
			return;
		}
		
		String remoteDirectory = InitConfig.getAdsConfig().getRealTimeAds().getDtmbChannelInfoPath();
		String localDirectory = InitConfig.getAdsConfig().getRealTimeAds().getDtmbTempPath();
	
		//取EPG最新生成的文件，每个区域有一个文件
	    List<String> fileNameList = ftpService.getLatestFiles(remoteDirectory);
	    
	    if(null != fileNameList && fileNameList.size() > 0){
	    	
	    	for(String remoteFileName : fileNameList){
	    		
	    		if(StringUtils.isBlank(remoteFileName)){
	    	    	return;
	    	    }
	    		
	    		String localFileName = remoteFileName;
	    		if(log.isInfoEnabled()){
	    			log.info("download channelInfo file [" + remoteFileName +  "] from dtmb ftp");
	    		}
	    		if(!ftpService.downloadFile(remoteFileName, localFileName, remoteDirectory, localDirectory)){
	    			return;
	    		}
	    		
	    		File channelInfoFile = new File(localDirectory + "/" + localFileName);
	    		
	    		if(channelInfoFile.exists()){
	    			log.info("parse file and update database's channelInfo table..");
	    			BaseDao daoImpl = (BaseDaoImpl)ContextHolder.getApplicationContext().getBean("baseDao");
	    			BufferedReader br = null;
	    			try {
	    				String networkId = "";
	    				if(localFileName.length() > 6){
	    					networkId = localFileName.substring(1, 6);
	    				}
	    				br = new BufferedReader(new InputStreamReader(new FileInputStream(channelInfoFile),"utf-8"));
	    				String line = "";
	    				while(null != (line = br.readLine())){
	    					parseInfoAndUpdateDb(networkId, line,daoImpl);
	    				} 
	    				
	    			} catch (FileNotFoundException e) {
	    				log.error("读取文件异常",e);
	    			}catch (IOException e) {
	    				log.error("读取文件异常",e);
	    			}finally{
	    				try {
	    					if(null != br){
	    						br.close();
	    					}
	    				} catch (IOException e) {
	    					log.error("关闭文件流异常",e);
	    				}
	    			}
	    			
	    			//删除同区域非最新文件
	    			deleteOtherFiles(localDirectory, localFileName);
	    			
	    		}
	    	}
	    	log.info("finish syn dtmb channelinfo data.");
	    }	
	}
	
	private void deleteOtherFiles(String localDir, String fileName){
		File folder = new File(localDir);
		for(File file : folder.listFiles()){
			//删除同区域（文件名前6位相同）的其他文件
			if(!file.getName().equals(fileName) && file.getName().substring(0, 6).equals(fileName.substring(0, 6))){
				file.delete();
			}
		}
	}
	
	private void parseInfoAndUpdateDb(String networkId, String line,BaseDao daoImpl){
		String[] datas = line.split("\\|");	
		if(datas.length == 4){  // 频道名称|servieID|tsid|频道类型	
			String channelName = datas[0].trim();
			String serviceId = datas[1].trim();	
			Long tsId = Long.parseLong(datas[2]);
			String channelType = datas[3].trim();
			
			//校验
			if(StringUtils.isBlank(channelName) || StringUtils.isBlank(serviceId)
					|| StringUtils.isBlank(channelType) || (!"1".equals(channelType) && !"2".equals(channelType))){
				return;
			}
			
			List<DChannelInfoSync> syncList = daoImpl.getListForAll("from DChannelInfoSync cs where cs.serviceId=? and cs.networkId =?", new Object[]{serviceId, networkId});
			DChannelInfoSync syncEntity = null;
			if(null != syncList && syncList.size() > 0){
				syncEntity = syncList.get(0);
				//名称变了或者类型变了更新，ts_id变了更新
				if(!channelName.equals(syncEntity.getChannelName()) || !channelType.equals(syncEntity.getChannleType())|| !tsId.equals(syncEntity.getTsId())){
					syncEntity.setChannelName(channelName);
					syncEntity.setChannleType(channelType);
					syncEntity.setTsId(tsId);	
					
					daoImpl.save(syncEntity);
				}
			}else{
				syncEntity = new DChannelInfoSync();
				syncEntity.setServiceId(serviceId);
				syncEntity.setNetworkId(networkId);
				syncEntity.setChannelName(channelName);
				syncEntity.setChannleType(channelType);
				syncEntity.setTsId(tsId);	
				
				daoImpl.save(syncEntity);
			}
				
			/*List<ChannelInfo> resultList = daoImpl.find("from ChannelInfo ci where ci.serviceId=?", serviceId);
			ChannelInfo entity = null;
			if(null != resultList && resultList.size() > 0){
				entity = resultList.get(0);
				//名称变了或者类型变了才更新，ts_id变了不更新
				if(!channelName.equals(entity.getChannelName()) || !channelType.equals(entity.getChannleType())){
					entity.setChannelName(channelName);
					entity.setChannleType(channelType);
					entity.setTsId(tsId);	
					
					daoImpl.save(entity);
				}
			}else{
				entity = new ChannelInfo();
				entity.setServiceId(serviceId);
				entity.setChannelName(channelName);
				entity.setChannleType(channelType);
				entity.setTsId(tsId);	
				
				daoImpl.save(entity);
			}*/
				
			
		}	
	}

}
