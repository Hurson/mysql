package com.dvnchina.advertDelivery.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dvnchina.advertDelivery.dao.ContractRunDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ContractRun;
import com.dvnchina.advertDelivery.service.ContractRunService;

public class ContractRunServiceImpl implements ContractRunService{
	
	private ContractRunDao contractRunDao;
	
	@Override
	public List<ContractRun> page(Map condition, int start, int end) {
		StringBuffer queryContractRun = new StringBuffer();
		queryContractRun.append("SELECT * FROM T_CONTRACT");
		
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryContractRun.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryContractRun.append(columnName).append("=").append("'").append(entry.getValue()).append("'");
				count++;
				if(count<mapSize){
					queryContractRun.append(" AND ");
				}
			}
		}
		
		return contractRunDao.page(queryContractRun.toString(), start, end);
	}

	public ContractRunDao getContractRunDao() {
		return contractRunDao;
	}

	public void setContractRunDao(ContractRunDao contractRunDao) {
		this.contractRunDao = contractRunDao;
	}

	@Override
	public int queryCount(Map condition) {
		StringBuffer queryContractRun = new StringBuffer();
		queryContractRun.append("SELECT COUNT(*) FROM T_CONTRACT");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryContractRun.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryContractRun.append(columnName).append("=").append("'").append(entry.getValue()).append("'");
				count++;
				if(count<mapSize){
					queryContractRun.append(" AND ");
				}
			}
		}
		return contractRunDao.queryTotalCount(queryContractRun.toString());
	}

	@Override
	public boolean saveContractRun(ContractRun contractRun) {
		boolean flag = false;
		int operationCount = contractRunDao.saveContractRun(contractRun);
		if(operationCount>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean removeContractRun(int contractRunId) {
		boolean flag = false;
		int result = contractRunDao.removeContractRun(contractRunId);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean updateContractRun(ContractRun contractRun) {
		boolean flag = false;
		int result = contractRunDao.updateContractRun(contractRun);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public List<ContractRun> getContractRunById(Integer id) {
		return contractRunDao.getContractRunById(id);
	}

	@Override
	public List<AdvertPosition> getAdvertPositionByContractId(Integer contractId) {
		return contractRunDao.queryPositionByContractIdFromRun(contractId);
	}

}
