package com.avit.ads.pushads.task.service;


import org.apache.cxf.jaxrs.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.task.bean.SystemMaintain;
import com.avit.ads.pushads.task.bean.UntSystemMaintain;
import com.avit.ads.pushads.task.dao.SystemMaintainDao;
import com.avit.ads.pushads.task.dao.UntSystemMaintainDao;
import com.avit.ads.util.CommonUtil;

@Service(value="standByService")
public class SystemMaintainService {
	private Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private SystemMaintainDao maintainDao;
	@Autowired
	private UntSystemMaintainDao untDao;

	public SystemMaintain getAllMaintain() {
		return maintainDao.getAllMaintain();
	}
	
	public UntSystemMaintain find(){
		return untDao.find();
	}
	public SystemMaintain findSystemMaintain(){
		return maintainDao.fin();
		
	}

	public void saveOrUpdate(SystemMaintain maintain) {
		
	}

	public void sendSystemMainToUnt(UntSystemMaintain unt,SystemMaintain systemMaintain) {
		log.info("待机指令发送开始");
		
				unt.setActiveHour(systemMaintain.getActiveHour().intValue());
				unt.setActionCode(CommonUtil.ACTIONCODE_STANDEY);
				untDao.saveOrUpdate(unt);
				//待机指令发送
				//HttpUtils.post(url, null);
			
		//}

	}
	public void sendActionCode(UntSystemMaintain unt,SystemMaintain systemMaintain){
		
				unt.setActiveHour(systemMaintain.getActiveHour().intValue());
				unt.setActionCode(CommonUtil.ACTIONCODE_STOP);
				untDao.saveOrUpdate(unt);
				//HttpUtils.post(url, null);
			
	}
	


}
