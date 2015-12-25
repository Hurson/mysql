package com.avit.ads.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.avit.ads.dtmb.bean.PlayList;
import com.avit.common.page.dao.impl.BaseDaoImpl;

@Component
public class DtmbUnRealTimeAdsPushHelper extends BaseDaoImpl{
	
	private Log log = LogFactory.getLog(this.getClass());

	//处理过期播出单使用
	private Map<Integer, Integer> delMap = new HashMap<Integer, Integer>();
	
	//投放播出单使用
	private Map<Integer, Integer> pushMap = new HashMap<Integer, Integer>();
	
	private static final Integer PUSH_TIMES = 3;
	
	public boolean delDecreaseAndTryAgain(Integer playListId){
		if(!delMap.containsKey(playListId)){
			delMap.put(playListId, PUSH_TIMES);
		}
		if(delMap.get(playListId) > 1){
			delMap.put(playListId, delMap.get(playListId) -1);
			log.info("ads push failed, it has " + delMap.get(playListId) + " times to try");
			return true;
		}
		return false;
	}
	
	public void cleanDelMap(Integer playListId){
		delMap.remove(playListId);
	}
	
	public boolean pushDecreaseAndTryAgain(Integer playListId){
		if(!pushMap.containsKey(playListId)){
			pushMap.put(playListId, PUSH_TIMES);
		}
		if(pushMap.get(playListId) > 1){
			pushMap.put(playListId, pushMap.get(playListId) -1);
			log.info("ads push failed, it has " + pushMap.get(playListId) + " times to try");
			return true;
		}
		return false;
	}
	
	public void cleanPushMap(Integer playListId){
		pushMap.remove(playListId);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void changeOrderStateToFailed(Integer playListId){
		PlayList entity = (PlayList) get(PlayList.class, playListId);
		String orderCode = entity.getOrderCode();
		final String updateSql = "UPDATE d_order SET state = '9' WHERE order_code = '" + orderCode+"'";
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createSQLQuery(updateSql).executeUpdate();
				return null;
			}
		});
	}
	
	
}
