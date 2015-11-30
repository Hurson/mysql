package com.avit.dtmb.ploy.dao;

import com.dvnchina.advertDelivery.bean.PageBean;
import com.dvnchina.advertDelivery.dao.BaseDao;

public interface DPloyDao extends BaseDao {
	/**
	 * 
	 * @return
	 */
	PageBean queryPloyList();

}
