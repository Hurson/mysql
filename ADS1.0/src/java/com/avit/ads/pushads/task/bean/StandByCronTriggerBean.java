package com.avit.ads.pushads.task.bean;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.avit.ads.pushads.task.dao.SystemMaintainDao;

public class StandByCronTriggerBean extends CronTriggerBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Log log = LogFactory.getLog(this.getClass());
	@Resource
	private SystemMaintainDao dao;

	@PostConstruct
	public void init() {
		try {
			SystemMaintainBean maintain = dao.getAllMaintain();
			if (maintain != null) {
					Date date = maintain.getSendTime();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					// CronTriggerBean拼接
					String pushTime = cal.get(Calendar.SECOND)
							+ " "
							+ cal.get(Calendar.MINUTE)
							+ " "
							+ cal.get(Calendar.HOUR_OF_DAY)
							+ " "
							+ cal.get(Calendar.DAY_OF_MONTH)
							+ " "
							+ cal.get(Calendar.MONTH) + 1
							+ " ?";
					log.debug(pushTime);
					setCronExpression(pushTime);
				}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
