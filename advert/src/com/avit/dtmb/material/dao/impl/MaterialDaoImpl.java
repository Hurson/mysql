package com.avit.dtmb.material.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.material.dao.MaterialDao;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.VideoMeta;
@Repository("MaterialDao")
public class MaterialDaoImpl extends BaseDaoImpl implements MaterialDao{
	@Override
	public PageBeanDB queryDMaterialList(DResource meterialQuery,int pageNo, int pageSize) {
		
		String sql = "select distinct new com.avit.dtmb.material.bean.DResource ("+
					"r.id,"
					+ "r.resourceName,"
					+ "r.resourceType,"
					+ "r.customerId,"
					+ "r.categoryId,"
					+ "r.positionCode,"
					+ "r.status,"
					+ "r.createTime"
					+ ",p.positionName,"
					+ "c.advertisersName ) "+
					" from DResource r,DAdPosition p,Customer c "+
					"where r.positionCode=p.positionCode and r.customerId = c.id";
		if(null != meterialQuery){
			 if(null != meterialQuery.getCreateTime() && !"".equals(meterialQuery.getCreateTime())){
				 sql += " and r.createTime = '" + meterialQuery.getCreateTime() + "'";
			 }
			 if(StringUtils.isNotBlank(meterialQuery.getResourceName())){
				 sql += " and r.resourceName like '%" + meterialQuery.getResourceName().trim() + "%'";
			 }
			 if(StringUtils.isNotBlank(meterialQuery.getPositionName())){
				 sql += " and p.positionName like '%" + meterialQuery.getPositionName().trim() + "%'";
			 }
			 if(StringUtils.isNotBlank(meterialQuery.getAdvertisersName())){
				 sql += " and c.advertisersName like '%" + meterialQuery.getAdvertisersName().trim() + "%'";
			 }
			 if(StringUtils.isNotBlank(meterialQuery.getStatus())){
				 sql += " and r.status ='" + meterialQuery.getStatus() + "'";
			 }
		 }
		sql += " order by r.id desc";
		PageBeanDB pageResultList = this.getPageList2(sql, null, pageNo, pageSize);
		return pageResultList;
	}

	@Override
	public PageBeanDB queryPosisonList(int pageNo, int pageSize) {
		String hql = "from DAdPosition";
		int rowcount = this.getTotalCountHQL(hql, null);
		PageBeanDB page = new PageBeanDB();
		if(pageNo==0){
			pageNo=1;
		}
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1)/pageSize + 1;
		if(rowcount==0){
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List list = this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}

	public List getList(final String hql, final List params ) {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				
				Query query = session.createQuery(hql);
				if (params != null){
					for (int i = 0; i < params.size(); i++){
						query.setParameter(i, params.get(i));
					}
				}
				
					return  query.list();
				}
		});
	}

	@Override
	public void saveDResource(DResource materialTemp) {
		getHibernateTemplate().saveOrUpdate(materialTemp);
	}

	@Override
	public VideoMeta getVideoMetaByID(Integer id) {
		String hql =  " from VideoMeta t where t.id="+id;

        List list = this.getList(hql,null);
        VideoMeta videoMeta =null;
        if (list!=null && list.size()>0)
        {
            videoMeta = (VideoMeta)(list.get(0));
        }

        return videoMeta;
	}

	@Override
	public DResource getMaterialByID(int materialId) {
		String hql = "select distinct new com.avit.dtmb.material.bean.DResource(r,a.positionName,b.advertisersName)" +      
		        " from DResource r,DAdPosition a,Customer b "+
		        "where r.positionCode = a.positionCode and r.customerId=b.id and r.id="+materialId;
		        List list = this.getList(hql,null);
		        DResource material =null;
		        if (list!=null && list.size()>0)
		        {
		            material = (DResource)(list.get(0));
		            
		            int mid=material.getId();
			    	 String sql1="SELECT COUNT(1) FROM d_order_mate_rel rel, d_order o WHERE rel.RESOURCE_ID ="+mid+"  AND rel.ORDER_CODE = o.ORDER_CODE AND o.STATE <> '7'";
			    	 Query query = getSession().createSQLQuery(sql1);
			    	 List a=query.list();	    	 
			    	 /*if(a.get(0).toString()!="0"){
			    		 material.setStateStr("7");
			    	 }*/
		        }
		        
		        return material;
	}

	@Override
	public ImageMeta getImageMetaByID(Integer resourceId) {
		String hql =  " from ImageMeta t where t.id="+resourceId;

        List list = this.getList(hql,null);
        ImageMeta imageMeta =null;
        if (list!=null && list.size()>0)
        {
            imageMeta = (ImageMeta)(list.get(0));
        }

        return imageMeta;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DResource getDRsourceByName(String resourceName) {
		String hql = "from DResource res where res.resourceName = ?";
		List<DResource> list = this.list(hql, resourceName);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	

}
