package com.avit.ads.pushads.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.task.bean.SystemMaintain;
import com.avit.ads.pushads.task.dao.SystemMaintainDao;


@Repository
public class SystemMaintainDaoImpl extends HibernateDaoSupport implements SystemMaintainDao {
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
    public SystemMaintain getAllMaintain() {
		// TODO Auto-generated method stub
		List<SystemMaintain> mainmainList=new ArrayList<SystemMaintain>();
		Session session=getSession();
		Query query=session.createQuery("from SystemMaintain");
		mainmainList=query.list();
		session.close();
		if(mainmainList.size()!=0){
			return mainmainList.get(0);
		}else{
			return null;
		}
		
	}

	
	public void sendSystemMainToUnt() {
		// TODO Auto-generated method stub
		SystemMaintain systemMaintain=this.getAllMaintain();
		//Timestamp sendTime=systemMaintain.getSendTime();
		//Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		//if(sendTime==nowTime){
			String hql="insert into system_maintain(ActiveHour,ActionCode) values(?,?)";
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			Connection conn = null;
		    PreparedStatement stmt = null;
			try {
			    conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
				stmt = conn.prepareStatement(hql);
				stmt.setInt(1, systemMaintain.getActiveHour().intValue());
				stmt.setInt(2, systemMaintain.getActionCode().intValue());
				stmt.execute();
			    conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
			      if (stmt != null) {
			          try {
			            stmt.close();
			          } catch (SQLException e) {
			            e.printStackTrace();
			          }
			        }

			        if (conn != null) {
			          try {
			            conn.close();
			          } catch (SQLException e) {
			            e.printStackTrace();
			          }
			        }

			        if (session != null)
			          session.close();
			      }
		}

	
	public SystemMaintain fin() {
		// TODO Auto-generated method stub
		List<SystemMaintain> mainmainList=new ArrayList<SystemMaintain>();
		Session session=getSession();
		Query query=session.createQuery("from SystemMaintain");
		mainmainList=query.list();
		if(mainmainList.size()!=0){
			return mainmainList.get(0);
		}else{
			return null;
		}
		
	}
	public int saveOrUpdate(SystemMaintain o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(o);
		return 0;
	}
			
		
	//}

}
