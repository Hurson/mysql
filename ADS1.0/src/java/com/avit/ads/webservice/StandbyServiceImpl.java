package com.avit.ads.webservice;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.task.bean.StandByCronTriggerBean;

@Service
@WebService(endpointInterface = "com.avit.ads.webservice.StandbyService", serviceName = "StandbyService")
public class StandbyServiceImpl implements StandbyService {
	
	
	@Resource(name="startQuertz")
	private Scheduler scheduler;

	public void sendStandByToUnt() {
		System.out.println("reschedule standby time...");
		try {
			StandByCronTriggerBean trigger = (StandByCronTriggerBean)scheduler.getTrigger("standbyTime", Scheduler.DEFAULT_GROUP);
			trigger.init();
			scheduler.rescheduleJob("standbyTime", Scheduler.DEFAULT_GROUP, trigger);
			System.out.println(scheduler.isInStandbyMode());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void clearUnt(String id) {
		// TODO Auto-generated method stub

	}

}
