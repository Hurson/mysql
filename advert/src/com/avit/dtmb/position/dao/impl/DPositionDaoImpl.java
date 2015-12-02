package com.avit.dtmb.position.dao.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.position.dao.DPositionDao;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
@Repository("DPositionDao")
public class DPositionDaoImpl extends BaseDaoImpl implements DPositionDao {

	@Override
	public PageBeanDB queryDPositionList(DAdPosition dAdPosition, int pageNo,
			int pageSize) {
		String hql = "from dAdPosition";
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
	public Integer save(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection saveAll(Collection entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Collection entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObjectById(String hql, Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObj(String objName, Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalCountHQL(String hql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCountSQL(String sql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer toInteger(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long toLong(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listByPage(String hql, int begin, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(String hql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String hql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Class c, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount(String hql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<?> getListForPage(String hql, Object[] values, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getDataBySql(String sql, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getListBySql(String sql, Object[] values, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int executeByHQL(String hql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeBySQL(String sql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getListForAll(String hql, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
