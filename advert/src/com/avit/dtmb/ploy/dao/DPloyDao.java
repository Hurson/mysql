package com.avit.dtmb.ploy.dao;

import java.util.List;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.AdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;

public interface DPloyDao extends BaseDao {
	/**
	 * 
	 * @return
	 */
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize);
	
	public void saveDTMBPloy(DPloy ploy);
	
	public DPloy getDTMBPloy(Integer id);
	
	public void deleteDTMBPloy(Integer id);
	
	public List<AdPosition> queryPositionList();	
	
	public AdPosition getPositionByCode(String positionCode);
	
	

}
