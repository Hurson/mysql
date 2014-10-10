package com.dvnchina.advertDelivery.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.report.bean.AdvertReport;
import com.dvnchina.advertDelivery.report.bean.QuestionReport;
import com.dvnchina.advertDelivery.report.dao.ReportDao;
import com.dvnchina.advertDelivery.utils.StringUtil;

/**
 * @ClassName ReportDaoImpl
 * @Description
 * @author panxincheng
 * @date 2013-10-28 
 */
public class ReportDaoImpl extends BaseDaoImpl implements ReportDao {


	@Override
	public PageBeanDB queryDayReportList(AdvertReport aReport, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		//sql.append("select tr.id, tr.position_code, tr.resource_id, date_format(tr.push_date,'%Y-%m-%d %H:%i:%S') as pushDate, count(tr.resource_id) as push_count, tr.receive_count, ta.position_name, tre.resource_name, tca.category_name, tc.advertisers_name,0 reach_count, 0 eff_count");
		sql.append("select tr.id, tr.position_code, tr.resource_id, date_format(tr.push_date,'%Y-%m-%d %H:%i:%S') as pushDate, count(tr.resource_id) as push_count, tr.receive_count, ta.position_name, tre.resource_name, tca.category_name, tc.advertisers_name,tr.reach_count,tr.eff_count");
		sql.append(" from t_report_data tr, t_advertposition ta, t_resource tre, t_customer tc, t_category tca");
		sql.append(" where tr.position_code=ta.position_code and (tr.position_code not in ('02072','02074','02112','02111','02122','02121'))");
		sql.append(" and tr.resource_id=tre.id");
		sql.append(" and tre.customer_id=tc.id");
		sql.append(" and tre.category_id=tca.id");
		String startDate,endDate;
		if (aReport.getPushDateStart()!=null && aReport.getPushDateStart().length()==10)
		{
			startDate=aReport.getPushDateStart()+" 00:00:00";
			//report.setPushDateStart(report.getPushDateStart()+" 00:00:00");
		}
		else
		{
			startDate=aReport.getPushDateStart();
		}
		if (aReport.getPushDateStart()!=null && aReport.getPushDateStart().length()<=10)
		{
			endDate=aReport.getPushDateStart()+" 23:59:59";
		}
		else
		{
			endDate=aReport.getPushDateStart();
		}
		
		if(aReport != null){
			if(!StringUtils.isEmpty(aReport.getPositionName())){
				sql.append(" and ta.position_name like '%").append(aReport.getPositionName().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(aReport.getPositionCode())){
				sql.append(" and ta.position_code like '%").append(aReport.getPositionCode().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
				sql.append(" and tr.push_date >= str_to_date('").append(startDate).append("','%Y-%m-%d %H:%i:%S')");
			}
			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
				sql.append(" and tr.push_date <= str_to_date('").append(endDate).append("','%Y-%m-%d %H:%i:%S')");
			}
//			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
//				sql.append(" and tr.push_date >= str_to_date('").append(aReport.getPushDateStart()).append("','%Y-%m-%d %H:%i:%S')");
//			}
//			if(!StringUtils.isEmpty(aReport.getPushDateEnd())){
//				sql.append(" and tr.push_date <= str_to_date('").append(aReport.getPushDateEnd()).append("','%Y-%m-%d %H:%i:%S')");
//			}
//			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
//				String startTime = aReport.getPushDateStart().split(" ")[0] + "00:00:00";
//				String endTime = aReport.getPushDateStart().split(" ")[0] + "23:59:59";
//				sql.append(" and tr.push_date >= str_to_date('").append(startTime).append("','%Y-%m-%d %H:%i:%S')");
//				sql.append(" and tr.push_date <= str_to_date('").append(endTime).append("','%Y-%m-%d %H:%i:%S')");
//			}
		}
		sql.append(" group by tr.position_code, pushDate, tr.resource_id");
		sql.append(" order by tr.position_code, tr.push_date, tr.resource_id");
		
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

		List<AdvertReport> list = getReportList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<AdvertReport> getReportList(List<?> resultList) {
		List<AdvertReport> list = new ArrayList<AdvertReport>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			AdvertReport report = new AdvertReport();
			report.setId(toInteger(obj[0]));
			report.setPositionCode((String)obj[1]);
			report.setPushDate((String)obj[3]);
			report.setPushCount(toLong(obj[4]));
			report.setReceiveCount(toLong(obj[5]));
			report.setPositionName((String)obj[6]);
			report.setResourceName((String)obj[7]);
			report.setCategoryName((String)obj[8]);
			report.setAdvertisersName((String)obj[9]);
			report.setReacheCount(toLong(obj[10]));
			report.setEffCount(toLong(obj[11]));
			list.add(report);
		}
		return list;
	}

	@Override
	public PageBeanDB queryWeekReportList(AdvertReport aReport, int pageNo,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tr.id, tr.position_code, tr.resource_id, date_format(tr.push_date,'%Y-%m-%d') as pushDate, count(tr.resource_id) as push_count, sum(tr.receive_count), ta.position_name, tre.resource_name, tca.category_name, tc.advertisers_name,sum(tr.reach_count),sum(tr.eff_count)");
		sql.append(" from t_report_data tr, t_advertposition ta, t_resource tre, t_customer tc, t_category tca");
		sql.append(" where tr.position_code=ta.position_code and (tr.position_code not in ('02072','02074','02112','02111','02122','02121'))");
		sql.append(" and tr.resource_id=tre.id");
		sql.append(" and tre.customer_id=tc.id");
		sql.append(" and tre.category_id=tca.id");
		
		String startDate,endDate;
		if (aReport.getPushDateStart()!=null && aReport.getPushDateStart().length()==10)
		{
			startDate=aReport.getPushDateStart()+" 00:00:00";
			//report.setPushDateStart(report.getPushDateStart()+" 00:00:00");
		}
		else
		{
			startDate=aReport.getPushDateStart();
		}
		if (aReport.getPushDateEnd()!=null && aReport.getPushDateEnd().length()<=10)
		{
			endDate=aReport.getPushDateEnd()+" 23:59:59";
		}
		else
		{
			endDate=aReport.getPushDateEnd();
		}
		if(aReport != null){
			if(!StringUtils.isEmpty(aReport.getPositionName())){
				sql.append(" and ta.position_name like '%").append(aReport.getPositionName().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(aReport.getPositionCode())){
				sql.append(" and ta.position_code like '%").append(aReport.getPositionCode().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
				sql.append(" and tr.push_date >= str_to_date('").append(startDate).append("','%Y-%m-%d %H:%i:%S')");
			}
			if(!StringUtils.isEmpty(aReport.getPushDateEnd())){
				sql.append(" and tr.push_date <= str_to_date('").append(endDate).append("','%Y-%m-%d %H:%i:%S')");
			}
//			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
//				sql.append(" and tr.push_date >= str_to_date('").append(aReport.getPushDateStart()).append("','%Y-%m-%d %H:%i:%S')");
//			}
//			if(!StringUtils.isEmpty(aReport.getPushDateEnd())){
//				sql.append(" and tr.push_date <= str_to_date('").append(aReport.getPushDateEnd()).append("','%Y-%m-%d %H:%i:%S')");
//			}
		}
		sql.append(" group by tr.position_code, pushDate, tr.resource_id");
		sql.append(" order by tr.position_code,tr.push_date,  tr.resource_id");
		
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

		List<AdvertReport> list = getReportList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}

	@Override
	public PageBeanDB queryMonthReportList(AdvertReport aReport, int pageNo,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tr.id, tr.position_code, tr.resource_id, date_format(tr.push_date,'%Y-%m') as pushDate, count(tr.resource_id) as push_count, sum(tr.receive_count), ta.position_name, tre.resource_name, tca.category_name, tc.advertisers_name,sum(tr.reach_count),sum(tr.eff_count)");
		sql.append(" from t_report_data tr, t_advertposition ta, t_resource tre, t_customer tc, t_category tca");
		sql.append(" where tr.position_code=ta.position_code and (tr.position_code not in ('02072','02074','02112','02111','02122','02121'))");
		sql.append(" and tr.resource_id=tre.id");
		sql.append(" and tre.customer_id=tc.id");
		sql.append(" and tre.category_id=tca.id");
		
		String startDate,endDate;
		if (aReport.getPushDateStart()!=null && aReport.getPushDateStart().length()==10)
		{
			startDate=aReport.getPushDateStart()+" 00:00:00";
			//report.setPushDateStart(report.getPushDateStart()+" 00:00:00");
		}
		else
		{
			startDate=aReport.getPushDateStart();
		}
		if (aReport.getPushDateEnd()!=null && aReport.getPushDateEnd().length()<=10)
		{
			endDate=aReport.getPushDateEnd()+" 23:59:59";
		}
		else
		{
			endDate=aReport.getPushDateEnd();
		}
		
		if(aReport != null){
			if(!StringUtils.isEmpty(aReport.getPositionName())){
				sql.append(" and ta.position_name like '%").append(aReport.getPositionName().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(aReport.getPositionCode())){
				sql.append(" and ta.position_code like '%").append(aReport.getPositionCode().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
				sql.append(" and tr.push_date >= str_to_date('").append(startDate).append("','%Y-%m-%d %H:%i:%S')");
			}
			if(!StringUtils.isEmpty(aReport.getPushDateEnd())){
				sql.append(" and tr.push_date <= str_to_date('").append(endDate).append("','%Y-%m-%d %H:%i:%S')");
			}
//			if(!StringUtils.isEmpty(aReport.getPushDateStart())){
//				sql.append(" and tr.push_date >= str_to_date('").append(aReport.getPushDateStart()).append("','%Y-%m-%d %H:%i:%S')");
//			}
//			if(!StringUtils.isEmpty(aReport.getPushDateEnd())){
//				sql.append(" and tr.push_date <= str_to_date('").append(aReport.getPushDateEnd()).append("','%Y-%m-%d %H:%i:%S')");
//			}
		}
		sql.append(" group by tr.position_code, pushDate, tr.resource_id");
		sql.append(" order by tr.position_code,tr.push_date,  tr.resource_id");
		
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

		List<AdvertReport> list = getReportList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
//	select c.ADVERTISERS_NAME, o.id,o.ORDER_CODE,o.START_TIME,o.END_TIME , r.RESOURCE_NAME,count(uq.QUESTIONNAIRE_id) pushcount
//	from t_order o , t_user_questionnaire uq,t_resource r,t_customer c
//	where o.ID=uq.order_id  and uq.QUESTIONNAIRE_id = r.RESOURCE_ID  and c.id=r.CUSTOMER_ID
	
	/**
	 * 查询问卷报表
	 * @param pageNo
	 * @param pageSize
	 * @return PageBeanDB
	 */
	public PageBeanDB queryQuestionReportList(QuestionReport qReport, int pageNo, int pageSize)
	{
		String searchStart="";
		String searchEnd="";
		if (qReport==null)
		{
			qReport = new QuestionReport();			
		}
		if (qReport.getPushDateStart()==null || "".equals(qReport.getPushDateStart()))
		{
			searchStart="1970-01-01";
		}
		else
		{
			searchStart=qReport.getPushDateStart();
		}
		if (qReport.getPushDateEnd()==null || "".equals(qReport.getPushDateEnd()))
		{
			searchEnd="1970-01-01";
		}
		else
		{
			searchEnd=qReport.getPushDateEnd();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select c.advertisers_name,r.RESOURCE_NAME,date_format(o.START_TIME,'%Y-%m-%d') as starttime ,date_format(o.END_TIME,'%Y-%m-%d') as endtime , count(uq.QUESTIONNAIRE_id) as pushcount,'"+StringUtil.toNotNullStr(searchStart)+"' as searchStart,'"+StringUtil.toNotNullStr(searchEnd)+"' as searchEnd,o.ORDER_CODE ");
		
		sql.append(" from t_order o , t_user_questionnaire uq,t_resource r,t_customer c ");
		sql.append(" where o.ID=uq.order_id  and uq.QUESTIONNAIRE_id = r.RESOURCE_ID  and c.id=r.CUSTOMER_ID");
		
//		sql.append("select c.advertisers_name as customername,r.RESOURCE_NAME as questionname, date_format(o.START_TIME,'%Y-%m-%d') as starttime ,date_format(o.END_TIME,'%Y-%m-%d') as endtime , count(uq.QUESTIONNAIRE_id) as pushcount,o.id,o.ORDER_CODE ");
//		sql.append(" from t_order o , t_user_questionnaire uq,t_resource r,t_customer c ");
//		sql.append(" where o.ID=uq.order_id  and uq.QUESTIONNAIRE_id = r.RESOURCE_ID  and c.id=r.CUSTOMER_ID");
//		
		if(qReport != null){
			if(!StringUtils.isEmpty(qReport.getAdvertisersName())){
				sql.append(" and c.advertisers_name like '%").append(qReport.getAdvertisersName().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(qReport.getResourceName())){
				sql.append(" and r.RESOURCE_NAME like '%").append(qReport.getResourceName().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(qReport.getPushDateStart())){
				sql.append(" and uq.create_time >= str_to_date('").append(qReport.getPushDateStart()).append("','%Y-%m-%d')");
			}
			if(!StringUtils.isEmpty(qReport.getPushDateEnd())){
				sql.append(" and uq.create_time <= str_to_date('").append(qReport.getPushDateEnd()).append("','%Y-%m-%d')");
			}
		}
		sql.append(" group by  c.advertisers_name,r.RESOURCE_NAME");
		
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

		List<QuestionReport> list ;
		List tempList= this.getListBySql(sql.toString(), null, pageNo, pageSize);
//		if (tempList!=null && tempList.size()==1)
//		{
//			Object[] obj = (Object[]) tempList.get(0);
//			//如果查出投放次数为0则为无问卷回答
//			if (toInteger(obj[4])==0)
//			{
//				sql= new StringBuffer();
//				sql.append("select c.advertisers_name,r.RESOURCE_NAME,date_format(o.START_TIME,'%Y-%m-%d') as starttime ,date_format(o.END_TIME,'%Y-%m-%d') as endtime , 0 as pushcount,"+qReport.getPushDateStart()+" as searchStart,"+qReport.getPushDateEnd()+" as searchEnd,o.id,o.ORDER_CODE ");
//				sql.append(" from t_order o , t_order_mate_rel uq,t_resource r,t_customer c ");
//				sql.append(" where o.ID=uq.order_id  and uq.MATE_ID = r.ID  and c.id=r.CUSTOMER_ID");
//				
//				if(qReport != null){
//					if(!StringUtils.isEmpty(qReport.getAdvertisersName())){
//						sql.append(" and c.ADVERTISERS_NAME like '%").append(qReport.getAdvertisersName().trim()).append("%' ");
//					}
//					if(!StringUtils.isEmpty(qReport.getResourceName())){
//						sql.append(" and r.RESOURCE_NAME like '%").append(qReport.getResourceName().trim()).append("%' ");
//					}
//					if(!StringUtils.isEmpty(qReport.getPushDateStart())){
//						sql.append(" and o.START_TIME >= str_to_date('").append(qReport.getPushDateStart()).append("','%Y-%m-%d')");
//					}
//					if(!StringUtils.isEmpty(qReport.getPushDateEnd())){
//						sql.append(" and o.START_TIME <= str_to_date('").append(qReport.getPushDateEnd()).append("','%Y-%m-%d')");
//					}
//				}
//				sql.append(" order by  o.START_TIME");
//				tempList = this.getListBySql(sql.toString(), null, pageNo, pageSize);
//			}
//		}
		list = getQuestionReportList(tempList);
		page.setDataList(list);
		return page;
	}
	
	private List<QuestionReport> getQuestionReportList(List<?> resultList) {
		List<QuestionReport> list = new ArrayList<QuestionReport>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			QuestionReport report = new QuestionReport();
			
			report.setAdvertisersName((String)obj[0]);
			report.setResourceName((String)obj[1]);
			report.setPushDateStart((String)obj[2]);
			report.setPushDateEnd((String)obj[3]);
			report.setReceiveCount(toLong(obj[4]));
			report.setSearchStart(StringUtil.toNotNullStr(obj[5]));
			report.setSearchEnd(StringUtil.toNotNullStr(obj[6]));
			
			
			//report.setId(toInteger(obj[5]));
			//report.setPositionCode((String)obj[1]);
			//report.setPushDate((String)obj[3]);
			//report.setPushCount(toLong(obj[4]));
			
			//report.setPositionName((String)obj[6]);
			
			//report.setCategoryName((String)obj[8]);
			
			list.add(report);
		}
		return list;
	}

	
}
