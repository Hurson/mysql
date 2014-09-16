package com.avit.ads.util.warn;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.avit.ads.util.bean.WarnInfo;
import com.avit.common.page.dao.impl.BaseDaoImpl;

@Component
public class WarnHelper extends BaseDaoImpl{
	/*
	 * 将告警信息写入数据库
	 */
	public void writeWarnInfoToDB(WarnInfo entity){
		save(entity);
	}
	public void writeWarnMsgToDb(String msg){
		WarnInfo entity = new WarnInfo();
		entity.setTime(new Date());
		entity.setContent(msg);
		entity.setIsProcessed(0);
		save(entity);
	}
}
