package com.dvnchina.advertDelivery.log.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.dao.OperateLogDao;

public class OperateLogDaoImpl extends BaseDaoImpl implements OperateLogDao {
	
	/**
	 * 分页查询操作日志信息
	 * @param operateLog
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOperateLogList (OperateLog operateLog,int pageNo, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("select id,name,module_name,operate_type,operate_result,operate_ip,");
		sql.append(" date_format(operate_time,'%Y-%m-%d %H:%i:%S') as operateTime ,operate_info from");
		sql.append(" (select id,'超级管理员' as name,module_name,operate_type,operate_result,operate_ip,operate_time,operate_info");
		sql.append(" from t_operate_log where user_id=0 ");
		sql.append(" union ");
		sql.append(" select o.id,u.name,o.module_name,o.operate_type,o.operate_result,o.operate_ip,o.operate_time,o.operate_info ");
		sql.append(" from t_operate_log o,t_user u where o.user_id = u.user_id ) log where 1=1 ");
		
		
		if(operateLog != null){
			if(!StringUtils.isEmpty(operateLog.getUserName())){
				sql.append(" and name like '%").append(operateLog.getUserName()).append("%' ");
			}
			if(!StringUtils.isEmpty(operateLog.getModuleName())){
				sql.append(" and module_name like '%").append(operateLog.getModuleName()).append("%' ");
			}
			if(!StringUtils.isEmpty(operateLog.getBeginTime())){
				sql.append(" and operate_time >= str_to_date('").append(operateLog.getBeginTime()).append("','%Y-%m-%d %H:%i:%S')");
			}
			if(!StringUtils.isEmpty(operateLog.getEndTime())){
				sql.append(" and operate_time <= str_to_date('").append(operateLog.getEndTime()).append("','%Y-%m-%d %H:%i:%S')");
			}
		}
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
		sql.append("order by operate_time desc");
		List<OperateLog> list = getOperateLogList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<OperateLog> getOperateLogList(List<?> resultList) {
		List<OperateLog> list = new ArrayList<OperateLog>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OperateLog log = new OperateLog();
			log.setId(toInteger(obj[0]));
			log.setUserName((String)obj[1]);
			log.setModuleName((String)obj[2]);
			log.setOperateType((String) obj[3]);
			log.setOperateResult(toInteger(obj[4]));
			log.setOperateIP((String)obj[5]);
			log.setOperateTime((String)obj[6]);
			log.setOperateInfo((String)obj[7]);
			list.add(log);
		}
		return list;
	}

}
