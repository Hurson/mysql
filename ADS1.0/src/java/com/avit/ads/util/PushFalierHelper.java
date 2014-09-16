package com.avit.ads.util;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.avit.ads.pushads.task.bean.AdPlaylistGis;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.Ads;
import com.avit.common.page.dao.impl.BaseDaoImpl;

@Component
public class PushFalierHelper extends BaseDaoImpl{
	
	private List<AdPlaylistGis> newGisEntityList = new ArrayList<AdPlaylistGis>();
	
	public void addNewPlayList(List<AdPlaylistGis> newList){
		newGisEntityList.addAll(newList);
	}
	
	
	public void changeStateToFailed(String areaCode){
		List<BigDecimal> orderIdList = new ArrayList<BigDecimal>();
		
		for(AdPlaylistGis entity : newGisEntityList){
			if(areaCode.equals(entity.getAreas())){
				//修改播出单状态
				entity.setState(2L);
				save(entity);
				//需要修改状态的订单id
				BigDecimal orderId = entity.getOrderId();
				if(!orderIdList.contains(orderId)){
					orderIdList.add(orderId);
				}
			}
		}
		//修改订单状态  
		changeOrderStateToFailed(orderIdList);
	}
	
	public boolean rePush(String areaCode){
		for(AdPlaylistGis entity : newGisEntityList){
			if(areaCode.equals(entity.getAreas()) && "1".equals(entity.getRePush())){
				return true;
			}
		}
		return false;
	}
	
	public void resetRePushFlag(String areaCode){
		for(AdPlaylistGis entity : newGisEntityList){
			if(areaCode.equals(entity.getAreas()) && "1".equals(entity.getRePush())){
				entity.setRePush("0");
				save(entity);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void changeOrderStateToFailed(List<BigDecimal> orderIdList){
		for(BigDecimal orderId : orderIdList){
			final String updateSql = "UPDATE t_order SET state = '9' WHERE id = " + toInteger(orderId);
			getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					session.createSQLQuery(updateSql).executeUpdate();
					return null;
				}
			});
		}
	}
	
	public void deletePlayListEntity(String areaCode){
		Iterator<AdPlaylistGis> iterator = newGisEntityList.iterator();
		while(iterator.hasNext()){
			AdPlaylistGis entity = iterator.next();
			if(areaCode.equals(entity.getAreas())){
				iterator.remove();
			}
		}
	}
	
	public void changeStateToFailed(List<Ads> adsList){
		List<BigDecimal> orderIdList = new ArrayList<BigDecimal>();
		for(Ads ads : adsList){
			for(AdPlaylistGis entity : newGisEntityList){
				if(ads.getAdsCode().equals(entity.getAdSiteCode())){
					//修改播出单状态
					entity.setState(2L);
					save(entity);
					//需要修改状态的订单id
					BigDecimal orderId = entity.getOrderId();
					if(!orderIdList.contains(orderId)){
						orderIdList.add(orderId);
					}
				}
			}
		}
		//修改订单状态  
		changeOrderStateToFailed(orderIdList);
	}
	
	public boolean rePush(List<Ads> adsList){
		for(Ads ads : adsList){
			for(AdPlaylistGis entity : newGisEntityList){
				if(ads.getAdsCode().equals(entity.getAdSiteCode()) && "1".equals(entity.getRePush()) ){
					return true;
				}
			}
		}
		return false;
	}
	
	public void resetRePushFlag(List<Ads> adsList){
		for(Ads ads : adsList){
			for(AdPlaylistGis entity : newGisEntityList){
				if(ads.getAdsCode().equals(entity.getAdSiteCode()) && "1".equals(entity.getRePush()) ){
					entity.setRePush("0");
					save(entity);
				}
			}
		}
	}
	
	public void deletePlayListEntity(List<Ads> adsList){
		Iterator<AdPlaylistGis> iterator = newGisEntityList.iterator();
		while(iterator.hasNext()){
			AdPlaylistGis entity = iterator.next();
			for(Ads ads : adsList){
				if(ads.getAdsCode().equals(entity.getAdSiteCode())){
					iterator.remove();
					break;
				}
			}
		}
	}
}
