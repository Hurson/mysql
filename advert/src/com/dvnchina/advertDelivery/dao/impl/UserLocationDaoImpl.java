package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.UserLocationDao;
import com.dvnchina.advertDelivery.model.Location;

public class UserLocationDaoImpl extends BaseDaoImpl implements UserLocationDao{

	@Override
	public boolean deleteUserLocationBanding(Integer userId) {
		
		try {
			String hql = "delete UserLocation ul where ul.userId =:userId";
			this.getSession().createQuery(hql).setInteger("userId", userId).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<Location> getUserOwnLocation(Integer userId){
		
		String sql = "select t.* from T_RELEASE_AREA t ,t_user_area ua where t.AREA_CODE = ua.release_areacode and ua.user_id ="+userId;
		Query query = this.getSession().createSQLQuery(sql).addEntity(Location.class);
		return query.list();
	}
	
	@Override
	public List<String> getUserOwnLocationCodes(Integer userId){
		
		String sql = "select t.area_code from T_RELEASE_AREA t ,t_user_area ua where t.AREA_CODE = ua.release_areacode and ua.user_id ="+userId;
		Query query = this.getSession().createSQLQuery(sql).addScalar("area_code",Hibernate.STRING);
		return query.list();
	}
	//广告商获取绑定区域
	@Override
	public List<String> getUserOwnLocationCodes2(Integer userId){
		
		String sql = "select distinct ca.area_code from t_contract_area ca, t_contract c, t_user_adcustomer tu where  ca.contract_id = c.id and c.custom_id = tu.cutomer_id and c.approval_end_date > current_date and tu.user_id ="+userId;
		Query query = this.getSession().createSQLQuery(sql).addScalar("area_code",Hibernate.STRING);
		return query.list();
	}
	@Override
	public List<Integer> getAccessUserIdList(Integer userId){
		String sql ="select DISTINCT(t.user_id) from t_user_area t where t.user_id not in(" 
					+"select distinct(t1.user_id) from t_user_area t1 where t1.release_areacode not in ( select t2.release_areacode from t_user_area t2 where t2.user_id="+userId+"))";
		Query query = this.getSession().createSQLQuery(sql).addScalar("user_id",Hibernate.INTEGER);
		
		return query.list();
	}
	@Override
	public PageBeanDB queryAllLocations(int pageNo, int pageSize){

		String hql = "from Location";
		
		int rowcount = this.getTotalCountHQL(hql, null);
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
		
		List list = this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}

}
