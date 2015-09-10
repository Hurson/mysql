package com.avit.ads.pushads.task.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.task.bean.AdDefault;
import com.avit.ads.pushads.task.bean.AdvertPosition;
import com.avit.ads.pushads.task.bean.NvodMenuType;
import com.avit.ads.pushads.task.bean.TChannelinfo;
import com.avit.ads.pushads.task.bean.TChannelinfoNpvr;
import com.avit.ads.pushads.task.bean.TLoopbackCategory;
import com.avit.ads.pushads.task.dao.DatainitDao;
import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class DatainitDaoImpl extends CommonDaoImpl implements DatainitDao {

	public List<TChannelinfo> queryChannel() {
		// TODO Auto-generated method stub
		  StringBuffer sb = new StringBuffer();
	      sb.append("select distinct new com.avit.ads.pushads.task.bean.TChannelinfo(t.channelName,t.channelCode,t.serviceId) from TChannelinfo t  where 1=1");
	      
	      List params = new ArrayList();
	     // sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 
	    
	}

	public List<TChannelinfoNpvr> queryChannelNpvr() {
		// TODO Auto-generated method stub
		 StringBuffer sb = new StringBuffer();
	      sb.append("select distinct TChannelinfoNpvr from TChannelinfo ads where 1=1");
	      
	      List params = new ArrayList();
	     // sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 
	}

	public List<TLoopbackCategory> queryLookbackCategory() {
		// TODO Auto-generated method stub
		 StringBuffer sb = new StringBuffer();
	      sb.append("select distinct TLoopbackCategory from TChannelinfo ads where 1=1");
	      
	      List params = new ArrayList();
	     // sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 
	}
	//查询默认素材 
	public List<AdDefault> queryAdDefalult()
	{
		 String sql ="select a.POSITION_CODE POSITIONCODE,CONCAT(i.formal_file_path ,'/',i.name) DEFAULTFILE"+
					" from t_advertposition a,T_RESOURCE_AD rd,t_image_meta i,T_RESOURCE r"+
					" where a.id=rd.ad_id and r.id=rd.resource_id and i.ID=r.RESOURCE_ID order by a.id" ;
	      List params = new ArrayList();
	      List<AdDefault>  adDefaultlist= this.getObjectListForAll(sql, params,AdDefault.class);
	      return this.getObjectListForAll(sql, params,AdDefault.class); 
	}
	//List<ordercount>  orderlist =  query.setResultTransformer(Transformers.aliasToBean(ordercount.class)).list();
	public List<AdvertPosition> queryAdvertPosition()
	{
		 StringBuffer sb = new StringBuffer();
	      sb.append("from AdvertPosition where 1=1");
	      
	      List params = new ArrayList();
	     // sb.append(" order by ads.id desc "); //按id升序排序
	      return this.getListForAll(sb.toString(), params); 
	}

	public List<NvodMenuType> queryNvodMenuType() {
		String hql = "from NvodMenuType";
		return this.getListForAll(hql, new ArrayList());
	}

}
