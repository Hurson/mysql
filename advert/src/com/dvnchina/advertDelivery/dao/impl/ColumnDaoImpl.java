package com.dvnchina.advertDelivery.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.dvnchina.advertDelivery.dao.ColumnDao;
import com.dvnchina.advertDelivery.model.Column;
@SuppressWarnings("unchecked")
public class ColumnDaoImpl  extends BaseDaoImpl implements ColumnDao {

	@Override
	public void deleteColumn(String columnId) {
		
	  String hql = "delete from Column where id = :id"; //hql语句
	  getSession().createQuery(hql).setInteger("id", Integer.valueOf(columnId)).executeUpdate();

	}

	@Override
	public List<Column> getAllColumnList() {
		String hql = "from Column ";
		List<Column> columnList = list(hql, null);
		
		
			
		return columnList;
	}

	@Override
	public List<Column> getAllColumnList(int begin, int pageSize) {
		String hql = "from Column ";
		
		List<Column> columnList = list(hql, begin, pageSize);
		return columnList;
	}

	@Override
	public List<Column> getChildColumnList(String columnPId) {
		String sql = "select * from t_columns t start with id="+columnPId+" connect by prior id = parent_id ";
		Query query = getSession().createSQLQuery(sql).addEntity(Column.class);
		List<Column> lc = query.list();
		return lc;
	}

	
	@Override
	public List<Column> getColumnList(int columnPId,int level) {
			List<Column> lc;
			try {
				String sql = "call getColumnChildList(?,?)";
				List<Column> columnList = getColumnList(this.getDataBySql(sql, new Object[]{columnPId,null}));
				return columnList;
				
				//String sql = "select * from t_columns t start with COLUMN_ID="+columnPId+" connect by prior COLUMN_ID = parent_id and level <= "+level+" order by level,column_id asc";
//				String sql = "call getColumnChildList("+columnPId+",null)";
//				Query query = getSession().createSQLQuery(sql).addEntity(Column.class);
//				lc = query.list();
//				return lc;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
	}
	
	private List<Column> getColumnList(List<?> resultList) {
		List<Column> list = new ArrayList<Column>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Column column = new Column();
			column.setId(toInteger(obj[0]));
			column.setName((String)obj[1]);
			column.setColumnCode((String)obj[2]);
			column.setParentId(toInteger(obj[3]));
			list.add(column);
		}
		return list;
	}

	@Override
	public List<Column> queryColumnByUserId(String userId) {
		//String sql = "select t.* from t_columns t where t.column_id in(select cr.column_id from t_columns_role cr where cr.role_id in (select ur.role_id from t_user_role ur  where  ur.user_id ="+userId+"))";
		String sql = "select t.* from t_columns t,  t_columns_role cr, t_user_role ur where t.column_id = cr.column_id and cr.role_id = ur.role_id and ur.user_id ="+userId;
		List<Column> lc;
		try {
			Query query = getSession().createSQLQuery(sql).addEntity(Column.class);
			lc = query.list();
			return lc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
