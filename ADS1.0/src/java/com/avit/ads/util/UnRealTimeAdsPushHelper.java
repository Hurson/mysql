package com.avit.ads.util;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.avit.ads.pushads.task.bean.AdPlaylistGis;
import com.avit.common.page.dao.impl.BaseDaoImpl;

@Component
public class UnRealTimeAdsPushHelper extends BaseDaoImpl{
	
	private Log log = LogFactory.getLog(this.getClass());

	private Map<Long, Integer> delMap = new HashMap<Long, Integer>();
	
	private Map<Long, Integer> pushMap = new HashMap<Long, Integer>();
	
	private static final Integer PUSH_TIMES = 3;
	
	public boolean delDecreaseAndTryAgain(Long playListId){
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
	
	public void cleanDelMap(Long playListId){
		delMap.remove(playListId);
	}
	
	public boolean pushDecreaseAndTryAgain(Long playListId){
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
	
	public void cleanPushMap(Long playListId){
		pushMap.remove(playListId);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void changeOrderStateToFailed(Long playListId){
		BigDecimal bdPlayListId = BigDecimal.valueOf(playListId);
		AdPlaylistGis entity = (AdPlaylistGis) get(AdPlaylistGis.class, bdPlayListId);
		BigDecimal orderId = entity.getOrderId();
		final String updateSql = "UPDATE t_order SET state = '9' WHERE id = " + toInteger(orderId);
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createSQLQuery(updateSql).executeUpdate();
				return null;
			}
		});
	}
	
	
}
