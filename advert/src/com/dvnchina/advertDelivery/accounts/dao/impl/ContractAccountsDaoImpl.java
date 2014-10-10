package com.dvnchina.advertDelivery.accounts.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dvnchina.advertDelivery.accounts.QueryConditionName;
import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.accounts.dao.ContractAccountsDao;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.Contract;

public class ContractAccountsDaoImpl extends BaseDaoImpl implements ContractAccountsDao {

	@Override
	public PageBeanDB queryContractAccountsList(int pageNo, int pageSize, Map<String, String> queryCondition, int contractId) {
		String hql = "from ContractAccounts c where contractId= "+contractId;
		String accountOrder = queryCondition.get("accountOrder");
		if(accountOrder != null && !accountOrder.isEmpty()){
			hql += " and c.accountsCode like '%"+accountOrder+"%'";
		}
		hql += " order by c.accountsId desc";
		//hql = this.addQueryCondition(hql, queryCondition);
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
		List<ContractAccounts> list = (List<ContractAccounts>)this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}

	@Override
	public ContractAccounts getContractAccountsById(int lid) {
		return (ContractAccounts) this.get(ContractAccounts.class, lid);
	}

	@Override
	public void saveContractAccounts(ContractAccounts contractAccounts) {
		this.save(contractAccounts);
	}

	@Override
	public void updateContractAccounts(ContractAccounts contractAccounts) {
		this.update(contractAccounts);
	}
	
	/**
	 * 添加查询条件
	 * @param hql
	 * @param queryCondition
	 * @return hql
	 */
	private String addQueryCondition(String hql, Map<String, String> queryCondition) {
		
		// 添加台账订单号查询条件
		String accountCode = queryCondition.get(QueryConditionName.ACCOUNT_CODE);
		if(accountCode != null && !accountCode.isEmpty()){
			hql += "and c.accountsCode = '"+accountCode+"'";
		}
		return hql;
	}

	/**
	 * 获取当前合同的最后一次交费的到期结束时间
	 * @param contractId
	 * @return ContractAccounts
	 */
	@Override
	public ContractAccounts getContractsDeadLine(int contractId) {
		ContractAccounts obj = null;
		Session session = this.getSessionFactory().openSession();
		String hql = "from ContractAccounts c where c.payVallidityPeriodEnd = (select max(t.payVallidityPeriodEnd) from ContractAccounts t where t.contractId = :contractId)";
		Query query = session.createQuery(hql);
		query.setInteger("contractId", contractId);
		if(query.list() != null && query.list().size() >0){
			obj = (ContractAccounts)query.list().get(0);
		}
		session.close();
		return obj;
	}
	
	/**
	 * @description: 首页代办7天内合同到期欠费的合同数
	 * @return 7天内合同到期欠费的合同数
	 */
	public int getExpireingContractCount(String contractIds, Date today, Date shiftDay){
		BigInteger count = null;
		Session session = this.getSessionFactory().openSession();
		//String hql = "select count(f.c2) from(select * from (select max(t.pay_vallidity_period_end) c, t.CONTRACT_ID c2 from t_contract_accounts t GROUP BY t.contract_id) x where x.c < current_date + 7 and x.c >= current_date ) f ";
		String hql = "select count(f.c2) from(select * from (select max(t.pay_vallidity_period_end) c, t.CONTRACT_ID c2 from t_contract_accounts t ";
		if(!contractIds.isEmpty()){
			hql += " where t.CONTRACT_ID in ("+contractIds+") ";
		}
		hql += " GROUP BY t.contract_id) x where x.c BETWEEN :today AND :shiftDay ) f ";
		Query query = session.createSQLQuery(hql);
		query.setDate("today", today);
		query.setDate("shiftDay", shiftDay);
		if(query.list() != null && query.list().size() >0){
			count = (BigInteger) query.list().get(0);
		}
		session.close();
		return count.intValue();
	}

	@Override
	public PageBeanDB queryContract(String contractName, int pageNo, int pageSize) {
		String hql = "from Contract t ";
		if (contractName !=null && !contractName.isEmpty()){
			hql +=" where t.contractName like '%"+contractName+"%'";
	    }
		int rowcount = this.getTotalCountHQL(hql, null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		if(pageSize == 0){
			pageSize = 5;
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
		List<Contract> list = (List<Contract>)this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
		
	}
}
