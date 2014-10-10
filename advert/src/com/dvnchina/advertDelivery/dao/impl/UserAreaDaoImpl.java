package com.dvnchina.advertDelivery.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.dvnchina.advertDelivery.bean.UserReleaseArea.UserReleaseAreaBean;
import com.dvnchina.advertDelivery.constant.UserAreaConstant;
import com.dvnchina.advertDelivery.dao.UserAreaDao;
import com.dvnchina.advertDelivery.model.UserArea;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class UserAreaDaoImpl extends HibernateSQLTemplete implements UserAreaDao {
	@Override
	public int deleteUserAreaInfo(UserArea userArea) {
		
		logger.debug("------getChannelInfoById()-----start----");
		int count = 0;
		if(userArea != null && !"".equals(userArea)){
			this.getHibernateTemplate().delete(userArea);
			count = 1;
		}
		return count;
		
	}

	@Override
	public UserArea getUserAreaById(Integer id) {
		logger.debug("------getUserAreaById()-----start----");
		return  this.getHibernateTemplate().get(UserArea.class, id);
	}
	
	

	@Override
	public int getUserAreaCount(UserArea userArea, String state) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = 0;
		
		String hql = "select count(*) from UserArea where 1=1";
		hql += fillCond(map,userArea,state);
		count = this.getTotalByHQL(hql, map);
		
		if(count != 0){
			System.out.println(count);
		}
		return count;
		
	}

	@Override
	public List<UserArea> listUserAreaMgr(UserArea userArea, int x, int y,String state) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<UserArea> listUserArea = null;
		String hql = "from UserArea where 1=1"; 
		hql += fillCond(map,userArea,state);
		listUserArea =(List<UserArea>)this.findByHQL(hql, map,x,y);
		
		return listUserArea;
	}

	private String fillCond(Map<String, Object> map, UserArea userArea,String state) {
		
		StringBuffer sb = new StringBuffer("");
		
		if(StringUtils.isNotBlank(userArea.getUserIdStr())){
			map.put("userId",Integer.parseInt(userArea.getUserIdStr()));
			sb.append(" AND userId =:userId");
		}
		
		if(StringUtils.isNotBlank(userArea.getReleaseAreacode())){
			map.put("releaseAreacode","%" +userArea.getReleaseAreacode()+ "%");
			sb.append(" AND releaseAreacode like:releaseAreacode");
		}
		
		sb.append(" order by id desc ");
		
		return sb.toString();
	}

	@Override
	public UserArea getUserAreaByUserId(Integer userId) {
		List<UserArea> listUserArea = null;
		UserArea userArea = null;
		String hql = "from UserArea as tu where tu.userId="+userId;
		listUserArea = this.getHibernateTemplate().find(hql);
		
		if(listUserArea != null && listUserArea.size()>0){
			userArea = listUserArea.get(0);
		}else{
			userArea = null;
		}
		return userArea;
	}

	
	
	@Override
	public List<UserReleaseAreaBean> getUserAndReleaseAreaList(Map map) {
		String userName = (String) map.get(UserAreaConstant.USER_NAME);
		String releaseArea = (String) map.get(UserAreaConstant.RELEASE_AREA);
		
		StringBuffer sb = new StringBuffer();
		sb.append("select u.*,ra.* from T_USER u , T_RELEASE_AREA ra, T_USER_AREA ua where 1=1 and u.user_ID = ua.user_ID and ra.id  = ua.RELEASE_AREACODE");
		
		if(userName != null){
			sb.append(" and ");
			sb.append(" u.user_NAME ");
			sb.append(" like ");
			sb.append("'%"+userName+"%'");
		}
		
		if(releaseArea != null){
			sb.append(" and ");
			sb.append(" ra.AREA_NAME ");
			sb.append(" like ");
			sb.append("'%"+releaseArea+"%'");
		}
		
		sb.append(" order by u.modify_time desc");
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		
		if(query.list() == null || query.list().size()==0){
			return null;
		}else{
			List<UserReleaseAreaBean> listuserReleaseAreaBean= new ArrayList<UserReleaseAreaBean>();
			
		//	List<UserArea> listuserReleaseAreaBean= new ArrayList<UserArea>();
			for(int i = 0 ; i < query.list().size();i++){
				Object[] obj = (Object[]) query.list().get(i);
				listuserReleaseAreaBean.add(objToReleaseAreaBean(obj));
			}
			return listuserReleaseAreaBean;
		}
	}

	private UserReleaseAreaBean objToReleaseAreaBean(Object[] obj) {
		
		UserReleaseAreaBean userReleaseAreaBean = new UserReleaseAreaBean();
		
		userReleaseAreaBean.setUserId(obj[0]+"");
		userReleaseAreaBean.setUsername(obj[1]+"");
		userReleaseAreaBean.setLoginname(obj[2]+"");
		userReleaseAreaBean.setPassword(obj[3]+"");
		userReleaseAreaBean.setEmail(obj[4]+"");
		
		if(obj[5] == null){
			obj[5]="";
		}
		
		if(obj[6] == null){
			obj[6]="";
		}
		
		userReleaseAreaBean.setCreateTime(obj[5]+"");
		userReleaseAreaBean.setModifyTime(obj[6]+"");
		userReleaseAreaBean.setStatus(obj[7]+"");
		userReleaseAreaBean.setReleaseAreaId(obj[8]+"");
		
		userReleaseAreaBean.setAreaCode(obj[9]+"");
		userReleaseAreaBean.setAreaName(obj[10]+"");
		userReleaseAreaBean.setPrentCode(obj[11]+"");
		userReleaseAreaBean.setLocationType(obj[12]+"");
		
		return userReleaseAreaBean;
	}

	@Override
	public List<UserReleaseAreaBean> getUserAndReleaseAreaList(Map map,final int first, final int pageSize) {
	//	return this.queryUserAndReleaseAreaList(map,first,pageSize);
		
		int count= 0;
		
		String userName = (String) map.get(UserAreaConstant.USER_NAME);
		String releaseArea = (String) map.get(UserAreaConstant.RELEASE_AREA);
		
		final StringBuffer sb = new StringBuffer();
//		sb.append("select u.*,ra.* from T_USER u , T_RELEASE_AREA ra, T_USER_AREA ua where 1=1 and u.user_ID = ua.user_ID and ra.id  = ua.RELEASE_AREACODE");
		
		sb.append("select * from( select u.*,ra.*,rownum rn from T_USER u , T_RELEASE_AREA ra, T_USER_AREA ua where 1=1 and u.user_ID = ua.user_ID and ra.id  = ua.RELEASE_AREACODE");
		
		if(userName != null){
			sb.append(" and ");
			sb.append(" u.user_NAME ");
			sb.append(" like ");
			sb.append("'%"+userName+"%'");
		}
		
		if(releaseArea != null){
			sb.append(" and ");
			sb.append(" ra.AREA_NAME ");
			sb.append(" like ");
			sb.append("'%"+releaseArea+"%'");
		}
		
		sb.append(" and rownum <= ");
		sb.append(first+pageSize);
		sb.append(" )");
		sb.append(" where");
		sb.append(" rn >");
		sb.append(first);
		
	//	sb.append(" order by u.modify_time desc");
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		
		if(query.list() == null || query.list().size()==0){
			return null;
		}else{
			List<UserReleaseAreaBean> listuserReleaseAreaBean= new ArrayList<UserReleaseAreaBean>();
			for(int i = 0 ; i < query.list().size();i++){
				Object[] obj = (Object[]) query.list().get(i);
				listuserReleaseAreaBean.add(objToReleaseAreaBean(obj));
			}
			return listuserReleaseAreaBean;
		}
		
		
	}

	
}






