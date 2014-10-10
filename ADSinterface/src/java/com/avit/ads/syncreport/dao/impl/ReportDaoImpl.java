package com.avit.ads.syncreport.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.syncreport.bean.AdPlaylistGis;
import com.avit.ads.syncreport.bean.ContentPush;
import com.avit.ads.syncreport.bean.TReportData;
import com.avit.ads.syncreport.dao.ReportDao;
import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class ReportDaoImpl  extends CommonDaoImpl implements ReportDao {

	public List<AdPlaylistGis> queryAdPlaylistGisList(String positionCode,Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		  StringBuffer sb = new StringBuffer();
		  List params = new ArrayList();
		  sb.append("from AdPlaylistGis ads where ads.state <> 2 and ads.state <> 3 "
				+ " and ads.adSiteCode IN ('01002', '02012', '02102', '02052', '02042', '02022', '02032', '02034', '02062') "
		  		+ " and ((ads.startTime <=? and ads.endTime >=?) or (ads.startTime <=? and ads.endTime >?) or (ads.startTime <? and ads.endTime >=?) or (ads.startTime >=? and ads.endTime <=?))");
		  params.add(startTime);	      
		  params.add(endTime);
		  
		  params.add(startTime);	
		  params.add(startTime);	
	     
		  params.add(endTime);
		  params.add(endTime);
		  params.add(startTime);	      
		  params.add(endTime);
	      if (positionCode!=null && !positionCode.equals(""))
	      {
	    	  sb.append(" and ads.adSiteCode =? ");
	    	  params.add(positionCode);
	      }
	      sb.append(" order by ads.id "); //按id升序排序
	     // params = new ArrayList();
	     // from AdPlaylistGis ads where 1=1
	     // return this.getListForAll("from AdPlaylistGis ads where 1=1 and ads.id=1564", params); 
	      return this.getListForAll(sb.toString(), params); 
		//return null;
	}
	public boolean insertReport(TReportData report)
	{
		try
		{
			this.getHibernateTemplate().save(report);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public boolean deleteReportByDate(Date startDate,Date endDate)
	{
		try
		{
			String deleteSql = "delete from TReportData where pushDate>=? and pushDate<?";
			Object [] values= new Object[2];
			values[0]=startDate;
			values[1]=endDate;
			this.executeByHQL(deleteSql, values);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public boolean addEffandReacheCount(ContentPush contentPush ,Date day)
	{
		
		try
		{
			  StringBuffer sb = new StringBuffer();
			  List params = new ArrayList();
			  sb.append("from TReportData ads where 1=1 and (ads.pushDate >=? and ads.pushDate <=?) and ads.resourceId="+contentPush.getContentid());
			  //sb.append("from TReportData ads where 1=1 and ads.resourceId="+contentPush.getContentid());
			  
			  Date startTime=day;
			  Date endTime=day;
			  
			  Calendar cal =Calendar.getInstance(); 
			  cal.setTime(day);
			  cal.set(Calendar.HOUR_OF_DAY, 0);
			  cal.set(Calendar.MINUTE, 0);
		      cal.set(Calendar.SECOND, 0);
		      startTime = cal.getTime();
		      
		      cal.set(Calendar.HOUR_OF_DAY, 23);
			  cal.set(Calendar.MINUTE, 59);
		      cal.set(Calendar.SECOND, 59);
		      endTime = cal.getTime();
			  params.add(startTime);	      
			  params.add(endTime);		
			  
			  List<TReportData> list=getListForAll(sb.toString(),params);
			  if (list!=null&& list.size()>0 )
			  {
				  TReportData reportDatatemp=list.get(0);
				  reportDatatemp.setEffCount(contentPush.getEffectiveCount());
				  reportDatatemp.setReacheCount(contentPush.getReachCount());
				  
				  this.save(reportDatatemp);
			  }
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
