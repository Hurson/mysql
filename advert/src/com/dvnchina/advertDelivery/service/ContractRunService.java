package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.ContractRun;
/**
 * 合同运营相关操作
 * @author lester
 *
 */
public interface ContractRunService {
	/**
	 * 保存合同运营相关信息
	 * @param advertPositionType
	 * @return
	 */
	public boolean saveContractRun(ContractRun contractRun);
	/**
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	public List<ContractRun> page(Map condition, int start, int end);
	/**
	 * 查询合同运营信息
	 * @param condition
	 * @return
	 */
	public int queryCount(Map condition);
	/**
	 * 删除指定合同运营记录
	 * @return
	 */
	public boolean removeContractRun(int contractRunId);
	/**
	 * 更新合同运营记录
	 * @return
	 */
	public boolean updateContractRun(ContractRun contractRun);
	
	/**
	 * 按照资源合同号即合同主键查询
	 * @param id
	 * @return
	 */
	public List<ContractRun> getContractRunById(Integer id);
	
	/**
	 * 根据合同号查询相关广告位 
	 * @param contractId 合同id  
	 * @return
	 */
	public List<AdvertPosition> getAdvertPositionByContractId(Integer contractId);
}
