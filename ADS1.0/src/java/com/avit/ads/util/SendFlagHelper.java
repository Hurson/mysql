package com.avit.ads.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class SendFlagHelper {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private Map<String, SendEntity> sendFlagMap = new HashMap<String, SendEntity>();
	
	//投放失败调用
	public void decreaseSendTimes(String areaCode){
		addEntityToMap(areaCode);
		SendEntity entity = sendFlagMap.get(areaCode);
		entity.setSendFlag(true);
		entity.setSendTimes(entity.getSendTimes() - 1);
		log.info("ads push failed, it has try " + (3 - entity.getSendTimes()) + " times");
	}
	
	//出现视频外框，菜单图片广告调用
	public void makeSent(String areaCode){
		addEntityToMap(areaCode);
		SendEntity entity = sendFlagMap.get(areaCode);
		entity.setSendFlag(true);
	}
	
	//判断是否需要投放
	public boolean needSent(String areaCode){
		SendEntity entity = sendFlagMap.get(areaCode);
		if(null != entity && entity.sendFlag() == true && entity.getSendTimes() > 0){
			return true;
		}
		return false;
	}
	/*
		判断是否投放三次都失败了
		可能少于三次：订单a投放两次失败后，新增投放订单b，也投放失败，投放结束；
		也可能多于三次：订单a投放三次失败，新增投放订单b,接着投放(MD5变化)，直到无新增订单
		出现上述两种情况概率较低，因为三次投放的时间间隔较短，即便出现这两种情况，也无大碍
	 */
	public boolean tryAllChance(String areaCode){
		SendEntity entity = sendFlagMap.get(areaCode);
		if(null != entity && entity.getSendTimes() <= 0){
			log.info("ads push failed, it has no chance to try again, change order's state for failure");
			return true;
		}
		return false;
	}
	
	//重置
	public void reset(String areaCode){
		SendEntity entity = new SendEntity(false, 3);
		sendFlagMap.put(areaCode, entity);
	}
	
	
	private void addEntityToMap(String areaCode){
		if(!sendFlagMap.containsKey(areaCode)){
			SendEntity entity = new SendEntity(false, 3);
			sendFlagMap.put(areaCode, entity);
		}
	}
	
	private class SendEntity{
		private boolean sendFlag;
		private int sendTimes;
		protected SendEntity(boolean sendFlag, int sendTimes){
			this.sendFlag = sendFlag;
			this.sendTimes = sendTimes;
		}
		public boolean sendFlag() {
			return sendFlag;
		}
		public void setSendFlag(boolean sendFlag) {
			this.sendFlag = sendFlag;
		}
		public int getSendTimes() {
			return sendTimes;
		}
		public void setSendTimes(int sendTimes) {
			this.sendTimes = sendTimes;
		}
		
	}
}
