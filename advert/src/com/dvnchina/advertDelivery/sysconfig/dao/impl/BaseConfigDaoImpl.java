package com.dvnchina.advertDelivery.sysconfig.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.sysconfig.bean.BaseConfig;
import com.dvnchina.advertDelivery.sysconfig.dao.BaseConfigDao;

public class BaseConfigDaoImpl extends BaseDaoImpl implements BaseConfigDao{

	/**
	 * 分页查信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryConfigList(int pageNo, int pageSize, String queryKey){
		String hql = "from BaseConfig b ";
		if(!"".equals(queryKey) && queryKey != null){
			hql += "where b.remindName like '%"+queryKey+"%'";
		}
		
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
		List<BaseConfig> list = (List<BaseConfig>)this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 根据ID获取配置项信息
	 * @param id
	 * @return BaseConfig
	 */
	public BaseConfig getBaseConfigById(int id){
		return (BaseConfig) this.get(BaseConfig.class, id);
	}
	
	/**
	 * 保存
	 * @param BaseConfig
	 * @return int
	 */
	public int saveBaseConfig(BaseConfig obj){
		return this.save(obj);
	}
	
	/**
	 * 修改
	 * @param BaseConfig
	 * 
	 */
	public void updateBaseConfig(BaseConfig obj){
		this.update(obj);
	}
	
	/**
	 * 保存
	 * @param List<BaseConfig>
	 * 
	 */
	public void deleteBaseConfig(List<BaseConfig> obj){
		this.deleteAll(obj);
	}
	
	/**
	 * 根据编码获取配置值
	 * @param List<BaseConfig>
	 * 
	 */
	public String getBaseConfigByCode(String configCode){
		Session session = this.getSession();
		String hql = "select REMIND_VALUE from ADMIN_SYSTEM_CONFIG where REMIND_KEY = :key";
		Query query = session.createSQLQuery(hql);
		query.setString("key", configCode);
		List list = query.list();
		
		if( list == null || list.size()<=0){
			return null;
		}
		String obj = (String) list.get(0);
		return  obj;
	}
}
