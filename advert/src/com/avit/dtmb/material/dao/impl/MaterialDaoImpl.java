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
import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.material.dao.MaterialDao;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
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
			 if(StringUtils.isNotBlank(meterialQuery.getCustomerName())){
				 sql += " and c.advertisersName like '%" + meterialQuery.getCustomerName().trim() + "%'";
			 }
		 }
		sql += " order by r.id desc";
		System.out.println(sql);
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

	@Override
	public VideoSpecification getVideoSpc(Integer advertPositionId) {
		String hql =  "select  new com.dvnchina.advertDelivery.position.bean.VideoSpecification(t.id,t.duration,t.fileSize) from VideoSpecification t,DAdPosition a where a.specificationId=t.id and a.id ="+advertPositionId;

        List list = this.getList(hql,null);
        VideoSpecification videoSpecification =null;
        if (list!=null && list.size()>0)
        {
            videoSpecification = (VideoSpecification)(list.get(0));
        }

        return videoSpecification;
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
	public ImageSpecification getImageMateSpeci(Integer advertPositionId) {
		String hql =  "select  new com.dvnchina.advertDelivery.position.bean.ImageSpecification(t.id,t.imageWidth,t.imageHeight,t.type,t.fileSize ) from ImageSpecification t,DAdPosition a where a.specificationId=t.id and a.id ="+advertPositionId;

        List list = this.getList(hql,null);
        ImageSpecification imageSpecification =null;
        if (list!=null && list.size()>0)
        {
            imageSpecification = (ImageSpecification)(list.get(0));
        }

        return imageSpecification;
	}
	

}
