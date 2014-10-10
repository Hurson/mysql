package com.dvnchina.advertDelivery.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.report.bean.AdvertOrderReport;
import com.dvnchina.advertDelivery.report.bean.OrderBean;
import com.dvnchina.advertDelivery.report.dao.AdvertReportDao;

/**
 * @ClassName AdvertReportDaoImpl
 * @Description
 * @author marui
 * @date 2013-12-05
 */
public class AdvertReportDaoImpl extends BaseDaoImpl implements AdvertReportDao {

	@Override
	public PageBeanDB queryAdvertReportList(OrderBean order, int pageNo,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tr.id, tr.position_code, tr.resource_id, date_format(tr.push_date,'%Y-%m-%d') as pushDate, count(tr.resource_id) as push_count, sum(tr.receive_count), ta.position_name, tre.resource_name, tca.category_name, tc.advertisers_name");
		sql.append(" from t_report_data tr, t_advertposition ta, t_resource tre, t_customer tc, t_category tca, t_order o");
		sql.append(" where tr.position_code=ta.position_code");
		sql.append(" and tr.resource_id=tre.id");
		sql.append(" and tre.customer_id=tc.id");
		sql.append(" and tre.category_id=tca.id");
		sql.append(" and o.id=tr.order_id");
		
//		sql.append(" and date_format(o.end_time,'%Y-%m-%d') = '" + orderEndTime + "'");
		if (order != null) {
			if (order.getId() != null) {
				sql.append(" and o.id=").append(order.getId());
			}
		}
		sql.append(" group by pushDate, tr.resource_id");
		sql.append(" order by pushDate, tr.resource_id");

		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo == 0) {
			pageNo = 1;
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

		List<AdvertOrderReport> list = getReportList(this.getListBySql(
				sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}

	@Override
	public PageBeanDB queryReqAdvertReportList(OrderBean order, int pageNo,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tr.id, tr.AD_SITE_ID, tr.CONTENT_ID, date_format(tr.DATETIME,'%Y-%m-%d') as pushDate, count(tr.CONTENT_ID) as push_count, count(tr.CONTENT_ID) as receive_count, ta.position_name, tre.resource_name, tca.category_name, tc.advertisers_name");
		sql.append(" from ad_playlist_req_history tr, t_advertposition ta, t_resource tre, t_customer tc, t_category tca, t_order o");
		sql.append(" where tr.AD_SITE_ID=ta.position_code");
		sql.append(" and tr.CONTENT_ID=tre.id");
		sql.append(" and tre.customer_id=tc.id");
		sql.append(" and tre.category_id=tca.id");
		sql.append(" and o.id=tr.order_id");
		sql.append(" and tr.state = 1");
		
//		sql.append(" and date_format(o.end_time,'%Y-%m-%d') = '" + orderEndTime + "'");
		if (order != null) {
			if (order.getId() != null) {
				sql.append(" and o.id=").append(order.getId());
			}
		}
		sql.append(" group by pushDate,tr.CONTENT_ID");
		sql.append(" order by pushDate,tr.CONTENT_ID");
		logger.info("reqsql:"+sql.toString());
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo == 0) {
			pageNo = 1;
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

		List<AdvertOrderReport> list = getReportList(this.getListBySql(
				sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<AdvertOrderReport> getReportList(List<?> resultList) {
		List<AdvertOrderReport> list = new ArrayList<AdvertOrderReport>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			AdvertOrderReport report = new AdvertOrderReport();
			report.setId(toInteger(obj[0]));
			report.setPositionCode((String)obj[1]);
			report.setPushDate((String)obj[3]);
			report.setPushCount(toLong(obj[4]));
			report.setReceiveCount(toLong(obj[5]));
			report.setPositionName((String)obj[6]);
			report.setResourceName((String)obj[7]);
			report.setCategoryName((String)obj[8]);
			report.setAdvertisersName((String)obj[9]);

			list.add(report);
		}
		return list;
	}

	@Override
	public List<OrderBean> queryOrderList(OrderBean order) {
		String sql = "select o.id,o.order_code from t_order o where date_format(o.end_time,'%Y-%m-%d')= '"+order.getEndTime()+"'";
		logger.info("order sql="+sql);
		List list = this.getDataBySql(sql, null);
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		for (int i=0; i<list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			OrderBean orderBean = new OrderBean();
			orderBean.setId(toInteger(obj[0]));
			orderBean.setOrderCode((String)obj[1]);
			orderList.add(orderBean);
		}
		return orderList;
	}

}
