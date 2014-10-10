package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.bean.contract.ContractContractADRelation;
import com.dvnchina.advertDelivery.model.ContractBackup;
/**
 * 合同维护相关操作
 * @author lester
 *
 */
public interface ContractBackupService {
	/**
	 * 保存合同维护相关操作
	 * @param contractBackup
	 * @return
	 */
	public Map<String,String> saveContractBackup(ContractBackup contractBackup);
	/**
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	public List<ContractBackup> page(Map condition, int start, int end);
	/**
	 * 查询记录数
	 * @param condition
	 * @return
	 */
	public int queryCount(Map condition);
	
	/**
	 * 删除指定记录
	 * @return
	 */
	public boolean removeContractBackup(int contractBackupId);
	/**
	 * 更新指定记录
	 * @return 
	 */
	public Map<String,String> updateContractBackup(String contractParam,String contractParamBefore,String comparedFormResult,String contractAd,Integer userId);
	/**
	 * 审核合同
	 * @param contractParam
	 * @return
	 */
	public Map<String,String> updateApprovalContractContractBackup(String contractParam,Integer userId,String username);
	/**
	 * 按照客户id查询
	 * @param id
	 * @return
	 */
	public List<ContractBackup> getContractBackupById(Integer id);
	
	/**
	 * 根据合同号加载相关数据
	 * @param contractId 合同id  
	 * @return
	 */
	public ContractBackup getContractByContractId(Integer contractId) throws Exception;
	/**
	 * 获取合同和合同关系表对应关系
	 * @param contractId
	 * @return
	 * @throws Exception
	 */
	public List<ContractContractADRelation> getContractContractADRelation(Integer contractId) throws Exception;
}
