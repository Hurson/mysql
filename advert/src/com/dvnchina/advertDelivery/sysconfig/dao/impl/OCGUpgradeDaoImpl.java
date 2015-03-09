package com.dvnchina.advertDelivery.sysconfig.dao.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.sysconfig.bean.AreaOCG;
import com.dvnchina.advertDelivery.sysconfig.dao.OCGUpgradeDao;


public class OCGUpgradeDaoImpl extends BaseDaoImpl implements OCGUpgradeDao {

	@Override
	public PageBeanDB queryAreaOCGList(AreaOCG ocg, int pageNo, int pageSize) {
		
		StringBuffer hql = new StringBuffer("select new com.dvnchina.advertDelivery.sysconfig.bean.AreaOCG(ao, ra.areaName) from AreaOCG ao,ReleaseArea ra where ao.areaCode=ra.areaCode ");
		if(ocg != null){
			if(!StringUtils.isEmpty(ocg.getAreaName())){
				hql.append(" and ra.areaName like '%").append(ocg.getAreaName()).append("%' ");
			}
			
		}
		
		int rowcount = this.getTotalCountHQL(hql.toString(), null);
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
		hql.append("order by ao.id");
		List<AreaOCG> list = (List<AreaOCG>)this.getListForPage(hql.toString(), null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}

	@Override
	public void updateAreaOCG(final String version, final String ip) {
		
		 this.getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createQuery("update AreaOCG ao set ao.version=? where ao.ip =?");
				query.setParameter(0, version);
				query.setParameter(1, ip);
								
				return query.executeUpdate();
			}
		});
	}

	
}
