package com.dvnchina.advertDelivery.warn.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelationTmp;
import com.dvnchina.advertDelivery.warn.bean.WarnInfo;
import com.dvnchina.advertDelivery.warn.dao.WarnDao;

public class WarnDaoImpl extends BaseDaoImpl implements WarnDao{

	@Override
	public List<WarnInfo> getEntityList(String areaCodes) {
		String sql = "SELECT w.id, w.time, w.content FROM t_warn_info w WHERE w.area_code in(" + areaCodes + ") and w.is_processed = 0 ORDER BY w.time DESC limit 0,5";		
		List<?> resultList = this.getDataBySql(sql, null);
		List<WarnInfo> entityList = new ArrayList<WarnInfo>();
		for(Object obj : resultList){
			Object[] objs = (Object[])obj;
			WarnInfo entity = new WarnInfo();
			entity.setId((toInteger(objs[0])));
			entity.setTime((Date)objs[1]);
			entity.setContent((String)objs[2]);
			entityList.add(entity);
		}
		return entityList;
	}

	@Override
	public void deleteWarnInfo(Integer id) {
		String sql = "UPDATE t_warn_info SET is_processed = 1 WHERE id = " + id.intValue();
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	@Override
	public PageBeanDB queryWarning(String areaCodes, int pageNo, int pageSize) {
		String sql = "SELECT w.id, w.time, w.content FROM t_warn_info w WHERE w.area_code in(" + areaCodes + ") and w.is_processed = 0 ORDER BY w.time DESC";
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
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
		List<WarnInfo> list = null;
		try {
			list = getWarnInfoList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		page.setDataList(list);
		return page;
	}
	private List<WarnInfo> getWarnInfoList(List<?> resultList) throws ParseException {
		List<WarnInfo> list = new ArrayList<WarnInfo>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			WarnInfo wi = new WarnInfo();
			wi.setId(toInteger(toInteger(obj[0])));
			String time=(obj[1]).toString();
			Date d;
			String model = "yyyy-MM-dd HH:mm:ss" ;
			SimpleDateFormat sdf1 = new SimpleDateFormat(model);
			d=sdf1.parse(time);
			wi.setTime(d);
			wi.setContent(obj[2].toString());
			list.add(wi);
		}
		return list;
	}

	@Override
	public void deleteWarnInfo(String ids) {
		String sql = "UPDATE t_warn_info SET is_processed = 1  where id in ("+ids+")";
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		
	}

}
