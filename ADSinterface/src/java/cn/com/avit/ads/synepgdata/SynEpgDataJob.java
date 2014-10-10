package cn.com.avit.ads.synepgdata;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.avit.ads.util.ContextHolder;
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
				
		BaseDao daoImpl = (BaseDaoImpl)ContextHolder.getApplicationContext().getBean("baseDao");
		
		//ads.xml中添加EPG提供的FTP访问方式
		String ip = "";
		Integer port = 21;
		String username = "username";
		String password = "password";
		
//		try {
//			ftpService.setServer(ip, port, username, password);
//		} catch (IOException e) {
//			warnHelper.writeWarnMsgToDb("EPG系统ftp连接失败   ip:" + ip + ", port: " + port + " , username: " + username + ", pwd: " + password);
//			log.error("EPG系统ftp连接失败", e);
//		}
				
		//FTP下载文件
		
		//如果下载到了文件，逐行解析字段
		String serviceId = "1024";
		String channelName = "channelName";
		String channelType = "channelType";
		Long tsId = 23423L;
		
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
