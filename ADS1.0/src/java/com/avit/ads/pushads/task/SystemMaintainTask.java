package com.avit.ads.pushads.task;

import java.sql.Time;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.avit.ads.pushads.task.bean.SystemMaintain;
import com.avit.ads.pushads.task.bean.UntSystemMaintain;
import com.avit.ads.pushads.task.service.SystemMaintainService;

public class SystemMaintainTask {
	private Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private SystemMaintainService maintainService;
	@Value("${systemMaintain.ip}")
	private String url;

	public void maintainTask() {
		try {
			Time sysDate = new Time(System.currentTimeMillis());
			log.debug("系统时间:"+sysDate.getTime());
			SystemMaintain systemMaintain = maintainService.getAllMaintain();
			if (systemMaintain != null ) {
				Time sendTime = systemMaintain.getSendTime();
				if(sendTime.getHours()==sysDate.getHours()&& sendTime.getMinutes()==sysDate.getMinutes()
						&&sendTime.getSeconds() == sysDate.getSeconds()){
					log.info("开始待机指令发送!");
					UntSystemMaintain unt = maintainService.find();
					if (unt == null) {
						unt = new UntSystemMaintain();
					}
					maintainService.sendSystemMainToUnt(unt, systemMaintain);
					Thread.sleep(systemMaintain.getDuration() * 1000L);
					maintainService.sendActionCode(unt, systemMaintain);
					log.info("结束待机指令发送!");	
				}
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("InterruptedException", e);
			e.printStackTrace();
		} catch (Exception e2) {
			log.error("待机指令发送error", e2);
		}
	}

}
