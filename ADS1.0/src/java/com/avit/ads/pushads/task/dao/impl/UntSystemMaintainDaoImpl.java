package com.avit.ads.pushads.task.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.task.bean.UntSystemMaintain;
import com.avit.ads.pushads.task.dao.UntSystemMaintainDao;

@Repository
public class UntSystemMaintainDaoImpl  extends HibernateDaoSupport implements
		UntSystemMaintainDao {
	@Resource(name = "sessionFactoryForUnt")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public void saveOrUpdate(Object obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(obj);
		
	}

	
	public UntSystemMaintain find() {
		// TODO Auto-generated method stub
		List<UntSystemMaintain> mainmainList=new ArrayList<UntSystemMaintain>();
		Session session=getSession();
		Query query=session.createQuery("from UntSystemMaintain");
		mainmainList=query.list();
		if(mainmainList.size()!=0){
			return mainmainList.get(0);
		}else{
			return null;
		}
		
	}

}
