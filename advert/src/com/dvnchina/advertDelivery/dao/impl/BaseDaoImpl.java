package com.dvnchina.advertDelivery.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dvnchina.advertDelivery.dao.BaseDao;

@SuppressWarnings("unchecked")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	@Override
	public Integer save(Object obj) {
		return (Integer) getHibernateTemplate().save(obj);
	}

	@Override
	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}
	
	public Collection saveAll(Collection entities) {
		this.getHibernateTemplate().saveOrUpdateAll(entities);
		return entities;
	}

	public void deleteAll(Collection entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}
	
	/**
	 * 执行HQL语句，获取总记录数
	 * @param hql HQL语句
	 * @param values 参数
	 * @return int 总记录数
	 */
	public int getTotalCountHQL(final String hql, final Object[] values) {
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session) throws HibernateException, SQLException{
				int sql_from = hql.indexOf("from");
				String countStr = "select count(*) " + hql.substring(sql_from);

				Query query = session.createQuery(countStr);
				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				List<?> list = query.list();
				if (!list.isEmpty() && list.size()>0) {
					try {
						return ((Long)list.get(0)).intValue();
					} catch (Exception ex) {
						ex.printStackTrace();
						return 0;
					}
				} else{
					return 0;
				}
			}
		});
	}
	
	public int getTotalCountSQL(String sql, Object[] values) {
        String countSql = "select count(*) from (" + sql + ") tot";
        Query query = getSession().createSQLQuery(countSql);
        
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
            	query.setParameter(i, values[i]);
            }
        }
		List<?> list = query.list();
		if (list.size()>0) {
			try {
				return toInteger(list.get(0)).intValue();
			} catch (Exception ex) {
				ex.printStackTrace();
				return 0;
			}
		} else{
			return 0;
		}
	}
	
	public int getTotalCountSQL2(String sql, List list) {
        String countSql = "select count(*) from (" + sql + ") tot";
        Query query = getSession().createSQLQuery(countSql);
        
        if (list != null && list.size()>0) {
        	int size = list.size();
            for (int i = 0; i < size; i++) {
            	query.setParameter(i, list.get(i));
            }
        }
		List<?> retuenList = query.list();
		if (retuenList.size()>0) {
			try {
				return toInteger(retuenList.get(0)).intValue();
			} catch (Exception ex) {
				ex.printStackTrace();
				return 0;
			}
		} else{
			return 0;
		}
	}
	
	/**
	 * BigDecimal to Integer
	 * @param obj
	 * @return Integer
	 */
	public Integer toInteger(Object obj) {
		Integer value = null;
		if (obj != null) {
			if (obj instanceof BigDecimal) {
				value = ((BigDecimal) obj).intValue();
			} else if (obj instanceof BigInteger) {
				value = ((BigInteger) obj).intValue();
			} else if (obj instanceof Integer) {
				value = ((Integer) obj).intValue();
			}
		}
		return value;
	}
	
	/**
	 * BigDecimal to Long
	 * @param obj
	 * @return Long
	 */
	public Long toLong(Object obj) {
		Long value = null;
		if (obj != null) {
			if (obj instanceof BigDecimal) {
				value = ((BigDecimal) obj).longValue();
			} else if (obj instanceof BigInteger) {
				value = ((BigInteger) obj).longValue();
			} else if (obj instanceof Integer) {
				value = ((Integer) obj).longValue();
			} else if (obj instanceof Double) {
				value = ((Double) obj).longValue();
			}
		}
		return value;
	}
	
	@Override
	public List listByPage(final String hql, final int begin, final int pageSize) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session sess)
					throws HibernateException, SQLException {
				Query q = sess.createQuery(hql);
				q.setFirstResult(begin);
				q.setMaxResults(pageSize);
				return q.list();
			}
		});
	}
	
	/**
	 * 执行HQL语句，获取分页记录
	 * @param hql HQL语句
	 * @param values 参数
	 * @param page 当前页码
	 * @param pageSize 每页大小
	 * @return List<?>
	 */
	public List<?> getListForPage(final String hql, final Object[] values,final int page, final int pageSize){
		return this.getHibernateTemplate().execute(new HibernateCallback<List<?>>(){
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException{
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
				return query.list();
			}
		});
	}

	@Override
	public List list(String hql,Object... params) {
		return getHibernateTemplate().find(hql, params);
	}
	
	@Override
	public Object get(String hql, Object... params) {
		if (getHibernateTemplate().find(hql, params).size() != 0) {
			return getHibernateTemplate().find(hql, params).get(0);
		}
		return null;
	}

	@Override
	public Object get(Class c, Integer id) {
		return getHibernateTemplate().get(c, id);
	}

	@Override
	public int getCount(String hql) {
		return getHibernateTemplate().find(hql).size();
	}
	@Override
	public void deleteObjectById(String hql ,Integer id) {
		getSession().createQuery(hql).setInteger("id", Integer.valueOf(id)).executeUpdate();
		
	}
	@Override
	public void deleteObj(String objName ,Integer id) {
		String hql = "delete from "+objName+" where id = :id"; //hql语句
		getSession().createQuery(hql).setInteger("id", id).executeUpdate();
		
	}
	
    /**
	  * @param sql  sql语句
	 * @param values 参数
	 * 根据SQL语句获取列表
	 */
   public List getDataBySql(String sql, final Object[] values) {
       Query qury = getSession().createSQLQuery(sql);
       if (values != null) {
           for (int i = 0; i < values.length; i++) {
               qury.setParameter(i, values[i]);
           }
       }
       List qList = qury.list();
       return qList;
   }
   
   public List<?> getListBySql(final String sql, final Object[] values, final int page, final int pageSize) {
	    
	     return (List<?>)this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
         public Object doInHibernate(Session session) throws HibernateException, SQLException{
             Query query = session.createSQLQuery(sql);

             if (values != null){
                 for (int i = 0; i < values.length; i++){
                     query.setParameter(i, values[i]);
                 }
             }
             if (page > -1 && pageSize > -1) {
                 query.setFirstResult((page - 1) * pageSize);
                 query.setMaxResults(pageSize);
             }
             
             return query.list();
         }
     });
	     
	}
   
   public List<?> getListBySql2(final String sql, final List list, final int page, final int pageSize) {
	    
	     return (List<?>)this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
       public Object doInHibernate(Session session) throws HibernateException, SQLException{
           Query query = session.createSQLQuery(sql);

           if (list != null && list.size()>0){
        	   int size = list.size();
               for (int i = 0; i < size; i++){
                   query.setParameter(i, list.get(i));
               }
           }
           if (page > -1 && pageSize > -1) {
               query.setFirstResult((page - 1) * pageSize);
               query.setMaxResults(pageSize);
           }
           
           return query.list();
       }
   });
	     
	}
   
   public int executeByHQL(final String hql, final Object[] values){
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createQuery(hql);

				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}
   
   public int executeBySQL(final String sql, final Object[] values){
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session) throws HibernateException, SQLException{
				Query query = session.createSQLQuery(sql);

				if (values != null){
					for (int i = 0; i < values.length; i++){
						query.setParameter(i, values[i]);
					}
				}
				return query.executeUpdate();
			}
		});
	}
   
   /**
    * 不分页，全部查询。
    * @param hql
    * @param values
    * @return
    */
   public List getListForAll(final String hql, final Object[] values){
       return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
           public Object doInHibernate(Session session) throws HibernateException, SQLException{
               //Query query = session.getNamedQuery(namedQuery);
               Query query = session.createQuery(hql);
               if (values != null){
                   for (int i = 0; i < values.length; i++){
                       query.setParameter(i, values[i]);
                   }
               }
               return query.list();
           }
       });
       
   }
}
