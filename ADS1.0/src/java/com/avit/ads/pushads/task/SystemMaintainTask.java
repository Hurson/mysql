package com.avit.ads.pushads.task;

import java.sql.Time;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.avit.ads.pushads.ocg.service.OcgService;
import com.avit.ads.pushads.task.bean.SystemMaintainBean;
import com.avit.ads.pushads.task.bean.UntSystemMaintain;
import com.avit.ads.pushads.task.service.SystemMaintainService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.message.SystemMaintain;

public class SystemMaintainTask {
	private Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private SystemMaintainService maintainService;
	@Autowired
	private OcgService ocgService;
	@Value("${systemMaintain.ip}")
	private String url;
	/**
	 * 测试用
	 * @param args
	 */
	public static void main(String[] args) {
		SystemMaintainTask a = new SystemMaintainTask();
		a.maintainTask();
	}
	/**
	 * 定时任务，用于发送有线待机指令
	 */
	public void maintainTask() {
		try {
			Time sysDate = new Time(System.currentTimeMillis());
			log.debug("系统时间:"+sysDate.getTime());
			SystemMaintainBean systemMaintainBean = maintainService.getAllMaintain();
			if (systemMaintainBean != null ) {
				Date sendTime = systemMaintainBean.getSendTime();
				log.info("开始待机指令发送!");
				SystemMaintain unt = new SystemMaintain();
				unt.setActionCode(systemMaintainBean.getActionCode());
				unt.setActiveHour(systemMaintainBean.getActiveHour());
				String[] Codes = systemMaintainBean.getAreaCodes().trim().split(",");
				for(int i=0;i<Codes.length;i++){
					ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_STB, unt,Codes[i], null);
				}
				Thread.sleep(systemMaintainBean.getDuration() * 1000L);
				//发送周期结束后，发送停止指令（0）
				unt.setActionCode(0);
				for(int i=0;i<Codes.length;i++){
					ocgService.sendUntUpdateByAreaCode(ConstantsHelper.REALTIME_UNT_MESSAGE_STB, unt,Codes[i], null);
				}
				/*maintainService.sendSystemMainToUnt(unt, systemMaintain);
				maintainService.sendActionCode(unt, systemMaintain)*/;
				log.info("结束待机指令发送!");	
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Exception", e);
			e.printStackTrace();
		} 
	}
}
