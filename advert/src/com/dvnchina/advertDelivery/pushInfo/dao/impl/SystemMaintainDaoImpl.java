package com.dvnchina.advertDelivery.pushInfo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.pushInfo.bean.SystemMaintain;
import com.dvnchina.advertDelivery.pushInfo.dao.SystemMaintainDao;

public class SystemMaintainDaoImpl extends BaseDaoImpl implements
		SystemMaintainDao {

	@Override
	public SystemMaintain getAllMaintain() {
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

	@Override
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

	@Override
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseArea> getUserRelaArea(Integer userId) {
		String hql = "select area from ReleaseArea area, UserArea ua where area.areaCode = ua.releaseAreacode and ua.userId = ?";
		return this.getListForAll(hql, new Object[]{userId});
	}
			
		
	//}

}
