package com.dvnchina.advertDelivery.log.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.log.bean.AuditLog;
import com.dvnchina.advertDelivery.log.dao.AuditLogDao;

public class AuditLogDaoImpl extends BaseDaoImpl implements AuditLogDao{
	
	/**
	 * 查询审核日志记录
	 * @param log
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAuditLogList(AuditLog log, int pageNo, int pageSize){
		
		if(log != null && log.getRelationType().intValue()==Constant.AUDIT_RELATION_TYPE_ORDER){
			StringBuffer sql = new StringBuffer();
			sql.append("select id,relation_type,relation_id,order_code,state,operator_id,userName,");
			sql.append(" date_format(audit_time,'%Y-%m-%d %H:%i:%S') as auditTime,audit_opinion from");
			sql.append(" ( select l.id,l.relation_type,l.relation_id,o.order_code,l.state,");
			sql.append(" l.operator_id,'超级管理员' as userName,l.audit_time,l.audit_opinion");
			sql.append(" from t_audit_log l,t_order o where l.relation_id = o.id and l.relation_type=1 and l.operator_id=0");
			sql.append(" union ");
			sql.append(" select l.id,l.relation_type,l.relation_id,o.order_code,l.state,");
			sql.append(" l.operator_id,u.name as userName,l.audit_time,l.audit_opinion");
			sql.append(" from t_audit_log l,t_order o, t_user u ");
			sql.append(" where l.relation_id = o.id and l.operator_id = u.user_id and l.relation_type=1 ) auditLog where 1=1 ");
			
			if(log.getRelationId() != null){
				sql.append(" and relation_id = "+log.getRelationId());
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
			sql.append(" order by audit_time desc");
			List<AuditLog> list = getAuditLogList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
			page.setDataList(list);
			return page;
		}else{
			return null;
		}
	}
	
	private List<AuditLog> getAuditLogList(List<?> resultList) {
		List<AuditLog> list = new ArrayList<AuditLog>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			AuditLog log = new AuditLog();
			log.setId(toInteger(obj[0]));
			log.setRelationType(toInteger(obj[1]));
			log.setRelationId(toInteger(obj[2]));
			log.setRelationName((String)obj[3]);
			log.setState(toInteger(obj[4]));
			log.setOperatorId(toInteger(obj[5]));
			log.setOperatorName((String)obj[6]);
			log.setAuditTime((String)obj[7]);
			log.setAuditOpinion((String)obj[8]);
			list.add(log);
		}
		return list;
	}

}
