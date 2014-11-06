package cn.com.avit.ads.synepgdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.StringUtils;
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
		
		String remoteDirectory = InitConfig.getAdsConfig().getRealTimeAds().getEpgChannelInfoPath();
		String localDirectory = InitConfig.getAdsConfig().getRealTimeAds().getEpgTempPath();
	
		//取EPG最新生成的文件，每个区域有一个文件
	    Collection<String> fileNameCol = ftpService.getLatestFiles(remoteDirectory);
	    
	    if(null != fileNameCol && fileNameCol.size() > 0){
	    	
	    	for(String remoteFileName : fileNameCol){
	    		
	    		if(StringUtils.isBlank(remoteFileName)){
	    	    	return;
	    	    }
	    		
	    		String localFileName = remoteFileName;
	    		
	    		log.info("download channelInfo file [" + remoteFileName +  "] from epg ftp");
	    		if(!ftpService.downloadFile(remoteFileName, localFileName, remoteDirectory, localDirectory)){
	    			return;
	    		}
	    		
	    		File channelInfoFile = new File(localDirectory + "/" + localFileName);
	    		
	    		if(channelInfoFile.exists()){
	    			log.info("parse file and update database's channelInfo table..");
	    			BaseDao daoImpl = (BaseDaoImpl)ContextHolder.getApplicationContext().getBean("baseDao");
	    			BufferedReader br = null;
	    			try {
	    				br = new BufferedReader(new InputStreamReader(new FileInputStream(channelInfoFile)));
	    				String line = "";
	    				while(null != (line = br.readLine())){
	    					parseInfoAndUpdateDb(line,daoImpl);
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
	    			
	    			//利用临时表删除正式表中多余的serviceid
	    			daoImpl.excuteBySql(" DELETE FROM t_channelinfo WHERE SERVICE_ID NOT IN (SELECT SERVICE_ID FROM t_channelinfo_temp)", null);
	    			//清空临时表
	    			daoImpl.deleteBySql("DELETE FROM t_channelinfo_temp", null);
	    		}
	    	}
	    	log.info("finish syn epg channelinfo data.");
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
	
	private void parseInfoAndUpdateDb(String line,BaseDao daoImpl){
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
			
			//写临时表表，用处：正式表中不存在文件中的serviceid需要删除
			ChannelInfoTemp tempEntity = new ChannelInfoTemp();			
			tempEntity.setServiceId(serviceId);
			daoImpl.save(tempEntity);
			
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
