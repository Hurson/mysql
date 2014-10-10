package com.dvnchina.advertDelivery.meterial.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.meterial.dao.DefaultMeterManageDao;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;

public class DefaultMeterManageDaoImpl extends BaseDaoImpl implements
		DefaultMeterManageDao {

	@Override
	public PageBeanDB queryMeterialList(Resource meterialQuery, int pageSize,
			int pageNo) {
		String sql = "SELECT DISTINCT new com.dvnchina.advertDelivery.model.Resource("
				+ "r.id, r.resourceId,r.resourceName,r.resourceType,r.categoryId,r.createTime,r.resourceDesc,r.keyWords, "
				+ "a.id, a.positionName) "
				+ "from AdvertPosition a, Resource r " 
				+ "where r.advertPositionId=a.id " 
				+ "AND r.isDefault = 1 ";

		sql = this.addQueryCondition(sql, meterialQuery);
		sql = sql + " order by r.id desc";
		// 分页查询
		int rowcount = this.getTotalCountHQL(sql, null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
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
		List<Resource> list = this.getListForPages(sql, null, pageNo, pageSize);
		list = getUsedOfResource(list);
		page.setDataList(list);
		return page;
	} 
	
	private List<Resource> getUsedOfResource(List<Resource> list){
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		for (Resource resource : list) {
			String hqlUpdate = "select resource_id from t_resource_ad where resource_id = " +resource.getId();
			Query query = session.createSQLQuery(hqlUpdate);
			if(query.list() != null && query.list().size() > 0){
				resource.setUsed(1);
			}
		}
		ts.commit();
		session.close();
		return list;
		
	}
	
	/**
	 * 添加查询条件
	 * @param hql
	 * @param queryCondition
	 * @return hql
	 */
	private String addQueryCondition(String hql, Resource meterialQuery) {
		// 如果没有查询条件直接返回
		if(meterialQuery == null){
			return hql;
		}
		// 添加台账订单号查询条件
		String resourceName = meterialQuery.getResourceName();
		if(resourceName != null && !resourceName.isEmpty()){
			hql += " and r.resourceName like '%"+resourceName+"%'";
		}
		if (meterialQuery!=null && meterialQuery.getPositionIds()!=null && !"".equals(meterialQuery.getPositionIds()))
        {
			hql += " and a.id in ("+meterialQuery.getPositionIds()+")";
        }
		 if (meterialQuery!=null && meterialQuery.getCustomerId()!=null )
        {
			 hql +=" and r.customerId = "+meterialQuery.getCustomerId();
        }
		return hql;
	}


	/**
	 * 根据ID查询素材
	 */
	public Resource getMaterialByID(int materialId) {
		Session session = this.getSession();
		
		String hql = "select distinct new com.dvnchina.advertDelivery.model.Resource(" +
        "r.id,r.resourceId,r.resourceName,r.resourceType,r.categoryId,r.createTime,r.resourceDesc,r.keyWords," +
        "a.id,a.positionName,-1)" +      
        " from Resource r,AdvertPosition a "+
        "where r.advertPositionId=a.id and  r.id="+materialId;
		Query query = session.createQuery(hql);
        List list = query.list();
        Resource material =null;
        if (list!=null && list.size()>0)
        {
            material = (Resource)(list.get(0));
        }
		return material;
	}


	/**
	 * 删除素材
	 */
	public boolean deleteMaterialByIds(String id) {
		boolean flag = true;
		try{
			Session session = this.getSessionFactory().openSession();
			Transaction ts = session.beginTransaction();
			String hqlUpdate = "DELETE from T_RESOURCE_BACKUP WHERE ID IN" +id;
			Query query = session.createSQLQuery(hqlUpdate);
			query.executeUpdate();
			
			String hqlUpdate2 = "DELETE from T_RESOURCE WHERE ID IN" +id;
			Query query2 = session.createSQLQuery(hqlUpdate2);
			query2.executeUpdate();
			ts.commit();
			session.close();
		
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public List<Resource> getListForPages(final String hql, final Object[] values,final int page, final int pageSize){
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Resource>>(){
			public List<Resource> doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createQuery(hql);
				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				if(page != -1 && pageSize != -1){
					int start = (page-1) * pageSize;
					query.setFirstResult(start);
					query.setMaxResults(pageSize);
				}
				return  query.list();
			}
		});
	}

	@Override
	public void saveTextMaterialReal(MessageReal textMetaReal) {
		 this.save(textMetaReal);
		
	}

	@Override
	public void updateTextMaterialReal(MessageReal textMetaReal) {
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "UPDATE T_TEXT_META H SET H.NAME = :name " +
				", H.CONTENT = :content " +
				", H.URL = :url " +
				", H.ACTION = :action " +
//				", H.DURATION_TIME = :time " +
				", H.FONT_SIZE = :size " +
				", H.FONT_COLOR = :color " +
				", H.POSITION_VERTEX_COORDINATES = :position " +
				"WHERE H.ID = :id";
		Query query = session.createSQLQuery(hqlUpdate);
		query.setString("name", textMetaReal.getName());
		query.setBinary("content", textMetaReal.getContent());
		query.setString("url", textMetaReal.getURL());
		query.setString("action", textMetaReal.getAction());
//		if(textMetaReal.getDurationTime() != null){
//			query.setInteger("time", textMetaReal.getDurationTime());
//		}
		
			query.setInteger("size", textMetaReal.getFontSize());
		
		
		query.setString("color", textMetaReal.getFontColor());
		query.setString("position", textMetaReal.getPositionVertexCoordinates());
		query.setInteger("id", textMetaReal.getId());
		query.executeUpdate();
		
		ts.commit();
		session.close();
	}
	
	public void updateId(MessageReal textMetaReal){
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "INSERT INTO T_TEXT_META (ID, NAME, CONTENT, URL, ACTION, DURATION_TIME, FONT_SIZE, FONT_COLOR, BACKGROUND_COLOR, ROLL_SPEED, POSITION_VERTEX_COORDINATES, POSITION_WIDTH_HEIGHT) values " +
				"(:id, :name, :content, :url, :action, :time, :size, :color, :color2, :speed, :position, :width)";
				
		Query query = session.createSQLQuery(hqlUpdate);
		query.setInteger("id", textMetaReal.getId());
		query.setString("name", textMetaReal.getName());
		query.setBinary("content", textMetaReal.getContent());
		query.setString("url", textMetaReal.getURL());
		query.setString("action", textMetaReal.getAction());
	    if (textMetaReal.getDurationTime()==null)
	    {
	    	textMetaReal.setDurationTime(0);
	    }
		query.setInteger("time", textMetaReal.getDurationTime());
		query.setInteger("size", textMetaReal.getFontSize());
		query.setString("color", textMetaReal.getFontColor());
		query.setString("color2", textMetaReal.getBkgColor());
		query.setInteger("speed", textMetaReal.getRollSpeed());
		query.setString("position", textMetaReal.getPositionVertexCoordinates());
		query.setString("width", textMetaReal.getPositionWidthHeight());
		query.executeUpdate();
		ts.commit();
		session.close();
	}

	
}
