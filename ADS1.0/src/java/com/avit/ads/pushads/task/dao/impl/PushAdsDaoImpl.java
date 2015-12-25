package com.avit.ads.pushads.task.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.avit.ads.dtmb.bean.PlayList;
import com.avit.ads.pushads.task.bean.AdPlaylistGis;
import com.avit.ads.pushads.task.dao.PushAdsDao;
import com.avit.ads.util.bean.Ads;
import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class PushAdsDaoImpl extends CommonDaoImpl implements PushAdsDao {

	public HibernateTemplate getTemplate(){
		return this.getHibernateTemplate();
	}
	
	public List<AdPlaylistGis> queryStartAds(String adsTypeCode,String state,Date startTime)
	{
		// TODO Auto-generated method stub
		  StringBuffer sb = new StringBuffer();
		  List params = new ArrayList();
		  sb.append("from AdPlaylistGis ads where ads.endTime>=? ");
	      
		  params.add(startTime);
	      if (state!=null && !state.equals(""))
	      {
	    	  sb.append(" and ads.state=" + state);
	      }
	      if (startTime!=null)
	      {
	    	//  sb.append(" and ads.startTime <=? ");
	    	//  params.add(startTime);
	      }
	      if (adsTypeCode!=null && !adsTypeCode.equals(""))
	      {
	    	  sb.append(" and ads.adSiteCode =? ");
	    	  params.add(adsTypeCode);
	      }
	      sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 

	}
	public List<AdPlaylistGis> queryStartAds( Date startTime, String adsTypeCode) {
		// TODO Auto-generated method stub
		  StringBuffer sb = new StringBuffer();
	      sb.append("from AdPlaylistGis ads where ads.state=0 and ads.endTime>=? ");  
	      List params = new ArrayList();
	       params.add(startTime);
	      if (startTime!=null)
	      {
	    	  sb.append(" and ads.startTime <=? ");
	    	  params.add(startTime);
	      }
	      if (adsTypeCode!=null && !adsTypeCode.equals(""))
	      {
	    	  sb.append(" and ads.adSiteCode =? ");
	    	  params.add(adsTypeCode);
	      }
	      sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 

	}
	@SuppressWarnings("all")
	public List<PlayList> queryDStartAds(String adsTypeCode) {
		  StringBuffer sb = new StringBuffer();
	      sb.append("from PlayList ads where ads.status=0 and curdate() between ads.startDate and ads.endDate and date_format(now(),'%H:%i:%s') between ads.startTime and ads.endTime ");  
	      List params = new ArrayList();
	       
	      if (adsTypeCode!=null && !adsTypeCode.equals(""))
	      {
	    	  sb.append(" and ads.positionCode =? ");
	    	  params.add(adsTypeCode);
	      }
	      sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 

	}

	public List<AdPlaylistGis> queryNewPlayList(Date startTime, List<Ads> adsList) {
		if(null == adsList || adsList.size() == 0){
			return null;
		}
		List<String> adsCodeList = new ArrayList<String>();
		for(Ads ads : adsList){
			adsCodeList.add(ads.getAdsCode());
		}
		StringBuffer sb = new StringBuffer();
	    sb.append("from AdPlaylistGis ads where ads.state=0 and ads.endTime>=? ");  
	    List params = new ArrayList();
	    params.add(startTime);
	      
	    sb.append(" and ads.startTime <=? ");
	    params.add(startTime);
	     
	    sb.append(" and ads.adSiteCode in (:adsCodes) ");
	   
	    sb.append(" order by ads.id desc "); //按id升序排序
	    return this.getListForAllWithList(sb.toString(), params, adsCodeList); 
	}
	
	public List<AdPlaylistGis> queryEndAds(Date endTime, String adsTypeCode) {
		 StringBuffer sb = new StringBuffer();
	      sb.append("from AdPlaylistGis ads where ads.state=1 ");  
	      List params = new ArrayList();
	      if (endTime!=null)
	      {
	    	  sb.append(" and ads.endTime <=? ");
	    	  params.add(endTime);
	      }
	      if (adsTypeCode!=null && !adsTypeCode.equals(""))
	      {
	    	  sb.append(" and ads.adSiteCode =?" );
	    	  params.add(adsTypeCode);
	      }
	      sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 
	}
	
	@SuppressWarnings("all")
	public List<PlayList> queryDEndAds(String adsTypeCode) {
		 StringBuffer sb = new StringBuffer();
	      sb.append("from PlayList ads where ads.status='1' ");  
	      List params = new ArrayList();
	     
	      if (adsTypeCode!=null && !adsTypeCode.equals(""))
	      {
	    	  sb.append(" and ads.positionCode =?" );
	    	  params.add(adsTypeCode);
	      }
	      sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 
	}
	
	

	@SuppressWarnings("rawtypes")
	public String queryImageUrlByPlayListId(final Integer playListID) {
		final String sql = "SELECT m.imageUrl FROM ad_playlist_gis p, t_resource r, t_image_meta m WHERE p.ID = ? AND p.CONTENT_ID = r.ID AND r.RESOURCE_ID = m.ID";
		List resultList = getHibernateTemplate().executeFind(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException, SQLException {  
                Query query = session.createSQLQuery(sql);
                query.setInteger(0, playListID);
                return query.list();
            }  
        });
		if(null != resultList && resultList.size() > 0){
			return (String) resultList.get(0);
		}
		return "";
	}
	public void updateAdsFlag(Long adsid,String flag) {
		// TODO Auto-generated method stub
		 String updateSql;
		 updateSql ="update AdPlaylistGis ads set ads.state="+flag ;
		 if (adsid!=null && !adsid.equals(0))
		 {
			 updateSql = updateSql + " where ads.id="+adsid;
		 }
	     this.executeByHQL(updateSql,(Object[])null);
	}
	public void updateDAdsFlag(Integer adsid,String flag) {
		 String updateSql;
		 updateSql ="update PlayList ads set ads.status="+flag ;
		 if (adsid!=null && !adsid.equals(0))
		 {
			 updateSql = updateSql + " where ads.id="+adsid;
		 }
	     this.executeByHQL(updateSql,(Object[])null);
	}
	public void updateOrderState(final Long adsid, final String flag) {
		// TODO Auto-generated method stub
		String hql = "update TOrder ord set ord.state=? where ord.id=(select ads.orderId from AdPlaylistGis ads where ads.id=?)";
		this.executeByHQL(hql, new Object[]{flag, new BigDecimal(adsid)});
		
	}
	public void updateDOrderState(final Integer adsid, final String flag) {
		String hql = "update DOrder ord set ord.state=? where ord.orderCode=(select ads.orderCode from PlayList ads where ads.id=?)";
		this.executeByHQL(hql, new Object[]{flag, adsid});
		
	}
	public void updateAdsFlag(String adsids,String flag) {
		// TODO Auto-generated method stub
		 String updateSql;
		 updateSql ="update AdPlaylistGis ads set ads.state="+flag ;
		 if (adsids!=null && !adsids.equals(""))
		 {
			 updateSql = updateSql + " where ads.id in ("+adsids+")";
		 }
	     this.executeByHQL(updateSql,(Object[])null);
	}

	public String getAreaChannelTsId(final String areaCode, final String serviceId) {
		final String hql = "select ch.tsId from ChannelInfoSync ch where ch.networkId=? and ch.serviceId=?";
		
		return this.getHibernateTemplate().execute(new HibernateCallback<String>(){
			public String doInHibernate(Session session){
				Query query = session.createQuery(hql);
				query.setParameter(0, Long.valueOf(areaCode));
				query.setParameter(1, serviceId);
				return query.list().size() > 0 ?String.valueOf(query.list().get(0)) : null;
			}
				
		});
	}

	@SuppressWarnings("unchecked")
	public List<PlayList> querySendDTMBAds() {
		String hql = "select new PlayList(pl,ap.isHd,ap.position) from PlayList pl,DAdPosition ap where pl.positionCode = ap.positionCode " +
				     " and curdate() between pl.startDate and pl.endDate and date_format(now(),'%H:%i:%s') between pl.startTime and pl.endTime and ap.positionType = '1'";
		return (List<PlayList>)this.getListForAll(hql, new Object[]{});
	}
	}
